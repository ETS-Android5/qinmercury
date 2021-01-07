package com.mercury.game.InAppChannel;

import com.alipay.sdk.app.PayTask;
import com.mercury.game.InAppDialog.IDCardVerifyDialog;
import com.mercury.game.InAppDialog.LoginDialog;
import com.mercury.game.InAppDialog.PaymentDialog;
import com.mercury.game.InAppDialog.PrivacyDialog;
import com.mercury.game.InAppDialog.SigneInDialog;
import com.mercury.game.InAppRemote.RemoteConfig;
import com.mercury.game.InAppRemote.UserConfig;
import com.mercury.game.util.APPBaseInterface;
import com.mercury.game.util.InAppBase;

//comment
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import com.mercury.game.MercuryActivity;
import com.mercury.game.util.LoginCallBack;
import com.mercury.game.util.MD5;
import com.mercury.game.util.MercuryConst;
import com.mercury.game.util.NetCheckUtil;
import com.mercury.game.util.PayMethodCallBack;
import com.mercury.game.util.SPUtils;
import com.mercury.game.util.SpConfig;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
//shrinkpartstart
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
//shrinkpartend
import static com.mercury.game.InAppChannel.Util.httpPost;
import static com.mercury.game.InAppDialog.LoginDialog.local_age;
import static com.mercury.game.InAppRemote.RemoteConfig.chinese_id;
import static com.mercury.game.InAppRemote.RemoteConfig.download_game_data;
import static com.mercury.game.InAppRemote.RemoteConfig.global_total_payment;
import static com.mercury.game.MercuryActivity.DeviceId;
import static com.mercury.game.MercuryActivity.GameName;
import static com.mercury.game.MercuryActivity.LogLocal;
import static com.mercury.game.MercuryActivity.order_id;
import static com.mercury.game.MercuryApplication.channelname;
import static com.mercury.game.util.Function.readFileData;
import static com.mercury.game.util.Function.redeemCode;
import static com.mercury.game.util.Function.writeFileData;
import static com.mercury.game.util.MercuryConst.GlobalProductionList;
import static com.mercury.game.util.UIUtils.isJSONValid;


public class InAppChannel extends InAppBase {

    private String Channelname = "InAppChannel";

    private static String pid;
    public PayReq req;
    private IWXAPI msgApi;
    public  Map<String,String> resultunifiedorder;
    public  StringBuffer sb;
    public static final String APPID = "2021001193697300";
    public static final String RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCgLjeGu1WoskYfyOxt9CA58MrO/LxpfZNGbNkhII5iO5t9dWPPUD8Zugt32dVPuNTHkJeZc9X8EvL9DDKaRzGC4EqT3BaOuUblblKZ5ItzEOyBLKKqGUR83n99F2oFaohqbbNMDUkwPQUPv4Sjkl2cuNXR5OPmpinOUKDVVdQ8bldlgWlxxT6iy9c4LfqDTmOro5yzMXA0ET34eC1Uvj1rB+KafkKbAWSOgbR6F6rgKkdn7BWU66e7nEhATI6xutessw7kYT8feywT+Os0Za4szXEWUFwU2losfH2GqgoWcwBZ4GL4Hb7S+GYXdA7XFFwf1WmC1o1BEhmGY0fN4g6JAgMBAAECggEAf06cNQn4953Q2/w95NnNLx+woLgAKztx7Nwf6hNM9sf3Ocwt6pwVuqXB7ZyEy9rTylSiGIUXAkQxOWsTYMjKkgEfZMrcZszciwaWwdcB+g7uWXAXTGfOpgvUeaA9VFaqWyQbB4vbqmok9rI5giOXITNKRYrMkwlnWqF8YnHXv7qOAo4RCuh+1cKb4Ckqxeok2fWEIaBdS1xO4+WcHXac1J/aX4eZgR1iG3nlvor9ZQOZn+30QxO9wEJ69+4OLevQfBTAb9pcLXrFkkvPHbUFdOSgZKlcHjoCdzNotDdKliGQW4OGmcuCP/ERVN5fpBc/WvMBe78PDdT1p03Uc0BkzQKBgQD5PtARWzSMFM1DsC37Q/P6EfvmyupF5u3Stps2uYPSlLkenJFO8zYHnF3MaU9g9MKn9nwImMuMIDlkE5NVFcATbvePKqPJsVWCFMCXuUVlQ21TFQO1xc3aYdhEQHvxEh/laFmazWGCYw9ork2DAY/jKjLDz9pxhcHN1zDmRHUjJwKBgQCkhX/58vvkRuiiHWB+4YOneQnQxrE+MCwSovQggA4oWk10DglfzGLSslXp//XLsj15jUmOgD3ZNRFWiBDkCC9dA9WTGNBiuuNNrlCS7yF37rUplUMmevonTvR0SyYlfl6ahdo5yXXAfz9w2eTTw/yapRvNG5C4QW4clsSBd92OzwKBgCTvmgX4biEUNBcD1MyXlWBJqfrZtz4Eqtm/FeFWPKLIR2ax7Ra2FBusoHnaYVkM7IvXiyn6+q8ZV2ftPrgtMPmwSB9/QiZxkSplyOSzIAWRqHHXe2VEmuzx8wqqQ7PF69QjUqQOK5UW+QGaUwJHCPuxFTTPaJ/KIp5OdYCqRHGhAoGBAJregIXNUYilpz9T4A4QQ1pW+gJpx1b/Cb3RX3VolesudlKVFAX14+Dqty07ISnKc0wE0AUwewgIiHWoSB4gBlXM0jNR3HfT7TympnpqWFsJfcfTFg8XHHv8OuluBE6vEmbrMW3MUugN4K5erqUZjKQWYIFHPYf1L64BzqEBMLLJAoGBAIGKzyusYCrhh3SG0J5wVmnY3NjVQibTg5BmPODlNduujHGWGdfJcRzwwQVap5LrF60rvxOn+9qre+wgCjrTukivNuyFYJgHPKsEloiWknvul73MDKMf0LDIIu7s4NUpvoVKtvY1PxwwLcH9TjP4Cull+cyCtta3CLJs+HzdXf9l";
    public static final String RSA_PRIVATE = "";
    public static final String WX_APP_ID = "wxb745727a544b480c";
    public static final String WX_MCH_ID = "1571964901";
    public static final String WX_API_KEY="75d99d3175225b6a907caf6734e21c4d";
    public static final String WXShareID="6938589979530b5b1b8220988e7c0180";
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    public static final String WX_NOTIFY_URL = String.format("https://gamesupporttest.singmaan.com:10013/%s/%s/client_success_callback",GameName,"wxpay");
    public static final String ALIPAY_NOTIFY_URL = String.format("https://gamesupporttest.singmaan.com:10013/%s/%s/client_success_callback",GameName,"alipay");
    @Override
    public void ActivityInit(Activity context, final APPBaseInterface appinterface) {
        super.ActivityInit(context, appinterface);
        MercuryActivity.LogLocal("[InAppChannel][ActivityInit]=" + Channelname);
        Toast.makeText(mContext, "只限于" + channelname + "测试，请勿泄漏", Toast.LENGTH_SHORT).show();
        //这里写渠道的Activity的初始化
        req = new PayReq();
        sb=new StringBuffer();
        msgApi = WXAPIFactory.createWXAPI(mContext,WX_APP_ID);
    }

    public void ActivityBundle(Bundle bundle) {
        super.ActivityBundle(bundle);
        MercuryActivity.LogLocal("[InAppChannel][ActivityBundle]=" + bundle);
        //这里写渠道的Bundle初始化有关的的初始化，ActivityBundle会在ActivityInit之后调用
    }

    public void ApplicationInit(Application appcontext) {
        mAppContext = appcontext;
        MercuryActivity.LogLocal("[InAppChannel][ApplicationInit]=" + Channelname);
        //这里写渠道的Application有关的的初始化，这个方法会优先调用
    }
    @Override
    public void Purchase(final String strProductId) {
        //渠道支付代码写在这里，下面方法是SDK默认的支付，详情进入SDK内部
        MercuryPay(strProductId);
    }
    public void SingmaanLogin() {
        //渠道登录代码写在这里，下面方法是SDK默认的支付，详情进入SDK内部。默认的登录方法包含了登录和防沉迷
        LogLocal("[InAppChannel][SingmaanLogin]");
        MercuryLogin();
    }
    public void SingmaanLogout() {
        //渠道登出代码写在这里，下面方法是SDK默认的支付，详情进入SDK内部，默认方法会清理本地缓存，下次会再次提示登录。
        LogLocal("[InAppChannel][SingmaanLogout]" + DeviceId);
        LoginCancelCallBack(DeviceId);
    }
    public void Data_UseItem(int myint, String item) {
        //统计代码，这里是用了XX物品
        LogLocal("[InAppChannel][Data_UseItem]myint="+myint+",item="+item);
    }

    public void Data_LevelBegin(String key) {
        //统计代码，这里是关卡开始
        LogLocal("[InAppChannel][Data_LevelBegin]"+key);
    }

    public void Data_LevelCompleted(String key) {
        //统计代码，这里是关卡结束
        LogLocal("[InAppChannel][Data_LevelCompleted]"+key);
    }

    public void Data_Event(String event) {
        //统计代码，这里是事件代码
        LogLocal("[InAppChannel][Data_LevelCompleted]event="+event);
    }
    @Override
    public void ExitGame() {
        //渠道退出代码写在这里，下面方法是SDK默认的退出，详情进入SDK内部
        LogLocal("[InAppChannel][ExitGame]");
        MercuryExit();
    }

    public void UploadGameData() {
        //云存储的上传游戏数据功能
        LogLocal("[MercuryActivity][SingmaanLogin]");
        DownloadGameData();
    }

    public void DownloadGameData() {
        //云存储的下载游戏数据功能，上传的数值会原封不动的返回
        LogLocal("[MercuryActivity][SingmaanLogout]");
        download_game_data();
    }

    public void Redeem() {
        LogLocal("[MercuryActivity][Redeem]");
        //兑换码相关的代码，默认会走支付成功，返回相应的计费点ID
        redeemCode();
    }

    public void RateGame() {
        //打开链接方法，计划是游戏评论
        LogLocal("[InAppChannel][RateGame]");
        OpenUrl("http://www.singmaan.com");
    }

    public void ShareGame() {
        //打开链接方法，计划是分享游戏
        LogLocal("[InAppChannel][ShareGame]");
        OpenUrl("http://www.singmaan.com");
    }

    public void OpenGameCommunity() {
        //打开链接方法，计划是打开游戏社区
        LogLocal("[InAppChannel][OpenGameCommunity]");
        OpenUrl("http://www.singmaan.com");

    }

    public void VIPPanel() {
        //打开VIP面板
        MercuryVIPPanel();
    }

    public void DailyCheckInPanel() {
        //打开每日签到面板
        MercuryDialyCheckIn();
    }

    @Override
    public void onPause() {
        //渠道安卓生命周期方法
        MercuryActivity.LogLocal("[" + Channelname + "][onPause]");
    }

    @Override
    public void onResume() {
        //渠道安卓生命周期方法
        MercuryActivity.LogLocal("[" + Channelname + "][onResume]");
    }

    @Override
    public void onDestroy() {
        //渠道安卓生命周期方法
        MercuryActivity.LogLocal("[" + Channelname + "][onDestroy]");
    }

    @Override
    public void onStop() {
        //渠道安卓生命周期方法
        MercuryActivity.LogLocal("[" + Channelname + "][onStop]");
    }

    @Override
    public void onStart() {
        //渠道安卓生命周期方法
        MercuryActivity.LogLocal("[" + Channelname + "][onStart]");
    }

    @Override
    public void onRestart() {
        //渠道安卓生命周期方法
        MercuryActivity.LogLocal("[" + Channelname + "][onRestart]");
    }

    public void onTerminate() {
        //渠道安卓生命周期方法
        MercuryActivity.LogLocal("[" + Channelname + "][onTerminate]");
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //渠道安卓生命周期方法
        MercuryActivity.LogLocal("[" + Channelname + "] onActivityResult(int requestCode, int resultCode, Intent data)");
    }

    @Override
    public void onNewIntent(Intent intent) {
        //渠道安卓生命周期方法
        MercuryActivity.LogLocal("[" + Channelname + "] onNewIntent(Intent intent) ");
    }

    @Override
    public void attachBaseContext(Context base) {
        //渠道安卓生命周期方法
        MercuryActivity.LogLocal("[" + Channelname + "] attachBaseContext(Context base) ");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        //渠道安卓生命周期方法
        MercuryActivity.LogLocal("[" + Channelname + "] onWindowFocusChanged(Context base) ");
    }



/**************************************************************************************************
 *                              following are detial of each method.                              *
 **************************************************************************************************/

    public void MercuryExit()
    {
        try {
            Builder builder = new Builder(mContext);
            builder.setMessage("Testing Mode");
            builder.setTitle("ExitGame");
            builder.setPositiveButton("Success", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ((Activity) MercuryActivity.mContext).finish();
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            });
            builder.setNegativeButton("Dismiss", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setCancelable(false);
            builder.create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void OpenUrl(String URL)
    {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(URL);//此处填链接
        intent.setData(content_url);
        MercuryActivity.mContext.startActivity(intent);
    }
    public  void MercuryVIPPanel()
    {
        try {
            Builder builder = new Builder(mContext);
            builder.setMessage("Testing Mode");
            builder.setTitle("VIPPanel");
            builder.setPositiveButton("Success", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        String[][] item_list = {{"Coin", "1000"}, {"Token", "15"}};
                        int[] ages = {18, 20};
                        JSONArray reward_list = new JSONArray();
                        for (int i = 0; i < item_list.length; i++) {
                            JSONObject student = new JSONObject();
                            student.put("ResourceId", item_list[i][0]);
                            student.put("Amount", Integer.parseInt(item_list[i][1]));
                            reward_list.put(student);
                        }
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("OnClaimReward", reward_list);
                        MercuryActivity.LogLocal("[VIPPanel]OnClaimReward=" + reward_list.toString());
                        OnClaimReward(reward_list.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
            builder.setNeutralButton("Failed", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setNegativeButton("Dismiss", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });
            builder.create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void MercuryDialyCheckIn()
    {
        try {
            Builder builder = new Builder(mContext);
            builder.setMessage("Testing Mode");
            builder.setTitle("DailyCheckInPanel");
            builder.setPositiveButton("Success", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        String[][] item_list = {{"Coin", "1000"}, {"Token", "15"}};
                        int[] ages = {18, 20};
                        JSONArray reward_list = new JSONArray();
                        for (int i = 0; i < item_list.length; i++) {
                            JSONObject student = new JSONObject();
                            student.put("ResourceId", item_list[i][0]);
                            student.put("Amount", Integer.parseInt(item_list[i][1]));
                            reward_list.put(student);
                        }
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("OnClaimReward", reward_list);
                        MercuryActivity.LogLocal("[DailyCheckInPanel]OnClaimReward=" + reward_list.toString());
                        OnClaimReward(reward_list.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            builder.setNeutralButton("Failed", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setNegativeButton("Dismiss", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });
            builder.create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void MercuryPay(final String strProductId)
    {
        //shrinkpartstart
        UserConfig.getPayPermition(DeviceId, GameName, channelname, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogLocal("[UserConfig][get_pay_permition] failed=" + e.toString());
                Looper.prepare();
                AlipayAndWechat();
                Looper.loop();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                if (s != null && isJSONValid(s)) {
                    JSONObject json = null;
                    try {
                        json = (JSONObject) new JSONTokener(s).nextValue();
                        String isPayPermitted = String.valueOf((Integer) json.getInt("data"));
                        PaymentDialog.isPayPermitted = isPayPermitted;
                        pid = strProductId;
                        MercuryConst.PayInfo(strProductId);
                        MercuryActivity.LogLocal("[InAppChannel][Purchase] MercuryConst.QinPid=" + MercuryConst.QinPid);
                        MercuryActivity.LogLocal("[InAppChannel][Purchase] MercuryConst.Qindesc=" + MercuryConst.Qindesc);
                        MercuryActivity.LogLocal("[InAppChannel][Purchase] MercuryConst.Qinpricefloat=" + MercuryConst.Qinpricefloat);
                        Looper.prepare();
                        MercuryActivity.LogLocal("[InAppChannel][Purchase] MercuryConst.local_age=" + local_age);
                        if (local_age < 8 && local_age >=0) {
                            try {
                                Builder builder = new Builder(mContext);
                                builder.setMessage("提示");
                                builder.setTitle("8岁以下未成年人无法充值");
                                builder.setPositiveButton("确定", new OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                builder.create().show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        else if(local_age < 16 && local_age >=8)
                        {
                            if(MercuryConst.Qinpricefloat>=50)
                            {
                                Builder builder = new Builder(mContext);
                                builder.setMessage("提示");
                                builder.setTitle("8周岁以上未满16周岁的用户，单次充值金额不得超过50元人民币");
                                builder.setPositiveButton("确定", new OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                builder.create().show();
                            }
                            else
                            {
                                if(global_total_payment<=200) {
                                    AlipayAndWechat();
                                }
                                else
                                {
                                    try {
                                        Builder builder = new Builder(mContext);
                                        builder.setMessage("提示");
                                        builder.setTitle("8周岁以上未满16周岁的用户每月累计充值不能超过200");
                                        builder.setPositiveButton("确定", new OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        });
                                        builder.create().show();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                            }
                        }
                        else if(local_age < 18 && local_age >=16)
                        {
                            if (MercuryConst.Qinpricefloat >= 100) {
                                Builder builder = new Builder(mContext);
                                builder.setMessage("提示");
                                builder.setTitle("16周岁以上未满18周岁的用户，单次充值金额不得超过100元人民币");
                                builder.setPositiveButton("确定", new OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                builder.create().show();
                            }
                            else
                            {
                                if(global_total_payment<=400)
                                {
                                    AlipayAndWechat();
                                }
                                else
                                {
                                    Builder builder = new Builder(mContext);
                                    builder.setMessage("提示");
                                    builder.setTitle("16周岁以上未满18周岁的用户，每月累计充值不能超过400");
                                    builder.setPositiveButton("确定", new OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                                    builder.create().show();
                                }
                            }
                        }
                        else {
                            AlipayAndWechat();
                        }
                        Looper.loop();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(mContext, "网络未连接", Toast.LENGTH_SHORT).show();
                }
            }
        });
//shrinkpartend
    }
    public void AlipayAndWechat()
    {
        new PaymentDialog(mContext, new PayMethodCallBack() {
            @Override
            public void Alipay(String msg) {
                MercuryActivity.LogLocal("[InAppChannel][Purchase] Alipay");
                /*
                 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
                 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
                 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
                 *
                 * orderInfo 的获取必须来自服务端；
                 */
                boolean rsa2 = (RSA2_PRIVATE.length() > 0);
                Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2, MercuryConst.QinPid, MercuryConst.Qindesc, MercuryConst.Qinpricefloat);
                String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
                String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
                String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
                final String orderInfo = orderParam + "&" + sign;
                final Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(mContext);
                        Map<String, String> result = alipay.payV2(orderInfo, true);
                        Log.i("msp", result.toString());
                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };
                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();
            }

            @Override
            public void WechatPay(String msg) {
                MercuryActivity.LogLocal("[InAppChannel][Purchase] WechatPay");
                if(isNetworkAvailable(mContext))
                {
                    GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
                    getPrepayId.execute();
                }
                else {
                    Toast.makeText(mContext, "没有可用网络，微信无法支付！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private  class GetPrepayIdTask extends AsyncTask<Void, Void, Map<String,String>> {

        private ProgressDialog dialog;


        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(mContext,"正在生成订单", "请稍后！");
        }

        @Override
        protected void onPostExecute(Map<String,String> result) {
            if (dialog != null) {
                dialog.dismiss();
            }
            sb.append("prepay_id\n"+result.get("prepay_id")+"\n\n");
            MercuryActivity.LogLocal("[InAppChannel][onPostExecute]result = "+result);
            resultunifiedorder=result;
            WXSendReq();

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Map<String,String>  doInBackground(Void... params) {

            String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
            String entity = genProductArgs();
            MercuryActivity.LogLocal("[InAppChannel][GetPrepayIdTask]entity = "+entity);

            byte[] buf = httpPost(url, entity);
            String content = new String(buf);
            MercuryActivity.LogLocal("[InAppChannel][GetPrepayIdTask]entity = "+entity);
            Map<String,String> xml=decodeXml(content);

            return xml;
        }
    }
    public  Map<String,String> decodeXml(String content) {

        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName=parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:

                        if("xml".equals(nodeName)==false){
                            //实例化student对象
                            xml.put(nodeName,parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }

            return xml;
        } catch (Exception e) {
            MercuryActivity.LogLocal("[InAppChannel][genProductArgs] decodeXml = " + e.toString());
        }
        return null;

    }
    private  String genProductArgs() {
        StringBuffer xml = new StringBuffer();
        int nPrice = (int)(MercuryConst.Qinpricefloat*100);
        String strPriceString = String.valueOf(nPrice);
        try {
            String  nonceStr = genNonceStr();
            xml.append("</xml>");
            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
            packageParams.add(new BasicNameValuePair("appid", WX_APP_ID));
            MercuryActivity.LogLocal("[InAppChannel][genProductArgs] order_id = " + order_id);
            packageParams.add(new BasicNameValuePair("attach",MercuryConst.Qindesc+","+DeviceId+","+MercuryConst.QinPid+","+channelname+"_"+GameName+"_"+order_id));

            packageParams.add(new BasicNameValuePair("body", MercuryConst.Qindesc));
            packageParams.add(new BasicNameValuePair("mch_id", WX_MCH_ID));
            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
            packageParams.add(new BasicNameValuePair("notify_url",WX_NOTIFY_URL));

            packageParams.add(new BasicNameValuePair("out_trade_no",order_id));
            packageParams.add(new BasicNameValuePair("spbill_create_ip","127.0.0.1"));

            packageParams.add(new BasicNameValuePair("total_fee", strPriceString));
            packageParams.add(new BasicNameValuePair("trade_type", "APP"));
            String sign = genPackageSign(packageParams);
            packageParams.add(new BasicNameValuePair("sign", sign));
            String xmlstring =toXml(packageParams);

            return new String(xmlstring.toString().getBytes(), "ISO8859-1");

        } catch (Exception e) {
            MercuryActivity.LogLocal("[InAppChannel][genProductArgs] ex = " + e.getMessage());
            return null;
        }
    }
    private  String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<"+params.get(i).getName()+">");


            sb.append(params.get(i).getValue());
            sb.append("</"+params.get(i).getName()+">");
        }
        sb.append("</xml>");
        return sb.toString();
    }
    private  String genPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(WX_API_KEY);
        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        return packageSign;
    }

    public   boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected())
            {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }
    public  void WXSendReq()
    {
        genPayReq();
        msgApi.registerApp(WX_APP_ID);
        msgApi.sendReq(req);
    }
    private  void genPayReq() {

        req.appId = WX_APP_ID;
        req.partnerId = WX_MCH_ID;
        req.prepayId = resultunifiedorder.get("prepay_id");
        req.packageValue = "prepay_id="+resultunifiedorder.get("prepay_id");
        req.nonceStr = genNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());
        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
        req.sign = genAppSign(signParams);
        sb.append("sign\n"+req.sign+"\n\n");
        LogLocal("[InAppChannel][genPayReq] signParams.toString()="+signParams.toString());

    }
    private  String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(WX_API_KEY);

        this.sb.append("sign str\n"+sb.toString()+"\n\n");
        String appSign = MD5.getMessageDigest(sb.toString().getBytes());
        LogLocal("[InAppChannel][genAppSign]"+appSign);
        return appSign;
    }
    private static String genNonceStr() {
        Random random = new Random();
        DateFormat format = new java.text.SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.US);
        String requestId = format.format(new Date());
        return MD5.getMessageDigest((String.valueOf(random.nextInt(10000))+requestId).getBytes());
    }
    private  long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        MercuryActivity.LogLocal("[InAppChannel][Purchase] SDK_PAY_FLAG payResult success="+payResult);
                        onPurchaseSuccess(MercuryConst.QinPid);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        MercuryActivity.LogLocal("[InAppChannel][Purchase] SDK_PAY_FLAG payResult failed="+payResult);
                        onPurchaseFailed(MercuryConst.QinPid);
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        MercuryActivity.LogLocal("[InAppChannel][Purchase] SDK_AUTH_FLAG payResult success="+authResult);
                    } else {
                        // 其他状态值则为授权失败
                        MercuryActivity.LogLocal("[InAppChannel][Purchase] SDK_AUTH_FLAG payResult failed="+authResult);
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };
    public void TestPay() {
        try {
            Builder builder = new Builder(mContext);
            builder.setMessage("Testing Mode");
            builder.setTitle("TestPay");
            builder.setPositiveButton("Success", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onPurchaseSuccess(MercuryConst.QinPid);
                }
            });
            builder.setNeutralButton("Failed", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onPurchaseFailed(MercuryConst.QinPid);
                }
            });
            builder.setCancelable(false);
            builder.create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void MercuryLogin() {
        //shrinkpartstart
        String phone = "";
        LogLocal("[InAppChannel][SingmaanLogin]" + DeviceId);
        if(NetCheckUtil.checkNet(mContext))
        {
            UserConfig.getLoginPermition(DeviceId, GameName, channelname, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Looper.prepare();
                    DisplayLoginDialog();
                    Looper.loop();
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String s = response.body().string();
                    if (s != null && isJSONValid(s)) {
                        JSONObject json = null;
                        try {
                            Looper.prepare();
                            json = (JSONObject) new JSONTokener(s).nextValue();
                            LoginDialog.isLoginPermitted = String.valueOf(json.getInt("data"));
                            LogLocal("[UserConfig][get_login_permition] result=" + LoginDialog.isLoginPermitted);
                            LogLocal("[UserConfig][get_login_permition] remote result=" + s);
                            DisplayLoginDialog();
                            Looper.loop();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        LogLocal("[RemoteConfig][verify_signe_in] server returned formate is not a json");
                    }
                }
            });
        }
        //shrinkpartend
    }
    public void DisplayLoginDialog()
    {
        LoginDialog loginDialog = new LoginDialog(mContext, MercuryActivity.DeviceId, new LoginCallBack() {
            @Override
            public void success(String phone) {
                LogLocal("[InAppChannel][SingmaanLogin] Success");
                DeviceId = phone;
                //shrinkpartend
                LoginSuccessCallBack(DeviceId);
                if (readFileData("privacyagreement").equals("")) {
                    new PrivacyDialog(mContext);
                }
                //shrinkpartstart
            }
            @Override
            public void fail(String msg) {
                LogLocal("[InAppChannel][SingmaanLogin] Login failed");
                LoginCancelCallBack(msg);
            }
        });
    }


    public void MercuryIDVerify() {

    }

    public void MercurySigneIn() {
        //shrinkpartstart
        new IDCardVerifyDialog(mContext, new LoginCallBack() {
            @Override
            public void success(String msg) {
                LogLocal("[InAppDialog][SigneInDialog] ID card Success");
            }

            @Override
            public void fail(String msg) {
                LogLocal("[InAppDialog][SigneInDialog] ID card failed");
            }
        });
        //shrinkpartend
    }



    public static String[] convertStrToArray(String str, String symbol) {
        String[] strArray = null;
        strArray = str.split(symbol); //拆分字符为symbol 可以是 "," ,然后把结果交给数组strArray
        return strArray;
    }

}
