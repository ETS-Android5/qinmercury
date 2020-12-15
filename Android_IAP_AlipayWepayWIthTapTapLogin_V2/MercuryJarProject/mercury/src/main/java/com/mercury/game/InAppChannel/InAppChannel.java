package com.mercury.game.InAppChannel;
import com.alipay.sdk.app.PayTask;
import com.mercury.game.InAppDialog.IDCardVerifyDialog;
import com.mercury.game.InAppDialog.LoginDialog;
import com.mercury.game.InAppDialog.PaymentDialog;
import com.mercury.game.InAppRemote.RemoteConfig;
import com.mercury.game.util.APPBaseInterface;
import com.mercury.game.util.Function;
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
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Toast;

import com.mercury.game.MercuryActivity;
import com.mercury.game.util.LoginCallBack;
import com.mercury.game.util.MD5;
import com.mercury.game.util.MercuryConst;
import com.mercury.game.util.PayMethodCallBack;
import com.taptap.sdk.AccessToken;
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
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.mercury.game.InAppChannel.Util.httpPost;
import static com.mercury.game.InAppChannel.Util.stringsToList;
import static com.mercury.game.InAppDialog.LoginDialog.local_age;
import static com.mercury.game.InAppRemote.RemoteConfig.chinese_id;
import static com.mercury.game.InAppRemote.RemoteConfig.get_remote_iap;
import static com.mercury.game.InAppRemote.RemoteConfig.id_verify_result;
import static com.mercury.game.InAppRemote.RemoteConfig.login_in;
import static com.mercury.game.MercuryActivity.DeviceId;
import static com.mercury.game.MercuryActivity.GameName;
import static com.mercury.game.MercuryActivity.LogLocal;
import static com.mercury.game.MercuryActivity.order_id;
import static com.mercury.game.MercuryApplication.channelname;
//import static com.mercury.game.util.Function.readFileData;
import static com.mercury.game.util.Function.writeFileData;
import static com.mercury.game.util.MercuryConst.GlobalProductionList;

import com.taptap.sdk.CallBackManager;
import com.taptap.sdk.LoginManager;
import com.taptap.sdk.LoginResponse;
import com.taptap.sdk.Profile;
import com.taptap.sdk.TapTapLoginCallback;
import com.taptap.sdk.TapTapSdk;
import com.taptap.sdk.net.Api;
//end


public class InAppChannel extends InAppBase {

	//comment
	private String Channelname="InAppChannel";
	private static String pid;
	/**
	 * 用于支付宝支付业务的入参 app_id。
	 */
	public String app_id = "0yFB9JbE7tFGGfSnlw";//taptap的id
	public static final String APPID = "2021001193697300";
	public static final String RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCgLjeGu1WoskYfyOxt9CA58MrO/LxpfZNGbNkhII5iO5t9dWPPUD8Zugt32dVPuNTHkJeZc9X8EvL9DDKaRzGC4EqT3BaOuUblblKZ5ItzEOyBLKKqGUR83n99F2oFaohqbbNMDUkwPQUPv4Sjkl2cuNXR5OPmpinOUKDVVdQ8bldlgWlxxT6iy9c4LfqDTmOro5yzMXA0ET34eC1Uvj1rB+KafkKbAWSOgbR6F6rgKkdn7BWU66e7nEhATI6xutessw7kYT8feywT+Os0Za4szXEWUFwU2losfH2GqgoWcwBZ4GL4Hb7S+GYXdA7XFFwf1WmC1o1BEhmGY0fN4g6JAgMBAAECggEAf06cNQn4953Q2/w95NnNLx+woLgAKztx7Nwf6hNM9sf3Ocwt6pwVuqXB7ZyEy9rTylSiGIUXAkQxOWsTYMjKkgEfZMrcZszciwaWwdcB+g7uWXAXTGfOpgvUeaA9VFaqWyQbB4vbqmok9rI5giOXITNKRYrMkwlnWqF8YnHXv7qOAo4RCuh+1cKb4Ckqxeok2fWEIaBdS1xO4+WcHXac1J/aX4eZgR1iG3nlvor9ZQOZn+30QxO9wEJ69+4OLevQfBTAb9pcLXrFkkvPHbUFdOSgZKlcHjoCdzNotDdKliGQW4OGmcuCP/ERVN5fpBc/WvMBe78PDdT1p03Uc0BkzQKBgQD5PtARWzSMFM1DsC37Q/P6EfvmyupF5u3Stps2uYPSlLkenJFO8zYHnF3MaU9g9MKn9nwImMuMIDlkE5NVFcATbvePKqPJsVWCFMCXuUVlQ21TFQO1xc3aYdhEQHvxEh/laFmazWGCYw9ork2DAY/jKjLDz9pxhcHN1zDmRHUjJwKBgQCkhX/58vvkRuiiHWB+4YOneQnQxrE+MCwSovQggA4oWk10DglfzGLSslXp//XLsj15jUmOgD3ZNRFWiBDkCC9dA9WTGNBiuuNNrlCS7yF37rUplUMmevonTvR0SyYlfl6ahdo5yXXAfz9w2eTTw/yapRvNG5C4QW4clsSBd92OzwKBgCTvmgX4biEUNBcD1MyXlWBJqfrZtz4Eqtm/FeFWPKLIR2ax7Ra2FBusoHnaYVkM7IvXiyn6+q8ZV2ftPrgtMPmwSB9/QiZxkSplyOSzIAWRqHHXe2VEmuzx8wqqQ7PF69QjUqQOK5UW+QGaUwJHCPuxFTTPaJ/KIp5OdYCqRHGhAoGBAJregIXNUYilpz9T4A4QQ1pW+gJpx1b/Cb3RX3VolesudlKVFAX14+Dqty07ISnKc0wE0AUwewgIiHWoSB4gBlXM0jNR3HfT7TympnpqWFsJfcfTFg8XHHv8OuluBE6vEmbrMW3MUugN4K5erqUZjKQWYIFHPYf1L64BzqEBMLLJAoGBAIGKzyusYCrhh3SG0J5wVmnY3NjVQibTg5BmPODlNduujHGWGdfJcRzwwQVap5LrF60rvxOn+9qre+wgCjrTukivNuyFYJgHPKsEloiWknvul73MDKMf0LDIIu7s4NUpvoVKtvY1PxwwLcH9TjP4Cull+cyCtta3CLJs+HzdXf9l";
	public static final String RSA_PRIVATE = "";
	public static final String WX_APP_ID = "wxb745727a544b480c";
	public static final String WX_MCH_ID = "1571964901";
	public static final String WX_API_KEY="75d99d3175225b6a907caf6734e21c4d";
	public static final String WXShareID="6938589979530b5b1b8220988e7c0180";
	private static final int SDK_PAY_FLAG = 1;
	private static final int SDK_AUTH_FLAG = 2;
	public static final String ALIPAY_NOTIFY_URL = String.format("https://gamesupport.singmaan.com:10013/%s/%s/client_success_callback",GameName,"alipay");
	public static final String WX_NOTIFY_URL = String.format("https://gamesupporttest.singmaan.com:10013/%s/%s/client_success_callback",GameName,"wxpay");
	private static String RESTORE_URL = "https://gamesupporttest.singmaan.com:10013/order/undelivered?user_id=%s";
	private static String UPDATE_ORDER_SUCCESS_URL = "https://gamesupporttest.singmaan.com:10013/order/deliver";
	private static String GET_REFUNDED_ORDER_URL = "https://gamesupporttest.singmaan.com:10013/order/refunded?user_id=%s";
	private static String CANCEL_ORDER_URL = "https://gamesupporttest.singmaan.com:10013/order/cancel";

	public String m_msg;
	public CallBackManager callBackManager = null;
	public static String global_orderId ="";
	public static String global_user_id ="";
	public static String global_production_id ="";
	public PayReq req;
	private IWXAPI msgApi;
	public  Map<String,String> resultunifiedorder;
	public  StringBuffer sb;
	@Override
	public void ActivityInit(Activity context, final APPBaseInterface appinterface)
	{
		super.ActivityInit(context, appinterface);
		MercuryActivity.LogLocal("[InAppChannel][ActivityInit]="+Channelname);
//		Toast.makeText(mContext, "只限于"+channelname+"测试，请勿泄漏", Toast.LENGTH_SHORT).show();
		req = new PayReq();
		sb=new StringBuffer();
		msgApi = WXAPIFactory.createWXAPI(mContext,WX_APP_ID);
		Restore();
		GetRefundedOrder();
		TapTapSdk.LoginSdkConfig config = new TapTapSdk.LoginSdkConfig();
		config.roundCorner = false; TapTapSdk.sdkInitialize(mContext, app_id, config);
		callBackManager = CallBackManager.Factory.create();
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
		final String price= String.valueOf((int)mProductPrice);
		if(local_age<18 && local_age!=0 )
		{
			try {
				AlertDialog.Builder builder = new Builder(mContext);
				builder.setMessage("提示");
				builder.setTitle("未成年人无法充值");
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});
				builder.create().show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else
		{
			new PaymentDialog(mContext, new PayMethodCallBack() {
				@Override
				public void Alipay(String msg) {
					MercuryActivity.LogLocal("[InAppChannel][Purchase] Alipay");
					AliPay_InApp();
					//TestPay();
				}

				@Override
				public void WechatPay(String msg) {
					MercuryActivity.LogLocal("[InAppChannel][Purchase] WechatPay");
					WechatPay_InApp();
					//TestPay();
				}
			});
		}
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
				onFunctionCallBack("CancelOrder:"+s);
				
			}
		});
	}


	@Override
	public void ExitGame()
	{
		try {
			AlertDialog.Builder builder = new Builder(mContext);
			builder.setMessage("你确定要退出游戏吗？");
			builder.setTitle("退出游戏");
			builder.setPositiveButton("确定", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					((Activity) MercuryActivity.mContext).finish();
					android.os.Process.killProcess(android.os.Process.myPid());
				}
			});
			builder.setNegativeButton("取消", new OnClickListener() {
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
	public void AliPay_InApp()
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
	public void WechatPay_InApp()
	{
		if(isNetworkAvailable(mContext))
		{
			GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
			getPrepayId.execute();
		}
		else {
			Toast.makeText(mContext, "没有可用网络，微信无法支付！", Toast.LENGTH_SHORT).show();
		}
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
//			order_id = channelname+"_"+GameName+"_"+order_id;
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
	private static String genNonceStr() {
		Random random = new Random();
		DateFormat format = new java.text.SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.US);
		String requestId = format.format(new Date());
		return MD5.getMessageDigest((String.valueOf(random.nextInt(10000))+requestId).getBytes());
	}

	public void TestPay()
	{
		try {
			AlertDialog.Builder builder = new Builder(mContext);
			builder.setMessage("是否购买商品");
			builder.setTitle("购买");
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					onPurchaseSuccess(MercuryConst.QinPid);
				}
			});
			builder.setNeutralButton("取消", new OnClickListener() {
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
		LoginDialog loginDialog = new LoginDialog(mContext, MercuryActivity.DeviceId, new LoginCallBack() {
			@Override
			public void success(String phone) {
				LogLocal("[InAppChannel][SingmaanLogin] Success");
				DeviceId = phone;
			}
			@Override
			public void fail(String msg) {
				LogLocal("[InAppChannel][SingmaanLogin] Login failed");
			}
		});

		LoginManager.getInstance().registerCallback(callBackManager, new TapTapLoginCallback<LoginResponse>() {
			@Override
			public void onSuccess(LoginResponse loginResult) {
				// TODO:登录成功
				LogLocal("[InAppChannel][SingmaanLogin]Profile.getCurrentProfile()=" + Profile.getCurrentProfile());
				LogLocal("[InAppChannel][SingmaanLogin]AccessToken.getCurrentAccessToken()=" + AccessToken.getCurrentAccessToken());
				Profile.fetchProfileForCurrentAccessToken(new Api.ApiCallback()
				{
					@Override
					public void onSuccess(final Object o) {
						LogLocal("[InAppChannel][SingmaanLogin]o=" + o.toString());
					}
					@Override
					public void onError(Throwable error) {
						LogLocal("[InAppChannel][SingmaanLogin]error=" + error.toString());
					}
				});
				LoginSuccessCallBack(loginResult.toString());
				LogLocal("[InAppChannel][SingmaanLogin]Profile" + Profile.getCurrentProfile().getOpenid());
				Verification();
			}
			@Override
			public void onCancel() {
				// TODO:用户取消
				LoginCancelCallBack("");
			}
			@Override
			public void onError(Throwable throwable) {
				// TODO:登录失败
				LogLocal("[InAppChannel][SingmaanLogin]error=" + throwable.toString());
				LoginCancelCallBack("");
			}
		});

		LoginManager.getInstance().logInWithReadPermissions(mContext, TapTapSdk.SCOPE_PUIBLIC_PROFILE);
	}

	public static String card_id = "";
	public void  Verification()
	{
		login_in(Profile.getCurrentProfile().getOpenid());
	}

	public static String ipaddress = "gamesupportcluster.singmaan.com";
	public static void set_check_chinese_id(final String id,final String account) {
		LogLocal("set_login_time:--------------------------------------------------------------------->11111111111111111");
		new Thread(new Runnable() {
			@Override
			public void run() {
				LogLocal("set_login_time:"+id);
//				Looper.prepare();
				try {
					//1.创建OkHttpClient对象
					OkHttpClient client = new OkHttpClient();
					//2.创建RequestBody对象
					RequestBody requestBody = new FormBody.Builder()
							.add("account", id)
							.add("code", account)
							.build();
					String url = "https://"+ipaddress+":10012"+"/setauthentication";//setlogintime
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
							LogLocal("[RemoteConfig][set_login_time] set_check_chinese_id failed="+s);
							/*if (s != null) {
								JSONObject json = null;
								try {
									json = (JSONObject) new JSONTokener(s).nextValue();
									LogLocal("get -------[RemoteConfig][origin_login_time] remote json=" + json);
									LoginDialog.play_time = (String) json.getString("data");
									if (LoginDialog.play_time != null && !LoginDialog.play_time.equals("")) {
										//do something
									}
									else {
										LoginDialog.play_time = "0";
									}
									//开始倒计时
									LoginDialog.Instance.age_difference(LoginDialog.play_time);
								} catch (JSONException e) {
									e.printStackTrace();
								}
								LogLocal("get --------[RemoteConfig][origin_login_time] ----------LoginDialog.Instance.play_time=>" + LoginDialog.play_time);
								LogLocal("get -------[RemoteConfig][origin_login_time] remote result=" + s);
							}*/
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
				}
//				Looper.loop();
			}
		}).start();
		//shrinkpartend
	}

	public void login_in(final String id) {
		LogLocal("[RemoteConfig][login_in] id:"+id);
		new Thread(new Runnable() {
			@Override
			public void run() {
				Looper.prepare();
				try {
					//1.创建OkHttpClient对象
					OkHttpClient client = new OkHttpClient();
					//2.创建RequestBody对象
					RequestBody requestBody = new FormBody.Builder()
							.add("account", id)
							//.add("password", password)
							.build();
					//3.创建Request对象
					Request request = new Request.Builder()
							.post(requestBody)
							.url("https://"+ ipaddress+":10012/getauthentication")//getlogintime
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
							LogLocal("[RemoteConfig][login_in] remote resultif="+s);
							if (s != null && !s.contains("500 S")) {
								JSONObject json = null;
								try {
									json = (JSONObject) new JSONTokener(s).nextValue();
									String json_result2 = (String)json.getString("data");
									chinese_id = json_result2;
									InAppChannel.card_id = json_result2;
								} catch (JSONException e) {
									e.printStackTrace();
								}
								LogLocal("[RemoteConfig][login_in] remote" + s + "chinese_id" + chinese_id);
								Looper.prepare();
								Looper.loop();
							}
						}
					});
				} catch (Exception e) {
					LogLocal("[InAppBase][Purchase]Exception");
					e.printStackTrace();
				} finally {
				}
				Looper.loop();
			}
		}).start();

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				LogLocal("[InAppBase]chinese_id");
				if (chinese_id.equals(""))
				{
					LogLocal("[InAppBase][Purchase]chinese_id");
					new IDCardVerifyDialog(mContext, new LoginCallBack() {
						@Override
						public void success(String msg) {
							LogLocal("[InAppDialog][LoginDialog] ID card Success");
						}
						@Override
						public void fail(String msg) {
							LogLocal("[InAppDialog][LoginDialog] ID card failed");
						}
					});
				}else {
					LogLocal("[InAppBase][Purchase]chinese_id Not Null");
					LoginDialog.Instance.age_difference(LoginDialog.Instance.play_time);
				}
				String id ="TapTapID:" + Profile.getCurrentProfile().getOpenid();
				InAppBase.appinterface.onFunctionCallBack(id);
			}
		},3000); // 延时1秒
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
	private  long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
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
		if (callBackManager != null) {
			callBackManager.onActivityResult(requestCode, resultCode, data); }
	}
	@Override
	public void onNewIntent(Intent intent)
	{
		MercuryActivity.LogLocal("["+Channelname+"] onNewIntent(Intent intent) ");
	}
	//end

/*	private String login_in_result="";*/
	/*public  String ip_address = "gamesupport.singmaan.com";
	public String login_in(final String account*//*, final String password*//*) {
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
							//.add("password", password)
							.build();
					//3.创建Request对象
					Request request = new Request.Builder()
							.post(requestBody)
							.url("https://"+ ip_address+":10012/loginwithpassword")
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
							LogLocal("[RemoteConfig][login_in] failed="+s);
							if (s != null && !s.contains("500")) {
								JSONObject json = null;
								try {
									json = (JSONObject) new JSONTokener(s).nextValue();
									RemoteConfig.login_in_result = (String) json.getString("status");

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
	}*/

	public static void age_difference(){
		LogLocal("[loginButton]验证成功");
		set_check_chinese_id(Profile.getCurrentProfile().getOpenid(),chinese_id);//分钟
		LoginDialog.Instance.age_difference(LoginDialog.play_time);
	}
}
