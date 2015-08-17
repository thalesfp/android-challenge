package me.thales.androidchallenge.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by thales on 6/14/15.
 */
public class LoadingDialog {
    private ProgressDialog progress;
    private Context context;

    public LoadingDialog(Context context) {
        this.context = context;
    }

    public void showLoadingDialog(String title, String message) {
        if (progress == null) {
            progress = new ProgressDialog(this.context);

            progress.setTitle(title);
            progress.setMessage(message);
        }

        progress.show();
    }

    public void dismissLoadingDialog() {
        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }
}
