package com.missu.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.missu.Bean.DaoSession;
import com.missu.Bean.QQMessage;
import com.missu.Bean.QQMessageType;
import com.missu.Bean.Users;
import com.missu.Bean.UsersDao;
import com.missu.R;
import com.missu.Util.QQConnection;

import java.io.IOException;

public class ChangePassActivity extends AppCompatActivity {

    EditText lastPass,newPass,confirmPass;
    Button confirm;
    ImApp app;
    QQConnection conn;
    DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass2);
        lastPass = (EditText)findViewById(R.id.et_changepass_oldpass);
        newPass = (EditText)findViewById(R.id.et_changepass_newpass);
        confirmPass = (EditText)findViewById(R.id.et_changepass_confirmpass);
        confirm = (Button)findViewById(R.id.btn_changepass_confirm);
        app = (ImApp) getApplication();
        daoSession = app.getDaoSession();


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastPass.getText().toString().trim().equals("")){
                    Toast.makeText(ChangePassActivity.this,"原理的密码不能为空，请重新输入",Toast.LENGTH_SHORT).show();
                }
                if (newPass.getText().toString().trim().equals("")){
                    Toast.makeText(ChangePassActivity.this,"新密码不能为空，请重新输入",Toast.LENGTH_SHORT).show();
                }
                if (lastPass.getText().toString().equals(app.getPassword())){
                    if (newPass.getText().toString().equals(confirmPass.getText().toString())){
                        QQMessage message = new QQMessage();
                        message.type = QQMessageType.MSG_TYPE_UPDATE;
                        message.from = app.getMyAccount();
                        
                        message.content = app.getMyAccount()+"#"+newPass.getText().toString()+"#"+ app.getNick() + "#" + app.getAvater()+"#"+app.getSex();
                        conn = app.getMyConn();
                        try {
                            conn.sendMessage(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Users users = daoSession.getUsersDao().queryBuilder().where(UsersDao.Properties.User_name.eq(app.getMyAccount())).unique();
                        Users user = new Users(users.getId(),users.getUser_name(),newPass.getText().toString(),users.getUser_nickname(),"true",users.getUser_profile());
                        daoSession.getUsersDao().update(user);
                        Toast.makeText(getApplicationContext(),"密码修改成功",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ChangePassActivity.this,MyActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(ChangePassActivity.this,"两次输入的新密码不一致,请重新输入",Toast.LENGTH_SHORT).show();
                        newPass.setText("");
                        lastPass.setText("");
                    }
                }else{
                    Toast.makeText(ChangePassActivity.this,"你输入的原来的的密码不正确，请重新输入",Toast.LENGTH_SHORT).show();
                    lastPass.setText("");
                }
            }
        });

    }
    }

