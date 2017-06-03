package com.missu.View;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by alimj on 10/13/2016.
 * 主界面的ViewPager类
 * 里面动态替换三个Fragment
 */

public class TabFragment extends Fragment {

    private String mTitle = "Default";
    public static final String TITLE="title";


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments()!=null){
            mTitle = getArguments().getString(TITLE);//动态获取每个Fragment显示的Title
        }
        TextView tv = new TextView(getActivity());//定义一个心的TextView
        tv.setTextSize(30);
        tv.setBackgroundColor(Color.parseColor("#ffffff"));
        tv.setText(mTitle);


        return tv;
    }
}
