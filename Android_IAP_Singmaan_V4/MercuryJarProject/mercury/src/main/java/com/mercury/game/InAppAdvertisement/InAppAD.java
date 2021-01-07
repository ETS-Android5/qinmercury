package com.mercury.game.InAppAdvertisement;
import com.mercury.game.InAppRemote.ADConfig;
import com.mercury.game.MercuryApplication;
import com.mercury.game.util.APPBaseInterface;
import com.mercury.game.util.InAppBase;
import com.mercury.game.MercuryActivity;
import com.mercury.game.util.NetCheckUtil;
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
import static com.mercury.game.MercuryActivity.LogLocal;
import static com.mercury.game.MercuryApplication.channelname;
import static com.mercury.game.util.UIUtils.isJSONValid;
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
		ADConfig.isAdAccessable(
				GameName, new Callback() {
					@Override
					public void onFailure(Call call, IOException e)
					{

					}
					@Override
					public void onResponse(Call call, Response response) throws IOException
					{
						//这里更新SDK
					}
				}
		);
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
		MercuryActivity.LogLocal("["+appShow+"] ActiveInterstitial");
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setMessage("Testing Mode");
		builder.setTitle("ActiveInterstitial");
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
		builder.setCancelable(false);
		builder.create().show();
	}
	public void ActiveBanner() {
		MercuryActivity.LogLocal("["+appShow+"] ActiveBanner");
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setMessage("Testing Mode");
		builder.setTitle("ActiveBanner");
		builder.setPositiveButton("Success", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				AdShowSuccessCallBack("ActiveBanner");
				AdLoadSuccessCallBack("ActiveBanner");
			}
		});
		builder.setNeutralButton("Failed", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				AdShowFailedCallBack("ActiveBanner");
			}
		});
		builder.setCancelable(false);
		builder.create().show();
	}
	public void ActiveNative() {
		MercuryActivity.LogLocal("["+appShow+"] ActiveNative");
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setMessage("Testing Mode");
		builder.setTitle("ActiveNative");
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
		builder.setCancelable(false);
		builder.create().show();
	}
	public void ActiveRewardVideo() {
		MercuryActivity.LogLocal("["+appShow+"] ActiveRewardVideo");
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setMessage("Testing Mode");
		builder.setTitle("ActiveRewardVideo");
		builder.setPositiveButton("Success", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				AdShowSuccessCallBack("ActiveRewardVideo");
				AdLoadSuccessCallBack("ActiveRewardVideo");
			}
		});
		builder.setNeutralButton("Failed", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				AdShowFailedCallBack("ActiveRewardVideo");
			}
		});
		builder.setCancelable(false);
		builder.create().show();
	}
	//end

	public void onFailureAction(String msg) {
//		Looper.prepare();
//		if(!NetCheckUtil.checkNet(mContext)){
//			Toast.makeText(mContext, "网络未连接", Toast.LENGTH_SHORT).show();
//		}else {
//			Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
//		}
//		AdLoadFailedCallBack(msg);
//		Looper.loop();
	}

	//接入sdk的时候根据传入的接口名作不同的处理
	public void onActiveAction(Response response, String interfaceName) throws IOException{
		String s = response.body().string();
		Looper.prepare();
		if (s != null) {
			getAdAccessable(s,interfaceName);
			if (!isAdAccessable) {
				Toast.makeText(mContext, "无法获取到该游戏的广告权限", Toast.LENGTH_SHORT).show();
				AdShowFailedCallBack(interfaceName);
				return;
			}
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

	public void getAdAccessable(String s,String interfaceName) {
		JSONObject json = null;
		if (s != null && isJSONValid(s)) {
			try {
				json = (JSONObject) new JSONTokener(s).nextValue();
				JSONObject data = json.getJSONObject("data");
				if (data.isNull("isAdAccessable")) {
					JSONObject result = data.getJSONObject("result");
					isAdAccessable = result.isNull(channelname) ? true : result.getJSONObject(channelname).getBoolean("isAdAccessable");
				} else {
					isAdAccessable = data.getBoolean("isAdAccessable");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			LogLocal("[RemoteConfig][verify_signe_in] server returned formate is not a json");
		}
	}

}

