package com.mercury.game.InAppChannel;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Application;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;

import com.mercury.game.MercuryActivity;
import com.mercury.game.util.APPBaseInterface;
import com.mercury.game.util.InAppBase;
import com.mercury.game.util.MercuryConst;

import com.youloft.api.ApiManager;
import com.youloft.api.callback.NetCallBack;
import com.youloft.api.callback.PayCallBack;

public class InAppChannel extends InAppBase {
	
	//comment
	private String Channelname="InAppWanNianLi";
	private static String pid;
	@Override
	public void ActivityInit(Activity context, final APPBaseInterface appinterface)
	{		
		super.ActivityInit(context, appinterface);
		MercuryActivity.LogLocal("["+Channelname+"]->init:InAppChannel.init="+context+"  MercuryActivity.DeviceId="+MercuryActivity.DeviceId);
		ApiManager.init(context, "80388d1f-8901-414c-b7f8-62d125e60200", "12345678123456781234567812345678", true);
		ApiManager.userLogin(MercuryActivity.DeviceId, 0, MercuryActivity.DeviceId, "", new NetCallBack() {
		@Override
		public void success(String s) {
			//返回的数据示例
			MercuryActivity.LogLocal("["+Channelname+"]->init:InAppChannel.ApplicationInit="+s);
		}

		@Override
		public void failed(String s) {
			//返回的错误信息
		}
	});
	}
	public void ApplicationInit(Application appcontext)
	{
		mAppContext=appcontext;
		MercuryActivity.LogLocal("["+Channelname+"]->init:InAppChannel.ApplicationInit="+appcontext);
	}

	public static String[] convertStrToArray(String str,String symbol){
		String[] strArray = null;
		strArray = str.split(symbol); //拆分字符为symbol 可以是 "," ,然后把结果交给数组strArray
		return strArray;
	}
	@Override
	public void Purchase(final String strProductId)
	{
		pid = strProductId;
		MercuryConst.PayInfo(strProductId);
		MercuryActivity.LogLocal("[InAppChannel][Purchase] MercuryConst.QinPid="+MercuryConst.QinPid);
		MercuryActivity.LogLocal("[InAppChannel][Purchase] MercuryConst.Qindesc="+MercuryConst.Qindesc);
		MercuryActivity.LogLocal("[InAppChannel][Purchase] MercuryConst.Qinpricefloat="+MercuryConst.Qinpricefloat);
//		ApiManager.userOrder("9066649d-4222-487a-82cf-da1d84582992", new NetCallBack() {
//			@Override
//			public void success(String s) {
//				onPurchaseSuccess(pid);
//			}
//
//			@Override
//			public void failed(String s) {
//				onPurchaseFailed(pid);
//			}
//		});

		ApiManager.userOrder("9066649d-4222-487a-82cf-da1d84582992", new NetCallBack() {
			@Override
			public void success(String s) {
				MercuryActivity.LogLocal("[InAppChannel][Purchase] success");
			}

			@Override
			public void failed(String s) {
				MercuryActivity.LogLocal("[InAppChannel][Purchase] success");
			}
		}, new PayCallBack() {
			@Override
			public void success(String s) {
				MercuryActivity.LogLocal("[InAppChannel][Purchase] success");
				onPurchaseSuccess(pid);
			}

			@Override
			public void failed(String s) {
				MercuryActivity.LogLocal("[InAppChannel][Purchase] success");
				onPurchaseFailed(pid);
			}
		});
//		TestPay();

	}
	@Override
	public void ExitGame()
	{
		try {
			AlertDialog.Builder builder = new Builder(mContext);
			builder.setMessage("Testing Mode");
			builder.setTitle("Choice Result");
			builder.setPositiveButton("Success", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

					((Activity) MercuryActivity.mContext).finish();
					android.os.Process.killProcess(android.os.Process.myPid());
				}
			});

			builder.setNegativeButton("Dismiss", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.create().show();
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
	public void TestPay()
	{
		try {
			AlertDialog.Builder builder = new Builder(mContext);
			builder.setMessage("Testing Mode");
			builder.setTitle("Choice Result");
			builder.setPositiveButton("Success", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					onPurchaseSuccess(pid);
				}
			});
			builder.setNeutralButton("Failed", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					onPurchaseFailed(pid);
				}
			});
			builder.setNegativeButton("Dismiss", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					onPurchaseFailed(pid);
					dialog.dismiss();
				}
			});
			builder.create().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onPause()
	{
		MercuryActivity.LogLocal("["+Channelname+"] onPause");
	}
	
	@Override
	public void onResume()
	{
		MercuryActivity.LogLocal("["+Channelname+"] onResume");
	}
	@Override
	public void onDestroy()
	{
		MercuryActivity.LogLocal("["+Channelname+"] onDestroy");
	}
	@Override
	public void onStop() 
	{
		MercuryActivity.LogLocal("["+Channelname+"] onStop");
	}
	@Override
	public void onStart() 
	{
		MercuryActivity.LogLocal("["+Channelname+"] onStart");
	}
	@Override
	public void onRestart()
	{
		MercuryActivity.LogLocal("["+Channelname+"] onRestart");
		
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		MercuryActivity.LogLocal("["+Channelname+"] onActivityResult(int requestCode, int resultCode, Intent data)");
	}
	@Override
	public void onNewIntent(Intent intent) 
	{
		MercuryActivity.LogLocal("["+Channelname+"] onNewIntent(Intent intent) ");
	}
	



	//end
}
