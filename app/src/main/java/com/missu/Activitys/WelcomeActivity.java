package com.missu.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.missu.Adapter.MyApplication;
import com.missu.R;

public class WelcomeActivity extends Activity {

     boolean isLogin =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mhandler.sendEmptyMessageDelayed(1,2*1000);



    }


    Handler mhandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            SharedPreferences pref = getSharedPreferences("login",MODE_PRIVATE);
            boolean isLogin = pref.getBoolean("login",false);
            /**
             * 通过SharedPreference获取用户登录状态，从而动态决定要跳转到的页面
             */
            if (isLogin){
                Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }else {
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };
}
