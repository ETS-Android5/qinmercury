package com.mercury.game.InAppRemote;

import com.mercury.game.InAppDialog.LoginDialog;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static com.mercury.game.InAppRemote.RemoteConfig.ip_address;
import static com.mercury.game.MercuryActivity.LogLocal;

public class ADConfig {
    private static String adAccessableUrl = "https://"+ip_address+":10009/isadaccessable?game_name=%s";
    public static void isAdAccessable(String game_name, Callback callback){
        try {
            String url = String.format(adAccessableUrl,game_name);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            client.newCall(request).enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            LogLocal("[ADConfig][isAdAccessable]");
        }
    }

}
