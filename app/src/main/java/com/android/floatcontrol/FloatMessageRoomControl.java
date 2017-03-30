package com.android.floatcontrol;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.ViewItem;
import com.android.adapetr.FloatMessageRoomAdapter;
import com.android.floatwindowpermission.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liupanpan on 2017/3/14.
 * 悬浮窗房间
 */

public class FloatMessageRoomControl implements TextWatcher, View.OnClickListener {
    private static FloatMessageRoomControl floatMessageRoomControl;
    private Context context;
    private EditText et_search_room;
    private ListView lv_room;
    private Button bt_seach_room;

    public FloatMessageRoomControl(Context context, View view) {
        this.context = context;
        initView(context, view);
    }

    private void initView(final Context context, View view) {
        et_search_room = (EditText) view.findViewById(R.id.float_message_room_edit_search);
        lv_room = (ListView) view.findViewById(R.id.float_message_room_lv);
        bt_seach_room = (Button) view.findViewById(R.id.bt_seach);
        bt_seach_room.setOnClickListener(this);
        et_search_room.addTextChangedListener(this);
        //设置可获得焦点
        et_search_room.setFocusable(true);
        et_search_room.setFocusableInTouchMode(true);
        //请求获得焦点
        et_search_room.requestFocus();
        lv_room.setAdapter(new FloatMessageRoomAdapter(context, getData()));
    }

    public static FloatMessageRoomControl getfloatMessageRoomControl(Context context, View view) {
        if (floatMessageRoomControl == null) {
            synchronized (FloatSettingControl.class) {
                if (floatMessageRoomControl == null) {
                    floatMessageRoomControl = new FloatMessageRoomControl(context, view);
                }
            }
        }
        return floatMessageRoomControl;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_seach:
                if (!TextUtils.isEmpty(et_search_room.getText().toString())) {
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et_search_room.getWindowToken(), 0);
                    Toast.makeText(context, et_search_room.getText().toString(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "搜索内容不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
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
