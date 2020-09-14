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
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.WindowManager;
import android.widget.Toast;

import com.mercury.game.MercuryActivity;
import com.mercury.game.util.LoginCallBack;
import com.mercury.game.util.MD5;
import com.mercury.game.util.MercuryConst;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import static com.mercury.game.InAppChannel.Util.httpPost;
import static com.mercury.game.MercuryActivity.DeviceId;
import static com.mercury.game.MercuryActivity.GameName;
import static com.mercury.game.MercuryActivity.LogLocal;
import static com.mercury.game.MercuryActivity.order_id;
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
	public static final String APPID = "2021001193697300";
	public static final String RSA2_PRIVATE = "";
	public static final String RSA_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCi/IHCOaXwpMBBEAa8iuflO5r12l7swMsOFMib7vGuHNGL1qTHOz9vH39Cv954UGvzD1rvd+ou6Bo+xav7NEiAh5nVvCM33ffP+a41G6OKHAmfazksz30zbT7V0uR3UHQSlu6pj7teNx7FRRlXx05gKHvQ2VxD9YeaBzgJbZqV0IMR7mB4lAc+8Vow0HoGcxUeFgO+x4BV+rNLNctPpnw9UrfxJsoDsSerAq98Bxf0JiIoU1vQ6c1YGqThM4AClEdZWMUNXFXRNvzA9Thbd46t05FMNRlHKqH03LSwzWj53XR3eoG/5R+OYyGFXCmwlCJYQTC572/CJO8vVkFza08xAgMBAAECggEAHWDQbtb/TzoTIWcnH7MWC57L1sbu11QFBdu/MURHse+l4cPl3beTPo952+lDy4ILgztgU8vUnXHe0TPGRThuXSSUGQ+ZdYeAFK/Xs+DwgIN/RIoKO0aHNc7aRaaG4e7RjUxBZrTuvBPJaxuCbu0Q9oo1jzaux90o/R2R/zkPZTNv1364RT+i3DtNs+0bUEhkEdvbRJisuBIAL1u6WKUmqrXVOx1mtWKnesxjzijK9EoKrD6veXKTd3bEzs8yFfR/YTexwXRUr58qkUz7ljZvBZJts7D1Llmp6UBxdHNS5NfaVuU4ysePAG+L4zJ4RatZz3MLKohrQJAvpCxPmIoGAQKBgQDPDrt26q6Iav2S53tosBtmnNKMYhqWhuY53g9h5AMI0vP1LmEeuxR9ZrRXWUG9znrKBah/NzLRo74mWJH+/+Sp9zDKSk5q4R0fIr23B5FD9TMF0R2q4mw0rOcEpLaFvw2qqy3DbLvNyuSExIGeTq425e+F8q3n9yXsZZeL/mMs0QKBgQDJgvsXwfPLw1O3szMPh7ctpL2Z+4aBYbZZ+VH6HCNpXPd0yiBo2Eh53ZaPjvYhlX3xu8mIRopMpR8YJa5KyUnXHwoS9xs4f+Q/WCR1cqDwgSlC8fx5p+JUg08b115Z6xxvTiP3H+bb6qn4FKXaHDvg2o9UXQiVZxrVAtPX1KcUYQKBgCgW8SMAI1TUak5UNWe6mUOP35BAumckrVTM8uuAKzo9JfD1zuYVUM1K4mX7KShn3wxYdhxTgqpmar2f7nyR7SMfcjnokzBMb8gEgPj8JRskUYGx0G2ys0Krq3sRrSlOKYY+6HhrCB27R+2Q4ovVLhQBBxRHPXapOpV/wgzf7zHRAoGAdkcGIJy7/3bHtcReDRiIwSa4DyCeg2SaBtebcWWAt4BU0t0tBr0kVTtl+x9bgrzfLrsdgHp/BJvK036Sfd0GFVlnrVgTRydyDmgrBjDhHCmD0YJ9wd5zr01faqUQAVFJ4F8KJyw3cg+b8jwUWSBHWSSQCmGM/zyEeFDvjPiJlSECgYEAxKZWL/bZgTxLWxQeFsFRGtTLUCjPc8YJgdj7mMsOi2rTcCzu9zeON39+Ou74FCGDOL12Rzeu64Qpm2j2ZF/tVSPAE1PHuEL2wY+mwcdNcpaa8u8tGbYEmnAIUG0O6e0cdsFEDF4vgGmpYgXr4BuNS6RJP3RmEopIq6vlbYdIr90=";
	public static final String WX_APP_ID = "wxb745727a544b480c";
	public static final String WX_MCH_ID = "1571964901";
	public static final String WX_API_KEY="75d99d3175225b6a907caf6734e21c4d";
	public static final String WXShareID="6938589979530b5b1b8220988e7c0180";
	private static final int SDK_PAY_FLAG = 1;
	private static final int SDK_AUTH_FLAG = 2;
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
		try {
			AlertDialog.Builder builder = new Builder(mContext);
			builder.setMessage("选择支付方式");
			builder.setTitle("提示");
			builder.setPositiveButton("微信支付", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					WechatPay();
				}
			});
			builder.setNeutralButton("支付宝支付", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					AliPay();
				}
			});
			AlertDialog alertDialog;
			alertDialog = builder.create();// AlertDialog.Builder.create();
			alertDialog.setCancelable(false);
			alertDialog.setCanceledOnTouchOutside(false);
			alertDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
					WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
			alertDialog.show();

			alertDialog.getWindow().getDecorView()
					.setSystemUiVisibility((mContext).getWindow()
							.getDecorView().getSystemUiVisibility());
			alertDialog.getWindow()
					.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
		} catch (Exception e) {
			e.printStackTrace();
		}


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
	public void WechatPay()
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
			MercuryActivity.LogLocal("[InAppChannel][genProductArgs] order_id = " + order_id);
			packageParams.add(new BasicNameValuePair("attach", DeviceId+","+MercuryConst.QinPid+","+channelname+"_"+GameName+"_"+order_id));
			packageParams.add(new BasicNameValuePair("body", MercuryConst.Qindesc));
			packageParams.add(new BasicNameValuePair("mch_id", WX_MCH_ID));
			packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
			packageParams.add(new BasicNameValuePair("notify_url","weixinCPS"));

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
	}
	@Override
	public void onNewIntent(Intent intent) 
	{
		MercuryActivity.LogLocal("["+Channelname+"] onNewIntent(Intent intent) ");
	}




	//end
}
