package com.missu.Bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;

/**
 * Created by alimj on 2017/3/8.
 */
@Entity
public class Users{
    @Id
    private Long id;
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

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Generated(hash = 394723671)
    public Users(Long id, String user_name, String user_password,
            String user_nickname, String user_sex, String user_profile) {
        this.id = id;
        this.user_name = user_name;
        this.user_password = user_password;
        this.user_nickname = user_nickname;
        this.user_sex = user_sex;
        this.user_profile = user_profile;
    }
    public Users() {
    }




}
