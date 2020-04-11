package com.mercury.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mercury.game.InAppAdvertisement.InAppAD;
import com.mercury.game.InAppChannel.InAppChannel;
import com.mercury.game.util.APPBaseInterface;
import com.mercury.game.util.InAppBase;
import com.mercury.game.util.MercuryConst;


import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.mercury.game.MercuryApplication.OpenUmeng;

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
		mInAppChannel = new InAppChannel() ;
		mInAppAD= new InAppAD() ;
		activityforappbase=this;
		InitChannel(appcall);
		InitAd(appcall);
	}
	public void ChannelSplash()
	{
		LogLocal("[MercuryActivity][ChannelSplash] ChannelSplash.png");
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
			LogLocal("[MercuryActivity][ChannelSplash] init e="+e.toString());
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
		LogLocal("[MercuryActivity][InitChannel] Local InitChannel()->"+mInAppChannel);
		mInAppChannel.ActivityInit((Activity)mContext, appcall);
	
	}
	public void InitAd(final APPBaseInterface appcall)
	{
		final Context applicationContext = mContext.getApplicationContext();
		LogLocal("[MercuryActivity][InitAd] Local InitAd()->"+mInAppAD);
		mInAppAD.ActivityInit((Activity)mContext,appcall);

	}
	
	public void Purchase(String pidname)
	{
		LogLocal("[MercuryActivity][purchaseProduct] " + pidname);
		mInAppChannel.Purchase(pidname);
//		MercuryConst.PayInfo(pidname);
//		mInAppChannel.purchase(MercuryConst.QinPid, MercuryConst.Qindesc, MercuryConst.Qinpricefloat);
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

	public void swtichuser()
	{
		LogLocal("[MercuryActivity][swtichuser]");
		mInAppChannel.swtichuser();
	}
	public void uploadclick() {
		// TODO Auto-generated method stub
		if(mInAppAD != null)
		{
			mInAppAD.uploadclick();
		}
	}
	public void show_banner()
	{
		LogLocal("[MercuryActivity][show_banner]" + mInAppAD);
		if(mInAppAD != null) { mInAppAD.show_banner(); }
	}

	public void show_insert()
	{
		LogLocal("[MercuryActivity][show_insert]" + mInAppAD);
		if(mInAppAD != null) { mInAppAD.show_insert(); }
	}
	public void show_push()
	{
		LogLocal("[MercuryActivity][show_push]" + mInAppAD);
		if(mInAppAD != null) { mInAppAD.show_push(); }
	}
	public void show_out()
	{
		LogLocal("[MercuryActivity][show_out]" + mInAppAD);
		if(mInAppAD != null) { mInAppAD.show_out(); }
	}
	public void show_video()
	{
		LogLocal("[MercuryActivity][show_video]" + mInAppAD);
		if(mInAppAD != null) { mInAppAD.show_video(); }
	}


	public void onPause() {

		if(mInAppChannel != null) { LogLocal("[MercuryActivity] mInAppChannel onPause()->" + mInAppChannel);mInAppChannel.onPause();}
		if(mInAppAD != null) { LogLocal("[MercuryActivity] mInAppAD onPause()->" + mInAppAD);mInAppAD.onPause();}
	}
	public void onStop() {

		if(mInAppChannel != null) { LogLocal("[MercuryActivity] mInAppChannel onStop()->" + mInAppChannel);mInAppChannel.onStop();}
		if(mInAppAD != null) { LogLocal("[MercuryActivity] mInAppAD onStop()->" + mInAppAD);mInAppAD.onStop();}
	}
	public void onStart() {

		if(mInAppChannel != null) { LogLocal("[MercuryActivity] mInAppChannel onStart()->" + mInAppChannel);mInAppChannel.onStart();}
		if(mInAppAD != null) { LogLocal("[MercuryActivity] mInAppAD onStart()->" + mInAppAD);mInAppAD.onStart();}

	}
	public void onRestart()
	{
		if(mInAppChannel != null) { LogLocal("[MercuryActivity] mInAppChannel onRestart()->" + mInAppChannel);mInAppChannel.onRestart();}
		if(mInAppAD != null) { LogLocal("[MercuryActivity] mInAppAD onRestart()->" + mInAppAD);mInAppAD.onRestart();}
	}
	public void onResume()
	{
		if(mInAppChannel != null) { LogLocal("[MercuryActivity] mInAppChannel onResume()->" + mInAppChannel);mInAppChannel.onResume();}
		if(mInAppAD != null) { LogLocal("[MercuryActivity] mInAppAD onResume()->" + mInAppAD);mInAppAD.onResume();}
	}

	public void onDestroy()
	{
		if(mInAppChannel != null) { LogLocal("[MercuryActivity] mInAppChannel onDestroy()->" + mInAppChannel);mInAppChannel.onDestroy();}
		if(mInAppAD != null) { LogLocal("[MercuryActivity] mInAppAD onDestroy()->" + mInAppAD);mInAppAD.onDestroy();}
	}
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(mInAppChannel != null) { LogLocal("[MercuryActivity]->onActivityResult:mInAppChannel="+mInAppChannel);mInAppChannel.onActivityResult(requestCode,resultCode,data); }
		if(mInAppAD != null) { LogLocal("[MercuryActivity]->onActivityResult:mInAppChannel="+mInAppAD);mInAppAD.onActivityResult(requestCode,resultCode,data); }
	}
	public void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		if(mInAppChannel != null) { LogLocal("[MercuryActivity]->onNewIntent:mInAppChannel="+mInAppChannel);mInAppChannel.onNewIntent(intent); }
		if(mInAppAD != null) { LogLocal("[MercuryActivity]->onNewIntent:mInAppAD="+mInAppAD);mInAppAD.onNewIntent(intent); }
	}
	
	
	public static Object getInstance() {
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
				Log.e("IAP","[E2WApp]->Exchange:Android");
				//Login();
				InAppBase mInAppFunction= new InAppBase();
				mInAppFunction.Exchange();

			}
		});
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

	public void Message(final String news)
	{
		Toast.makeText(mContext, news,Toast.LENGTH_SHORT).show();
	}
	public static void LogLocal(final String news)
	{
		Log.w("MercurySDK",news);
	}

}
