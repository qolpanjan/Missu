package com.missu.Adapter;

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
import com.missu.R;

import java.util.List;

/**
 * Created by alimj on 2017/3/9.
 */

public class MessageListAdapter extends ArrayAdapter<Message> {
    private int resourceId;
    private Bitmap bitmap;
    public MessageListAdapter(Context context, @LayoutRes int resource, List<Message> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Message msg = getItem(position);
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
            view.setTag(viewHoldr);
        }else {
            view = convertView;
            viewHoldr = (ViewHoldr) view.getTag();
        }

        if (msg.getMessg_type() == MessageListActivity.TYPE_RECEIVED){
            viewHoldr.leftLayout.setVisibility(View.VISIBLE);
            viewHoldr.rightLayout.setVisibility(View.GONE);
            viewHoldr.receivedMsg.setText(msg.getMessg_content());

            String imgUrl = msg.getMessg_from_profile();
            Glide.with(getContext()).load(imgUrl).into(viewHoldr.receivedImg);

            //viewHoldr.receivedImg.setImageBitmap(msg.getMessg_from_profile());
        }else {
            try{
                viewHoldr.rightLayout.setVisibility(View.VISIBLE);
                viewHoldr.leftLayout.setVisibility(View.GONE);
                viewHoldr.sendMsg.setText(msg.getMessg_content() +"\n"+ msg.getTranslate().getUighur());
                Log.e("ADAPTER",viewHoldr.sendMsg.getText().toString());
                viewHoldr.sendImg.setImageBitmap(MessageListActivity.bmp1);

            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return view;
    }

    private void drawableToBitamp(Drawable drawable)
     {
                 BitmapDrawable bd = (BitmapDrawable) drawable;
                 bitmap = bd.getBitmap();
     }

    class ViewHoldr{
        LinearLayout leftLayout;
        RelativeLayout rightLayout;

        TextView receivedMsg;
        TextView sendMsg;

        ImageView receivedImg;
        ImageView sendImg;
    }
}
