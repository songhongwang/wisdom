package com.cbt.main.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cbt.main.BuildConfig;
import com.cbt.main.R;
import com.cbt.main.model.VersionCodeBean;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * author vigorous123
 */
public class VersionCodeUpdate {
    private Context context;

    public VersionCodeUpdate(Context context) {
        this.context = context;
    }

    /**
     * 更新提示Dialog
     * @param versionCodeBean
     */
    public void infoToPersonUpdate(final VersionCodeBean versionCodeBean){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("版本提示：");
        builder.setMessage("发现新版本：\n" + "版本号：" + versionCodeBean.getVersionCode() + "\n版本更新：内容" + versionCodeBean.getContent());
        builder.setCancelable(false);
        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                downLoaderApk(versionCodeBean);
                //更新
            }
        });
        builder.setNegativeButton("不更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //不更新
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    /**
     * 下载apk
     * @param versionCodeBean
     */
    private void downLoaderApk(final VersionCodeBean versionCodeBean){
        //显示下载进度
        progressDialog();
        //下载apk
        new Thread(new Runnable() {
            @Override
            public void run() {
                File path = new File(Environment.getExternalStorageDirectory() + "/zhnq/app/");
                File file = new File(Environment.getExternalStorageDirectory() + "/zhnq/app/apkDownLoad.apk");
                if(!path.exists()){
                    path.mkdirs();
                }
                if(file.exists()){
                    file.delete();
                }
                try {
                    boolean newFile = file.createNewFile();
                    Log.d("create_file", "create file success " + newFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BufferedOutputStream bufferedOutputStream = null;
                BufferedInputStream bufferedInputStream = null;
                HttpURLConnection httpURLConnection = null;
                try {
                    URL url = new URL(versionCodeBean.getUrl());
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setReadTimeout(5000);
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.setRequestProperty("Accept-Encoding", "identity");
                    int contentLength = httpURLConnection.getContentLength();
                    bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                    bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
                    byte[] bytes = new byte[1024];
                    int length;
                    int totalLength = 0;
                    while((length = bufferedInputStream.read(bytes)) != -1 ){
                        //下载中
                        bufferedOutputStream.write(bytes, 0, length);
                        totalLength += length;

                        int pro = (int)(totalLength * 1.0f / contentLength * 100);
                        if(pro > 0){
                            Message message = handler.obtainMessage();
                            message.arg2 = pro;
                            handler.sendMessage(message);
                        }
                    }
                    bufferedOutputStream.flush();
                    //下载完成
                    Message message = handler.obtainMessage();
                    message.arg2 = 200;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    //下载失败
                    Message message = handler.obtainMessage();
                    message.arg2 = 150;
                    handler.sendMessage(message);
                }finally {
                    if(httpURLConnection != null){
                        httpURLConnection.disconnect();
                    }
                    if(bufferedInputStream != null){
                        try {
                            bufferedInputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(bufferedOutputStream != null){
                        try {
                            bufferedOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }


            }
        }).start();
    }

    /**
     * 下载进度Dialog
     */
    private AlertDialog progressDialog;
    private void progressDialog(){
        AlertDialog.Builder  builder = new AlertDialog.Builder(context);
        builder.setMessage("正在为您更新...， 请不要断开网络！");
        builder.setCancelable(false);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //取消更新
                dialog.dismiss();
            }
        });
        builder.setView(R.layout.dialog_update);
        progressDialog = builder.create();
        progressDialog.show();
    }

    private void updateProgress(int pro){
        if(progressDialog.isShowing()){
            ProgressBar progressBar = progressDialog.findViewById(R.id.pb);
            progressBar.setProgress(pro);
        }
    }

    /**
     * 获取本地版本号
     * @return
     */
    private int getVersionCode(){
        PackageManager packageManager = context.getPackageManager();
        int versionCode = 0;
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final int arg2 = msg.arg2;
            if(arg2 == 200){
                //下载完成
                progressDialog.dismiss();
                installApk();
            }else if(arg2 == 150){
                //下载失败
                Toast.makeText(context, "更新失败！", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }else if(arg2 > 0 && arg2 < 101){
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("apk_update", "" + arg2);
                        updateProgress(arg2);
                    }
                });
            }
        }
    };

    /**
     * 安装apk
     */
    private void installApk(){
        File apkFile = new File(Environment.getExternalStorageDirectory() + "/zhnq/app/apkDownLoad.apk");
        if(! apkFile.exists()){
            Toast.makeText(context, "安装包不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context.getApplicationContext(), BuildConfig.APPLICATION_ID + ".fileprovider", apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        context.getApplicationContext().startActivity(intent);
    }

}
