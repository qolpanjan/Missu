package com.missu.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.missu.Activitys.ChangePassActivity;
import com.missu.Activitys.ImApp;
import com.missu.Activitys.MyAvaterActivity;
import com.missu.Activitys.QRActivity;
import com.missu.Activitys.SettingActivity;

import com.missu.Bean.DaoSession;
import com.missu.Bean.QQMessage;
import com.missu.Bean.QQMessageType;
import com.missu.Bean.Users;
import com.missu.Bean.UsersDao;
import com.missu.R;
import com.missu.Util.QQConnection;
import com.missu.Util.ThreadUtils;

import java.io.IOException;


/**
 * Created by alimj on 2017/3/8.
 */

public class AboutmeFragment extends Fragment {

    public static final String MYAVATERURL = "myavater";
    public static final String MYID = "mineid";
    TextView user_name,user_nickname,user_sex,user_id;
    String sex;//
    private ImApp app;
    Context context;
    QQConnection conn;
    DaoSession daoSession;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me_fragment,container,false);
        //MyApplication app = MyApplication.getInstances();
        app = (ImApp) getActivity().getApplication();
        daoSession = app.getDaoSession();
        final Users users = daoSession.getUsersDao().queryBuilder().where(UsersDao.Properties.User_name.eq(app.getMyAccount())).build().unique();

        //用户头像
        ImageView myAvater = (ImageView) view.findViewById(R.id.img_my_avater);
        Glide.with(getContext()).load(app.getAvater()).placeholder(R.mipmap.icon).fitCenter().animate(R.anim.item_alpha_in).into(myAvater);
        //头像右边的昵称
        user_name = (TextView) view.findViewById(R.id.tv_me_user_nickname);
        //下面的昵称
        user_nickname = (TextView)view.findViewById(R.id.tv_me_nickname);
        //设置两个昵称
        user_nickname.setText(app.getNick());
        user_name.setText(app.getNick());
        //用户ID
        user_id = (TextView)view.findViewById(R.id.tv_me_user_id);
        user_id.setText(app.getMyAccount());
        user_sex = (TextView)view.findViewById(R.id.tv_me_sex);
        sex = app.getSex();
        if (sex.equals("true")){
            user_sex.setText(getString(R.string.man));
        }else{
            user_sex.setText(getString(R.string.woman));
        }



        LinearLayout avater_layout= (LinearLayout) view.findViewById(R.id.lo_me_avater);
        avater_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyAvaterActivity.class);
                intent.putExtra(MYAVATERURL,"");
                startActivity(intent);
            }
        });

        /**
         * 点击账户
         */
        final RelativeLayout UserId = (RelativeLayout)view.findViewById(R.id.lo_me_user_id);
        UserId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),"用户ID是用户识别的唯一凭证，只能在注册的时候设置，无法修改", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), QRActivity.class);
                startActivity(intent);
            }
        });

        /**
         * 打开修改密码页面
         */
        RelativeLayout UserPassword = (RelativeLayout)view.findViewById(R.id.lo_me_user_password);
        UserPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(getContext(), ChangePassActivity.class);
                    startActivity(intent);
            }
        });

        /**
         * 修改昵称
         */

        final RelativeLayout UserNickName = (RelativeLayout)view.findViewById(R.id.lo_me_user_nickname);
        UserNickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText et = new EditText(getContext());
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle(getString(R.string.input_new_nick));
                dialog.setView(et);
                dialog.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String newNickName = et.getText().toString().trim();
                        if (newNickName.equals("")) {
                            Toast.makeText(getContext(), getString(R.string.new_nick_not_null) + et, Toast.LENGTH_LONG).show();
                        }else {
                            //MyApplication.getInstances().getMyConn().sendMessage();
                            user_nickname.setText(newNickName);
                            user_name.setText(newNickName);
                            app.setNick(newNickName);

                            ThreadUtils.runInSubThread(new Runnable() {
                                @Override
                                public void run() {
                                    //String content = user_id.getText().toString()+"#"+ users.getUser_password()+"#"+newNickName+"#"+users.getUser_profile()+"#"+users.getUser_sex();
                                    conn = app.getMyConn();
                                    QQMessage message = new QQMessage();
                                    message.type = QQMessageType.MSG_TYPE_UPDATE;
                                    message.content = app.getMyAccount()+"#"+app.getPassword()+"#"+ newNickName + "#" + app.getAvater()+"#"+app.getSex();
                                    try {
                                        conn.sendMessage(message);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                            Users user = new Users(users.getId(),users.getUser_name(),users.getUser_password(),newNickName,users.getUser_sex(),users.getUser_profile());
                            daoSession.getUsersDao().update(user);
                            Toast.makeText(getContext(), getString(R.string.nick_change_succes), Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    }
                });
                dialog.setNegativeButton(getString(R.string.cancel),null);
                dialog.show();
            }
        });


        /**
         * 修改性别
         */
        RelativeLayout UserSex = (RelativeLayout)view.findViewById(R.id.lo_me_user_sex);
        UserSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final RadioButton rb_man  = new RadioButton(getContext());
                rb_man.setText(getString(R.string.man));
                RadioButton rb_woman = new RadioButton(getContext());
                rb_woman.setText(getString(R.string.woman));
                final RadioGroup layout = new RadioGroup(getContext());
                layout.addView(rb_man);
                layout.addView(rb_woman);
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("请选择你的性别");
                dialog.setView(layout);
                dialog.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int newsex = layout.getCheckedRadioButtonId();
                        if (newsex == 0){
                            //新性别为男
                            //Users users1 = new Users(users.getId(),users.getUser_name(),users.getUser_password(),users.getUser_nickname(),"男",users.getUser_profile(),users.getUnread_message());
                            //daoSession.getUsersDao().update(users1);
                            user_sex.setText(getString(R.string.man));

                            ThreadUtils.runInSubThread(new Runnable() {
                                @Override
                                public void run() {
                                    //String content = user_id.getText().toString()+"#"+ users.getUser_password()+"#"+user_nickname.getText().toString()+"#"+users.getUser_profile()+"#"+"true";
                                    //QQMessage messageBean1 = new MessageBean(MessageType.MSG_TYPE_CHANGE_USER,"Client","Server",content, Mytime.geTime(),users.getUser_profile());
                                    app.setSex("true");
                                    conn = app.getMyConn();
                                    QQMessage message = new QQMessage();
                                    message.type = QQMessageType.MSG_TYPE_UPDATE;
                                    message.content = app.getMyAccount()+"#"+app.getPassword()+"#"+ app.getNick() + "#" + app.getAvater()+"#"+"true";
                                    try {
                                        conn.sendMessage(message);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });

                            Users user = new Users(users.getId(),users.getUser_name(),users.getUser_password(),users.getUser_nickname(),"true",users.getUser_profile());
                            daoSession.getUsersDao().update(user);
                            Toast.makeText(getContext(), getString(R.string.sex_change_succes), Toast.LENGTH_LONG).show();

                        }
                        if (newsex == 1){
                            user_sex.setText(getString(R.string.woman));
                            ThreadUtils.runInSubThread(new Runnable() {
                                @Override
                                public void run() {
                                    conn = app.getMyConn();
                                    app.setSex("false");
                                    QQMessage message = new QQMessage();
                                    message.type = QQMessageType.MSG_TYPE_UPDATE;
                                    message.content = app.getMyAccount()+"#"+ app.getNick() + "#" + app.getAvater()+"#"+"false";
                                    try {
                                        conn.sendMessage(message);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                            Users user = new Users(users.getId(),users.getUser_name(),users.getUser_password(),users.getUser_nickname(),"false",users.getUser_profile());
                            daoSession.getUsersDao().update(user);
                            Toast.makeText(getContext(),getString(R.string.sex_change_succes) , Toast.LENGTH_LONG).show();

                        }
                    }
                });
                dialog.setNegativeButton(getString(R.string.cancel),null);
                dialog.show();
            }
        });

        /**
         * 打开设置界面
         */

        LinearLayout UserSetting = (LinearLayout)view.findViewById(R.id.lo_user_setting);
        UserSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
            }
        });



        return view;
    }

    }

