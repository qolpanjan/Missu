package com.missu.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.missu.Activitys.ImApp;
import com.missu.Bean.QQMessage;
import com.missu.R;

import java.util.List;

/**
 * 聊天消息的适配器
 * 
 * @author ZHY
 * 
 */
public class ChartMessageAdapter extends ArrayAdapter<QQMessage> {
	ImApp app;

	public ChartMessageAdapter(Context context, List<QQMessage> objects) {
		super(context, 0, objects);
		Activity activity = (Activity) context;
		app = (ImApp) activity.getApplication();

	}

	/**
	 * 根据集合中的position位置，返回不同的值，代表不同的布局 0代表自己发送的消息 1代表接收到的消息
	 * 
	 */
	@Override
	public int getItemViewType(int position) {// 这个方法是给getView用得
		QQMessage msg = getItem(position);
		// 消息来自谁，如果消息来自我自己，说明是我发送的
		if (msg.from == app.getMyAccount()) {
			// 我自己的消息，发送
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
			// 发送的布局
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
			QQMessage msg = getItem(position);
			holder.time.setText(msg.sendTime);
			//holder.head.setImageResource(msg.fromAvatar);
			holder.content.setText(msg.content);
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
			QQMessage msg = getItem(position);
			//holder.head.setImageResource(msg.fromAvatar);
			holder.time.setText(msg.sendTime);
			holder.content.setText(msg.content);

			return convertView;
		}

	}
}
