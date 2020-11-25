package com.mercury.game.InAppAdvertisement;
import com.adtiming.mediationsdk.AdTimingAds;
import com.adtiming.mediationsdk.InitCallback;
import com.adtiming.mediationsdk.utils.error.AdTimingError;
import com.mercury.game.MercuryApplication;
import com.mercury.game.util.APPBaseInterface;
import com.mercury.game.util.InAppBase;
import com.mercury.game.MercuryActivity;
import com.adtiming.mediationsdk.video.AdTimingRewardedVideo;
import com.adtiming.mediationsdk.video.RewardedVideoListener;
import com.adtiming.mediationsdk.utils.error.AdTimingError;
import com.adtiming.mediationsdk.utils.model.Scene;
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
	public void ActivityInit(Activity context,final APPBaseInterface appcall)
	{
		super.ActivityInit(context, appcall);
		MercuryActivity.LogLocal("[InAppAD][ActivityInit]="+appShow);
		String appKey = "6KZ7KEDfmtVfH1m11cZ3vy5dMWjCZEFk";
		AdTimingAds.init(mContext, appKey, new InitCallback() {

			// Invoked when the initialization is successful.
			@Override
			public void onSuccess() {
				MercuryActivity.LogLocal("[InAppAD][ActivityInit]=onSuccess");

				AdTimingRewardedVideo.setAdListener(new RewardedVideoListener() {

					/**
					 * Invoked when the ad availability status is changed.
					 *
					 * @param available is a boolean.
					 *      True: means the rewarded videos is available and
					 *          you can show the video by calling AdTimingRewardedVideo.showAd().
					 *      False: means no videos are available
					 */
					@Override
					public void onRewardedVideoAvailabilityChanged(boolean available) {
						// Change the rewarded video state according to availability in app.
						// You could show ad right after it's was loaded here
						MercuryActivity.LogLocal("[InAppAD][ActivityInit]onRewardedVideoAvailabilityChanged="+available);
					}

					/**
					 * Invoked when the RewardedVideo ad view has opened.
					 * Your activity will lose focus.
					 */
					@Override
					public void onRewardedVideoAdShowed(Scene scene) {
						// Do not perform heavy tasks till the video ad is going to be closed.
						MercuryActivity.LogLocal("[InAppAD][ActivityInit]onRewardedVideoAdShowed="+scene);
					}

					/**
					 * Invoked when the call to show a rewarded video has failed
					 * @param error contains the reason for the failure:
					 */
					@Override
					public void onRewardedVideoAdShowFailed(Scene scene, AdTimingError error) {
						// Video Ad show failed
						MercuryActivity.LogLocal("[InAppAD][ActivityInit]onRewardedVideoAdShowFailed error="+error);
					}

					/**
					 * Invoked when the user clicked on the RewardedVideo ad.
					 */
					@Override
					public void onRewardedVideoAdClicked(Scene scene) {
						// Video Ad is clicked
						MercuryActivity.LogLocal("[InAppAD][ActivityInit]onRewardedVideoAdClicked scene="+scene);
					}

					/**
					 * Invoked when the RewardedVideo ad is closed.
					 * Your activity will regain focus.
					 */
					@Override
					public void onRewardedVideoAdClosed(Scene scene) {
						// Video Ad Closed
						MercuryActivity.LogLocal("[InAppAD][ActivityInit]onRewardedVideoAdClosed scene="+scene);
					}

					/**
					 * Invoked when the RewardedVideo ad start to play.
					 * NOTE:You may not receive this callback on some AdNetworks.
					 */
					@Override
					public void onRewardedVideoAdStarted(Scene scene) {
						// Video Ad Started
						MercuryActivity.LogLocal("[InAppAD][ActivityInit]onRewardedVideoAdStarted scene="+scene);
					}

					/**
					 * Invoked when the RewardedVideo ad play end.
					 * NOTE:You may not receive this callback on some AdNetworks.
					 */
					@Override
					public void onRewardedVideoAdEnded(Scene scene) {
						// Video Ad play end
						MercuryActivity.LogLocal("[InAppAD][ActivityInit]onRewardedVideoAdEnded scene="+scene);
					}

					/**
					 * Invoked when the video is completed and the user should be rewarded.
					 * If using server-to-server callbacks you may ignore this events and wait
					 * for the callback from the AdTiming server.
					 */
					@Override
					public void onRewardedVideoAdRewarded(Scene scene) {
						// Here you can reward the user according to your in-app settings.
						MercuryActivity.LogLocal("[InAppAD][ActivityInit]onRewardedVideoAdRewarded scene="+scene);
						AdShowSuccessCallBack("ActiveRewardVideo");
						AdTimingRewardedVideo.loadAd();
					}
				});
			}

			// Invoked when the initialization is failed.
			@Override
			public void onError(AdTimingError message) {
				MercuryActivity.LogLocal("[InAppAD][ActivityInit]message="+message);
			}
		});

	}


	@Override
	public void ApplicationInit(Application app)
	{
		MercuryActivity.LogLocal("[InAppAD][ApplicationInit]="+appShow);
	}

	@Override
	public void onPause()
	{
		MercuryActivity.LogLocal("["+appShow+"][onPause]");
		AdTimingAds.onPause(mContext);
	}

	@Override
	public void onResume()
	{
		MercuryActivity.LogLocal("["+appShow+"][onResume]");
		AdTimingAds.onResume(mContext);
	}

	@Override
	public void onDestroy()
	{
		MercuryActivity.LogLocal("["+appShow+"][onDestroy]");
	}
	@Override
	public void onStop()
	{
		MercuryActivity.LogLocal("["+appShow+"][onStop]");
	}
	@Override
	public void onStart()
	{
		MercuryActivity.LogLocal("["+appShow+"][onStart]");
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
		builder.setTitle("ActiveInterstitial");
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
		builder.setCancelable(false);
		builder.create().show();
	}
	public void ActiveBanner() {
		// TODO Auto-generated method stub
		MercuryActivity.LogLocal("["+appShow+"] ActiveBanner");
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setMessage("Testing Mode");
		builder.setTitle("ActiveBanner");
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
		builder.setCancelable(false);
		builder.create().show();
	}
	public void ActiveNative() {
		// TODO Auto-generated method stub
		MercuryActivity.LogLocal("["+appShow+"] ActiveNative");
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setMessage("Testing Mode");
		builder.setTitle("ActiveNative");
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
		builder.setCancelable(false);
		builder.create().show();
	}
	public void ActiveRewardVideo() {
		// TODO Auto-generated method stub
		MercuryActivity.LogLocal("["+appShow+"] ActiveRewardVideo AdTimingRewardedVideo.isReady()="+AdTimingRewardedVideo.isReady());
		//if you would like to show ad when it's required
		if (AdTimingRewardedVideo.isReady()) {
			AdTimingRewardedVideo.showAd();
		}
		else
		{
			AdTimingRewardedVideo.loadAd();
		}
//		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//		builder.setMessage("Testing Mode");
//		builder.setTitle("ActiveRewardVideo");
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
//		builder.setCancelable(false);
//		builder.create().show();
	}
	//end
}

