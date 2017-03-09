package com.missu.Fragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.missu.Adapter.FriendAdapter;
import com.missu.Bean.friend;
import com.missu.R;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.badgeview.QBadgeView;

/**
 * Created by alimj on 2017/3/8.
 */

public class FriendListFragment extends Fragment{

    ListView friendListView;
    ImageView img_investment;
    List<friend> FriendList;
    FriendAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        FriendList = getFriend();
        adapter = new FriendAdapter(context,R.layout.friendlist_item,FriendList);

    }

    private List<friend> getFriend() {
        List<friend> friends = new ArrayList<>();
        for(int i=0;i<15;i++) {
            friend friend1 = new friend();
            Resources res = getResources();
            Bitmap bmp = BitmapFactory.decodeResource(res, R.mipmap.icon);
            friend1.setFriend_nickname("超哥");
            friend1.setUser_profile(bmp);
            friends.add(friend1);
        }
        return friends;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.friendlist_fragment,container,false);
        img_investment = (ImageView) view.findViewById(R.id.img_investment);
        new QBadgeView(getContext()).bindTarget(img_investment).setBadgeNumber(2).setGravityOffset(-3,true);
        friendListView = (ListView) view.findViewById(R.id.lv_friendlist);

        friendListView.setAdapter(adapter);
        return view;
    }
}
