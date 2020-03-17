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
		//find carriers
		MercuryConst.GetChannelID("");
		checkSIM();	
		checkChannel();		
		checkExtSDK();
		checkChannelName();
		checkMobileSplash();
		checkChannelSplash();
		checkLoge();
		JSXML();
		checkNumber();
		
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
		if(iscarriersneed.equals("open"))
		{				
			if(!MercuryActivity.SortChannelID.equals("telecom")&&!MercuryActivity.SortChannelID.equals("unicom"))
				System.loadLibrary("megjb");
		}
		
		
		
	}
	public void APPApplicationInit(Application context)
	{
		Acontext=context;
		MercuryConst.GetChannelID("");
		//find carriers
		checkSIM();	
		checkChannel();		
		checkExtSDK();
		checkChannelName();
		checkMobileSplash();
		checkChannelSplash();
		checkLoge();
		JSXML();
		checkNumber();
		
		try 
		{
			 key=getSign(Acontext);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.e("IAP","[SDKApp]SdkName="+msg);	
		
		if(iscarriersneed!=null)
		{
			if(iscarriersneed.equals("open"))
			{				
				if(!MercuryActivity.SortChannelID.equals("telecom")&&!MercuryActivity.SortChannelID.equals("unicom"))
					System.loadLibrary("megjb");
			}		
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

	public void getSingInfo() {
		
		try {
			PackageInfo packageInfo = getPackageManager().getPackageInfo(
					Acontext.getPackageName(),
					PackageManager.GET_SIGNATURES);
			Signature[] signs = packageInfo.signatures;
			Signature sign = signs[0];
			parseSignature(sign.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void parseSignature(byte[] signature) {
		try {
			CertificateFactory certFactory = CertificateFactory
					.getInstance("X.509");
			X509Certificate cert = (X509Certificate) certFactory
					.generateCertificate(new ByteArrayInputStream(signature));
			String pubKey = cert.getPublicKey().toString();
			String signNumber = cert.getSerialNumber().toString();

		} catch (CertificateException e) {
			e.printStackTrace();
		}
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

	private void checkChannel() {
		try {
		    ApplicationInfo ai = Acontext.getPackageManager().getApplicationInfo(Acontext.getPackageName(), PackageManager.GET_META_DATA);
		    Bundle bundle = ai.metaData;
		    mChannelId = bundle.getInt("EGAME_CHANNEL");
		} catch (NameNotFoundException e) {
		    Log.e(MercuryConst.TAG, "checkChannel:Failed to load meta-data, EGAME_CHANNEL NameNotFound: " + e.getMessage());
		    mChannelId = 0;
		} catch (NullPointerException e) {
		    Log.e(MercuryConst.TAG, "checkChannel:Failed to load meta-data, EGAME_CHANNEL NullPointer: " + e.getMessage());
		    mChannelId = -1;
		}
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
			else if(MercuryActivity.SortChannelID.equals("txyysc"))
			{
				MercuryActivity.LongChannelID="tengxunyingyongshichang";
			}
			else if(MercuryActivity.SortChannelID.equals("txyxzx"))
			{
				MercuryActivity.LongChannelID="tengxunyouxizhongxin";
			}
			else if(MercuryActivity.SortChannelID.equals("dxx"))
			{
				MercuryActivity.LongChannelID="xuancaiweimi";
			}
			else if(MercuryActivity.SortChannelID.equals("bf"))
			{
				MercuryActivity.LongChannelID="baofengyingyin";
			}
			else if(MercuryActivity.SortChannelID.equals("txllq"))
			{
				MercuryActivity.LongChannelID="tengxunliulanqi";
			}
			else if(MercuryActivity.SortChannelID.equals("al"))
			{
				MercuryActivity.LongChannelID="ali";
			}
			else if(MercuryActivity.SortChannelID.equals("unicom"))
			{
				MercuryActivity.LongChannelID="liantong";
			}
			else if(MercuryActivity.SortChannelID.equals("mobile"))
			{
				MercuryActivity.LongChannelID="yidong";
			}
			else if(MercuryActivity.SortChannelID.equals("qqgj"))
			{
				MercuryActivity.LongChannelID="QQguanjia";
			}
			else if(MercuryActivity.SortChannelID.equals("none"))
			{
				MercuryActivity.LongChannelID="kongxiangmu";
			}
			else if(MercuryActivity.SortChannelID.equals("telecom"))
			{
				MercuryActivity.LongChannelID="dianxin";
			}
			else if(MercuryActivity.SortChannelID.equals("debug"))
			{
				MercuryActivity.LongChannelID="ceshi";
			}
			else if(MercuryActivity.SortChannelID.equals("yy"))
			{
				MercuryActivity.LongChannelID="youyi";
			}
			else if(MercuryActivity.SortChannelID.equals("nd"))
			{
				MercuryActivity.LongChannelID="Nduo";
			}
			else if(MercuryActivity.SortChannelID.equals("yyh"))
			{
				MercuryActivity.LongChannelID="yingyonghui";
			}
			else if(MercuryActivity.SortChannelID.equals("yk"))
			{
				MercuryActivity.LongChannelID="youku";
			}
			else if(MercuryActivity.SortChannelID.equals("jf"))
			{
				MercuryActivity.LongChannelID="jifeng";
			}
			else if(MercuryActivity.SortChannelID.equals("sg"))
			{
				MercuryActivity.LongChannelID="sougou";
			}
			else if(MercuryActivity.SortChannelID.equals("txyyb"))
			{
				MercuryActivity.LongChannelID="tengxunyingyongbao";
			}
			else if(MercuryActivity.SortChannelID.equals("kw"))
			{
				MercuryActivity.LongChannelID="kuwo";
			}
			else if(MercuryActivity.SortChannelID.equals("aqy"))
			{
				MercuryActivity.LongChannelID="aiqiyi";
			}
			else if(MercuryActivity.SortChannelID.equals("yw"))
			{
				MercuryActivity.LongChannelID="yiwan";
			}
			else if(MercuryActivity.SortChannelID.equals("taptap"))
			{
				MercuryActivity.LongChannelID="TapTap";
			}
			else if(MercuryActivity.SortChannelID.equals("mzw"))
			{
				MercuryActivity.LongChannelID="muzhiwan";
			}
			else if(MercuryActivity.SortChannelID.equals("dl"))
			{
				MercuryActivity.LongChannelID="dangle";
			}
			else if(MercuryActivity.SortChannelID.equals("meitu"))
			{
				MercuryActivity.LongChannelID="meitu";
			}
			else if(MercuryActivity.SortChannelID.equals("east2west"))
			{
				MercuryActivity.LongChannelID="dongpinxishang";
			}
			else if(MercuryActivity.SortChannelID.equals("hw"))
			{
				MercuryActivity.LongChannelID="huawei";
			}
			else if(MercuryActivity.SortChannelID.equals("lxlsd"))
			{
				MercuryActivity.LongChannelID="lianxiangleshangdian";
			}
			else if(MercuryActivity.SortChannelID.equals("lxyx"))
			{
				MercuryActivity.LongChannelID="lianxiangyouxi";
			}
			else if(MercuryActivity.SortChannelID.equals("chel_4399"))
			{
				MercuryActivity.LongChannelID="4399";
			}
			else if(MercuryActivity.SortChannelID.equals("mz"))
			{
				MercuryActivity.LongChannelID="meizu";
			}
			else if(MercuryActivity.SortChannelID.equals("wdj"))
			{
				MercuryActivity.LongChannelID="wandoujia";
			}
			else if(MercuryActivity.SortChannelID.equals("ls"))
			{
				MercuryActivity.LongChannelID="leshi";
			}
			else if(MercuryActivity.SortChannelID.equals("jinli"))
			{
				MercuryActivity.LongChannelID="jinli";
			}
			else if(MercuryActivity.SortChannelID.equals("vivo"))
			{
				MercuryActivity.LongChannelID="VIVO";
			}
			else if(MercuryActivity.SortChannelID.equals("wxgame"))
			{
				MercuryActivity.LongChannelID="weixinyouxi";
			}
			else if(MercuryActivity.SortChannelID.equals("anzhi"))
			{
				MercuryActivity.LongChannelID="anzhi";
			}
			else if(MercuryActivity.SortChannelID.equals("baidu_dk"))
			{
				MercuryActivity.LongChannelID="baiduduoku";
			}
			else if(MercuryActivity.SortChannelID.equals("xm"))
			{
				MercuryActivity.LongChannelID="xiaomi";
			}
			else if(MercuryActivity.SortChannelID.equals("baidu_sjzs"))
			{
				MercuryActivity.LongChannelID="baidushoujizhushou";
			}
			else if(MercuryActivity.SortChannelID.equals("oppo"))
			{
				MercuryActivity.LongChannelID="OPPO";
			}
			else if(MercuryActivity.SortChannelID.equals("baidu_91"))
			{
				MercuryActivity.LongChannelID="baidu91";
			}
			else if(MercuryActivity.SortChannelID.equals("qihu360"))
			{
				MercuryActivity.LongChannelID="qihu360";
			}
			else if(MercuryActivity.SortChannelID.equals("baidu_tb"))
			{
				MercuryActivity.LongChannelID="baidutieba";
			}
			else if(MercuryActivity.SortChannelID.equals("UC"))
			{
				MercuryActivity.LongChannelID="UCjiuyou";
			}
			else if(MercuryActivity.SortChannelID.equals("e2wwk"))
			{
				MercuryActivity.LongChannelID="e2wwk";
			}
			
		} catch (NameNotFoundException e) {
		    Log.e(MercuryConst.TAG, "checkChannelName:Failed to load meta-data, CHANNEL_NAME NotFound: " + e.getMessage());
		    mChannelId = 0;
		} catch (NullPointerException e) {
		    Log.e(MercuryConst.TAG, "checkChannelName:Failed to load meta-data, CHANNEL_NAME NullPointer: " + e.getMessage());
		    mChannelId = -1;
		}

	}
	private void checkMobileSplash()
	{
		ApplicationInfo appInfo = null;
		try 
		{
			appInfo = Acontext.getPackageManager().getApplicationInfo(Acontext.getPackageName(),PackageManager.GET_META_DATA);
			String channelnametmp = appInfo.metaData.getString("MOBILE_SPLASH");
			iscarriersneed =channelnametmp;
		} catch (NameNotFoundException e) {
		    Log.e(MercuryConst.TAG, "checkMobileSplash:Failed to load meta-data MOBILE_SPLASH, NameNotFound: " + e.getMessage());
		    
		} catch (NullPointerException e) {
		    Log.e(MercuryConst.TAG, "checkMobileSplash:Failed to load meta-data MOBILE_SPLASH, NullPointer: " + e.getMessage());
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
			String isLogOpentmp = appInfo.metaData.getString("E2W_LOG");
			isAntLogOpen =isLogOpentmp;
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
	private void checkNumber()
	{
		ApplicationInfo appInfo = null;
		try 
		{
			appInfo = Acontext.getPackageManager().getApplicationInfo(Acontext.getPackageName(),PackageManager.GET_META_DATA);
			String isLogOpentmp = appInfo.metaData.getString("E2W_NUMBER");
			e2wnumber =isLogOpentmp;
			Log.e(MercuryConst.TAG, "E2W_NUMBER="+e2wnumber);
		} catch (NameNotFoundException e) {
		    Log.e(MercuryConst.TAG, "checkLoge:Failed to load meta-data E2W_NUMBER, NameNotFound: " + e.getMessage());
		    
		} catch (NullPointerException e) {
		    Log.e(MercuryConst.TAG, "checkLoge:Failed to load meta-data E2W_NUMBER, NullPointer: " + e.getMessage());
		}

	}
	
	public static int getSimOperatorId()
	{
		return mSimOperatorId;
	}
	
	public static int getExtSDKId()
	{
		return mExtSDKId;
	}
	
	public static int getChannelId()
	{
		return mChannelId;
	}
	public static String getChannelname()
	{
		return channelname;
	}
	public void JSXML()
	{
		checkExtJSIDChannel();
		checkExtJSCHANNELChannel();
		checkExtJSTJIDChannel();
	}
	//JS
	private void checkExtJSIDChannel() {		
	try {
		try {
			ApplicationInfo appInfo = null;
			try 
			{
				appInfo = this.getPackageManager().getApplicationInfo(getPackageName(),PackageManager.GET_META_DATA);
			} catch (NameNotFoundException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jsid = appInfo.metaData.getString("JS_ID");
	} 
	catch (NullPointerException e) 
	{
	    Log.e("game", "3Failed to load meta-data, NameNotFound: " + e.getMessage());
	   	    
	} 
	}
		catch (NullPointerException e) 
	{
		Log.e("game",  "4Failed to load meta-data, NullPointer: " + e.getMessage());         
	}
	
}

	private void checkExtJSCHANNELChannel() {		
	try {
		try {
			ApplicationInfo appInfo = null;
			try 
			{
				appInfo = this.getPackageManager().getApplicationInfo(getPackageName(),PackageManager.GET_META_DATA);
			} catch (NameNotFoundException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jschannel = appInfo.metaData.getString("JS_CHANNEL");
	} 
		catch (NullPointerException e) 
		{
		    Log.e("game", "3Failed to load meta-data, NameNotFound: " + e.getMessage());
		   	    
		} 
		}
			catch (NullPointerException e) 
		{
			Log.e("game",  "4Failed to load meta-data, NullPointer: " + e.getMessage());         
		}
	
}
private void checkExtJSTJIDChannel() {		
	try {
		try {
			ApplicationInfo appInfo = null;
			try 
			{
				appInfo = this.getPackageManager().getApplicationInfo(getPackageName(),PackageManager.GET_META_DATA);
			} catch (NameNotFoundException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jstjid = appInfo.metaData.getString("JS_TJID");
	} 
		catch (NullPointerException e) 
		{
		    Log.e("game", "3Failed to load meta-data, NameNotFound: " + e.getMessage());
		   	    
		} 
		}
			catch (NullPointerException e) 
		{
			Log.e("game",  "4Failed to load meta-data, NullPointer: " + e.getMessage());         
		}
	
}


}
