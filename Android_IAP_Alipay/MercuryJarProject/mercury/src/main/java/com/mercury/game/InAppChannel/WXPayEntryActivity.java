package com.mercury.game.InAppChannel;

//import com.east2west.game.E2WApp;
//import com.east2west.game.inApp.InAppBase;
//import com.east2west.game.inApp.InAppEAST2WEST;
//import com.tencent.mm.sdk.constants.ConstantsAPI;
//import com.tencent.mm.sdk.modelbase.BaseReq;
//import com.tencent.mm.sdk.modelbase.BaseResp;
//import com.tencent.mm.sdk.openapi.IWXAPI;
//import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
//import com.tencent.mm.sdk.openapi.WXAPIFactory;
//import com.unity3d.player.UnityPlayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mercury.game.MercuryActivity;
import com.mercury.game.util.MercuryConst;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import static com.mercury.game.InAppChannel.InAppChannel.WX_APP_ID;
import static com.mercury.game.MercuryActivity.mInAppBase;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	private static final String TAG = "WXPayEntryActivity";
    private IWXAPI api;
    public String channelname="[com.ironhidegames.android.ironmarines.e2w.wxapi]";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);      
    	api = WXAPIFactory.createWXAPI(this, WX_APP_ID);
        api.handleIntent(getIntent(), this);
		MercuryActivity.LogLocal("[WXPayEntryActivity] onCreate->"+WX_APP_ID);

    }
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
		MercuryActivity.LogLocal("[WXPayEntryActivity]->onNewIntent");
	}
	@Override
	public void onResp(BaseResp baseResp) {
		MercuryActivity.LogLocal("[WXPayEntryActivity][onResp] errCode = " + baseResp.errCode);
		if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			if (baseResp.errCode == 0) {
				//支付成功
				Toast.makeText(this, "支付成功", Toast.LENGTH_LONG).show();
				mInAppBase.onPurchaseSuccess(MercuryConst.QinPid);
				finish();
			}
			else 
			{
				//支付失败
				Toast.makeText(this, "支付失败", Toast.LENGTH_LONG).show();
				mInAppBase.onPurchaseFailed(channelname);
				finish();
			}
		}
	}
	@Override
	public void onReq(BaseReq baseReq) {

	}
}