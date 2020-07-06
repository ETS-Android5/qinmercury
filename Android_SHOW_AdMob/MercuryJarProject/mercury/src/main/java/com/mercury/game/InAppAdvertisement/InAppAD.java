package com.mercury.game.InAppAdvertisement;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
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
import android.support.annotation.NonNull;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.qinbatista.demo.R;

//end

public class InAppAD extends InAppBase {
	//comment
	public static String appShow="InAppAD";
	public static String MyScence = "";
	public static String reward_video_id = "ca-app-pub-6116228052981506/5023465158";
	public static String banner_id = "ca-app-pub-6116228052981506/3497904141";
	public static String InterstitialAd_id = "ca-app-pub-6116228052981506/3278412299";
	private RewardedAd rewardedAd;
	private AdView mAdView;
	private InterstitialAd mInterstitialAd;
	public void ActivityInit(Activity context,final APPBaseInterface appcall)
	{
		super.ActivityInit(context, appcall);
		MercuryActivity.LogLocal("["+appShow+"]->ActivityInit");
		MobileAds.initialize(mContext, new OnInitializationCompleteListener() {
			@Override
			public void onInitializationComplete(InitializationStatus initializationStatus) {
			}
		});
		rewardedAd = new RewardedAd(mContext, reward_video_id);
		RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
			@Override
			public void onRewardedAdLoaded() {
				// Ad successfully loaded.
				AdLoadSuccessCallBack("ActiveRewardVideo");
			}

			@Override
			public void onRewardedAdFailedToLoad(int errorCode) {
				// Ad failed to load.
			}
		};
		rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);

		AdView adView = new AdView(mContext);
		adView.setAdSize(AdSize.BANNER);
		adView.setAdUnitId(banner_id);
		AdSize adSize = new AdSize(300, 50);

		mInterstitialAd = new InterstitialAd(mContext);
		mInterstitialAd.setAdUnitId(InterstitialAd_id);
		mInterstitialAd.loadAd(new AdRequest.Builder().build());
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
		if (mInterstitialAd.isLoaded()) {
			mInterstitialAd.show();
			mInterstitialAd.setAdListener(new AdListener() {
				@Override
				public void onAdLoaded() {
					// Code to be executed when an ad finishes loading.
					AdLoadSuccessCallBack("ActiveInterstitial");
				}

				@Override
				public void onAdFailedToLoad(int errorCode) {
					// Code to be executed when an ad request fails.
					AdLoadFailedCallBack("ActiveInterstitial");
				}

				@Override
				public void onAdOpened() {
					// Code to be executed when the ad is displayed.
				}

				@Override
				public void onAdClicked() {
					// Code to be executed when the user clicks on an ad.
				}

				@Override
				public void onAdLeftApplication() {
					// Code to be executed when the user has left the app.
				}

				@Override
				public void onAdClosed() {
					// Code to be executed when the interstitial ad is closed.
					AdShowSuccessCallBack("ActiveInterstitial");
					mInterstitialAd = new InterstitialAd(mContext);
					mInterstitialAd.setAdUnitId(InterstitialAd_id);
					mInterstitialAd.loadAd(new AdRequest.Builder().build());
				}

			});
		} else {
			MercuryActivity.LogLocal("The interstitial wasn't loaded yet.");
		}
	}
	public void ActiveBanner() {
		// TODO Auto-generated method stub
//		mAdView = mContext.findViewById(R.id.);
//		AdRequest adRequest = new AdRequest.Builder().build();
//		mAdView.loadAd(adRequest);

//		mAdView.adListener = object: AdListener() {
//			override fun onAdLoaded() {
//				// Code to be executed when an ad finishes loading.
//			}
//
//			override fun onAdFailedToLoad(errorCode : Int) {
//				// Code to be executed when an ad request fails.
//			}
//
//			override fun onAdOpened() {
//				// Code to be executed when an ad opens an overlay that
//				// covers the screen.
//				AdShowSuccessCallBack("ActiveBanner");
//			}
//
//			override fun onAdClicked() {
//				// Code to be executed when the user clicks on an ad.
//			}
//
//			override fun onAdLeftApplication() {
//				// Code to be executed when the user has left the app.
//			}
//
//			override fun onAdClosed() {
//				// Code to be executed when the user is about to return
//				// to the app after tapping on an ad.
//				AdShowFailedCallBack("ActiveNative");
//			}
//		}
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
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
	public void ActiveRewardVideo() {
		// TODO Auto-generated method stub
		MercuryActivity.LogLocal("["+appShow+"] ActiveRewardVideo");
		if (rewardedAd.isLoaded()) {
			Activity activityContext = mContext;
			RewardedAdCallback adCallback = new RewardedAdCallback() {
				@Override
				public void onRewardedAdOpened() {
					// Ad opened.
				}

				@Override
				public void onRewardedAdClosed() {
					// Ad closed.
					rewardedAd = createAndLoadRewardedAd();
					RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
						@Override
						public void onRewardedAdLoaded() {
							// Ad successfully loaded.
							AdLoadSuccessCallBack("ActiveRewardVideo");
						}

						@Override
						public void onRewardedAdFailedToLoad(int errorCode) {
							// Ad failed to load.
						}
					};
					rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
				}

				@Override
				public void onUserEarnedReward(@NonNull RewardItem reward) {
					// User earned reward.
					AdShowSuccessCallBack("ActiveRewardVideo");

				}

				@Override
				public void onRewardedAdFailedToShow(int errorCode) {
					// Ad failed to display.
					AdShowFailedCallBack("ActiveRewardVideo");
				}
			};
			rewardedAd.show(activityContext, adCallback);
		} else {
			MercuryActivity.LogLocal("The rewarded ad wasn't loaded yet.");
		}
	}
	public RewardedAd createAndLoadRewardedAd() {
		RewardedAd rewardedAd = new RewardedAd(mContext,reward_video_id);
		RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
			@Override
			public void onRewardedAdLoaded() {
				// Ad successfully loaded.
			}

			@Override
			public void onRewardedAdFailedToLoad(int errorCode) {
				// Ad failed to load.
			}
		};
		rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
		return rewardedAd;
	}

	//end
}

