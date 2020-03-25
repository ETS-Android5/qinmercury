package com.qinbatista.mercury;
import com.mercury.game.MercuryApplication;
import com.umeng.commonsdk.UMConfigure;

import android.app.Application;
import android.util.Log;




public class QinApplication extends Application {
 
	@Override
	public void onCreate() {
		super.onCreate();

		/**
		 * 注意: 即使您已经在AndroidManifest.xml中配置过appkey和channel值，也需要在App代码中调
		 * 用初始化接口（如需要使用AndroidManifest.xml中配置好的appkey和channel值，
		 * UMConfigure.init调用中appkey和channel参数请置为null）。
		 */
		Log.w("MercuryDemo","[step][1]init application");
		MercuryApplication sdkapp= new MercuryApplication();
		sdkapp.APPApplicationInit(this);
	}
	
}
