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
 * ������Ϣ��������
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
	 * ���ݼ����е�positionλ�ã����ز�ͬ��ֵ������ͬ�Ĳ��� 0�����Լ����͵���Ϣ 1������յ�����Ϣ
	 * 
	 */
	@Override
	public int getItemViewType(int position) {// ��������Ǹ�getView�õ�
		QQMessage msg = getItem(position);
		// ��Ϣ����˭�������Ϣ�������Լ���˵�����ҷ��͵�
		if (msg.from == app.getMyAccount()) {
			// ���Լ�����Ϣ������
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
			// ���͵Ĳ���
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
			QQMessage msg = getItem(position);
			holder.time.setText(msg.sendTime);
			//holder.head.setImageResource(msg.fromAvatar);
			holder.content.setText(msg.content);
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
			QQMessage msg = getItem(position);
			//holder.head.setImageResource(msg.fromAvatar);
			holder.time.setText(msg.sendTime);
			holder.content.setText(msg.content);

			return convertView;
		}

	}
}
