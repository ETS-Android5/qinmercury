package com.mercury.game.InAppAdvertisement;
import com.mercury.game.MercuryApplication;
import com.mercury.game.util.APPBaseInterface;
import com.mercury.game.util.InAppBase;
import com.mercury.game.MercuryActivity;
//comment
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import java.util.HashMap;
import java.util.Map;

import cn.sirius.nga.BuildConfig;
import cn.sirius.nga.NGASDK;
import cn.sirius.nga.NGASDKFactory;
import cn.sirius.nga.properties.NGABannerController;
import cn.sirius.nga.properties.NGABannerListener;
import cn.sirius.nga.properties.NGABannerProperties;
import cn.sirius.nga.properties.NGAVideoController;
import cn.sirius.nga.properties.NGAVideoListener;
import cn.sirius.nga.properties.NGAVideoProperties;
import cn.sirius.nga.properties.NGAdController;

import static android.content.Context.WINDOW_SERVICE;
//end

public class InAppAD extends InAppBase {
	//comment
	public static String appShow="InAppAD4399";
	public static String appId = "1000007981";
	public static String videoPosId = "1591865545281";
	public static String bannerPosId = "1591865465803";
	private NGAVideoController mVideoController;
	private NGABannerController mController;
	private NGABannerProperties mProperties;
	private ViewManager mWindowManager;
	private RelativeLayout mBannerView;
	public void ActivityInit(Activity context,final APPBaseInterface appcall)
	{
		super.ActivityInit(context, appcall);
		MercuryActivity.LogLocal("["+appShow+"]->ActivityInit11");
		initSdk(mContext, new NGASDK.InitCallback() {
			@Override
			public void success() {
				MercuryActivity.LogLocal("["+appShow+"]->Advertisement initial success");
				loadAdVideo(mContext);
			}
			@Override
			public void fail(Throwable throwable) {
				throwable.printStackTrace();
				MercuryActivity.LogLocal("["+appShow+"]->Advertisement initial failed");
			}
		});

		loadAdBanner(mContext);

	}
	private static void initSdk(Activity activity, final NGASDK.InitCallback initCallback) {
		// 重新初始化sdk
		MercuryActivity.LogLocal("["+appShow+"]->initSdk");
		NGASDK ngasdk = NGASDKFactory.getNGASDK();
		Map<String, Object> args = new HashMap<>();
		args.put(NGASDK.APP_ID, appId);
		//打Release包的时候，需要把DebugMode设置为false
		args.put(NGASDK.DEBUG_MODE, false);
		ngasdk.init(activity, args, initCallback);
	}

	NGAVideoListener mVideoAdListener = new NGAVideoListener() {

		@Override
		public void onShowAd() {
			MercuryActivity.LogLocal( "["+appShow+"]onShowAd");

		}

		@Override
		public void onClickAd() {
			MercuryActivity.LogLocal("["+appShow+"]onClickAd");
		}

		@Override
		public void onCloseAd() {
			mVideoController = null;
			MercuryActivity.LogLocal("["+appShow+"]onCloseAd");

		}
		@Override
		public void onErrorAd(final int code, final String message) {
			mVideoController = null;
			MercuryActivity.LogLocal( String.format("onErrorAd code=%s, message=%s", code, message));
		}
		@Override
		public void onRequestAd() {
			MercuryActivity.LogLocal( "["+appShow+"]onRequestAd");
		}
		@Override
		public <T extends NGAdController> void onReadyAd(T controller) {
			mVideoController = (NGAVideoController) controller;
			MercuryActivity.LogLocal( "["+appShow+"]onReadyAd");
			AdLoadSuccessCallBack("ActiveRewardVideo");
			mVideoController.showAd();
		}

		@Override
		public void onCompletedAd() {
			AdShowSuccessCallBack("ActiveRewardVideo");
		}
	};

	NGABannerListener mBannerAdListener = new NGABannerListener() {
		@Override
		public void onRequestAd() {
			MercuryActivity.LogLocal( "["+appShow+"]onRequestAd");
		}

		@Override
		public <T extends NGAdController> void onReadyAd(T controller) {
			mController = (NGABannerController) controller;
			MercuryActivity.LogLocal( "["+appShow+"] mBannerAdListener onReadyAd");
			mBannerView.setVisibility(View.VISIBLE);
			mController.showAd();
		}

		@Override
		public void onShowAd() {
			MercuryActivity.LogLocal( "["+appShow+"]mBannerAdListener onShowAd");
		}

		@Override
		public void onCloseAd() {
			//广告关闭之后mController置null，鼓励加载广告重新调用loadAd，提高广告填充率
			mController = null;
			mBannerView.setVisibility(View.GONE);
			MercuryActivity.LogLocal( "["+appShow+"] mBannerAdListener onCloseAd");
		}

		@Override
		public void onErrorAd(int code, String message) {
			MercuryActivity.LogLocal( "["+appShow+"]onErrorAd errorCode:" + code + ", message:" + message);
		}

		@Override
		public void onClickAd() {
			MercuryActivity.LogLocal( "["+appShow+"]onClickAd");
		}
	};

	public void loadAdVideo(Activity activity) {
		MercuryActivity.LogLocal( "["+appShow+"]loadAdVideo appId="+appId+" videoPosId="+videoPosId);
		final NGAVideoProperties properties = new NGAVideoProperties(activity, appId, videoPosId);
		properties.setListener(mVideoAdListener);
		NGASDK ngasdk = NGASDKFactory.getNGASDK();
		ngasdk.loadAd(properties);
	}
	@TargetApi(Build.VERSION_CODES.M)
	private void loadAdBanner(Activity activity) {
		MercuryActivity.LogLocal( "["+appShow+"]loadAdBanner");
		if (mBannerView != null && mBannerView.getParent() != null) {
			mWindowManager.removeView(mBannerView);
		}
		mBannerView = new RelativeLayout(activity);

		//此代码是为了显示广告区域，游戏请根据游戏主题背景决定是否要添加
		mBannerView.setBackgroundColor(mContext.getColor(android.R.color.darker_gray));

		WindowManager.LayoutParams params = new WindowManager.LayoutParams();
		params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
		params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		params.gravity = Gravity.BOTTOM | Gravity.CENTER;
		params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
		//如果你的应用是竖屏，且隐藏了虚拟按键，请设置为下面的flag
		//params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
		mWindowManager = (WindowManager) activity.getSystemService(WINDOW_SERVICE);
		mWindowManager.addView(mBannerView, params);

		mProperties = new NGABannerProperties(activity, appId, bannerPosId, mBannerView);
		mProperties.setListener(mBannerAdListener);
		NGASDK ngasdk = NGASDKFactory.getNGASDK();
		ngasdk.loadAd(mProperties);

		// 若需要默认横幅广告不展示
		mBannerView.setVisibility(View.VISIBLE);
	}

	private void destroyBannerAd(Activity activity) {
		if (mWindowManager != null) {
			mWindowManager.removeView(mBannerView);
			mWindowManager = null;
		}
		if (mController != null) {
			mController.closeAd();
			mController = null;
		}
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
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
	public void ActiveBanner() {
		showAd(mContext);
	}
	private void showAd(Activity activity) {
		if (mController != null)
		{
			MercuryActivity.LogLocal("["+appShow+"] ActiveBanner setVisibility");
			mController.showAd();
		}
		else
		{
			MercuryActivity.LogLocal("["+appShow+"] ActiveBanner loadAdBanner");
			loadAdBanner(mContext);
		}
		//destroyBannerAd(mContext);删除banner广告
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
		MercuryActivity.LogLocal("["+appShow+"] ActiveRewardVideo mVideoController="+mVideoController);
		if(mVideoController==null)
		{
			loadAdVideo(mContext);
		}
//		boolean hasCacheAd = mVideoController.hasCacheAd();
//		if (hasCacheAd==false)
//		{
//			loadAdVideo(mContext);
//		}
		else
		{
			mVideoController.showAd();
		}
	}
	//end
}

