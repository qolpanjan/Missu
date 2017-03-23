package com.missu.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anye.greendao.gen.DaoSession;
import com.anye.greendao.gen.UsersDao;
import com.anye.greendao.gen.friendDao;
import com.missu.Adapter.MyApplication;
import com.missu.Bean.Users;
import com.missu.Bean.friend;
import com.missu.R;

public class SeachFriendActivity extends AppCompatActivity {

    public static final String SEARCHUSERID = "searchuser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach_friend);
        final EditText serach_id = (EditText)findViewById(R.id.et_searchfriend);
        Button searchFriend = (Button)findViewById(R.id.btn_search_friend);
        searchFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (serach_id.getText().toString().trim().equals("")){
                    Toast.makeText(SeachFriendActivity.this,"用户名不能为空白",Toast.LENGTH_SHORT).show();
                    return;
                }
                DaoSession daoSession = MyApplication.getInstances().getDaoSession();
                Users users=daoSession.getUsersDao().queryBuilder().where(UsersDao.Properties.User_name.eq(serach_id.getText().toString().trim())).unique();
                if (users==null){
                    Toast.makeText(SeachFriendActivity.this,"找不到你搜索的用户ID",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getApplicationContext(),SearchResultActivity.class);
                intent.putExtra(SEARCHUSERID,users.getUser_name());
                startActivity(intent);
                finish();
            }
        });
    }
}
