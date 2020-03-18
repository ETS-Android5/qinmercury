package com.singmaan.game;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mercury.game.MercuryActivity;
import com.mercury.game.util.APPBaseInterface;

import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

public class GameActivity extends UnityPlayerActivity{
	public static GameActivity mContext = null;
	public MercuryActivity MercurySDK;
	public static APPBaseInterface appcall=null;
	private String callBackObjectName="PluginSingmaan";
	private static String tagname="SingmaanUnity";
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		mContext=this;
		MercurySDK=new MercuryActivity();
		appcall = new  APPBaseInterface() {

			@Override
			public void onPurchaseSuccessCallBack(String strProductId) {
				// TODO Auto-generated method stub
				Log.w(tagname, "onCreate onPurchaseSuccessCallBack strProductId="+strProductId);
				Toast.makeText(mContext, "onPurchaseSuccessCallBack",Toast.LENGTH_SHORT).show();
				UnityPlayer.UnitySendMessage(callBackObjectName, "OnPurchaseSuccess", strProductId);
			}

			@Override
			public void onPurchaseFailedCallBack(String strProductId) {
				// TODO Auto-generated method stub
				Log.w(tagname, "onCreate onPurchaseFailedCallBack strProductId="+strProductId);
				Toast.makeText(mContext, "onPurchaseFailedCallBack",Toast.LENGTH_SHORT).show();
				UnityPlayer.UnitySendMessage(callBackObjectName, "OnPurchaseSuccess", strProductId);
			}

			@Override
			public void onPurchaseCancelCallBack(String strProductId) {
				// TODO Auto-generated method stub
				Log.w(tagname, "onCreate onPurchaseCancelCallBack strProductId="+strProductId);
				Toast.makeText(mContext, "onPurchaseCancelCallBack",Toast.LENGTH_SHORT).show();
				UnityPlayer.UnitySendMessage(callBackObjectName, "OnPurchaseSuccess", strProductId);
			}

			@Override
			public void onLoginCancelCallBack(String arg0) {
				// TODO Auto-generated method stub
				Log.w(tagname, "onCreate onLoginCancelCallBack arg0="+arg0);
			}

			@Override
			public void onLoadSuccessfulCallBack(String s) {
				Log.w(tagname, "onCreate onLoginCancelCallBack arg0="+s);
			}

			@Override
			public void onLoadFailedCallBack(String s) {
				Log.w(tagname, "onCreate onLoginCancelCallBack arg0="+s);
			}

			@Override
			public void onSaveSuccessfulCallBack(String s) {
				Log.w(tagname, "onCreate onLoginCancelCallBack arg0="+s);
			}

			@Override
			public void onSaveFailedCallBack(String s) {
				Log.w(tagname, "onCreate onLoginCancelCallBack arg0="+s);
			}

			@Override
			public void onOnVideoCompletedCallBack(String s) {
				Log.w(tagname, "onCreate onLoginCancelCallBack arg0="+s);
			}

			@Override
			public void onOnVideoFailedCallBack(String s) {
				Log.w(tagname, "onCreate onLoginCancelCallBack arg0="+s);
			}

			@Override
			public void onLoginFailedCallBack(String arg0) {
				// TODO Auto-generated method stub
				Log.w(tagname, "onCreate onLoginFailedCallBack arg0="+arg0);
			}

			@Override
			public void onLoginSuccessCallBack(String arg0) {
				// TODO Auto-generated method stub
				Log.w(tagname, "onCreate onLoginSuccessCallBack arg0="+arg0);
			}

			@Override
			public void onFunctionCallBack(String arg0) {
				// TODO Auto-generated method stub
				Log.w(tagname, "onCreate onFunctionCallBack arg0="+arg0);
				if(arg0.equals("ExitGame"))
				{
					//exit game by yourself
				}
				else if(arg0.equals("UnlockGame"))
				{
					//unlock game
				}
				else if(arg0.equals("SplashEnd"))
				{
					//splash finished
				}
			}
		};
		Log.w(tagname,"[step][4]Init MercurySDK");
		MercurySDK.InitSDK (mContext,appcall);
	}

	public static Object getInstance() {
		Log.e(tagname,"getInstance=" + mContext);
		return mContext;
	}

	public void PurchaseProduct(final String strProductId)
	{
		UnityPlayer.currentActivity.runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				mContext.runOnUiThread(new Runnable() {
					public void run() {
						Log.e(tagname, "purchaseProduct=="+strProductId);
						MercurySDK.Purchase(strProductId);
					}
				});
			}
		});
	}

	public void show_video()
	{
		UnityPlayer.currentActivity.runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				mContext.runOnUiThread(new Runnable() {
					public void run() {
						Log.e(tagname,"ShowVideoAd");
						MercurySDK.show_video();
					}
				});
			}
		});
	}

	public void Exchange()
	{
		UnityPlayer.currentActivity.runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				mContext.runOnUiThread(new Runnable() {
					public void run() {
						Log.e(tagname,"Exchange");
						MercurySDK.Exchange();
					}
				});
			}
		});
	}

	public void ExitGame()
	{
		UnityPlayer.currentActivity.runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				mContext.runOnUiThread(new Runnable() {
					public void run() {
						Log.e(tagname,"Exit");
						MercurySDK.ExitGame();
					}
				});
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.e(tagname, "onActivityResult(int requestCode, int resultCode, Intent data) ");
		MercurySDK.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		Log.e(tagname, "onNewIntent");
		MercurySDK.onNewIntent(intent);
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d(tagname, "onStart()");
		MercurySDK.onStart();
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(tagname, "onPause()");
		MercurySDK.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d(tagname, "onStop()");
		MercurySDK.onStop();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.d(tagname, "onRestart()");
		MercurySDK.onRestart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(tagname, "onResume()");
		MercurySDK.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(tagname, "onDestroy()");
		MercurySDK.onDestroy();
	}

}

