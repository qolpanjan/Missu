package com.missu.Bean;

import android.graphics.Bitmap;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.sql.Blob;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by alimj on 2017/3/8.
 */
@Entity
public class Users {
    @Id
    private long id;
    @Property
    private String user_name;
    @Property
    private String user_password;
    @Property
    private String user_nickname;
    @Property
    private String user_sex;
    @Property
    private String user_profile;
    @Property
    private int unread_message;
    public int getUnread_message() {
        return this.unread_message;
    }
    public void setUnread_message(int unread_message) {
        this.unread_message = unread_message;
    }
    public String getUser_profile() {
        return this.user_profile;
    }
    public void setUser_profile(String user_profile) {
        this.user_profile = user_profile;
    }
    public String getUser_sex() {
        return this.user_sex;
    }
    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }
    public String getUser_nickname() {
        return this.user_nickname;
    }
    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }
    public String getUser_password() {
        return this.user_password;
    }
    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }
    public String getUser_name() {
        return this.user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    @Generated(hash = 1809545191)
    public Users(long id, String user_name, String user_password,
            String user_nickname, String user_sex, String user_profile,
            int unread_message) {
        this.id = id;
        this.user_name = user_name;
        this.user_password = user_password;
        this.user_nickname = user_nickname;
        this.user_sex = user_sex;
        this.user_profile = user_profile;
        this.unread_message = unread_message;
    }
    @Generated(hash = 2146996206)
    public Users() {
    }




}
