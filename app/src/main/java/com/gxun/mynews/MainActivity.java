package com.gxun.mynews;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.android.material.navigation.NavigationView;
import com.gxun.mynews.Adapter.ListAdapter;
import com.gxun.mynews.Adapter.RecycleAdapter;
import com.gxun.mynews.entity.NewsInfo;
import com.gxun.mynews.util.AppConst;
import com.gxun.mynews.util.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Callback;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private TextView tvMenu, tvAccount;
    private NavigationView navigationView;
    private RecyclerView recyclerView;
    private ImageView ivHead;
    private ListView listView;

    private ListAdapter listAdapter;
    public static final int LIST_VIEW = 1;

    private long exitTime; // 获取第一次点击返回键的系统时间
    private static final int INTERNET_REQUEST_CODE = 1;
    private static final int LOGIN_REQUEST_CODE = 2;

    private static boolean isLogin = false;
    public static String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPermission();
        initWidget();
        initData();

        String newsAddress = AppConst.NewsInfoList.getAllNews;
        getAllNewsWithOkHttp(newsAddress);
    }

    private void initWidget() {
        drawerLayout = findViewById(R.id.drawerLayout);
        tvMenu = findViewById(R.id.menu);
        navigationView = findViewById(R.id.navigation_view);
        recyclerView = findViewById(R.id.recycler_view);
        listView = findViewById(R.id.list_view);
        View v = navigationView.getHeaderView(0);
        tvAccount = (TextView) v.findViewById(R.id.account);
        ivHead = (ImageView) v.findViewById(R.id.head);
    }

    private void initData() {
        navigationView.setNavigationItemSelectedListener(this);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        layout.setOrientation(LinearLayoutManager.HORIZONTAL);//设置为横向排列
        recyclerView.setLayoutManager(layout);
        List<String> list = new ArrayList<>();
        list.add("推荐");
        list.add("热门");
        list.add("抗疫");
        list.add("体育");
        list.add("娱乐");
        list.add("科技");
        RecycleAdapter adapter = new RecycleAdapter(list);
        recyclerView.setAdapter(adapter);

        tvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 点击图标打开侧滑菜单
                if (!drawerLayout.isDrawerOpen(navigationView)) {
                    drawerLayout.openDrawer(navigationView);
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
    private void initPermission() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
        || ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
        ||ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //没有权限，申请权限
            String[] permissions = {Manifest.permission.INTERNET, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //申请权限
            ActivityCompat.requestPermissions(MainActivity.this, permissions, INTERNET_REQUEST_CODE);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.favorite:
                gotoFavorite();
                break;
            case R.id.history:
                gotoHistory();
                break;
            case R.id.exit:
                checkExit();
                break;
            case R.id.click_me:
                if (isLogin){
                    logout();
                }else{
                    startLogin();
                }
                break;
        }
        return false;
    }

    public void startLogin() {
        Intent intent = new Intent();
        intent.setClass(this, LoginActivity.class);
        startActivityForResult(intent, LOGIN_REQUEST_CODE);
    }

    public void logout() {
        isLogin = false;
        tvAccount.setText("点击上方头像登录");
        navigationView.getMenu().getItem(3).setTitle("登录");
        ivHead.setImageResource(R.mipmap.undefined);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {  //接收返回的数据
        switch (requestCode) {
            case LOGIN_REQUEST_CODE:
                if (resultCode == RESULT_OK) { //正常返回且有结果
                    super.onActivityResult(requestCode, resultCode, intent);
                    userId = intent.getExtras().getString("userId");
                    String userName = intent.getExtras().getString("userName");
                    tvAccount.setText("ID:" + userId + "\n用户名:" + userName);
                    isLogin = true;
                    navigationView.getMenu().getItem(3).setTitle("注销");
                    ivHead.setImageResource(R.mipmap.login_head);
                }
                break;
            default:
                Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT);
                break;
        }
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LIST_VIEW:
                    List<NewsInfo> newsInfoList = (List<NewsInfo>) msg.obj;
                    listAdapter = new ListAdapter(newsInfoList);
                    listView.setAdapter(listAdapter);
                    break;
            }
        }
    };

    public void getAllNewsWithOkHttp(String address){
        HttpUtil.getAllNewsWithOkhttp(address, new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                //在这里对异常情况进行处理
            }
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                final String responseData = response.body().string();
                try {
                    // 转为JSONObject对象
                    final JSONObject jsonObject = new JSONObject(responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (jsonObject.getBoolean("flag")==true) {
                                    final List<NewsInfo> newsInfoList = JSON.parseObject(jsonObject.getString("newsInfoList"),new TypeReference<List<NewsInfo>>(){});
                                    Message message = new Message();
                                    message.what = LIST_VIEW;
                                    message.obj = newsInfoList;
                                    handler.sendMessage(message);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void gotoFavorite() {
        if (isLogin) {
            Intent intent = new Intent();
            intent.setClass(this, CollectActivity.class);
            startActivityForResult(intent, LOGIN_REQUEST_CODE);
        }else{
            Toast.makeText(this, "请先登录", Toast.LENGTH_LONG).show();
        }
    }

    public void gotoHistory() {
        if (isLogin) {
            Intent intent = new Intent();
            intent.setClass(this, HistoryActivity.class);
            startActivityForResult(intent, LOGIN_REQUEST_CODE);
        }else{
            Toast.makeText(this, "请先登录", Toast.LENGTH_LONG).show();
        }
    }

    public void checkExit() {
        new AlertDialog.Builder(this).setTitle("是否退出应用程序")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“确认”后的操作
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“返回”后的操作,这里不设置没有任何操作
                    }
                }).show();
    }

    // 监听按键（返回键）
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    // 退出当前Activity
    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {  //系统时间减去exitTime
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish(); // 结束Activity
            System.exit(0);
        }
    }
}
