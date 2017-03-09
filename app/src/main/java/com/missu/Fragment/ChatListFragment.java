package com.missu.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.missu.Activitys.MessageListActivity;
import com.missu.Adapter.MessageAdapter;
import com.missu.Bean.Message;
import com.missu.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alimj on 2017/3/8.
 */

public class ChatListFragment extends Fragment {

    private ListView MessageListView;
    private List<Message> messageList;
    private MessageAdapter adapter;
    public static final String CHATTINGFRIEND = "chattingfriendname";


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        messageList = getMessage();
        adapter = new MessageAdapter(context, R.layout.chat_item,messageList);
    }

    private List<Message> getMessage() {
        List<Message> messageList = new ArrayList<>();
        Message messg1 = new Message();
        Resources res = getResources();
        Bitmap bmp = BitmapFactory.decodeResource(res,R.mipmap.icon);
        messg1.setMessg_from_profile(bmp);
        messg1.setMessg_from_username("Alimzhan");
        messg1.setMessg_content("Hello, Happy World Womans Day!");
        messg1.setMessg_time("下午 12:37");
        messg1.setMessg_type("1");
        messg1.setMessg_state(2);
        messageList.add(messg1);

        Message messg2 = new Message();
        Resources res1 = getResources();
        Bitmap bmp1 = BitmapFactory.decodeResource(res1,R.mipmap.ic_launcher);
        messg2.setMessg_from_profile(bmp1);
        messg2.setMessg_from_username("马化腾");
        messg2.setMessg_content("把世界做的功能美好点啊！不然的话那里的那么多钱给你花，给你赚啊，我都等不及了。。。");
        messg2.setMessg_time("下午 12:42");
        messg2.setMessg_type("1");
        messg2.setMessg_state(1);
        messageList.add(messg2);

        return messageList;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_fragment,container,false);
        MessageListView = (ListView) view.findViewById(R.id.lv_chatlist);
        MessageListView.setAdapter(adapter);
        MessageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Message message = messageList.get(position);
                Intent intent = new Intent(getContext(), MessageListActivity.class);
                intent.putExtra(CHATTINGFRIEND,message.getMessg_from_username());
                message.setMessg_state(0);
                startActivity(intent);
            }
        });
        return view;
    }


}
