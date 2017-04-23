package com.missu.Adapter;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.anye.greendao.gen.DaoMaster;
import com.anye.greendao.gen.DaoSession;
import com.missu.Bean.ContactInfoList;
import com.missu.Utils.NetConnection;


/**
 * Created by alimj on 2017/3/14.
 */

public class MyApplication extends Application {
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    public static MyApplication instances;

    public ContactInfoList getList() {
        return list;
    }

    public void setList(ContactInfoList list) {
        this.list = list;
    }

    /**
     * Soccket通讯
     */

    private ContactInfoList list;

    private NetConnection myConn;// 长连接

    private String myAccount;// 用户的登录账号
    private String myAvater;

    public String getMyAvater() {
        return myAvater;
    }

    public void setMyAvater(String myAvater) {
        this.myAvater = myAvater;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    private String password;
    private String nickName;
    private String sex;

    private String buddyListJson;// 好友列表的json串

    public NetConnection getMyConn() {
        return myConn;
    }

    public void setMyConn(NetConnection myConn) {
        this.myConn = myConn;
    }


    public String getMyAccount() {
        return myAccount;
    }

    public void setMyAccount(String myAccount) {
        this.myAccount = myAccount;
    }

    public String getBuddyListJson() {
        return buddyListJson;
    }

    public void setBuddyListJson(String buddyListJson) {
        this.buddyListJson = buddyListJson;
    }

    /**
     * 初始化函数，初始化GreenDao获取的函数
     */

    @Override
    public void onCreate() {
        super.onCreate();
        instances = this;
        setDatabase();

    }

    public static MyApplication getInstances() {
        return instances;
    }

    private void setDatabase() {
        mHelper = new DaoMaster.DevOpenHelper(getApplicationContext(),"chat.db",null);
        mDaoMaster = new DaoMaster(mHelper.getWritableDb());
        mDaoSession = mDaoMaster.newSession();

    }

    public DaoSession getDaoSession(){
        return mDaoSession;
    }

    public SQLiteDatabase getDB(){
        return mHelper.getWritableDatabase();
    }

}
