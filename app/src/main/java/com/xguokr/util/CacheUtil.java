package com.xguokr.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created on 2015/12/9.
 */
public class CacheUtil {

    private DiskLruCache diskLruCache = null;

    public void saveList(Context context,String key,String uniqueName,Object o){
        File dir = getDiskCacheDir(context,uniqueName);
        ObjectOutputStream oos = null;
        OutputStream os = null;
        if(!dir.exists()){
            dir.mkdir();
        }
        try {
            diskLruCache = DiskLruCache.open(dir, getAppVersion(context), 1, 1 * 1024 * 1024);
            DiskLruCache.Editor editor = diskLruCache.edit(key);
            os = editor.newOutputStream(0);
            oos = new ObjectOutputStream(os);
            oos.writeObject(o);
            oos.flush();
            os.flush();
            editor.commit();
            if(oos != null) oos.close();
            if(os != null) os.close();
            if(diskLruCache != null) diskLruCache.close();
            XGUtil.LogUitl(getClass().getName() + " save success,key:" + key);
        } catch (IOException e) {
            e.printStackTrace();
            XGUtil.LogUitl(getClass().getName() + e.toString());
        }
    }

    public Object readList(Context context,String key,String uniqueName){
        File dir = getDiskCacheDir(context,uniqueName);
        if (dir == null){
            XGUtil.LogUitl(getClass().getName() + " get faile,key:  " + key);
            return null;
        }
        ObjectInputStream ois  = null;
        InputStream is = null;
        Object o = new Object();
        if(!dir.exists()){
            return o;
        }

        try {
            diskLruCache = DiskLruCache.open(dir, getAppVersion(context), 1, 1 * 1024 * 1024);
            DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
            if(snapshot != null){
                is = snapshot.getInputStream(0);
                ois = new ObjectInputStream(is);
                o = ois.readObject();
                XGUtil.LogUitl(getClass().getName() + " get success ,key:  " + key);
            }else{
                XGUtil.LogUitl(getClass().getName() + " get faile,key:  " + key);
            }
            if(ois !=null) ois.close();
            if(is != null) is.close();
            if(diskLruCache != null) diskLruCache.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return o;
    }




    public File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            File f = context.getExternalCacheDir();
            if (f != null){
                cachePath = f.getPath();
            }else{
                return null;
            }
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    public int getAppVersion(Context context) {

        PackageInfo info = null;
        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     *计算MD5
     */
    public String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
