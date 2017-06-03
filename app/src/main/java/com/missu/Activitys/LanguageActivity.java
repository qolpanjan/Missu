package com.missu.Activitys;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.missu.R;

import java.util.Locale;

import static com.hss01248.notifyutil.NotifyUtil.context;

public class LanguageActivity extends AppCompatActivity {

    LinearLayout chinise,uighur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        setTitle(getString(R.string.langugae));
        chinise = (LinearLayout)findViewById(R.id.language_chinise);
        uighur = (LinearLayout)findViewById(R.id.language_uighur);
        chinise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("LANGUAGE","CHINISE");
                Resources resources = getResources();//获得res资源对象
                Configuration config = resources.getConfiguration();//获得设置对象
                DisplayMetrics dm = resources .getDisplayMetrics();//获得屏幕参数：主要是分辨率，像素等。
                config.locale = Locale.SIMPLIFIED_CHINESE; //简体中文
                resources.updateConfiguration(config, dm);
                Toast.makeText(LanguageActivity.this,"语言已设置为中文",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LanguageActivity.this, MyActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        uighur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("LANGUAGE","UIGHUR");
                //   ug_ [Uyghur (Arabic)]
                Toast.makeText(LanguageActivity.this,"ئۇيغۇرچە تىل بولىقى ئېچىلدى",Toast.LENGTH_SHORT).show();
                Resources resources = getResources();//获得res资源对象
                Configuration config = resources.getConfiguration();//获得设置对象
                DisplayMetrics dm = resources .getDisplayMetrics();//获得屏幕参数：主要是分辨率，像素等。
                Locale locale = new Locale("ug","CN");
                config.locale = locale;
                resources.updateConfiguration(config, dm);
                Intent intent = new Intent(LanguageActivity.this, MyActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });
    }
}
