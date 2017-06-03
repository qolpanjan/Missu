package com.missu.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.missu.Adapter.ContactInfoAdapter;
import com.missu.Bean.ContactInfo;
import com.missu.R;

import java.util.ArrayList;
import java.util.List;

public class Add_Friend_Ask_Activity extends AppCompatActivity {

    ListView listView;
    String account =null;
    String nick ="";
    String avater = "";
    String sex = "";
    private List<ContactInfo> infos = new ArrayList<ContactInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__friend__ask_);
        listView = (ListView)findViewById(R.id.lv_asked_friend);
        setTitle(getString(R.string.new_friend_activity));
        Intent intent =getIntent();
        account = intent.getStringExtra("account");
        nick = intent.getStringExtra("nick");
        avater = intent.getStringExtra("avater");
        sex = intent.getStringExtra("sex");


        ContactInfo info = new ContactInfo();
        info.account =account;
        info.avatar = avater;
        info.nick = nick;
        info.sex = sex;
        infos.add(info);


        ContactInfoAdapter adapter = new ContactInfoAdapter(Add_Friend_Ask_Activity.this,infos);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ContactInfo info = infos.get(i);
                Intent intent = new Intent(Add_Friend_Ask_Activity.this, FriendDetailActivity.class);
                intent.putExtra("account", info.account);
                intent.putExtra("nick", info.nick);
                intent.putExtra("avater",info.avatar);
                intent.putExtra("sex",info.sex);
                intent.putExtra("asked","addfriends");
                startActivity(intent);
            }
        });

    }
}
