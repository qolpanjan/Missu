package com.missu.Bean;

import android.graphics.Bitmap;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by alimj on 2017/3/8.
 */

public class Message {

    private Long Id;
    private String messg_type;
    private String messg_time;
    private String messg_content;
    private Bitmap messg_from_profile;
    private String messg_from_username;
    private String messg_translation;
    private int messg_state;



    public String getMessg_type() {
        return messg_type;
    }

    public String getMessg_time() {
        return messg_time;
    }

    public String getMessg_content() {
        return messg_content;
    }

    public Bitmap getMessg_from_profile() {
        return messg_from_profile;
    }

    public String getMessg_from_username() {
        return messg_from_username;
    }

    public String getMessg_translation() {
        return messg_translation;
    }

    public int getMessg_state(){return messg_state;}



    public void setMessg_type(String messg_type) {
        this.messg_type = messg_type;
    }

    public void setMessg_time(String messg_time) {
        this.messg_time = messg_time;
    }

    public void setMessg_content(String messg_content) {
        this.messg_content = messg_content;
    }

    public void setMessg_from_profile(Bitmap messg_from_profile) {
        this.messg_from_profile = messg_from_profile;
    }

    public void setMessg_from_username(String messg_to_profile) {
        this.messg_from_username = messg_to_profile;
    }

    public void setMessg_translation(String messg_translation) {
        this.messg_translation = messg_translation;
    }
    public void setMessg_state(int messg_state){
        this.messg_state = messg_state;
    }


}
