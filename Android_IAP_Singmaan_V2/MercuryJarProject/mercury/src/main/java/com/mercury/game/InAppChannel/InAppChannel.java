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
import android.net.Uri;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

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

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import static com.mercury.game.MercuryActivity.DeviceId;
import static com.mercury.game.MercuryActivity.LogLocal;
import static com.mercury.game.MercuryApplication.channelname;
import static com.mercury.game.util.Function.verifyGame;
import static com.mercury.game.util.MercuryConst.GlobalProductionList;
//end


public class InAppChannel extends InAppBase {
	
	//comment
	private String Channelname="InAppSingmaan";
	private ZhongTuiSdk sdk;
	private String APPID_KEY = "1214";
	private String CLICENT_KEY = "1c1b4fe2f3b7ebb2333a3766bba124f7";
	private static String pid;
	@Override
	public void ActivityInit(final Activity context, final APPBaseInterface appinterface)
	{		
		super.ActivityInit(context, appinterface);

		MercuryActivity.LogLocal("["+Channelname+"]->init:InAppChannel.init="+context);
		Toast.makeText(mContext, "只限于"+channelname+"测试，请勿泄漏", Toast.LENGTH_SHORT).show();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				/**
				 * 延时执行的代码
				 */
				try {
					MercuryActivity.LogLocal("["+Channelname+"]->init:InAppChannelSingmaan CLICENT_KEY="+CLICENT_KEY);
					sdk = (ZhongTuiSdk) ZhongTuiSdkFactory.getSdkProxyInstance();
					sdk.init(context, APPID_KEY, CLICENT_KEY, new InitCallback() {
						@Override
						public void onInitSucceed() {
							sdk.login(context, new LoginCallback() {
								@SuppressLint("SetTextI18n")
								@Override
								public void onLoginSucceed(final String s, final String s1) {
									MercuryActivity.LogLocal("登录成功" + "\n" + "uid:" + s + "\n" + "token:" + s1);
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
							MercuryActivity.LogLocal( "初始化失败:"+s);
						}
					});
				}
				catch (Exception e)
				{
					MercuryActivity.LogLocal("初始化失败:"+e.toString());
				}
			}
		},1000); // 延时1秒

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				/**
				 * 延时执行的代码
				 */
				try {
					MercuryActivity.LogLocal("["+Channelname+"]->init:InAppChannelSingmaan CLICENT_KEY="+CLICENT_KEY);
					sdk = (ZhongTuiSdk) ZhongTuiSdkFactory.getSdkProxyInstance();
					sdk.init(context, APPID_KEY, CLICENT_KEY, new InitCallback() {
						@Override
						public void onInitSucceed() {
							sdk.login(context, new LoginCallback() {
								@SuppressLint("SetTextI18n")
								@Override
								public void onLoginSucceed(final String s, final String s1) {
									MercuryActivity.LogLocal("登录成功" + "\n" + "uid:" + s + "\n" + "token:" + s1);
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
							MercuryActivity.LogLocal( "初始化失败:"+s);
						}
					});
				}
				catch (Exception e)
				{
					MercuryActivity.LogLocal("初始化失败:"+e.toString());
				}
			}
		},6000); // 延时1秒
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
		pid = strProductId;
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
			builder.setMessage("Testing Mode");
			builder.setTitle("Choice Result");
			builder.setPositiveButton("Success", new DialogInterface.OnClickListener() {
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
			builder.setNegativeButton("Dismiss", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					onPurchaseFailed(pid);
					dialog.dismiss();
				}
			});
			builder.create().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void SingmaanLogin()
	{
		LogLocal("[MercuryActivity][SingmaanLogin]"+DeviceId);
		LoginSuccessCallBack("SingmaanLogin"+DeviceId);
	}
	public void SingmaanLogout()
	{
		LogLocal("[MercuryActivity][SingmaanLogout]"+DeviceId);
		LoginCancelCallBack("SingmaanLogout"+DeviceId);
	}

	public void UploadGameData()
	{
		LogLocal("[MercuryActivity][SingmaanLogin]"+DeviceId);
		onFunctionCallBack("SingmaanUploadGameData");
	}
	public void DownloadGameData()
	{
		LogLocal("[MercuryActivity][SingmaanLogout]"+DeviceId);
		onFunctionCallBack("SingmaanUpDownloadGameData");
	}
	public void Redeem()
	{
		int max=GlobalProductionList.length;
		int min=0;
		Random random = new Random();
		int s = random.nextInt(max)%(max-min+1) + min;
		onPurchaseSuccess(GlobalProductionList[s][0]);
	}
	public void RateGame()
	{
		LogLocal("[InAppChannel][RateGame]");
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		Uri content_url = Uri.parse("http://www.singmaan.com");//此处填链接
		intent.setData(content_url);
		MercuryActivity.mContext.startActivity(intent);
	}
	public void ShareGame()
	{
		LogLocal("[InAppChannel][ShareGame]");
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		Uri content_url = Uri.parse("http://www.singmaan.com");//此处填链接
		intent.setData(content_url);
		MercuryActivity.mContext.startActivity(intent);
	}
	public void OpenGameCommunity()
	{
		LogLocal("[InAppChannel][OpenGameCommunity]");
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		Uri content_url = Uri.parse("http://www.singmaan.com");//此处填链接
		intent.setData(content_url);
		MercuryActivity.mContext.startActivity(intent);
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
