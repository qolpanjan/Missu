package com.missu.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.missu.Bean.DaoSession;
import com.missu.Bean.Users;
import com.missu.R;

import java.io.IOException;

import com.missu.Util.MyService;
import com.missu.Util.QQConnection;
import com.missu.Util.ThreadUtils;
import com.missu.Bean.QQMessage;
import com.missu.Bean.QQMessageType;


public class LoginActivity extends Activity {


	private AutoCompleteTextView account;

	private EditText password;

	private String accountStr;
	private String passwordStr;//
    ImApp app;
    ProgressBar progressBar;
    ScrollView Sview;

    QQConnection conn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
        account = (AutoCompleteTextView)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        progressBar =(ProgressBar) findViewById(R.id.login_progress);
        Sview = (ScrollView)findViewById(R.id.login_form);
        /*
		 * 登录的逻辑:首先连接服务器，获取用户输入的账号和密码，讲账户和密码发送给服务器;服务器做一些验证操作，将验证结果返回给客户端，
		 * 客户端再进行接收消息的操作// 与网络相关的操作要放在子线程中进行 */

		ThreadUtils.runInSubThread(new Runnable() {
			public void run() {
				try {
					conn = new QQConnection("115.159.109.131", 8090);// Socket
					conn.connect();
					//链接之后把链接保存在Application作为长连接
					app = (ImApp) getApplication();
					// 保存一个长连接
					app.setMyConn(conn);
					conn.addOnMessageListener(listener);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		conn.removeOnMessageListener(listener);
	}

    /**
     * 登录按钮点击事件
     * @param view
     */
	public void login(View view) {
		progressBar.setVisibility(View.VISIBLE);
        Sview.setVisibility(View.GONE);
		accountStr = account.getText().toString().trim();
		passwordStr = password.getText().toString().trim();
        Intent intent1 = new Intent(LoginActivity.this,MyService.class);
        startService(intent1);

		/**
		 * <QQMessage>
		 <content>alimjan#12345</content>
		 <from>0L</from>
		 <fromAvatar>1</fromAvatar>
		 <fromNick></fromNick>
		 <sendTime>05-01 13:02:27</sendTime>
		 <to>0L</to>
		 <type>login</type>
		 </QQMessage>
		 */

		//将密码和用户名封装成对象在线程发给服务器
		ThreadUtils.runInSubThread(new Runnable() {

			public void run() {
				try {
					QQMessage msg = new QQMessage();
					msg.type = QQMessageType.MSG_TYPE_LOGIN;
					msg.content = accountStr + "#" + passwordStr;
					conn.sendMessage(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

	}

	public void goRegister(View view){
        Intent intent = new Intent(LoginActivity.this,ResitActivity.class);
        startActivity(intent);
    }

	/**
	 * 登录成功之后返回好友列表 <QQMessage> <type>buddylist</type> <from>0</from>
	 */
	// 客户端这里要完成接收的逻辑
	// 使用接收消息的监听器
	private QQConnection.OnMessageListener listener = new QQConnection.OnMessageListener() {

		@Override
		public void onReveive(final QQMessage msg) {
			System.out.println(msg.toXML());
			// 接收服务器返回的结果.处理数据的显示,运行在主线程中
			ThreadUtils.runInUiThread(new Runnable() {

				public void run() {
					if (QQMessageType.MSG_TYPE_BUDDY_LIST.equals(msg.type)) {
						// 登录成功，返回的数据是好友列表
						// 有用的信息是content的json串
						Toast.makeText(getBaseContext(),R.string.toast_login_succes, Toast.LENGTH_SHORT).show();
						// 将连接conn保存到application中，作为一个长连接存在，这样在其他的activity，server中都能拿到这个连接，保证了项目连接的唯一性
						// 新建一个application类，给出get，set方法，使用application可以在整个项目中共享数据
						// 保存一个长连接
						//app.setMyConn(conn);
						// 保存好友列表的json串
                        String content = msg.content;
                        String[] buddyJson = content.split("#");
                        String[] userbuddy =buddyJson[1].split("!");
                        Log.e("userbuddy",accountStr);

                        Users users = new Users(null,accountStr,userbuddy[4],userbuddy[1],userbuddy[2],userbuddy[0]);
                        app.setMyAccount(accountStr);
                        app.setAvater(userbuddy[0]);
                        app.setNick(userbuddy[1]);
                        app.setSex(userbuddy[2]);
                        app.setId(userbuddy[3]);
                        app.setPassword(userbuddy[4]);
                        DaoSession daoSession = app.getDaoSession();
                        daoSession.getUsersDao().deleteAll();
                        daoSession.getUsersDao().insert(users);
						app.setBuddyListJson(buddyJson[0]);
						// 保存当前登录用户的账号
						app.setMyAccount(accountStr);
						// 打开主页
						Intent intent = new Intent();

						intent.setClass(LoginActivity.this, MyActivity.class);
						intent.putExtra("account", accountStr);
						startActivity(intent);
                        finish();

					} else if (QQMessageType.MSG_TYPE_FAILURE.equals(msg.type)) {
                            progressBar.setVisibility(View.GONE);
                            Sview.setVisibility(View.VISIBLE);
                            Toast.makeText(getBaseContext(),R.string.toast_login_error, Toast.LENGTH_SHORT).show();
                    }else if(QQMessageType.MSG_TYPE_WRONGPASS.equals(msg.type)){
                            progressBar.setVisibility(View.GONE);
                            Sview.setVisibility(View.VISIBLE);
                            Toast.makeText(getBaseContext(),R.string.toas_pass_wrong, Toast.LENGTH_SHORT).show();
                    }
				}
			});

		}
	};




}
