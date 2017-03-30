package com.android.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;

/**
 * Created by liupanpan on 2017/3/21.
 */

public class FloatMessagePopleDialog extends BaseDialog {
    private static FloatMessagePopleDialog floatMessagePopleDialog;
    private Context context;

    public static FloatMessagePopleDialog getInstance(Context context, int style) {
        if (floatMessagePopleDialog == null) {
            synchronized (FloatMessageMainDialog.class) {
                if (floatMessagePopleDialog == null) {
                    floatMessagePopleDialog = new FloatMessagePopleDialog(context, style);
                }
            }
        }
        return floatMessagePopleDialog;
    }

    public FloatMessagePopleDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    public void setContextView(View view) {
        super.setContextView(view);
        setViewId(view);
    }

    private void setViewId(View view) {
    }
}
