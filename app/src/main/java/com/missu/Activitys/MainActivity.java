package com.missu.Activitys;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.EditText;
import android.widget.Toast;

import com.missu.ChangeColorAndIcon;
import com.missu.Fragment.AboutmeFragment;
import com.missu.Fragment.ChatListFragment;
import com.missu.Fragment.FriendListFragment;
import com.missu.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import q.rorbin.badgeview.QBadgeView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,ViewPager.OnPageChangeListener{

    private ViewPager mViewpager;
    private List<Fragment> mTabs = new ArrayList<Fragment>();
    private FragmentPagerAdapter mAdapter;
    private List<ChangeColorAndIcon> mTabIndecator=new ArrayList<ChangeColorAndIcon>();
    public static String USERNAME = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //调用显示菜单方法
        setOverFlowButtonAlways();
        //getActionBar().setDisplayShowHomeEnabled(false);
        //actionbar.setTitle("MissU");
        SharedPreferences pref = getSharedPreferences("login",MODE_PRIVATE);
        USERNAME = pref.getString("name",null);


        initView();
        initDatas();
        mViewpager.setAdapter(mAdapter);
        initEvent();
    }

    /**
     * 初始化所有事件
     */
    private void initEvent() {
        mViewpager.addOnPageChangeListener(this);
    }

    private void initView() {


        mViewpager = (ViewPager)findViewById(R.id.viewpager);

        ChangeColorAndIcon one = (ChangeColorAndIcon)findViewById(R.id.indicator_one);
        mTabIndecator.add(one);
        ChangeColorAndIcon two = (ChangeColorAndIcon)findViewById(R.id.indicator_two);
        mTabIndecator.add(two);

        //ChangeColorAndIcon three = (ChangeColorAndIcon)findViewById(R.id.indicator_three);
        //mTabIndecator.add(three);

        ChangeColorAndIcon four = (ChangeColorAndIcon)findViewById(R.id.indicator_four);
        mTabIndecator.add(four);

        /**
         * 底部菜单点击事件
         */
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        //three.setOnClickListener(this);
        four.setOnClickListener(this);

        one.setIconAlpha(1.0f);

        /**
         * 设置消息提示数目红点
         *
         */

        new QBadgeView(MainActivity.this).bindTarget(one).setBadgeNumber(3).setGravityOffset(98,0,true);
        new QBadgeView(MainActivity.this).bindTarget(two).setBadgeNumber(1).setGravityOffset(98,0,true);

    }

    private void initDatas() {

        ChatListFragment chatListFragment = new ChatListFragment();
        mTabs.add(chatListFragment);

        FriendListFragment friendListFragment=new FriendListFragment();
        mTabs.add(friendListFragment);

        AboutmeFragment aboutmeFragment = new AboutmeFragment();
        mTabs.add(aboutmeFragment);
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                return mTabs.get(position);
            }

            @Override
            public int getCount() {
                return mTabs.size();
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                Intent intent = new Intent(getApplicationContext(),SeachFriendActivity.class);
                startActivity(intent);
                break;

        }
        return true;
    }

    /**
     * 顶部菜单显示选项显示图标
     * @param menu
     * @return
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menu!=null){
            if (menu.getClass().getSimpleName().equals("MenuBuilder"))
            {
                try {
                    Method method=menu.getClass().getDeclaredMethod("setOptionalIconsVisible",Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu,true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
            resetOtherTabs();
        switch (v.getId()){
            case R.id.indicator_one:
                mTabIndecator.get(0).setIconAlpha(1.0f);
                mViewpager.setCurrentItem(0,false);
                break;
            case R.id.indicator_two:
                mTabIndecator.get(1).setIconAlpha(1.0f);
                mViewpager.setCurrentItem(1,false);
                break;
            //case R.id.indicator_three:
                //mTabIndecator.get(2).setIconAlpha(1.0f);
              //  mViewpager.setCurrentItem(2,false);
               // break;

            case R.id.indicator_four:
                mTabIndecator.get(2).setIconAlpha(1.0f);
                mViewpager.setCurrentItem(3,false);
                break;
        }
    }

    /*

     */
    private void resetOtherTabs() {
        for (int i=0;i<mTabIndecator.size();i++){
            mTabIndecator.get(i).setIconAlpha(0);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset>0){
            ChangeColorAndIcon left= mTabIndecator.get(position);
            ChangeColorAndIcon right=mTabIndecator.get(position+1);
            left.setIconAlpha(1-positionOffset);
            right.setIconAlpha(positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    /**
     * 显示顶部菜单
     */
    private void setOverFlowButtonAlways()
    {
        try {
            ViewConfiguration config = ViewConfiguration.get(MainActivity.this);

            Field field = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            field.setAccessible(true);
            field.setBoolean(config,false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }



}

