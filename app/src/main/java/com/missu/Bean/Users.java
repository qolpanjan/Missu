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

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public Object getUser_profile() {
        return user_profile;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public void setUser_profile(Blob user_profile) {
        this.user_profile = user_profile;
    }

    public void setUnread_message(int unread_message) {
        this.unread_message = unread_message;
    }

    public int getUnread_message() {
        return unread_message;
    }


}
