package com.mercury.game.InAppChannel;
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
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Looper;
import android.os.Process;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.mercury.game.MercuryActivity;
import com.mercury.game.util.MercuryConst;
import com.mercury.game.MercuryApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
	private String Channelname="InAppChannel";
	private static String pid;

	private String mSecret = "4399Efgh99d313G1";
	private String mGameId = "121237";
	private String checkInstall = "https://huodong3.3839.com/qudao/kuaibao/api2.php";
	private String checkUpdate = "https://huodong3.3839.com/hykb/gave/api/ApiGameAction.php";
	private String mVersion = "";
	public interface HttpCallBack {
		public void OnSucceed(String msg) throws JSONException;
		public void OnFailure(String msg);
	}
	@Override
	public void ActivityInit(Activity context, final APPBaseInterface appinterface)
	{		
		super.ActivityInit(context, appinterface);
//		verifyGame("ww2");
		MercuryActivity.LogLocal("["+Channelname+"]->init:InAppChannel.init="+context);
//		Toast.makeText(mContext, "只限于"+channelname+"测试，请勿泄漏", Toast.LENGTH_SHORT).show();

		mVersion = getAppVersionName(mContext);
//        Boolean isBool = isPlatformInstalled("com.qinbatista.demo");
//        Toast.makeText(mActivity, "主线程显示", Toast.LENGTH_SHORT).show();
		Send_HTTP_Get(checkInstall, new HttpCallBack() {
			@Override public void OnSucceed(String msg) throws JSONException {
				JSONObject jsonObj = new JSONObject(msg);
				JSONArray jsonArr1 = jsonObj.getJSONArray("expected");
				JSONArray jsonArr2 = jsonObj.getJSONArray("undesirable");
				JSONArray jsonArr3 = jsonObj.getJSONArray("undesirable_appname");
				String tmpMsg = jsonObj.getString("notinstallmsg");
				String tmpStr = jsonObj.getString("malwaremsg");
				final String tmpUrl = jsonObj.getString("gameurl");

				Looper.prepare();
				int state = 0;// 0未安装 1已安装 2不受欢迎
				int len = jsonArr1.length();
				for (int i = 0; i < len; i++) {
					//PackageInfo pi = pm.getPackageInfo(jsonArr1.get(i).toString(),0);
					if(isPlatformInstalled(jsonArr1.get(i).toString())){
						state = 1;
						break;
					}
				}
				len = jsonArr2.length();
				for (int i = 0; i < len; i++) {
					if(1 == state && isPlatformInstalled(jsonArr2.get(i).toString())){
						state = 2;
						tmpStr = tmpStr.replace("{appname}", jsonArr3.get(i).toString());
						break;
					}
				}
//                Boolean isBool = isPlatformInstalled("com.singmaan.gofree");
//                Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
				AlertDialog.Builder alert = new AlertDialog.Builder(mContext).setTitle("温馨提示");
				alert.setCancelable(false);
				state = 2;
				switch (state)
				{
					case 0: alert.setMessage(tmpMsg).setPositiveButton("前往下载", new OnClickListener(){
						@Override public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent();
							intent.setData(Uri.parse(tmpUrl));//Url 就是你要打开的网址
							intent.setAction(Intent.ACTION_VIEW);
							mContext.startActivity(intent);
							Process.killProcess(Process.myPid());
							System.exit(0);
						}
					}).create().show(); break;
					case 1: break;
					case 2: alert.setMessage(tmpStr).setPositiveButton("退出", new DialogInterface.OnClickListener(){
						@Override public void onClick(DialogInterface dialog, int which) {
							android.os.Process.killProcess(android.os.Process.myPid());
							System.exit(0);
						}
					}).create().show();

						break;
				}
				Looper.loop();
			}

			@Override public void OnFailure(String msg) {
				Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
			}
		});

		Send_HTTP_Post(checkUpdate, new HttpCallBack() {
			@Override public void OnSucceed(String msg) throws JSONException {
				JSONObject jsonObj = new JSONObject(msg);
				int code = jsonObj.getInt("code");
				Looper.prepare();
				switch (code) {
					case 100:{
						JSONObject json = jsonObj.getJSONObject("result");
						String info = json.getString("msg");
						final String dUrl = json.getString("download_url");
						String isFU = json.getString("force_update");
						String sVer = json.getString("last_client_version");

						if(info.equals("success") && !sVer.equals(mVersion) && isFU.equals("2")) {
							new Builder(mContext).setTitle("温馨提示").setCancelable(false).setMessage("Update").setPositiveButton("前往下载", new OnClickListener(){
								@Override public void onClick(DialogInterface dialog, int which) {
									Intent intent = new Intent();
									intent.setData(Uri.parse(dUrl));//Url 就是你要打开的网址
									intent.setAction(Intent.ACTION_VIEW);
									mContext.startActivity(intent);
									Process.killProcess(Process.myPid());
									System.exit(0);
								}
							}).create().show();
						}
						break;}
					case 99: Toast.makeText(mContext, "参数错误", Toast.LENGTH_SHORT).show(); break;
					case 98: Toast.makeText(mContext, "Token错误", Toast.LENGTH_SHORT).show(); break;
					case 97: Toast.makeText(mContext, "查询方法错误", Toast.LENGTH_SHORT).show(); break;
					case 96: Toast.makeText(mContext, "未查询到数据", Toast.LENGTH_SHORT).show(); break;
				}
				Looper.loop();
			}

			@Override public void OnFailure(String msg) {
				Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
			}
		});
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
		TestPay();

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
	}
	@Override
	public void onStop() 
	{
		MercuryActivity.LogLocal("["+Channelname+"] onStop");
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


	private void Send_HTTP_Get(String address, final HttpCallBack hcb)
	{
		final String fullUrl = address+"?gid="+mGameId;
		new Thread(new Runnable() {//
			@Override public void run() {
				HttpURLConnection connection = null;
				BufferedReader reader = null;
				try {
					URL url = new URL(fullUrl);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(5000);
					//获取输入流
					InputStream in = connection.getInputStream();

					//下面对获取到的输入流进行读取
					reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder sb = new StringBuilder();
					String line;
					while ((line = reader.readLine())!=null){
						sb.append(line);
					}
					if(null != hcb){
						hcb.OnSucceed(sb.toString());
//                        Log.i("TAG", "GetHttps:" + sb );
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (reader!= null ){
						try {
							//关闭bufferreader
							reader.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (connection != null){
						//关闭这个HTTP连接
						connection.disconnect();
					}
				}
			}
		}).start();
	}
	public String getCurTimeLong(){

		long timeStampSec = System.currentTimeMillis()/1000;
		return String.format("%010d", timeStampSec);
	}
	public static String md5(String string) {
		if (TextUtils.isEmpty(string)) {
			return "";
		}
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			byte[] bytes = md5.digest(string.getBytes());
			String result = "";
			for (byte b : bytes) {
				String temp = Integer.toHexString(b & 0xff);
				if (temp.length() == 1) {
					temp = "0" + temp;
				}
				result += temp;
			}
			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
	private void Send_HTTP_Post(final String address, final HttpCallBack hcb)
	{
		final String timeStamp = getCurTimeLong();
		final String sginSrc  = "#"+mGameId+"&"+mSecret+"*"+timeStamp+"|";
		new Thread(new Runnable(){
			@Override public void run() {
				try {
					URL url = new URL(address);
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("POST");
					connection.setDoInput(true);
					connection.setDoOutput(true);
					connection.setUseCaches(false);
					connection.setInstanceFollowRedirects(true); //有误重定向
					connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
					OutputStream outputStream = connection.getOutputStream();
					PrintStream printStream = new PrintStream(outputStream);
					//发送的参数
					String submitData = String.format("ac=readgameversion&ts=%1$s&gameid=%2$s&token=%3$s",timeStamp,mGameId,md5(sginSrc));
					printStream.print(submitData); //向服务器提交数据
					//接受数据
					if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
						InputStream inputStream = connection.getInputStream();
						BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
						StringBuilder sb = new StringBuilder();
						String line;
						while((line = bufferedReader.readLine()) != null){ //不为空进行操作
							sb.append(line);
						}
						if(null != hcb){
							hcb.OnSucceed(sb.toString());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

	public boolean isPlatformInstalled(String packageName) {
		android.content.pm.ApplicationInfo info = null;
		try {
			info = mContext.getPackageManager().getApplicationInfo(packageName, 0);
			return info != null;
		} catch (PackageManager.NameNotFoundException e) {
			return false;
		}
	}
	public static String getAppVersionName(Activity context) {
		String versionName = null;
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
		} catch (Exception e) {
			Log.e("VersionInfo", "Exception", e);
		}
		return versionName;
	}
	//end
}
