package com.gxun.mynews;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gxun.mynews.entity.UserInfo;
import com.gxun.mynews.util.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;
    TextView tvCancel, tvRegister, tvForgetPassword;
    EditText etUserName, etPassword;
  String TAG ="loginActivity";
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
        String loginAddress="http://localhost:8080/MyNewServer/login";
        String account= etUserName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        UserInfo userInfo=new UserInfo();
        userInfo.setUserId(account);
        userInfo.setPassword(password);

        if (TextUtils.isEmpty(account)){
            Toast.makeText(this,"登录名不能为空", Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"密码不能为空", Toast.LENGTH_LONG).show();
        }else{
            loginWithOkHttp(loginAddress,userInfo);
        }
    }

    public void loginWithOkHttp(String address,UserInfo u){
        HttpUtil.loginWithOkHttp(address,u, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                final String responseData = response.body().string();

                try {
                    final JSONObject jsonObject = new JSONObject(responseData);
                    Log.d(TAG, responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (jsonObject.getBoolean("flag")==true){
                                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
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