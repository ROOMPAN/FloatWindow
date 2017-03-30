package com.android;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;

import com.android.permission.AVCallFloatView;
import com.android.permission.MessageWindow;

/**
 * Created by liupanpan on 2017/3/6.
 */

public class MessageWindowManager {
    private static final String TAG = "FloatWindowManager";

    private static volatile MessageWindowManager instance;
    private MessageWindow messageWindow;

    private boolean isWindowDismiss = true;
    private WindowManager windowManager = null;
    private WindowManager.LayoutParams mParams = null;
    private AVCallFloatView floatView = null;

    public static MessageWindowManager getInstance() {
        if (instance == null) {
            synchronized (MessageWindowManager.class) {
                if (instance == null) {
                    instance = new MessageWindowManager();
                }
            }
        }
        return instance;
    }


    public void applyOrShowFloatWindow(Context context) {
//        if (isWindowDismiss) {
//            showWindow(context);
//        } else {
//            dismissWindow();
//        }
        showWindow(context);
    }

    private void showWindow(Context context) {
        if (!isWindowDismiss) {
            Log.e(TAG, "view is already added here");
            return;
        }

        isWindowDismiss = false;
        if (windowManager == null) {
            windowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        }

        Point size = new Point();
        windowManager.getDefaultDisplay().getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;

        mParams = new WindowManager.LayoutParams();
        mParams.packageName = context.getPackageName();
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR | WindowManager.LayoutParams.TYPE_PHONE;
        mParams.format = PixelFormat.RGBA_8888;
        mParams.gravity = Gravity.LEFT | Gravity.TOP;
        mParams.x = screenWidth - dp2px(context, 320);
        mParams.y = screenHeight - dp2px(context, 550);


//        ImageView imageView = new ImageView(mContext);
//        imageView.setImageResource(R.drawable.app_icon);
//        floatView.setParams(mParams);
//        floatView.setIsShowing(true);

        messageWindow = new MessageWindow(context);
//        messageWindow.setParams(mParams);
        messageWindow.setLayoutParams(mParams);
        messageWindow.setIsShowing(true);
//        AVCallFloatView avCallFloatView = new AVCallFloatView(context);
//        avCallFloatView.setIsMessShowing(true);
        windowManager.addView(messageWindow, mParams);
//        floatView.setViewpager();
    }

    public void dismissWindow() {
        if (isWindowDismiss) {
            Log.e(TAG, "window can not be dismiss cause it has not been added");
            return;
        }

        isWindowDismiss = true;
        messageWindow.setIsShowing(false);
        if (windowManager != null && messageWindow != null) {
            windowManager.removeViewImmediate(messageWindow);
        }
    }

    private int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
