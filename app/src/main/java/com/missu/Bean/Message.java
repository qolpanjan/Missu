package com.missu.Bean;

import android.graphics.Bitmap;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.anye.greendao.gen.DaoSession;
import com.anye.greendao.gen.MessageDao;
import com.anye.greendao.gen.TranslateDao;

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
    private Long translateId;

    @ToOne(joinProperty="translateId")
    private Translate translate;
    @Property
    private int messg_state;
    @Generated(hash = 387954614)
    private transient Long translate__resolvedKey;
    /** Used for active entity operations. */
    @Generated(hash = 859287859)
    private transient MessageDao myDao;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
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
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1699625296)
    public void setTranslate(Translate translate) {
        synchronized (this) {
            this.translate = translate;
            translateId = translate == null ? null : translate.getId();
            translate__resolvedKey = translateId;
        }
    }
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 561132888)
    public Translate getTranslate() {
        Long __key = this.translateId;
        if (translate__resolvedKey == null || !translate__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TranslateDao targetDao = daoSession.getTranslateDao();
            Translate translateNew = targetDao.load(__key);
            synchronized (this) {
                translate = translateNew;
                translate__resolvedKey = __key;
            }
        }
        return translate;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 747015224)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getMessageDao() : null;
    }
    public Long getTranslateId() {
        return this.translateId;
    }
    public void setTranslateId(Long translateId) {
        this.translateId = translateId;
    }
    @Generated(hash = 723907757)
    public Message(Long Id, String messg_type, String messg_time,
            String messg_content, String messg_from_profile,
            String messg_from_username, String messg_translation, Long translateId,
            int messg_state) {
        this.Id = Id;
        this.messg_type = messg_type;
        this.messg_time = messg_time;
        this.messg_content = messg_content;
        this.messg_from_profile = messg_from_profile;
        this.messg_from_username = messg_from_username;
        this.messg_translation = messg_translation;
        this.translateId = translateId;
        this.messg_state = messg_state;
    }
    @Generated(hash = 637306882)
    public Message() {
    }








}
