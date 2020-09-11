package com.mercury.game.InAppChannel;
//import com.east2west.game.E2WApp;
//import com.east2west.game.QinConst;
//import com.east2west.game.inApp.InAppEAST2WEST;
//import com.tencent.mm.sdk.constants.ConstantsAPI;
//import com.tencent.mm.sdk.modelbase.BaseReq;
//import com.tencent.mm.sdk.modelbase.BaseResp;
//import com.tencent.mm.sdk.modelmsg.ShowMessageFromWX;
//import com.tencent.mm.sdk.openapi.IWXAPI;
//import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
//
//import com.tencent.mm.sdk.openapi.WXAPIFactory;
//import com.unity3d.player.UnityPlayer;
//import com.unity3d.player.UnityPlayerActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mercury.game.MercuryActivity;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import static com.mercury.game.InAppChannel.InAppChannel.WXShareID;
import static com.mercury.game.MercuryActivity.mInAppBase;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
	private IWXAPI api;
	private static final String APP_ID = "wxc09894676cfb0f15";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.entry);
		api = WXAPIFactory.createWXAPI(this, WXShareID, false);
		api.registerApp(WXShareID);
		api.handleIntent(getIntent(), this);
		MercuryActivity.LogLocal("[WXPayEntryActivity] onCreate->"+WXShareID);
		//finish();
	}
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	public void onReq(BaseReq req) {
		switch (req.getType()) {
			case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
				//goToGetMsg();
				break;
			case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
				break;
			default:
				break;
		}
	}

	public void onResp(BaseResp resp) {
		int result = 0;
		switch (resp.errCode) {
			case BaseResp.ErrCode.ERR_OK:
				mInAppBase.onFunctionCallBack("Share:0");
				finish();
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				mInAppBase.onFunctionCallBack("Share:1");
				finish();
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				mInAppBase.onFunctionCallBack("Share:3");
				finish();
				break;
			default:
				mInAppBase.onFunctionCallBack("Share:4");
				finish();
				break;
		}
	}
}