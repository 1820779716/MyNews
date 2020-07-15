package com.gxun.mynews;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gxun.mynews.entity.UserInfo;
import com.gxun.mynews.util.AppConst;
import com.gxun.mynews.util.HttpUtil;
import com.gxun.mynews.util.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvCancel;
    private EditText etAccount, etPassword, etRePassword, etEmail;
    private Button btnReset;
String TAG="ForgetPasswordActivity";
    private boolean isEmailRight = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        initWidget(); //初始化控件

        tvCancel.setOnClickListener(this);
        btnReset.setOnClickListener(this);

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ValidateEmail();
            }
        });
    }

    public void initWidget(){
        tvCancel = findViewById(R.id.cancel);
        etAccount = findViewById(R.id.account);
        etPassword = findViewById(R.id.password);
        etRePassword = findViewById(R.id.rePassword);
        etEmail = findViewById(R.id.email);
        btnReset = findViewById(R.id.reset_password);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reset_password: goReset(); break;
            case R.id.cancel: goBack(); break;
        }
    }

    public void goReset(){
        HashMap<String,Object> u = null;
        String forgetPassAddress= AppConst.UserInfo.forgetPassword;
        String account = etAccount.getText().toString();
        String password = etPassword.getText().toString();
        String rePassword = etRePassword.getText().toString();
        String email = etEmail.getText().toString();

        u.put("userId",account);
        u.put("check",email);
        u.put("newPassWord",rePassword);
       // UserInfo userInfo =new UserInfo(null,null,password,rePassword,email);
        if (account == null && account.equals("")){
            Toast.makeText(this, "账号不能为空", Toast.LENGTH_LONG).show();
        }else if (password == null && password.equals("")){
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_LONG).show();
        }else if (!password.equals(rePassword)){
            Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_LONG).show();
        }else if (!isEmailRight){
            Toast.makeText(this, "邮箱错误", Toast.LENGTH_LONG).show();
        }else{
            forgetPasswordWithOkHttp(forgetPassAddress,u);
        }
    }

    public void goBack(){
        finish();
    }


    private void ValidateEmail(){
        isEmailRight = Validator.isEmail(etEmail.getText().toString());
        if(isEmailRight){
            //是邮件地址则不处理
            etEmail.setTextColor(Color.parseColor("#000000"));
        }else{
            etEmail.setTextColor(Color.parseColor("#FF3030"));
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

    public void forgetPasswordWithOkHttp(String address, HashMap<String,Object> u) {
        HttpUtil.forgetPasswordWithOkHttp(address, u, new Callback() {
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
                                if (jsonObject.getBoolean("flag") == true) {
                                    Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(ForgetPasswordActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                                } else {
                                    //Toast.makeText(RegisterActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
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
}
