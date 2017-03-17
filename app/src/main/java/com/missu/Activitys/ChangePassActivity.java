package com.missu.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anye.greendao.gen.DaoSession;
import com.anye.greendao.gen.UsersDao;
import com.missu.Adapter.MyApplication;
import com.missu.Bean.Users;
import com.missu.R;

public class ChangePassActivity extends AppCompatActivity {

    EditText lastPass,newPass,confirmPass;
    Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        lastPass = (EditText)findViewById(R.id.et_changepass_oldpass);
        newPass = (EditText)findViewById(R.id.et_changepass_newpass);
        confirmPass = (EditText)findViewById(R.id.et_changepass_confirmpass);
        confirm = (Button)findViewById(R.id.btn_changepass_confirm);
        final DaoSession daoSession = MyApplication.getInstances().getDaoSession();
        final Users users = daoSession.getUsersDao().queryBuilder().where(UsersDao.Properties.User_name.eq(MainActivity.USERNAME)).build().unique();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastPass.getText().toString().trim().equals("")){
                    Toast.makeText(ChangePassActivity.this,"原理的密码不能为空，请重新输入",Toast.LENGTH_SHORT).show();
                }
                if (newPass.getText().toString().trim().equals("")){
                    Toast.makeText(ChangePassActivity.this,"新密码不能为空，请重新输入",Toast.LENGTH_SHORT).show();
                }
                if (lastPass.getText().toString().equals(users.getUser_password())){
                    if (newPass.getText().toString().equals(confirmPass.getText().toString())){
                          Users users1 = new Users(users.getId(),users.getUser_name(),newPass.getText().toString(),users.getUser_nickname(),users.getUser_sex(),users.getUser_profile(),users.getUnread_message());
                            daoSession.getUsersDao().update(users1);
                        Toast.makeText(getApplicationContext(),"密码修改成功",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ChangePassActivity.this,MainActivity.class);
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
