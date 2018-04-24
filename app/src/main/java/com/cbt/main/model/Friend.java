package com.cbt.main.model;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.

import android.net.Uri;
import android.os.Parcel;
import android.text.TextUtils;

import com.cbt.main.engin.ChineseToEnglish2;

import java.io.Serializable;

import io.rong.common.ParcelUtils;
import io.rong.imlib.model.UserInfo;

/**
 * Entity mapped to table FRIEND.
 */
public class Friend implements Serializable{

    private String icon;
    private String uid;
    private String username;
    private String letters;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLetters() {
        String firstSpell ="a";
        if(!TextUtils.isEmpty(firstSpell)){
            firstSpell= ChineseToEnglish2.getFirstSpell(username);
        }
        return firstSpell;
    }

    //    public Friend(Parcel in) {
//        super(in);
//        setDisplayName(ParcelUtils.readFromParcel(in));
//        setRegion(ParcelUtils.readFromParcel(in));
//        setPhoneNumber(ParcelUtils.readFromParcel(in));
//        setStatus(ParcelUtils.readFromParcel(in));
//        setTimestamp(ParcelUtils.readLongFromParcel(in));
//        setNameSpelling(ParcelUtils.readFromParcel(in));
//        setDisplayNameSpelling(ParcelUtils.readFromParcel(in));
//        setLetters(ParcelUtils.readFromParcel(in));
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        super.writeToParcel(dest,flags);
//        ParcelUtils.writeToParcel(dest, getDisplayName());
//        ParcelUtils.writeToParcel(dest, getRegion());
//        ParcelUtils.writeToParcel(dest, getPhoneNumber());
//        ParcelUtils.writeToParcel(dest, getStatus());
//        ParcelUtils.writeToParcel(dest, getTimestamp());
//        ParcelUtils.writeToParcel(dest, getNameSpelling());
//        ParcelUtils.writeToParcel(dest, getDisplayNameSpelling());
//        ParcelUtils.writeToParcel(dest, getLetters());
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    public static final Creator<Friend> CREATOR = new Creator<Friend>() {
//        @Override
//        public Friend createFromParcel(Parcel in) {
//            return new Friend(in);
//        }
//
//        @Override
//        public Friend[] newArray(int size) {
//            return new Friend[size];
//        }
//    };
//
//    public boolean isExitsDisplayName() {
//        return !TextUtils.isEmpty(displayName);
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Friend friend = (Friend) o;

        return uid.equals(friend.uid);
    }

    @Override
    public int hashCode() {
        return uid.hashCode();
    }
}
