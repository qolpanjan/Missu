package com.missu.Activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anye.greendao.gen.DaoSession;
import com.anye.greendao.gen.UsersDao;
import com.google.gson.Gson;
import com.missu.Adapter.MyApplication;
import com.missu.Bean.ContactInfoList;
import com.missu.Bean.Friends;
import com.missu.Bean.MessageBean;
import com.missu.Bean.MessageType;
import com.missu.Bean.Users;
import com.missu.R;
import com.missu.Utils.NetConnection;
import com.missu.Utils.ThreadUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText login_user,login_password;
    Button login_go,login_sign;
    String user_name,user_pass;
    NetConnection conn;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.e("ACTIVITY","LOGIN");
        /**
		 * 登录的逻辑:首先连接服务器，获取用户输入的账号和密码，讲账户和密码发送给服务器;服务器做一些验证操作，将验证结果返回给客户端，
		 * 客户端再进行接收消息的操作
		 * 与网络相关的操作要放在子线程中进行
		 */
        initView();
        ThreadUtils.runInSubThread(new Runnable() {

            public void run() {
                try {
                    conn = new NetConnection(MyApplication.IP, 8090);// Socket
                    MyApplication app = (MyApplication) getApplication();
                    // 保存一个长连接
                    app.setMyConn(conn);
                    // 保存好友列表的json串
                    conn.connect();// 建立连接
                    // 建立连接之后，将监听器添加到连接里面

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
     * 初始化可视化控件
     */
    private void initView() {
        login_user = (EditText)findViewById(R.id.et_login_user);

        login_password = (EditText)findViewById(R.id.et_login_password);

        login_go = (Button)findViewById(R.id.btn_login_login);
        login_go.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            user_name = login_user.getText().toString();
                                            user_pass = login_password.getText().toString();
                                            if (user_name == null || user_name.equals("")) {
                                                Toast.makeText(LoginActivity.this, "账号不能为空！", Toast.LENGTH_SHORT).show();
                                            }
                                            if (user_pass == null || user_pass.equals("")) {
                                                Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                                            }

                                            ThreadUtils.runInSubThread(new Runnable() {

                                                public void run() {
                                                    try {
                                                        conn.addOnMessageListener(listener);
                                                        MessageBean msg = new MessageBean();
                                                        msg.setType(MessageType.MSG_TYPE_LOGIN);
                                                        msg.setContent(user_name + "#" + user_pass);
                                                        Log.e("MSG", msg.getContent());
                                                        conn.sendMessage(msg);
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            });
                                        }
                                    });

                /*
                 *
                 */

//                UsersDao usersDao = MyApplication.getInstances().getDaoSession().getUsersDao();
//                   List<Users> users = usersDao.loadAll();//获取User表的所有数据
//                    for (int i=0;i<users.size();i++) {//遍历从数据库表获取的所有数据
//                        if (users.get(i).getUser_name().equals(user_name)){//如果找到用户输入的ID
//                        //if (users.get(0).getUser_name().equals(user_name)) {
//                            if (users.get(i).getUser_password().equals(user_pass)) {//就会查找对应的密码，密码正确就会跳转
//                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                                /**
//                                 * 用sharedPreferences实现应用程序记住用户登录状态
//                                 */
//                                SharedPreferences.Editor editor = getSharedPreferences("login",MODE_PRIVATE).edit();
//                                editor.putBoolean("login",true);
//                                editor.putString("name",user_name);
//                                editor.commit();
//
//
//                                Toast.makeText(LoginActivity.this, "恭喜你，登录成功！", Toast.LENGTH_SHORT).show();
//                                startActivity(intent);
//                                finish();
//
//                                break;
//                            } else {//如果密码错误提示用户并且情况密码输入框
//                                Toast.makeText(LoginActivity.this, "密码输入错误，请重新输入", Toast.LENGTH_SHORT).show();
//                                login_password.setText("");
//                            }
//                        } else {//如果所有用户中找不到用户名，提示用户用户名不存在并且情况用户名输入框和密码输入框
//                            Toast.makeText(LoginActivity.this, "账户名不存在，请重新输入", Toast.LENGTH_SHORT).show();
//                            login_user.setText("");
//                            login_password.setText("");
//
//                        }
//                    }
//
//            }
//        });

                login_sign = (Button) findViewById(R.id.btn_login_sign);
                login_sign.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LoginActivity.this, SignInActivity.class);
                        startActivity(intent);

                    }
                });

            }


            private NetConnection.OnMessageListener listener = new NetConnection.OnMessageListener() {

                @Override
                public void onReveive(final MessageBean msg) {
                    System.out.println(msg.toXML());
                    // 接收服务器返回的结果.处理数据的显示,运行在主线程中
                    ThreadUtils.runInUiThread(new Runnable() {

                        public void run() {
                            if (MessageType.MSG_TYPE_LOGIN_SUCCESS.equals(msg.getType())) {
                                // 登录成功，返回的数据是好友列表
                                /**
                                 * <QQMessage>
                                 * <content>{&quot;buddyList&quot;:[{&quot;account
                                 * &quot;:101,&quot;nick&quot;:&quot;金莉
                                 * 1&quot;,&quot;avatar&quot;:0}]}</content>
                                 * <type>buddylist</type> <sendTime>02-26
                                 * 15:18:19</sendTime> <fromNick></fromNick>
                                 * <from>0</from> <to>0</to> <fromAvatar>1</fromAvatar>
                                 * </QQMessage>
                                 */
                                // 有用的信息是content的json串
                                progressDialog = new ProgressDialog(LoginActivity.this);
                                progressDialog.setMessage("正在载入···");
                                progressDialog.setCancelable(false);
                                progressDialog.show();


                                // 将连接conn保存到application中，作为一个长连接存在，这样在其他的activity，server中都能拿到这个连接，保证了项目连接的唯一性
                                // 新建一个application类，给出get，set方法，使用application可以在整个项目中共享数据
                                ThreadUtils.runInSubThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        MyApplication app = (MyApplication) getApplication();
                                        // 保存一个长连接
                                        //app.setMyConn(conn);
                                        // 保存好友列表的json串
                                        // app.setBuddyListJson(msg.getContent());
                                        // 保存当前登录用户的账号
                                        app.setMyAccount(user_name);
                                        //app.setPassword(user_pass);
                                        //Gson gson = new Gson();
                                        Log.e("MGS CONTENT",msg.getContent());
                                       // ContactInfoList contactInfoList = gson.fromJson(msg.getContent(), ContactInfoList.class);
                                        JSONArray jsonArray = null;
                                        try{
                                            JSONObject jsonObject = new JSONObject(msg.getContent());
                                            jsonArray = jsonObject.getJSONArray("List");
                                            for(int i=0;i<jsonArray.length();i++){
                                                JSONObject mylist = jsonArray.getJSONObject(i);

                                                DaoSession daoSession = app.getDaoSession();
                                                Friends friends = new Friends(mylist.getString("account"),mylist.getString("nick"),mylist.getString("avatar"),mylist.getString("sex"),mylist.getString("belongTo"));
                                                daoSession.getFriendsDao().insert(friends);
                                                Log.e("FRIENDS","SUCCES"+friends.getNick());
                                            }
                                        }catch (JSONException e){
                                            e.printStackTrace();
                                        }


                                        SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
                                        editor.putBoolean("login", true);
                                        editor.putString("name", user_name);
                                        editor.commit();
                                    }
                                });

                                // 打开主页
                                Log.e("LOGING", msg.getType());
                                Intent intent = new Intent();
                                intent.setClass(getBaseContext(), LoadingActicity.class);
                                intent.putExtra("account", user_name);
                                progressDialog.dismiss();
                                Toast.makeText(getBaseContext(), "登录成功 ！", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();

                            } else if (MessageType.MSG_TYPE_FAILURE.equals(msg.getType())) {
                                Toast.makeText(getBaseContext(), "账号或者密码错误，请检查重新输入", Toast.LENGTH_SHORT).show();
                            } else if (MessageType.MSG_TYPE_GETUSER_SUCCESS.equals(msg.getType())){
                                Log.e("MSG TYPE", msg.getType());

                            }else {
                                Toast.makeText(getBaseContext(), "网络链接错误", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }


            } ;
        }
