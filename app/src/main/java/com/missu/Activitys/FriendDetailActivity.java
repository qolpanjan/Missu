package com.missu.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.anye.greendao.gen.DaoSession;
import com.anye.greendao.gen.FriendsDao;
import com.anye.greendao.gen.friendDao;
import com.bumptech.glide.Glide;
import com.missu.Adapter.MyApplication;
import com.missu.Bean.Friends;
import com.missu.Bean.MessageBean;
import com.missu.Bean.Users;
import com.missu.Bean.friend;
import com.missu.Fragment.FriendListFragment;
import com.missu.R;

public class FriendDetailActivity extends AppCompatActivity {

    ImageView friend_profile;
    TextView friend_user_id;
    TextView friend_user_ninkname;
    TextView friend_sex;
    Button send_message_btn;
    Button delete_friend_btn;
    public static final String USERDETAILID = "userId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);
        Intent intent =this.getIntent();
        final Friends friends = (Friends)intent.getSerializableExtra("friend");
        Log.e("MainActivityName",friends.getAccount()+"");
        //DaoSession daoSession = MyApplication.getInstances().getDaoSession();
        //Friends friends =daoSession.getFriendsDao().queryBuilder().where(FriendsDao.Properties.Account.eq(userId)).unique();

        friend_profile = (ImageView)findViewById(R.id.img_friend_detail_profile);

        Glide.with(getApplicationContext()).load(friends.getAvatar()).placeholder(R.mipmap.icon).fitCenter().into(friend_profile);

        friend_user_id = (TextView)findViewById(R.id.tv_friend_detail_userid);
        friend_user_id.setText(friends.getAccount());

        friend_user_ninkname = (TextView)findViewById(R.id.tv_friend_detail_nickname);
        friend_user_ninkname.setText(friends.getNick());

        friend_sex = (TextView)findViewById(R.id.tv_friend_detail_sex);
        if (friends.getSex().equals("true")){
            friend_sex.setText("男");
        }else{
            friend_sex.setText("女");
        }


        send_message_btn = (Button)findViewById(R.id.btn_send_message);
        delete_friend_btn = (Button)findViewById(R.id.btn_delete_friend);

        send_message_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(FriendDetailActivity.this,MessageListActivity.class);
//                Bundle bundle = new Bundle();
//                MessageBean messageBean = new MessageBean();
//                messageBean.setFrom(friends.getAccount());
//
//                bundle.putSerializable("msg", messageBean);
                inten.putExtra("account",friends.getAccount());
                inten.putExtra("nick",friends.getNick());
                inten.putExtra("avater",friends.getAvatar());
                startActivity(inten);
            }
        });
        
    }


}
