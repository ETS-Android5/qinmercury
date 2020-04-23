package com.mercury.game;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.mercury.game.InAppAdvertisement.InAppAD;
import com.mercury.game.util.InAppBase;
import com.mercury.game.util.MercuryConst;
import com.reyun.tracking.sdk.Tracking;
import com.umeng.commonsdk.UMConfigure;

import java.util.Iterator;
import java.util.List;


public class MercuryApplication extends Application{//UnicomApplicationWrapper {
	private static int mExtSDKId = -1;
	private static int mChannelId = -1;
	public static String msg = "";
	public static String channelname = "";
	public static String key="";
	public InAppBase mInApp;
	public static String iscarriersneed="1";
	public static String channelSplash="1";
	public static Application Acontext;
	public static boolean OpenUmeng = false;
	private InAppBase mInAppExt;
	public static String channel_name = "";
	@Override
	public void onCreate() {
		super.onCreate();
		checkSIM();
		checkExtSDK();
		checkChannelName();
		checkChannelSplash();
		try
		{
			key=getSign(this);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void APPApplicationInit(Application context)
	{
		Acontext = context;
		checkSIM();
		checkExtSDK();
		checkChannelName();
		checkChannelSplash();
		OpenUmeng();

		channel_name = "singmaan";
		Log.w("MercurySDK","[SDKApp]SdkName="+channel_name);
		Log.w("MercurySDK","[SDKApp]InAppAD");
		mInAppExt = new InAppAD();
		mInAppExt.ApplicationInit(Acontext);
		if (OpenUmeng ==true) {
			Tracking.setDebugMode(true);
			Tracking.initWithKeyAndChannelId(context,"f4160d760ca45f3cffb7a37c0257c547","_default_");
			UMConfigure.init(context, "5ea12d7a978eea083f0c8960", channel_name, 0, "");
			UMConfigure.setProcessEvent(true);
		}

		try 
		{
			 key=getSign(context);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

//		mSimOperatorId = MercuryConst.ChinaMobile;
//
//		try {
//
//			TelephonyManager telManager = (TelephonyManager) Acontext.getSystemService(Context.TELEPHONY_SERVICE);
//
//			String imsi = telManager.getSubscriberId();
//
//			if (imsi != null) {
//				if (imsi.startsWith("46000") || imsi.startsWith("46002")
//						|| imsi.startsWith("46007")) {
//					mSimOperatorId = MercuryConst.ChinaMobile;
//				} else if (imsi.startsWith("46001") || imsi.startsWith("46006")|| imsi.startsWith("46009")) {
//					mSimOperatorId = MercuryConst.ChinaUnicom;
//				} else if (imsi.startsWith("46003") || imsi.startsWith("46005")
//						|| imsi.startsWith("20404")) {// 20404  Vodafone) {
//					mSimOperatorId = MercuryConst.ChinaTelecom;
//				}
//			} else {
//
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//
//		}
	}
	
	private void checkExtSDK() 
	{		

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
//		    Log.e(MercuryConst.TAG, "checkChannelName:Failed to load meta-data, CHANNEL_NAME NotFound: " + e.getMessage());
		    mChannelId = 0;
		} catch (NullPointerException e) {
//		    Log.e(MercuryConst.TAG, "checkChannelName:Failed to load meta-data, CHANNEL_NAME NullPointer: " + e.getMessage());
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
//		    Log.e(MercuryConst.TAG, "checkChannelSplash to load meta-data CHANNEL_SPLASH, NameNotFound: " + e.getMessage());
		    
		} catch (NullPointerException e) {
//		    Log.e(MercuryConst.TAG, "checkChannelSplash to load meta-data CHANNEL_SPLASH, NullPointer: " + e.getMessage());
		}

	}
	private void OpenUmeng()
	{
		ApplicationInfo appInfo = null;
		try 
		{
			appInfo = Acontext.getPackageManager().getApplicationInfo(Acontext.getPackageName(),PackageManager.GET_META_DATA);
			String isUmengOpen = appInfo.metaData.getString("open_umeng");
			if(isUmengOpen.equals("open"))
			{
				OpenUmeng=true;
				Log.e("MercurySDK","umeng opened");
			}
		} catch (NameNotFoundException e) {
//		    Log.e(MercuryConst.TAG, "checkLoge:Failed to load meta-data open_umeng, NameNotFound: " + e.getMessage());
		    
		} catch (NullPointerException e) {
//		    Log.e(MercuryConst.TAG, "checkLoge:Failed to load meta-data open_umeng, NullPointer: " + e.getMessage());
		}

	}





}
