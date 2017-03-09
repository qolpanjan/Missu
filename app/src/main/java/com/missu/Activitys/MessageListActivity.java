package com.missu.Activitys;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.missu.Adapter.MessageListAdapter;
import com.missu.Bean.Message;
import com.missu.Fragment.ChatListFragment;
import com.missu.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alimj on 2017/3/9.
 */

public class MessageListActivity extends AppCompatActivity {

    public static final String TYPE_RECEIVED = "0";
    public static final String TYPE_SEND = "1";
    public static Bitmap bmp1;

    private ListView MsgListView;
    private EditText inputMsg;
    private Button msgSendBtn;
    private MessageListAdapter adapter;
    private List<Message> msgList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_list);
        Intent intent = getIntent();
        String chattingfriendname = intent.getStringExtra(ChatListFragment.CHATTINGFRIEND);
        setTitle(chattingfriendname);
        Resources rec= getResources();
        bmp1 = BitmapFactory.decodeResource(rec,R.mipmap.icon);
        initMsgs();
        adapter = new MessageListAdapter(this,R.layout.message_item,msgList);
        inputMsg = (EditText)findViewById(R.id.et_messagelist);
        msgSendBtn = (Button)findViewById(R.id.btn_send_message);
        MsgListView = (ListView)findViewById(R.id.lv_messagelist);
        MsgListView.setAdapter(adapter);

        msgSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputMsg.getText().toString();
                if (!"".equals(content)){
                    Message msg = new Message();
                    msg.setMessg_content(content);
                    msg.setMessg_type(TYPE_SEND);
                    msg.setMessg_from_username("Alimzhan");
                    msgList.add(msg);
                    adapter.notifyDataSetChanged();
                    MsgListView.setSelection(msgList.size());
                    inputMsg.setText("");
                }
            }
        });

    }

    private void initMsgs() {
        Message msg=new Message();
        msg.setMessg_type(TYPE_RECEIVED);
        Resources res = getResources();
        Bitmap bmp2 = BitmapFactory.decodeResource(res,R.mipmap.ic_launcher);
        msg.setMessg_from_profile(bmp2);
        msg.setMessg_content("阿力木江你好，祝你妇女节快乐");
        msg.setMessg_from_username("马化腾");
        msgList.add(msg);
    }
}
