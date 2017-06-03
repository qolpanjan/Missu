package com.missu.Activitys;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.missu.R;


public class AboutRoolActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_rool);
        SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
        boolean screen = pref.getBoolean("screen",false);

        setTitle(getString(R.string.about_rool));
    }
}
