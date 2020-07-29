package com.mercury.game.InAppAdvertisement;
import com.mercury.game.MercuryApplication;
import com.mercury.game.util.APPBaseInterface;
import com.mercury.game.util.InAppBase;
import com.mercury.game.MercuryActivity;
import com.miui.zeus.mimo.sdk.MimoSdk;
import com.miui.zeus.mimo.sdk.RewardVideoAd;
//comment
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
//end

public class InAppAD extends InAppBase {
	//comment
	public static String appShow="InAppAD";
	public static String MyScence = "";
	private static final String APP_ID = "2882303761518459342";
	private static final String PORTRAIT_POS_ID = "bc9f6437616d3e6248a98c6810a3011e";
	private RewardVideoAd mRewardVideoAd;
	public void ActivityInit(Activity context,final APPBaseInterface appcall)
	{
		super.ActivityInit(context, appcall);
		MercuryActivity.LogLocal("["+appShow+"]->ActivityInit");
		loadVideoAd();

	}
	@Override
	public void ApplicationInit(Application app)
	{
		MercuryActivity.LogLocal("["+appShow+"]->ApplicationInit="+app);
		MimoSdk.init(app, APP_ID);
		MimoSdk.setDebugOn(true); // 设置sdk 是否打开debug
		MimoSdk.setStagingOn(true); // 设置sdk是否打开测试环境
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
		if (mRewardVideoAd != null) {
			mRewardVideoAd.recycle();
		}
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
	public void onTerminate()
	{

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
				AdShowSuccessCallBack("ActiveInterstitial");
				AdLoadSuccessCallBack("ActiveInterstitial");
			}
		});
		builder.setNeutralButton("Failed", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				AdShowFailedCallBack("ActiveInterstitial");
			}
		});
		builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				AdShowFailedCallBack("ActiveInterstitial");
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
				AdShowSuccessCallBack("ActiveBanner");
				AdLoadSuccessCallBack("ActiveBanner");
			}
		});
		builder.setNeutralButton("Failed", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				AdShowFailedCallBack("ActiveBanner");
			}
		});
		builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				AdShowFailedCallBack("ActiveInterstitial");
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
				AdShowSuccessCallBack("ActiveNative");
				AdLoadSuccessCallBack("ActiveNative");
			}
		});
		builder.setNeutralButton("Failed", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				AdShowFailedCallBack("ActiveNative");
			}
		});
		builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				AdShowFailedCallBack("ActiveInterstitial");
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
	public void loadVideoAd()
	{
		mRewardVideoAd = new RewardVideoAd(mContext);
		mRewardVideoAd.loadAd(PORTRAIT_POS_ID, new RewardVideoAd.RewardVideoLoadListener() {
			@Override
			public void onAdLoadSuccess() {
				AdLoadSuccessCallBack("ActiveRewardVideo");
			}
			@Override
			public void onAdLoadFailed(int errorCode, String errorMsg) {
				MercuryActivity.LogLocal("["+appShow+"]->errorcode = " + errorCode + ", errorMsg = " + errorMsg);
			} });

	}
	public void ActiveRewardVideo() {
		// TODO Auto-generated method stub
		MercuryActivity.LogLocal("["+appShow+"] ActiveRewardVideo");
		mRewardVideoAd.showAd(mRewardVideoInteractionListener);
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
//				AdShowFailedCallBack("ActiveRewardVideo");
//			}
//		});
//		builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				AdShowFailedCallBack("ActiveInterstitial");
//				dialog.dismiss();
//			}
//		});
//		builder.create().show();
	}
	private RewardVideoAd.RewardVideoInteractionListener mRewardVideoInteractionListener
			= new RewardVideoAd.RewardVideoInteractionListener() {
		@Override
		public void onAdPresent() {
			MercuryActivity.LogLocal("onAdPresent");
		}

		@Override
		public void onAdClick() {
			MercuryActivity.LogLocal("onAdClick");
		}

		@Override
		public void onAdDismissed() {
			MercuryActivity.LogLocal("onAdDismissed");
		}

		@Override
		public void onAdFailed(String message) {
			MercuryActivity.LogLocal("onAdFailed");
		}

		@Override
		public void onVideoStart() {
			MercuryActivity.LogLocal("onVideoStart");
		}

		@Override
		public void onVideoPause() {
			MercuryActivity.LogLocal("onVideoPause");
		}

		@Override
		public void onVideoComplete() {
			MercuryActivity.LogLocal("onVideoComplete");
			AdShowSuccessCallBack("ActiveRewardVideo");
		}

		@Override
		public void onPicAdEnd() {
			MercuryActivity.LogLocal("onPicAdEnd");
		}
	};
	//end
}

