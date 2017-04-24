package com.missu.Bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by alimj on 2017/4/23.
 */
@Entity
public class Friends {
    private int id = 0;
    private String account;// 账号 QQ号
    private String nick = "";// 昵称
    private String sex="true";
    private String avatar="";


    @Generated(hash = 219859861)
    public Friends(int id, String account, String nick, String sex, String avatar) {
        this.id = id;
        this.account = account;
        this.nick = nick;
        this.sex = sex;
        this.avatar = avatar;
    }

    @Generated(hash = 823074882)
    public Friends() {
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getBelongTo() {
        return id;
    }

    public void setBelongTo(String belongTo) {
        this.id = Integer.getInteger(belongTo);
    }



    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }




}
