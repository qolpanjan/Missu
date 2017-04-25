package com.missu.Utils;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;


import com.missu.Activitys.MainActivity;
import com.missu.Activitys.MessageListActivity;
import com.missu.Adapter.MyApplication;
import com.missu.Bean.MessageBean;
import com.missu.Bean.MessageType;
import com.missu.R;

import java.io.IOException;

/**
 * Created by alimj on 2017/4/24.
 */

public class SendMsgService extends Service {

    NetConnection conn;
    private MyBinder myBinder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent();
        intent.setClass(getBaseContext(),MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),0,intent,0);
        Notification notification = new Notification.Builder(getBaseContext())
                .setAutoCancel(false)
                .setContentTitle("正在运行")
                .setContentText("MissU正在后台运行")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.icon)
                //.setWhen(System.currentTimeMillis())
                .build();
        //notification.setLatestEventInfo(this, "这是通知的标题", "这是通知的内容",pendingIntent);
        startForeground(1, notification);


        try {
            conn = new NetConnection(MyApplication.IP, 8090);// Socket
            MyApplication app = (MyApplication) getApplication();
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
         NetConnection.OnMessageListener listener = new NetConnection.OnMessageListener() {

            @Override
            public void onReveive(MessageBean msg) {

            }
        };
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }


    public class MyBinder extends Binder{

        public void SendMsg(){
             NetConnection.OnMessageListener listener = new NetConnection.OnMessageListener() {

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
                                         //.setWhen(System.currentTimeMillis())
                                         .build();

                                 // 刷新消息
                                 //if (adapter != null) {
                                     //adapter.notifyDataSetChanged();
                                 //}
                                 // 展示到最新发送的消息出
                                // if (messages.size() > 0) {
                                     //listView.setSelection(messages.size() - 1);
                                // }

                             }

                         }
                     });
                 }

            };
        }

    }
}
