package xyz.girudo.jetset.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xyz.girudo.jetset.R;
import xyz.girudo.jetset.controllers.JetsetApp;
import xyz.girudo.jetset.controllers.Preferences;
import xyz.girudo.jetset.controllers.RealmDataControl;
import xyz.girudo.jetset.helpers.AlertHelper;

/**
 * Created by Novyandi Nurahmad on 19/11/2016
 */
public class SplashScreenActivity extends Activity {
    private ImageView imageLogo;
    private ViewAnimator spAnimator;

    //Permission Marshmallow
    private int REQUEST_MULTIPLE_PERMISSION = 109;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initFirst();
    }

    private void initFirst() {
        spAnimator = (ViewAnimator) findViewById(R.id.sp_animator);
        imageLogo = (ImageView) findViewById(R.id.sp_img);
        imageLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissions()) return;
                goNextActivity();
            }
        });

        if (!JetsetApp.checkConfig(this, Preferences.FIRST_START)) {
            boolean isPrepared = prepareData();
            if (isPrepared) {
                JetsetApp.setConfig(this, Preferences.FIRST_START, "true");
                spAnimator.setDisplayedChild(1);
            } else {
                spAnimator.setDisplayedChild(2);
            }
        } else {
            JetsetApp.log("Not first time", "i");
            spAnimator.setDisplayedChild(1);
        }

        if (!JetsetApp.checkConfig(this, Preferences.NOTIFICATION))
            JetsetApp.setConfig(this, Preferences.NOTIFICATION, "true");
        JetsetApp.setConfig(this, Preferences.NOT_CHANGE_SCREEN, "true");
    }

    public void goNextActivity() {
        new Handler(getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            final List<String> permissionsNeeded = new ArrayList<>();
            final List<String> permissionsList = new ArrayList<>();
            if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                permissionsNeeded.add(getString(R.string.sp_storage));
            if (!addPermission(permissionsList, Manifest.permission.GET_ACCOUNTS))
                permissionsNeeded.add(getString(R.string.sp_contacts));
            if (permissionsList.size() > 0) {
                if (permissionsNeeded.size() > 0) {
                    // Need Rationale
                    String message = getString(R.string.sp_access_message) + " " + permissionsNeeded.get(0);
                    for (int i = 1; i < permissionsNeeded.size(); i++)
                        message = message + ", " + permissionsNeeded.get(i);
                    final String AppName = getResources().getString(R.string.app_name);
                    AlertDialog.Builder permissionRequest = AlertHelper.getInstance().showAlertWithoutListener(this, AppName, message);
                    permissionRequest.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String message = String.format(getString(R.string.sp_cancel_info), AppName, getString(R.string.sp_contacts), getString(R.string.sp_storage));
                            showPermissionDialog(SplashScreenActivity.this, AppName, message, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                        }
                    });
                    permissionRequest.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), REQUEST_MULTIPLE_PERMISSION);
                        }
                    });
                    permissionRequest.setCancelable(false);
                    permissionRequest.show();
                    return true;
                }
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), REQUEST_MULTIPLE_PERMISSION);
                return true;
            }
        }
        return false;
    }

    private void showPermissionDialog(Context context, String title, String message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder permissionInfo = AlertHelper.getInstance().showAlertWithoutListener(context, title, message);
        permissionInfo.setCancelable(false);
        permissionInfo.setPositiveButton(getString(R.string.ok), onClickListener);
        permissionInfo.show();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == REQUEST_MULTIPLE_PERMISSION) {
            Map<String, Integer> perms = new HashMap<>();
            // Initial
            perms.put(Manifest.permission.GET_ACCOUNTS, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
            // Fill with results
            for (int i = 0; i < permissions.length; i++)
                perms.put(permissions[i], grantResults[i]);
            // Check for ACCESS_FINE_LOCATION
            if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && perms.get(Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED) {
                // All Permissions Granted
                goNextActivity();
            } else {
                // Permission Denied
                Toast.makeText(this, getString(R.string.sp_info_denied), Toast.LENGTH_SHORT).show();
                final String AppName = getResources().getString(R.string.app_name);
                String message = String.format(getString(R.string.sp_cancel_info), AppName, getString(R.string.sp_contacts), getString(R.string.sp_storage));
                showPermissionDialog(this, AppName, message, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
            }
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.info_exit))
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                finish();
                            }
                        }).create().show();
    }

    private boolean prepareData() {
        return RealmDataControl.getInstance(this).prepareAllData();
    }
}