package com.mercury.game.InAppChannel;

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
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.mercury.game.MercuryActivity;
import com.mercury.game.util.LoginCallBack;
import com.mercury.game.util.MercuryConst;
import com.mercury.game.util.NetCheckUtil;
import com.mercury.game.util.PayMethodCallBack;
import com.mercury.game.util.SPUtils;
import com.mercury.game.util.SpConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
//shrinkpartstart
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
//shrinkpartend
import static com.mercury.game.InAppDialog.LoginDialog.local_age;
import static com.mercury.game.InAppRemote.RemoteConfig.chinese_id;
import static com.mercury.game.MercuryActivity.DeviceId;
import static com.mercury.game.MercuryActivity.GameName;
import static com.mercury.game.MercuryActivity.LogLocal;
import static com.mercury.game.MercuryApplication.channelname;
import static com.mercury.game.util.Function.readFileData;
import static com.mercury.game.util.Function.writeFileData;
import static com.mercury.game.util.MercuryConst.GlobalProductionList;
import static com.mercury.game.util.UIUtils.isJSONValid;


public class InAppChannel extends InAppBase {

    private String Channelname = "InAppChannel";

    private static String pid;

    @Override
    public void ActivityInit(Activity context, final APPBaseInterface appinterface) {
        super.ActivityInit(context, appinterface);
        MercuryActivity.LogLocal("[InAppChannel][ActivityInit]=" + Channelname);
        Toast.makeText(mContext, "只限于" + channelname + "测试，请勿泄漏", Toast.LENGTH_SHORT).show();
        //这里写渠道的Activity的初始化
    }

    public void ActivityBundle(Bundle bundle) {
        super.ActivityBundle(bundle);
        MercuryActivity.LogLocal("[InAppChannel][ActivityBundle]=" + bundle);
        //这里写渠道的Bundle有关的的初始化
    }

    public void ApplicationInit(Application appcontext) {
        mAppContext = appcontext;
        MercuryActivity.LogLocal("[InAppChannel][ApplicationInit]=" + Channelname);
        //这里写渠道的Application有关的的初始化
    }
    @Override
    public void Purchase(final String strProductId) {
        //渠道支付代码写在这里，下面方法是SDK默认的支付，详情进入SDK内部
        MercuryPay(strProductId);
    }
    public void SingmaanLogin() {
        //渠道登录代码写在这里，下面方法是SDK默认的支付，详情进入SDK内部。默认的登录方法包含了登录和防沉迷
        MercuryLogin();
    }
    public void SingmaanLogout() {
        //渠道登出代码写在这里，下面方法是SDK默认的支付，详情进入SDK内部，默认方法会清理本地缓存，下次会再次提示登录。
        LogLocal("[MercuryActivity][SingmaanLogout]" + DeviceId);
        LoginCancelCallBack(DeviceId);
    }
    public void Data_UseItem(int myint, String item) {
        //统计代码，这里是用了XX物品
    }

    public void Data_LevelBegin(String key) {
        //统计代码，这里是关卡开始
    }

    public void Data_LevelCompleted(String key) {
        //统计代码，这里是关卡结束
    }

    public void Data_Event(String event, Map<String, Object> map) {
        //统计代码，这里是事件代码
    }
    @Override
    public void ExitGame() {
        //渠道退出代码写在这里，下面方法是SDK默认的退出，详情进入SDK内部
        MercuryExit();
    }

    public void UploadGameData() {
        //云存储的上传游戏数据功能
        LogLocal("[MercuryActivity][SingmaanLogin]" + DeviceId);
        onFunctionCallBack("SingmaanUploadGameData");
    }

    public void DownloadGameData() {
        //云存储的下载游戏数据功能，上传的数值会原封不动的返回
        LogLocal("[MercuryActivity][SingmaanLogout]" + DeviceId);
        onFunctionCallBack("SingmaanDownloadGameData");
    }

    public void Redeem() {
        //兑换码相关的代码，默认会走支付成功，返回相应的计费点ID
        int max = GlobalProductionList.length;
        int min = 0;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        onPurchaseSuccess(GlobalProductionList[s][0]);
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
                                AlipayAndWechat();
                            }
                        }
                        else if(local_age < 18 && local_age >=16)
                        {
                            if(MercuryConst.Qinpricefloat>=100)
                            {
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
                                AlipayAndWechat();
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
                TestPay();
            }

            @Override
            public void WechatPay(String msg) {
                MercuryActivity.LogLocal("[InAppChannel][Purchase] WechatPay");
                TestPay();
            }
        });
    }
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
