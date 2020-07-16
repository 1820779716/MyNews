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
import com.gxun.mynews.Adapter.HistoryAdapter;
import com.gxun.mynews.entity.History;
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

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvCancel, tvClear;
    private ListView lvHistory;

    private List<NewsInfo> newsInfoList = new ArrayList<>();
    private static final int HISTORY_LIST = 1;


    public static String getHistoryAddress = AppConst.History.getHistory;
    public static String getNewsinfo = AppConst.NewsInfoList.getNews;
    public static String delAllHistoryAddr = AppConst.History.deleteHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initWidget();
        initData();
    }
    protected void initWidget(){
        tvCancel = findViewById(R.id.cancel);
        tvClear = findViewById(R.id.clear);
        lvHistory = findViewById(R.id.history_list);
    }
    protected void initData(){
        tvCancel.setOnClickListener(this);
        tvClear.setOnClickListener(this);
        History history = new History();
        history.setUserId(MainActivity.userId);
        getHistoryWithOkhttp(getHistoryAddress, history);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel: goBack(); break;
            case R.id.clear: clearHistory(); break;
        }
    }

    public void goBack(){
        Toast.makeText(this,"退出我的历史", Toast.LENGTH_LONG).show();
        finish();
    }

    public void clearHistory(){
        new AlertDialog.Builder(this).setTitle("是否清空历史记录")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“确认”后的操作
                        History history = new History();
                        history.setUserId(MainActivity.userId);
                        deleteAllHistoryWithOkhttp(delAllHistoryAddr, history);
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
                case HISTORY_LIST:
                    List<NewsInfo> newsInfoList = (List<NewsInfo>) msg.obj;
                    HistoryAdapter listAdapter = new HistoryAdapter(newsInfoList,lvHistory);
                    lvHistory.setAdapter(listAdapter);
                    break;
            }
        }
    };

    private void deleteAllHistoryWithOkhttp(String address, History h){
        HttpUtil.deleteAllHistoryWithOkhttp(address, h, new Callback() {
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

    private void getHistoryWithOkhttp(String address, History h) {
        HttpUtil.getHistoryWithOkhttp(address, h, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = response.body().string();
                try {
                    final JSONObject jsonObject = new JSONObject(responseData);
                    final List<History> historyList = JSON.parseObject(jsonObject.getString("historyList"),new TypeReference<List<History>>(){});
                    if (historyList.size() != 0) {
                        for (History h : historyList) {
                            NewsInfo newsInfo = new NewsInfo();
                            newsInfo.setNewsId(h.getNewId());
                            getNewsWithOkhttp(getNewsinfo, newsInfo);
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Message message = new Message();
                        message.what = HISTORY_LIST;
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
