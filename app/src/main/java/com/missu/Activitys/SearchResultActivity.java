package com.missu.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.missu.R;

public class SearchResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);
        ImageView searchAvater = (ImageView)findViewById(R.id.img_friend_detail_profile);
        TextView searchNickname = (TextView)findViewById(R.id.tv_friend_detail_nickname);
        TextView searchUserId = (TextView)findViewById(R.id.tv_friend_detail_userid);
        TextView searchUserSex = (TextView)findViewById(R.id.tv_friend_detail_sex);
        Button searchAddFriend = (Button)findViewById(R.id.btn_send_message);
        searchAddFriend.setText("添加为朋友");
        Button searchNull = (Button)findViewById(R.id.btn_delete_friend);
        searchNull.setVisibility(View.GONE);
    }
}
