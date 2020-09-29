package com.mercury.game.InAppChannel;
import com.mercury.game.InAppDialog.IDCardVerifyDialog;
import com.mercury.game.InAppDialog.LoginDialog;
import com.mercury.game.InAppDialog.PaymentDialog;
import com.mercury.game.util.APPBaseInterface;
import com.mercury.game.util.InAppBase;

//comment
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.mercury.game.MercuryActivity;
import com.mercury.game.util.LoginCallBack;
import com.mercury.game.util.MercuryConst;
import com.mercury.game.util.PayMethodCallBack;
import com.zhongtui.sdk.ZhongTuiSdk;
import com.zhongtui.sdk.ZhongTuiSdkFactory;
import com.zhongtui.sdk.bean.PayParams;
import com.zhongtui.sdk.callback.InitCallback;
import com.zhongtui.sdk.callback.LoginCallback;
import com.zhongtui.sdk.callback.LogoutCallback;
import com.zhongtui.sdk.callback.PayCallback;
import com.zhongtui.sdk.listener.OnSdkLogoutListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Random;

import static com.mercury.game.MercuryActivity.DeviceId;
import static com.mercury.game.MercuryActivity.LogLocal;
import static com.mercury.game.MercuryApplication.channelname;
import static com.mercury.game.util.MercuryConst.GlobalProductionList;
//end


public class InAppChannel extends InAppBase {
	
	//comment
	private String Channelname="InAppChannel";
	private String APPID_KEY = "1215";
	private String CLICENT_KEY = "7a956d6f257bd0a0601cf351daf42fe0";
	private static String pid;
	public static boolean isInit = false;
	@Override
	public void ActivityInit(Activity context, final APPBaseInterface appinterface)
	{		
		super.ActivityInit(context, appinterface);
		MercuryActivity.LogLocal("[InAppChannel][ActivityInit]mContext="+mContext);
		MercuryActivity.LogLocal("[InAppChannel][ActivityInit]APPID_KEY="+APPID_KEY);
		MercuryActivity.LogLocal("[InAppChannel][ActivityInit]CLICENT_KEY="+CLICENT_KEY);
		try {

			((ZhongTuiSdk)(ZhongTuiSdkFactory.getSdkProxyInstance())).init(mContext, APPID_KEY, CLICENT_KEY, new InitCallback() {
				@Override
				public void onInitSucceed() {
					MercuryActivity.LogLocal("初始化成功");
					isInit=true;
				}

				@Override
				public void onInitFailed(String s) {
					MercuryActivity.LogLocal("初始化失败");
				}
			});
			((ZhongTuiSdk)(ZhongTuiSdkFactory.getSdkProxyInstance())).registerSdkLogoutListener(new OnSdkLogoutListener() {
				@Override
				public void onSdkLogout() {

				}
			});
		} catch (Exception e) {
			MercuryActivity.LogLocal("初始化失败");
			e.printStackTrace();
		}
//		Toast.makeText(mContext, "只限于"+channelname+"测试，请勿泄漏", Toast.LENGTH_SHORT).show();
//		new IDCardVerifyDialog(mContext, new LoginCallBack() {
//			@Override
//			public void success(String msg) {
//				LogLocal("[InAppChannel][SingmaanLogin] ID card Success");
//				onFunctionCallBack("IDCardVerifyDialog");
//			}
//			@Override
//			public void fail(String msg) {
//				LogLocal("[InAppChannel][SingmaanLogin] ID card failed");
//			}
//		});

	}
	public void ApplicationInit(Application appcontext)
	{
		mAppContext=appcontext;
		MercuryActivity.LogLocal("[InAppChannel][ApplicationInit]="+Channelname);
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
		//shrinkpartstart
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
		((ZhongTuiSdk)(ZhongTuiSdkFactory.getSdkProxyInstance())).pay(mContext, payParams, new PayCallback() {
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

		//shrinkpartend

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
					((ZhongTuiSdk)(ZhongTuiSdkFactory.getSdkProxyInstance())).logout(new LogoutCallback() {
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
			builder.setTitle("TestPay");
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
			builder.setCancelable(false);
			builder.create().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void SingmaanLogin()
	{
		if(isInit)
		{
			((ZhongTuiSdk) (ZhongTuiSdkFactory.getSdkProxyInstance())).login(mContext, new LoginCallback() {
				@SuppressLint("SetTextI18n")
				@Override
				public void onLoginSucceed(final String s, final String s1) {
					MercuryActivity.LogLocal("登录成功" + "\n" + "uid:" + s + "\n" + "token:" + s1);
					LoginSuccessCallBack(s1);
				}

				@Override
				public void onLoginFailed(String s) {
					Log.e(Channelname, "登陆失败:" + s);
				}
			});
		}
		else
		{
			Toast.makeText(mContext, "网络繁忙，稍后再试。", Toast.LENGTH_SHORT).show();
			try {

				((ZhongTuiSdk)(ZhongTuiSdkFactory.getSdkProxyInstance())).init(mContext, APPID_KEY, CLICENT_KEY, new InitCallback() {
					@Override
					public void onInitSucceed() {
						MercuryActivity.LogLocal("初始化成功");
						isInit=true;
					}

					@Override
					public void onInitFailed(String s) {
						MercuryActivity.LogLocal("初始化失败");
					}
				});
				((ZhongTuiSdk)(ZhongTuiSdkFactory.getSdkProxyInstance())).registerSdkLogoutListener(new OnSdkLogoutListener() {
					@Override
					public void onSdkLogout() {

					}
				});
			} catch (Exception e) {
				MercuryActivity.LogLocal("初始化失败");
				e.printStackTrace();
			}
		}
		LogLocal("[InAppChannel][SingmaanLogin]" + DeviceId);

	}
	public void SingmaanLogout()
	{
		LogLocal("[MercuryActivity][SingmaanLogout]"+DeviceId);
		LoginCancelCallBack(DeviceId);
	}

	public void UploadGameData()
	{
		LogLocal("[MercuryActivity][SingmaanLogin]"+DeviceId);
		onFunctionCallBack("SingmaanUploadGameData");
	}
	public void DownloadGameData()
	{
		LogLocal("[MercuryActivity][SingmaanLogout]"+DeviceId);
		onFunctionCallBack("SingmaanDownloadGameData");
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
		MercuryActivity.LogLocal("["+Channelname+"][onPause]");
	}
	
	@Override
	public void onResume()
	{
		MercuryActivity.LogLocal("["+Channelname+"][onResume]");
	}
	@Override
	public void onDestroy()
	{
		MercuryActivity.LogLocal("["+Channelname+"][onDestroy]");
		((ZhongTuiSdk)(ZhongTuiSdkFactory.getSdkProxyInstance())).onDestroy();
	}
	@Override
	public void onStop() 
	{
		MercuryActivity.LogLocal("["+Channelname+"][onStop]");
		((ZhongTuiSdk)(ZhongTuiSdkFactory.getSdkProxyInstance())).onStop();
	}
	@Override
	public void onStart() 
	{
		MercuryActivity.LogLocal("["+Channelname+"][onStart]");
	}
	@Override
	public void onRestart()
	{
		MercuryActivity.LogLocal("["+Channelname+"][onRestart]");
		((ZhongTuiSdk)(ZhongTuiSdkFactory.getSdkProxyInstance())).onRestart(mContext);
	}
	public void onTerminate()
	{

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
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)  {
		((ZhongTuiSdk)(ZhongTuiSdkFactory.getSdkProxyInstance())).onRequestPermissionsResult(mContext, requestCode, permissions);
	}



	//end
}
