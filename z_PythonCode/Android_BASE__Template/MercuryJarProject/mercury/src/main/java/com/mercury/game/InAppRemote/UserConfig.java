package com.mercury.game.InAppRemote;

import android.os.Looper;

import com.mercury.game.InAppDialog.LoginDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.IOException;
//shrinkpartstart
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
//shrinkpartend
import static com.mercury.game.InAppRemote.RemoteConfig.ip_address;
import static com.mercury.game.MercuryActivity.LogLocal;

public final class UserConfig {
    public static String isPayPermitted = "1";
//    private static String ipAddress = "gamesupportcluster.singmaan.com";

    //shrinkpartstart
    public static String getLoginPermition(final String uniqueId, final String gameName, final String channel,Callback callback) {

        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("unique_id", uniqueId)
                    .add("game_name", gameName)
                    .add("channel", channel)
                    .build();
            Request request = new Request.Builder()
                    .post(requestBody)
                    .url("https://" + ip_address + ":10013/user/is_login_permitted")
                    .build();
            client.newCall(request).enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            LogLocal("[UserConfig][get_login_permition] data=" + LoginDialog.isLoginPermitted);
        }

        return LoginDialog.isLoginPermitted;
    }
    //shrinkpartend
    
    //shrinkpartstart
    public static String getPayPermition(final String uniqueId, final String gameName, final String channel,Callback callback) {
        LogLocal("[UserConfig][get_pay_permition] gameName=" + gameName);
        LogLocal("[UserConfig][get_pay_permition] channel=" + channel);
        LogLocal("[UserConfig][get_pay_permition] uniqueId=" + uniqueId);
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("unique_id", uniqueId)
                    .add("game_name", gameName)
                    .add("channel", channel)
                    .build();
            Request request = new Request.Builder()
                    .post(requestBody)
                    .url("https://" + ip_address + ":10013/user/is_pay_permitted")
                    .build();

            client.newCall(request).enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            LogLocal("[UserConfig][get_pay_permition] data=" + isPayPermitted);
        }

        return isPayPermitted;
    }
    //shrinkpartend

}
