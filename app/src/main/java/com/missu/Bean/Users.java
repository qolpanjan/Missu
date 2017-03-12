package com.missu.Bean;

import android.graphics.Bitmap;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.sql.Blob;

/**
 * Created by alimj on 2017/3/8.
 */

public class Users {

    private long id;
    private String user_name;
    private String user_password;
    private String user_nickname;
    private String user_sex;
    private Object user_profile;
    private int unread_message;




}
