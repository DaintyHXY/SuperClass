package com.example.dainty.superclass;


import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

public class PermissionUtils {

        //Storage Permissions
        private static final int REQUEST_EXTERNAL_STORAGE = 1;

        private static String[] PERMISSIONS_STORAGE = {
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        };


        public static void verifyStoragePermissions(Activity activity){
            //check if we have write permission
            int permission = ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if(permission != PackageManager.PERMISSION_GRANTED){
                //we dont have permission so prompt the user
                ActivityCompat.requestPermissions(activity,PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        }

    }


