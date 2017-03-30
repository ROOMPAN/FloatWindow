package com.android.floatcontrol;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.adapetr.FloatMessageAdapter;
import com.android.floatwindowpermission.FloatMessagerMainWindow;
import com.android.floatwindowpermission.R;

/**
 * Created by liupanpan on 2017/3/13.
 * 悬浮窗消息
 */

public class FloatMessagerControl implements View.OnClickListener {
    private static FloatMessagerControl floatMessagerControl;
    private Context context;
    private ListView mlv_message;

    public static FloatMessagerControl getFloatMessagerinsert(Context context, View view) {
        if (floatMessagerControl == null) {
            synchronized (FloatMessagerControl.class) {
                if (floatMessagerControl == null) {
                    floatMessagerControl = new FloatMessagerControl(context, view);
                }
            }
        }
        return floatMessagerControl;
    }

    public FloatMessagerControl(Context context, View view) {
        this.context = context;
        initView(context, view);
    }

    private void initView(Context context, View view) {
        mlv_message = (ListView) view.findViewById(R.id.float_meaasge_lv);
        mlv_message.setAdapter(new FloatMessageAdapter(context));
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.flaot_message_linner);
        linearLayout.setOnClickListener(this);
    }

    //TODO 设置跳转到好友列表页面
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.flaot_message_linner:
                FloatMessagerMainWindow.getFloatMessagerMainWindow(context, null);
//                View view = LayoutInflater.from(context).inflate(R.layout.item_float_message_layout, null);
//                FloatMessagePopleDialog.getInstance(context, R.style.webviewTheme).setContextView(view);
                break;

        }
    }
}
