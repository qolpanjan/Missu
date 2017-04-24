package com.missu.Bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by alimj on 2017/4/23.
 */
@Entity
public class Friends {
    private String account;// 账号 QQ号
    private String nick = "";// 昵称
    private String sex="true";
    private String avatar="";
    private String belongTo ="";

    @Generated(hash = 2035630059)
    public Friends(String account, String nick, String sex, String avatar, String belongTo) {
        this.account = account;
        this.nick = nick;
        this.sex = sex;
        this.avatar = avatar;
        this.belongTo = belongTo;
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

    public String getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }



    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }




}
