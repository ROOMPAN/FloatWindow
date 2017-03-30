package com.android.adapetr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.ViewItem;
import com.android.floatwindowpermission.R;

import java.util.List;

/**
 * Created by liupanpan on 2017/3/14.
 */

public class FloatMessageUniconAdapter extends BaseAdapter {
    private Context context;
    /**
     * 标题的item
     */
    public static final int ITEM_TITLE = 0;
    /**
     * 二级菜单的item
     */
    public static final int ITEM_INTRODUCE = 1;
    private List<ViewItem> mList;

    private LayoutInflater inflater;

    public FloatMessageUniconAdapter(Context context, List<ViewItem> mList) {
        this.context = context;
        this.mList = mList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (mList != null) {
            return mList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).type;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        switch (type) {
            case ITEM_TITLE:
                convertView = inflater.inflate(R.layout.item_float_message_unicon_title, null);
                break;
            case ITEM_INTRODUCE:
                convertView = inflater.inflate(R.layout.item_message_unicon_list, null);
                break;
        }
        return convertView;
    }
}
