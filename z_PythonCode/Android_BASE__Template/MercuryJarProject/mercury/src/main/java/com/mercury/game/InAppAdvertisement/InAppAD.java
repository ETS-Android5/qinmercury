package com.mercury.game.InAppAdvertisement;
import com.mercury.game.InAppRemote.ADConfig;
import com.mercury.game.MercuryApplication;
import com.mercury.game.util.APPBaseInterface;
import com.mercury.game.util.InAppBase;
import com.mercury.game.MercuryActivity;
//comment
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Looper;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.mercury.game.MercuryActivity.DeviceId;
import static com.mercury.game.MercuryActivity.GameName;
import static com.mercury.game.MercuryApplication.channelname;
//end


public class InAppAD extends InAppBase {
	//comment
	public static String appShow="InAppAD";
	public static String MyScence = "";
	public static boolean isAdAccessable = true;
	public void ActivityInit(Activity context,final APPBaseInterface appcall)
	{
		super.ActivityInit(context, appcall);
		MercuryActivity.LogLocal("[InAppAD][ActivityInit]="+appShow);
	}
	@Override
	public void ApplicationInit(Application app)
	{
		MercuryActivity.LogLocal("[InAppAD][ApplicationInit]="+appShow);
	}

	@Override
	public void onPause()
	{
		MercuryActivity.LogLocal("["+appShow+"][onPause]");
	}


	@Override
	public void onResume()
	{
		MercuryActivity.LogLocal("["+appShow+"][onResume]");
	}

	@Override
	public void onDestroy()
	{
		MercuryActivity.LogLocal("["+appShow+"][onDestroy]");
	}
	@Override
	public void onStop()
	{
		MercuryActivity.LogLocal("["+appShow+"][onStop]");
	}
	@Override
	public void onStart()
	{
		MercuryActivity.LogLocal("["+appShow+"][onStart]");
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
		ADConfig.isAdAccessable(
				GameName, new Callback() {
					@Override
					public void onFailure(Call call, IOException e) {
						onFailureAction("无法获取到该游戏的广告权限");
					}
					@Override
					public void onResponse(Call call, Response response) throws IOException {
						onActiveAction(response,"ActiveInterstitial");
					}
				});
	}
	public void ActiveBanner() {
		ADConfig.isAdAccessable(
				GameName, new Callback() {
					@Override
					public void onFailure(Call call, IOException e) {
						onFailureAction("无法获取到该游戏的广告权限");
					}
					@Override
					public void onResponse(Call call, Response response) throws IOException {
						onActiveAction(response,"ActiveBanner");
					}
				}
				);
	}
	public void ActiveNative() {
		ADConfig.isAdAccessable(
				GameName, new Callback() {
					@Override
					public void onFailure(Call call, IOException e) {
						onFailureAction("无法获取到该游戏的广告权限");
					}
					@Override
					public void onResponse(Call call, Response response) throws IOException {
						onActiveAction(response,"ActiveNative");
					}
				}
		);
	}
	public void ActiveRewardVideo() {
		ADConfig.isAdAccessable(
				GameName, new Callback() {
					@Override
					public void onFailure(Call call, IOException e) {
						onFailureAction("ActiveNative");
					}
					@Override
					public void onResponse(Call call, Response response) throws IOException {
						onActiveAction(response,"ActiveRewardVideo");
					}
				}
		);
	}
	//end

	public void onFailureAction(String msg) {
		Looper.prepare();
		Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
		AdShowFailedCallBack("ActiveNative");
		Looper.loop();
	}

	//接入sdk的时候根据传入的接口名作不同的处理
	public void onActiveAction(Response response, String interfaceName) throws IOException{
		String s = response.body().string();
		Looper.prepare();
		if (s != null) {
			getAdAccessable(s,interfaceName);
			alertADDialogShow(interfaceName,"Testing Mode",interfaceName);
		}else {
			Toast.makeText(mContext, "无法获取到该游戏的广告权限",Toast.LENGTH_SHORT).show();
			AdShowFailedCallBack("ActiveNative");
		}
		Looper.loop();
	}

	public void alertADDialogShow(String title, String mode, final String interfaceName){
		MercuryActivity.LogLocal("["+appShow+"]" + interfaceName);
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setMessage(mode);
		builder.setTitle(title);
		builder.setPositiveButton("Success", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				AdShowSuccessCallBack(interfaceName);
				AdLoadSuccessCallBack(interfaceName);
			}
		});
		builder.setNeutralButton("Failed", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				AdShowFailedCallBack(interfaceName);
			}
		});
		builder.setCancelable(false);
		builder.create().show();
	}

	public void getAdAccessable(String s,String interfaceName){
		JSONObject json = null;
		try {
			json = (JSONObject) new JSONTokener(s).nextValue();
			JSONObject data = json.getJSONObject("data");
			if (data.isNull("isAdAccessable")) {
				JSONObject result = data.getJSONObject("result");
				isAdAccessable = result.isNull(channelname) ? true : result.getJSONObject(channelname).getBoolean("isAdAccessable");
			} else {
				isAdAccessable = data.getBoolean("isAdAccessable");
			}
			if (!isAdAccessable) {
				Toast.makeText(mContext, "无法获取到该游戏的广告权限", Toast.LENGTH_SHORT).show();
				AdShowFailedCallBack(interfaceName);
				return;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}

