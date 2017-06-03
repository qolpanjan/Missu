package com.missu.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.missu.R;



public class AboutActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
        boolean screen = pref.getBoolean("screen",false);
        initView();
    }

    private void initView() {
        setTitle(getString(R.string.about));
        View about_rool=(View)findViewById(R.id.contect_auther);
        about_rool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AboutActivity.this,AboutRoolActivity.class);
                startActivity(intent);
            }
        });

        View contact_auther=(View)findViewById(R.id.about_rool);
        contact_auther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this,QR_Activity.class);
                startActivity(intent);
            }
        });

        View goto_check=(View)findViewById(R.id.goto_check);
        goto_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AboutActivity.this,getString(R.string.testing),Toast.LENGTH_SHORT).show();
            }
        });


    }
}
