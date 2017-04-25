package com.missu.Utils;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.missu.Activitys.MessageListActivity;
import com.missu.Adapter.MyApplication;
import com.missu.Bean.MessageBean;
import com.missu.Bean.MessageType;
import com.missu.R;

/**
 * Created by alimj on 2017/4/24.
 */

public class MyService extends Service {


    public static final String TAG = "MyService";
    NetConnection conn;

    private MyBinder myBinder = new MyBinder();
    NetConnection.OnMessageListener listener;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"OnCreate");
        Intent intent = new Intent();
        intent.setClass(getBaseContext(),MessageListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),0,intent,0);
        Notification notification = new Notification.Builder(getBaseContext())
                .setAutoCancel(true)
                .setContentTitle("通知标题")
                .setContentText("这是通知内容了")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.icon)
                .build();
                //.setWhen(System.currentTimeMillis())

        //notification.setLatestEventInfo(this, "这是通知的标题", "这是通知的内容",pendingIntent);
        startForeground(1, notification);



        try {
            conn = new NetConnection(MyApplication.IP, 8090);// Socket
            MyApplication app = (MyApplication) getApplication();
            if (listener!=null){
                app.getMyConn().addOnMessageListener(listener);
                Log.e("ADD SUCCE","HERE  ");
            }

            // 保存一个长连接
            app.setMyConn(conn);
            // 保存好友列表的json串
            conn.connect();// 建立连接
            // 建立连接之后，将监听器添加到连接里面


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG,"OnStartCommand");
        listener = new NetConnection.OnMessageListener() {

            @Override
            public void onReveive(final MessageBean msg) {
                ThreadUtils.runInUiThread(new Runnable() {

                    public void run() {

                        // 服务器返回回来的消息
                        System.out.println(msg.getContent());
                        if (MessageType.MSG_TYPE_CHAT_P2P.equals(msg.getType())) {
                            //messages.add(msg);// 把消息加到消息集合中，这是最新的消息
                            Intent intent = new Intent();
                            intent.setClass(getBaseContext(),MessageListActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("msg", msg);
                            intent.putExtras(bundle);

                            PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),0,intent,0);
                            Notification notification = new Notification.Builder(getBaseContext())
                                    .setAutoCancel(false)
                                    .setContentTitle(msg.getFrom())
                                    .setContentText(msg.getContent())
                                    .setContentIntent(pendingIntent)
                                    .setSmallIcon(R.mipmap.icon)
                                    .build();
                        }

                    }
                });
            }

        };
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
