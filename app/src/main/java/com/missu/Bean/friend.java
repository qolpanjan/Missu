package com.missu.Bean;

import android.graphics.Bitmap;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.sql.Blob;

/**
 * Created by alimj on 2017/3/8.
 */

public class friend {

    private Long Id;

    private String friend_name;

    private String friend_nickname;

    private String friend_sex;

    private Bitmap friend_profile;



    public void setFriend_name(String friend_name) {
        this.friend_name = friend_name;
    }

    public void setFriend_nickname(String friend_nickname) {
        this.friend_nickname = friend_nickname;
    }

    public void setFriend_sex(String friend_sex) {
        this.friend_sex = friend_sex;
    }

    public void setUser_profile(Bitmap friend_profile) {
        this.friend_profile = friend_profile;
    }



    public String getFriend_name() {
        return friend_name;
    }

    public String getFriend_nickname() {
        return friend_nickname;
    }

    public String getFriend_sex() {
        return friend_sex;
    }

    public Bitmap getUser_profile() {
        return friend_profile;
    }


}
