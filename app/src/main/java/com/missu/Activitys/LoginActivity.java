package com.missu.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.missu.R;

public class LoginActivity extends AppCompatActivity {

    EditText login_user,login_password;
    Button login_go,login_sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        login_user = (EditText)findViewById(R.id.et_login_user);
        login_password = (EditText)findViewById(R.id.et_login_password);
        login_go = (Button)findViewById(R.id.btn_login_login);
        login_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        login_sign = (Button)findViewById(R.id.btn_login_sign);
        login_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignInActivity.class);
                startActivity(intent);

            }
        });

    }
}
