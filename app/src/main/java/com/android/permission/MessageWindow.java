package com.android.permission;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.MessageWindowManager;
import com.android.adapetr.ViewPagerAdapter;
import com.android.floatwindowpermission.R;
import com.android.widgets.HixgoTabViewpager;
import com.android.widgets.PagerSlidingTabStrip;

import java.util.ArrayList;

/**
 * Created by liupanpan on 2017/2/28.
 */

public class MessageWindow extends FrameLayout {
    private Context context;
    private static final String TAG = "AVCallFloatView";

    /**
     * 记录手指按下时在小悬浮窗的View上的横坐标的值
     */
    private float xInView;

    /**
     * 记录手指按下时在小悬浮窗的View上的纵坐标的值
     */
    private float yInView;
    /**
     * 记录当前手指位置在屏幕上的横坐标值
     */
    private float xInScreen;

    /**
     * 记录当前手指位置在屏幕上的纵坐标值
     */
    private float yInScreen;

    /**
     * 记录手指按下时在屏幕上的横坐标的值
     */
    private float xDownInScreen;

    /**
     * 记录手指按下时在屏幕上的纵坐标的值
     */
    private float yDownInScreen;

    private boolean isAnchoring = false;
    private boolean isShowing = false;
    private WindowManager windowManager = null;
    private WindowManager.LayoutParams mParams = null;

    public MessageWindow(Context context) {
        super(context);
        this.context = context;
        initView();
    }


    private void initView() {
        windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View floatView = inflater.inflate(R.layout.message_win_layout, null);
        HixgoTabViewpager viewpager = (HixgoTabViewpager) floatView.findViewById(R.id.message_view_pager);
        PagerSlidingTabStrip pagerTabStrip = (PagerSlidingTabStrip) floatView.findViewById(R.id.fragment_home_tab_pagertab);
        addView(floatView);
        setViewpager(viewpager, pagerTabStrip);
    }

    @Override
    public void setLayoutMode(int layoutMode) {
        super.setLayoutMode(layoutMode);
    }

    public void setParams(WindowManager.LayoutParams params) {
        mParams = params;
    }

    public void setIsShowing(boolean isShowing) {
        this.isShowing = isShowing;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isAnchoring) {
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xInView = event.getX();
                yInView = event.getY();
                xDownInScreen = event.getRawX();
                yDownInScreen = event.getRawY();
                xInScreen = event.getRawX();
                yInScreen = event.getRawY();
                Log.e("onTouchEvent", "ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                xInScreen = event.getRawX();
                yInScreen = event.getRawY();
                // 手指移动的时候更新小悬浮窗的位置
                Log.e("onTouchEvent", "ACTION_MOVE");
//                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:
                if (Math.abs(xDownInScreen - xInScreen) <= ViewConfiguration.get(getContext()).getScaledTouchSlop()
                        && Math.abs(yDownInScreen - yInScreen) <= ViewConfiguration.get(getContext()).getScaledTouchSlop()) {
                    // 点击效果
                    Toast.makeText(getContext(), "this float window is clicked", Toast.LENGTH_SHORT).show();
                } else {
                    //吸附效果
                    anchorToSide();
                }
                break;
            case MotionEvent.ACTION_OUTSIDE:
                Log.e("touch", "ACTION_OUTSIDE");
                if (isShowing) {
                    MessageWindowManager.getInstance().dismissWindow();
                }
                break;
        }
        return true;
    }

    private void anchorToSide() {
        isAnchoring = true;
        Point size = new Point();
        windowManager.getDefaultDisplay().getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;
        int middleX = mParams.x + getWidth() / 2;


        int animTime = 0;
        int xDistance = 0;
        int yDistance = 0;

        int dp_25 = dp2px(15);

        //1
        if (middleX <= dp_25 + getWidth() / 2) {
            xDistance = dp_25 - mParams.x;
        }
        //2
        else if (middleX <= screenWidth / 2) {
            xDistance = dp_25 - mParams.x;
        }
        //3
        else if (middleX >= screenWidth - getWidth() / 2 - dp2px(25)) {
            xDistance = screenWidth - mParams.x - getWidth() - dp_25;
        }
        //4
        else {
            xDistance = screenWidth - mParams.x - getWidth() - dp_25;
        }

        //1
        if (mParams.y < dp_25) {
            yDistance = dp_25 - mParams.y;
        }
        //2
        else if (mParams.y + getHeight() + dp_25 >= screenHeight) {
            yDistance = screenHeight - dp_25 - mParams.y - getHeight();
        }
        Log.e(TAG, "xDistance  " + xDistance + "   yDistance" + yDistance);

        animTime = Math.abs(xDistance) > Math.abs(yDistance) ? (int) (((float) xDistance / (float) screenWidth) * 600f)
                : (int) (((float) yDistance / (float) screenHeight) * 900f);
        this.post(new AnchorAnimRunnable(Math.abs(animTime), xDistance, yDistance, System.currentTimeMillis()));
    }

    public int dp2px(float dp) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private class AnchorAnimRunnable implements Runnable {

        private int animTime;
        private long currentStartTime;
        private Interpolator interpolator;
        private int xDistance;
        private int yDistance;
        private int startX;
        private int startY;

        public AnchorAnimRunnable(int animTime, int xDistance, int yDistance, long currentStartTime) {
            this.animTime = animTime;
            this.currentStartTime = currentStartTime;
            interpolator = new AccelerateDecelerateInterpolator();
            this.xDistance = xDistance;
            this.yDistance = yDistance;
            startX = mParams.x;
            startY = mParams.y;
        }

        @Override
        public void run() {
            if (System.currentTimeMillis() >= currentStartTime + animTime) {
                isAnchoring = false;
                return;
            }
            float delta = interpolator.getInterpolation((System.currentTimeMillis() - currentStartTime) / (float) animTime);
            int xMoveDistance = (int) (xDistance * delta);
            int yMoveDistance = (int) (yDistance * delta);
            Log.e(TAG, "delta:  " + delta + "  xMoveDistance  " + xMoveDistance + "   yMoveDistance  " + yMoveDistance);
            mParams.x = startX + xMoveDistance;
            mParams.y = startY + yMoveDistance;
            if (!isShowing) {
                return;
            }
            windowManager.updateViewLayout(MessageWindow.this, mParams);
            MessageWindow.this.postDelayed(this, 2);
        }
    }

    public void updateViewPosition(WindowManager.LayoutParams param) {
        //增加移动误差
        param.x = (int) (xInScreen - xInView) + 5;
        param.y = (int) (yInScreen - yInView) + 5;
//        Log.e(TAG, "x  " + mParams.x + "   y  " + mParams.y);
        windowManager.updateViewLayout(this, param);
    }

    /**
     * 打开已安装的APP
     *
     * @param packageName
     */
    private void runstallApp(String packageName) {
//        Uri packageURI = Uri.parse("package:" + packageName);
        try {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "未发现对应APP", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 卸载手机上的安装应用
     *
     * @param packageName 传入项目APP的包名
     */
    private void uninstallApp(String packageName) {
        try {
            Uri packageURI = Uri.parse("package:" + packageName);
            Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
            context.startActivity(uninstallIntent);
        } catch (Exception e) {
            Toast.makeText(context, "未发现对应APP", Toast.LENGTH_SHORT).show();
        }
        //setIntentAndFinish(true, true);
    }

    public void setViewpager(HixgoTabViewpager viewpager, PagerSlidingTabStrip pagerTabStrip) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View view1 = mInflater.inflate(R.layout.fragment_ware, null);
        View view2 = mInflater.inflate(R.layout.fragmentware1, null);
        ArrayList fragmentlist = new ArrayList<>(2);
        fragmentlist.add(view1);
        fragmentlist.add(view2);
        ArrayList tablist = new ArrayList<>();
        tablist.add("第一个frament");
        tablist.add("第二个frament");

        ViewPagerAdapter adapter = new ViewPagerAdapter(fragmentlist, tablist);
        viewpager.setAdapter(adapter);
        pagerTabStrip.setViewPager(viewpager);
    }
}

