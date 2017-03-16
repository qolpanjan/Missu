package com.missu.Activitys;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.anye.greendao.gen.DaoSession;
import com.anye.greendao.gen.UsersDao;
import com.missu.Adapter.MyApplication;
import com.missu.Bean.Users;
import com.missu.R;

public class SignInActivity extends AppCompatActivity {

    EditText sign_user,sign_password,sign_password_confirm,sign_nickname;
    Button sign_in;
    String sex = "";

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
        RadioGroup rg = (RadioGroup)findViewById(R.id.rg_choose_sex);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rbt_sex_man){
                    sex = "男";
                }
                if (checkedId == R.id.rbt_sex_woman){
                    sex = "女";
                }
            }
        });

        sign_in = (Button)findViewById(R.id.btn_sign_sign);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DaoSession daoSession = MyApplication.getInstances().getDaoSession();
                if (sign_user.getText().toString().trim().equals("")){
                    Toast.makeText(SignInActivity.this,"用户名为必填项",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!(sign_password.getText().toString().trim().equals(sign_password_confirm.getText().toString().trim()))){
                    Toast.makeText(SignInActivity.this,"两次输入的密码不一样，请重新输入",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (sign_password.getText().toString().trim().equals("")){
                    Toast.makeText(SignInActivity.this,"密码为必填项",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (sign_nickname.getText().toString().trim().equals("")){
                    Toast.makeText(SignInActivity.this,"昵称为必填项",Toast.LENGTH_SHORT).show();
                    return;
                }

                Users users = new Users(null,sign_user.getText().toString().trim(),sign_password.getText().toString(),sign_nickname.getText().toString(),sex,null,0);
                try {
                    daoSession.getUsersDao().insert(users);
                }catch (Exception e){
                    Log.e("MainActivity","数据库插入！");
                }
                Toast.makeText(SignInActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignInActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
