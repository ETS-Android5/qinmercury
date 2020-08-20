package com.mercury.game;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import static com.mercury.game.MercuryApplication.channelname;
import static com.mercury.game.util.Function.redeemCode;
//shrinkpartstart
import android.support.v4.app.ActivityCompat;
import static com.mercury.game.util.Function.readFileData;
import static com.mercury.game.util.Function.verifyGame;
import static com.mercury.game.util.Function.writeFileData;
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
import com.tendcloud.tenddata.TDGAAccount;
import com.tendcloud.tenddata.TDGAMission;
import com.tendcloud.tenddata.TDGAVirtualCurrency;
import com.tendcloud.tenddata.TalkingDataGA;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
	public static String order_id = "";
	public  static APPBaseInterface mappcall= null;
	public void InitSDK(Context ContextFromUsers,final APPBaseInterface appcall)
	{
		LogLocal("[MercuryActivity][InitSDK]1");
		mappcall = appcall;
		mContext =  ContextFromUsers;
		mActivity = (Activity)ContextFromUsers;
		activityforappbase=this;
		ChannelSplash();
		PlayVideo();
		GetProductionInfo();
//		verifyGame(GameName);
		InitChannel(mappcall);
		InitAd(mappcall);
		UserDeviceID();
		//login
		TDGAAccount account = TDGAAccount.setAccount(TalkingDataGA.getDeviceId(mContext));
		account.setAccountType(TDGAAccount.AccountType.ANONYMOUS);
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

	public void PlayVideo() {

		final SurfaceView svStart = new SurfaceView(mActivity);
		final MediaPlayer mediaPlayer = new MediaPlayer();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		final SurfaceHolder holder = svStart.getHolder();
		holder.addCallback(new SurfaceHolder.Callback() {
			@Override
			public void surfaceCreated(SurfaceHolder surfaceHolder) {
				Log.e("SurfaceView", "surfaceCreated");
			}

			@Override
			public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
				mediaPlayer.setDisplay(holder);
				Log.e("SurfaceView", "surfaceChanged");
			}

			@Override
			public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
				Log.e("SurfaceView", "surfaceDestroyed");

			}
		});
		holder.setKeepScreenOn(true);
		mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				FrameLayout.LayoutParams videoViewp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
				videoViewp.gravity = Gravity.CENTER;
				mActivity.addContentView(svStart, videoViewp);
				if (!mediaPlayer.isPlaying()) {
					mediaPlayer.start();
				}
			}
		});
		mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mediaPlayer) {
				if (mediaPlayer.isPlaying()) {
					mediaPlayer.stop();
				}
				mediaPlayer.release();
				//播放完成  做你的其他操作
				((ViewGroup) svStart.getParent()).removeView(svStart);
				InitAd(mappcall);
			}
		});

		try {
			String path = getAssetsCacheFile(mActivity, "ChannelSplash.mp4");
			mediaPlayer.setDataSource(path);
			mediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
			mediaPlayer.setLooping(false);
			mediaPlayer.prepare();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			Log.e("IOException", ioe.getMessage());
		}

	}

	public static String getAssetsCacheFile(Context context, String fileName) {
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
		//shrinkpartstart
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
		LogLocal("[MercuryActivity][UserDeviceID] DeviceId="+DeviceId);
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
		//shrinkpartend
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
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		String temp = sf.format(new Date());
		int random = (int) ((Math.random() + 1) * 100000);
		order_id = temp + random;
		mInAppChannel.Purchase(pidname);
		TDGAVirtualCurrency.onChargeRequest(order_id, MercuryConst.Qindesc,  MercuryConst.Qinpricefloat, "CNY", 1, channelname);
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

	public void onEvent(String eventID)
	{
		LogLocal("[MercuryActivity][onEvent] " + eventID);
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
	public void Data_UseItem(String quantity, String item)
	{
		LogLocal("[MercuryActivity] Data_UseItem()->mInApp="+mInAppAD);
		int myint =  Integer.parseInt(quantity);
		TDGAVirtualCurrency.onReward(myint, item);
	}

	public void Data_LevelBegin(String key)
	{
		LogLocal("[MercuryActivity] Data_LevelBegin()->mInApp="+mInAppAD);
		TDGAMission.onBegin(key);
	}
	public void Data_LevelCompleted(String key)
	{
		LogLocal("[MercuryActivity] Data_LevelCompleted()->mInApp="+mInAppAD);
		TDGAMission.onCompleted(key);
	}
	public void Data_Event(String key)
	{
		LogLocal("[MercuryActivity] Data_Event()->mInApp="+mInAppAD);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key", key);// 在注册环节的每一步完成时，以步骤名作为value传送数据
		TalkingDataGA.onEvent("Event", map);
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
