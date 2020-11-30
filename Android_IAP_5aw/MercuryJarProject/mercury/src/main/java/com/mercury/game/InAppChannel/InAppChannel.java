package com.mercury.game.InAppChannel;
import com.mercury.game.InAppDialog.IDCardVerifyDialog;
import com.mercury.game.InAppDialog.LoginDialog;
import com.mercury.game.InAppDialog.PaymentDialog;
import com.mercury.game.InAppDialog.PrivacyDialog;
import com.mercury.game.InAppDialog.SigneInDialog;
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
import android.widget.Toast;

import com.mercury.game.MercuryActivity;
import com.mercury.game.util.LoginCallBack;
import com.mercury.game.util.MercuryConst;
import com.mercury.game.util.PayMethodCallBack;
import com.mercury.game.util.SPUtils;
import com.mercury.game.util.SpConfig;
import com.yoogame.sdk.Hg5awGameSDK;
import com.yoogame.sdk.common.SignInResult;
import com.yoogame.sdk.interfaces.HgIFManager;
import com.yoogame.sdk.interfaces.InitCallback;
import com.yoogame.sdk.interfaces.PaymentCallback;
import com.yoogame.sdk.interfaces.SignInCallback;
import com.yoogame.sdk.interfaces.SignOutCallback;
import com.yoogame.sdk.interfaces.SubmitCallback;
import com.yoogame.sdk.payment.entity.OrderInfo;
import com.yoogame.sdk.payment.entity.PayResult;
import com.yoogame.sdk.payment.entity.RoleInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.mercury.game.InAppDialog.LoginDialog.local_age;
import static com.mercury.game.InAppRemote.RemoteConfig.chinese_id;
import static com.mercury.game.MercuryActivity.DeviceId;
import static com.mercury.game.MercuryActivity.GameName;
import static com.mercury.game.MercuryActivity.LogLocal;
import static com.mercury.game.MercuryActivity.order_id;
import static com.mercury.game.MercuryApplication.channelname;
import static com.mercury.game.util.Function.readFileData;
import static com.mercury.game.util.Function.writeFileData;
import static com.mercury.game.util.MercuryConst.GlobalProductionList;
//end


public class InAppChannel extends InAppBase {
	
	//comment
	private String Channelname="InAppChannel";

	private static String pid;
	private static String RESTORE_URL = "https://gamesupporttest.singmaan.com:10013/order/undelivered?user_id=%s";
	private static String UPDATE_ORDER_SUCCESS_URL = "https://gamesupporttest.singmaan.com:10013/order/deliver";
	private static String GET_REFUNDED_ORDER_URL = "https://gamesupporttest.singmaan.com:10013/order/refunded?user_id=%s";
	private static String CANCEL_ORDER_URL = "https://gamesupporttest.singmaan.com:10013/order/cancel";
	public static String global_orderId ="";
	public static String global_user_id ="";
	public static String global_production_id ="";
	@Override
	public void ActivityInit(Activity context, final APPBaseInterface appinterface)
	{		
		super.ActivityInit(context, appinterface);
		MercuryActivity.LogLocal("[InAppChannel][ActivityInit]="+Channelname);
		Hg5awGameSDK.getInstance().onCreate(mContext, mBundle);
		Hg5awGameSDK.getInstance().init(mContext, "42c84435e5f9247d82f3f4a9161c50c3","1000247",  new InitCallback() {
			@Override
			public void onSuccess() {
				LogLocal("[InAppChannel][ActivityInit]初始化成功");
			}

			@Override
			public void onFailure(int code, String msg) {
				LogLocal("[InAppChannel][ActivityInit]初始化失败:"+msg);
			}
		});

//		Toast.makeText(mContext, "只限于"+channelname+"测试，请勿泄漏", Toast.LENGTH_SHORT).show();
//		if(readFileData("privacyagreement").equals(""))
//		{
//			new PrivacyDialog(mContext);
//		}
//		if(readFileData("card_id").equals("")) {
//			new IDCardVerifyDialog(mContext, new LoginCallBack() {
//				@Override
//				public void success(String msg) {
//					LogLocal("[InAppDialog][SigneInDialog] ID card Success");
//				}
//
//				@Override
//				public void fail(String msg) {
//					LogLocal("[InAppDialog][SigneInDialog] ID card failed");
//				}
//			});
//		}
	}
	public void autoSignIn() {
		Hg5awGameSDK. getInstance().autoSignIn(mContext, mSignInCallback);
	}
	public void ActivityBundle(Bundle bundle)
	{
		super.ActivityBundle(bundle);
		MercuryActivity.LogLocal("[InAppChannel][ActivityBundle]="+bundle);
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


	public void GetRefundedOrder(){
		final String url = String.format(GET_REFUNDED_ORDER_URL,DeviceId);
		new Thread(new Runnable() {
			@Override
			public void run() {
				OkHttpClient client = new OkHttpClient();
				Request request = new  Request.Builder().url(url).build();
				client.newCall(request).enqueue(new Callback() {
					@Override
					public void onFailure(Call call, IOException e) {
						LogLocal("[InAppChannel][getrefundedorder] error:"+e.getMessage());
					}
					@Override
					public void onResponse(Call call, Response response) throws IOException {
						String s = response.body().string();
						if (s!=null){
							try {
								JSONObject jsonObject = new JSONObject(s);
								JSONArray array = jsonObject.getJSONArray("data");
								int size = array.length();
								LogLocal("[InAppChannel][getrefundedorder] data size:"+size);
								for (int i = 0; i < size; i++) {
									JSONObject order = array.getJSONObject(i);
									String user_id = order.getString("user_id");
									String orderId = order.getString("order_id");
									if ((user_id!=null && orderId!=null)||(user_id!="" && orderId!="")){
										CancelOrder(user_id,orderId);
										LogLocal("[InAppChannel][getrefundedorder] cancel success");
									}
								}
							} catch (Exception e) {
								LogLocal("[InAppChannel][getrefundedorder] error:"+e.getMessage());
							}
						}

					}
				});
			}
		}).start();
	}

	public void Restore() {
		final String url = String.format(RESTORE_URL,DeviceId);
		new Thread(new Runnable() {
			@Override
			public void run() {
				Looper.prepare();
				OkHttpClient client = new OkHttpClient();
				Request request = new  Request.Builder().url(url).build();
				client.newCall(request).enqueue(new Callback() {
					@Override
					public void onFailure(Call call, IOException e) {
						LogLocal("[InAppChannel][restore] error 2:"+e.getMessage());
					}
					@Override
					public void onResponse(Call call, Response response) throws IOException {
						String s = response.body().string();
						if (s!=null){
							try {
								JSONObject jsonObject = new JSONObject(s);
								JSONArray array = jsonObject.getJSONArray("data");
								int size = array.length();
								LogLocal("[InAppChannel][restore] data size:"+size);
								Looper.prepare();
								for (int i = 0; i < size; i++) {
									JSONObject order = array.getJSONObject(i);
									global_user_id = order.getString("user_id");
									global_orderId = order.getString("order_id");
									global_production_id = order.getString("production_id");
									if ((global_user_id!=null && global_orderId!=null)||(global_user_id!="" && global_orderId!="")){
										onPurchaseSuccess(global_production_id);
										LogLocal("[InAppChannel][restore] update success");
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
	}
	public void UpdateOrderSuccess(final String userId, final String orderId){
		LogLocal("[MercuryActivity][InAppChannel][UpdateOrderSuccess]");
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody=new FormBody.Builder().
				add("user_id",userId).
				add("order_id",orderId).build();
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
	}


	public void CancelOrder(final String userId, final String orderId){
		LogLocal("[MercuryActivity][InAppChannel][CancelOrder]");
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody=new FormBody.Builder().
				add("user_id",userId).
				add("order_id",orderId).build();
		Request request=new  Request.Builder().url(CANCEL_ORDER_URL).post(formBody).build();
		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				LogLocal("[InAppChannel][CancelOrderOrder] result error:"+e.getMessage());
			}
			@Override
			public void onResponse(Call call, Response response) throws IOException {
				String s = response.body().string();
				LogLocal("[InAppChannel][CancelOrderOrder] result:"+s);
			}
		});
	}


	@Override
	public void Purchase(final String strProductId) {
		pid = strProductId;
		MercuryConst.PayInfo(strProductId);
		MercuryActivity.LogLocal("[InAppChannel][Purchase] MercuryConst.QinPid="+MercuryConst.QinPid);
		MercuryActivity.LogLocal("[InAppChannel][Purchase] MercuryConst.Qindesc="+MercuryConst.Qindesc);
		MercuryActivity.LogLocal("[InAppChannel][Purchase] MercuryConst.Qinpricefloat="+MercuryConst.Qinpricefloat);
		//        String productID = productIdEditText.getText().toString().trim();               //商品ID
//        String productName = productIdEditText.getText().toString().trim();               //商品名称
		String price = MercuryConst.Qinpricefloat+"";              //金额
		String productID = MercuryConst.QinPid;               //商品ID
		String productName =  MercuryConst.Qindesc;            //商品名称
		String serverId = "3";                  //游戏区服ID
		String serverName = "server_03";           //游戏区服名称
		String roleId = "r307551";                   //角色ID
		String roleName = "Nicole";               //角色名称
		String roleLevel = "60";                 //角色等级
		String currencyType = "USD";             //貨幣類型
		String notifyURL = "https://gamesupporttest.singmaan.com:10013/foodtruckchef/5aw/client_success_callback";                   //支付回调
		order_id = channelname+"_"+GameName+"_"+order_id;
		JSONObject extensionJson = new JSONObject();
		try {
			extensionJson.put("device_id",DeviceId);
			extensionJson.put("des","");
			extensionJson.put("order_id",order_id);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String extension = extensionJson.toString();                   //附加参数
		OrderInfo orderInfo = new OrderInfo.Builder()
				.withPrice(price)
				.withProductId(productID)
				.withProductName(productName)
				.withServerId(serverId)
				.withServerName(serverName)
				.withRoleId(roleId)
				.withRoleName(roleName)
				.withRoleLevel(roleLevel)
				.withCurrencyType(currencyType)
				.withNotifyURL(notifyURL)
				.withExtension(extension)
				.build();
		Hg5awGameSDK.getInstance().pay(mContext, orderInfo, mPaymentCallback);

//		if(local_age<8 && local_age!=0 )
//		{
//			try {
//				AlertDialog.Builder builder = new Builder(mContext);
//				builder.setMessage("提示");
//				builder.setTitle("未成年人无法充值");
//				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
////						onPurchaseSuccess(MercuryConst.QinPid);
//					}
//				});
//				builder.create().show();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		else
//		{
//			//shrinkpartstart
//			new PaymentDialog(mContext, new PayMethodCallBack() {
//				@Override
//				public void Alipay(String msg) {
//					MercuryActivity.LogLocal("[InAppChannel][Purchase] Alipay");
//					//shrinkpartend
//					TestPay();
//					//shrinkpartstart
//				}
//
//				@Override
//				public void WechatPay(String msg) {
//					MercuryActivity.LogLocal("[InAppChannel][Purchase] WechatPay");
//					TestPay();
//				}
//			});
//			//shrinkpartend
//		}


	}
	@Override
	public void ExitGame()
	{
		try {
			AlertDialog.Builder builder = new Builder(mContext);
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
	public void signIn()
	{
		Hg5awGameSDK.getInstance().signIn(mContext, mSignInCallback);
	}
	public void SingmaanLogin()
	{
		String phone = "";
		LogLocal("[InAppChannel][SingmaanLogin]" + DeviceId);
		signIn();
		//shrinkpartstart
//		LoginDialog loginDialog = new LoginDialog(mContext, MercuryActivity.DeviceId, new LoginCallBack() {
//			@Override
//			public void success(String phone) {
//				LogLocal("[InAppChannel][SingmaanLogin] Success");
//				DeviceId = phone;
//				//shrinkpartend
//				LoginSuccessCallBack(DeviceId);
//				//shrinkpartstart
//			}
//			@Override
//			public void fail(String msg) {
//				LogLocal("[InAppChannel][SingmaanLogin] Login failed");
//				LoginCancelCallBack(msg);
//			}
//		});
		//shrinkpartend
	}
	public void SingmaanLogout()
	{
		LogLocal("[MercuryActivity][SingmaanLogout]"+DeviceId);
		writeFileData("chineseid","");
		writeFileData("account","");
		LoginCancelCallBack(DeviceId);
	}
	public void MercuryIDVerify()
	{


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
	public void VIPPanel() {
		try {
			AlertDialog.Builder builder = new Builder(mContext);
			builder.setMessage("Testing Mode");
			builder.setTitle("VIPPanel");
			builder.setPositiveButton("Success", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					try
					{
						String[][] item_list = {{"Coin", "1000"},{"Token", "15"}};
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
						MercuryActivity.LogLocal("[VIPPanel]OnClaimReward="+reward_list.toString());
						OnClaimReward(reward_list.toString());
					}
					catch (JSONException e)
					{
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

	public void DailyCheckInPanel() {
		try {
			AlertDialog.Builder builder = new Builder(mContext);
			builder.setMessage("Testing Mode");
			builder.setTitle("DailyCheckInPanel");
			builder.setPositiveButton("Success", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					try
					{
						String[][] item_list = {{"Coin", "1000"},{"Token", "15"}};
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
						MercuryActivity.LogLocal("[DailyCheckInPanel]OnClaimReward="+reward_list.toString());
						OnClaimReward(reward_list.toString());
					}
					catch (JSONException e)
					{
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

	@Override
	public void onPause()
	{
		MercuryActivity.LogLocal("["+Channelname+"][onPause]");
		Hg5awGameSDK.getInstance().onPause(mContext);
	}

	@Override
	public void onResume()
	{
		MercuryActivity.LogLocal("["+Channelname+"][onResume]");
		Hg5awGameSDK.getInstance().onResume(mContext);
	}
	@Override
	public void onDestroy()
	{
		MercuryActivity.LogLocal("["+Channelname+"][onDestroy]");
		Hg5awGameSDK.getInstance().onDestroy(mContext);
	}
	@Override
	public void onStop()
	{
		MercuryActivity.LogLocal("["+Channelname+"][onStop]");
		Hg5awGameSDK.getInstance().onStop(mContext);
	}
	@Override
	public void onStart()
	{
		MercuryActivity.LogLocal("["+Channelname+"][onStart]");
		Hg5awGameSDK.getInstance().onStart(mContext);
		setSdkCallback();
	}
	private SignOutCallback mSignOutCallback = new SignOutCallback() {
		@Override
		public void onSuccess(int code) {
			if (code == SignOutCallback.SWITCH_ACCOUNT) {
				Hg5awGameSDK.getInstance().signIn(mContext, mSignInCallback);
			}
			MercuryActivity.LogLocal("["+Channelname+"][SignOutCallback]登出成功");
		}

		@Override
		public void onFailure(int code, String msg) {
			MercuryActivity.LogLocal("["+Channelname+"][SignOutCallback]登出失败" + msg);
		}
	};
	private SignInCallback mSignInCallback = new SignInCallback() {
		@Override
		public void onSuccess(SignInResult signInResult) {
			MercuryActivity.LogLocal("["+Channelname+"][SignOutCallback]登录成功" + signInResult.toString());
			Toast.makeText(mContext,"登入成功",Toast.LENGTH_SHORT).show();
			String sid = null;
			try {
				JSONObject res = new JSONObject(signInResult.toString());
				sid = res.getString("sid");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			LoginSuccessCallBack(sid);
		}

		@Override
		public void onFailure(int code, String msg) {
			MercuryActivity.LogLocal("["+Channelname+"][SignOutCallback]登录失败" + msg);
			Toast.makeText(mContext,"登录失败",Toast.LENGTH_SHORT).show();
			LoginCancelCallBack(msg);
		}
	};

	private PaymentCallback mPaymentCallback = new PaymentCallback() {
		@Override
		public void onSuccess(PayResult payResult) {
			MercuryActivity.LogLocal("["+Channelname+"][SignOutCallback]支付成功#" + payResult.toString());
		}

		@Override
		public void onFailure(int code, String msg) {
			MercuryActivity.LogLocal("["+Channelname+"][SignOutCallback]支付失败#" + msg);
		}
	};

	private SubmitCallback mSubmitCallback = new SubmitCallback() {
		@Override
		public void onSuccess() {
			MercuryActivity.LogLocal("["+Channelname+"][SignOutCallback]上报成功");
		}
		@Override
		public void onFailure(int code, String msg) {
			MercuryActivity.LogLocal("["+Channelname+"][SignOutCallback]上报失败"+msg);
		}
	};

	private void setSdkCallback() {
// 􏱃􏲓􏲔􏱓􏲂􏲃􏱲􏲕􏲖􏲗􏱁􏰊􏲘􏲙app􏲚􏰩􏰭􏰮􏲛􏰪􏲜􏱴
		HgIFManager.getInstance().setSignInCallback(mSignInCallback);
		HgIFManager.getInstance().setSignOutCallback(mSignOutCallback);
		HgIFManager.getInstance().setPaymentCallback(mPaymentCallback);
		HgIFManager.getInstance().setSubmitCallback(mSubmitCallback);
//		HgIFManager.getInstance().setRewardedAdCallback(mRewardedAdCallback)
		;
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
		Hg5awGameSDK.getInstance().onActivityResult(requestCode, resultCode, data);
	}
	@Override
	public void onNewIntent(Intent intent)
	{
		MercuryActivity.LogLocal("["+Channelname+"] onNewIntent(Intent intent) ");
	}
	@Override
	public void attachBaseContext(Context newBase)
	{
		Context context = Hg5awGameSDK.getInstance().wrapConfigurationContext(newBase);
		super.attachBaseContext(context);
	}

	public void onWindowFocusChanged(boolean hasFocus)
	{
		Context context = Hg5awGameSDK.getInstance().wrapConfigurationContext(mContext);
		super.onWindowFocusChanged(hasFocus);
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
	public void onSubmitUserData(String mroleLevel) {
		//上报类型
		String type = RoleInfo.TYPE_LEVEL_UP;
		//游戏区服ID
		String serverId = DeviceId;
		//游戏区服名称
		String serverName = "S1";
		//角色ID0
		String roleId = DeviceId;        //角色名称
		String roleName = DeviceId;
		//角色等级
		String roleLevel = mroleLevel;
		//充值等级
		String payLevel = "Lv0";
		//拓展字段
		String extension = "extension";
		RoleInfo roleInfo = new RoleInfo.Builder()
				.withType(type)
				.withServerId(serverId)
				.withServerName(serverName)
				.withRoleId(roleId)
				.withRoleName(roleName)
				.withRoleLevel(roleLevel)
				.withPayLevel(payLevel)
				.withExtension(extension)
				.build();
		Hg5awGameSDK.getInstance().submitExtraData(mContext, roleInfo, mSubmitCallback);
	}
	public void Data_Event(String key) {
		onSubmitUserData(key);
	}

}
