package com.mercury.game.InAppChannel;
import com.mercury.game.util.APPBaseInterface;
import com.mercury.game.util.InAppBase;

//comment
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.RemoteException;
import android.util.Log;

import com.mercury.game.MercuryActivity;
import com.mercury.game.util.MercuryConst;
import com.mercury.game.MercuryApplication;
import com.zhongtui.sdk.ZhongTuiSdk;
import com.zhongtui.sdk.ZhongTuiSdkFactory;
import com.zhongtui.sdk.bean.PayParams;
import com.zhongtui.sdk.callback.InitCallback;
import com.zhongtui.sdk.callback.LoginCallback;
import com.zhongtui.sdk.callback.LogoutCallback;
import com.zhongtui.sdk.callback.PayCallback;
import com.zhongtui.sdk.listener.OnSdkLogoutListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;
//end


public class InAppChannel extends InAppBase {
	
	//comment
	private InAppBase mBaseInApp = null;
	private String Channelname="InAppChannelSingmaan";
	private String mUid;
	private String mSession;
	private int mAccountType;
	private String demovalue1;
	private ZhongTuiSdk sdk;
	private String APPID_KEY = "1195";
	private String CLICENT_KEY = "cff1318bdc1d8f9635b5f1e63e7c9229";
	@Override
	public void ActivityInit(final Activity context, final APPBaseInterface appinterface)
	{		
		super.ActivityInit(context, appinterface);
		MercuryActivity.LogLocal("["+Channelname+"]->init:InAppChannel.init="+context);
		try {
			sdk = (ZhongTuiSdk) ZhongTuiSdkFactory.getSdkProxyInstance();
			sdk.init(context, APPID_KEY, CLICENT_KEY, new InitCallback() {
				@Override
				public void onInitSucceed() {
					sdk.login(context, new LoginCallback() {
						@SuppressLint("SetTextI18n")
						@Override
						public void onLoginSucceed(final String s, final String s1) {
							Log.e(Channelname, "登录成功" + "\n" + "uid:" + s + "\n" + "token:" + s1);
						}

						@Override
						public void onLoginFailed(String s) {
							Log.e(Channelname, "登陆失败:" + s);
						}
					});
					sdk.registerSdkLogoutListener(new OnSdkLogoutListener() {
						@Override
						public void onSdkLogout() {
							Log.e(Channelname, "退出监听");
						}
					});
				}

				@Override
				public void onInitFailed(String s) {
					//失败
					Log.e(Channelname, "初始化失败:"+s);
				}
			});
		}
		catch (UndeclaredThrowableException ignored)
		{

		}


	}
	public void ApplicationInit(Application appcontext)
	{
		mAppContext=appcontext;
		MercuryActivity.LogLocal("["+Channelname+"]->init:InAppChannel.ApplicationInit="+appcontext);
	}

	public static String[] convertStrToArray(String str,String symbol){
		String[] strArray = null;
		strArray = str.split(symbol); //拆分字符为symbol 可以是 "," ,然后把结果交给数组strArray
		return strArray;
	}
	@Override
	public void Purchase(final String strProductId)
	{
		MercuryConst.PayInfo(strProductId);
		MercuryActivity.LogLocal("[InAppChannel][Purchase] MercuryConst.QinPid="+MercuryConst.QinPid);
		MercuryActivity.LogLocal("[InAppChannel][Purchase] MercuryConst.Qindesc="+MercuryConst.Qindesc);
		MercuryActivity.LogLocal("[InAppChannel][Purchase] MercuryConst.Qinpricefloat="+MercuryConst.Qinpricefloat);
		PayParams payParams = new PayParams();
		payParams.setAmount(MercuryConst.Qinpricefloat);//单位元
		payParams.setExtension("扩展信息，原样返回游戏服务器");
		payParams.setProductID(MercuryConst.QinPid);
		payParams.setProductNAME(MercuryConst.Qindesc);
		payParams.setRoleID("默认角色");
		payParams.setRoleNAME("游戏玩家");
		payParams.setServerID(1);
		payParams.setServerNAME("默认服务器");
		//支付参数
		MercuryActivity.LogLocal("支付参数:"
				+ payParams.getExtension() + "\n"
				+ payParams.getAmount() + "\n"
				+ payParams.getProductID() + "\n"
				+ payParams.getProductNAME() + "\n"
				+ payParams.getServerID() + "\n"
				+ payParams.getRoleID() + "\n"
				+ payParams.getRoleNAME() + "\n"
				+ payParams.getServerID() + "\n"
				+ payParams.getServerNAME() + "\n");
		sdk.pay(mContext, payParams, new PayCallback() {
			@Override
			public void onPaySuccess() {
				MercuryActivity.LogLocal("支付成功");
				onPurchaseSuccess("success message");
			}

			@Override
			public void onPayFail(String s) {
				MercuryActivity.LogLocal("支付失败:" + s);
				onPurchaseFailed("failed message");
			}
		});
	}
	@Override
	public void ExitGame()
	{
		try {
			AlertDialog.Builder builder = new Builder(mContext);
			builder.setMessage("退出游戏");
			builder.setTitle("选择结果");
			builder.setPositiveButton("确认退出", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					sdk.logout(new LogoutCallback() {
						@Override
						public void onLogout() {
							((Activity) MercuryActivity.mContext).finish();
							android.os.Process.killProcess(android.os.Process.myPid());
						}
					});
				}
			});

			builder.setNegativeButton("取消退出", new OnClickListener() {
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
	public void TestPay()
	{
		try {
			AlertDialog.Builder builder = new Builder(mContext);
			builder.setMessage("测试支付模式");
			builder.setTitle("选择结果");
			builder.setPositiveButton("成功", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					onPurchaseSuccess("success message");
				}
			});
			builder.setNeutralButton("失败", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					onPurchaseFailed("failed message");
				}
			});
			builder.setNegativeButton("取消", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					onPurchaseFailed("failed message");
					dialog.dismiss();
				}
			});
			builder.create().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onPause()
	{
		MercuryActivity.LogLocal("["+Channelname+"] onPause");
	}
	
	@Override
	public void onResume()
	{
		MercuryActivity.LogLocal("["+Channelname+"] onResume");
	}
	@Override
	public void onDestroy()
	{
		MercuryActivity.LogLocal("["+Channelname+"] onDestroy");
		sdk.onDestroy();
	}
	@Override
	public void onStop() 
	{
		MercuryActivity.LogLocal("["+Channelname+"] onStop");
		sdk.onStop();
	}
	@Override
	public void onStart() 
	{
		MercuryActivity.LogLocal("["+Channelname+"] onStart");
	}
	@Override
	public void onRestart()
	{
		MercuryActivity.LogLocal("["+Channelname+"] onRestart");
		sdk.onRestart(mContext);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		MercuryActivity.LogLocal("["+Channelname+"] onActivityResult(int requestCode, int resultCode, Intent data)");
	}
	@Override
	public void onNewIntent(Intent intent) 
	{
		MercuryActivity.LogLocal("["+Channelname+"] onNewIntent(Intent intent) ");
	}
	



	//end
}
