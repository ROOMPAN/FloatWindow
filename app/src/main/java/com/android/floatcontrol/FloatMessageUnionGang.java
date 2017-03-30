package com.android.floatcontrol;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import com.android.ViewItem;
import com.android.adapetr.FloatMessageUniconAdapter;
import com.android.floatwindowpermission.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liupanpan on 2017/3/14.
 * 悬浮窗工会开黑
 */


public class FloatMessageUnionGang {
    private Context context;
    private View view;
    private static  FloatMessageUnionGang floatMessageUnionGang;

    public static FloatMessageUnionGang getfloatMessageUnionGang(Context context, View view) {
        if (floatMessageUnionGang == null) {
            synchronized (FloatSettingControl.class) {
                if (floatMessageUnionGang == null) {
                    floatMessageUnionGang = new FloatMessageUnionGang(context, view);
                }
            }
        }
        return floatMessageUnionGang;
    }

    public FloatMessageUnionGang(Context context, View view) {
        this.context = context;
        this.view = view;
        initView(context, view);
    }

    private void initView(Context context, View view) {
        ListView lv_message_unicon = (ListView) view.findViewById(R.id.float_message_union_gang_lv);
        lv_message_unicon.setAdapter(new FloatMessageUniconAdapter(context,getData()));
    }
    //添加数据
    public List<ViewItem> getData() {
        List list = new ArrayList<ViewItem>();
        for (int i = 0; i < 10; i++) {
            ViewItem item = new ViewItem();//这个必须在这里new 不然 数据都是一样的
            if (i % 4 == 0) {
                item.type = 0;
            } else {
                item.type = 1;
            }
            item.address = "tianjin" + i;
            item.name = "wang" + i;
            list.add(item);

        }

        return list;
    }
}
