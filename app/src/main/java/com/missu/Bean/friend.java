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
public class friend {
    @Id
    private Long Id;
    @Property
    private String friend_name;
    @Property
    private String friend_nickname;
    @Property
    private String friend_sex;
    @Property
    private String friend_profile;
    public String getFriend_profile() {
        return this.friend_profile;
    }
    public void setFriend_profile(String friend_profile) {
        this.friend_profile = friend_profile;
    }
    public String getFriend_sex() {
        return this.friend_sex;
    }
    public void setFriend_sex(String friend_sex) {
        this.friend_sex = friend_sex;
    }
    public String getFriend_nickname() {
        return this.friend_nickname;
    }
    public void setFriend_nickname(String friend_nickname) {
        this.friend_nickname = friend_nickname;
    }
    public String getFriend_name() {
        return this.friend_name;
    }
    public void setFriend_name(String friend_name) {
        this.friend_name = friend_name;
    }
    public Long getId() {
        return this.Id;
    }
    public void setId(Long Id) {
        this.Id = Id;
    }
    @Generated(hash = 131311379)
    public friend(Long Id, String friend_name, String friend_nickname,
            String friend_sex, String friend_profile) {
        this.Id = Id;
        this.friend_name = friend_name;
        this.friend_nickname = friend_nickname;
        this.friend_sex = friend_sex;
        this.friend_profile = friend_profile;
    }
    @Generated(hash = 131109238)
    public friend() {
    }





}
