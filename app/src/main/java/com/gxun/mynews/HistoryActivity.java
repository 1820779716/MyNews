package com.gxun.mynews;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gxun.mynews.entity.History;
import com.gxun.mynews.util.HttpUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvCancel, tvClear;
    List<History> HistoryList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        String address="http://localhost:8080/MyNews/getHistory";

        initWidget();
        initData();
    }
    protected void initWidget(){
        tvCancel = findViewById(R.id.cancel);
        tvClear = findViewById(R.id.clear);
    }
    protected void initData(){
        tvCancel.setOnClickListener(this);
        tvClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel: goBack(); break;
            case R.id.clear: break;
        }
    }
    public void goBack(){
        Toast.makeText(this,"退出我的历史", Toast.LENGTH_LONG).show();
        finish();
    }

//    private void getHistoryWithOkhttp(String address) {
//        HttpUtil.getHistoryWithOkhttp(address, new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                String responseData = response.body().string();
////                Log.i("11111",responseData);
//                Gson gson = new Gson();
//                HistoryList = gson.fromJson(responseData, new TypeToken<List<History>>() {}.getType());
//                Message message = new Message();
//                message.what = GRT_CART;
//                message.obj = HistoryList;
//                handler.sendMessage(message);
//            }
//        });
//
//    }
}
