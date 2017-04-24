package com.missu.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.missu.Bean.Friends;
import com.missu.R;

import java.util.List;

public class ContactInfoAdapter extends ArrayAdapter<Friends> {

	/**
	 * 
	 * @param context
	 * @param objects
	 */
	public ContactInfoAdapter(Context context, List<Friends> objects) {
		super(context, 0, objects);
	}

	class ViewHolder {
		ImageView avater;
		TextView title;
		//TextView desc;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Friends info = getItem(position);//
		//
		ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(getContext(), R.layout.friendlist_item, null);
			holder = new ViewHolder();
			holder.avater = (ImageView) convertView.findViewById(R.id.icon);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			//holder.desc = (TextView) convertView.findViewById(R.id.desc);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		//
		if (info.getAvatar() ==null) {
			//
			holder.avater.setImageResource(R.mipmap.icon);
		} else {
			Glide.with(getContext()).load(info.getAvatar()).placeholder(R.mipmap.icon).into(holder.avater);
		}

		holder.title.setText(info.getNick() + "");

		//holder.desc.setText(info.nick);
		return convertView;
	}
}
