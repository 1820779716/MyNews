package com.gxun.mynews;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.gxun.mynews.Adapter.CollectAdapter;
import com.gxun.mynews.entity.Collect;
import com.gxun.mynews.entity.NewsInfo;
import com.gxun.mynews.util.AppConst;
import com.gxun.mynews.util.HttpUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CollectActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvCancel, tvClear;

    private ListView lvCollect;

    private List<NewsInfo> newsInfoList = new ArrayList<>();
    private static final int COLLECT_LIST = 1;

    public static String getColletAddr = AppConst.Collect.getCollect;
    public static String getNewsinfo = AppConst.NewsInfoList.getNews;
    public static String delAllCollect = AppConst.Collect.delAllCollect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        initWidget();
        initData();
    }
    protected void initWidget(){
        tvCancel = findViewById(R.id.cancel);
        tvClear = findViewById(R.id.clear);
        lvCollect = findViewById(R.id.collect_list);
    }
    protected void initData(){
        tvCancel.setOnClickListener(this);
        tvClear.setOnClickListener(this);
        Collect collect = new Collect();
        collect.setUserId(MainActivity.userId);
        getCollectWithOkHttp(getColletAddr, collect);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel: goBack(); break;
            case R.id.clear: clearCollect(); break;
        }
    }
    public void goBack(){
        Toast.makeText(this,"退出我的收藏", Toast.LENGTH_LONG).show();
        finish();
    }
    public void clearCollect(){
        new AlertDialog.Builder(this).setTitle("是否清空收藏列表")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“确认”后的操作
                        Collect collect = new Collect();
                        collect.setUserId(MainActivity.userId);
                        delAllCollectWithOkhttp(delAllCollect, collect);
                    }
                })
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“返回”后的操作,这里不设置没有任何操作
                    }
                }).show();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case COLLECT_LIST:
                    List<NewsInfo> newsInfoList = (List<NewsInfo>) msg.obj;
                    CollectAdapter listAdapter = new CollectAdapter(newsInfoList, lvCollect);
                    lvCollect.setAdapter(listAdapter);
                    break;
            }
        }
    };

    private void delAllCollectWithOkhttp(String address, Collect c){
        HttpUtil.delAllCollectWithOkhttp(address, c, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = response.body().string();
                try {
                    final JSONObject jsonObject = new JSONObject(responseData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getCollectWithOkHttp(String address, Collect c){
        HttpUtil.getCollectWithOkHttp(address, c, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = response.body().string();
                try {
                    final JSONObject jsonObject = new JSONObject(responseData);
                    final List<Collect> collects = JSON.parseObject(jsonObject.getString("collectList"),new TypeReference<List<Collect>>(){});
                    System.out.println(collects.size());
                    if (collects.size() != 0) {
                        for (Collect c : collects) {
                            NewsInfo newsInfo = new NewsInfo();
                            newsInfo.setNewsId(c.getNewId());
                            getNewsWithOkhttp(getNewsinfo, newsInfo);
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Message message = new Message();
                        message.what = COLLECT_LIST;
                        message.obj = newsInfoList;
                        handler.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getNewsWithOkhttp(String address, final NewsInfo newsInfo){
        HttpUtil.getNewsWithOkhttp(address, newsInfo, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = response.body().string();
                try {
                    final JSONObject jsonObject = new JSONObject(responseData);
                    final List<NewsInfo> newsInfos = JSON.parseObject(jsonObject.getString("newsInfoList"),new TypeReference<List<NewsInfo>>(){});
                    for (NewsInfo n : newsInfos) {
                        newsInfoList.add(n);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
