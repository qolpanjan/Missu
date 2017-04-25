package com.missu.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.anye.greendao.gen.DaoSession;
import com.anye.greendao.gen.UsersDao;
import com.bumptech.glide.Glide;
import com.missu.Activitys.ChangePassActivity;
import com.missu.Activitys.MainActivity;
import com.missu.Activitys.MyAvaterActivity;
import com.missu.Activitys.SettingActivity;
import com.missu.Adapter.MyApplication;
import com.missu.Bean.MessageBean;
import com.missu.Bean.MessageType;
import com.missu.Bean.Users;
import com.missu.R;
import com.missu.Utils.Mytime;
import com.missu.Utils.NetConnection;
import com.missu.Utils.ThreadUtils;

import java.io.IOException;


/**
 * Created by alimj on 2017/3/8.
 */

public class AboutmeFragment extends Fragment {

    public static final String MYAVATERURL = "myavater";
    public static final String MYID = "mineid";
    TextView user_name,user_nickname,user_sex,user_id;
    String sex= "男";//


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me_fragment,container,false);
        //MyApplication app = MyApplication.getInstances();
        //final Users users = new Users((long)1,app.getMyAccount(),app.getPassword(),app.getNickName(),app.getSex(),app.getMyAvater(),0);

        final DaoSession daoSession = MyApplication.getInstances().getDaoSession();
        final Users users = daoSession.getUsersDao().queryBuilder().where(UsersDao.Properties.User_name.eq(MainActivity.USERNAME)).build().unique();

        //用户头像
        ImageView myAvater = (ImageView) view.findViewById(R.id.img_my_avater);
        //头像右边的昵称
        user_name = (TextView) view.findViewById(R.id.tv_me_user_nickname);
        //下面的昵称
        user_nickname = (TextView)view.findViewById(R.id.tv_me_nickname);
        //设置两个昵称
        user_nickname.setText(users.getUser_nickname());
        user_name.setText(users.getUser_nickname());
        //用户ID
        user_id = (TextView)view.findViewById(R.id.tv_me_user_id);
        user_id.setText(users.getUser_name());
        user_sex = (TextView)view.findViewById(R.id.tv_me_sex);
        sex = users.getUser_sex();
        if (sex.equals("true")){
            user_sex.setText("男");
        }else{
            user_sex.setText("女");
        }


        if (users.getUser_profile() != null && !users.getUser_profile().equals("")) {
                Glide.with(getContext()).load(users.getUser_profile()).placeholder(R.mipmap.icon).into(myAvater);
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
        final LinearLayout UserId = (LinearLayout)view.findViewById(R.id.lo_me_user_id);
        UserId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"用户ID是用户识别的唯一凭证，只能在注册的时候设置，无法修改",Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 打开修改密码页面
         */
        LinearLayout UserPassword = (LinearLayout)view.findViewById(R.id.lo_me_user_password);
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

        final LinearLayout UserNickName = (LinearLayout)view.findViewById(R.id.lo_me_user_nickname);
        UserNickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText et = new EditText(getContext());
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("请输入你的新昵称");
                dialog.setView(et);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String newNickName = et.getText().toString().trim();
                        if (newNickName.equals("")) {
                            Toast.makeText(getContext(), "新昵称不能为空！" + et, Toast.LENGTH_LONG).show();
                        }else {
                            Users users1 = new Users(users.getId(),users.getUser_name(),users.getUser_password(),newNickName,users.getUser_sex(),users.getUser_profile(),users.getUnread_message());
                            daoSession.getUsersDao().update(users1);
                            final MessageBean messageBean = new MessageBean();

                            //MyApplication.getInstances().getMyConn().sendMessage();
                            user_nickname.setText(newNickName);
                            user_name.setText(newNickName);

                            ThreadUtils.runInSubThread(new Runnable() {
                                @Override
                                public void run() {
                                    String content = user_id.getText().toString()+"#"+ users.getUser_password()+"#"+newNickName+"#"+users.getUser_profile()+"#"+users.getUser_sex();
                                    MessageBean messageBean1 = new MessageBean(MessageType.MSG_TYPE_CHANGE_USER,"Client","Server",content, Mytime.geTime(),users.getUser_profile());
                                    try {
                                        SendMsg(messageBean1);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                            Toast.makeText(getContext(), "昵称修改成功！", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    }
                });
                dialog.setNegativeButton("取消",null);
                dialog.show();
            }
        });


        /**
         * 修改性别
         */
        LinearLayout UserSex = (LinearLayout)view.findViewById(R.id.lo_me_user_sex);
        UserSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final RadioButton rb_man  = new RadioButton(getContext());
                rb_man.setId(R.id.tv_message_send);
                rb_man.setText("男");
                RadioButton rb_woman = new RadioButton(getContext());
                rb_woman.setId(R.id.tv_message_received);
                rb_woman.setText("女");
                final RadioGroup layout = new RadioGroup(getContext());
                layout.addView(rb_man);
                layout.addView(rb_woman);
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("请选择你的性别");
                dialog.setView(layout);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int newsex = layout.getCheckedRadioButtonId();
                        if (newsex == R.id.tv_message_send){
                            //新性别为男
                            Users users1 = new Users(users.getId(),users.getUser_name(),users.getUser_password(),users.getUser_nickname(),"男",users.getUser_profile(),users.getUnread_message());
                            daoSession.getUsersDao().update(users1);
                            user_sex.setText("男");

                            ThreadUtils.runInSubThread(new Runnable() {
                                @Override
                                public void run() {
                                    String content = user_id.getText().toString()+"#"+ users.getUser_password()+"#"+user_nickname.getText().toString()+"#"+users.getUser_profile()+"#"+"true";
                                    MessageBean messageBean1 = new MessageBean(MessageType.MSG_TYPE_CHANGE_USER,"Client","Server",content, Mytime.geTime(),users.getUser_profile());
                                    try {
                                        SendMsg(messageBean1);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });


                            Toast.makeText(getContext(), "性别修改成功！", Toast.LENGTH_LONG).show();

                        }
                        if (newsex == R.id.tv_message_received){
                            //新性别为女
                            Users users1 = new Users(users.getId(),users.getUser_name(),users.getUser_password(),users.getUser_nickname(),"女",users.getUser_profile(),users.getUnread_message());
                            //daoSession.getUsersDao().update(users1);
                            user_sex.setText("女");
                            ThreadUtils.runInSubThread(new Runnable() {
                                @Override
                                public void run() {
                                    String content = user_name.getText().toString()+"#"+ users.getUser_password()+"#"+user_nickname.getText().toString()+"#"+users.getUser_profile()+"#"+"false";
                                    MessageBean messageBean1 = new MessageBean(MessageType.MSG_TYPE_CHANGE_USER,"Client","Server",content, Mytime.geTime(),users.getUser_profile());
                                    try {
                                        SendMsg(messageBean1);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                            Toast.makeText(getContext(), "性别修改成功！", Toast.LENGTH_LONG).show();

                        }
                    }
                });
                dialog.setNegativeButton("取消",null);
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



    void SendMsg(final MessageBean msg) throws IOException {
        MyApplication app = MyApplication.getInstances();
        NetConnection conn = app.getMyConn();
        if (conn==null){
            conn = new NetConnection(MyApplication.IP, 8090);
            Log.e("NEWCONN","NEWCONN");
        }

        conn.connect();
        conn.sendMessage(msg);
        conn.addOnMessageListener(new NetConnection.OnMessageListener() {
            @Override
            public void onReveive(MessageBean msgFrom) {

                if (MessageType.MSG_TYPE_LOGIN_SUCCESS.equals(msgFrom.getType())){

                }
                if (MessageType.MSG_TYPE_FAILURE.equals(msgFrom.getType())){
                    try {
                        SendMsg(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


}
