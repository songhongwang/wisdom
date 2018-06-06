package me.nereo.imagechoose;

import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vigorous on 18/6/6.
 */

public class BaseActivity extends FragmentActivity {

    @TargetApi(Build.VERSION_CODES.M)
    public void checkAndRequestPermission(List<String> permissionList) {
        List<String> lackedPermission = new ArrayList<String>();

        for(String permission: permissionList){
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                lackedPermission.add(permission);
            }
        }

        if(lackedPermission.size()>0){
            // 请求所缺少的权限，在onRequestPermissionsResult中再看是否获得权限
            String[] requestPermissions = new String[lackedPermission.size()];
            lackedPermission.toArray(requestPermissions);
            requestPermissions(requestPermissions, 1024);
        }
    }
    public boolean hasAllPermissionsGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }
}
