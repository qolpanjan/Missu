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
 * ������Ϣ��������
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
	 * ���ݼ����е�positionλ�ã����ز�ͬ��ֵ������ͬ�Ĳ��� 0�����Լ����͵���Ϣ 1������յ�����Ϣ
	 * 
	 */
	@Override
	public int getItemViewType(int position) {// ��������Ǹ�getView�õ�
		MessageListBean msg = getItem(position);
		// ��Ϣ����˭�������Ϣ�������Լ���˵�����ҷ��͵�
		if (msg.getType().equals("to")) {
			// ���Լ�����Ϣ������
            //to �Լ����͵�
            //from �յ���
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * ���ֲ���
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
			// �Լ����ͷ��͵Ĳ���
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

			// ����ֵ
			MessageListBean msg = getItem(position);
			holder.time.setText(msg.getTime());
			Glide.with(context).load(app.getAvater()).placeholder(R.mipmap.icon).fitCenter().animate(R.anim.item_alpha_in).into(holder.head);
			//holder.head.setImageResource(app.getAvater());
			holder.content.setText(msg.getContent());
			return convertView;

		} else {
			// ���յĲ���
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
			// ����ֵ
			MessageListBean msg = getItem(position);
			//holder.head.setImageResource(msg.fromAvatar);
            Glide.with(context).load(msg.getOther_avater()).placeholder(R.mipmap.icon).fitCenter().animate(R.anim.item_alpha_in).into(holder.head);
			holder.time.setText(msg.getTime());
			holder.content.setText(msg.getContent());

			return convertView;
		}

	}
}
