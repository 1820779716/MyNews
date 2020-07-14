package com.gxun.mynews;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gxun.mynews.util.Validator;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvCancel;
    private EditText etUserName, etPassword, etRePassword, etEmail;
    private Button btnReset;

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
        etUserName = findViewById(R.id.username);
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
        String username = etUserName.getText().toString();
        String password = etPassword.getText().toString();
        String rePassword = etRePassword.getText().toString();
        String email = etEmail.getText().toString();
        if (username == null && username.equals("")){
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_LONG).show();
        }else if (password == null && password.equals("")){
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_LONG).show();
        }else if (!password.equals(rePassword)){
            Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_LONG).show();
        }else if (!isEmailRight){
            Toast.makeText(this, "邮箱错误", Toast.LENGTH_LONG).show();
        }else{

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
}
