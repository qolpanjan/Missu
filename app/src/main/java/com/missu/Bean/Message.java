package com.missu.Bean;

import android.graphics.Bitmap;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by alimj on 2017/3/8.
 */
@Entity
public class Message {
    @Id
    private Long Id;
    @Property
    private String messg_type;
    @Property
    private String messg_time;
    @Property
    private String messg_content;
    @Property
    private String messg_from_profile;
    @Property
    private String messg_from_username;
    @Property
    private String messg_translation;
    @Property
    private int messg_state;
    public int getMessg_state() {
        return this.messg_state;
    }
    public void setMessg_state(int messg_state) {
        this.messg_state = messg_state;
    }
    public String getMessg_translation() {
        return this.messg_translation;
    }
    public void setMessg_translation(String messg_translation) {
        this.messg_translation = messg_translation;
    }
    public String getMessg_from_username() {
        return this.messg_from_username;
    }
    public void setMessg_from_username(String messg_from_username) {
        this.messg_from_username = messg_from_username;
    }
    public String getMessg_from_profile() {
        return this.messg_from_profile;
    }
    public void setMessg_from_profile(String messg_from_profile) {
        this.messg_from_profile = messg_from_profile;
    }
    public String getMessg_content() {
        return this.messg_content;
    }
    public void setMessg_content(String messg_content) {
        this.messg_content = messg_content;
    }
    public String getMessg_time() {
        return this.messg_time;
    }
    public void setMessg_time(String messg_time) {
        this.messg_time = messg_time;
    }
    public String getMessg_type() {
        return this.messg_type;
    }
    public void setMessg_type(String messg_type) {
        this.messg_type = messg_type;
    }
    public Long getId() {
        return this.Id;
    }
    public void setId(Long Id) {
        this.Id = Id;
    }
    @Generated(hash = 1451709155)
    public Message(Long Id, String messg_type, String messg_time,
            String messg_content, String messg_from_profile,
            String messg_from_username, String messg_translation, int messg_state) {
        this.Id = Id;
        this.messg_type = messg_type;
        this.messg_time = messg_time;
        this.messg_content = messg_content;
        this.messg_from_profile = messg_from_profile;
        this.messg_from_username = messg_from_username;
        this.messg_translation = messg_translation;
        this.messg_state = messg_state;
    }
    @Generated(hash = 637306882)
    public Message() {
    }








}
