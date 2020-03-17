package com.mercury.game.InAppChannel;

public interface APPBaseInterface {
	abstract void onPurchaseSuccessCallBack(String strProductId);
	abstract void onPurchaseFailedCallBack(String strProductId);
	abstract void onPurchaseCancelCallBack(String strProductId);
	abstract void onLoginSuccessCallBack(String strProductId);
	abstract void onLoginFailedCallBack(String strProductId);
	abstract void onLoginCancelCallBack(String strProductId);
	
	abstract void onLoadSuccessfulCallBack(String strProductId);
	abstract void onLoadFailedCallBack(String strProductId);
	
	abstract void onSaveSuccessfulCallBack(String strProductId);
	abstract void onSaveFailedCallBack(String strProductId);
	
	abstract void onOnVideoCompletedCallBack(String strProductId);
	abstract void onOnVideoFailedCallBack(String strProductId);
	
	abstract void onFunctionCallBack(String strProductId);
}
