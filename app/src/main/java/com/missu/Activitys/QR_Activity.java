package com.missu.Activitys;

import android.app.Activity;
import android.os.Bundle;

import com.missu.R;


public class QR_Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_);
        setTitle(getString(R.string.email_contact));
    }
}
