package com.mercury.game.InAppAdvertisement;
import com.mercury.game.MercuryApplication;
import com.mercury.game.util.APPBaseInterface;
import com.mercury.game.util.InAppBase;
import com.mercury.game.MercuryActivity;
import com.mob4399.adunion.AdUnionBanner;
import com.mob4399.adunion.AdUnionInterstitial;
import com.mob4399.adunion.AdUnionParams;
import com.mob4399.adunion.AdUnionSDK;
import com.mob4399.adunion.AdUnionVideo;
import com.mob4399.adunion.listener.OnAuBannerAdListener;
import com.mob4399.adunion.listener.OnAuInitListener;
import com.mob4399.adunion.listener.OnAuInterstitialAdListener;
import com.mob4399.adunion.listener.OnAuVideoAdListener;
//comment
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
//end

public class InAppAD extends InAppBase {

	//comment
	public static String appShow="InAppAD_4399";
	public static String MyScence = "";
	public static String mAppId = "2762";
	public static String mVPosId = "11233";
	public static String mIPosId = "11232";
	public static String mBPosId = "";

	static boolean isInitSucc = false;
	private RelativeLayout mBannerContaierLayout;

	public void ActivityInit(Activity context,final APPBaseInterface appcall)
	{
		super.ActivityInit(context, appcall);
		MercuryActivity.LogLocal("["+appShow+"]->ActivityInit");
//		mBannerContaierLayout = findViewById(R.id.layout_banner);
//		mBannerContaierLayout = new FrameLayout.LayoutParams(-1,-2, 80);
		initSDK();
	}
	@Override
	public void ApplicationInit(Application app)
	{
		MercuryActivity.LogLocal("["+appShow+"]->ApplicationInit="+app);
	}
	@Override public void onPause() { MercuryActivity.LogLocal("["+appShow+"]->onPause"); }
	@Override public void onResume() { MercuryActivity.LogLocal("["+appShow+"]->onResume"); }
	@Override public void onDestroy() { MercuryActivity.LogLocal("["+appShow+"]->onDestroy"); }
	@Override public void onStop() { MercuryActivity.LogLocal("["+appShow+"]->onStop"); }
	@Override public void onStart() { MercuryActivity.LogLocal("["+appShow+"]->onStart"); }
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
		MercuryActivity.LogLocal("["+appShow+"] ActiveInterstitial");
		if(false == isInitSucc) {
			initSDK();
			return;
		}
		if(null != adUnionInterstitial)
			adUnionInterstitial.show();
		else
			Toast.makeText(mContext, "请先初始化完成", Toast.LENGTH_SHORT).show();
	}
	public void ActiveBanner() {
		// TODO Auto-generated method stub
		MercuryActivity.LogLocal("["+appShow+"] ActiveBanner");
		TestFunction();
//		if (adUnionBanner == null) {
//			Toast.makeText(mContext, "请先初始化Banner广告位", Toast.LENGTH_SHORT).show();
//			return;
//		}
//		adUnionBanner.loadAd();
	}
	public void ActiveNative() {
		// TODO Auto-generated method stub
		MercuryActivity.LogLocal("["+appShow+"] ActiveNative");
		TestFunction();
	}
	public void ActiveRewardVideo() {
		MercuryActivity.LogLocal("["+appShow+"] ActiveRewardVideo");
		if(false == isInitSucc) {
			initSDK();
			return;
		}
		boolean isVideoInit = (videoAd != null);
		boolean isVideoReady = videoAd.isReady();
		if (isVideoInit && isVideoReady) {
			videoAd.show();
		}else {
			Toast.makeText(mContext, "请先初始化完成", Toast.LENGTH_SHORT).show();
		}
	}

	private void TestFunction()
	{
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
	//end

	// ============================================================================================
	AdUnionVideo videoAd;
	AdUnionInterstitial adUnionInterstitial;
	/**
	 * SDK初始化方法
	 */
	private void initSDK(){
		//AdUnionSDK.init(this,appId, onAuInitListener);
		AdUnionParams params = new AdUnionParams.Builder(mAppId) .setDebug(true).build();
//		AdUnionSDK.init(mContext, params,
		AdUnionSDK.init(mContext, mAppId,
				new OnAuInitListener() {
					@Override public void onSucceed() {
						MercuryActivity.LogLocal("["+appShow+"] SDK initialize succeed");
						isInitSucc = true;
						MercuryActivity.LogLocal("["+appShow+"] mContext" + (null == mContext));
						videoAd = new AdUnionVideo(mContext, mVPosId, new OnAuVideoAdListener() {
							@Override public void onVideoAdLoaded() {
								MercuryActivity.LogLocal("["+appShow+"] 视频加载成功");
								AdLoadSuccessCallBack("VideoLoadComplete");
							}
							@Override public void onVideoAdFailed(String message) {
								MercuryActivity.LogLocal("["+appShow+"] VideoFailed:" + message);
								AdLoadFailedCallBack(message);
							}
							@Override public void onVideoAdComplete() {
								MercuryActivity.LogLocal("["+appShow+"] OnComplete");
								AdShowSuccessCallBack("VideoPlayComplete");
							}
							@Override public void onVideoAdShow() {	MercuryActivity.LogLocal("["+appShow+"] 视频显示成功"); }
							@Override public void onVideoAdClicked() { MercuryActivity.LogLocal("["+appShow+"] OnClick"); }
							@Override public void onVideoAdClosed() { MercuryActivity.LogLocal("["+appShow+"] OnClosed"); }
						});

						adUnionInterstitial = new AdUnionInterstitial(mContext, mIPosId, new OnAuInterstitialAdListener() {
							@Override public void onInterstitialLoaded() {
								MercuryActivity.LogLocal("["+appShow+"] Intertitial loaded and show");
								AdLoadSuccessCallBack("InterstitialLoadComplete");
								AdShowSuccessCallBack("InterstitialPlayComplete");
							}
							@Override public void onInterstitialLoadFailed(String message) {
								MercuryActivity.LogLocal("["+appShow+"] InterstitialFailed:" + message);
								AdLoadFailedCallBack(message);
							}
							@Override public void onInterstitialClicked() {
								MercuryActivity.LogLocal("["+appShow+"] Intertitial clicked");
							}
							@Override public void onInterstitialClosed() {
								MercuryActivity.LogLocal("["+appShow+"] Intertitial closed");
							}
						});
					}
					@Override public void onFailed(String msg) {
						MercuryActivity.LogLocal("["+appShow+"] SDK initialize onFailed,error msg = " + msg);
					}
				});
	}

//	AdUnionBanner adUnionBanner = new AdUnionBanner(mContext, mBPosId, new OnAuBannerAdListener() {
//		@Override
//		public void onBannerLoaded(View bannerView) {
//			//判断当前广告是否已被加载,若已被加载则进行移除后重新添加
//			if (bannerView.getParent() != null) {
//				((ViewGroup)bannerView.getParent()).removeView(bannerView);
//			}
//			//添加广告到容器中
//			mBannerContaierLayout.addView(bannerView);
//			MercuryActivity.LogLocal("["+appShow+"] Banner loaded");
//			AdLoadSuccessCallBack("BannerLoadComplete");
//		}
//		@Override public void onBannerFailed(String message) {
//			MercuryActivity.LogLocal("["+appShow+"] BannerFailed:" + message);
//			AdLoadFailedCallBack(message);
//		}
//		@Override public void onBannerClicked() { Log.i("AD_DEMO","广告被点击"); }
//		@Override public void onBannerClosed() { Log.i("AD_DEMO","广告被关闭"); }
//	});
}

