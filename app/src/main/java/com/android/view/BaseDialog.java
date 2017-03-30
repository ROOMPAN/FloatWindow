package com.android.view;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by liupanpan on 2017/3/8.
 */

public class BaseDialog extends Dialog {
    public static volatile BaseDialog dialoginstance;
    public Context context;
    private Display display;//这个设置显示属性用的
    private View view;


    public static BaseDialog getInstance(Context context, int style) {
        if (dialoginstance == null) {
            synchronized (BaseDialog.class) {
                if (dialoginstance == null) {
                    dialoginstance = new BaseDialog(context, style);
                }
            }
        }
        return dialoginstance;
    }

    public void setContextView(View view) {
        this.view = view;
        showDialog();
    }


    public BaseDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view);
        initview();

    }

    private void initview() {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        //在这里初始化 基础对话框
        this.setCanceledOnTouchOutside(true);
        //隐藏系统输入盘
        this.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams Param = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
        //添加当输入框弹出时 窗体上移。
        dialogWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Param.width = (int) (d.widthPixels * 0.9);
        Param.height = (int) (d.heightPixels * 0.54);// 高度设置为屏幕的0.5
        Param.x = 100; // 新位置X坐标
        Param.y = 5; // 新位置Y坐标
        dialogWindow.setAttributes(Param);
    }


    public void showDialog() {
        if (isShowing()) {
            dismiss();
        } else {
            show();
        }
    }

    public boolean isScreenChange() {
        Configuration mConfiguration = context.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向
        if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {
            Log.e("Re", "false");
            Toast.makeText(context, "false", Toast.LENGTH_SHORT).show();
            //横屏
            return false;
        } else if (ori == mConfiguration.ORIENTATION_PORTRAIT) {
            //竖屏
            Toast.makeText(context, "true", Toast.LENGTH_SHORT).show();
            Log.e("Re", "true");
            return true;
        }
        return false;
    }
}
