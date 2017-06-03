package com.missu.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.missu.Bean.QQMessage;
import com.missu.Bean.QQMessageType;
import com.missu.R;
import com.missu.Util.MyTime;
import com.missu.Util.QQConnection;
import com.missu.Util.ThreadUtils;

import java.io.IOException;

/**
 * 注册活动
 * Created by alimj on 2017/4/29.
 */

public class ResitActivity extends AppCompatActivity {

    EditText account,password,nick,confirm,phone;
    RadioGroup rg;
    Button reigter;
    RadioButton rbtn1,rbtn2;
    String sex = "true";
    ImApp app;
    QQConnection conn;
    ProgressBar progressBar;


    QQConnection.OnMessageListener listener = new QQConnection.OnMessageListener() {
        @Override
        public void onReveive(QQMessage msg) {
            if (QQMessageType.MSG_TYPE_SUCCESS.equals(msg.type)){
                ThreadUtils.runInUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ResitActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ResitActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

            }else if (QQMessageType.MSG_TYPE_FAILURE.equals(msg.type)){
                ThreadUtils.runInUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ResitActivity.this,"账户已存在，请登录或者更换账号",Toast.LENGTH_SHORT).show();
                        account.setText("");
                    }
                });

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        account = (EditText)findViewById(R.id.et_sign_user);
        password = (EditText)findViewById(R.id.et_sign_password);
        confirm = (EditText)findViewById(R.id.et_sign_confirm);
        nick = (EditText)findViewById(R.id.et_sign_nickname);
        phone = (EditText)findViewById(R.id.et_sign_phone);
        rg = (RadioGroup)findViewById(R.id.rg_choose_sex);
        rbtn1 = (RadioButton)findViewById(R.id.rbt_sex_man);
        rbtn2 = (RadioButton)findViewById(R.id.rbt_sex_woman);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rbt_sex_man){
                    sex = "true";
                }
                if (checkedId == R.id.rbt_sex_woman){
                    sex = "boolean";
                }
            }
        });
        progressBar = (ProgressBar)findViewById(R.id.sign_progress);



        app = (ImApp) getApplication();
        conn = app.getMyConn();
        //判断Socket变量
        if (conn==null){
            try {
                conn =new QQConnection("115.159.109.131", 8090);// Socket
                conn.connect();
                Log.e("ResitActivity","NEW CONNECT");
            } catch (IOException e) {
                e.printStackTrace();
            }
            //链接之后把链接保存在Application作为长连接
            app = (ImApp) getApplication();
            // 保存一个长连接
            app.setMyConn(conn);
        }
        conn.addOnMessageListener(listener);

        reigter = (Button)findViewById(R.id.btn_sign_sign);
        reigter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String accountStr = account.getText().toString().trim();
                String passwordStr = password.getText().toString().trim();
                String confirmStr = confirm.getText().toString().trim();
                String nickStr = nick.getText().toString();
                String phoneStr = phone.getText().toString().trim();


                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        if (checkedId == R.id.rbt_sex_man){
                            sex = "true";
                        }
                        if (checkedId == R.id.rbt_sex_woman){
                            sex = "false";
                        }
                    }
                });
                if (accountStr==null||accountStr.equals("")){
                    Toast.makeText(ResitActivity.this,"账户不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (passwordStr==null||passwordStr.equals("")){
                    Toast.makeText(ResitActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (confirmStr==null||confirmStr.equals("")){
                    Toast.makeText(ResitActivity.this,"确认密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(phoneStr==null||passwordStr.equals("")){
                    Toast.makeText(ResitActivity.this,"手机号不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (nick==null||nick.equals("")){
                    Toast.makeText(ResitActivity.this,"昵称不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }


                if (!(passwordStr.equals(confirmStr))){
                    Toast.makeText(ResitActivity.this,"两次输入密码不一样",Toast.LENGTH_SHORT).show();
                    return;
                }

                final QQMessage  msg = new QQMessage(QQMessageType.MSG_TYPE_REGISTER,accountStr,nickStr,"0","to",accountStr+"#"+passwordStr+"#"+nickStr+"#"+phoneStr+"#"+sex, MyTime.geTime());
                //msg.content = accountStr+"#"+passwordStr+"#"+nickStr+"#"+phoneStr+"#"+sex;
                //msg.type = QQMessageType.MSG_TYPE_REGISTER;
                ThreadUtils.runInSubThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            conn.sendMessage(msg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });


    }




}
