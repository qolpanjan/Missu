package com.missu.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                Intent intent = new Intent(getApplicationContext(),SearchResultActivity.class);
                intent.putExtra(SEARCHUSERID,serach_id.getText().toString().trim());
                startActivity(intent);
                finish();
            }
        });
    }
}
