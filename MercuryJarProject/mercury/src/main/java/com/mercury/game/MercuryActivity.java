package com.mercury.game;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import com.mercury.game.inApp.APPBaseInterface;
import com.mercury.game.inApp.InAppBase;
import com.mercury.game.inApp.InAppDefault;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Intent;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

public class MercuryActivity  {

	public static Context mContext = null;
	private InAppBase mInApp;
	public InAppBase mInAppShow;
	private InAppBase mInAppExt;
	private InAppBase mInShare;

	public static int mSimOperatorId;
	private int mChannelId;
	private int mExtSDKId = -1;
	public static String ChannelForServer;
	//private String msg_string;
	//public static int msg;
	public static String nikeString;	
	public int platform;
	public static String packagenameforuse;
	public static String isLogOpen="";
	public static MercuryActivity activityforappbase=null;
	public static int Platform=-1;
	public static String DeviceId="";
	public static String SavePidName="";
	public static String SortChannelID="";
	public static String LongChannelID="";
	private static ImageView img = null;
	public void InitSDK(Context ContextForE2wSDK,final APPBaseInterface appcall)
	{
		mContext = ContextForE2wSDK;	
		ChannelSplash();
		MercuryConst.GetChannelID("");
		mInApp = new InAppDefault() ;
		mInAppExt= new InAppDefault() ;
		activityforappbase=this;
		InitChannel(appcall);
		InitAd(appcall);
	}
	public void ChannelSplash()
	{
		MercuryActivity.LogLocal("[inapp] ChannelSplash.png");
		try {
			final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT,
					RelativeLayout.LayoutParams.MATCH_PARENT);
			String name = "ChannelSplash.png";
			InputStream in = mContext.getResources().getAssets().open(name);
			if (in != null) {
				final Bitmap bitmap = BitmapFactory.decodeStream(in);
				// activity.getWindow().getDecorView().getHandler().postDelayed(
				((Activity) mContext).runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						final ImageView image = new ImageView(mContext);
						image.setImageBitmap(bitmap);
						((Activity) mContext).addContentView(image, params);
						image.setScaleType(ScaleType.FIT_XY);
						new Thread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								try {
									Thread.sleep(3000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								((Activity) mContext).runOnUiThread(new Runnable() {
									@Override
									public void run() {
										ViewGroup vg = (ViewGroup) image
												.getParent();
										vg.removeView(image);
									}
								});
							}

						}).start();
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
			MercuryActivity.LogLocal("[inapp] init e="+e.toString());
		}
	}
 	public String SavePidName(){    
 	    String id="";   
 	    //获取当前时间戳         
 	    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");    
 	    String temp = sf.format(new Date());    
 	    //获取6位随机数  
 	    int random=(int) ((Math.random()+1)*100000);    
 	    id=temp+random;    
 	    return id;    
 	} 
	
	
	public void InitChannel(final APPBaseInterface appcall)
	{
		final Context applicationContext = mContext.getApplicationContext();		
		LogLocal("[MercuryActivity] Local InitChannel()->"+mInApp);
		mInApp.init(applicationContext, (Activity)mContext, MercuryConst.APPID, MercuryConst.APPKEY,appcall);
	
	}
	public void InitAd(final APPBaseInterface appcall)
	{
		final Context applicationContext = mContext.getApplicationContext();
		LogLocal("[MercuryActivity] Local InitChannel()->"+mInApp);
		mInApp.init(applicationContext, (Activity)mContext, MercuryConst.APPID, MercuryConst.APPKEY,appcall);

	}
	public void InitCarriers(final APPBaseInterface appcall)
	{
		
	}
	
	public void purchaseProduct(String pidname) 
	{
		LogLocal("[MercuryActivity] purchaseProduct: " + pidname);
		MercuryConst.PayInfo(pidname);
		mInApp.purchase(MercuryConst.QinPid, MercuryConst.Qindesc, MercuryConst.Qinpricefloat);
	}
	public void ExitGame()
	{    		
		new Handler(mContext.getMainLooper()).post(new Runnable() {
			@Override
			public void run() 
			{
				
				mInApp.ExitGame();	
			}
			});
	}
	
	public String ShortChannelID()
	{
		return SortChannelID;
	}
	public String LongChannelID()
	{
		return LongChannelID;
	}



	public void show_banner()
	{		
		if(mInAppShow != null)
		{
			mInAppShow.show_banner();
		}
	}
	public void swtichuser()
	{		
		if(mInAppShow != null)
		{
			mInAppShow.swtichuser();
		}
	}
	public void show_insert()
	{
		if(mInAppShow != null)
		{
			mInAppShow.show_insert();
		}
	}
	public void show_push()
	{
		if(mInAppShow != null)
		{
			mInAppShow.show_push();
		}
	}
	public void show_out()
	{
		if(mInAppShow != null)
		{
			mInAppShow.show_out();
		}
	}
	public void show_video()
	{
		if(mInAppShow != null)
		{
			mInAppShow.show_video();
		}
	}
	public void uploadclick() {
		// TODO Auto-generated method stub
		if(mInAppShow != null)
		{
			mInAppShow.uploadclick();
		}
	}

	public void onPause() {

		if(mInApp != null)
		{
			mInApp.onPause();
		}
		
		if(mInAppExt != null)
		{
			mInAppExt.onPause();
		}
	}
	public void onStop() {

		if(mInApp != null)
		{
			mInApp.onStop();
		}
		
		if(mInAppExt != null)
		{
			mInAppExt.onStop();
		}
	}
	public void onStart() {

		if(mInApp != null)
		{
			mInApp.onStart();
		}
		
		if(mInAppExt != null)
		{
			mInAppExt.onStart();
		}
	}
	public void onRestart()
	{
		if(mInApp != null)
		{
			mInApp.onRestart();
		}
		
		if(mInAppExt != null)
		{
			mInAppExt.onRestart();
		}
	}
	public void onResume() {


		if(mInApp != null)
		{
			mInApp.onResume();
		}
		
		if(mInAppExt != null)
		{
			mInAppExt.onResume();
		}
	}


	public void onDestroy() {
		if(mInApp != null)
		{
			mInApp.onDestroy();
		}
		
		if(mInAppExt != null)
		{
			mInAppExt.onDestroy();
		}
		
	}

	
	
	public static Object getInstance() {	
		Log.e("IAP","Unity Game");
		Platform=MercuryConst.Unity;
		return mContext;
	}


	public int getChannelId() {
		return mChannelId;
	}
	
	public InAppBase getBaseInApp()
	{
		LogLocal("[MercuryActivity] getBaseInApp()->mInApp="+mInApp);
		InAppBase myapp = new InAppDefault();
		return myapp;
	}


	
	public void Exchange()
	{
		new Handler(mContext.getMainLooper()).post(new Runnable() {
    		@Override
			public void run() {
    			

			}
		});
	}
	public void Exchange(final APPBaseInterface appcall)
	{
		//Log.d(Const.TAG, "showToast: " + strMessage + ", " + iDuration);
		new Handler(mContext.getMainLooper()).post(new Runnable() {
    		@Override
			public void run() {
    			InAppBase mInAppFunction= new InAppBase();
    			//mInAppFunction.Exchange(appcall);
			}
		});
	}
	private void GetmInAppExt()
	{
		LogLocal("[MercuryActivity] GetmInAppExt()->"+mExtSDKId);
		switch(mExtSDKId)
		{
			
		}
	}

	public void Message(final String news)
	{	
		Toast.makeText(mContext, news,Toast.LENGTH_SHORT).show();
	}
	public void SharePicture(String imagePath,boolean istimeline,final APPBaseInterface appcall)
	{
		
		
	}

	
	public void letUserLogin() 
	{
		MercuryActivity.LogLocal("[MercuryActivity]->letUserLogin:mInAppExt="+mInAppExt);
		if(mInAppExt != null)
		{			
			mInAppExt.letUserLogin();
		}
	}
	public void stopWaiting() {
		// TODO Auto-generated method stub
		MercuryActivity.LogLocal("[MercuryActivity]->stopWaiting:mInAppExt="+mInAppExt);
		if(mInAppExt != null)
		{			
			mInAppExt.stopWaiting();
		}
	}
	public void letUserLogout() {
		// TODO Auto-generated method stub
		MercuryActivity.LogLocal("[MercuryActivity]->letUserLogout:mInAppExt="+mInAppExt);
		if(mInAppExt != null)
		{			
			mInAppExt.letUserLogout();
		}
	}
	public void showDiffLogin() 
	{
		MercuryActivity.LogLocal("[MercuryActivity]->showDiffLogin:mInAppExt="+mInAppExt);
		if(mInAppExt != null)
		{
			mInAppExt.showDiffLogin();
		}
	}

	public void TencentLogin(int kind)
	{
		MercuryActivity.LogLocal("[MercuryActivity]->TencentLogin:mInAppExt="+mInAppExt+" kind="+kind);
		if(mInAppExt != null)
		{
			mInAppExt.login(kind);
		}
	}
	public void TencentLoginOut()
	{
		MercuryActivity.LogLocal("[MercuryActivity]->TencentLoginOut:mInAppExt="+mInAppExt);
		if(mInAppExt != null)
		{
			mInAppExt.logout();
		}
	}
	public void TencentLoginOutOnly()
	{
		MercuryActivity.LogLocal("[MercuryActivity]->TencentLoginOutOnly:mInAppExt="+mInAppExt);
		if(mInAppExt != null)
		{
			mInAppExt.TencentLoginOutOnly();
		}
	}
	public void ShowTencentAd()
	{
		MercuryActivity.LogLocal("[MercuryActivity]->ShowTencentAd:mInAppExt="+mInAppExt);
		if(mInAppExt != null)
		{
			mInAppExt.ShowTencentAd();
		}
	}
	public void repairindentRequest()
	{
		
	}
	public void respondCPserver()
	{
		
	}
	public void showMessageDialog()
	{
		MercuryActivity.LogLocal("[MercuryActivity]->showMessageDialog:mInAppExt="+mInAppExt);
		if(mInAppExt != null)
		{
			mInAppExt.showMessageDialog();
		}
	}
	public void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		MercuryActivity.LogLocal("[MercuryActivity]->onActivityResult:mInAppExt="+mInAppExt);
		if(mInAppExt != null)
		{
			mInAppExt.onActivityResult(requestCode,resultCode,data);
		}
	}
	public void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		MercuryActivity.LogLocal("[MercuryActivity]->onNewIntent:mInAppExt="+mInAppExt);
		if(mInAppExt != null)
		{
			mInAppExt.onNewIntent(intent);
		}
	}
	public static void LogLocal(final String news)
	{		
		Log.e(MercuryConst.TAG,news);
	}

}
