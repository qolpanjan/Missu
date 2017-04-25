package com.missu.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.missu.Activitys.MessageListActivity;
import com.missu.Bean.Message;
import com.missu.Bean.MessageBean;
import com.missu.R;

import java.util.List;

/**
 * Created by alimj on 2017/3/9.
 */

public class MessageListAdapter extends ArrayAdapter<MessageBean> {
    private int resourceId;
    private Bitmap bitmap;
    MyApplication app;
    public MessageListAdapter(Context context, @LayoutRes int resource, List<MessageBean> objects) {
        super(context, resource, objects);
        Activity activity = (Activity) context;
        app = (MyApplication) activity.getApplication();
        resourceId = resource;
    }


    /**
     * 根据集合中的position位置，返回不同的值，代表不同的布局 0代表自己发送的消息 1代表接收到的消息
     *
     */
    @Override
    public int getItemViewType(int position) {// 这个方法是给getView用得
        MessageBean msg = getItem(position);
        // 消息来自谁，如果消息来自我自己，说明是我发送的
        if (msg.getFrom() == app.getMyAccount()) {
            // 我自己的消息，发送
            return 0;
        } else {
            //接受的消息
            return 1;
        }
    }

    /**
     * 两种布局
     */
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MessageBean msg = getItem(position);
        int type = getItemViewType(position);
        View view;
        ViewHoldr viewHoldr;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHoldr = new ViewHoldr();
            viewHoldr.leftLayout = (LinearLayout) view.findViewById(R.id.lo_left_message);
            viewHoldr.rightLayout = (RelativeLayout) view.findViewById(R.id.lo_right_message);
            viewHoldr.receivedMsg = (TextView) view.findViewById(R.id.tv_message_received);
            viewHoldr.sendMsg = (TextView) view.findViewById(R.id.tv_message_send);
            viewHoldr.receivedImg = (ImageView) view.findViewById(R.id.img_profile_you);
            viewHoldr.sendImg = (ImageView)view.findViewById(R.id.img_profile_me);
            viewHoldr.time = (TextView)view.findViewById(R.id.time);
            view.setTag(viewHoldr);
        }else {
            view = convertView;
            viewHoldr = (ViewHoldr) view.getTag();
        }



        if (0 == type){
            //接受的消息
            viewHoldr.leftLayout.setVisibility(View.VISIBLE);
            viewHoldr.rightLayout.setVisibility(View.GONE);
            viewHoldr.receivedMsg.setText(msg.getContent());
            String imgUrl = msg.getFromAvater();

            Glide.with(getContext()).load(imgUrl).into(viewHoldr.receivedImg);
            viewHoldr.time.setText(msg.getSendTime());
            //viewHoldr.receivedImg.setImageBitmap(msg.getMessg_from_profile());
        }else {
                //发送的消息
                viewHoldr.time.setText(msg.getSendTime());
                viewHoldr.rightLayout.setVisibility(View.VISIBLE);
                viewHoldr.leftLayout.setVisibility(View.GONE);
                viewHoldr.sendMsg.setText(msg.getContent());
                //Log.e("ADAPTER",viewHoldr.sendMsg.getText().toString());
                viewHoldr.sendImg.setImageBitmap(MessageListActivity.bmp1);

                Glide.with(getContext()).load(msg.getFromAvater()).placeholder(R.mipmap.icon).into(viewHoldr.receivedImg);
        }
        return view;
    }



    class ViewHoldr{
        LinearLayout leftLayout;
        RelativeLayout rightLayout;

        TextView receivedMsg;
        TextView sendMsg;
        TextView time;
        ImageView receivedImg;
        ImageView sendImg;
    }
}
