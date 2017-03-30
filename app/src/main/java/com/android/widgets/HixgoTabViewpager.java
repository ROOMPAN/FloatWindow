package com.android.widgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by zhuzi on 16/3/25.
 */
public class HixgoTabViewpager extends ViewPager {

    public HixgoTabViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HixgoTabViewpager(Context context) {
        super(context);
    }
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if(ev.getY() >= 500){
//            return super.onInterceptTouchEvent(ev);
//        }else{
//            return false;
//        }
//    }
}
