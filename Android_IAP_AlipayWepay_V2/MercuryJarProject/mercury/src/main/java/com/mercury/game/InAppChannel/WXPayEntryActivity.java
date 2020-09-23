package com.mercury.game.InAppChannel;import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.mercury.game.MercuryActivity;
import com.mercury.game.util.InAppBase;
import com.mercury.game.util.MercuryConst;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import static com.mercury.game.InAppChannel.InAppChannel.WX_APP_ID;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	private static final String TAG = "WXPayEntryActivity";
    private IWXAPI api;
	InAppBase mInAppBase;
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
				mInAppBase = new InAppBase();
				mInAppBase.onPurchaseSuccess(MercuryConst.QinPid);
				finish();
			}
			else 
			{
				//支付失败
				mInAppBase = new InAppBase();
				mInAppBase.onPurchaseFailed(MercuryConst.QinPid);
				finish();
			}
		}
	}
	@Override
	public void onReq(BaseReq baseReq) {

	}
}