package com.gxun.mynews;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;
    TextView tvCancel, tvRegister, tvForgetPassword;
    EditText etUserName, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initWidget(); //初始化控件

        tvCancel.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        tvForgetPassword.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }
    // 初始化控件
    protected void initWidget(){
        btnLogin = findViewById(R.id.login);
        tvCancel = findViewById(R.id.cancel);
        tvRegister = findViewById(R.id.register);
        etUserName = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        tvForgetPassword = findViewById(R.id.forget_password);
    }
    // 点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel: goBack(); break;
            case R.id.register: goRegister(); break;
            case R.id.forget_password: geResetPassword(); break;
            case R.id.login: goLogin(); break;
        }
    }

    public void goBack(){ // 结束Activity并返回MainActivity
        finish();
    }

    public void goRegister(){
        Intent intent = new Intent();
        intent.setClass(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void geResetPassword(){
        Intent intent = new Intent();
        intent.setClass(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void goLogin(){
        String userName = etUserName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(userName)){
            Toast.makeText(this,"登录名不能为空", Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"密码不能为空", Toast.LENGTH_LONG).show();
        }else{

        }
    }

    // 设置点击返回键事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {
            finish(); //按下返回键结束当前Activity
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}