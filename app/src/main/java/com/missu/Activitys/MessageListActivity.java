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
import com.google.gson.Gson;
import com.missu.Adapter.MessageListAdapter;
import com.missu.Adapter.MyApplication;
import com.missu.Bean.Message;
import com.missu.Bean.Translate;
import com.missu.Fragment.ChatListFragment;
import com.missu.R;

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
    private List<Message> msgList = new ArrayList<>();
    Request.Builder builder = new Request.Builder();
    DaoSession daoSession;
    Bundle bundle;
    //你好

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_list);
        bundle = savedInstanceState;
        Intent intent = getIntent();
        String chattingfriendname = intent.getStringExtra(CHATTINGFRIEND);
        setTitle(chattingfriendname);
        Resources rec= getResources();
        bmp1 = BitmapFactory.decodeResource(rec,R.mipmap.icon);
        daoSession = MyApplication.getInstances().getDaoSession();
        initMsgs();

        adapter = new MessageListAdapter(this,R.layout.message_item,msgList);
        inputMsg = (EditText)findViewById(R.id.et_messagelist);
        msgSendBtn = (Button)findViewById(R.id.btn_send_message);
        MsgListView = (ListView)findViewById(R.id.lv_messagelist);
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

                    Message message = new Message(null,TYPE_SEND,time,content,"R.mipmap.icon",MainActivity.USERNAME,"",null,0);
                    daoSession.getMessageDao().insert(message);

//                    Message msg = new Message();
//                    msg.setMessg_content(content);
//                    msg.setMessg_type(TYPE_SEND);
//                    msg.setMessg_from_username("Alimzhan");
                    msgList.add(message);
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
                Message message = msgList.get(info.position);
                getTranslate(message);

                return true;
            case 1:
                //System.out.println("删除"+info.id);

                daoSession.getMessageDao().delete( msgList.get(info.position) );
                daoSession.getTranslateDao().delete(msgList.get(info.position).getTranslate());
                msgList.remove(info.position);
                adapter.notifyDataSetChanged();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    private void initMsgs() {

        List<Message> messages = daoSession.getMessageDao().loadAll();
        for (Message message:messages){
            Log.e("INITMSG",message.getMessg_content());
            msgList.add(message);
        }


//        Message msg=new Message();
//        msg.setMessg_type(TYPE_RECEIVED);
//        msg.setMessg_from_profile("R.mipmap.ic_launcher");
//        msg.setMessg_content("阿力木江你好，祝你妇女节快乐");
//        msg.setMessg_from_username("马化腾");
//        msgList.add(msg);

    }

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
}
