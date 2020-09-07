package com.mercury.game.InAppAdvertisement;
import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.ATSDK;
import com.anythink.core.api.AdError;
import com.anythink.rewardvideo.api.ATRewardVideoAd;
import com.anythink.rewardvideo.api.ATRewardVideoListener;
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

public class InAppAD extends InAppBase {
	//comment
	public static String appShow="InAppAD";
	public static String MyScence = "";
	public String appid = "a5f50b6668457b";
	public String appKey = "8cd3e6589494074cc2d8bc1be31656e2";
	public String videoid = "b5f50b79556b76";
	ATRewardVideoAd mRewardVideoAd = null;
	public void ActivityInit(Activity context,final APPBaseInterface appcall)
	{
		super.ActivityInit(context, appcall);
		MercuryActivity.LogLocal("["+appShow+"]->ActivityInit appid="+appid+" appKey="+appKey+" videoid="+videoid+", ATSDK.getSDKVersionName()="+ATSDK.getSDKVersionName());
		ATSDK.init(mContext, appid, appKey);

		videoADLoad();

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
	public void videoADLoad()
	{
		mRewardVideoAd = new ATRewardVideoAd(mContext,videoid);
		mRewardVideoAd.setAdListener(new ATRewardVideoListener() {
			@Override
			public void onRewardedVideoAdLoaded() {
				MercuryActivity.LogLocal("["+appShow+"] onRewardedVideoAdLoaded");
				AdLoadSuccessCallBack("ActiveRewardVideo");
			}

			@Override
			public void onRewardedVideoAdFailed(AdError errorCode) {
				MercuryActivity.LogLocal("["+appShow+"] onRewardedVideoAdFailed："+errorCode.toString());
			}

			@Override
			public void onRewardedVideoAdPlayStart(ATAdInfo entity) {
				MercuryActivity.LogLocal("["+appShow+"] onRewardedVideoAdPlayStart");
			}
			@Override
			public void onRewardedVideoAdPlayEnd(ATAdInfo entity) {
				MercuryActivity.LogLocal("["+appShow+"] onRewardedVideoAdPlayEnd");
				mRewardVideoAd.load();
			}

			@Override
			public void onRewardedVideoAdPlayFailed(AdError errorCode, ATAdInfo entity) {
				MercuryActivity.LogLocal("["+appShow+"] onRewardedVideoAdPlayFailed");
			}

			@Override
			public void onRewardedVideoAdClosed(ATAdInfo entity) {
				//建议在此回调中调用load进行广告的加载，方便下一次广告的展示
				MercuryActivity.LogLocal("["+appShow+"] onRewardedVideoAdClosed");
			}

			@Override
			public void onReward(ATAdInfo entity) {
				AdShowSuccessCallBack("ActiveRewardVideo");

			}
			@Override
			public void onRewardedVideoAdPlayClicked(ATAdInfo entity) {
				MercuryActivity.LogLocal("["+appShow+"] onRewardedVideoAdPlayClicked");
			}
		});
		mRewardVideoAd.load();
	}
	public void ActiveRewardVideo() {
		// TODO Auto-generated method stub
		MercuryActivity.LogLocal("["+appShow+"] ActiveRewardVideo");
		if(mRewardVideoAd.isAdReady()){
			MercuryActivity.LogLocal("["+appShow+"] isAdReady");
			mRewardVideoAd.show(mContext);
		} else {
			MercuryActivity.LogLocal("["+appShow+"] loading");
			mRewardVideoAd.load();
		}
	}
	//end
}

