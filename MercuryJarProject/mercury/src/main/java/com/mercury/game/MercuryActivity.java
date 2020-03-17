package com.mercury.game;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import com.mercury.game.InAppChannel.APPBaseInterface;
import com.mercury.game.InAppChannel.InAppBase;
import com.mercury.game.InAppChannel.InAppChannel;
import com.mercury.game.InAppAdvertisement.InAppAD;
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
	private InAppChannel mInAppChannel;
	public InAppAD mInAppAD;


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
	public void InitSDK(Context ContextFromUsers,final APPBaseInterface appcall)
	{
		mContext = ContextFromUsers;
		ChannelSplash();
		MercuryConst.GetChannelID("");
		mInAppChannel = new InAppChannel() ;
		mInAppAD= new InAppAD() ;
		activityforappbase=this;
		InitChannel(appcall);
		InitAd(appcall);
	}
	public void ChannelSplash()
	{
		LogLocal("[inapp] ChannelSplash.png");
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
			LogLocal("[inapp] init e="+e.toString());
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
		LogLocal("[MercuryActivity] Local InitChannel()->"+mInAppChannel);
		mInAppChannel.init(applicationContext, (Activity)mContext, MercuryConst.APPID, MercuryConst.APPKEY,appcall);
	
	}
	public void InitAd(final APPBaseInterface appcall)
	{
		final Context applicationContext = mContext.getApplicationContext();
		LogLocal("[MercuryActivity] Local InitChannel()->"+mInAppAD);
		mInAppAD.init(applicationContext, (Activity)mContext, MercuryConst.APPID, MercuryConst.APPKEY,appcall);

	}
	public void InitCarriers(final APPBaseInterface appcall)
	{
		
	}
	
	public void purchaseProduct(String pidname) 
	{
		LogLocal("[MercuryActivity] purchaseProduct: " + pidname);
		MercuryConst.PayInfo(pidname);
		mInAppChannel.purchase(MercuryConst.QinPid, MercuryConst.Qindesc, MercuryConst.Qinpricefloat);
	}
	public void ExitGame()
	{    		
		new Handler(mContext.getMainLooper()).post(new Runnable() {
			@Override
			public void run() 
			{

				mInAppChannel.ExitGame();
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
		if(mInAppAD != null)
		{
			mInAppAD.show_banner();
		}
	}
	public void swtichuser()
	{		
		if(mInAppAD != null)
		{
			mInAppAD.swtichuser();
		}
	}
	public void show_insert()
	{
		if(mInAppAD != null)
		{
			mInAppAD.show_insert();
		}
	}
	public void show_push()
	{
		if(mInAppAD != null)
		{
			mInAppAD.show_push();
		}
	}
	public void show_out()
	{
		if(mInAppAD != null)
		{
			mInAppAD.show_out();
		}
	}
	public void show_video()
	{
		if(mInAppAD != null)
		{
			mInAppAD.show_video();
		}
	}
	public void uploadclick() {
		// TODO Auto-generated method stub
		if(mInAppAD != null)
		{
			mInAppAD.uploadclick();
		}
	}

	public void onPause() {

		if(mInAppChannel != null)
		{
			mInAppChannel.onPause();
		}
	}
	public void onStop() {

		if(mInAppChannel != null)
		{
			mInAppChannel.onStop();
		}
	}
	public void onStart() {

		if(mInAppChannel != null)
		{
			mInAppChannel.onStart();
		}
	}
	public void onRestart()
	{
		if(mInAppChannel != null)
		{
			mInAppChannel.onRestart();
		}
	}
	public void onResume()
	{
		if(mInAppChannel != null)
		{
			mInAppChannel.onResume();
		}
	}


	public void onDestroy()
	{
		if(mInAppChannel != null)
		{
			mInAppChannel.onDestroy();
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
	
	public InAppBase getmInAppChannel()
	{
		LogLocal("[MercuryActivity] getBaseInApp()->mInApp="+mInAppChannel);
		return mInAppChannel;
	}

	public InAppBase getmInAppAD()
	{
		LogLocal("[MercuryActivity] getBaseInApp()->mInApp="+mInAppAD);
		return mInAppAD;
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

	public void Message(final String news)
	{	
		Toast.makeText(mContext, news,Toast.LENGTH_SHORT).show();
	}
	public void SharePicture(String imagePath,boolean istimeline,final APPBaseInterface appcall)
	{
		
		
	}

	
	public void letUserLogin() 
	{
		LogLocal("[MercuryActivity]->letUserLogin:mInAppChannel="+mInAppChannel);
		if(mInAppChannel != null)
		{
			mInAppChannel.letUserLogin();
		}
	}
	public void stopWaiting()
	{
		LogLocal("[MercuryActivity]->stopWaiting:mInAppChannel="+mInAppChannel);
		if(mInAppChannel != null)
		{
			mInAppChannel.stopWaiting();
		}
	}
	public void letUserLogout()
	{
		LogLocal("[MercuryActivity]->letUserLogout:mInAppChannel="+mInAppChannel);
		if(mInAppChannel != null)
		{
			mInAppChannel.letUserLogout();
		}
	}
	public void showDiffLogin() 
	{
		LogLocal("[MercuryActivity]->showDiffLogin:mInAppChannel="+mInAppChannel);
		if(mInAppChannel != null)
		{
			mInAppChannel.showDiffLogin();
		}
	}

	public void TencentLogin(int kind)
	{
		LogLocal("[MercuryActivity]->TencentLogin:mInAppChannel="+mInAppChannel+" kind="+kind);
		if(mInAppChannel != null)
		{
			mInAppChannel.login(kind);
		}
	}
	public void TencentLoginOut()
	{
		LogLocal("[MercuryActivity]->TencentLoginOut:mInAppChannel="+mInAppChannel);
		if(mInAppChannel != null)
		{
			mInAppChannel.logout();
		}
	}
	public void TencentLoginOutOnly()
	{
		LogLocal("[MercuryActivity]->TencentLoginOutOnly:mInAppChannel="+mInAppChannel);
		if(mInAppChannel != null)
		{
			mInAppChannel.TencentLoginOutOnly();
		}
	}
	public void ShowTencentAd()
	{
		LogLocal("[MercuryActivity]->ShowTencentAd:mInAppChannel="+mInAppChannel);
		if(mInAppChannel != null)
		{
			mInAppChannel.ShowTencentAd();
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
		LogLocal("[MercuryActivity]->showMessageDialog:mInAppChannel="+mInAppChannel);
		if(mInAppChannel != null)
		{
			mInAppChannel.showMessageDialog();
		}
	}
	public void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		LogLocal("[MercuryActivity]->onActivityResult:mInAppChannel="+mInAppChannel);
		if(mInAppChannel != null)
		{
			mInAppChannel.onActivityResult(requestCode,resultCode,data);
		}
	}
	public void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		LogLocal("[MercuryActivity]->onNewIntent:mInAppChannel="+mInAppChannel);
		if(mInAppChannel != null)
		{
			mInAppChannel.onNewIntent(intent);
		}
	}
	public static void LogLocal(final String news)
	{
		Log.w("MercurySDK",news);
	}

}
