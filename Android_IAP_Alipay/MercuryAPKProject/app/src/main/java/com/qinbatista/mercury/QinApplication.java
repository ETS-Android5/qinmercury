package com.qinbatista.mercury;
import com.mercury.game.MercuryApplication;

import android.app.Application;
import android.util.Log;




public class QinApplication extends Application {
 
	@Override
	public void onCreate() {
		super.onCreate();
		Log.w("MercuryDemo","[step][1]init application");
		MercuryApplication sdkapp= new MercuryApplication();
		sdkapp.APPApplicationInit(this);
	}
	
}
