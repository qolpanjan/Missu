package com.missu.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.anye.greendao.gen.DaoSession;
import com.anye.greendao.gen.UsersDao;
import com.google.gson.Gson;
import com.missu.Activitys.FriendDetailActivity;
import com.missu.Activitys.MainActivity;
import com.missu.Adapter.FriendAdapter;
import com.missu.Adapter.MyApplication;
import com.missu.Bean.ContactInfoList;
import com.missu.Bean.Friends;
import com.missu.Bean.MessageBean;
import com.missu.Bean.MessageType;
import com.missu.Bean.Users;
import com.missu.Bean.friend;
import com.missu.R;
import com.missu.Utils.NetConnection;
import com.missu.Utils.ThreadUtils;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.badgeview.QBadgeView;

/**
 * Created by alimj on 2017/3/8.
 */

public class FriendListFragment extends Fragment{

    ListView friendListView;
    ImageView img_investment;
    List<Friends> FriendList;
    FriendAdapter adapter;
    public static final String USERID = "userid";
    DaoSession daoSession;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        daoSession = MyApplication.getInstances().getDaoSession();
        FriendList = getFriend();
        adapter = new FriendAdapter(context,R.layout.friendlist_item,FriendList);

    }

    private List<Friends> getFriend() {
        List<Friends> friends = new ArrayList<>();

        try{
            friends = MyApplication.getInstances().getDaoSession().getFriendsDao().loadAll();
       }catch (Exception e){
           e.printStackTrace();
       }

        return friends;
        /**
        for(int i=0;i<5;i++) {
            friend friend1 = new friend();
            Resources res = getResources();
            //Bitmap bmp = BitmapFactory.decodeResource(res, R.mipmap.icon);
            friend1.setFriend_nickname("超哥");
            String bmp = "R.mipmap.icon";
            friend1.setFriend_profile(bmp);
            daoSession.getFriendDao().insert(friend1);
        }
        */


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.friendlist_fragment,container,false);
        LinearLayout question_item = (LinearLayout) view.findViewById(R.id.lo_friendlist_question);
        question_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击申请栏
            }
        });
        LinearLayout group_chat = (LinearLayout)view.findViewById(R.id.lo_friendlist_groupchat);
        group_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击群聊栏

            }
        });

        img_investment = (ImageView) view.findViewById(R.id.img_investment);
        //通知与申请的红点


        new QBadgeView(getContext()).bindTarget(img_investment).setBadgeNumber(MainActivity.UNREADMESSAGE).setGravityOffset(-3,true);



        friendListView = (ListView) view.findViewById(R.id.lv_friendlist);

        friendListView.setAdapter(adapter);
        friendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Friends mfriend =FriendList.get(position);
                Intent intent = new Intent(getContext(), FriendDetailActivity.class);
                intent.putExtra(USERID,mfriend.getAccount());
                startActivity(intent);
            }
        });
        return view;
    }
}
