package com.gxun.mynews;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CollectActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvCancel, tvClear;

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
        Toast.makeText(this,"退出我的收藏", Toast.LENGTH_LONG).show();
        finish();
    }
}
