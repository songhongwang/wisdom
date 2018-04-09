package com.cbt.main.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.cbt.main.model.User;
import com.google.gson.Gson;

/**
 * Created by vigorous on 17/12/25.
 *
 */

public class SharedPreferencUtil {
    public static final String FILE_APP_CONFIG = "FILE_APP_CONFIG ";
    public static final String KEY_SP_LOGIN_USER = "KEY_SP_LOGIN_USER ";

    public static void saveLogin(Context context, User user){
        String userJson = new Gson().toJson(user);
        if(user == null){
            return;
        }
        SharedPreferences sp = context.getSharedPreferences(FILE_APP_CONFIG, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(KEY_SP_LOGIN_USER, userJson);
        boolean commit = edit.commit();
    }

    public static User getLogin(Context context){
        SharedPreferences sp = context.getSharedPreferences(FILE_APP_CONFIG, Context.MODE_PRIVATE);
        String userJson = sp.getString(KEY_SP_LOGIN_USER, null);
        if(TextUtils.isEmpty(userJson)){
            return null;
        }
        User user = new Gson().fromJson(userJson, User.class);
        return user;
    }

    public static void logout(Context context){
        SharedPreferences sp = context.getSharedPreferences(FILE_APP_CONFIG, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(KEY_SP_LOGIN_USER, null);
        boolean commit = edit.commit();
    }
}
