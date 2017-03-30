package com.android.floatcontrol;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.android.floatwindowpermission.R;

/**
 * Created by liupanpan on 2017/3/10.
 * 悬浮窗设置
 */

public class FloatSettingControl implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private Context context;
    private View view;
    private boolean BROISOPEN = false;
    private boolean MESSABUBBLE = false;
    private ImageView img_automatic_broadcast;//语音播报开关
    private ImageView img_message_bubble;//消息气泡开关
    private SeekBar skb_message_volume;
    private static volatile FloatSettingControl floatSettingControl = null;

    public static FloatSettingControl getFloatSettinginsert(Context context, View view) {
        if (floatSettingControl == null) {
            synchronized (FloatSettingControl.class) {
                if (floatSettingControl == null) {
                    floatSettingControl = new FloatSettingControl(context, view);
                }
            }
        }
        return floatSettingControl;
    }

    public FloatSettingControl(Context context, View view) {
        this.context = context;
        this.view = view;
        initView(context, view);
    }

    private void initView(final Context context, View view) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);//获取当前媒体的音量大小

        img_automatic_broadcast = (ImageView) view.findViewById(R.id.float_message_setting_automatic_broadcast);
        img_message_bubble = (ImageView) view.findViewById(R.id.float_message_setting_bubble);
        skb_message_volume = (SeekBar) view.findViewById(R.id.float_message_setting_seekbar);
        skb_message_volume.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));// 当前的媒体音量
        myRegisterReceiver();//注册同步更新的广播
        skb_message_volume.setMax(15);//设置最大音量
        img_automatic_broadcast.setOnClickListener(this);
        img_message_bubble.setOnClickListener(this);
        skb_message_volume.setOnSeekBarChangeListener(this);
        new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange) {
                // TODO Auto-generated method stub
                super.onChange(selfChange);
                AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                skb_message_volume.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
                //或者你也可以用媒体音量来监听改变，效果都是一样的，。
                //mVoiceSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.float_message_setting_automatic_broadcast:
                if (BROISOPEN) {
                    img_automatic_broadcast.setImageDrawable(context.getResources().getDrawable(R.mipmap.float_setting_view_cb_off));
                    BROISOPEN = false;
                } else {
                    img_automatic_broadcast.setImageDrawable(context.getResources().getDrawable(R.mipmap.float_setting_view_cb_on));
                    BROISOPEN = true;
                }
                break;
            case R.id.float_message_setting_bubble:
                if (MESSABUBBLE) {
                    img_message_bubble.setImageDrawable(context.getResources().getDrawable(R.mipmap.float_setting_view_cb_off));
                    MESSABUBBLE = false;
                } else {
                    img_message_bubble.setImageDrawable(context.getResources().getDrawable(R.mipmap.float_setting_view_cb_on));
                    MESSABUBBLE = true;
                }
                break;
        }
    }

    /**
     * 注册当音量发生变化时接收的广播
     */
    private void myRegisterReceiver() {
        MyVolumeReceiver mVolumeReceiver = new MyVolumeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.media.VOLUME_CHANGED_ACTION");
        context.registerReceiver(mVolumeReceiver, filter);
    }

    /**
     * 处理音量变化时的界面显示
     *
     * @author long
     */
    private class MyVolumeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //如果音量发生变化则更改seekbar的位置
            if (intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")) {
                AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                int currVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);// 当前的媒体音量
                skb_message_volume.setProgress(currVolume);
            }
        }
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        Log.v("lyj_ring", "mVoiceSeekBar max progress = " + progress);
        //系统音量和媒体音量同时更新
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
        audioManager.setStreamVolume(3, progress, 0);//  3 代表  AudioManager.STREAM_MUSIC
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

}