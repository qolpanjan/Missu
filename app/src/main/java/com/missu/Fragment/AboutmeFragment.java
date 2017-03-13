package com.missu.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
        return view;
    }


}
