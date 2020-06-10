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

import java.util.HashMap;
import java.util.Map;

import cn.sirius.nga.NGASDK;
import cn.sirius.nga.NGASDKFactory;
import cn.sirius.nga.properties.NGAVideoController;
import cn.sirius.nga.properties.NGAVideoListener;
import cn.sirius.nga.properties.NGAVideoProperties;
import cn.sirius.nga.properties.NGAdController;
//end

public class InAppAD extends InAppBase {
	//comment
	public static String appShow="InAppJiuyou";
	public static String MyScence = "";
	public static String appId = "1000004107";
	public static String videoPosId = "1475893508926018";

	public void ActivityInit(Activity context,final APPBaseInterface appcall)
	{
		super.ActivityInit(context, appcall);
		MercuryActivity.LogLocal("["+appShow+"]->ActivityInit");
		initSdk(mContext, new NGASDK.InitCallback() {
			@Override
			public void success() {
				loadAd(mContext);
			}
			@Override
			public void fail(Throwable throwable) {
				throwable.printStackTrace();
			}
		});

	}
	private NGAVideoController mController;
	NGAVideoListener mAdListener = new NGAVideoListener() {

		@Override
		public void onShowAd() {
			MercuryActivity.LogLocal("["+appShow+"]onShowAd");
			AdShowSuccessCallBack("ActiveRewardVideo");
		}

		@Override
		public void onClickAd() {
			MercuryActivity.LogLocal("["+appShow+"]onClickAd");
		}

		@Override
		public void onCloseAd() {
			mController = null;
			MercuryActivity.LogLocal("["+appShow+"]onCloseAd");
			loadAd(mContext);
		}

		@Override
		public void onErrorAd(final int code, final String message) {
			mController = null;
			MercuryActivity.LogLocal("["+appShow+"]"+String.format("onErrorAd code=%s, message=%s", code, message));
		}

		@Override
		public void onRequestAd() {
			MercuryActivity.LogLocal("["+appShow+"]onRequestAd");
		}

		@Override
		public <T extends NGAdController> void onReadyAd(T controller) {
			mController = (NGAVideoController) controller;
			MercuryActivity.LogLocal("["+appShow+"]onReadyAd");
		}

		@Override
		public void onCompletedAd() {
			MercuryActivity.LogLocal("["+appShow+"]onCompletedAd");
		}
	};
	private static void initSdk(Activity activity, final NGASDK.InitCallback initCallback) {
		// 重新初始化sdk
		MercuryActivity.LogLocal("["+appShow+"]->AdConfig");
		NGASDK ngasdk = NGASDKFactory.getNGASDK();
		Map<String, Object> args = new HashMap<>();
		args.put(NGASDK.APP_ID,appId);
		//打Release包的时候，需要把DebugMode设置为false
		args.put(NGASDK.DEBUG_MODE, false);
		ngasdk.init(activity, args, initCallback);
	}
	public void loadAd(Activity activity) {
		final NGAVideoProperties properties = new NGAVideoProperties(activity, appId,videoPosId);
		properties.setListener(mAdListener);
		NGASDK ngasdk = NGASDKFactory.getNGASDK();
		ngasdk.loadAd(properties);
	}
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
		MercuryActivity.LogLocal("["+appShow+"] ActiveRewardVideo");
		boolean hasCacheAd = mController.hasCacheAd();
		if (hasCacheAd==false)
		{
			loadAd(mContext);
		}
		else
		{
			mController.showAd();
		}
//		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//		builder.setMessage("Testing Mode");
//		builder.setTitle("Choice Result");
//		builder.setPositiveButton("Success", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				AdShowSuccessCallBack("ActiveRewardVideo");
//				AdLoadSuccessCallBack("ActiveRewardVideo");
//			}
//		});
//		builder.setNeutralButton("Failed", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				AdLoadFailedCallBack("failed");
//			}
//		});
//		builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.dismiss();
//			}
//		});
//		builder.create().show();
	}
	//end
}

