package com.editsoft.guitartuner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    private static final int REQUEST_RECORD_AUDIO = 101;
    private Handler mHandler;
    private Runnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                String[] permission = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.RECORD_AUDIO};
                if (checkPermissionManual(permission, REQUEST_RECORD_AUDIO)) {
                    startActivity(new Intent(Splash.this, MainActivity.class));
                } else {
                    Splash.this.finish();
                }

            }
        };
        mHandler.postDelayed(mRunnable, 5000);
    }


    public boolean checkPermissionManual(String[] permission, int permissionCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permission, permissionCode);
            hasPermission(permission);
        }
        return true;
    }


    public boolean hasPermission(String[] permission) {
        for (String s : permission) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (PackageManager.PERMISSION_GRANTED != checkSelfPermission(s)) {
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_AUDIO:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED) && (grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                    startActivity(new Intent(Splash.this, MainActivity.class));
                }
        }
    }
}
