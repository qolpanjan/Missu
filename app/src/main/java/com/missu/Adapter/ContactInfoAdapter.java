package com.missu.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.missu.Bean.ContactInfo;
import com.missu.R;

import java.util.List;

public class ContactInfoAdapter extends ArrayAdapter<ContactInfo> {

	/**
	 * �����������뼯��
	 * 
	 * @param context
	 * @param objects
	 */
	public ContactInfoAdapter(Context context, List<ContactInfo> objects) {
		super(context, 0, objects);
	}

	class ViewHolder {
		ImageView icon;
		TextView title;
		TextView desc;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ContactInfo info = getItem(position);// �Ӽ�����ȡ������
		// listView���Ż�
		ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(getContext(), R.layout.friendlist_item, null);
			holder = new ViewHolder();
			holder.icon = (ImageView) convertView.findViewById(R.id.img_friendlist_profile);
			holder.title = (TextView) convertView.findViewById(R.id.tv_friendlist_nickname);
			holder.desc = (TextView) convertView.findViewById(R.id.tv_friendlist_account);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// ��ֵ
		if (info.avatar.equals("0")) {
			// Ĭ��ͷ��
			holder.icon.setImageResource(R.mipmap.icon);
		} else {
			//holder.icon.setImageResource(info.avatar);
		}
		// ������Լ���¼���˺ţ�����ʾ�Լ���������ʾ�˺�
		// ��ñ�����application�е��Լ���¼���˺�

		holder.title.setText(info.nick + "");

		holder.desc.setText(info.account);
		return convertView;
	}
}
