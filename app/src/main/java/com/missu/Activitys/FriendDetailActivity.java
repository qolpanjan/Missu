package com.missu.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.missu.Bean.DaoSession;
import com.missu.Bean.Message;
import com.missu.Bean.QQMessage;
import com.missu.Bean.QQMessageType;
import com.missu.R;
import com.missu.Util.MyTime;
import com.missu.Util.QQConnection;
import com.missu.Util.ThreadUtils;

import java.io.IOException;

public class FriendDetailActivity extends AppCompatActivity {

    ImageView friend_profile;
    TextView friend_user_id;
    TextView friend_user_ninkname;
    TextView friend_sex,search_no_result;

    Button send_message_btn;
    Button delete_friend_btn;
    String f_account,f_nick,f_avater,f_sex = "true";
    String asked= null;
    ImApp app;
    QQConnection conn;
    ProgressBar progressBar;
    private DaoSession daoSession;
    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);
        app = (ImApp) getApplication();
        daoSession = app.getDaoSession();
        conn = app.getMyConn();
        final Intent intent =this.getIntent();
        f_account = intent.getStringExtra("account");
        f_avater = intent.getStringExtra("avater");
        f_nick = intent.getStringExtra("nick");
        f_sex = intent.getStringExtra("sex");
        asked = intent.getStringExtra("asked");
        progressBar = (ProgressBar)findViewById(R.id.search_progress);
        progressBar.setVisibility(View.GONE);
        //DaoSession daoSession = MyApplication.getInstances().getDaoSession();
        //Friends friends =daoSession.getFriendsDao().queryBuilder().where(FriendsDao.Properties.Account.eq(userId)).unique();
        search_no_result = (TextView)findViewById(R.id.tv_search_no_result);
        search_no_result.setVisibility(View.GONE);

        friend_profile = (ImageView)findViewById(R.id.img_friend_detail_profile);

        //Glide.with(getApplicationContext()).load(friends.getAvatar()).placeholder(R.mipmap.icon).fitCenter().into(friend_profile);

        friend_user_id = (TextView)findViewById(R.id.tv_friend_detail_userid);
        friend_user_id.setText(f_account);

        friend_user_ninkname = (TextView)findViewById(R.id.tv_friend_detail_nickname);
        friend_user_ninkname.setText(f_nick);

        friend_sex = (TextView)findViewById(R.id.tv_friend_detail_sex);
        if (f_sex.equals("true")){
            friend_sex.setText("男");
        }else{
            friend_sex.setText("女");
        }



        send_message_btn = (Button)findViewById(R.id.btn_send_message);
        delete_friend_btn = (Button)findViewById(R.id.btn_delete_friend);

        if (asked!=null){
            send_message_btn.setText("通过验证");
            delete_friend_btn.setText("拒绝");

            delete_friend_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    QQMessage message = new QQMessage();
                    message.type = QQMessageType.MSG_TYPE_FAILURE;
                    message.content = app.getMyAccount()+"#"+f_account;
                    try {
                        conn.sendMessage(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        send_message_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (asked==null){
                    Intent inten = new Intent(FriendDetailActivity.this,ChatActivity.class);
                    inten.putExtra("account",f_account);
                    inten.putExtra("nick",f_nick);
                    inten.putExtra("avater",f_avater);
                    startActivity(inten);

                }else{


                    ThreadUtils.runInSubThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                QQMessage message = new QQMessage();
                                message.type = QQMessageType.MSG_TYPE_AGREE_ADD_FRIEND;
                                message.content = app.getMyAccount()+"#"+f_account;
                                conn.sendMessage(message);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });


                    Intent intent1 = new Intent(FriendDetailActivity.this,MyActivity.class);
                    Intent inten = new Intent("com.missu.ADD_SUCCES");
                    inten.putExtra("account",f_account);
                    inten.putExtra("nick",f_nick);
                    inten.putExtra("avater",f_avater);
                    inten.putExtra("sex",f_sex);

                    Message message1 = new Message(null,f_account,f_avater, MyTime.geTime(),"你好，我们成为好友了，现在可以开始聊天了！",f_nick);
                    daoSession.getMessageDao().insert(message1);
                    Intent intent2 = new Intent("com.missu.new_chat.ADD");

                    localBroadcastManager = LocalBroadcastManager.getInstance(FriendDetailActivity.this);
                    localBroadcastManager.sendBroadcast(intent2);
                    localBroadcastManager.sendBroadcast(inten);
                    startActivity(intent1);
                    finish();
                }

            }
        });

        
    }


}
