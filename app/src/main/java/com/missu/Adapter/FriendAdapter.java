package com.missu.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.missu.Bean.Users;
import com.missu.Bean.friend;
import com.missu.R;

import java.util.List;

/**
 * Created by alimj on 2017/3/8.
 */

public class FriendAdapter extends ArrayAdapter<friend> {

    private int ResourceID;

    public FriendAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<friend> objects) {
        super(context, resource, objects);
        ResourceID = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        friend Friend = getItem(position);
        View view;
        Viewholder viewholder;
        if (convertView==null){
            view = LayoutInflater.from(getContext()).inflate(ResourceID,null);
            viewholder = new Viewholder();
            viewholder.friend_profile = (ImageView) view.findViewById(R.id.img_friendlist_profile);
            viewholder.friend_nickname = (TextView)view.findViewById(R.id.tv_friendlist_nickname);
            view.setTag(viewholder);
        }else {
            view = convertView;
            viewholder = (Viewholder) view.getTag();
        }

        viewholder.friend_nickname.setText(Friend.getFriend_nickname());

        String imgUrl = Friend.getFriend_profile();
        Glide.with(getContext()).load(imgUrl).into(viewholder.friend_profile);

        //viewholder.friend_profile.setImageBitmap(Friend.getUser_profile());
        return view;
    }

    class Viewholder{
        TextView friend_nickname;
        ImageView friend_profile;
}

}
