package com.missu.Activitys;

import android.app.Application;

import android.os.Environment;


import com.missu.Bean.DaoMaster;
import com.missu.Bean.DaoSession;
import com.missu.Util.QQConnection;
import com.tsy.sdk.myokhttp.MyOkHttp;

import org.greenrobot.greendao.database.Database;



public class ImApp extends Application {

	private QQConnection myConn;//Long connection

	private String myAccount;//my account

	private String buddyListJson;// buddylist Json

    private static DaoSession daoSession;
    public static final boolean ENCRYPTED = true;
    private DaoMaster.DevOpenHelper mHelper;

    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    public static ImApp instances;
    public static final String IP = "192.168.1.103";

    public boolean isInfrom_new_Message() {
        return infrom_new_Message;
    }

    public void setInfrom_new_Message(boolean infrom_new_Message) {
        this.infrom_new_Message = infrom_new_Message;
    }

    public boolean isInfrom_sound() {
        return infrom_sound;
    }

    public void setInfrom_sound(boolean infrom_sound) {
        this.infrom_sound = infrom_sound;
    }

    public boolean isInform_vibrate() {
        return inform_vibrate;
    }

    public void setInform_vibrate(boolean inform_vibrate) {
        this.inform_vibrate = inform_vibrate;
    }

    private boolean infrom_new_Message=true;
	private boolean infrom_sound = true;
	private boolean inform_vibrate = true;

	String downloadFileDir = Environment.getExternalStorageDirectory().getPath()+"/okHttp_download/";
	String cacheDir = Environment.getExternalStorageDirectory().getPath();


	public String getAvater() {
		return avater;
	}

	public void setAvater(String avater) {
		this.avater = avater;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String avater = "";

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;
	private String sex;
	private String nick;
	private String id;

    public int getAsked() {
        return asked;
    }

    public void setAsked(int asked) {
        this.asked = asked;
    }

    private int asked=0;

    public int getUnreadMsg() {
        return unreadMsg;
    }

    public void setUnreadMsg(int unreadMsg) {
        this.unreadMsg = unreadMsg;
    }

    private int unreadMsg = 0;

    MyOkHttp mMyOkhttp = new MyOkHttp();



	@Override
	public void onCreate() {
		super.onCreate();
        instances = this;

        /**
         * 获取GreenDao实例
         */
        init();
        /**
         *
         */
		if(getExternalCacheDir() != null){
			//缓存目录，APP卸载后会自动删除缓存数据
			cacheDir = getExternalCacheDir().getPath();
		}

        /**
         *
         */
        com.missu.View.Type.SetDefaultFont(this,"SERIF","ukij.ttf");


	}

	public void init(){
        mHelper = new DaoMaster.DevOpenHelper(this, "notes-db");
        Database db = mHelper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

    }

    public static DaoSession getDaoSession(){
        return daoSession;
    }

	public QQConnection getMyConn() {
		return myConn;
	}

	public void setMyConn(QQConnection myConn) {
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







}
