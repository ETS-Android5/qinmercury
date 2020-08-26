package com.mercury.game.InAppAdvertisement;
import com.mercury.game.MercuryApplication;
import com.mercury.game.util.APPBaseInterface;
import com.mercury.game.util.InAppBase;
import com.mercury.game.MercuryActivity;
import com.mob4399.adunion.AdUnionBanner;
import com.mob4399.adunion.AdUnionSDK;
import com.mob4399.adunion.AdUnionVideo;
import com.mob4399.adunion.listener.OnAuBannerAdListener;
import com.mob4399.adunion.listener.OnAuInitListener;
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
import android.widget.EditText;
import android.widget.RelativeLayout;
//end

public class InAppAD extends InAppBase {
	//comment
	public static String appShow="InAppAD";
	public static String MyScence = "";
	String appId = "2762"; //应用ID，开发者平台进行获取
	String videoPosId = "11233";
	String BANNER_POS_ID = "11367";
	AdUnionVideo videoAd;
	private AdUnionBanner adUnionBanner;
	private RelativeLayout mBannerContaierLayout;
	public void ActivityInit(Activity context,final APPBaseInterface appcall)
	{
		super.ActivityInit(context, appcall);
		MercuryActivity.LogLocal("["+appShow+"]->ActivityInit");
		MercuryActivity.LogLocal("["+appShow+"]->appId="+appId);
		MercuryActivity.LogLocal("["+appShow+"]->videoPosId="+videoPosId);
		MercuryActivity.LogLocal("["+appShow+"]->BANNER_POS_ID="+BANNER_POS_ID);

		AdUnionSDK.init(mContext,appId, onAuInitListener);

	}
	private OnAuInitListener onAuInitListener = new OnAuInitListener() {
		@Override
		public void onSucceed() {
			//SDK初始化成功后，进行广告加载
			MercuryActivity.LogLocal("["+appShow+"]->onSucceed");
			onShowVideoAd();
			onBannerAdInit();
//			fetchAD();
		}

		@Override
		public void onFailed(String s) {
			// SDK初始化失败回调
			MercuryActivity.LogLocal("["+appShow+"]->onFailed="+s);
//			finish();
		}
	};



	public void onBannerAdInit() {
		adUnionBanner = new AdUnionBanner(mContext, BANNER_POS_ID, new OnAuBannerAdListener() {
			@Override
			public void onBannerLoaded(View bannerView) {
				//判断当前广告是否已被加载,若已被加载则进行移除后重新添加
				if (bannerView.getParent() != null) {
					((ViewGroup)bannerView.getParent()).removeView(bannerView);
				}
				//添加广告到容器中
				mBannerContaierLayout.addView(bannerView);
				AdShowSuccessCallBack("ActiveBanner");
			}

			@Override
			public void onBannerFailed(String s) {
				MercuryActivity.LogLocal("广告加载失败："+s);
				AdShowFailedCallBack("ActiveBanner");
			}

			@Override
			public void onBannerClicked() {
				MercuryActivity.LogLocal("广告被点击");
			}

			@Override
			public void onBannerClosed() {
				MercuryActivity.LogLocal("广告被关闭");
			}
		});
	}
	public void onShowVideoAd() {

		//广告初始化，预加载
		videoAd = new AdUnionVideo(mContext, videoPosId , new OnAuVideoAdListener() {
			@Override
			public void onVideoAdLoaded() {
				MercuryActivity.LogLocal("["+appShow+"]->onVideoAdLoaded");
				AdLoadSuccessCallBack("ActiveRewardVideo");
			}

			@Override
			public void onVideoAdShow() {
				MercuryActivity.LogLocal("["+appShow+"]->onVideoAdShow");
			}

			@Override
			public void onVideoAdFailed(String message) {
				MercuryActivity.LogLocal("["+appShow+"]->onVideoAdFailed");
				AdShowFailedCallBack("ActiveRewardVideo");
			}

			@Override
			public void onVideoAdClicked() {
				MercuryActivity.LogLocal("["+appShow+"]->onVideoAdClicked");
			}

			@Override
			public void onVideoAdClosed() {
				MercuryActivity.LogLocal("["+appShow+"]->onVideoAdClosed");
			}

			@Override
			public void onVideoAdComplete() {
				MercuryActivity.LogLocal("["+appShow+"]->onVideoAdComplete");
				AdShowSuccessCallBack("ActiveRewardVideo");
			}
		});
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
		adUnionBanner.loadAd();
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
	public void ActiveRewardVideo() {
		// TODO Auto-generated method stub
		if (videoAd != null && videoAd.isReady()) {
			MercuryActivity.LogLocal("["+appShow+"] ActiveRewardVideo showing");
			videoAd.show();
		}else {
			AdShowFailedCallBack("ActiveRewardVideo failed, so loading again");
			onShowVideoAd();
		}

	}
	//end
}

