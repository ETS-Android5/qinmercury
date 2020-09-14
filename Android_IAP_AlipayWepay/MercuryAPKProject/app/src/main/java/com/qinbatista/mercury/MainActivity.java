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

import com.singmaan.guanbao.R;public class MainActivity extends Activity  {
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
				Toast.makeText(context, "onFunctionCallBack"+s,Toast.LENGTH_SHORT).show();
			}

			@Override
			public void ProductionIDCallBack(String s) {
				Toast.makeText(context, "ProductionIDCallBack"+s,Toast.LENGTH_SHORT).show();
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
				Log.w("MercuryDemo","[step][5]->purchaseProduct id= uk.fiveaces.nsfcchina.bux_1");
				MercurySDK.Purchase("uk.fiveaces.nsfcchina.bux_1");
			}
		});
		Button exit = (Button) findViewById(R.id.exit);
		exit.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.w("MercuryDemo","ExitGame");
				MercurySDK.ExitGame();
			}
		});
		Button btn1 = (Button) findViewById(R.id.button1);
		btn1.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.w("MercuryDemo","Reedem");
				MercurySDK.Redeem();
			}
		});
		
		Button btn2 = (Button) findViewById(R.id.button2);
		btn2.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.w("MercuryDemo","RestorePruchase");
				MercurySDK.RestorePruchase();
			}
		});
		Button btn3 = (Button) findViewById(R.id.button3);
		btn3.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.w("MercuryDemo","ActiveInterstitial");
				MercurySDK.ActiveInterstitial();
			}
		});
		Button btn4 = (Button) findViewById(R.id.button4);
		btn4.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.w("MercuryDemo","ActiveRewardVideo");
				MercurySDK.ActiveRewardVideo();
			}
		});
		Button btn5 = (Button) findViewById(R.id.button5);
		btn5.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.w("MercuryDemo","ActiveBanner");
				MercurySDK.ActiveBanner();
			}
		});
		Button btn6 = (Button) findViewById(R.id.button6);
		btn6.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.w("MercuryDemo","ActiveNative");
				MercurySDK.ActiveNative();
			}
		});
		Button btn7 = (Button) findViewById(R.id.button7);
		btn7.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.w("MercuryDemo","SingmaanLogin");
				MercurySDK.SingmaanLogin();
			}
		});
		Button btn8 = (Button) findViewById(R.id.button8);
		btn8.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.w("MercuryDemo","SingmaanLogout");
				MercurySDK.SingmaanLogout();
			}
		});
		Button btn9 = (Button) findViewById(R.id.button9);
		btn9.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.w("MercuryDemo","UploadGameData");
				MercurySDK.UploadGameData("this is testing uploaded data");
			}
		});
		Button btn10 = (Button) findViewById(R.id.button10);
		btn10.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.w("MercuryDemo","DownloadGameData");
				MercurySDK.DownloadGameData();
			}
		});
		Button btn11 = (Button) findViewById(R.id.button11);
		btn11.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.w("MercuryDemo","GetProductions="+MercurySDK.GetProductionInfo());
			}
		});
		Button btn12 = (Button) findViewById(R.id.button12);
		btn12.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.w("MercuryDemo","OpenGameCommunity");
				MercurySDK.OpenGameCommunity();
			}
		});
		Button btn13 = (Button) findViewById(R.id.button13);
		btn13.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.w("MercuryDemo","ShareGame");
				MercurySDK.ShareGame();
			}
		});
		Button btn14 = (Button) findViewById(R.id.button14);
		btn14.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.w("MercuryDemo","RateGame");
				MercurySDK.RateGame();
			}
		});
		Button btn15 = (Button) findViewById(R.id.button15);
		btn15.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.w("MercuryDemo","GetDeviceId"+MercurySDK.getDeviceId());
			}
		});
		Button btn16 = (Button) findViewById(R.id.button16);
		btn16.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.w("MercuryDemo","DataALSUseItem");
				MercurySDK.Data_UseItem("5","Data_UseItem");
			}
		});
		Button btn17 = (Button) findViewById(R.id.button17);
		btn17.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.w("MercuryDemo","DataALSLevelBegin");
				MercurySDK.Data_LevelBegin("DataALSLevelBegin");
			}
		});
		Button btn18 = (Button) findViewById(R.id.button18);
		btn18.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.w("MercuryDemo","DataALSLevelCompleted");
				MercurySDK.Data_LevelCompleted("Data_LevelCompleted");
			}
		});
		Button btn19 = (Button) findViewById(R.id.button19);
		btn19.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.w("MercuryDemo","DataALSEvent");
				MercurySDK.Data_Event("Data_Event");
			}
		});
		Button btn20 = (Button) findViewById(R.id.button20);
		btn20.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.w("MercuryDemo","PlayVideo");
				MercurySDK.PlayVideo();
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
