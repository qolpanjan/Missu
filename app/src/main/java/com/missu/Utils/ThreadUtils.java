package com.missu.Utils;

import android.os.Handler;

/**
 * Created by alimj on 2017/4/21.
 */

public class ThreadUtils {


    private static Handler handler = new Handler();

    /**
     * 运行在子线程(发送数据)
     *
     * @param r
     */
    public static void runInSubThread(Runnable r) {
        new Thread(r).start();
    }



    /**
     * 运行在主线程(UI 线程 更新界面)
     *
     * @param r
     */
    public static void runInUiThread(Runnable r) {
        handler.post(r);// Message-->handler.sendMessage-->handleMessage()
        // 主线程-->r.run();
    }
}
