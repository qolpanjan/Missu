package com.missu.Bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by alimj on 2017/5/2.
 */
@Entity
public class Message {
    @org.greenrobot.greendao.annotation.Id
    private Long Id;
    @Property
    private String account;
    @Property
    private String avater;
    @Property
    private String time;
    @Property
    private String content;
    @Property
    private String nick;
    @Generated(hash = 1355506322)
    public Message(Long Id, String account, String avater, String time,
            String content, String nick) {
        this.Id = Id;
        this.account = account;
        this.avater = avater;
        this.time = time;
        this.content = content;
        this.nick = nick;
    }
    @Generated(hash = 637306882)
    public Message() {
    }
    public Long getId() {
        return this.Id;
    }
    public void setId(Long Id) {
        this.Id = Id;
    }
    public String getAccount() {
        return this.account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getAvater() {
        return this.avater;
    }
    public void setAvater(String avater) {
        this.avater = avater;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getNick() {
        return this.nick;
    }
    public void setNick(String nick) {
        this.nick = nick;
    }

}
