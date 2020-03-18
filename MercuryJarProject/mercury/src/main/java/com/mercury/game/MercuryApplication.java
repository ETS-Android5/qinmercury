package com.mercury.game;
import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;
import android.R;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.mercury.game.InAppChannel.InAppBase;
import com.mercury.game.InAppChannel.InAppChannel;


public class MercuryApplication extends Application{//UnicomApplicationWrapper {
	
	public static int mSimOperatorId = MercuryConst.ChinaNull;
	private static int mExtSDKId = -1;
	private static int mChannelId = -1;
	public static String msg = "";
	public static String channelname = "";
	public static String key="";
	public InAppBase mInApp;
	public static String iscarriersneed="1";
	public static String channelSplash="1";
	public static String isAntLogOpen="";
	public static String e2wnumber="";
	public static Application Acontext;
	public static String jsid="";
	public static String jschannel="";
	public static String jstjid="";
	private InAppBase mInAppExt;

	@Override
	public void onCreate() {
		super.onCreate();
		checkSIM();
		checkExtSDK();
		checkChannelName();
		checkChannelSplash();
		checkLoge();
		try
		{
			key=getSign(this);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.e("IAP","[SDKApp]SdkName="+msg);
	}
	public void APPApplicationInit(Application context)
	{
		Acontext = context;
		checkSIM();
		checkExtSDK();
		checkChannelName();
		checkChannelSplash();
		checkLoge();
		try 
		{
			 key=getSign(context);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.e("IAP","[SDKApp]SdkName="+msg);	

	}
	
	
	private String getSign(Context context) {

		PackageManager pm = context.getPackageManager();
		List<PackageInfo> apps = pm.getInstalledPackages(PackageManager.GET_SIGNATURES);
		Iterator<PackageInfo> iter = apps.iterator();
		while (iter.hasNext()) {
			PackageInfo packageinfo = iter.next();
			String packageName = packageinfo.packageName;
			if (packageName.equals(Acontext.getPackageName())) {				
				return packageinfo.signatures[0].toCharsString();

			}
		}
		return null;
	}

	private void checkSIM() {

		mSimOperatorId = MercuryConst.ChinaMobile;

		try {

			TelephonyManager telManager = (TelephonyManager) Acontext.getSystemService(Context.TELEPHONY_SERVICE);

			String imsi = telManager.getSubscriberId();

			if (imsi != null) {
				if (imsi.startsWith("46000") || imsi.startsWith("46002")
						|| imsi.startsWith("46007")) {
					mSimOperatorId = MercuryConst.ChinaMobile;
				} else if (imsi.startsWith("46001") || imsi.startsWith("46006")|| imsi.startsWith("46009")) {
					mSimOperatorId = MercuryConst.ChinaUnicom;
				} else if (imsi.startsWith("46003") || imsi.startsWith("46005")
						|| imsi.startsWith("20404")) {// 20404  Vodafone) {
					mSimOperatorId = MercuryConst.ChinaTelecom;
				}
			} else {

			}						
		} catch (Exception e) {
			e.printStackTrace();		

		}
	}
	
	private void checkExtSDK() 
	{		

			Log.e(MercuryConst.TAG, "[MercuryActivity] Default=ApplicationInit");
	    	mInAppExt = new InAppBase();
	    	mInAppExt.ApplicationInit(Acontext);
	}


	private void checkChannelName()
	{
		ApplicationInfo appInfo = null;
		try 
		{
			appInfo = Acontext.getPackageManager().getApplicationInfo(Acontext.getPackageName(),PackageManager.GET_META_DATA);
			String channelnametmp = appInfo.metaData.getString("CHANNEL_NAME");
			MercuryActivity.SortChannelID=channelname =channelnametmp;
			if(MercuryActivity.SortChannelID.equals("kp"))
			{
				MercuryActivity.LongChannelID="kupai";
			}
			
		} catch (NameNotFoundException e) {
		    Log.e(MercuryConst.TAG, "checkChannelName:Failed to load meta-data, CHANNEL_NAME NotFound: " + e.getMessage());
		    mChannelId = 0;
		} catch (NullPointerException e) {
		    Log.e(MercuryConst.TAG, "checkChannelName:Failed to load meta-data, CHANNEL_NAME NullPointer: " + e.getMessage());
		    mChannelId = -1;
		}

	}

	private void checkChannelSplash()
	{
		ApplicationInfo appInfo = null;
		try 
		{
			appInfo = Acontext.getPackageManager().getApplicationInfo(Acontext.getPackageName(),PackageManager.GET_META_DATA);
			String channelnametmp = appInfo.metaData.getString("CHANNEL_SPLASH");
			channelSplash =channelnametmp;
		} catch (NameNotFoundException e) {
		    Log.e(MercuryConst.TAG, "checkChannelSplash to load meta-data CHANNEL_SPLASH, NameNotFound: " + e.getMessage());
		    
		} catch (NullPointerException e) {
		    Log.e(MercuryConst.TAG, "checkChannelSplash to load meta-data CHANNEL_SPLASH, NullPointer: " + e.getMessage());
		}

	}
	private void checkLoge()
	{
		ApplicationInfo appInfo = null;
		try 
		{
			appInfo = Acontext.getPackageManager().getApplicationInfo(Acontext.getPackageName(),PackageManager.GET_META_DATA);
			isAntLogOpen = appInfo.metaData.getString("E2W_LOG");
			if(isAntLogOpen.equals("open"))
			{
				Log.e("IAP","Log Verison:"+MercuryConst.LogVERSION);
			}
		} catch (NameNotFoundException e) {
		    Log.e(MercuryConst.TAG, "checkLoge:Failed to load meta-data E2W_LOG, NameNotFound: " + e.getMessage());
		    
		} catch (NullPointerException e) {
		    Log.e(MercuryConst.TAG, "checkLoge:Failed to load meta-data E2W_LOG, NullPointer: " + e.getMessage());
		}

	}





}
