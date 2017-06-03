package com.missu.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.missu.R;
import com.missu.Util.MyTime;


public class SettingActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    LinearLayout language_setting ,inform_setting;
    SwitchCompat infrom,sound,vibrate,night;
    ImApp app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle(getString(R.string.setting));
        app = (ImApp)getApplication();
        Button signOut = (Button)findViewById(R.id.btn_setting_signout);
        language_setting = (LinearLayout)findViewById(R.id.language_setting);
        inform_setting = (LinearLayout)findViewById(R.id.infrom_setting);
        infrom = (SwitchCompat) findViewById(R.id.new_message_inform);
        infrom.setOnCheckedChangeListener(this);
        sound = (SwitchCompat) findViewById(R.id.new_message_sound);
        sound.setOnCheckedChangeListener(this);
        vibrate = (SwitchCompat) findViewById(R.id.new_message_vibrate);
        vibrate.setOnCheckedChangeListener(this);
        night = (SwitchCompat) findViewById(R.id.new_message_night_inform);
        night.setOnCheckedChangeListener(this);




        language_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(SettingActivity.this,LanguageActivity.class);
                    startActivity(intent);
            }
        });
        inform_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("login",MODE_PRIVATE).edit();
                editor.clear();
                editor.commit();
                finish();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.new_message_inform:
                if (isChecked){
                    app.setInfrom_new_Message(true);
                }else{
                    app.setInfrom_new_Message(false);
                }
                break;
            case R.id.new_message_sound:
                if (isChecked){
                    app.setInfrom_sound(true);
                }else{
                    app.setInfrom_sound(false);
                }
                break;
            case R.id.new_message_vibrate:
                if (isChecked){
                    Toast.makeText(this,"开了",Toast.LENGTH_SHORT).show();
                    app.setInform_vibrate(true);
                }else{
                    Toast.makeText(this,"关了",Toast.LENGTH_SHORT).show();
                    app.setInform_vibrate(false);
                }
                break;
            case R.id.new_message_night_inform:
                if (isChecked){
                    Log.e("TIME",MyTime.geTime());
                    long time=System.currentTimeMillis();
                    final Calendar mCalendar= Calendar.getInstance();
                    mCalendar.setTimeInMillis(time);
                    int mHour=mCalendar.get(Calendar.HOUR);
                    if (mHour>21){
                        app.setInfrom_new_Message(false);
                    }
                    Log.e("TIME",MyTime.geTime()+"+++"+mHour);
                }else{
                    app.setInfrom_new_Message(true);
                }
                break;

        }
    }
}
