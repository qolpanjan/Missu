package com.missu.Activitys;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.github.yoojia.qrcode.qrcode.QRCodeEncoder;
import com.missu.R;

public class QRActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        setTitle("我的二维码");
        ImApp app = (ImApp) getApplication();
        ImageView qr = (ImageView)findViewById(R.id.qr);

        final Bitmap centerImage = BitmapFactory.decodeResource(getResources(), R.mipmap.icon);
        Bitmap qrImage = new QRCodeEncoder.Builder().width(1000).height(1000).paddingPx(50).marginPt(0).centerImage(centerImage).build().encode(app.getMyAccount());
        ///Drawable drawable =new BitmapDrawable(qrImage);
        qr.setImageBitmap(qrImage);
    }
}
