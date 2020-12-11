package com.mercury.game.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @Author: 萧唐
 * @CreateDate: 2020/12/10 20:15
 * @UpdateDate: 2020/12/10 20:15
 * @UpdateUser: 更新者
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 作用描述
 */
public class DataUtil {
    /**
     * 简单数据存储(存)
     **/
    public static void storageData(Context context, String key, String data) {
        SharedPreferences sp = context.getSharedPreferences("gameData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, data);
        editor.commit();
    }

    public static void storageData(Context context, String key, boolean data) {
        SharedPreferences sp = context.getSharedPreferences("gameData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, data);
        editor.commit();
    }

    public static void storageData(Context context, String key, int data) {
        SharedPreferences sp = context.getSharedPreferences("gameData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, data);
        editor.commit();
    }

    /**
     * 简单数据存储(取)
     **/
    public static String getData(Context context, String key, String default_data) {
        SharedPreferences sp = context.getSharedPreferences("gameData", Context.MODE_PRIVATE);
        String data = sp.getString(key, default_data);
        return data;
    }


    public static boolean getData(Context context, String key, boolean default_data) {
        SharedPreferences sp = context.getSharedPreferences("gameData", Context.MODE_PRIVATE);
        boolean data = sp.getBoolean(key, default_data);
        return data;
    }

    public static int getData(Context context, String key, int default_data) {
        SharedPreferences sp = context.getSharedPreferences("gameData", Context.MODE_PRIVATE);
        int data = sp.getInt(key, default_data);
        return data;
    }
}
