package com.android.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.adapetr.ViewPagerAdapter;
import com.android.floatcontrol.FloatMessageRoomControl;
import com.android.floatcontrol.FloatMessageUnionGang;
import com.android.floatcontrol.FloatMessagerControl;
import com.android.floatcontrol.FloatSettingControl;
import com.android.floatwindowpermission.R;
import com.android.permission.FloatWindowManager;
import com.android.widgets.HixgoTabViewpager;
import com.android.widgets.PagerSlidingTabStrip;

import java.util.ArrayList;

/**
 * Created by liupanpan on 2017/3/17.
 */

public class FloatMessageMainDialog extends BaseDialog implements View.OnClickListener {
    private static FloatMessageMainDialog floatMessageMainDialog;
    private Context context;

    public static FloatMessageMainDialog getInstance(Context context, int style) {
        if (floatMessageMainDialog == null) {
            synchronized (FloatMessageMainDialog.class) {
                if (floatMessageMainDialog == null) {
                    floatMessageMainDialog = new FloatMessageMainDialog(context, style);
                }
            }
        }
        return floatMessageMainDialog;
    }

    public FloatMessageMainDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    @Override
    public void setContextView(View view) {
        super.setContextView(view);
        setViewId(view);
    }

    private void setViewId(View view) {
        HixgoTabViewpager viewpager = (HixgoTabViewpager) view.findViewById(R.id.message_view_pager);
        PagerSlidingTabStrip pagerTabStrip = (PagerSlidingTabStrip) view.findViewById(R.id.fragment_home_tab_pagertab);
        ImageView bt_exit = (ImageView) view.findViewById(R.id.image_exit);
        bt_exit.setOnClickListener(this);
        setViewpager(viewpager, pagerTabStrip);
    }

    public void setViewpager(HixgoTabViewpager viewpager, PagerSlidingTabStrip pagerTabStrip) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View float_message_view = mInflater.inflate(R.layout.float_message_layout, null);
        View float_message_union_view = mInflater.inflate(R.layout.float_message_union_gang, null);
        View float_message_room_view = mInflater.inflate(R.layout.float_message_room_layout, null);
        View float_setting_view = mInflater.inflate(R.layout.float_setting_layout, null);
        FloatSettingControl.getFloatSettinginsert(context, float_setting_view);
        FloatMessagerControl.getFloatMessagerinsert(context, float_message_view);
        FloatMessageRoomControl.getfloatMessageRoomControl(context, float_message_room_view);
        FloatMessageUnionGang.getfloatMessageUnionGang(context, float_message_union_view);
        ArrayList fragmentlist = new ArrayList<>(4);
        fragmentlist.add(float_message_view);
        fragmentlist.add(float_message_union_view);
        fragmentlist.add(float_message_room_view);
        fragmentlist.add(float_setting_view);
        ArrayList tablist = new ArrayList<>();
        tablist.add("消息");
        tablist.add("工会语音");
        tablist.add("房间");
        tablist.add("设置");
        ViewPagerAdapter adapter = new ViewPagerAdapter(fragmentlist, tablist);
        viewpager.setAdapter(adapter);
        pagerTabStrip.setViewPager(viewpager);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_exit:
                Toast.makeText(context, "exit", Toast.LENGTH_SHORT).show();
                FloatWindowManager.getInstance().dismissWindow();
                dismiss();
                break;
        }
    }
}
