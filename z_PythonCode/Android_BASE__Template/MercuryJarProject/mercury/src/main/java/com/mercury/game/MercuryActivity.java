package com.mercury.game;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
//shrinkpartstart
import android.support.v4.app.ActivityCompat;
//shrinkpartend
import android.widget.VideoView;

import com.mercury.game.InAppAdvertisement.InAppAD;
import com.mercury.game.InAppChannel.InAppChannel;
import com.mercury.game.util.APPBaseInterface;
import com.mercury.game.util.Function;
import com.mercury.game.util.InAppBase;
import com.mercury.game.util.MD5;
import com.mercury.game.util.MercuryConst;
import com.mercury.game.util.PermissionConstants;
import com.mercury.game.util.PermissionUtils;
import com.mercury.game.util.PhoneUtils;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.mercury.game.MercuryApplication.OpenUmeng;
import static com.mercury.game.util.Function.readFileData;
import static com.mercury.game.util.Function.redeemCode;
import static com.mercury.game.util.Function.verifyGame;
import static com.mercury.game.util.Function.writeFileData;
import static com.mercury.game.util.MercuryConst.GetProductionList;

public class MercuryActivity  {

	public static Context mContext = null;
	public static Activity mActivity = null;
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
	public static String DeviceId="123";
	public static String SavePidName="";
	public static String SortChannelID="";
	public static String LongChannelID="";
	private static ImageView img = null;
	public static String GameName="ww1";
	public static String deviceId = "";
	public  static APPBaseInterface mappcall= null;
	public void InitSDK(Context ContextFromUsers,final APPBaseInterface appcall)
	{
		LogLocal("[MercuryActivity][InitSDK]123");
		mappcall = appcall;
		mContext =  ContextFromUsers;
		mActivity = (Activity)ContextFromUsers;
		activityforappbase=this;
		LogLocal("[SDKApp]UserDeviceID()="+UserDeviceID());
		ChannelSplash();
		PlayVideo();
		GetProductionInfo();
//		verifyGame(GameName);
		SingmaanLogin();


	}
	public String GetProductionInfo()
	{
		LogLocal("[MercuryActivity][GetProductionInfo] GetProductionList="+GetProductionList());
		return GetProductionList();
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
												if (vg!=null)
												{vg.removeView(image);}
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

	public void PlayVideo()
	{
		LogLocal("[MercuryActivity][PlayVideo] ChannelSplash.mp4");
		RelativeLayout.LayoutParams relayout = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		relayout.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		relayout.addRule(RelativeLayout.CENTER_HORIZONTAL);

		final VideoView videoView = new VideoView(mContext);
		FrameLayout.LayoutParams videoViewp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
		videoViewp.gravity = Gravity.CENTER;
		((Activity)mContext).addContentView(videoView,videoViewp);

		String path = "file:///android_asset/ChannelSplash.mp4";
		videoView.setVideoPath(getAssetsCacheFile(mContext, "ChannelSplash.mp4"));
		LogLocal("[MercuryActivity][PlayVideo] start");

		videoView.start();
		LogLocal("[MercuryActivity][PlayVideo] started");
		videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
			@Override
			public void onCompletion(MediaPlayer mp) {
				LogLocal("[MercuryActivity][PlayVideo] end");
				((ViewGroup) videoView.getParent()).removeView(videoView);
				LogLocal("[MercuryActivity][PlayVideo] UserDeviceID");
				InitChannel(mappcall);
				InitAd(mappcall);
				UserDeviceID();
			}
		});
	}

	public String getAssetsCacheFile(Context context, String fileName)   {
		File cacheFile = new File(context.getCacheDir(), fileName);
		try {
			InputStream inputStream = context.getAssets().open(fileName);
			try {
				FileOutputStream outputStream = new FileOutputStream(cacheFile);
				try {
					byte[] buf = new byte[1024];
					int len;
					while ((len = inputStream.read(buf)) > 0) {
						outputStream.write(buf, 0, len);
					}
				} finally {
					outputStream.close();
				}
			} finally {
				inputStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cacheFile.getAbsolutePath();
	}
 	public String GetUniqueID(){
 	    String id="";   
 	    //获取当前时间戳         
 	    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");    
 	    String temp = sf.format(new Date());    
 	    //获取6位随机数  
 	    int random=(int) ((Math.random()+1)*100000);    
 	    id=temp+random;    
 	    return id;    
 	}

	public String UserDeviceID()
	{
		LogLocal("[MercuryActivity][UserDeviceID] get in");
		PermissionUtils.permission(PermissionConstants.PHONE).callback(new PermissionUtils.FullCallback() {
			@Override
			public void onGranted(List<String> permissionsGranted) {
				//用户同意权限
				writeFileData("UserIMEI","");
				DeviceId = PhoneUtils.getUnicodeId(mContext);
				getDeviceId(mContext);
				LogLocal("[MercuryActivity][UserDeviceID] permission");
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						//调用登录接口
						Toast.makeText(mContext, "您已通过权限，账户已与您的手机绑定，请放心游戏", Toast.LENGTH_LONG).show();
					}
				},3000);
			}
			@Override
			public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
				//用户拒绝权限
				DeviceId = Settings.System.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
				LogLocal("[MercuryActivity][UserDeviceID] User denied");
				getDeviceId(mContext);
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						//调用登录接口
						Toast.makeText(mContext, "您已拒绝权限，删除应用会导致游戏帐号无法找回，建议重装游戏重现赋予权限", Toast.LENGTH_LONG).show();
					}
				},3000);
			}
		}).rationale(new PermissionUtils.OnRationaleListener() {
			@Override
			public void rationale(ShouldRequest shouldRequest) {
				shouldRequest.again(true);
			}
		}).request();
		return DeviceId;
	}
	public String getDeviceId(Context context) {
		LogLocal("[getDeviceId] sssssss");
		String strUserID = "";
		String imei = "";
		imei = DeviceId;
		LogLocal("[getDeviceId] -imei = ["+imei+"]");
		LogLocal("[getDeviceId] -readFileData(\"UserIMEI\") = ["+readFileData("UserIMEI")+"]");
		if((imei==null&&imei=="")&&(readFileData("UserIMEI")==null&&readFileData("UserIMEI")==""))
		{
			imei= ""+java.util.UUID.randomUUID();
			writeFileData("UserIMEI",imei);
			strUserID=imei;
			LogLocal("[getDeviceId] write imei = ["+imei+"]");
		}
		else if((imei==null&&imei=="")&&(readFileData("UserIMEI")!=null&&readFileData("UserIMEI")!=""))
		{
			imei=readFileData("UserIMEI");
			strUserID=imei;
			LogLocal("[getDeviceId] read imei = ["+imei+"]");
		}
		else if((imei!=null&&imei!="")&&(readFileData("UserIMEI")==null&&readFileData("UserIMEI")==""))
		{
			writeFileData("UserIMEI",imei);
			strUserID=imei;
			LogLocal("[getDeviceId] Set imei as local imei = ["+strUserID+"]");
		}
		else
		{
			strUserID=imei;
			LogLocal("[getDeviceId] Set imei as phone = ["+imei+"]");
		}
		LogLocal("[getDeviceId] strUserID = ["+strUserID+"]");
		DeviceId= strUserID;//MD5.getMessageDigest(strUserID.getBytes());
		LogLocal("[getDeviceId] Get DeviceId = ["+DeviceId+"]");
		return DeviceId;
	}



	public void InitChannel(final APPBaseInterface appcall)
	{
		final Context applicationContext = mContext.getApplicationContext();
		mInAppChannel = new InAppChannel() ;
		LogLocal("[MercuryActivity][InitChannel] Local InitChannel()->"+mInAppChannel);
		mInAppChannel.ActivityInit((Activity)mContext, appcall);
	}
	public void InitAd(final APPBaseInterface appcall)
	{
		final Context applicationContext = mContext.getApplicationContext();
		mInAppAD= new InAppAD() ;
		LogLocal("[MercuryActivity][InitAd] Local InitAd()->"+mInAppAD);
		mInAppAD.ActivityInit((Activity)mContext,appcall);

	}
	public void Purchase(String pidname)
	{
		LogLocal("[MercuryActivity][Purchase] " + pidname);
		mInAppChannel.Purchase(pidname);
	}
	public void RestoreProduct()
	{
		LogLocal("[MercuryActivity][RestoreProduct] ");
		mInAppChannel.RestoreProduct();
	}
	public void ExitGame()
	{
		LogLocal("[MercuryActivity][ExitGame] ");
		new Handler(mContext.getMainLooper()).post(new Runnable() {
			@Override
			public void run()
			{
				mInAppChannel.ExitGame();
			}
			});
	}
	public void SingmaanLogin()
	{
		LogLocal("[MercuryActivity][SingmaanLogin] ");
		new Handler(mContext.getMainLooper()).post(new Runnable() {
			@Override
			public void run()
			{
				if(mInAppChannel != null) {
					mInAppChannel.SingmaanLogin();
				}
			}
		});
	}
	public void SingmaanLogout()
	{
		LogLocal("[MercuryActivity][SingmaanLogout]" + mInAppChannel);
		mInAppChannel.SingmaanLogout();
	}
	public void UploadGameData()
	{
		LogLocal("[MercuryActivity][UploadGameData]" + mInAppChannel);
		mInAppChannel.UploadGameData();
	}
	public void DownloadGameData()
	{
		LogLocal("[MercuryActivity][DownloadGameData]" + mInAppChannel);
		mInAppChannel.DownloadGameData();
	}
	public void Redeem()
	{
		new Handler(mContext.getMainLooper()).post(new Runnable() {
			@Override
			public void run()
			{
				redeemCode();
			}
		});
	}

	public void RateGame()
	{
		LogLocal("[MercuryActivity][RateGame]mInAppChannel="+mInAppChannel);
		new Handler(mContext.getMainLooper()).post(new Runnable() {
			@Override
			public void run()
			{
				mInAppChannel.RateGame();
			}
		});
	}
	public void ShareGame()
	{
		LogLocal("[MercuryActivity][ShareGame] mInAppChannel="+mInAppChannel);
		new Handler(mContext.getMainLooper()).post(new Runnable() {
			@Override
			public void run()
			{
				mInAppChannel.ShareGame();
			}
		});
	}
	public void OpenGameCommunity()
	{
		LogLocal("[MercuryActivity][OpenGameCommunity] mInAppChannel="+mInAppChannel);
		new Handler(mContext.getMainLooper()).post(new Runnable() {
			@Override
			public void run()
			{
				mInAppChannel.OpenGameCommunity();
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


	public void ActiveBanner()
	{
		LogLocal("[MercuryActivity][ActiveBanner]" + mInAppAD);
		if(mInAppAD != null) { mInAppAD.ActiveBanner(); }
	}
	public void ActiveInterstitial()
	{
		LogLocal("[MercuryActivity][ActiveInterstitial]" + mInAppAD);
		if(mInAppAD != null) { mInAppAD.ActiveInterstitial(); }
	}
	public void ActiveNative()
	{
		LogLocal("[MercuryActivity][ActiveNative]" + mInAppAD);
		if(mInAppAD != null) { mInAppAD.ActiveNative(); }
	}
	public void ActiveRewardVideo()
	{
		LogLocal("[MercuryActivity][ActiveRewardVideo]" + mInAppAD);
		if(mInAppAD != null) { mInAppAD.ActiveRewardVideo(); }
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
	
	


}
