package com.missu.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.anye.greendao.gen.TranslateDao;
import com.anye.greendao.gen.UsersDao;
import com.missu.Adapter.MyApplication;
import com.missu.Bean.Users;
import com.missu.R;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle("设置");
        Button signOut = (Button)findViewById(R.id.btn_setting_signout);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("login",MODE_PRIVATE).edit();
                editor.clear();
                editor.commit();

                Users users =MyApplication.getInstances().getDaoSession().getUsersDao().queryBuilder().where(UsersDao.Properties.User_name.eq(MainActivity.USERNAME)).unique();
                MyApplication.getInstances().getDaoSession().getUsersDao().delete(users);
                Intent intent = new Intent(SettingActivity.this,WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
