package com.mercury.game.InAppChannel;
import com.mercury.game.util.APPBaseInterface;
import com.mercury.game.util.InAppBase;

//comment
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.mercury.game.MercuryActivity;
import com.mercury.game.util.MercuryConst;
import com.mercury.game.MercuryApplication;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import static com.mercury.game.MercuryActivity.DeviceId;
import static com.mercury.game.MercuryActivity.LogLocal;
import static com.mercury.game.MercuryApplication.channelname;
import static com.mercury.game.util.Function.verifyGame;
import static com.mercury.game.util.MercuryConst.GlobalProductionList;
//end


public class InAppChannel extends InAppBase {
	
	//comment
	private String Channelname="InAppChannel";
	private static String pid;
	@Override
	public void ActivityInit(Activity context, final APPBaseInterface appinterface)
	{		
		super.ActivityInit(context, appinterface);
		verifyGame("ww2");
		MercuryActivity.LogLocal("["+Channelname+"]->init:InAppChannel.init="+context);
		Toast.makeText(mContext, "只限于"+channelname+"测试，请勿泄漏", Toast.LENGTH_SHORT).show();
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
		TestPay();

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
					onPurchaseSuccess(MercuryConst.QinPid);
				}
			});
			builder.setNeutralButton("Failed", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					onPurchaseFailed(MercuryConst.QinPid);
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
	public void SingmaanLogin()
	{
		LogLocal("[MercuryActivity][SingmaanLogin]"+DeviceId);
		LoginSuccessCallBack("SingmaanLogin"+DeviceId);
	}
	public void SingmaanLogout()
	{
		LogLocal("[MercuryActivity][SingmaanLogout]"+DeviceId);
		LoginCancelCallBack("SingmaanLogout"+DeviceId);
	}

	public void UploadGameData()
	{
		LogLocal("[MercuryActivity][SingmaanLogin]"+DeviceId);
		onFunctionCallBack("SingmaanUploadGameData");
	}
	public void DownloadGameData()
	{
		LogLocal("[MercuryActivity][SingmaanLogout]"+DeviceId);
		onFunctionCallBack("SingmaanUpDownloadGameData");
	}
	public void Redeem()
	{
		int max=GlobalProductionList.length;
		int min=0;
		Random random = new Random();
		int s = random.nextInt(max)%(max-min+1) + min;
		onPurchaseSuccess(GlobalProductionList[s][0]);
	}
	public void RateGame()
	{
		LogLocal("[InAppChannel][RateGame]");
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		Uri content_url = Uri.parse("https://m.3839.com/a/121237.htm");//此处填链接
		intent.setData(content_url);
		MercuryActivity.mContext.startActivity(intent);
	}
	public void ShareGame()
	{
		LogLocal("[InAppChannel][ShareGame]");
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		Uri content_url = Uri.parse("https://bbs.3839.com/forum-15108.htm");//此处填链接
		intent.setData(content_url);
		MercuryActivity.mContext.startActivity(intent);
	}
	public void OpenGameCommunity()
	{
		LogLocal("[InAppChannel][OpenGameCommunity]");
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		Uri content_url = Uri.parse("https://m.3839.com/a/121237.htm");//此处填链接
		intent.setData(content_url);
		MercuryActivity.mContext.startActivity(intent);
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
