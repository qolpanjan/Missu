package com.missu.Bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by alimj on 2017/5/2.
 */
@Entity
public class MessageListBean {
    @org.greenrobot.greendao.annotation.Id
    private Long Id;
    @Property
    private String Other_Account;
    @Property
    private String Other_nick;
    @Property
    private String Other_avater;
    @Property
    private String type;
    @Property
    private String time;
    @Property
    private String translate;
    @Property
    private String content;
    @Generated(hash = 1898492758)
    public MessageListBean(Long Id, String Other_Account, String Other_nick,
            String Other_avater, String type, String time, String translate,
            String content) {
        this.Id = Id;
        this.Other_Account = Other_Account;
        this.Other_nick = Other_nick;
        this.Other_avater = Other_avater;
        this.type = type;
        this.time = time;
        this.translate = translate;
        this.content = content;
    }
    @Generated(hash = 751367207)
    public MessageListBean() {
    }
    public Long getId() {
        return this.Id;
    }
    public void setId(Long Id) {
        this.Id = Id;
    }
    public String getOther_Account() {
        return this.Other_Account;
    }
    public void setOther_Account(String Other_Account) {
        this.Other_Account = Other_Account;
    }
    public String getOther_nick() {
        return this.Other_nick;
    }
    public void setOther_nick(String Other_nick) {
        this.Other_nick = Other_nick;
    }
    public String getOther_avater() {
        return this.Other_avater;
    }
    public void setOther_avater(String Other_avater) {
        this.Other_avater = Other_avater;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getTranslate() {
        return this.translate;
    }
    public void setTranslate(String translate) {
        this.translate = translate;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }

}
