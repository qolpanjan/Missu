package com.missu.Fragment;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.missu.Activitys.ChatActivity;
import com.missu.Activitys.ImApp;
import com.missu.Activitys.MyActivity;
import com.missu.Bean.DaoSession;
import com.missu.Bean.Message;
import com.missu.Bean.MessageDao;
import com.missu.Bean.MessageListBean;
import com.missu.R;
import com.missu.Util.MyTime;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;


/**
 * Created by alimj on 2017/3/8.
 */

public class ChatListFragment extends Fragment {

    private ListView MessageListView;
    //private List<Message> messageList;
    private MessageAdapter adapter;
    public static final String CHATTINGFRIEND = "chattingfriendname";
    private List<Message> mList = new ArrayList<>();
    private DaoSession daoSession;
    private ImApp app;
    private Context context;
    private LocalBroadcastManager localBroadcastManager;
    IntentFilter intentFilter;;
    Map<String,Integer> badgenumbers = new HashMap<String,Integer>();
    int i=0;
    Badge msgnum;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context =context;
        app = (ImApp) getActivity().getApplication();
        daoSession = app.getDaoSession();
        NewChatReceiver newChatReceiver = new NewChatReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.missu.new_chat.ADD");
        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        localBroadcastManager.registerReceiver(newChatReceiver,intentFilter);

    }

    private List<Message> getMessage() {
        //第一次登录 显示欢迎与

        List<Message> messageList = daoSession.getMessageDao().queryBuilder().orderDesc(MessageDao.Properties.Time).list();
        List<Message> messages1 = daoSession.getMessageDao().queryBuilder().where(MessageDao.Properties.Account.eq("MISSU")).list();
        try{
            if (messages1.get(1)!=null) {
                daoSession.getMessageDao().delete(messages1.get(1));
            }
        }catch(Exception e){

        }
        try{
            int x=0;
            for (int i=0;i<messageList.size();i++){
                for(int j=0;j<messageList.size();i++){
                    if (messageList.get(i).getAccount().equals(messageList.get(j).getAccount())){
                        if (x>1){
                            daoSession.getMessageDao().delete(messageList.get(j));
                            break;
                        }
                        x++;
                    }
                }
            }
        }catch (Exception e){

        }

        return messageList;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_fragment,container,false);
        MessageListView = (ListView) view.findViewById(R.id.lv_chatlist);
        //第一次登录 显示欢迎与
        if (i==0){

            List<Message> messages = daoSession.getMessageDao().queryBuilder().where(MessageDao.Properties.Account.eq("MISSU")).list();

            if (messages!=null) {
                for(int i = 0;i<messages.size();i++){
                    daoSession.getMessageDao().delete(messages.get(i));
                }

            }
            Message message = new Message(null,"MISSU","0", MyTime.geTime(),"欢迎使用Miss U维汉翻译聊天系统！","MISSU团队");
            i++;
            daoSession.getMessageDao().insert(message);

        }
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        int num = 0;
        mList = getMessage();
        if (mList.size()>num&&num!=0){
            msgnum = new QBadgeView(getContext()).bindTarget(MyActivity.one).setBadgeText((mList.size()-num)+"");
        }
        num = mList.size();
        adapter = new MessageAdapter(context, R.layout.chat_item,mList);
        MessageListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        MessageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (msgnum!=null){
                    msgnum = new QBadgeView(getContext()).bindTarget(MyActivity.one).setBadgeNumber(0);
                    msgnum.hide(true);
                }

                Message message = mList.get(position);
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("account",message.getAccount());
                intent.putExtra("nick",message.getNick());
                intent.putExtra("avater",message.getAvater());
                startActivity(intent);
            }
        });
        MessageListView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.add(0,0,0,getString(R.string.delete));
                contextMenu.add(0,1,0,getString(R.string.make_unread));
                contextMenu.add(0,2,0,getString(R.string.make_top));
            }
        });

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){
            case 0://第一项操作
                Message message = mList.get(info.position);
                daoSession.getMessageDao().delete(message);
                mList.remove(info.position);
                adapter.notifyDataSetChanged();
                break;
            case 1:
                msgnum = new QBadgeView(getContext()).bindTarget(MyActivity.one).setBadgeNumber(1);
                msgnum.setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
                    @Override
                    public void onDragStateChanged(int dragState, Badge badge, View targetView) {

                    }
                });
                break;
            case 2:

                break;
            default:
                break;

        }
        return super.onContextItemSelected(item);
    }


    class MessageAdapter extends ArrayAdapter<Message>{
        private Context context;
        private int resourceId;

        public MessageAdapter(Context context, int textViewResourceID, List<Message> objects){
            super(context,textViewResourceID,objects);
            context = context;
            resourceId = textViewResourceID;
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            final Message message = getItem(position);
            View view;
            ViewHolder viewHolder;
            if (convertView == null){
                view = LayoutInflater.from(getContext()).inflate(resourceId,null);
                viewHolder = new ViewHolder();
                viewHolder.imageChatProfile = (ImageView) view.findViewById(R.id.img_chat_profile);
                viewHolder.tvChatUsername = (TextView) view.findViewById(R.id.tv_chat_username);
                viewHolder.tvLastChatContent = (TextView) view.findViewById(R.id.tv_last_chat_content);
                viewHolder.tvLastChattime = (TextView) view.findViewById(R.id.tv__last_chat_time);
                viewHolder.badge = new QBadgeView(getContext()).bindTarget(view.findViewById(R.id.img_chat_profile)).setGravityOffset(-3,true);
                viewHolder.badge.setBadgeTextSize(16,true);
                view.setTag(viewHolder);
            }else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }

            viewHolder.tvChatUsername.setText(message.getAccount());

            for (String key : badgenumbers.keySet()) {
                if (message.getAccount().equals(key)) {
                    viewHolder.badge.setBadgeNumber(badgenumbers.get(key));
                }
            }


            viewHolder.tvLastChatContent.setText(message.getContent());
            viewHolder.tvLastChattime.setText(message.getTime());
            viewHolder.badge.setBadgeNumber(0); //Badge
            //String imgUrl = message.fromAvatar;
            Glide.with(getContext()).load(message.getTime()).placeholder(R.mipmap.icon).into(viewHolder.imageChatProfile);



            return view;
        }



    }
    class ViewHolder{
        ImageView imageChatProfile;
        TextView tvChatUsername;
        TextView tvLastChatContent;
        TextView tvLastChattime;
        Badge badge;
    }

    public class NewChatReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
           // mList.clear();
            //Toast.makeText(getContext(),"新置顶",Toast.LENGTH_SHORT).show();
            String account = intent.getStringExtra("account");
            if (badgenumbers.containsKey(account)){
                int n = badgenumbers.get(account)+1;
                badgenumbers.remove(account);
                badgenumbers.put(account,n);
            }else{
                badgenumbers.put(account,1);
                Log.e("BADGENUMBER",badgenumbers.get(account)+"");
            }
            if (adapter!=null){
                adapter.notifyDataSetChanged();
            }

        }
    }

}
