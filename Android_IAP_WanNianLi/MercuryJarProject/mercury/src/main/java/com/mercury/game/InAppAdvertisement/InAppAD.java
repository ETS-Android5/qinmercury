package com.mercury.game.InAppAdvertisement;
import com.mercury.game.MercuryApplication;
import com.mercury.game.util.APPBaseInterface;
import com.mercury.game.util.InAppBase;
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
	public void ActivityInit(Activity context,final APPBaseInterface appcall)
	{
		super.ActivityInit(context, appcall);
		MercuryActivity.LogLocal("["+appShow+"]->ActivityInit");

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
	public void show_insert(String Scenes) {
		// TODO Auto-generated method stub
		MercuryActivity.LogLocal("["+appShow+"] show_insert");

	}
	public void show_banner(String Scenes) {
		// TODO Auto-generated method stub
		MercuryActivity.LogLocal("["+appShow+"] show_banner");
	}
	public void show_push(String Scenes) {
		// TODO Auto-generated method stub
		MercuryActivity.LogLocal("["+appShow+"] show_push");
	}

	public void show_out(String Scenes) {
		// TODO Auto-generated method stub
		MercuryActivity.LogLocal("["+appShow+"] show_out");
	}
	public void show_video(String Scenes) {
		// TODO Auto-generated method stub
		MercuryActivity.LogLocal("["+appShow+"] show_video");
	}
	//end
}

