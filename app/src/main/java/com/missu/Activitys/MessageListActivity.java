package com.missu.Activitys;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.anye.greendao.gen.DaoSession;
import com.anye.greendao.gen.TranslateDao;
import com.anye.greendao.gen.UsersDao;
import com.google.gson.Gson;
import com.missu.Adapter.MessageListAdapter;
import com.missu.Adapter.MyApplication;
import com.missu.Bean.Friends;
import com.missu.Bean.Message;
import com.missu.Bean.MessageBean;
import com.missu.Bean.MessageType;
import com.missu.Bean.Translate;
import com.missu.Bean.Users;
import com.missu.Fragment.ChatListFragment;
import com.missu.R;
import com.missu.Utils.Mytime;
import com.missu.Utils.NetConnection;
import com.missu.Utils.ThreadUtils;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.missu.Fragment.ChatListFragment.CHATTINGFRIEND;
import static java.security.AccessController.getContext;

/**
 * 详细聊天界面
 * Created by alimj on 2017/3/9.
 */

public class MessageListActivity extends AppCompatActivity {


    public final String URL = "http://192.168.1.110/ttest.php?chinise=";
    OkHttpClient okHttpClient = new OkHttpClient();

    public static final String TYPE_RECEIVED = "0";
    public static final String TYPE_SEND = "1";
    public static final String DBNAME = "missu.db";
    public static Bitmap bmp1;

    private ListView MsgListView;
    private EditText inputMsg;
    private Button msgSendBtn;
    private MessageListAdapter adapter;
    private List<MessageBean> msgList = new ArrayList<>();
    Request.Builder builder = new Request.Builder();
    DaoSession daoSession;
    Bundle bundle;
    String account = "";
    String nick = "";
    String avater = "";
    //你好

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_list);
        MsgListView = (ListView)findViewById(R.id.lv_messagelist);

        adapter = new MessageListAdapter(this,R.layout.message_item,msgList);
        bundle = savedInstanceState;
        Intent intent = getIntent();
        MessageBean msg;

        try{
           msg  = (MessageBean) intent.getSerializableExtra("msg");
            account = intent.getStringExtra("account");
            nick = intent.getStringExtra("nick");
            avater = intent.getStringExtra("avater");
            if(msg!=null){
                msgList.add(msg);
            }

            if (msgList.size() > 0) {
                MsgListView.setSelection(msgList.size() - 1);
            }
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }

        }catch (Exception e){

        }


         // 把消息加到消息集合中，这是最新的消息
            // 刷新消息

            // 展示到最新发送的消息出


        setTitle("与"+nick+"聊天");
        Resources rec= getResources();
        bmp1 = BitmapFactory.decodeResource(rec,R.mipmap.icon);

        daoSession = MyApplication.getInstances().getDaoSession();
        initMsgs();


        inputMsg = (EditText)findViewById(R.id.et_messagelist);
        msgSendBtn = (Button)findViewById(R.id.btn_send_message);

        MsgListView.setAdapter(adapter);
        MsgListView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                //menu.setHeaderTitle("选择操作");
                menu.add(0, 0, 0, "翻译");
                menu.add(0, 1, 0, "删除");

            }
        });
        Request.Builder builder = new Request.Builder();


        msgSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputMsg.getText().toString();
                if (!"".equals(content)){
                    SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日 HH:mm:ss ");
                    Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                    String time = formatter.format(curDate);

                    //Message message = new Message(null,TYPE_SEND,time,content,"R.mipmap.icon",MainActivity.USERNAME,"",0);
                    //Translate translate = new Translate(null,content,"This is trianslate");
                    //daoSession.getTranslateDao().insert(translate);
                    //Translate unique = daoSession.getTranslateDao().queryBuilder().where(TranslateDao.Properties.Chinise.eq(content)).unique();
                    Users unique = daoSession.getUsersDao().queryBuilder().where(UsersDao.Properties.User_name.eq(MainActivity.USERNAME)).unique();
                    final MessageBean msgbean = new MessageBean(MessageType.MSG_TYPE_CHAT_P2P,MainActivity.USERNAME,account,content, Mytime.geTime(),unique.getUser_profile());

                    daoSession.getMessageBeanDao().insert(msgbean);


                    ThreadUtils.runInSubThread(new Runnable() {
                        @Override
                        public void run() {


                            MyApplication app  = (MyApplication) getApplication();
                            NetConnection conn = app.getMyConn();
                            try{
                                if (conn !=null){
                                    conn.connect();
                                    conn.sendMessage(msgbean);
                                    Log.e("Content",msgbean.getContent());
                                }else{
                                    conn = new NetConnection(MyApplication.IP,8090);
                                    conn.connect();
                                    conn.sendMessage(msgbean);
                                    Log.e("Content+new",msgbean.getContent());
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                                Log.e("Content+ERR",msgbean.getContent());
                            }
                        }
                    });



                    msgList.add(msgbean);
                    adapter.notifyDataSetChanged();
                    MsgListView.setSelection(msgList.size());
                    inputMsg.setText("");
                }
            }
        });

    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        //info.id得到listview中选择的条目绑定的id
        String id = String.valueOf(info.id);
        switch (item.getItemId()) {
            case 0:
                MessageBean message = msgList.get(info.position);
                //getTranslate(message);

                return true;
            case 1:
                //System.out.println("删除"+info.id);

                daoSession.getMessageBeanDao().delete( msgList.get(info.position) );
                //daoSession.getTranslateDao().delete(msgList.get(info.position).getTranslate());
                msgList.remove(info.position);
                adapter.notifyDataSetChanged();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    private void initMsgs() {

        List<MessageBean> messages = daoSession.getMessageBeanDao().loadAll();
        for (MessageBean message:messages){
            Log.e("INITMSG",message.getContent());
            msgList.add(message);
        }


//        Message msg=new Message();
//        msg.setMessg_type(TYPE_RECEIVED);
//        msg.setMessg_from_profile("R.mipmap.ic_launcher");
//        msg.setMessg_content("阿力木江你好，祝你妇女节快乐");
//        msg.setMessg_from_username("马化腾");
//        msgList.add(msg);

    }

    private NetConnection.OnMessageListener listener = new NetConnection.OnMessageListener() {

        public void onReveive(final MessageBean msg) {
            // 注意onReveive是子线程，更新界面一定要在主线程中
            ThreadUtils.runInUiThread(new Runnable() {

                public void run() {

                    // 服务器返回回来的消息
                    System.out.println(msg.getContent());
                    if (MessageType.MSG_TYPE_CHAT_P2P.equals(msg.getType())) {
                        msgList.add(msg);// 把消息加到消息集合中，这是最新的消息
                        // 刷新消息
                        if (adapter != null) {
                            adapter.notifyDataSetChanged();
                        }
                        // 展示到最新发送的消息出
                        if (msgList.size() > 0) {
                            MsgListView.setSelection(msgList.size() - 1);
                        }

                    }

                }
            });

        }
    };

    /**

    public Message getTranslate(final Message message) {
       String url = URL + message.getMessg_content();
        final Request request = new Request.Builder().url(url.trim()).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(MessageListActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String reponse = response.body().string();
                Gson gson = new Gson();
                Log.e("RESPONSE",reponse);
                Translate translate =gson.fromJson(reponse,Translate.class);

                daoSession.getTranslateDao().insert(translate);

                //Log.e("TAG",translate.getUighur());
                Translate unique = daoSession.getTranslateDao().queryBuilder().where(TranslateDao.Properties.Chinise.eq(translate.getChinise())).unique();

                message.setTranslate(unique);
                daoSession.getMessageDao().update(message);

                //for(int i=0;i<msgList.size();i++){
                //    msgList.remove(i);
                //}
                //initMsgs();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();

                    }
                });


            }
        });
        return message;
    }

     */
}
