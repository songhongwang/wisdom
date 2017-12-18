package com.cbt.main.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by vigorous on 17/12/18.
 */

public class ToastUtils {

    public static void show(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }
}
