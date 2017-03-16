package com.missu.Activitys;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anye.greendao.gen.DaoSession;
import com.anye.greendao.gen.UsersDao;
import com.missu.Adapter.MyApplication;
import com.missu.Bean.Users;
import com.missu.R;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText login_user,login_password;
    Button login_go,login_sign;
    String user_name,user_pass;

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
                user_name = login_user.getText().toString();
                user_pass = login_password.getText().toString();
                UsersDao usersDao = MyApplication.getInstances().getDaoSession().getUsersDao();
                   List<Users> users = usersDao.loadAll();
                        if (users.get(0).getUser_name().equals(user_name)){
                            if (users.get(0).getUser_password().equals(user_pass)){
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                Toast.makeText(LoginActivity.this,"恭喜你，登录成功！",Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                            }else {
                                Toast.makeText(LoginActivity.this,"密码输入错误，请重新输入",Toast.LENGTH_SHORT).show();
                                login_password.setText("");
                            }
                        }else {
                            Toast.makeText(LoginActivity.this,"账户名不存在，请重新输入",Toast.LENGTH_SHORT).show();
                            //login_user.setText("");

                        }

                    Log.e("MainActivity",users.get(0).getUser_password());
                   //Users users =  daoSession.getUsersDao().queryBuilder().where(UsersDao.Properties.User_name.eq(user_name)).orderAsc(UsersDao.Properties.Id).build().unique();

                   /**

                    try {
                        UsersDao usersDao = daoSession.getUsersDao();
                        String sql = "SELECT * from " + UsersDao.TABLENAME +" WHERE "+ UsersDao.Properties.User_name+" ='"+user_name+"'";
                        Cursor users = daoSession.getDatabase().rawQuery(sql,null);
                        if(users.moveToFirst()) {
                            do {
                                String get_password = users.getString(users.getColumnIndex("User_password"));
                                Log.e("MainActivity",get_password);
                            }while (users.moveToNext());
                        }
                        users.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("MainActivity","查询失败！");
                    }

                    */

                //Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                //startActivity(intent);
                //finish();
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
