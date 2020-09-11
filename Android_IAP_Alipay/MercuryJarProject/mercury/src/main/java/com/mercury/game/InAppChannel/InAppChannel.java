package com.mercury.game.InAppChannel;
import com.alipay.sdk.app.PayTask;
import com.mercury.game.InAppDialog.IDCardVerifyDialog;
import com.mercury.game.InAppDialog.LoginDialog;
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
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.mercury.game.MercuryActivity;
import com.mercury.game.util.LoginCallBack;
import com.mercury.game.util.MercuryConst;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Map;
import java.util.Random;

import static com.mercury.game.MercuryActivity.DeviceId;
import static com.mercury.game.MercuryActivity.LogLocal;
import static com.mercury.game.MercuryApplication.channelname;
import static com.mercury.game.util.MercuryConst.GlobalProductionList;
//end


public class InAppChannel extends InAppBase {
	
	//comment
	private String Channelname="InAppChannel";
	private static String pid;
	/**
	 * 用于支付宝支付业务的入参 app_id。
	 */
	public static final String APPID = "2021001191645565";
	public static final String RSA2_PRIVATE = "";
	public static final String RSA_PRIVATE = "MIIEwAIBADANBgkqhkiG9w0BAQEFAASCBKowggSmAgEAAoIBAQDsq+XGiE9vqz6mq83RdK56cuqU8YKZbPeu98ljKbmpx3bffdPCrAWWiK0qZfsZHW5um4kB8jNbclKnsadlo0Ok2ufBeKUJdpVom5O/xtQCnprxLhAuEGTITiiCMEHpw2m9tnzcHAhZQuD7FiWMmMfqeoaN2ixPSv08QMDDzAL0Gs2cQcMNJkuc6x9nEuEg/oDatm0JWME2FUWtx/95C9aJyaS6gzrgPFIaH6jp2xbcoWG2O/EVEiERBsTfAXHKVv7zhRR0ZNeE7jGZM6Ewt87k+KCHomNJlz09K/KJbopGjJ8nNdm5Dtw3qmr6pf7Y3gy2BHRwAzhcN0+zp2gRlib/AgMBAAECggEBAN9DSf9/l3BAm1mfuQleiTn6LlFTg2A4626jUde6BOukvv8WNC3xGVRomvLkQXvvx72P/C9ZzBj4QADyFnhLDAT4fKiGpynGNCv6l+bSKi5OcNwUGC9cR7auBIjL/WIIdjgBbsg1qaqK7LHwsntvpSgNbeFjb2ld1IaCj1YlnqOm1N89MtVUo8fm844sO8XRsD9YYmzrnesIvp91oJJvFxc3XvYuRZAO/TtLuRMc6lnc0AX1ybI++DtutAd3vMcMBQtIGyuL7eUx2YTBxJXtPv91PQeeWQn1HGHv+SpDjkK+d6zHfbgtzOMhk0ckRUz4q1LhhLUtVnfDh8w1TIuFkAECgYEA+RE83iJ+2oz5bdeUnVt79BYxqlNb3jvjkVZsnfRg6ruRmuKvI6TQxtO82Io0XCH1v/9CW8FKlfuXouA4PiIjtkXo0wuTXbAdL/f64E2va2mdl1c/LLnaTaCO9vD1Igjnv79S5txmdK7pABwzsPuvPtOg1WAv4hY1HdTgomWW2FECgYEA80JU2j1ObTvkMmaMzJMaLUsbrTkcHOu5N56xgliccEjb2wM8QefbUsj/v4zggx19WLFiukNQHNzTYAxioA0iAhbD2QvnGfWL2A/iDm/P2tEArpGIccHEMpBfH4dDHGv32BJKqCFhXFVqiKWBF9ztHRMZDkWuL/VWeEw83UEfhk8CgYEA9GSQuFta5DLecYTPJBTnrRu2Ai6nf4p+g1ctX1SzYMFKX5O9TRllbyPHMydxt1HvZUUgpQ4mlML6CO1A4t728dzpV2UNZinwiegneL9huOE6rI0ExWtcpT0961uG/a2FUaZ3v2ZW9nnG0b/ajPh5/gkE0Tr/4TXvSuVewpsyh/ECgYEAjDV5TujzIUbZ+qrdELTg66ZU53z5VToQ4ZwYWDbWxGlaP1wYCSyoX4j2z+NVLH181/g2HYHa1Sf6tWuuFO484dNZQur3YyECX6CX/RY5SbgZmoTLjjXO7g4EpdmCtwkMK4Avg8TppxyccPJj++scyBtP38gN5BqWonPeyPBWSUUCgYEAqLlTT0e6pbtLvvoa96+vJDcaR8c9ks/tDfRe9sKSK4H0kn2U5t7XcXSKUaSCSHUE+UNtUeJw9L7emoglTbKY/cCOi1/AoxXNTq8GP/WW1bb2moCX9wpVmTCc0sfmEQbdcMHXl3f8SrZUoJlHJ79VyyerdqTmO7yzNdoTS54zvvk=";

	private static final int SDK_PAY_FLAG = 1;
	private static final int SDK_AUTH_FLAG = 2;
	@Override
	public void ActivityInit(Activity context, final APPBaseInterface appinterface)
	{		
		super.ActivityInit(context, appinterface);
		MercuryActivity.LogLocal("[InAppChannel][ActivityInit]="+Channelname);
		Toast.makeText(mContext, "只限于"+channelname+"测试，请勿泄漏", Toast.LENGTH_SHORT).show();
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
		AliPay();


	}
	@Override
	public void ExitGame()
	{
		try {
			AlertDialog.Builder builder = new Builder(mContext);
			builder.setMessage("Testing Mode");
			builder.setTitle("Choice Result");
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
	public void AliPay()
	{
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
	public void SingmaanLogin()
	{
		LogLocal("[InAppChannel][SingmaanLogin]" + DeviceId);
		//shrinkpartstart
		LoginDialog loginDialog = new LoginDialog(mContext, MercuryActivity.DeviceId, new LoginCallBack() {
			@Override
			public void success(final String phone) {
				LogLocal("[InAppChannel][SingmaanLogin] Success");
				//shrinkpartend
				LoginSuccessCallBack(DeviceId);
				//shrinkpartstart
				new IDCardVerifyDialog(mContext, new LoginCallBack() {
					@Override
					public void success(String msg) {
						LogLocal("[InAppChannel][SingmaanLogin] ID card Success");
						onFunctionCallBack("IDCardVerifyDialog");
					}
					@Override
					public void fail(String msg) {
						LogLocal("[InAppChannel][SingmaanLogin] ID card failed");
					}
				});
			}
			@Override
			public void fail(String msg) {
				LogLocal("[InAppChannel][SingmaanLogin] Login failed");
			}
		});
		//shrinkpartend
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
	}
	@Override
	public void onStop() 
	{
		MercuryActivity.LogLocal("["+Channelname+"][onStop]");
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




	//end
}
