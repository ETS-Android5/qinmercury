package com.singmaan.game;
import com.mercury.game.MercuryApplication;

import android.app.Application;
import android.util.Log;

public class GameApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		Log.w("Unity","[step][1]init application");
		MercuryApplication sdkapp= new MercuryApplication();
		sdkapp.APPApplicationInit(this);
	}

}


