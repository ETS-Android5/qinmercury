package com.mercury.game;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.mercury.game.InAppAdvertisement.InAppAD;
import com.mercury.game.InAppChannel.InAppChannel;
import com.mercury.game.util.InAppBase;
import com.mercury.game.util.MercuryConst;



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
	private InAppBase mInAppExt;
	private InAppBase mInAppChannel;
	public static String umeng_id="";
	public static String talkingdata_id="";
	@Override
	public void onCreate() {
		super.onCreate();
		AppInitCode(this);
	}
	public void APPApplicationInit(Application context)
	{
		AppInitCode(context);
	}
	public void AppInitCode(Application context)
	{
		Acontext = context;
		checkSIM();
		checkExtSDK();
		mInAppExt = new InAppAD();
		mInAppExt.ApplicationInit(Acontext);
		mInAppChannel = new InAppChannel();
		mInAppChannel.ApplicationInit(Acontext);
		OpenUmeng();
		OpenTalkingData();
		channelname = checkChannelName();
		checkChannelName();
		checkChannelSplash();
		Log.w("MercurySDK","[SDKApp]SdkName="+channelname);
		Log.w("MercurySDK","[SDKApp]InAppAD");
		try
		{
			key=getSign(context);
			SignatureVerify(key);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void SignatureVerify(String key)
	{
		if(umeng_id.equals("")&&talkingdata_id.equals(""))
		{
			return;
		}
		Log.w("MercurySDK","key="+key);
		if(key.equals("308203843082026ca003020102020900c821f1dfa3988202300d06092a864886f70d01010b0500306f310e300c060355040613054368696e613111300f0603550408130873696e676d61616e3111300f0603550407130873696e676d61616e3111300f060355040a130873696e676d61616e3111300f060355040b130873696e676d61616e3111300f0603550403130873696e676d61616e3020170d3230303531383038303533305a180f32333438313230353038303533305a306f310e300c060355040613054368696e613111300f0603550408130873696e676d61616e3111300f0603550407130873696e676d61616e3111300f060355040a130873696e676d61616e3111300f060355040b130873696e676d61616e3111300f0603550403130873696e676d61616e30820122300d06092a864886f70d01010105000382010f003082010a028201010097dd37357e6c47bf025cca86c51716025f638b65853eca2d861c278ab404d9b9cf0a278738c8c185cc3888279e5e035a16e99bcda9aef4fc1a05648dc5f731dd572642858744833f5866c0ab18a63089093437b0e861b26ce0957a2a64bcbf595b10f32b360ade7b41207d7957efbe32babc740ae08abcd4fa87af15081f8200d215823c646e053db3cf0d9b3b578c9936f1be0acd935cc502cb51a74f1f2551d6c785c45cf1197de5a2847681e8c2df0fd57e410bb33b1898ef4ed92d91007bc7f5308d3575a7fab266002d07b24c8c634d9abedc9c4fefb412da04cf2b99a462547bf6649e6c375e3aa70994fe4a7cf030c562a20396f9325ef05a67361c970203010001a321301f301d0603551d0e041604149f8ec64c66ef9b7227adfe84c8c0c69650bc5c22300d06092a864886f70d01010b050003820101006fda19d241bbc7c7bf7c0b90703deabbd54fe3764db6391a7a5654e7c775476fdc744a8f0ac9a3037c1e4a66a7171c2d79b0b9b4572d3068585aae04393b431f7ed98976011d33c90ca12b594ec6bcf2f5ee3bfb098754bcaf57443cf2ccfee88b68e74b7b245a99c6e09ea9b88b80b672bf11d4e31acdee153d6850cb535107dab86401c463ae777b8c070adb6d3ab54aab6f440a003b93f92910873dd8ed67d1ca7cd6ff81bff39c8ca8cd5246cb46699080f2e85babb1c090e194e8a1b47936b321304d6fadf3fa7756acbcec9c7e5bf313dfbced3acb995fc549163a55c880d3600adfd1c93b79b968ff60b3d414d3fbfa29d659712221e384f84cdd0a8f"))
		{
			Log.w("MercurySDK","[SignatureVerify]Success");
		}
		else {
			Log.w("MercurySDK", "[SignatureVerify]Failed");

			new Thread(new Runnable() {
				public void run() {
					int count = 0;
					while (true) {
						try {
							if (count == 10) {
								System.exit(0);
								android.os.Process.killProcess(android.os.Process.myPid());
							}
							Handler handler = new Handler(Looper.getMainLooper());
							final int finalCount = count;
							handler.post(
									new Runnable() {
										@Override
										public void run() {
											Toast.makeText(Acontext, "游戏被恶意修改，游戏即将推出。", Toast.LENGTH_SHORT).show();
										}
									}
							);

							Thread.sleep(1000L);
							count++;

						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
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


	private String checkChannelName()
	{
		ApplicationInfo appInfo = null;
		try
		{
			appInfo = Acontext.getPackageManager().getApplicationInfo(Acontext.getPackageName(),PackageManager.GET_META_DATA);
			String channelnametmp = appInfo.metaData.getString("channel_name");
			MercuryActivity.SortChannelID=channelname =channelnametmp;
			return channelnametmp;
		} catch (NameNotFoundException e) {
//		    Log.e(MercuryConst.TAG, "checkChannelName:Failed to load meta-data, CHANNEL_NAME NotFound: " + e.getMessage());
			mChannelId = 0;
			return "";
		} catch (NullPointerException e) {
//		    Log.e(MercuryConst.TAG, "checkChannelName:Failed to load meta-data, CHANNEL_NAME NullPointer: " + e.getMessage());
			mChannelId = -1;
			return "";
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

	private String OpenUmeng()
	{
		ApplicationInfo appInfo = null;
		try
		{
			appInfo = Acontext.getPackageManager().getApplicationInfo(Acontext.getPackageName(),PackageManager.GET_META_DATA);
			umeng_id = appInfo.metaData.getString("open_umeng");
			if(umeng_id.equals("")==false)
			{
				Log.e("MercurySDK","umeng opened");
			}
			return umeng_id;
		} catch (NameNotFoundException e) {
			return "";
		} catch (NullPointerException e) {
			return "";
		}

	}
	private String OpenTalkingData()
	{
		ApplicationInfo appInfo = null;
		try
		{
			appInfo = Acontext.getPackageManager().getApplicationInfo(Acontext.getPackageName(),PackageManager.GET_META_DATA);
			talkingdata_id = appInfo.metaData.getString("open_talkingdata");
			if(talkingdata_id.equals("")==false)
			{
				Log.e("MercurySDK","open_talkingdata opened");
			}
			return talkingdata_id;
		} catch (NameNotFoundException e) {
			return "";
		} catch (NullPointerException e) {
			return "";
		}
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		mInAppChannel.onTerminate();
		mInAppExt.onTerminate();
	}
}
