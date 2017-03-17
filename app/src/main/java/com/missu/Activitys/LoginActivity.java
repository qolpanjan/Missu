package com.missu.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anye.greendao.gen.UsersDao;
import com.missu.Adapter.MyApplication;
import com.missu.Bean.Users;
import com.missu.R;

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
                   List<Users> users = usersDao.loadAll();//获取User表的所有数据
                    for (int i=0;i<users.size();i++) {//遍历从数据库表获取的所有数据
                        if (users.get(i).getUser_name().equals(user_name)){//如果找到用户输入的ID
                        //if (users.get(0).getUser_name().equals(user_name)) {
                            if (users.get(i).getUser_password().equals(user_pass)) {//就会查找对应的密码，密码正确就会跳转
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                /**
                                 * 用sharedPreferences实现应用程序记住用户登录状态
                                 */
                                SharedPreferences.Editor editor = getSharedPreferences("login",MODE_PRIVATE).edit();
                                editor.putBoolean("login",true);
                                editor.putString("name",user_name);
                                editor.commit();
                                Toast.makeText(LoginActivity.this, "恭喜你，登录成功！", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                                break;
                            } else {//如果密码错误提示用户并且情况密码输入框
                                Toast.makeText(LoginActivity.this, "密码输入错误，请重新输入", Toast.LENGTH_SHORT).show();
                                login_password.setText("");
                            }
                        } else {//如果所有用户中找不到用户名，提示用户用户名不存在并且情况用户名输入框和密码输入框
                            Toast.makeText(LoginActivity.this, "账户名不存在，请重新输入", Toast.LENGTH_SHORT).show();
                            login_user.setText("");
                            login_password.setText("");

                        }
                    }
                   // Log.e("MainActivity",users.get(0).getUser_password());
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
