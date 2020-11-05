package com.mercury.game.InAppRemote;


import android.os.Looper;
import android.util.Log;

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
import static com.mercury.game.MercuryActivity.DeviceId;
import static com.mercury.game.MercuryActivity.GameName;
import static com.mercury.game.MercuryActivity.LogLocal;
import static com.mercury.game.MercuryActivity.mInAppBase;
import static com.mercury.game.util.Function.writeFileData;

//shrinkpartstart
//shrinkpartend

public final class RemoteConfig {
    public static String updating_result_json="";
    public static String game_data_result="";
    public static String iap_result_json="";
    public static String id_verify_result="";
    public static String id_signe_in_result="";
    public static String login_in_result="";
    public static String chinese_id_update_result="";
    public static String chinese_id="";
    public static String account_id="";
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
        iap_result_json="";
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
        updating_result_json="";
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

    public static String upload_game_data(final String data ) {
        game_data_result = data;
        //shrinkpartstart
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    //1.创建OkHttpClient对象
                    OkHttpClient client = new OkHttpClient();
                    //2.创建RequestBody对象
                    RequestBody requestBody = new FormBody.Builder()
                            .add("gamename", GameName)
                            .add("unique_id", DeviceId)
                            .add("data", data)
                            .build();
                    //3.创建Request对象
                    Request request = new Request.Builder()
                            .post(requestBody)
                            .url("https://"+ip_address+":10010/uploadgamedata")
                            .build();
                    //4. 同步请求
                    // Response response = client.newCall(request).execute();
                    //5.异步请求
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            LogLocal("[RemoteConfig][upload_game_data] failed="+e.toString());
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String s = response.body().string();
                            if (s != null) {
                                LogLocal("[RemoteConfig][upload_game_data] data=" + game_data_result);
                                game_data_result = s;
                                LogLocal("[RemoteConfig][upload_game_data] success=" + s);
                                Looper.prepare();
                                //shrinkpartend
                                mInAppBase.onFunctionCallBack("UploadGameData:"+game_data_result);
                                //shrinkpartstart
                                Looper.loop();

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
        return game_data_result;
    }

    public static String download_game_data() {
        game_data_result="";
        //shrinkpartstart
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                try {
                    //1.创建OkHttpClient对象
                    OkHttpClient client = new OkHttpClient();
                    //2.创建RequestBody对象
                    RequestBody requestBody = new FormBody.Builder()
                            .add("gamename", GameName)
                            .add("unique_id", DeviceId)
                            .build();
                    //3.创建Request对象
                    Request request = new Request.Builder()
                            .post(requestBody)
                            .url("https://"+ip_address+":10010/downloadgamedata")
                            .build();
                    //4. 同步请求
                    // Response response = client.newCall(request).execute();
                    //5.异步请求
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            LogLocal("[RemoteConfig][download_game_data] failed="+e.toString());
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String s = response.body().string();
                            if (s != null) {
                                JSONObject json = null;
                                try {
                                    json = (JSONObject) new JSONTokener(s).nextValue();
                                    JSONObject json_result = json.getJSONObject("data");
                                    game_data_result = (String) json_result.get("result");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                LogLocal("[RemoteConfig][download_game_data] data=" + game_data_result);
                                LogLocal("[RemoteConfig][download_game_data] remote result=" + s);

                                Looper.prepare();
                                //shrinkpartend
                                mInAppBase.onFunctionCallBack("DownloadGameData:"+game_data_result);
                                //shrinkpartstart
                                Looper.loop();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
                Looper.loop();
            }
        }).start();
        //shrinkpartend
        return game_data_result;
    }

    public static String verify_chinese_id(final String account_id, final String my_id, final String my_chinese_name) {
        id_verify_result="";
        //shrinkpartstart
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                try {
                    //1.创建OkHttpClient对象
                    OkHttpClient client = new OkHttpClient();
                    //2.创建RequestBody对象
                    RequestBody requestBody = new FormBody.Builder()
                            .add("account_id", account_id)
                            .add("chinese_id", my_id)
                            .add("chinese_name", my_chinese_name)
                            .build();
                    //3.创建Request对象
                    Request request = new Request.Builder()
                            .post(requestBody)
                            .url("https://"+ip_address+":10011/verify_chinese_id")
                            .build();
                    //4. 同步请求
                    // Response response = client.newCall(request).execute();
                    //5.异步请求
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            LogLocal("[RemoteConfig][verify_chinese_id] failed="+e.toString());
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String s = response.body().string();
                            if (s != null) {
                                JSONObject json = null;
                                try {
                                    json = (JSONObject) new JSONTokener(s).nextValue();
                                    id_verify_result = (String) json.getString("status");
//                                    id_verify_result = (String) json_result.get("result");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                LogLocal("[RemoteConfig][verify_chinese_id] data=" + id_verify_result);
                                LogLocal("[RemoteConfig][verify_chinese_id] remote result=" + s);

                                Looper.prepare();
                                //shrinkpartend
//                                mInAppBase.onFunctionCallBack("VerifyChineseID:"+id_verify_result);
                                //shrinkpartstart
                                Looper.loop();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
                Looper.loop();
            }
        }).start();
        //shrinkpartend
        return id_verify_result;
    }

    public static String verify_signe_in(final String account, final String password) {
        //shrinkpartstart
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                try {
                    //1.创建OkHttpClient对象
                    OkHttpClient client = new OkHttpClient();
                    //2.创建RequestBody对象
                    RequestBody requestBody = new FormBody.Builder()
                            .add("account", account)
                            .add("password", password)
                            .build();
                    //3.创建Request对象
                    Request request = new Request.Builder()
                            .post(requestBody)
                            .url("https://"+ip_address+":10012/signeinwithpassword")
                            .build();
                    //4. 同步请求
                    // Response response = client.newCall(request).execute();
                    //5.异步请求
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            LogLocal("[RemoteConfig][verify_signe_in] failed="+e.toString());
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String s = response.body().string();
                            if (s != null) {
                                JSONObject json = null;
                                try {
                                    json = (JSONObject) new JSONTokener(s).nextValue();
                                    id_signe_in_result = (String) json.getString("status");
//                                    id_verify_result = (String) json_result.get("result");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                LogLocal("[RemoteConfig][verify_signe_in] data=" + id_signe_in_result);
                                LogLocal("[RemoteConfig][verify_signe_in] remote result=" + s);
                                Looper.prepare();
                                //shrinkpartend
//                                mInAppBase.onFunctionCallBack("VerifyChineseID:"+id_verify_result);
                                //shrinkpartstart
                                Looper.loop();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
                Looper.loop();
            }
        }).start();
        //shrinkpartend
        return id_signe_in_result;
    }

    public static String login_in(final String account, final String password) {
        //shrinkpartstart
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                try {
                    //1.创建OkHttpClient对象
                    OkHttpClient client = new OkHttpClient();
                    //2.创建RequestBody对象
                    RequestBody requestBody = new FormBody.Builder()
                            .add("account", account)
                            .add("password", password)
                            .build();
                    //3.创建Request对象
                    Request request = new Request.Builder()
                            .post(requestBody)
                            .url("https://"+ip_address+":10012/loginwithpassword")
                            .build();
                    //4. 同步请求
                    // Response response = client.newCall(request).execute();
                    //5.异步请求
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            LogLocal("[RemoteConfig][login_in] failed="+e.toString());
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String s = response.body().string();
                            if (s != null) {
                                JSONObject json = null;
                                try {
                                    json = (JSONObject) new JSONTokener(s).nextValue();
                                    login_in_result = (String) json.getString("status");

                                    json = (JSONObject) new JSONTokener(s).nextValue();
                                    String json_result = (String)json.getString("data");

                                    json = (JSONObject) new JSONTokener(json_result).nextValue();
                                    String json_result1 = (String)json.getString("result");

                                    json = (JSONObject) new JSONTokener(json_result1).nextValue();
                                    String json_result2 = (String)json.getString("chineseid");

                                    chinese_id = json_result2;
                                    LogLocal("[RemoteConfig][login_in] chineseid=" + chinese_id);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                LogLocal("[RemoteConfig][login_in] data=" + login_in_result);
                                LogLocal("[RemoteConfig][login_in] remote result=" + s);
                                Looper.prepare();
                                //shrinkpartend
//                                mInAppBase.onFunctionCallBack("VerifyChineseID:"+id_verify_result);
                                //shrinkpartstart
                                Looper.loop();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
                Looper.loop();
            }
        }).start();
        //shrinkpartend
        return login_in_result;
    }

    public static String update_chinese_id(final String account, final String chinese_id) {
        //shrinkpartstart
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                try {
                    //1.创建OkHttpClient对象
                    OkHttpClient client = new OkHttpClient();
                    //2.创建RequestBody对象
                    RequestBody requestBody = new FormBody.Builder()
                            .add("account", account)
                            .add("chinese_id", chinese_id)
                            .build();
                    //3.创建Request对象
                    Request request = new Request.Builder()
                            .post(requestBody)
                            .url("https://"+ip_address+":10012/updatechineseid")
                            .build();
                    //4. 同步请求
                    // Response response = client.newCall(request).execute();
                    //5.异步请求
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            LogLocal("[RemoteConfig][lgoin_in] failed="+e.toString());
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String s = response.body().string();
                            if (s != null) {
                                JSONObject json = null;
                                try {
                                    json = (JSONObject) new JSONTokener(s).nextValue();
                                    chinese_id_update_result = (String) json.getString("status");

//                                    json = (JSONObject) new JSONTokener(s).nextValue();
//                                    String json_result = (String)json.getString("data");
//
//                                    json = (JSONObject) new JSONTokener(json_result).nextValue();
//                                    String json_result1 = (String)json.getString("result");
//
//                                    json = (JSONObject) new JSONTokener(json_result1).nextValue();
//                                    String json_result2 = (String)json.getString("chineseid");
//
//                                    chinese_id = json_result2;
//                                    LogLocal("[RemoteConfig][lgoin_in] chineseid=" + chinese_id);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                LogLocal("[RemoteConfig][lgoin_in] data=" + chinese_id_update_result);
                                LogLocal("[RemoteConfig][lgoin_in] remote result=" + s);
                                Looper.prepare();
                                //shrinkpartend
//                                mInAppBase.onFunctionCallBack("VerifyChineseID:"+id_verify_result);
                                //shrinkpartstart
                                Looper.loop();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
                Looper.loop();
            }
        }).start();
        //shrinkpartend
        return chinese_id_update_result;
    }


}