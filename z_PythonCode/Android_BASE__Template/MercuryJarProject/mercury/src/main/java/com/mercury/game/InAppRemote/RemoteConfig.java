package com.mercury.game.InAppRemote;


import android.util.Log;

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
import static com.mercury.game.MercuryActivity.DeviceId;
import static com.mercury.game.MercuryActivity.GameName;
import static com.mercury.game.MercuryActivity.LogLocal;
import static com.mercury.game.util.Function.writeFileData;

//shrinkpartstart
//shrinkpartend

public final class RemoteConfig {
    private static String updating_result_json="";
    private static String iap_result_json="";
    private static String ip_address = "office.singmaan.com";
    public static void GetAllConfig()
    {
        if (DeviceId.equals("9836ae60d6cc3666"))
        {
            ip_address = "192.168.10.7";
            LogLocal("[RemoteConfig][GetAllConfig] testing mode, IP="+ip_address);
        }
        get_remote_iap();
        get_update_config();
    }
    public static String get_remote_iap() {
        //shrinkpartstart
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //1.创建OkHttpClient对象
                    OkHttpClient client = new OkHttpClient();
                    //2.创建RequestBody对象
                    RequestBody requestBody = new FormBody.Builder()
                            .add("username", "tom")
                            .add("password", "123")
                            .build();
                    //3.创建Request对象
                    Request request = new Request.Builder()
                            .post(requestBody)
                            .url("http://"+ip_address+":10001/get_iap?gamename=" + GameName)
                            .build();
                    //4. 同步请求
                    // Response response = client.newCall(request).execute();
                    //5.异步请求
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            LogLocal("[RemoteConfig][get_remote_iap] failed="+e.toString());
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String s = response.body().string();
                            if (s != null) {
                                writeFileData("get_remote_iap", s);
                                LogLocal("[RemoteConfig][get_remote_iap] success=" + s);
                                iap_result_json=s;
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            }
        }).start();
        //shrinkpartend
        return iap_result_json;
    }
    public static String get_update_config() {
        //shrinkpartstart
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //1.创建OkHttpClient对象
                    OkHttpClient client = new OkHttpClient();
                    //2.创建RequestBody对象
                    RequestBody requestBody = new FormBody.Builder()
                            .add("username", "tom")
                            .add("password", "123")
                            .build();
                    //3.创建Request对象
                    Request request = new Request.Builder()
                            .post(requestBody)
                            .url("http://"+ip_address+":10001/get_update_verify?gamename=" + GameName)
                            .build();
                    //4. 同步请求
                    // Response response = client.newCall(request).execute();
                    //5.异步请求
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            LogLocal("[RemoteConfig][RemoteConfig] failed="+e.toString());
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String s = response.body().string();
                            if (s != null) {
                                writeFileData("verifyGame", s);
                                LogLocal("[RemoteConfig][verifyGame] success=" + s);
                                updating_result_json = s;
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            }
        }).start();
        //shrinkpartend
        return updating_result_json;
    }
}