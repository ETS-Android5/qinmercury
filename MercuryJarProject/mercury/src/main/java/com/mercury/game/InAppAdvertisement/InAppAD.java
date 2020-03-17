package com.mercury.game.InAppAdvertisement;
import com.mercury.game.MercuryApplication;
import com.mercury.game.InAppChannel.APPBaseInterface;
import com.mercury.game.InAppChannel.InAppBase;
import com.mercury.game.MercuryActivity;
//comment
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
//end

public class InAppAD extends InAppBase {
	//comment
	public static String appShow="InAppAD";
	public static String MyScence = "";
	public void init(Context appContext, Activity context, final String strAppId, final String strAppKey,final APPBaseInterface appcall)
	{
		super.init(appContext, context, strAppId, strAppKey, appcall);
//		MercuryActivity.LogLocal.LogLocal("[InAppShow"+appShow+"] init");
		//String s = QinConst.E2WADID;
	}
	@Override
	public void ApplicationInit(Application app)
	{
		MercuryActivity.LogLocal("[InAppShow"+appShow+"]->ApplicationInit="+app);
	}

	@Override
	public void onPause()
	{
	}

	@Override
	public void onResume()
	{
	}

	@Override
	public void onDestroy()
	{
	}
	@Override
	public void onStop()
	{
	}
	@Override
	public void onStart()
	{
	}
	public void onActivityResult(int reqCode, int resCode, Intent data) {
		super.onActivityResult(reqCode, resCode, data);
	}

	@Override
	public void attachBaseContext(Context base)
	{
		super.attachBaseContext(base);
	}
	public void show_insert(String Scenes) {
		// TODO Auto-generated method stub
		MyScence=Scenes;
		MercuryActivity.LogLocal("[InAppShow"+appShow+"] show_insert");
//		SdkApplication.myStatistics.DisplayAD(Scenes,"insert");

//		SdkApplication.myStatistics.HitAD(Scenes,"insert");
	}
	public void show_banner(String Scenes) {
		// TODO Auto-generated method stub
		MercuryActivity.LogLocal("[InAppShow"+appShow+"] show_banner");
		MyScence=Scenes;
//		SdkApplication.myStatistics.DisplayAD(Scenes,"banner");
//
//		SdkApplication.myStatistics.HitAD(Scenes,"banner");
	}
	public void show_push(String Scenes) {
		// TODO Auto-generated method stub
		MercuryActivity.LogLocal("[InAppShow"+appShow+"] show_push");
		MyScence=Scenes;
//		SdkApplication.myStatistics.DisplayAD(Scenes,"push");
//
//		SdkApplication.myStatistics.HitAD(Scenes,"push");
	}

	public void show_out(String Scenes) {
		// TODO Auto-generated method stub
		MercuryActivity.LogLocal("[InAppShow"+appShow+"] show_out");
		MyScence=Scenes;
//		SdkApplication.myStatistics.DisplayAD(Scenes,"out");
//
//		SdkApplication.myStatistics.HitAD(Scenes,"out");
	}
	public void show_video(String Scenes) {
		// TODO Auto-generated method stub
		MercuryActivity.LogLocal("[InAppShow"+appShow+"] show_video");
		MyScence=Scenes;
//		SdkApplication.myStatistics.DisplayAD(Scenes,"video");
//
//		SdkApplication.myStatistics.HitAD(Scenes,"video");
	}
	//end
}

