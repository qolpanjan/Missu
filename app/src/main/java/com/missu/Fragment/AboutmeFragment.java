package com.missu.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.missu.Activitys.ChangePassActivity;
import com.missu.Activitys.MyAvaterActivity;
import com.missu.R;


/**
 * Created by alimj on 2017/3/8.
 */

public class AboutmeFragment extends Fragment {

    public static final String MYAVATERURL = "myavater";
    public static final String MYID = "mineid";


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me_fragment,container,false);
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
                    intent.putExtra(MYID,"");
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

                        }
                        if (newsex == R.id.tv_message_received){
                            //新性别为女
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

            }
        });



        return view;
    }


}
