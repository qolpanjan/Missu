package com.missu.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.missu.Bean.QQMessage;
import com.missu.Bean.QQMessageType;
import com.missu.R;
import com.missu.Util.QQConnection;
import com.missu.Util.ThreadUtils;

import java.io.IOException;

public class SearchResultActivity extends AppCompatActivity {

    ProgressBar progressBar;
    ImApp app;
    QQConnection conn;
    TextView searchNickname,searchUserId,searchUserSex,search_no_result;
    ImageView searchAvater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);
        app = (ImApp) getApplication();
        conn = app.getMyConn();



        final Intent intent = getIntent();
        //String id = intent.getStringExtra(SeachFriendActivity.SEARCHUSERID);
        final String account = intent.getStringExtra("account");

        progressBar = (ProgressBar) findViewById(R.id.search_progress);
        progressBar.setVisibility(View.GONE);
        ThreadUtils.runInSubThread(new Runnable() {
            @Override
            public void run() {
                conn.addOnMessageListener(listener);

            }
        });


        final QQMessage message = new QQMessage();
        message.type = QQMessageType.MSG_TYPE_SEARCH_BUDDY;
        message.content = account;

        ThreadUtils.runInSubThread(new Runnable() {
            @Override
            public void run() {
                try {
                    conn.sendMessage(message);
                    Log.e("ACTIVITY","SEARCHRESULT");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });




        searchAvater = (ImageView)findViewById(R.id.img_friend_detail_profile);

        searchNickname = (TextView)findViewById(R.id.tv_friend_detail_nickname);
        //searchNickname.setText(friend.getUser_nickname());

        searchUserId = (TextView)findViewById(R.id.tv_friend_detail_userid);
        //searchUserId.setText(friend.getUser_name());

        searchUserSex = (TextView)findViewById(R.id.tv_friend_detail_sex);
        //searchUserSex.setText(friend.getUser_sex());
        search_no_result = (TextView)findViewById(R.id.tv_search_no_result);
        search_no_result.setVisibility(View.GONE);

        Button searchAddFriend = (Button)findViewById(R.id.btn_send_message);
        searchAddFriend.setText("添加为朋友");
        searchAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SearchResultActivity.this,"已发送申请，请等待好友验证", Toast.LENGTH_SHORT).show();
                ThreadUtils.runInSubThread(new Runnable() {
                    @Override
                    public void run() {
                        QQMessage msg = new QQMessage();
                        msg.type = QQMessageType.MSG_TYPE_ADD_ASK;
                        msg.content = app.getMyAccount()+"#"+account;
                        msg.to = account;
                        try {
                            conn.sendMessage(msg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                Intent intent1 = new Intent(SearchResultActivity.this,MyActivity.class);
                startActivity(intent1);
                finish();
            }
        });

        Button searchNull = (Button)findViewById(R.id.btn_delete_friend);
        searchNull.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        conn.removeOnMessageListener(listener);
    }

    private QQConnection.OnMessageListener listener = new QQConnection.OnMessageListener() {

        @Override
        public void onReveive(final QQMessage msg) {
            System.out.println(msg.toXML());
            //对UI操作，运行在主线程，显示从服务器返回的搜索结果
            ThreadUtils.runInUiThread(new Runnable() {

                public void run() {

                    progressBar.setVisibility(View.GONE);
                    if (QQMessageType.MSG_TYPE_SEARCH_BUDDY.equals(msg.type)){

                        String[] params = msg.content.split("#");
                        String accounts = params[0];
                        String nick = params[1];
                        String avater = params[2];
                        String sex = params[3];
                        searchNickname.setText(nick);
                        searchUserId.setText(accounts);
                        if ("true".equals(sex)){
                            searchUserSex.setText("男");
                        }else{
                            searchUserSex.setText("女");
                        }

                        Glide.with(SearchResultActivity.this).load(avater).fitCenter().placeholder(R.mipmap.icon).into(searchAvater);
                    }else if (QQMessageType.MSG_TYPE_FAILURE.equals(msg.type)){
                        search_no_result.setVisibility(View.VISIBLE);
                    }
                }
            });

        }
    };
}
