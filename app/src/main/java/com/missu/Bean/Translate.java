package com.missu.Bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by alimj on 2017/4/17.
 */
@Entity
public class Translate {
    @org.greenrobot.greendao.annotation.Id
    Long Id;
    String Chinise;
    String Uighur;
    public String getUighur() {
        return this.Uighur;
    }
    public void setUighur(String Uighur) {
        this.Uighur = Uighur;
    }
    public String getChinise() {
        return this.Chinise;
    }
    public void setChinise(String Chinise) {
        this.Chinise = Chinise;
    }
    public Long getId() {
        return this.Id;
    }
    public void setId(Long Id) {
        this.Id = Id;
    }
    @Generated(hash = 935383423)
    public Translate(Long Id, String Chinise, String Uighur) {
        this.Id = Id;
        this.Chinise = Chinise;
        this.Uighur = Uighur;
    }
    @Generated(hash = 1753315822)
    public Translate() {
    }
}
