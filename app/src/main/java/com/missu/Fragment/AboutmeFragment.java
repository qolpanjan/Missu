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
import com.missu.Bean.Users;
import com.missu.R;


/**
 * Created by alimj on 2017/3/8.
 */

public class AboutmeFragment extends Fragment {

    public static final String MYAVATERURL = "myavater";
    public static final String MYID = "mineid";
    TextView user_name,user_nickname,user_sex;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me_fragment,container,false);
        MyApplication app = MyApplication.getInstances();
        final Users users = new Users((long)1,app.getMyAccount(),app.getPassword(),app.getNickName(),app.getSex(),app.getMyAvater(),0);

       // final DaoSession daoSession = MyApplication.getInstances().getDaoSession();
        //final Users users = daoSession.getUsersDao().queryBuilder().where(UsersDao.Properties.User_name.eq(MainActivity.USERNAME)).build().unique();

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
        TextView user_id = (TextView)view.findViewById(R.id.tv_me_user_id);
        user_id.setText(users.getUser_name());
        user_sex = (TextView)view.findViewById(R.id.tv_me_sex);
        user_sex.setText(users.getUser_sex());


        if (users.getUser_profile() != null && !users.getUser_profile().equals("")) {
                Glide.with(getContext()).load(users.getUser_profile()).into(myAvater);
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

        LinearLayout UserId = (LinearLayout)view.findViewById(R.id.lo_me_user_id);
        UserId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"用户ID是用户识别的唯一凭证，只能在注册的时候设置，无法修改",Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayout UserPassword = (LinearLayout)view.findViewById(R.id.lo_me_user_password);
        UserPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(getContext(), ChangePassActivity.class);
                    startActivity(intent);
            }
        });

        LinearLayout UserNickName = (LinearLayout)view.findViewById(R.id.lo_me_user_nickname);
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
                        String newNickName = et.getText().toString().trim();
                        if (newNickName.equals("")) {
                            Toast.makeText(getContext(), "搜索内容不能为空！" + et, Toast.LENGTH_LONG).show();
                        }else {
                            Users users1 = new Users(users.getId(),users.getUser_name(),users.getUser_password(),newNickName,users.getUser_sex(),users.getUser_profile(),users.getUnread_message());
                            //daoSession.getUsersDao().update(users1);
                            MessageBean messageBean = new MessageBean();

                            //MyApplication.getInstances().getMyConn().sendMessage();
                            user_nickname.setText(newNickName);
                            user_name.setText(newNickName);
                            Toast.makeText(getContext(), "昵称修改成功！", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                dialog.setNegativeButton("取消",null);
                dialog.show();
            }
        });

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
                            //daoSession.getUsersDao().update(users1);
                            user_sex.setText("男");
                            Toast.makeText(getContext(), "性别修改成功！", Toast.LENGTH_LONG).show();

                        }
                        if (newsex == R.id.tv_message_received){
                            //新性别为女
                            Users users1 = new Users(users.getId(),users.getUser_name(),users.getUser_password(),users.getUser_nickname(),"女",users.getUser_profile(),users.getUnread_message());
                            //daoSession.getUsersDao().update(users1);
                            user_sex.setText("女");
                            Toast.makeText(getContext(), "性别修改成功！", Toast.LENGTH_LONG).show();

                        }
                    }
                });
                dialog.setNegativeButton("取消",null);
                dialog.show();
            }
        });


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
