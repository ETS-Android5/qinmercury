package com.mercury.game.util;

public interface APPBaseInterface {
	abstract void PurchaseSuccessCallBack(String strProductId);
	abstract void PurchaseFailedCallBack(String strProductId);
	abstract void LoginSuccessCallBack(String strProductId);
	abstract void LoginCancelCallBack(String strProductId);
	abstract void AdLoadSuccessCallBack(String strProductId);
	abstract void AdLoadFailedCallBack(String strProductId);
	abstract void AdShowSuccessCallBack(String strProductId);
	abstract void AdShowFailedCallBack(String strProductId);
	abstract void onFunctionCallBack(String strProductId);
}
