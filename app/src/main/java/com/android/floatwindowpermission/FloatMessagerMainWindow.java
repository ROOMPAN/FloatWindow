package com.android.floatwindowpermission;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.view.FloatMessagePopleDialog;

/**
 * Created by liupanpan on 2017/3/16.
 */

public class FloatMessagerMainWindow {
    private Context context;
    private View view;
    private WindowManager.LayoutParams mParams = null;
    private WindowManager windowManager = null;
    private static FloatMessagerMainWindow floatMessagerMainWindow;

    public FloatMessagerMainWindow(Context context, View view) {
        this.context = context;
        this.view = view;
        showWindow(context);
    }

    public static FloatMessagerMainWindow getFloatMessagerMainWindow(Context context, View view) {
        if (floatMessagerMainWindow == null) {
            synchronized (FloatMessagerMainWindow.class) {
                if (floatMessagerMainWindow == null) {
                    floatMessagerMainWindow = new FloatMessagerMainWindow(context, view);
                }
            }
        }
        return floatMessagerMainWindow;
    }

    private void showWindow(final Context context) {
//        if (!isWindowDismiss) {
//            Log.e(TAG, "view is already added here");
//            return;
//        }
//        isWindowDismiss = false;
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
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
//        mParams.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN;
        mParams.format = PixelFormat.RGBA_8888;
        mParams.gravity = Gravity.LEFT | Gravity.TOP;
        mParams.x = screenWidth - dp2px(context, 450);
        mParams.y = screenHeight - dp2px(context, 550);


        ImageView imageView = new ImageView(context);
        imageView.setImageResource(R.mipmap.icon_tab_item_message_pressed);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "image=========", Toast.LENGTH_SHORT).show();
                View view = LayoutInflater.from(context).inflate(R.layout.float_pople_room_layout, null);
                FloatMessagePopleDialog.getInstance(context, R.style.webviewTheme).setContextView(view);
            }
        });
//        floatView = new AVCallFloatView(context);
//        floatView.setParams(mParams);
//        floatView.setIsShowing(true);
        windowManager.addView(imageView, mParams);
    }

    private int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.1f);
    }

}
