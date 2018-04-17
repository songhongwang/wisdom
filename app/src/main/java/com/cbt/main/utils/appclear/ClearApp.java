package com.cbt.main.utils.appclear;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.cbt.main.utils.ToastUtils;

import java.io.File;
import java.util.Properties;

/**
 * Created by vigorous on 17/12/18.
 */

public class ClearApp {
    private final int CLEAN_SUC=1001;
    private final int CLEAN_FAIL=1002;

    private Context mContext;

    public ClearApp(Context context) {
        mContext = context;
    }

    /**
     * 计算缓存的大小
     */
    public String caculateCacheSize() {
        long fileSize = 0;
        String cacheSize = "0KB";
        File filesDir = mContext.getFilesDir();
        File cacheDir = mContext.getCacheDir();

        fileSize += FileUtil.getDirSize(filesDir);
        fileSize += FileUtil.getDirSize(cacheDir);
        // 2.2版本才有将应用缓存转移到sd卡的功能
        if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
            File externalCacheDir = MethodsCompat
                    .getExternalCacheDir(mContext);
            fileSize += FileUtil.getDirSize(externalCacheDir);
//            fileSize += FileUtil.getDirSize(new File(
//                    org.kymjs.kjframe.utils.FileUtils.getSDCardPath()
//                            + File.separator + "KJLibrary/cache"));
        }
        if (fileSize > 0)
            cacheSize = FileUtil.formatFileSize(fileSize);
//        tvCache.setText(cacheSize);
        return cacheSize;
    }

    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }
    /**
     * 清除app缓存
     */
    private void myclearaAppCache() {
        DataCleanManager.cleanDatabases(mContext);
        // 清除数据缓存
        DataCleanManager.cleanInternalCache(mContext);
        // 2.2版本才有将应用缓存转移到sd卡的功能
        if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
            File externalCacheDir = MethodsCompat
                    .getExternalCacheDir(mContext);

            DataCleanManager.cleanCustomCache(externalCacheDir.getPath());
        }
        // 清除编辑器保存的临时内容
        Properties props = getProperties();
        for (Object key : props.keySet()) {
            String _key = key.toString();
            if (_key.startsWith("temp"))
                removeProperty(_key);
        }
//        Core.getKJBitmap().cleanCache();
    }

    /**
     * 清除保存的缓存
     */
    public Properties getProperties() {
        return AppConfig.getAppConfig(mContext).get();
    }
    public void removeProperty(String... key) {
        AppConfig.getAppConfig(mContext).remove(key);
    }
    /**
     * 清除app缓存
     *
     * @param
     */
    public void clearAppCache() {

        new Thread() {
            @Override
            public void run() {
                Message msg = new Message();
                try {
                    myclearaAppCache();
                    msg.what = CLEAN_SUC;
                } catch (Exception e) {
                    e.printStackTrace();
                    msg.what = CLEAN_FAIL;
                }
                handler.sendMessage(msg);
            }
        }.start();
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case CLEAN_FAIL:
                    ToastUtils.show(mContext,"清除失败");
                    break;
                case CLEAN_SUC:
                    ToastUtils.show(mContext,"清除成功");
                    break;
            }
        };
    };

}
