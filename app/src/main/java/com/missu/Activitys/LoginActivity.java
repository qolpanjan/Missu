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
		 * ��¼���߼�:�������ӷ���������ȡ�û�������˺ź����룬���˻������뷢�͸�������;��������һЩ��֤����������֤������ظ��ͻ��ˣ�
		 * �ͻ����ٽ��н�����Ϣ�Ĳ���// ��������صĲ���Ҫ�������߳��н��� */

		ThreadUtils.runInSubThread(new Runnable() {
			public void run() {
				try {
					conn = new QQConnection("115.159.109.131", 8090);// Socket
					conn.connect();
					//����֮������ӱ�����Application��Ϊ������
					app = (ImApp) getApplication();
					// ����һ��������
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
     * ��¼��ť����¼�
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

		//��������û�����װ�ɶ������̷߳���������
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
	 * ��¼�ɹ�֮�󷵻غ����б� <QQMessage> <type>buddylist</type> <from>0</from>
	 */
	// �ͻ�������Ҫ��ɽ��յ��߼�
	// ʹ�ý�����Ϣ�ļ�����
	private QQConnection.OnMessageListener listener = new QQConnection.OnMessageListener() {

		@Override
		public void onReveive(final QQMessage msg) {
			System.out.println(msg.toXML());
			// ���շ��������صĽ��.�������ݵ���ʾ,���������߳���
			ThreadUtils.runInUiThread(new Runnable() {

				public void run() {
					if (QQMessageType.MSG_TYPE_BUDDY_LIST.equals(msg.type)) {
						// ��¼�ɹ������ص������Ǻ����б�
						// ���õ���Ϣ��content��json��
						Toast.makeText(getBaseContext(),R.string.toast_login_succes, Toast.LENGTH_SHORT).show();
						// ������conn���浽application�У���Ϊһ�������Ӵ��ڣ�������������activity��server�ж����õ�������ӣ���֤����Ŀ���ӵ�Ψһ��
						// �½�һ��application�࣬����get��set������ʹ��application������������Ŀ�й�������
						// ����һ��������
						//app.setMyConn(conn);
						// ��������б��json��
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
						// ���浱ǰ��¼�û����˺�
						app.setMyAccount(accountStr);
						// ����ҳ
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
