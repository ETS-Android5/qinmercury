package com.mercury.game.InAppRemote;

import android.os.Looper;

import com.mercury.game.InAppDialog.LoginDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import static com.mercury.game.MercuryActivity.LogLocal;

public final class UserConfig {
    public static String isPayPermitted = "1";
    private static String ipAddress = "gamesupportcluster.singmaan.com";

    public static String getLoginPermition(final String uniqueId, final String gameName, final String channel,Callback callback) {
        //isLoginPermitted = "0";
        try {
            //1.创建OkHttpClient对象
            OkHttpClient client = new OkHttpClient();
            //2.创建RequestBody对象
            RequestBody requestBody = new FormBody.Builder()
                    .add("unique_id", uniqueId)
                    .add("game_name", gameName)
                    .add("channel", channel)
                    .build();
            //3.创建Request对象
            Request request = new Request.Builder()
                    .post(requestBody)
                    .url("https://" + ipAddress + ":10013/user/is_login_permitted")
                    .build();
            //4. 同步请求
            // Response response = client.newCall(request).execute();
            //5.异步请求
            client.newCall(request).enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            LogLocal("[UserConfig][get_login_permition] data=" + LoginDialog.isLoginPermitted);
        }
        return LoginDialog.isLoginPermitted;
    }


    public static String getPayPermition(final String uniqueId, final String gameName, final String channel,Callback callback) {
        //isPayPermitted = "1";

        try {
            //1.创建OkHttpClient对象
            OkHttpClient client = new OkHttpClient();
            //2.创建RequestBody对象
            RequestBody requestBody = new FormBody.Builder()
                    .add("unique_id", uniqueId)
                    .add("game_name", gameName)
                    .add("channel", channel)
                    .build();
            //3.创建Request对象
            Request request = new Request.Builder()
                    .post(requestBody)
                    .url("https://" + ipAddress + ":10013/user/is_pay_permitted")
                    .build();
            //4. 同步请求
            // Response response = client.newCall(request).execute();
            //5.异步请求
            client.newCall(request).enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            LogLocal("[UserConfig][get_pay_permition] data=" + isPayPermitted);
        }

        return isPayPermitted;
    }

}
