package com.missu.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.missu.Fragment.FriendListFragment;
import com.missu.R;

public class FriendDetailActivity extends AppCompatActivity {

    ImageView friend_profile;
    TextView friend_user_id;
    TextView friend_user_ninkname;
    Button send_message_btn;
    Button delete_friend_btn;
    public static final String USERDETAILID = "userId";
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);
        Intent intent = getIntent();
        userId = intent.getStringExtra(FriendListFragment.USERID);
        
        intView();
        
    }

    private void intView() {
        friend_profile = (ImageView)findViewById(R.id.img_friend_detail_profile);
        friend_user_id = (TextView)findViewById(R.id.tv_friend_detail_userid);
        friend_user_ninkname = (TextView)findViewById(R.id.tv_friend_detail_nickname);
        send_message_btn = (Button)findViewById(R.id.btn_send_message);
        delete_friend_btn = (Button)findViewById(R.id.btn_delete_friend);

        send_message_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(FriendDetailActivity.this,MessageListActivity.class);
                inten.putExtra(USERDETAILID,userId);
                startActivity(inten);
            }
        });
    }
}
