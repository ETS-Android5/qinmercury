package com.qinbatista.mercury;

import com.mercury.game.MercuryActivity;
import com.mercury.game.MercuryConst;
import com.mercury.game.InAppChannel.APPBaseInterface;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.AmanitaDesign.Machinarium.E2W.R;

public class MainActivity extends Activity  {
	public static Context context;
	public MercuryActivity MercurySDK;
	public static APPBaseInterface appcall=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=this;
		Log.w("MercurySDK","[step][2]init activity");
		MercurySDK=new MercuryActivity();
		Log.w("MercurySDK","[step][3]init callback");
		appcall = new  APPBaseInterface() {
			
			@Override
			public void onPurchaseSuccessCallBack(String strProductId) {
				// TODO Auto-generated method stub
				Log.w("MercurySDK", "onCreate onPurchaseSuccessCallBack strProductId="+strProductId);
				Toast.makeText(context, "onPurchaseSuccessCallBack",Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onPurchaseFailedCallBack(String strProductId) {
				// TODO Auto-generated method stub
				Log.w("MercurySDK", "onCreate onPurchaseFailedCallBack strProductId="+strProductId);
			}
			
			@Override
			public void onPurchaseCancelCallBack(String strProductId) {
				// TODO Auto-generated method stub
				Log.w("MercurySDK", "onCreate onPurchaseCancelCallBack strProductId="+strProductId);
			}

			@Override
			public void onLoginCancelCallBack(String arg0) {
				// TODO Auto-generated method stub
				Log.w("MercurySDK", "onCreate onLoginCancelCallBack arg0="+arg0);
			}

			@Override
			public void onLoadSuccessfulCallBack(String s) {
				Log.w("MercurySDK", "onCreate onLoginCancelCallBack arg0="+s);
			}

			@Override
			public void onLoadFailedCallBack(String s) {
				Log.w("MercurySDK", "onCreate onLoginCancelCallBack arg0="+s);
			}

			@Override
			public void onSaveSuccessfulCallBack(String s) {
				Log.w("MercurySDK", "onCreate onLoginCancelCallBack arg0="+s);
			}

			@Override
			public void onSaveFailedCallBack(String s) {
				Log.w("MercurySDK", "onCreate onLoginCancelCallBack arg0="+s);
			}

			@Override
			public void onOnVideoCompletedCallBack(String s) {
				Log.w("MercurySDK", "onCreate onLoginCancelCallBack arg0="+s);
			}

			@Override
			public void onOnVideoFailedCallBack(String s) {
				Log.w("MercurySDK", "onCreate onLoginCancelCallBack arg0="+s);
			}

			@Override
			public void onLoginFailedCallBack(String arg0) {
				// TODO Auto-generated method stub
				Log.w("MercurySDK", "onCreate onLoginFailedCallBack arg0="+arg0);
			}

			@Override
			public void onLoginSuccessCallBack(String arg0) {
				// TODO Auto-generated method stub
				Log.w("MercurySDK", "onCreate onLoginSuccessCallBack arg0="+arg0);
			}

			@Override
			public void onFunctionCallBack(String arg0) {
				// TODO Auto-generated method stub
				Log.w("MercurySDK", "onCreate onFunctionCallBack arg0="+arg0);
				if(arg0.equals("ExitGame"))
				{
					//exit game by yourself
				}
				else if(arg0.equals("UnlockGame"))
				{
					//unlock game
				}
				else if(arg0.equals("SplashEnd"))
				{
					//splash finished
				}
			}
		};
		Log.w("MercurySDK","[step][4]Init MercurySDK");
		MercurySDK.InitSDK (context,appcall);

		setContentView(R.layout.activity_main);
		Button btn = (Button) findViewById(R.id.pay);
		// button buy
		btn.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.w("MercurySDK","[step][5]->purchaseProduct");
				MercurySDK.purchaseProduct("production1");
			}
		});

		// ****************************************exit
		Button exit = (Button) findViewById(R.id.exit);
		exit.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.w("MercurySDK","[step][6]->ExitGame");
				MercurySDK.ExitGame();
			}
		});

		Button btn1 = (Button) findViewById(R.id.button1);
		btn1.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.w("MercurySDK","[step][7]->repairindentRequest");
				MercurySDK.repairindentRequest();
			}
		});
		
		Button btn2 = (Button) findViewById(R.id.button2);
		btn2.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.w("MercurySDK","[step][7]->respondCPserver");
				MercurySDK.respondCPserver();
				
			}
		});
		Button btn3 = (Button) findViewById(R.id.button3);
		btn3.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.w("MercurySDK","[step][8]->show_insert");
				MercurySDK.show_insert();
			}
		});
		Button btn4 = (Button) findViewById(R.id.button4);
		btn4.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.w("MercurySDK","[step][8]->show_banner");
				MercurySDK.show_banner();
			}
		});
		Button btn5 = (Button) findViewById(R.id.Button5);
		btn5.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.w("MercurySDK","[step][8]->show_video");
				MercurySDK.show_video();
			}
		});
		Button btn6 = (Button) findViewById(R.id.Button6);
		btn6.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.w("MercurySDK","[step][8]->Exchange");
				MercurySDK.Exchange();
			}
		});
	}



	@Override
	public void onPause()
	{
		super.onPause();
		MercurySDK.onPause();
	}

	@Override
	public void onStop()
	{
		super.onStop();
		MercurySDK.onStop();
	}

	@Override
	public void onRestart()
	{
		super.onRestart();
		MercurySDK.onRestart();
	}
	@Override
	public void onResume()
	{
		super.onResume();
		MercurySDK.onResume();
	}
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		MercurySDK.onDestroy();
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode,resultCode,data);
		MercurySDK.onActivityResult(requestCode,resultCode,data);
	}
	@Override
	public void onNewIntent(Intent intent)
	{
		super.onNewIntent(intent);
		MercurySDK.onNewIntent(intent);
	}
}
