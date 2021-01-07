package com.mercury.game.InAppRemote;


import android.os.Looper;
import android.util.Log;

import com.mercury.game.InAppDialog.LoginDialog;
import com.mercury.game.util.InAppBase;

import org.json.JSONArray;
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
import static com.mercury.game.MercuryApplication.channelname;
import static com.mercury.game.util.Function.writeFileData;
import static com.mercury.game.util.UIUtils.isJSONValid;


public final class RemoteConfig {
    public static String updating_result_json = "";
    public static String game_data_result = "";
    public static String iap_result_json = "";
    public static String id_verify_result = "";
    public static String id_signe_in_result = "";
    public static String login_in_result = "";
    public static String chinese_id_update_result = "";
    public static String chinese_id = "";
    public static String account_id = "default";
    public static String global_orderId ="";
    public static String global_user_id ="";
    public static String global_production_id ="";
    public static float global_total_payment = 0;
//    private static String ip_address = "gamesupportcluster.singmaan.com";
    public static String ip_address = "gamesupporttest.singmaan.com";
    private static String RESTORE_URL = "https://"+ip_address+":10013/order/undelivered?";
    private static String UPDATE_ORDER_SUCCESS_URL = "https://"+ip_address+":10013/order/deliver";
    private static String GET_REFUNDED_ORDER_URL = "https://"+ip_address+":10013/order/refunded?";
    private static String CANCEL_ORDER_URL = "https://"+ip_address+":10013/order/cancel";
    private static String GET_TOTAL_AMOUNT_URL = "https://"+ip_address+":10013/order/totalpayment?";
    public static void GetAllConfig() {
        get_remote_iap();
        get_update_config();
    }

    public static String get_remote_iap() {
        iap_result_json = "";
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
                            .url("https://" + ip_address + ":10009/get_iap?gamename=" + GameName)
                            .build();
                    //4. 同步请求
                    // Response response = client.newCall(request).execute();
                    //5.异步请求
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            LogLocal("[RemoteConfig][get_remote_iap] failed=" + e.toString());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String s = response.body().string();
                            if (s != null) {
                                writeFileData("get_remote_iap", s);
                                LogLocal("[RemoteConfig][get_remote_iap] success=" + s);
                                iap_result_json = s;
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
        updating_result_json = "";
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
                            .url("https://" + ip_address + ":10009/get_update_verify?gamename=" + GameName)
                            .build();
                    //4. 同步请求
                    // Response response = client.newCall(request).execute();
                    //5.异步请求
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            LogLocal("[RemoteConfig][RemoteConfig] failed=" + e.toString());
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

    public static String upload_game_data(final String data) {
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
                            .url("https://" + ip_address + ":10010/uploadgamedata")
                            .build();
                    //4. 同步请求
                    // Response response = client.newCall(request).execute();
                    //5.异步请求
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            LogLocal("[RemoteConfig][upload_game_data] failed=" + e.toString());
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
                                mInAppBase.onFunctionCallBack("UploadGameData:" + game_data_result);
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
        game_data_result = "";
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
                            .url("https://" + ip_address + ":10010/downloadgamedata")
                            .build();
                    //4. 同步请求
                    // Response response = client.newCall(request).execute();
                    //5.异步请求
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            LogLocal("[RemoteConfig][download_game_data] failed=" + e.toString());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String s = response.body().string();
                            if (s != null&&isJSONValid(s)) {
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
                                mInAppBase.onFunctionCallBack("DownloadGameData:" + game_data_result);
                                //shrinkpartstart
                                Looper.loop();
                            }
                            else
                            {
                                LogLocal("[RemoteConfig][verify_signe_in] server returned formate is not a json");
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
    //shrinkpartstart
    public static String verify_chinese_id(final String account_id, final String my_id, final String my_chinese_name, final Callback callback) {
        id_verify_result = "";

        LogLocal("[RemoteConfig][verify_chinese_id]" + "https://" + ip_address + ":10011/verify_chinese_id");
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
                            .url("https://" + ip_address + ":10011/verify_chinese_id")
                            .build();
                    //4. 同步请求
                    // Response response = client.newCall(request).execute();
                    //5.异步请求
                    client.newCall(request).enqueue(callback);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
                Looper.loop();
            }
        }).start();

        return id_verify_result;
    }
    //shrinkpartend
    //shrinkpartstart
    public static String verify_signe_in(final String account, final String password, final Callback callback) {

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
                            .url("https://" + ip_address + ":10012/signeinwithpassword")
                            .build();
                    //4. 同步请求
                    // Response response = client.newCall(request).execute();
                    //5.异步请求
                    client.newCall(request).enqueue(callback);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
                Looper.loop();
            }
        }).start();

        return id_signe_in_result;
    }
    //shrinkpartend

    //shrinkpartstart
    public static String login_in(final String account, final String password, final Callback callback) {

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
                            .url("https://" + ip_address + ":10012/loginwithpassword")
                            .build();
                    //4. 同步请求
                    // Response response = client.newCall(request).execute();
                    //5.异步请求
                    client.newCall(request).enqueue(callback);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
                Looper.loop();
            }
        }).start();

        return login_in_result;
    }
    //shrinkpartend
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
                            .url("https://" + ip_address + ":10012/updatechineseid")
                            .build();
                    //4. 同步请求
                    // Response response = client.newCall(request).execute();
                    //5.异步请求
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            LogLocal("[RemoteConfig][lgoin_in] failed=" + e.toString());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String s = response.body().string();
                            if (s != null&&isJSONValid(s)) {
                                JSONObject json = null;
                                try {
                                    json = (JSONObject) new JSONTokener(s).nextValue();
                                    chinese_id_update_result = (String) json.getString("status");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                LogLocal("[RemoteConfig][lgoin_in] data=" + chinese_id_update_result);
                                LogLocal("[RemoteConfig][lgoin_in] remote result=" + s);
                                Looper.prepare();
                                //shrinkpartend
                                mInAppBase.onFunctionCallBack("VerifyChineseID:"+id_verify_result);
                                //shrinkpartstart
                                Looper.loop();
                            }
                            else
                            {
                                LogLocal("[RemoteConfig][verify_signe_in] server returned formate is not a json");
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

    public static void set_login_time(final String account, final String time) {
        //shrinkpartstart
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                try {
                    //1.创建OkHttpClient对象
                    OkHttpClient client = new OkHttpClient();
                    //2.创建RequestBody对象
                    String url = "https://"+ip_address+":10012"+"/setlogintime";
                    RequestBody requestBody = new FormBody.Builder()
                            .add("account", account)
                            .add("time", time)
                            .build();
                    //3.创建Request对象
                    Request request = new Request.Builder()
                            .post(requestBody)
                            .url(url)
                            .build();
                    //4. 同步请求
                    // Response response = client.newCall(request).execute();
                    //5.异步请求
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            LogLocal("[RemoteConfig][set_login_time] failed="+e.toString());
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String s = response.body().string();
                            if (s != null) {
                                JSONObject json = null;
                                try {
                                    json = (JSONObject) new JSONTokener(s).nextValue();
                                    LogLocal("[RemoteConfig][set_login_time] json=" + json);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                LogLocal("[RemoteConfig][set_login_time] remote result=" + s);
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
    }

    public static void get_login_time(final String account) {
        //shrinkpartstart
        if(LoginDialog.Instance.play_time !=""){
            return;
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //1.创建OkHttpClient对象
                    OkHttpClient client = new OkHttpClient();
                    //2.创建RequestBody对象
                    RequestBody requestBody = new FormBody.Builder()
                            .add("account", account)
                            .build();
                    String url = "https://"+ip_address+":10012"+"/getlogintime";
                    //3.创建Request对象
                    Request request = new Request.Builder()
                            .post(requestBody)
                            .url(url)
                            .build();
                    //4. 同步请求
                    // Response response = client.newCall(request).execute();
                    //5.异步请求
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            LogLocal("[RemoteConfig][get_login_time] failed="+e.toString());
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String s = response.body().string();
                            if (s != null) {
                                JSONObject json = null;
                                try {
                                    json = (JSONObject) new JSONTokener(s).nextValue();
                                    LogLocal("[RemoteConfig][origin_login_time] remote json=" + json);
                                    LoginDialog.Instance.play_time = (String) json.getString("data");
                                    if (LoginDialog.Instance.play_time != null && !LoginDialog.Instance.play_time.equals("")) {
                                        //do something
                                    }
                                    else {
                                        LoginDialog.Instance.play_time = "0";
                                    }
                                    //开始倒计时
                                    LoginDialog.Instance.age_difference(LoginDialog.Instance.play_time);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                LogLocal("[RemoteConfig][origin_login_time] LoginDialog.Instance.play_time=>" + LoginDialog.Instance.play_time);
                                LogLocal("[RemoteConfig][origin_login_time] remote result=" + s);
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
    }
    public static void Restore() {
        //shrinkpartstart
        final String url = String.format(RESTORE_URL+"user_id=%s&game_name=%s&channel=%s",DeviceId,GameName.toLowerCase(),channelname);
        LogLocal("[RemoteConfig][Restore] RESTORE_URL="+url);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                OkHttpClient client = new OkHttpClient();
                Request request = new  Request.Builder().url(url).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogLocal("[RemoteConfig][Restore] error 2:"+e.getMessage());
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String s = response.body().string();
                        if (s!=null){
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                JSONArray array = jsonObject.getJSONArray("data");
                                int size = array.length();
                                LogLocal("[RemoteConfig][Restore] data size:"+size+",data="+s);
                                Looper.prepare();
                                for (int i = 0; i < size; i++) {
                                    JSONObject order = array.getJSONObject(i);
                                    global_user_id = order.getString("user_id");
                                    global_orderId = order.getString("order_id");
                                    global_production_id = order.getString("production_id");
                                    if ((global_user_id!=null && global_orderId!=null)||(global_user_id!="" && global_orderId!="")){
                                        mInAppBase = new InAppBase();
                                        mInAppBase.onPurchaseSuccess(global_production_id);
                                        UpdateOrderSuccess(global_user_id,global_orderId);
                                        LogLocal("[RemoteConfig][Restore] update success");
                                    }
                                }
                                Looper.loop();
                            } catch (Exception e) {
                                LogLocal("[InAppChannel][restore] update error:"+e.getMessage());
                            }
                        }
                    }
                });
                Looper.loop();
            }
        }).start();
        //shrinkpartend
    }
    public static void UpdateOrderSuccess(final String userId, final String orderId){
        //shrinkpartstart
        if(orderId.equals("")||userId.equals(""))
        {
            LogLocal("[RemoteConfig][UpdateOrderSuccess]format error");
            return;
        }
        LogLocal("[RemoteConfig][UpdateOrderSuccess]UPDATE_ORDER_SUCCESS_URL="+UPDATE_ORDER_SUCCESS_URL);
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody=new FormBody.Builder().
                add("user_id",userId).
                add("order_id",orderId).
                add("game_name",GameName.toLowerCase()).
                add("channel",channelname)
                .build();
        LogLocal("[RemoteConfig][UpdateOrderSuccess]user_id="+userId);
        LogLocal("[RemoteConfig][UpdateOrderSuccess]orderId="+orderId);
        LogLocal("[RemoteConfig][UpdateOrderSuccess]GameName.toLowerCase()="+GameName.toLowerCase());
        LogLocal("[RemoteConfig][UpdateOrderSuccess]channelname="+channelname);

        Request request=new  Request.Builder().url(UPDATE_ORDER_SUCCESS_URL).post(formBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogLocal("[InAppChannel][updateOrderStatus] result error:"+e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                LogLocal("[InAppChannel][updateOrderStatus] result:"+s);
            }
        });
        //shrinkpartend
    }
    public static void GetRefundedOrder(){
        //shrinkpartstart
        final String url = String.format(GET_REFUNDED_ORDER_URL+"user_id=%s&game_name=%s&channel=%s",DeviceId,GameName.toLowerCase(),channelname);
        LogLocal("[RemoteConfig][GetRefundedOrder] GET_REFUNDED_ORDER_URL:"+url);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                OkHttpClient client = new OkHttpClient();
                Request request = new  Request.Builder().url(url).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogLocal("[RemoteConfig][GetRefundedOrder] error:"+e.getMessage());
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String s = response.body().string();
                        Looper.prepare();
                        if (s!=null){
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                JSONArray array = jsonObject.getJSONArray("data");
                                int size = array.length();
                                LogLocal("[RemoteConfig][GetRefundedOrder] data size:"+size+", data="+s);
                                for (int i = 0; i < size; i++) {
                                    JSONObject order = array.getJSONObject(i);
                                    global_user_id = order.getString("user_id");
                                    global_orderId = order.getString("order_id");
                                    global_production_id = order.getString("production_id");
                                    if ((global_user_id!=null && global_orderId!=null)||(global_user_id!="" &&global_production_id!="")){
                                        CancelOrder(global_user_id,global_orderId);
                                        mInAppBase = new InAppBase();
                                        mInAppBase.onPurchaseFailed(global_production_id);
                                        LogLocal("[RemoteConfig][GetRefundedOrder] cancel success");
                                    }
                                }
                                Looper.loop();
                            } catch (Exception e) {
                                LogLocal("[RemoteConfig][GetRefundedOrder] exception:"+e.getMessage());
                            }
                        }

                    }
                });
                Looper.loop();
            }
        }).start();
        //shrinkpartend
    }
    public static void CancelOrder(final String userId, final String orderId){
        //shrinkpartstart
        LogLocal("[RemoteConfig][CancelOrder]CANCEL_ORDER_URL="+CANCEL_ORDER_URL);
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody=new FormBody.Builder().
                add("user_id",userId).
                add("order_id",orderId).
                add("game_name",GameName.toLowerCase()).
                add("channel",channelname)
                .build();
        Request request=new  Request.Builder().url(CANCEL_ORDER_URL).post(formBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogLocal("[RemoteConfig][CancelOrder] result error:"+e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                LogLocal("[RemoteConfig][CancelOrder] result:"+s);
            }
        });
        //shrinkpartend
    }
    public static void TotalPayment() {
        //shrinkpartstart
        final String url = String.format(GET_TOTAL_AMOUNT_URL+"user_id=%s&game_name=%s&channel=%s",DeviceId,GameName.toLowerCase(),channelname);
        LogLocal("[RemoteConfig][TotalPayment] GET_TOTAL_AMOUNT_URL="+url);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                OkHttpClient client = new OkHttpClient();
                Request request = new  Request.Builder().url(url).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogLocal("[RemoteConfig][TotalPayment] error 2:"+e.getMessage());
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String s = response.body().string();
                        if (s!=null){
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                LogLocal("[RemoteConfig][TotalPayment] data="+s);
                                JSONArray array = jsonObject.getJSONArray("data");
                                int size = array.length();
                                Looper.prepare();
                                for (int i = 0; i < size; i++) {
                                    JSONObject order = array.getJSONObject(i);
                                    String m_payment = order.getString("SUM(price)");
                                    if(m_payment!=null && !m_payment.equals("")) {
                                        global_total_payment = Float.parseFloat(m_payment);
                                    }
                                    LogLocal("[RemoteConfig][TotalPayment] global_total_payment:"+global_total_payment);
                                }

                                Looper.loop();
                            } catch (Exception e) {
                                LogLocal("[RemoteConfig][TotalPayment] update error:"+e.getMessage());
                            }
                        }
                    }
                });
                Looper.loop();
            }
        }).start();
        //shrinkpartend
    }
}