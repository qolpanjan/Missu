package com.missu.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.missu.R;

import com.missu.Util.ThreadUtils;



public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		ImApp app = (ImApp) getApplication();
		app.setAsked(0);

		/**
		 * 在子线程中做一些操作，之后跳转到登录界面
		 */
		ThreadUtils.runInSubThread(new Runnable() {

			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				startActivity(new Intent(getApplicationContext(), LoginActivity.class));
				finish();

			}
		});

	}

}
