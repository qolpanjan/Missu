package com.missu.Fragment;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hss01248.notifyutil.NotifyUtil;
import com.missu.Activitys.Add_Friend_Ask_Activity;
import com.missu.Activitys.ChatActivity;
import com.missu.Activitys.FriendDetailActivity;
import com.missu.Activitys.ImApp;
import com.missu.Activitys.MyActivity;
import com.missu.Adapter.ContactInfoAdapter;
import com.missu.Bean.ContactInfo;
import com.missu.Bean.ContactInfoDao;
import com.missu.Bean.ContactInfoList;
import com.missu.Bean.QQMessage;
import com.missu.Bean.QQMessageType;
import com.missu.R;
import com.missu.Util.QQConnection;
import com.missu.Util.ThreadUtils;
import com.missu.View.ChangeColorAndIcon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;


/**
 * Created by alimj on 2017/3/8.
 */

public class FriendListFragment extends Fragment{

    ListView friendListView;
    ImageView img_investment;
    private static List<ContactInfo> infos = new ArrayList<ContactInfo>();
    IntentFilter intentFilter;;
    private ImApp app;
    Context context;
    int asked = 0;
    private ContactInfoAdapter adapter;
    String asked_account,asked_nick,asked_avater,asked_sex ;
    private LocalBroadcastManager localBroadcastManager;
    Badge dotView  = null;
    Badge mainDot = null;

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        this.context = context;
        AddSuccesReceicer receicer = new AddSuccesReceicer();
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.missu.ADD_SUCCES");
        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        localBroadcastManager.registerReceiver(receicer,intentFilter);

    }

    /**
     * 好友添加请求和系统通知
     */
    private QQConnection.OnMessageListener listener = new QQConnection.OnMessageListener() {

        public void onReveive(final QQMessage msg) {

            ThreadUtils.runInUiThread(new Runnable() {

                public void run() {
                    //
                    if (QQMessageType.MSG_TYPE_ADD_ASK.equals(msg.type)) {
                        String[] newBuddyListJson = msg.content.split("#");// ask for adding friend
                        asked_account = newBuddyListJson[0];
                        asked_nick = newBuddyListJson[1];
                        asked_avater = newBuddyListJson[2];
                        asked_sex = newBuddyListJson[3];

                        Intent intent =  new Intent(getContext(),Add_Friend_Ask_Activity.class);
                        intent.setClass(getContext(),ChatActivity.class);
                        intent.putExtra("account",asked_account);
                        intent.putExtra("nick",asked_nick);
                        intent.putExtra("avater",asked_avater);
                        intent.putExtra("sex",asked_sex);



                        if (app.isInfrom_new_Message()) {
                            PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, FLAG_UPDATE_CURRENT);
                            NotifyUtil.buildMailBox(104, R.mipmap.logo, msg.from)
                                    .addMsg(msg.content)
                                    .setContentIntent(pendingIntent)
                                    .setTicker(getString(R.string.new_friend))
                                    .show();
                        }

                        asked++;
                        dotView = new QBadgeView(getContext()).bindTarget(img_investment).setBadgeNumber(asked).setGravityOffset(-3,true);
                        if (MyActivity.two!=null){
                            mainDot = new QBadgeView(getContext()).bindTarget(MyActivity.two).setBadgeNumber(asked).setGravityOffset(20,0,true);
                        }
                    }

                }
            });
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.friendlist_fragment,container,false);
        SearchView searchView= (SearchView)view.findViewById(R.id.searchview);

        LinearLayout question_item = (LinearLayout) view.findViewById(R.id.lo_friendlist_question);
        img_investment = (ImageView) view.findViewById(R.id.img_investment);
        question_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asked=0;
                if (dotView!=null){
                    dotView.hide(true);
                }
                dotView = new QBadgeView(getContext()).bindTarget(img_investment).setBadgeNumber(0).setGravityOffset(-3,true);
                if (MyActivity.two!=null){
                    mainDot = new QBadgeView(getContext()).bindTarget(MyActivity.two).setBadgeNumber(asked).setGravityOffset(20,0,true);
                    mainDot.hide(true);

                }
                Intent intent = new Intent(getActivity(), Add_Friend_Ask_Activity.class);
                intent.putExtra("account",asked_account);
                intent.putExtra("nick",asked_nick);
                intent.putExtra("avater",asked_avater);
                intent.putExtra("sex",asked_sex);
                startActivity(intent);

            }
        });
        //通知与申请

        //new QBadgeView(getContext()).bindTarget(img_investment).setBadgeNumber(asked).setGravityOffset(-3,true);
        LinearLayout group_chat = (LinearLayout)view.findViewById(R.id.lo_friendlist_groupchat);
        group_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击群聊栏

            }
        });




        friendListView = (ListView) view.findViewById(R.id.lv_friendlist);

        app = (ImApp) getActivity().getApplication();

        //获取长连接，添加监听器
        app.getMyConn().addOnMessageListener(listener);
        String buddyListJson = app.getBuddyListJson();
        Log.e("tag",app.getBuddyListJson());
        Gson gson = new Gson();
        try {
            JSONObject jsonObject =new JSONObject(buddyListJson);
            JSONArray jsonArray =jsonObject.getJSONArray("FriendsList");
            infos.clear();
            for(int i=0;i<jsonArray.length();i++){
                ContactInfo friends =gson.fromJson(jsonArray.get(i).toString(),ContactInfo.class);
                try{
                    app.getDaoSession().getContactInfoDao().insert(friends);
                }catch (Exception e){

                }
                List<ContactInfo> myfriends = app.getDaoSession().getContactInfoDao().queryBuilder().where(ContactInfoDao.Properties.Account.eq(friends.account)).build().list();
                if (myfriends.size()>1){
                    app.getDaoSession().getContactInfoDao().delete(myfriends.get(1));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR","JSON ERRPR");
        }
        //ContactInfoList list = gson.fromJson(buddyListJson, ContactInfoList.class);
       // infos.addAll(list.buddyList);// buddyList是一个集合，把buddyList集合里的东西全部添加进infos
        //Log.e("FRIEND",infos.get(0).account);
        infos = app.getDaoSession().getContactInfoDao().loadAll();
        adapter = new ContactInfoAdapter(context, infos);
        friendListView.setAdapter(adapter);
        friendListView.setTextFilterEnabled(true);


        friendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // 获得当前点击条目的信息.包含账号和昵称
                ContactInfo info = infos.get(position);
                    Intent intent = new Intent(context, FriendDetailActivity.class);
                    // 将账号和个性签名带到下一个activity
                    intent.putExtra("account", info.account);
                    intent.putExtra("nick", info.nick);
                    intent.putExtra("avater",info.avatar);
                    intent.putExtra("sex",info.sex);
                    startActivity(intent);

            }
        });
        Cursor cursor = null;
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getContext(), R.layout.friendlist_item, cursor, new String[] { "tb_name" },new int[] { R.id.tv_friendlist_nickname });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                if (!TextUtils.isEmpty(s)){
                    friendListView.setFilterText(s);
                }else{
                    friendListView.clearTextFilter();
                }
                return false;
            }
        });


        return view;
    }



    public  class AddSuccesReceicer extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context,"加好友成功",Toast.LENGTH_SHORT).show();
            String f_account = intent.getStringExtra("account");
            String f_avater = intent.getStringExtra("avater");
            String f_nick = intent.getStringExtra("nick");
            String f_sex = intent.getStringExtra("sex");

            ContactInfo info = new ContactInfo(null,f_account,f_nick,f_avater,f_sex);

            app.getDaoSession().getContactInfoDao().insert(info);
            List<ContactInfo> myfriends = app.getDaoSession().getContactInfoDao().queryBuilder().where(ContactInfoDao.Properties.Account.eq(info.account)).build().list();
            if (myfriends.size()>1){
                app.getDaoSession().getContactInfoDao().delete(myfriends.get(1));
            }
            if (adapter!=null){
                adapter.notifyDataSetChanged();
            }
        }
    }
}
