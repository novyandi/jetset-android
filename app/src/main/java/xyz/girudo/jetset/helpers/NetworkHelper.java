package xyz.girudo.jetset.helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;

import xyz.girudo.jetset.R;
import xyz.girudo.jetset.controllers.JetsetApp;


/**
 * Created by Novyandi Nurahmad on 11/09/2016
 * Indonesia
 */
public class NetworkHelper {

    private static NetworkHelper networkHelper;

    public static NetworkHelper getInstance() {
        if (networkHelper == null) {
            networkHelper = new NetworkHelper();
        }

        return networkHelper;
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void checkConnection(final Context context, final CheckCallback checkCallback) {
        if (isNetworkAvailable(context)) {
            checkCallback.onSuccess();
        } else {
            checkCallback.onFailed();
            JetsetApp.log("No network available!", "e");
        }
    }

    public void showDialogNoConnection(final Context context) {
        showDialogNoConnection(context, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    public void showDialogNoConnection(final Context context, final DialogInterface.OnClickListener onClickListener) {
        new Handler(context.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                String title = context.getResources().getString(R.string.error_connection_title);
                String message = context.getResources().getString(R.string.error_connection_message);
                AlertDialog.Builder builder = AlertHelper.getInstance().showAlertWithoutListener(context, title, message);
                builder.setPositiveButton(context.getString(R.string.ok), onClickListener);
                builder.show();
            }
        });
    }

    public interface CheckCallback {
        void onSuccess();

        void onFailed();
    }
}