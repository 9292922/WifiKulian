package com.xiaohong.wifikulian.handler;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

import com.xiaohong.wifikulian.Interface.ProgressCancelListener;

/**
 * Created by Lpoint on 2017/1/20 14:48.
 */

public class ProgressDialogHandler extends Handler {
    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    private ProgressDialog pd;

    private Context mContext;
    private boolean cancelable;
    private ProgressCancelListener mProgressCancelListener;
    private String mProgressMsg;

    public ProgressDialogHandler(Context mContext, ProgressCancelListener mProgressCancelListener, boolean cancelable, String mProgressMsg) {
        super();
        int a = 10;
        this.mContext = mContext;
        this.mProgressCancelListener = mProgressCancelListener;
        this.mProgressMsg = mProgressMsg;
        this.cancelable = cancelable;
    }

    private void initProgressDialog() {
        if (pd == null) {
            pd = new ProgressDialog(mContext);
            pd.setMessage(mProgressMsg);
            pd.setCancelable(cancelable);
            pd.setCanceledOnTouchOutside(false);
            if (cancelable) {
                pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        mProgressCancelListener.onCancelProgress();
                    }
                });
            }
            if (!pd.isShowing())
                pd.show();
        }
    }

    private void dismissProgressDialog() {
        if (pd != null) {
            pd.dismiss();
            pd = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                if (!mProgressMsg.equals("null"))
                    initProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }
}
