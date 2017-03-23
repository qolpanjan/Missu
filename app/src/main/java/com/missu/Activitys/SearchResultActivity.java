package com.missu.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anye.greendao.gen.DaoSession;
import com.anye.greendao.gen.UsersDao;
import com.anye.greendao.gen.friendDao;
import com.bumptech.glide.Glide;
import com.missu.Adapter.MyApplication;
import com.missu.Bean.Users;
import com.missu.Bean.friend;
import com.missu.R;

public class SearchResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);
        Intent intent = getIntent();
        String id = intent.getStringExtra(SeachFriendActivity.SEARCHUSERID);
        final DaoSession daoSession = MyApplication.getInstances().getDaoSession();
        final Users friend=daoSession.getUsersDao().queryBuilder().where(UsersDao.Properties.User_name.eq(id)).unique();


        ImageView searchAvater = (ImageView)findViewById(R.id.img_friend_detail_profile);
        Glide.with(SearchResultActivity.this).load(friend.getUser_profile()).into(searchAvater);

        TextView searchNickname = (TextView)findViewById(R.id.tv_friend_detail_nickname);
        searchNickname.setText(friend.getUser_nickname());

        TextView searchUserId = (TextView)findViewById(R.id.tv_friend_detail_userid);
        searchUserId.setText(friend.getUser_name());

        TextView searchUserSex = (TextView)findViewById(R.id.tv_friend_detail_sex);
        searchUserSex.setText(friend.getUser_sex());

        Button searchAddFriend = (Button)findViewById(R.id.btn_send_message);
        searchAddFriend.setText("添加为朋友");
        searchAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friend.setUnread_message(friend.getUnread_message()+1);
                daoSession.getUsersDao().update(friend);
                Toast.makeText(SearchResultActivity.this,"已发送申请，请等待好友验证",Toast.LENGTH_SHORT).show();
            }
        });
        Button searchNull = (Button)findViewById(R.id.btn_delete_friend);
        searchNull.setVisibility(View.GONE);
    }
}
