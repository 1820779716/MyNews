package com.gxun.mynews;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.gxun.mynews.entity.NewsInfo;
import com.gxun.mynews.util.AppConst;
import com.gxun.mynews.util.HttpUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvBack, tvCollectIt, tvTitle, tvAuthor, tvTime, tvBrowseCount, tvDetail;
    private ImageView ivIcon;

    public static String getNewsinfo = AppConst.NewsInfoList.getNews;
    public static final int DETAIL = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_info_detail);
        initWidget();
        initData();
    }

    public void initWidget(){
        tvBack = findViewById(R.id.cancel);
        tvCollectIt = findViewById(R.id.collect_this);
        tvTitle = findViewById(R.id.detail_title);
        tvAuthor = findViewById(R.id.detail_author);
        tvTime = findViewById(R.id.detail_time);
        tvBrowseCount = findViewById(R.id.detail_browseCount);
        tvDetail = findViewById(R.id.detail_detail);
        ivIcon = findViewById(R.id.detail_img);
    }

    public void initData(){
        tvBack.setOnClickListener(this);
        tvCollectIt.setOnClickListener(this);

        Intent intent = getIntent();
        NewsInfo newsInfo = new NewsInfo();
        String newsId = intent.getStringExtra("newId");
        newsInfo.setNewsId(newsId);

        getNewsWithOkhttp(getNewsinfo, newsInfo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel: goBack(); break;
            case R.id.collect_this: break;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DETAIL:
                    NewsInfo newsInfo = (NewsInfo)msg.obj;
                    tvTitle.setText(newsInfo.getTitle());
                    tvAuthor.setText("作者："+newsInfo.getAuthor());
                    tvTime.setText("时间："+newsInfo.getTime());
                    tvBrowseCount.setText("访问量："+String.valueOf(newsInfo.getBrowseCount()));
                    Glide.with(DetailActivity.this).load(newsInfo.getImgUrl()).into(ivIcon);
                    tvDetail.setText(newsInfo.getDetail());
                    break;
            }
        }
    };
    private void getNewsWithOkhttp(String address, NewsInfo newsInfo){
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

                        Message message = new Message();
                        message.what = DETAIL;
                        message.obj = newsInfos.get(0);
                        handler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void goBack(){ // 结束Activity并返回MainActivity
        finish();
    }

    // 监听按键（返回键）
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
