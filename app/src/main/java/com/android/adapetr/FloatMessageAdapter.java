package com.android.adapetr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.floatwindowpermission.R;

/**
 * Created by liupanpan on 2017/3/13.
 */

public class FloatMessageAdapter extends BaseAdapter {
    private Context context;

    public FloatMessageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mviewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_float_message_layout, null);
            mviewHolder = new ViewHolder(convertView);
            convertView.setTag(mviewHolder);
        } else {
            mviewHolder = (ViewHolder) convertView.getTag();
        }
//        mviewHolder.mIv_message_user_photo
        mviewHolder.mTv_message_user_name.setText("乐蜀语音");
        mviewHolder.mTv_message_time.setText("16:14");
        mviewHolder.mTv_message_last_msg.setText("欢迎xmind加入");
        return convertView;
    }

    public static class ViewHolder {
        public View rootView;
        public ImageView mIv_message_user_photo;
        public TextView mTv_message_user_name;
        public TextView mTv_message_time;
        public TextView mTv_message_last_msg;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.mIv_message_user_photo = (ImageView) rootView.findViewById(R.id.item_message_user_photo);
            this.mTv_message_user_name = (TextView) rootView.findViewById(R.id.item_message_user_name);
            this.mTv_message_time = (TextView) rootView.findViewById(R.id.item_message_time);
            this.mTv_message_last_msg = (TextView) rootView.findViewById(R.id.item_message_last_msg);
        }

    }
}
