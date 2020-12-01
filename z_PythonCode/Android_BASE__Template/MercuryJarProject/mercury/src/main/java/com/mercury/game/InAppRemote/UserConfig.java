package com.mercury.game.InAppRemote;

import android.os.Looper;

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
    public static String isLoginPermitted = "0";
    private static String ipAddress = "gamesupportcluster.singmaan.com";

    public static String getLoginPermition(final String uniqueId, final String gameName, final String channel) {
        //isLoginPermitted = "0";
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
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
                            .url("https://"+ipAddress+":10013/user/is_login_permitted")
                            .build();
                    //4. 同步请求
                    // Response response = client.newCall(request).execute();
                    //5.异步请求
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            LogLocal("[UserConfig][get_login_permition] failed="+e.toString());
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String s = response.body().string();
                            if (s != null) {
                                JSONObject json = null;
                                try {
                                    json = (JSONObject) new JSONTokener(s).nextValue();
                                    isLoginPermitted = String.valueOf(json.getInt("data"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                LogLocal("[UserConfig][get_login_permition] result=" + isLoginPermitted);
                                LogLocal("[UserConfig][get_login_permition] remote result=" + s);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            }
        }).start();
        LogLocal("[UserConfig][get_login_permition] data=" + isLoginPermitted);
        return isLoginPermitted;
    }


    public static String getPayPermition(final String uniqueId, final String gameName, final String channel) {
        //isPayPermitted = "1";
        new Thread(new Runnable() {
            @Override
            public void run() {
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
                            .url("https://"+ipAddress+":10013/user/is_pay_permitted")
                            .build();
                    //4. 同步请求
                    // Response response = client.newCall(request).execute();
                    //5.异步请求
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            LogLocal("[UserConfig][get_pay_permition] failed="+e.toString());
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String s = response.body().string();
                            if (s != null) {
                                JSONObject json = null;
                                try {
                                    json = (JSONObject) new JSONTokener(s).nextValue();
                                    isPayPermitted = String.valueOf((Integer) json.getInt("data"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                LogLocal("[UserConfig][get_pay_permition] data=" + isPayPermitted);
                                LogLocal("[UserConfig][get_pay_permition] remote result=" + s);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            }
        }).start();
        return isPayPermitted;
    }

}
