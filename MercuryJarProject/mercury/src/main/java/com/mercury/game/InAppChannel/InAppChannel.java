package com.mercury.game.InAppChannel;
import com.mercury.game.InAppChannel.APPBaseInterface;
import com.mercury.game.InAppChannel.InAppBase;

//comment
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.RemoteException;
import android.util.Log;

import com.mercury.game.MercuryActivity;
import com.mercury.game.MercuryConst;
import com.mercury.game.MercuryApplication;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;
//end


public class InAppChannel extends InAppBase {
	
	//comment
	private InAppBase mBaseInApp = null;
	private String Channelname="InAppChannel";
	private String mUid;
	private String mSession;
	private int mAccountType;
	private String demovalue1;
	@Override
	public void init(Context appContext, Activity context, final String strAppId, final String strAppSecret, APPBaseInterface appinterface)
	{		
		super.init(appContext, context, strAppId, strAppSecret,appinterface);
		MercuryActivity.LogLocal("["+Channelname+"] strAppKey="+strAppId);
		MercuryActivity.LogLocal("["+Channelname+"] strAppSecret="+strAppSecret);
	}
	public void ApplicationInit(Application appcontext)
	{
		mAppContext=appcontext;
		MercuryActivity.LogLocal("["+Channelname+"]->init:InAppBase.ApplicationInit="+appcontext);
	}
	public void AnalysisID(String IDString)
	{
		try
		{
			String[] strArray=null;
			strArray = convertStrToArray(IDString,",");
			demovalue1=strArray[0].toString();
			MercuryActivity.LogLocal("["+Channelname+"] demovalue1="+demovalue1);
		}
		catch(Exception E)
		{
			MercuryActivity.LogLocal("[AnalysisID]Error="+E);
		}
	}
	public static String[] convertStrToArray(String str,String symbol){
		String[] strArray = null;
		strArray = str.split(symbol); //拆分字符为symbol 可以是 "," ,然后把结果交给数组strArray
		return strArray;
	}
	@Override
	public void purchase(final String strProductId, final String strProductDescription, final float fPrice)
	{
		super.purchase(strProductId, strProductDescription, fPrice);
		Log.e(MercuryConst.TAG,"purchase");
	}
	@Override
	public void ExitGame()
	{

		((Activity) MercuryActivity.mContext).finish();
		android.os.Process.killProcess(android.os.Process.myPid());

	}
	public void CarriersPay()
	{
		Log.e(MercuryConst.TAG,"CarriersPay=");
	}
	public void ChannelPay()
	{
		Log.e(MercuryConst.TAG,"ChannelPay="+mProductId);
	}
	public void DoublePay()
	{
		try {
			AlertDialog.Builder builder = new Builder(mContext);
			builder.setMessage("选择支付方式");
			builder.setTitle("提示");
			builder.setPositiveButton("小米支付", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					ChannelPay();
				}
			});
			builder.setNeutralButton("短信支付", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					CarriersPay();
				}
			});
			builder.setNegativeButton("取消", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					onPurchaseFailed(Channelname);
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
