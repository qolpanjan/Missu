package com.missu.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.missu.R;

public class SignInActivity extends AppCompatActivity {

    EditText sign_user,sign_password,sign_password_confirm,sign_nickname;
    Button sign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initView();
    }

    private void initView() {
        sign_user = (EditText)findViewById(R.id.et_sign_user);
        sign_password = (EditText)findViewById(R.id.et_sign_password);
        sign_password_confirm = (EditText)findViewById(R.id.et_sign_confirm);
        sign_nickname = (EditText)findViewById(R.id.et_sign_nickname);
        sign_in = (Button)findViewById(R.id.btn_sign_sign);

    }
}
