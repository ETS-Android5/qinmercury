package com.mercury.game.InAppAdvertisement;
import com.mercury.game.MercuryApplication;
import com.mercury.game.util.APPBaseInterface;
import com.mercury.game.util.InAppBase;
import com.mercury.game.MercuryActivity;
//comment
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
//end
import com.mob4399.adunion.AdUnionSDK;
import com.mob4399.adunion.AdUnionVideo;
import com.mob4399.adunion.listener.OnAuInitListener;
import com.mob4399.adunion.listener.OnAuVideoAdListener;

public class InAppAD extends InAppBase {
	//comment
	public static String appShow="InAppAD";
	public static String MyScence = "";
	AdUnionVideo videoAd;
	private static final String appId = "3379";
	private static final String VIDEO_POS_ID = "3379";
	public void ActivityInit(Activity context,final APPBaseInterface appcall)
	{
		super.ActivityInit(context, appcall);
		MercuryActivity.LogLocal("["+appShow+"]->ActivityInit");
		AdUnionSDK.init(context,appId, onAuInitListener);
	}
	private OnAuInitListener onAuInitListener = new OnAuInitListener() {
		@Override
		public void onSucceed() {
			//SDK初始化成功后，进行广告加载
			MercuryActivity.LogLocal("["+appShow+"]->init onSucceed");
		}

		@Override
		public void onFailed(String s) {
			// SDK初始化失败回调
			MercuryActivity.LogLocal("["+appShow+"]->init onFailed");
		}
	};
	@Override
	public void ApplicationInit(Application app)
	{
		MercuryActivity.LogLocal("["+appShow+"]->ApplicationInit="+app);
	}

	@Override
	public void onPause()
	{
		MercuryActivity.LogLocal("["+appShow+"]->onPause");
	}


	@Override
	public void onResume()
	{
		MercuryActivity.LogLocal("["+appShow+"]->onResume");
	}

	@Override
	public void onDestroy()
	{
		MercuryActivity.LogLocal("["+appShow+"]->onDestroy");
	}
	@Override
	public void onStop()
	{
		MercuryActivity.LogLocal("["+appShow+"]->onStop");
	}
	@Override
	public void onStart()
	{
		MercuryActivity.LogLocal("["+appShow+"]->onStart");
	}
	public void onActivityResult(int reqCode, int resCode, Intent data) {
		MercuryActivity.LogLocal("["+appShow+"]->onActivityResult");
		super.onActivityResult(reqCode, resCode, data);
	}

	@Override
	public void attachBaseContext(Context base)
	{
		super.attachBaseContext(base);
	}
	public void ActiveInterstitial() {
		// TODO Auto-generated method stub
		MercuryActivity.LogLocal("["+appShow+"] ActiveInterstitial");
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setMessage("Testing Mode");
		builder.setTitle("Choice Result");
		builder.setPositiveButton("Success", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				AdShowSuccessCallBack("Success");
				AdLoadSuccessCallBack("success");
			}
		});
		builder.setNeutralButton("Failed", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				AdLoadFailedCallBack("failed");
			}
		});
		builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
	public void ActiveBanner() {
		// TODO Auto-generated method stub
		MercuryActivity.LogLocal("["+appShow+"] ActiveBanner");
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setMessage("Testing Mode");
		builder.setTitle("Choice Result");
		builder.setPositiveButton("Success", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				AdShowSuccessCallBack("Success");
				AdLoadSuccessCallBack("success");
			}
		});
		builder.setNeutralButton("Failed", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				AdLoadFailedCallBack("failed");
			}
		});
		builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
	public void ActiveNative() {
		// TODO Auto-generated method stub
		MercuryActivity.LogLocal("["+appShow+"] ActiveNative");
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setMessage("Testing Mode");
		builder.setTitle("Choice Result");
		builder.setPositiveButton("Success", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				AdShowSuccessCallBack("Success");
				AdLoadSuccessCallBack("success");
			}
		});
		builder.setNeutralButton("Failed", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				AdLoadFailedCallBack("failed");
			}
		});
		builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
	public void ActiveRewardVideo() {
		// TODO Auto-generated method stub
		AdUnionVideo videoAd = new AdUnionVideo(mContext, VIDEO_POS_ID , new OnAuVideoAdListener() {
			@Override
			public void onVideoAdLoaded() {
				MercuryActivity.LogLocal("VideoAd loaded");
			}

			@Override
			public void onVideoAdShow() {
				MercuryActivity.LogLocal("VideoAd show");
			}

			@Override
			public void onVideoAdFailed(String message) {
				MercuryActivity.LogLocal( message);
			}

			@Override
			public void onVideoAdClicked() {
				MercuryActivity.LogLocal("VideoAd clicked");
			}

			@Override
			public void onVideoAdClosed() {
				MercuryActivity.LogLocal("VideoAd closed");
			}

			@Override
			public void onVideoAdComplete() {
				MercuryActivity.LogLocal( "VideoAd complete");
			}
		});

		if (videoAd != null) {
			videoAd.show();
		}
	}
	//end
}

