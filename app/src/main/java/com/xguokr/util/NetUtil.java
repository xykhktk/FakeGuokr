package com.xguokr.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by yk on 2015/12/30.
 */
public class NetUtil {

    public static boolean isWifiOpen(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return info != null && info.getState() == NetworkInfo.State.CONNECTED;
    }

    public static boolean isDownLoadImg(Context context) {
        String mode = XGUtil.SPGetStringUtil(context, Const.SPKey_DownloadPicMode);
        if (mode.equals(Const.DownloadPicMode_Always) || (mode.equals(Const.DownloadPicMode_OnlyWifi)  && NetUtil.isWifiOpen(context))){
            return true;
        }
        return false;
    }
}
