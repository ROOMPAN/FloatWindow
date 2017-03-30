/*
 * Copyright (C) 2016 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.android.floatwindowpermission;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import com.android.permission.FloatWindowManager;

/**
 * Description:
 *
 * @author zhaozp
 * @since 2016-10-17
 */

public class FloatWindowActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_show_or_apply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatWindowManager.getInstance().applyOrShowFloatWindow(FloatWindowActivity.this);
            }
        });

        findViewById(R.id.btn_dismiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatWindowManager.getInstance().dismissWindow();
            }
        });
    }

    void startAsyncTask() {
        // This async task is an anonymous class and therefore has a hidden reference to the outer
        // class MainActivity. If the activity gets destroyed before the task finishes (e.g. rotation),
        // the activity instance will leak.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                // Do some slow work in background
                SystemClock.sleep(20000);
                return null;
            }
        }.execute();
    }

}
