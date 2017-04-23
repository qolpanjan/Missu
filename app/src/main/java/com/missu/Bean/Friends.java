package com.missu.Bean;

/**
 * Created by alimj on 2017/4/23.
 */

public class Friends {
    public String account;// 账号 QQ号

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

    public String nick = "";// 昵称
    public String avatar;// 头像
    public String belongTo ="";
}
