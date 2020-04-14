package com.mercury.game.InAppAdvertisement;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConfig;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;
import com.mercury.game.MercuryApplication;
import com.mercury.game.util.APPBaseInterface;
import com.mercury.game.util.InAppBase;
import com.mercury.game.MercuryActivity;
//comment
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import java.util.List;
//end

public class InAppAD extends InAppBase {
	//comment
	public static String appShow="InAppADCSJ";
	public static String MyScence = "";
	private TTAdNative mTTAdNative;
	private TTRewardVideoAd mttRewardVideoAd;

	private TTNativeExpressAd mTTAd;

	private boolean mIsExpress = false; //是否请求模板广告
	private boolean mHasShowDownloadActive = false;
	private static String  ad_appid = "5059494";
	private static String  game_name = "砖块弹弹弹";
	private static String video_position_id = "945132328";
	private static String insert_position_id = "";
	private long startTime = 0;
	public void ActivityInit(Activity context,final APPBaseInterface appcall)
	{
		super.ActivityInit(context, appcall);
		MercuryActivity.LogLocal("["+appShow+"]->ActivityInit video_position_id 1.2="+video_position_id);
		TTAdManager ttAdManager = TTAdSdk.getAdManager();
		ttAdManager.requestPermissionIfNecessary(mContext);
		mTTAdNative = ttAdManager.createAdNative(mContext.getApplicationContext());

		if(video_position_id.equals("")==false){
			loadAd(video_position_id,TTAdConstant.HORIZONTAL);
		}

	}
	@Override
	public void ApplicationInit(Application app)
	{
		MercuryActivity.LogLocal("["+appShow+"]->ApplicationInit ad_appid="+ad_appid);
		TTAdSdk.init(app,
				new TTAdConfig.Builder()
						.appId(ad_appid)
						.useTextureView(false) //使用TextureView控件播放视频,默认为SurfaceView,当有SurfaceView冲突的场景，可以使用TextureView
						.appName(game_name)
						.titleBarTheme(TTAdConstant.TITLE_BAR_THEME_DARK)
						.allowShowNotify(true) //是否允许sdk展示通知栏提示
						.allowShowPageWhenScreenLock(true) //是否在锁屏场景支持展示广告落地页
						.debug(true) //测试阶段打开，可以通过日志排查问题，上线时去除该调用
						.directDownloadNetworkType(TTAdConstant.NETWORK_STATE_WIFI, TTAdConstant.NETWORK_STATE_4G) //允许直接下载的网络状态集合
						.supportMultiProcess(false) //是否支持多进程，true支持
						//.httpStack(new MyOkStack3())//自定义网络库，demo中给出了okhttp3版本的样例，其余请自行开发或者咨询工作人员。
						.build());

	}
	private void loadAd(final String codeId, int orientation) {
		//step4:创建广告请求参数AdSlot,具体参数含义参考文档
		AdSlot adSlot;
		//模板广告需要设置期望个性化模板广告的大小,单位dp,代码位是否属于个性化模板广告，请在穿山甲平台查看
		adSlot = new AdSlot.Builder()
				.setCodeId(codeId)
				.setSupportDeepLink(true)
				.setRewardName("金币") //奖励的名称
				.setRewardAmount(3)  //奖励的数量
				//模板广告需要设置期望个性化模板广告的大小,单位dp,激励视频场景，只要设置的值大于0即可
				.setExpressViewAcceptedSize(500,500)
				.setUserID("user123")//用户id,必传参数
				.setMediaExtra("media_extra") //附加参数，可选
				.setOrientation(orientation) //必填参数，期望视频的播放方向：TTAdConstant.HORIZONTAL 或 TTAdConstant.VERTICAL
				.build();
		mTTAdNative.loadRewardVideoAd(adSlot, new TTAdNative.RewardVideoAdListener() {
			@Override
			public void onError(int code, String message) {
				MercuryActivity.LogLocal("onError: " + code + ", " + String.valueOf(message));
			}

			//视频广告加载后，视频资源缓存到本地的回调，在此回调后，播放本地视频，流畅不阻塞。
			@Override
			public void onRewardVideoCached() {
				MercuryActivity.LogLocal("onRewardVideoCached");
			}

			//视频广告的素材加载完毕，比如视频url等，在此回调后，可以播放在线视频，网络不好可能出现加载缓冲，影响体验。
			@Override
			public void onRewardVideoAdLoad(TTRewardVideoAd ad) {
				MercuryActivity.LogLocal("onRewardVideoAdLoad");
				mttRewardVideoAd = ad;
				mttRewardVideoAd.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {

					@Override
					public void onAdShow() {
						loadAd(video_position_id,TTAdConstant.HORIZONTAL);
						MercuryActivity.LogLocal("rewardVideoAd show");
					}

					@Override
					public void onAdVideoBarClick() {
						MercuryActivity.LogLocal( "rewardVideoAd bar click");
					}

					@Override
					public void onAdClose() {
						MercuryActivity.LogLocal("rewardVideoAd close");
					}

					//视频播放完成回调
					@Override
					public void onVideoComplete() {
						MercuryActivity.LogLocal( "rewardVideoAd complete");
					}

					@Override
					public void onVideoError() {
						MercuryActivity.LogLocal("rewardVideoAd error");
					}

					//视频播放完成后，奖励验证回调，rewardVerify：是否有效，rewardAmount：奖励梳理，rewardName：奖励名称
					@Override
					public void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName) {
						MercuryActivity.LogLocal("verify:" + rewardVerify + " amount:" + rewardAmount + " name:" + rewardName);
					}

					@Override
					public void onSkippedVideo() {
						MercuryActivity.LogLocal("rewardVideoAd has onSkippedVideo");
					}
				});
				mttRewardVideoAd.setDownloadListener(new TTAppDownloadListener() {
					@Override
					public void onIdle() {
						mHasShowDownloadActive = false;
					}

					@Override
					public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
						MercuryActivity.LogLocal("onDownloadActive==totalBytes=" + totalBytes + ",currBytes=" + currBytes + ",fileName=" + fileName + ",appName=" + appName);

						if (!mHasShowDownloadActive) {
							mHasShowDownloadActive = true;
							MercuryActivity.LogLocal("下载中，点击下载区域暂停");
						}
					}

					@Override
					public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
						MercuryActivity.LogLocal("onDownloadPaused===totalBytes=" + totalBytes + ",currBytes=" + currBytes + ",fileName=" + fileName + ",appName=" + appName);
					}

					@Override
					public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
						MercuryActivity.LogLocal("onDownloadFailed==totalBytes=" + totalBytes + ",currBytes=" + currBytes + ",fileName=" + fileName + ",appName=" + appName);
					}

					@Override
					public void onDownloadFinished(long totalBytes, String fileName, String appName) {
						MercuryActivity.LogLocal("onDownloadFinished==totalBytes=" + totalBytes + ",fileName=" + fileName + ",appName=" + appName);
					}

					@Override
					public void onInstalled(String fileName, String appName) {
						MercuryActivity.LogLocal("onInstalled==" + ",fileName=" + fileName + ",appName=" + appName);
					}
				});
			}
		});
	}
	private void loadExpressAd(String codeId, int expressViewWidth, int expressViewHeight) {
		//step4:创建广告请求参数AdSlot,具体参数含义参考文档
		AdSlot adSlot = new AdSlot.Builder()
				.setCodeId(codeId) //广告位id
				.setSupportDeepLink(true)
				.setAdCount(1) //请求广告数量为1到3条
				.setExpressViewAcceptedSize(expressViewWidth, expressViewHeight) //期望模板广告view的size,单位dp
				.build();
		//step5:请求广告，对请求回调的广告作渲染处理
		mTTAdNative.loadInteractionExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
			@Override
			public void onError(int code, String message) {
				MercuryActivity.LogLocal("load error");
			}

			@Override
			public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
				if (ads == null || ads.size() == 0) {
					return;
				}
				mTTAd = ads.get(0);
				bindAdListener(mTTAd);
				startTime = System.currentTimeMillis();
				mTTAd.render();
			}
		});
	}
	private void bindAdListener(TTNativeExpressAd ad) {
		ad.setExpressInteractionListener(new TTNativeExpressAd.AdInteractionListener() {
			@Override
			public void onAdDismiss() {
				MercuryActivity.LogLocal("广告关闭");
			}

			@Override
			public void onAdClicked(View view, int type) {
				MercuryActivity.LogLocal("广告被点击");
			}

			@Override
			public void onAdShow(View view, int type) {
				MercuryActivity.LogLocal("广告展示");
			}

			@Override
			public void onRenderFail(View view, String msg, int code) {
				MercuryActivity.LogLocal("render fail:" + (System.currentTimeMillis() - startTime));
			}

			@Override
			public void onRenderSuccess(View view, float width, float height) {
				MercuryActivity.LogLocal("render suc:" + (System.currentTimeMillis() - startTime));
				//返回view的宽高 单位 dp
				mTTAd.showInteractionExpressAd(mContext);

			}
		});

		if (ad.getInteractionType() != TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
			return;
		}
		ad.setDownloadListener(new TTAppDownloadListener() {
			@Override
			public void onIdle() {
				MercuryActivity.LogLocal("点击开始下载");
			}

			@Override
			public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
				if (!mHasShowDownloadActive) {
					mHasShowDownloadActive = true;
					MercuryActivity.LogLocal("下载中，点击暂停");
				}
			}

			@Override
			public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
				MercuryActivity.LogLocal("下载暂停，点击继续");
			}

			@Override
			public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
				MercuryActivity.LogLocal("下载失败，点击重新下载");
			}

			@Override
			public void onInstalled(String fileName, String appName) {
				MercuryActivity.LogLocal("安装完成，点击图片打开");
			}

			@Override
			public void onDownloadFinished(long totalBytes, String fileName, String appName) {
				MercuryActivity.LogLocal("点击安装");
			}
		});
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
		if (mTTAd != null) {
			mTTAd.destroy();
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
	public void onActivityResult(int reqCode, int resCode, Intent data) {
		MercuryActivity.LogLocal("["+appShow+"]->onActivityResult");
		super.onActivityResult(reqCode, resCode, data);
	}

	@Override
	public void attachBaseContext(Context base)
	{
		super.attachBaseContext(base);
	}
	public void show_insert() {
		// TODO Auto-generated method stub
		MercuryActivity.LogLocal("["+appShow+"] show_insert");
		if(insert_position_id.equals("")==false){
			loadExpressAd(insert_position_id,400,400);
		}
	}
	public void show_banner() {
		// TODO Auto-generated method stub
		MercuryActivity.LogLocal("["+appShow+"] show_banner");
	}
	public void show_push() {
		// TODO Auto-generated method stub
		MercuryActivity.LogLocal("["+appShow+"] show_push");
	}

	public void show_out() {
		// TODO Auto-generated method stub
		MercuryActivity.LogLocal("["+appShow+"] show_out");
	}
	public void show_video() {
		// TODO Auto-generated method stub
		MercuryActivity.LogLocal("[" + appShow + "] show_video");
		if (mttRewardVideoAd != null) {
			MercuryActivity.LogLocal("[" + appShow + "] mttRewardVideoAd");
			mttRewardVideoAd.showRewardVideoAd(mContext);
//			mttRewardVideoAd.showRewardVideoAd(mContext, TTAdConstant.RitScenes.CUSTOMIZE_SCENES, "scenes_test");
			mttRewardVideoAd = null;
		} else {
			MercuryActivity.LogLocal("请先加载广告");
		}
	}
	//end
}

