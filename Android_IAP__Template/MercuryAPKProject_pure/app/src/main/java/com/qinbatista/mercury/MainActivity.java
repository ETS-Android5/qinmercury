package com.qinbatista.mercury;

import com.mercury.game.MercuryActivity;
import com.mercury.game.util.APPBaseInterface;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.demo.game.R;
public class MainActivity extends Activity  {
	public static Context context;
	public MercuryActivity MercurySDK;
	public static APPBaseInterface appcall=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=this;
		Log.w("MercuryDemo","[step][2]init activity");
		MercurySDK=new MercuryActivity();
		Log.w("MercuryDemo","[step][3]init callback");
		appcall = new  APPBaseInterface() {

			@Override
			public void PurchaseSuccessCallBack(String s) {
				Toast.makeText(context, "PurchaseSuccessCallBack",Toast.LENGTH_SHORT).show();
			}

			@Override
			public void PurchaseFailedCallBack(String s) {
				Toast.makeText(context, "PurchaseFailedCallBack",Toast.LENGTH_SHORT).show();
			}
			@Override
			public void LoginSuccessCallBack(String s) {
				Toast.makeText(context, "LoginSuccessCallBack",Toast.LENGTH_SHORT).show();
			}

			@Override
			public void LoginCancelCallBack(String s) {
				Toast.makeText(context, "LoginCancelCallBack",Toast.LENGTH_SHORT).show();
			}

			@Override
			public void AdLoadSuccessCallBack(String s) {
				Toast.makeText(context, "AdLoadSuccessCallBack",Toast.LENGTH_SHORT).show();
			}

			@Override
			public void AdLoadFailedCallBack(String s) {
				Toast.makeText(context, "AdLoadFailedCallBack",Toast.LENGTH_SHORT).show();
			}

			@Override
			public void AdShowSuccessCallBack(String s) {
				Toast.makeText(context, "AdShowSuccessCallBack",Toast.LENGTH_SHORT).show();
			}

			@Override
			public void AdShowFailedCallBack(String s) {
				Toast.makeText(context, "AdShowFailedCallBack",Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFunctionCallBack(String s) {
				Toast.makeText(context, "onFunctionCallBack",Toast.LENGTH_SHORT).show();
			}

		};
		Log.w("MercuryDemo","[step][4]Init MercurySDK");
		MercurySDK.InitSDK (context,appcall);

		setContentView(R.layout.activity_main);
		Button btn = (Button) findViewById(R.id.pay);
		// button buy
		btn.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.w("MercuryDemo","[step][5]->purchaseProduct");
				MercurySDK.Purchase("production1");

			}
		});

		// ****************************************exit
		Button exit = (Button) findViewById(R.id.exit);
		exit.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.w("MercuryDemo","[step][6]->ExitGame");
				MercurySDK.ExitGame();
			}
		});

		Button btn1 = (Button) findViewById(R.id.button1);
		btn1.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.w("MercuryDemo","[step][7]->repairindentRequest");
				MercurySDK.repairindentRequest();
			}
		});
		
		Button btn2 = (Button) findViewById(R.id.button2);
		btn2.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.w("MercuryDemo","[step][8]->respondCPserver");
				MercurySDK.respondCPserver();
				
			}
		});
		Button btn3 = (Button) findViewById(R.id.button3);
		btn3.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.w("MercuryDemo","[step][9]->show_insert");
				MercurySDK.show_insert();
			}
		});
		Button btn4 = (Button) findViewById(R.id.button4);
		btn4.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.w("MercuryDemo","[step][10]->show_banner");
				MercurySDK.show_banner();
			}
		});
		Button btn5 = (Button) findViewById(R.id.Button5);
		btn5.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.w("MercuryDemo","[step][11]->show_video");
				MercurySDK.show_video();
			}
		});
		Button btn6 = (Button) findViewById(R.id.Button6);
		btn6.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.w("MercuryDemo","[step][12]->Exchange");
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
