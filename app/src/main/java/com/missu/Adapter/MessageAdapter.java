package com.missu.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.missu.Bean.Message;
import com.missu.R;

import java.util.List;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * Created by alimj on 2017/3/8.
 */

public class MessageAdapter extends ArrayAdapter<Message> {

    private Context context;
    private int resourceId;

    public MessageAdapter(Context context,int textViewResourceID,List<Message> objects){
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

        viewHolder.tvChatUsername.setText(message.getMessg_from_username());
        viewHolder.tvLastChatContent.setText(message.getMessg_content());
        viewHolder.tvLastChattime.setText(message.getMessg_time());
        viewHolder.imageChatProfile.setImageBitmap(message.getMessg_from_profile());

        /**
         * 设置显示消息数目的红点
         */
        viewHolder.badge.setBadgeNumber(message.getMessg_state());
        viewHolder.badge.setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
            @Override
            public void onDragStateChanged(int dragState, Badge badge, View targetView) {
                if (dragState == STATE_SUCCEED){
                    Toast.makeText(getContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
                    message.setMessg_state(0);
                }
            }
        });

        return view;
    }

    class ViewHolder{
        ImageView imageChatProfile;
        TextView tvChatUsername;
        TextView tvLastChatContent;
        TextView tvLastChattime;
        Badge badge;
    }
}
