package com.missu.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.missu.Bean.Users;
import com.missu.R;

public class SeachFriendActivity extends AppCompatActivity {

    public static final String SEARCHUSERID = "searchuser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach_friend);
        setTitle("添加朋友");
        final EditText serach_id = (EditText)findViewById(R.id.et_searchfriend);
        Button searchFriend = (Button)findViewById(R.id.btn_search_friend);
        searchFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (serach_id.getText().toString().trim().equals("")){
                    Toast.makeText(SeachFriendActivity.this,"用户名不能为空白", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(SeachFriendActivity.this,SearchResultActivity.class);
                intent.putExtra("account",serach_id.getText().toString().trim());
                startActivity(intent);
                finish();
            }
        });
    }
}
