package com.mercury.game.InAppChannel;

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
import com.mercury.game.MercuryConst;
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
	public static Context forbassonly;
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

	public void init(Context appContext, Activity context, String strAppKey,String strAppSecret, APPBaseInterface appcall) {
		// TODO Auto-generated method stub
		mContext = context;
		mAppContext = appContext;
		mInstance = this;
		qc = new MercuryConst();
		this.appinterface=appcall;
		forbassonly=context;
		MercuryActivity.LogLocal("[InAppBase]->init:InAppBase.appinterface="+appcall);
	}
	

	public void purchase(String strProductId, String strProductDescription,float fPrice) {
		// TODO Auto-generated method stub
		mProductId = strProductId;
		mProductDescription = strProductDescription;
		mProductPrice = fPrice;		
		
	}
	
	public void ApplicationInit(Application appcontext)
	{
		mAppContext=appcontext;
		MercuryActivity.LogLocal("[InAppBase]->init:InAppBase.ApplicationInit="+appcontext);
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
	public void ShowTencentAd()
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
	
	public void onPurchaseSuccess(String message) {
		qc.onPurchaseSuccess(message,this,mProductId);
	}	
	public void onPurchaseFailed(String strError) {	
		qc.onPurchaseFailed(strError,this,mProductId);
	}	
	public void onPurchaseCanceled(String strError) {
		qc.onPurchaseFailed(strError,this,mProductId);		
	}
	public void onLoginSuccess(String strError) {
		qc.onLoginSuccess(strError,this);
	}
	public void onLoginCancel(String strError) {
		qc.onLoginCancel(strError,this);
	}
	public void onLoginFailed(String strError) {
		Log.e("IAP","this="+this);
		qc.onLoginFailed(strError,this);
	}
	public void onFunctionCallBack(String strError) {
		qc.onFunctionCallBack(strError,this);
	}
	
	public void show_cp()
	{

	}
	public void show_tt()
	{
		
	}
	public void show_wt()
	{

	}
	public void show_ts(boolean isopen)
	{
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
