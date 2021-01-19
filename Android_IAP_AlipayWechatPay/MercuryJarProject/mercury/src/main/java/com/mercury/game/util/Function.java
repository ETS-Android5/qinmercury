package com.mercury.game.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Looper;
import android.text.InputFilter;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.mercury.game.MercuryActivity;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
//shrinkpartstart
import org.apache.http.util.EncodingUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
//shrinkpartend
import static com.mercury.game.MercuryActivity.GameName;
import static com.mercury.game.MercuryActivity.LogLocal;
import static com.mercury.game.MercuryActivity.mActivity;
import static com.mercury.game.MercuryActivity.mContext;

public final class Function {
    private static String result_json;
    private static String iap_result_json;
    private static String ipAddress = "gamesupportcluster.singmaan.com";
    public static void writeFileData(String fileName, String message) {
        //shrinkpartstart
        DataUtil.storageData(MercuryActivity.mContext,fileName,message);
        //shrinkpartend
    }
    public static String readFileData(String fileName) {
        String res="";
        //shrinkpartstart
        res = DataUtil.getData(MercuryActivity.mContext,fileName,"");
        //shrinkpartend
        return res;
    }
    public static void VerifyGame(final String gamename) {
        //shrinkpartstart
        int remote_version = 0;
        String remote_dialog_message = "";
        String remote_dialog_title = "";
        String remote_url = "";
        //get remote version
        String remote_config = readFileData("verifyGame");
        LogLocal("[MercuryActivity][verifyGame] read remote_config from local =" + remote_config);
        try {
            JSONObject json = (JSONObject) new JSONTokener(remote_config).nextValue();
            JSONObject json_data = json.getJSONObject("data");
            JSONObject json_result = json_data.getJSONObject("result");
            remote_version = Integer.parseInt((String) json_result.get("version"));
            remote_url = (String) json_result.get("url");
            remote_dialog_message = (String) json_result.get("message");
            remote_dialog_title = (String) json_result.get("titile");
        } catch (JSONException e) {
            LogLocal("[Function][VerifyGame] error="+e.toString());
            e.printStackTrace();
        }
        //default value
        int local_version = 0;
        String local_dialog_message = "检测到新版本";
        String local_dialog_title = "更新游戏体验有更多游戏内容";
        String local_url = "http://www.singmaan.com";
        String display_dialog_message = "";
        String display_dialog_titile = "";
        String display_url = "";
        if (remote_dialog_message.equals("") == false) {
            display_dialog_message = remote_dialog_message;
            display_dialog_titile = remote_dialog_title;
            display_url = remote_url;
        } else {
            display_dialog_message = local_dialog_message;
            display_dialog_titile = local_dialog_title;
            display_url = local_url;
        }
        //get apk version
        try {
            PackageManager manager = MercuryActivity.mContext.getPackageManager();
            PackageInfo info = manager.getPackageInfo(MercuryActivity.mContext.getPackageName(), 0);
            local_version = info.versionCode;
            LogLocal("[Function][VerifyGame] local_version="+local_version+"｜remote_version="+remote_version);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (remote_version > local_version)
        //have new version
        {
            LogLocal("[Function][VerifyGame] displaying updating pop");
            AlertDialog.Builder builder = new AlertDialog.Builder(MercuryActivity.mContext);
            builder.setMessage(display_dialog_titile);
            builder.setTitle(display_dialog_message);
            final String finalRemote_url = display_url;
            builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(finalRemote_url);//此处填链接
                    intent.setData(content_url);
                    MercuryActivity.mContext.startActivity(intent);
                    ((Activity) MercuryActivity.mContext).finish();
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            });
            builder.setNeutralButton("下次更新,继续游戏", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                            ((Activity) MercuryActivity.mContext).finish();
//                            android.os.Process.killProcess(android.os.Process.myPid());
                }
            });
            builder.setCancelable(false);
            builder.create().show();
        }
        //shrinkpartend
    }




    public static String post_redeem_code(final String redeem_code) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //shrinkpartstart
                    //1.创建OkHttpClient对象
                    OkHttpClient client = new OkHttpClient();
                    //2.创建RequestBody对象
                    RequestBody requestBody = new FormBody.Builder()
                            .add("gamename", GameName)
                            .add("redeemcode", redeem_code)
                            .build();
                    //3.创建Request对象
                    Request request = new Request.Builder()
                            .post(requestBody)
                            .url("https://"+ipAddress+":10007/redeem")
                            .build();
                    //4. 同步请求
                    // Response response = client.newCall(request).execute();
                    //5.异步请求
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            LogLocal("[Function][post_redeem_code] failed e="+e.toString());
                            Looper.prepare();
                            if(!NetCheckUtil.checkNet(mContext)){
                                Toast.makeText(mContext, "网络未连接", Toast.LENGTH_SHORT).show();
                            }
                            Looper.loop();
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            //shrinkpartend
                            Looper.prepare();
                            //shrinkpartstart
                            result_json = response.body().string();
                            if (result_json != null) {
                                LogLocal("[Function][post_redeem_code] success=" + result_json);
                                JSONObject json = null;
                                try {
                                    json = (JSONObject) new JSONTokener(result_json).nextValue();
                                    int return_value = (int)json.get("status");
                                    if (return_value==200)
                                    {
                                        JSONObject json_data = json.getJSONObject("data");
                                        //shrinkpartend
                                        String redeem_code_result = redeem_code;
                                        //shrinkpartstart
                                        redeem_code_result = (String) json_data.get("result");
                                        //shrinkpartend
                                        LogLocal("[Function][post_redeem_code] redeem_code_result=" + redeem_code_result);
                                        InAppBase.appinterface.onFunctionCallBack(redeem_code_result);
                                        //shrinkpartstart
                                    }
                                    else if(return_value==201)
                                    {
                                        InAppBase.appinterface.onFunctionCallBack("-1");
                                    }
                                    else if(return_value==400)
                                    {
                                        InAppBase.appinterface.onFunctionCallBack("-1");
                                    }
                                    else if(return_value==401)
                                    {
                                        InAppBase.appinterface.onFunctionCallBack("-1");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            //shrinkpartend
                            Looper.loop();
                            //shrinkpartstart
                        }
                    });
                    //shrinkpartend
                }
                catch (Exception e)
                {
                    LogLocal("[Function][post_redeem_code] failed=e="+e.toString());
                    e.printStackTrace();
                }
                finally
                {
                }

            }
        }).start();
        return result_json;
    }

    public static void redeemCode() {
        // TODO Auto-generated method stub
        final EditText inputServer = new EditText(((Activity) MercuryActivity.mContext));
        inputServer.setFilters(new InputFilter[] { new InputFilter.LengthFilter(10) });
        AlertDialog.Builder builder = new AlertDialog.Builder(((Activity) MercuryActivity.mContext));
        builder.setTitle("兑换中心").setView(inputServer).setNegativeButton("取消", null);
        builder.setPositiveButton("确定",new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which) {
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Looper.prepare();
                        // TODO Auto-generated method stub
						String text = inputServer.getText().toString();
                        post_redeem_code(text);
                        Looper.loop();
                    }
                }).start();
            }
        });
        builder.show();
    }

}