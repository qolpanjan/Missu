package com.missu.Activitys;

import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.hss01248.notifyutil.NotifyUtil;
import com.missu.Adapter.ChatMessageAdapter;
import com.missu.Bean.DaoSession;
import com.missu.Bean.Message;
import com.missu.Bean.MessageDao;
import com.missu.Bean.MessageListBean;
import com.missu.Bean.MessageListBeanDao;
import com.missu.Bean.QQMessage;;
import com.missu.Bean.QQMessageType;
import com.missu.R;
import com.missu.Util.MyTime;
import com.missu.Util.QQConnection;
import com.missu.Util.ThreadUtils;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import java.util.List;


import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;


public class ChatActivity extends AppCompatActivity{


	private ListView listView;
	private EditText input;
	private ImApp app;
    private DaoSession daoSession;
	private ChatMessageAdapter adapter;

	private String toNick;
	private String toAccount;
	private String fromAccount;
	private String inputStr;
	private String avater;
    int  listener_num=0;
    boolean inform;
    /**
     * 关于翻译的一下变量
     */
    String host = "http://niutrans1.market.alicloudapi.com";
    String path = "/NiuTransServer/translation";
    String method = "GET";
    String appcode = "c053e2a948d94503824a2c9a4a3316af";//API提供的APPCODE

    private LocalBroadcastManager localBroadcastManager;
	// Collection of the QQmessage
	private List<MessageListBean> messages = new ArrayList<MessageListBean>();


	//get the editText content and put it in the collection QQmessage Bean should include for character;

	public void send(View view) {
		// Toast.makeText(getBaseContext(), "ddadasf", 0).show();
		inputStr = input.getText().toString().trim();
		// clear
		input.setText("");
		final QQMessage msg = new QQMessage();
		if ("".equals(inputStr)) {
			Toast.makeText(getApplicationContext(), "不能为空", Toast.LENGTH_SHORT).show();
			return;
		}

		msg.type = QQMessageType.MSG_TYPE_CHAT_P2P;
		msg.from = fromAccount;
		msg.to = toAccount;
		msg.content = inputStr;
		msg.fromAvatar = R.mipmap.icon + "";

		//MessageListBean msgbean = new MessageListBean(null,app.getMyAccount(),app.getNick(),app.getAvater(),"to", MyTime.geTime(),"",inputStr);
        MessageListBean msgbean = new MessageListBean(null,toAccount,toNick,avater,"to", msg.getSendTime(),"",inputStr);

		messages.add(msgbean);
		daoSession.getMessageListBeanDao().insert(msgbean);
        Message message = daoSession.getMessageDao().queryBuilder().where(MessageDao.Properties.Account.eq(toAccount)).unique();
        if (message!=null){
            Log.e("DELETED",message.getContent());
            daoSession.getMessageDao().delete(message);
            Message message1 = new Message(null,toAccount,avater,msg.getSendTime(),inputStr,toNick);
            Log.e("ADDED",message1.getContent());
            Long result = daoSession.getMessageDao().insert(message1);
        }else if (message==null){
            Message message1 = new Message(null,toAccount,avater,msg.getSendTime(),inputStr,toNick);
            Log.e("ADDED",message1.getContent());
            Long result = daoSession.getMessageDao().insert(message1);
            Log.e("RESULT",result+"");
            Intent intent = new Intent("com.missu.new_chat.ADD");
            localBroadcastManager = LocalBroadcastManager.getInstance(ChatActivity.this);
            localBroadcastManager.sendBroadcast(intent);
        }

        //daoSession.getQQMessageDao().insert(msg);

		if (adapter != null) {
			adapter.notifyDataSetChanged();
		}


		if (messages.size() > 0) {
			listView.setSelection(messages.size() - 1);
		}

		//send to server do it in sub Thread
		ThreadUtils.runInSubThread(new Runnable() {

			public void run() {
				try {
					app.getMyConn().sendMessage(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Receiver listen fot the QQmessage from server
	 */
	private QQConnection.OnMessageListener listener = new QQConnection.OnMessageListener() {

		public void onReveive(final QQMessage msg) {
			// OnReceive is in the SubThread but do it in UI Thread
			ThreadUtils.runInUiThread(new Runnable() {

				public void run() {

					//message from the Srver
					Log.e("OnREceive",msg.content);
					if (QQMessageType.MSG_TYPE_CHAT_P2P.equals(msg.type)) {
                        //新消息
                        if (msg.from.equals(toAccount)){

							MessageListBean msgbean = new MessageListBean(null,toAccount,toNick,avater,"from", MyTime.geTime(),"",msg.content);

                            messages.add(msgbean);// 把消息加到消息集合中，这是最新的消息

							daoSession.getMessageListBeanDao().insert(msgbean);

                            Message message = daoSession.getMessageDao().queryBuilder().where(MessageDao.Properties.Account.eq(toAccount)).unique();
                            if (message!=null){
                                daoSession.getMessageDao().delete(message);
                            }else if (message==null){
                                Message message1 = new Message(null,toAccount,avater,msg.getSendTime(),msg.getContent(),toNick);
                                daoSession.getMessageDao().insert(message1);
                                Intent intent = new Intent("com.missu.new_chat.ADD");
                                localBroadcastManager = LocalBroadcastManager.getInstance(ChatActivity.this);
                                localBroadcastManager.sendBroadcast(intent);
                            }


                            Intent intent = new Intent();
                            intent.setClass(getBaseContext(),ChatActivity.class);
                            intent.putExtra("account",msg.from);
                            intent.putExtra("nick",msg.fromNick);
                            intent.putExtra("avater",msg.fromAvatar);
                            intent.putExtra("content",msg.content);
                            intent.putExtra("to",msg.to);
                            intent.putExtra("time",msg.sendTime);

                            if (app.isInfrom_new_Message()&&inform){
                                PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),0,intent,FLAG_UPDATE_CURRENT);
                                NotifyUtil.buildMailBox(104,R.mipmap.logo,msg.from)
                                        .addMsg(msg.content)
                                        .setAction(app.isInfrom_sound(),app.isInform_vibrate(),true)
                                        .setContentIntent(pendingIntent)
                                        .setTicker(getString(R.string.new_message))
                                        .show();
                            }
                                Intent mintent = new Intent("com.missu.new_chat.ADD");
                                mintent.putExtra("account",toAccount);
                                mintent.putExtra("nick",toNick);
                                mintent.putExtra("content",msg.getContent());
                                mintent.putExtra("time",msg.getSendTime());
                                localBroadcastManager = LocalBroadcastManager.getInstance(ChatActivity.this);
                                localBroadcastManager.sendBroadcast(mintent);


                            if (adapter != null) {
                                adapter.notifyDataSetChanged();//跟新前检查适配器
                            }
                            // 展示到最新发送的消息出
                            if (messages.size() > 0) {
                                listView.setSelection(messages.size() - 1);
                            }
                        }else{//新朋友
                                MessageListBean msgbean = new MessageListBean(null,toAccount,toNick,avater,"to", msg.getSendTime(),"",inputStr);
                                daoSession.getMessageListBeanDao().insert(msgbean);

                                Message message1 = new Message(null,msg.from,msg.fromAvatar,msg.getSendTime(),msg.getContent(),msg.fromNick);
                                daoSession.getMessageDao().insert(message1);
                                Intent intent2 = new Intent("com.missu.new_chat.ADD");
                                localBroadcastManager = LocalBroadcastManager.getInstance(ChatActivity.this);
                                localBroadcastManager.sendBroadcast(intent2);

                                Intent intent = new Intent();
                                intent.setClass(getBaseContext(),ChatActivity.class);
                                intent.putExtra("account",msg.from);
                                intent.putExtra("nick",msg.fromNick);
                                intent.putExtra("avater",msg.fromAvatar);
                                intent.putExtra("content",msg.content);
                                intent.putExtra("to",msg.to);
                                intent.putExtra("time",msg.sendTime);

                                if (app.isInfrom_new_Message()&&inform) {
                                    PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, FLAG_UPDATE_CURRENT);
                                    NotifyUtil.buildMailBox(104, R.mipmap.ic_launcher, msg.from)
                                            .addMsg(msg.content)
                                            .setContentIntent(pendingIntent)
                                            .setTicker(getString(R.string.new_friend))
                                            .show();
                                }
                                Intent intent1 = new Intent("com.missu.ADD_SUCCES");
                                intent1.putExtra("account",msg.from);
                                intent1.putExtra("nick", msg.fromNick);
                                intent1.putExtra("avater", msg.fromAvatar);
                                intent1.putExtra("asked","addfriends");
                                intent1.putExtra("sex","true");
                                localBroadcastManager = LocalBroadcastManager.getInstance(ChatActivity.this);
                                localBroadcastManager.sendBroadcast(intent1);//new friend broadcast
                            }
                        }

				}
			});

		}
	};



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chart);
        //app.getMyConn().removeOnMessageListener(listener);
         inform =false;
		app = (ImApp) getApplication();
        daoSession = app.getDaoSession();
        IntentFilter intentFilter = new IntentFilter();

		// 开启监听
        NotifyUtil.init(getApplicationContext());
        if (listener_num==0){
            app.getMyConn().addOnMessageListener(listener);
            listener_num++;
        }

        Log.e("OnCreate",""+listener_num);


		// 聊天的界面是复杂的listView。发送消息的条目是item_chat_send.xml布局，接收到的消息现实的条目是item_chat_receive.xml布局
		/**
		 * 聊天的消息 content "约不？" type chatp2p from 老王 to 大头
		 */
		// 获得从上一个界面获取的账号与昵称
		Intent intent = getIntent();
		toNick = intent.getStringExtra("nick");
		toAccount = intent.getStringExtra("account");
        avater = intent.getStringExtra("avater");

        //title =(TextView) findViewById(R.id.title);
        listView = (ListView)findViewById(R.id.listview);
        input = (EditText)findViewById(R.id.input);

		setTitle("  "+toNick );
		fromAccount = app.getMyAccount();// 我的账户
        messages.clear();
        messages = daoSession.getMessageListBeanDao().queryBuilder().where(MessageListBeanDao.Properties.Other_Account.eq(toAccount)).build().list();
        Log.e("CHAT",messages.size()+"");
        if (messages==null||messages.size()==0){
            MessageListBean messageListBean2 = new MessageListBean(null,"MISSU","MISSU团队","0","from",MyTime.geTime(),"", getString(R.string.welcometouse));
            daoSession.getMessageDao().insert(new Message(null,"MISSU","0", MyTime.geTime(),getString(R.string.welcometouse),"MISSU团队"));
            daoSession.getMessageListBeanDao().insert(messageListBean2);
            Log.e("CHAT",messages.size()+"");
        }
        messages = daoSession.getMessageListBeanDao().queryBuilder().where(MessageListBeanDao.Properties.Other_Account.eq(toAccount)).build().list();
		adapter = new ChatMessageAdapter(this, messages);
		listView.setAdapter(adapter);
        listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.add(0,0,0,getString(R.string.translate));
                contextMenu.add(0,1,0,getString(R.string.delete));
                contextMenu.add(0,2,0,getString(R.string.copy));
            }
        });
        if (messages.size() > 0) {
            listView.setSelection(messages.size() - 1);
        }
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){
            case 0://第一项操作
                final int MID = (int) info.position;
                MessageListBean message = messages.get(info.position);
                Toast.makeText(ChatActivity.this,getString(R.string.translating),Toast.LENGTH_SHORT).show();
                final String s_text = message.getContent();
                String src_text = null;
                try {
                    src_text  = URLEncoder.encode(message.getContent(),"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();

                }
                app.mMyOkhttp.get().url(host+path).addParam("from", "zh").addParam("src_text", src_text).addParam("to", "uy")
                        .addHeader("Authorization","APPCODE " + appcode).enqueue(new JsonResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                                Toast.makeText(ChatActivity.this,"Failure",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        super.onSuccess(statusCode, response);
                        String text = null;
                        try {
                            text =  response.getString("tgt_text");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("RESPONCE",response.toString());
                        MessageListBean message = messages.get(MID);
                        message.setContent(s_text+"\n"+text);
                        adapter.notifyDataSetChanged();
                    }
                });
                break;
            case 1:
                MessageListBean messagebean = messages.get(info.position);
                daoSession.getMessageListBeanDao().delete(messagebean);
                messages.remove(messagebean);
                adapter.notifyDataSetChanged();
                break;
            case 2:
                MessageListBean m = messages.get(info.position);
                ClipboardManager myClipboard;
                myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                ClipData myClip;
                String text = m.getContent() ;
                myClip = ClipData.newPlainText("text", text);
                myClipboard.setPrimaryClip(myClip);
                Toast.makeText(ChatActivity.this,getString(R.string.copied),Toast.LENGTH_SHORT).show();
                break;
            default:
                break;

        }
        return super.onContextItemSelected(item);
    }


    @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
        inform =true;
        Log.e("OnDestry",inform+"");
		//app.getMyConn().removeOnMessageListener(listener);
	}



}
