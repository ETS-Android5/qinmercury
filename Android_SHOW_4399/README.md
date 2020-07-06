#Get Started

##Import SDK

Copy `MercurySDK.jar` ,`android-support-v4.jar`,`org.apache.http.legacy.jar`into your android project and link as your project library, or you can not use methonds from `MercurySDK.jar`. you can find them in `SDK` folder



## Initialize SDK

* Initialize in Application

```java
MercuryApplication sdkapp= new MercuryApplication();
sdkapp.APPApplicationInit(this);
```

* Initialize in Activity

```java
MercurySDK=new MercuryActivity();
MercurySDK.InitSDK (context,appcall);
```

* Set callback

```java
appcall = new  APPBaseInterface() {
			@Override
			public void PurchaseSuccessCallBack(String s) {
      }
  .....
};
```

* add methods to detect Android life cycle event .

```java
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
```



##Use SDK methods

All usable methods could be seen in **MainActivity.java** in demo android project, following are explaintation.

```java
MercurySDK.Purchase("production1");
```

* giving correct product id which named by developers will receive same product id from `PurchaseSuccessCallBack` in `APPBaseInterface` , make sure all productions are unique and giving user prodoctions in `PurchaseSuccessCallBack`.Current SDK just display testing dialog to help developers to debug, we will replace SDK in releasing version.

```java
MercurySDK.ExitGame();
```

* pressing exit button on Android phone is requried for this function.Current SDK just display testing dialog to help developers to debug, we will replace SDK in releasing version.

```java
MercurySDK.ActiveRewardVideo();
```

* Active advertisenment,  game are able to receive following callback if ad SDK returned, `AdLoadSuccessCallBack`,`AdLoadFailedCallBack`, `AdShowSuccessCallBack`, `AdShowFailedCallBack`. Current SDK just display testing dialog to help developers to debug, we will replace SDK in releasing version.

```java
MercurySDK.ActiveInterstitial();
```

* Active advertisenment,  game are able to receive following callback if ad SDK returned, `AdLoadSuccessCallBack`,`AdLoadFailedCallBack`, `AdShowSuccessCallBack`, `AdShowFailedCallBack`. Current SDK just display testing dialog to help developers to debug, we will replace SDK in releasing version.

```java
MercurySDK.ActiveBanner();
```

* Active advertisenment,  game are able to receive following callback if ad SDK returned, `AdLoadSuccessCallBack`,`AdLoadFailedCallBack`, `AdShowSuccessCallBack`, `AdShowFailedCallBack`. Current SDK just display testing dialog to help developers to debug, we will replace SDK in releasing version.

```java
MercurySDK.ActiveNative();
```

* Active advertisenment,  game are able to receive following callback if ad SDK returned, `AdLoadSuccessCallBack`,`AdLoadFailedCallBack`, `AdShowSuccessCallBack`, `AdShowFailedCallBack`. Current SDK just display testing dialog to help developers to debug, we will replace SDK in releasing version.

##AndroidManifest.xml examination

* Declared Application in AndroidManifest.xml

```xml
<application android:icon="@drawable/app_icon" android:label="@string/app_name" android:name="com.YourPackageName.YouApplicationName">
```

please make sure `sdkapp.APPApplicationInit(this);` had been Initialize from `com.YourPackageName.YouApplicationName`, `this` is the context of `com.YourPackageName.YouApplicationName`.



* Declared Activity in AndroidManifest.xml

```xml
<activity android:configChanges="orientation|screenSize" android:name="com.YourPackageName.YourMainActivity" android:screenOrientation="landscape" android:theme="@android:style/Theme.NoTitleBar.Fullscreen">
      <intent-filter>
        <action android:name="android.intent.action.MAIN"/>
        <category android:name="android.intent.category.LAUNCHER"/>
      </intent-filter>
</activity>
```

please make sure `MercurySDK.InitSDK (context,appcall);` had been Initialize from `com.YourPackageName.YourMainActivity`, `context` is the context of `com.YourPackageName.YourMainActivity`.



## *Advance SDK methods

if developers have to use these methods for correcting game logic, here is all we supported menthods. but if game logic will not bother too much without these methods, we recommend don't use these methods. 

```java
MercurySDK.SingmaanLogin();
```

* if developer logins successfully,  `LoginSuccessCallBack` in `APPBaseInterface` will return unique id(default unique id created by us, if you want your own unique id just igonre our unique id) for developer to identify user. Because singmaan will intergrate different channels' login SDK into singmaan's SDK(MercurySDK), it is impossible to support the account and the possword for developers to save in database all the time, in this case, please developers make sure all message between server and game client are encrpt as we use unique id to identify users. if users know other users unique id which means users can login other users account by the same unique id.

```java
MercurySDK.SingmaanLogout();
```

* if developer logins successfully,  `LoginCancelCallBack` in `APPBaseInterface` will return same unique id as login in for developer to manager database, game logic and so on. If this method executed successfully, users should have a new account when restart game.

```java
MercurySDK.Redeem();
```

* Singmaan will create really Redeem system in release version, make sure when game recived production id from `PurchaseSuccessCallBack` which could give users correct production, current version just return simple log without any functions.

```java
MercurySDK.RestoreProduct();
```

* Singmaan will create really RestoreProduct system in release version, make sure  game recived production id from `PurchaseSuccessCallBack` which could give users correct production, current version just return simple log without any functions.

```java
MercurySDK.GetProductionInfo();
```

* Singmaan will return all productions' prices, description and production id, such as 

  ```json
  {
     {"com.singmaan.game.removeAd","去除广告","6.0"},
     {"com.singmaan.game.gold200","200个黄金","6.0"},
  }
  ```

Developers have to provide us all games' productions id, then developers could get correct production, Chinese prices and Chinese descriptions from `GetProductionInfo()`. please make currency in game is "¥" or "元", don't display "$" in Chinese version.











