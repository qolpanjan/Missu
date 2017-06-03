package com.missu.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.missu.Activitys.ImApp;
import com.missu.Bean.MessageListBean;
import com.missu.Bean.QQMessage;
import com.missu.R;

import java.util.List;

/**
 * 聊天消息的适配器
 * 
 * @author ZHY
 * 
 */
public class ChatMessageAdapter extends ArrayAdapter<MessageListBean> {
	ImApp app;
    Context context;

	public ChatMessageAdapter(Context context, List<MessageListBean> objects) {
		super(context, 0, objects);
        this.context = context;
		Activity activity = (Activity) context;
		app = (ImApp) activity.getApplication();

	}

	/**
	 * 根据集合中的position位置，返回不同的值，代表不同的布局 0代表自己发送的消息 1代表接收到的消息
	 * 
	 */
	@Override
	public int getItemViewType(int position) {// 这个方法是给getView用得
		MessageListBean msg = getItem(position);
		// 消息来自谁，如果消息来自我自己，说明是我发送的
		if (msg.getType().equals("to")) {
			// 我自己的消息，发送
            //to 自己发送的
            //from 收到的
			return 0;
		} else {
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

	class ViewHolder {
		TextView time;
		TextView content;
		ImageView head;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		int type = getItemViewType(position);
		if (0 == type) {
			// 自己发送发送的布局
			ViewHolder holder;
			if (convertView == null) {
				convertView = View.inflate(getContext(), R.layout.item_chat_send, null);
				holder = new ViewHolder();
				holder.time = (TextView) convertView.findViewById(R.id.time);
				holder.content = (TextView) convertView.findViewById(R.id.content);
				holder.head = (ImageView) convertView.findViewById(R.id.head);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			// 设置值
			MessageListBean msg = getItem(position);
			holder.time.setText(msg.getTime());
			Glide.with(context).load(app.getAvater()).placeholder(R.mipmap.icon).fitCenter().animate(R.anim.item_alpha_in).into(holder.head);
			//holder.head.setImageResource(app.getAvater());
			holder.content.setText(msg.getContent());
			return convertView;

		} else {
			// 接收的布局
			ViewHolder holder;
			if (convertView == null) {
				convertView = View.inflate(getContext(), R.layout.item_chat_receive, null);
				holder = new ViewHolder();
				holder.time = (TextView) convertView.findViewById(R.id.time);
				holder.content = (TextView) convertView
						.findViewById(R.id.content);
				holder.head = (ImageView) convertView.findViewById(R.id.head);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			// 设置值
			MessageListBean msg = getItem(position);
			//holder.head.setImageResource(msg.fromAvatar);
            Glide.with(context).load(msg.getOther_avater()).placeholder(R.mipmap.icon).fitCenter().animate(R.anim.item_alpha_in).into(holder.head);
			holder.time.setText(msg.getTime());
			holder.content.setText(msg.getContent());

			return convertView;
		}

	}
}
