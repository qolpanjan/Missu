package com.missu.Adapter;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.anye.greendao.gen.DaoMaster;
import com.anye.greendao.gen.DaoSession;


/**
 * Created by alimj on 2017/3/14.
 */

public class MyApplication extends Application {
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    public static MyApplication instances;


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
