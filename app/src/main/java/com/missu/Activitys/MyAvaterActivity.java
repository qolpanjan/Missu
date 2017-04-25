package com.missu.Activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.anye.greendao.gen.DaoSession;
import com.anye.greendao.gen.UsersDao;
import com.bumptech.glide.Glide;
import com.missu.Adapter.MyApplication;
import com.missu.Bean.Users;
import com.missu.R;

import java.io.File;

public class MyAvaterActivity extends AppCompatActivity {
    ImageView myAvater;
    Users users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_avater);
        final DaoSession daoSession = MyApplication.getInstances().getDaoSession();
        Users users = daoSession.getUsersDao().queryBuilder().where(UsersDao.Properties.User_name.eq(MainActivity.USERNAME)).build().unique();
        Log.e("MainActivity",users.getUser_name());
        myAvater = (ImageView)findViewById(R.id.img_my_avater);
        if (users.getUser_profile() != null && !users.getUser_profile().equals("")){
            Glide.with(MyAvaterActivity.this).load(users.getUser_profile()).placeholder(R.mipmap.icon).into(myAvater);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.myavater,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_choose_from_gallery:
                Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 103);
                break;
            case R.id.menu_take_photo:
                String imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+users.getUser_name()+".jpg";
                File temp = new File(imageFilePath);
                Uri imageFileUri = Uri.fromFile(temp);//获取文件的Uri
                Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//跳转到相机Activity
                it.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageFileUri);//告诉相机拍摄完毕输出图片到指定的Uri
                startActivityForResult(it, 102);
                break;
        }
        return false;
    }
}
