package com.mercury.game.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.params.CoreConnectionPNames;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.mercury.game.MercuryActivity;
import com.mercury.game.util.MercuryConst;
import com.mercury.game.MercuryApplication;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;

import static com.mercury.game.MercuryActivity.LogLocal;

public class InAppBase{
	protected Activity mContext;
	protected String mProductId;
	protected String mProductDescription;
	protected float mProductPrice;
	protected Context mAppContext;
	protected InAppBase mInstance;
	public static APPBaseInterface appinterface;
	public static boolean sTestMode = false;
	public static MercuryConst qc;
	public static String OrderID="";
	public static String key = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALnMsImln+S8fxJt" +
		 			    		"f7NDqQhh8EA318buO6ScnyzNbaBkVmu4oL97ggRrgmI7z1YKYkPNs6ymufqjHDAA" +
		 			    		"qqyMm+KgNYodKsr+LtWOxwHVYEI7rdZL6ioNoOyv396pGQjoyXDB6FfP+dGXGN/W" +
		 			    		"MSyJrcXn2tdY025S+dBbQaMTnqhvAgMBAAECgYEAiaTJN//aF1NJdDZofz5lsA8W" +
		 			    		"NAzqrrX4u3dIOKGrUEJk/4KUm6Z86JdYzTtv21bv+zkdnY8agkJp9GnaBuBX7mVE" +
		 			    		"g3tHqzZrOCq5nVX9OHJoMytGwLxlHvejBZPVS1PmkfFnEYRAkBcti5nmsY+fCV5/" +
		 			    		"lxVScrYGdzfrf1vio1ECQQDpxHmicfwYCTb0ZcUIKU0CQvfD9Te/94TIxr82EKcq" +
		 			    		"/pPfoU3vQY+Ks7LI41Zc2kRYhT1dXezzKf/r2FjAMid3AkEAy3hXmEUZYdg+SPVU" +
		 			    		"RzrQt6PGjvLv/5Uxe75t8Ovx2JxHRgCD5j7IArzDg7ACMNn50xMHfQUD4QVdEG2v" +
		 			    		"tvK0yQJBAJp/wiw8zXJNVMa+JDS6pyzhecNHZGs5eccApAtlgjaGPtFEWK/SUr5G" +
		 			    		"+diPd9qyXw1qMh5tH1eu4HfNawrLmw0CQBRZ3hEJ4EcMFPbBKwPQ2y1zARotLFoY" +
		 			    		"9xEUc/Sj9NWgk/Rpesfdwa2cacXTJfTy6Gz3O0mC5eds3OkWv3uB/RkCQQCEkR2M" +
		 			    		"+vdStNq08KV7g+bOZKXElvnYjpUHMdVkO+oeXHPhLf9ltlpBOynA7WA60Jdg0OJa" +
		 			    		"14ngZcu2loawd+q2";

	public void ActivityInit(Activity context, final APPBaseInterface appcall) {
		// TODO Auto-generated method stub
		mContext = context;
		mInstance = this;
		qc = new MercuryConst();
		this.appinterface=appcall;
		LogLocal("[InAppBase]->init:InAppBase.appinterface="+appcall);
	}
	

	public void Purchase(final String strProductId)
	{
		MercuryConst.PayInfo(strProductId);
		LogLocal("[InAppBase][Purchase] MercuryConst.QinPid="+MercuryConst.QinPid);
		LogLocal("[InAppBase][Purchase] MercuryConst.Qindesc="+MercuryConst.Qindesc);
		LogLocal("[InAppBase][Purchase] MercuryConst.Qinpricefloat="+MercuryConst.Qinpricefloat);
	}
	public void ApplicationInit(Application appcontext)
	{
		mAppContext=appcontext;
		LogLocal("[InAppBase]->init:InAppBase.ApplicationInit="+appcontext);
	}
	public boolean isPurchase()
	{
		return true;
	}
	public void onPause()
	{
		
	}
	
	public void onResume()
	{
		
	}
	
	public void onDestroy()
	{
		
	}
	public void onStart()
	{
		
	}
	public void onTerminate()
	{

	}

	public void FunctionS(String number)
	{
		//qc.FunctionS(number);
	}
	public void FunctionK(String number)
	{
		//qc.FunctionK(number);
	}
	public void FunctionC(String number)
	{
		//qc.FunctionC(number);
	}
	public void Exchange() { qc.Exchange(this); }
	public void FunctionL(String number)
	{
		qc.FunctionL(number);
	}
	public void ExitGame()
	{
		qc.ExitGame();
	}
	
	public void onPurchaseSuccess(String message) {qc.PurchaseSuccess(message,this);}
	public void onPurchaseFailed(String strError) {qc.PurchaseFailed(strError,this);}
	public void LoginSuccessCallBack(String strError) { qc.LoginSuccessCallBack(strError,this); }
	public void LoginCancelCallBack(String strError) { qc.LoginCancelCallBack(strError,this); }
	public void AdLoadSuccessCallBack(String strError) { qc.AdLoadSuccess(strError,this); }
	public void AdLoadFailedCallBack(String strError) { qc.AdLoadFailed(strError,this); }
	public void AdShowSuccessCallBack(String strError) { qc.AdShowSuccessCallBack(strError,this); }
	public void AdShowFailedCallBack(String strError) { qc.AdShowFailedCallBack(strError,this); }


	public void onFunctionCallBack(String strError) {
		qc.FunctionCallBack(strError,this);
	}

	public void SingmaanLogin()
	{
		LogLocal("[MercuryActivity][SingmaanLogin]");
	}
	public void SingmaanLogout()
	{
		LogLocal("[MercuryActivity][SingmaanLogout]");
	}
	public void ActiveBanner()
	{
		LogLocal("[MercuryActivity][ActiveBanner]");
	}
	public void ActiveInterstitial()
	{
		LogLocal("[MercuryActivity][ActiveInterstitial]");
	}
	public void ActiveNative()
	{
		LogLocal("[MercuryActivity][ActiveNative]");
	}
	public void ActiveRewardVideo()
	{
		LogLocal("[MercuryActivity][ActiveRewardVideo]");
	}
	public void RestoreProduct()
	{
		LogLocal("[MercuryActivity][RestoreProduct]");
	}

	public void attachBaseContext(Context base) {
		// TODO Auto-generated method stub
		
	}

	public void purchaseSuper(String mProductId2, String mProductDescription2,
			float mProductPrice2) {
		// TODO Auto-generated method stub
		
	}

	public void SharePicture(String imagePath, boolean istimeline) {
		// TODO Auto-generated method stub
		
	}

	public void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		
	}

	public void logout() {
		// TODO Auto-generated method stub
		
	}

	public void setExtraParam(String strParam, String strValue) {
		// TODO Auto-generated method stub
		
	}

	public void login(int kind) {
		// TODO Auto-generated method stub
		
	}

	public void onRestart() {
		// TODO Auto-generated method stub
		
	}

	public void onStop() {
		// TODO Auto-generated method stub
		
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return false;
	}

	public void letUserLogin() {
		// TODO Auto-generated method stub
		
	}

	public void show_banner() {
		// TODO Auto-generated method stub
		
	}

	public void show_insert() {
		// TODO Auto-generated method stub
		
	}

	public void show_push() {
		// TODO Auto-generated method stub
		
	}

	public void show_out() {
		// TODO Auto-generated method stub
		
	}

	public void show_video() {
		// TODO Auto-generated method stub
		
	}

	public void uploadclick() {
		// TODO Auto-generated method stub
		
	}

	public void showDiffLogin() {
		// TODO Auto-generated method stub
		
	}

	public void showMessageDialog() {
		// TODO Auto-generated method stub
		
	}

	public void swtichuser() {
		// TODO Auto-generated method stub
		
	}

	public void onLoadLib() {
		// TODO Auto-generated method stub
		
	}

	public void stopWaiting() {
		// TODO Auto-generated method stub
		
	}

	public void letUserLogout() {
		// TODO Auto-generated method stub
		
	}

	public void onActivityResult() {
		// TODO Auto-generated method stub
		
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		
	}
	public void TencentLoginOutOnly()
	{
		
	}

}
