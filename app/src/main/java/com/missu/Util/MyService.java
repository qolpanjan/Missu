package com.missu.Util;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


import com.hss01248.notifyutil.NotifyUtil;
import com.missu.Activitys.ImApp;
import com.missu.Bean.QQMessage;
import com.missu.Bean.QQMessageType;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

/**
 * Created by alimj on 2017/4/24.
 */

public class MyService extends Service {


    public static final String TAG = "MyService";
    ImApp app;
    QQConnection conn;

    private MyBinder myBinder = new MyBinder();
    boolean isRunning = true;


    @Override
    public void onCreate() {
        super.onCreate();
        app = (ImApp) getApplication();
        NotifyUtil.init(getApplicationContext());
        conn = app.getMyConn();

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG,"OnStartCommand");

        Log.e("TAG"," startcommand finish");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"OnDestroy");
    }



    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    public class MyBinder extends Binder {

        public void startDownload(){
            Log.e(TAG,"startDownload");

        }
    }
}
