package com.missu.Activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.missu.Adapter.MyApplication;
import com.missu.Bean.ContactInfoList;
import com.missu.Bean.Friends;
import com.missu.Bean.MessageBean;
import com.missu.Bean.MessageType;
import com.missu.R;
import com.missu.Utils.NetConnection;
import com.missu.Utils.ThreadUtils;

import java.util.ArrayList;
import java.util.List;

public class LoadingActicity extends AppCompatActivity {

    ProgressDialog progressDialog;
    public static String USERNAME = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_acticity);
        Log.e("ACTIVITY","LODING");
        Intent intent = getIntent();
        USERNAME = intent.getStringExtra("account");
        Log.e("USErNAME", USERNAME);
        progressDialog = new ProgressDialog(LoadingActicity.this);
        progressDialog.setMessage("正在载入···");
        progressDialog.setCancelable(false);
        progressDialog.show();
        /**
         *  在线程中进行网络请求
         *  下载用户信息和通讯录
         */
        ThreadUtils.runInSubThread(new Runnable() {
            @Override
            public void run() {

                try {

                    MyApplication app = (MyApplication) getApplication();
                    NetConnection conn = app.getMyConn();
                    //conn = new NetConnection("10.22.131.23", 8090);// Socket
                    conn.connect();// 建立连接
                    // 建立连接之后，将监听器添加到连接里面
                    conn.addOnMessageListener(getUserlistener);
                    //conn.addOnMessageListener(getBuddyListener);

                    MessageBean getUserMsg = new MessageBean();
                    getUserMsg.setType(MessageType.MSG_TYPE_GETUSER);
                    getUserMsg.setContent(USERNAME);
                    conn.sendMessage(getUserMsg);

                   // MessageBean getBudyyList = new MessageBean();
                    //getBudyyList.setType(MessageType.MSG_TYPE_BUDDY_LIST);
                    //getBudyyList.setContent(USERNAME);
                   // conn.sendMessage(getBudyyList);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(LoadingActicity.this, "加载失败", Toast.LENGTH_SHORT).show();
                }


            }
        });



    }


    private NetConnection.OnMessageListener getUserlistener = new NetConnection.OnMessageListener() {

        @Override
        public void onReveive(final MessageBean msg) {

            ThreadUtils.runInSubThread(new Runnable() {
                @Override
                public void run() {

                    while (true){
                        if (MessageType.MSG_TYPE_GETUSER_SUCCESS.equals(msg.getType())) {
                            MyApplication app = (MyApplication) getApplication();
                            String[] params = msg.getContent().split("#");
                            String account = params[0];
                            String pwd = params[1];
                            String nick = params[2];
                            String avater = params[3];
                            String sex = params[4];

                            app.setMyAccount(account);
                            app.setPassword(pwd);
                            app.setNickName(nick);
                            app.setMyAvater(avater);
                            app.setSex(sex);

                            progressDialog.dismiss();
                            Intent intent = new Intent();
                            intent.setClass(LoadingActicity.this, MainActivity.class);
                            intent.putExtra("account", USERNAME);
                            startActivity(intent);
                            finish();


                            break;
                        }
                    }
                }
            });

        }
    };


//    private NetConnection.OnMessageListener getBuddyListener = new NetConnection.OnMessageListener() {
//
//        @Override
//        public void onReveive(final MessageBean msg) {
//            ThreadUtils.runInSubThread(new Runnable() {
//                @Override
//                public void run() {
//                    if (MessageType.MSG_TYPE_GET_BUDDY_SUCCES.equals(msg.getType())){
//                        String content = msg.getContent();
//                        Log.e("GETBUDDY",content);
//                        List<Friends> buddyList = new ArrayList<Friends>();
//                        Gson gson = new Gson();
//                        ContactInfoList newList = gson.fromJson(content, ContactInfoList.class);// 新上线好友的集合
//                        MyApplication app = (MyApplication) getApplication();
//                        app.setList(newList);
//                        friendloading = true;
//
//
//                    }
//
//                }
//            });
//
//
//
//
//        }
//    };
}
