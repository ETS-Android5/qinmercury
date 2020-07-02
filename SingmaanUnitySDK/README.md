## Get Started

SingmaanSDK is not a really SDK which is only a simulator to ensure game logic works. really SDK will be intergtrated by tools which depends APK only or XCode projects only.



###Import Singmaan SDK

Copy SDK folder or files from this Unity project into your Unity project, please check if scripts missing inside `SplashScreen.unity` after copying into game project, **SplashScreenManager.cs** is on `Main Camera` and **PluginMercury.cs** is on `PluginMercury`

* `Assets/Plugins`
* `Assets/Scene/SplashScreen.unity`
* `Assets/Script/SplashScreenManager.cs`
* `Assets/Script/PluginMercury.cs`
* `Assets/picture`

----

###Set Unity Scene

> Our scene will create a object named PluginMercury which will not be destoryed, thus you could use all Singmaan SDK functions without any issues. if developers don't want add scene, please make sure PluginMercury.cs is on the object named PluginMercury, or SDK can't recive callback.

* change your next scene name in **SplashScreenManager.cs**

```C#
public string nextSceneName = "YourGameScene";
```

* Set **SplashScreen.unity** as first Scenes in **File->Build Setting**

----

###Use Basic Singmaan SDK

All usable methods could be seen in **PluginMercury.cs** in this Unity project, following are explaintation.

```C#
public void Purchase(string strProductId)
```

* giving correct product id which named by developers will receive same product id from `PurchaseSuccessCallBack` in `PluginMercury.cs` , make sure all productions are unique and giving user prodoctions in `PurchaseSuccessCallBack`.

```C#
public void Redeem()
```

* Singmaan will create really Redeem system in release version, make sure when game recived production id from `PurchaseSuccessCallBack` could give users correct production, current version just return simple log without any functions.

```C#
public void RestoreProduct()
```

* Singmaan will create really RestoreProduct system in release version, make sure when game recived production id from `PurchaseSuccessCallBack` could give users correct production, current version just return simple log without any functions.

```C#
public void ExitGame()
```

* pressing exit button on Android phone is requried for this function, usually `Input.GetKeyDown(KeyCode.Escape)` is enough to detect this event. IOS version don't need.

```C#
public void ActiveRewardVideo()
```

* Active advertisenment,  game are able to receive following callback if ad SDK returned, `AdLoadSuccessCallBack`,`AdLoadFailedCallBack`, `AdShowSuccessCallBack`, `AdShowFailedCallBack`

```C#
public void ActiveInterstitial()
```

* Active advertisenment,  game are able to receive following callback if ad SDK returned, `AdLoadSuccessCallBack`,`AdLoadFailedCallBack`, `AdShowSuccessCallBack`, `AdShowFailedCallBack`

```C#
public void ActiveBanner()
```

* Active advertisenment,  game are able to receive following callback if ad SDK returned, `AdLoadSuccessCallBack`,`AdLoadFailedCallBack`, `AdShowSuccessCallBack`, `AdShowFailedCallBack`

```C#
public void ActiveNative()
```

* Active advertisenment,  game are able to receive following callback if ad SDK returned, `AdLoadSuccessCallBack`,`AdLoadFailedCallBack`, `AdShowSuccessCallBack`, `AdShowFailedCallBack`



## *Use advance Singmaan SDK

if developers have to use these methods for correcting game logic, here is all we supported menthods. but if game logic will not bother too much without these methods, we recommend don't use these methods. 

```java
public void SingmaanLogin()
```

* if developer logins successfully,  `LoginSuccessCallBack` in `APPBaseInterface` will return unique id(default unique id created by us, if you want your own unique id just igonre our unique id) for developer to identify user. Because singmaan will intergrate different channels' login SDK into singmaan's SDK(MercurySDK), it is impossible to support the account and the possword for developers to save in database all the time, in this case, please developers make sure all message between server and game client are encrpt as we use unique id to identify users. if users know other users unique id which means users can login other users account by the same unique id.

```java
public void SingmaanLogout()
```

* if developer logins successfully,  `LoginCancelCallBack` in `APPBaseInterface` will return same unique id as login in for developer to manager database, game logic and so on. If this method executed successfully, users should have a new account when restart game.

```java
public void Redeem()
```

* Singmaan will create really Redeem system in release version, make sure when game recived production id from `PurchaseSuccessCallBack` which could give users correct production, current version just return simple log without any functions.



```java
public void RestoreProduct()
```

* Singmaan will create really RestoreProduct system in release version, make sure  game recived production id from `PurchaseSuccessCallBack` which could give users correct production, current version just return simple log without any functions.



```java
public void GetProductionInfo()
```

* Singmaan will return all productions' prices, description and production id, such as 

  ```json
  {
     {"com.singmaan.game.removeAd","6.0¥"},
     {"com.singmaan.game.gold200","6.0¥"},
  }
  ```

Developers have to provide us all games' productions id, then developers could get correct production prices to displaying in games.

```java
public void RateGame()
```

* Displaying Rate dialog for game, usually displaying channels websites.

```java
public void ShareGame()
```

* users could share picture or link to social media.

```java
public void OpenGameCommunity()
```

* open a website which are related community



___

### Delivery Project

----

**Android**

* No crashing
* showing testing dialog
* recived callback and gave productions to users
* send APK to us

**IOS**

* No crashing
* showing testing dialog
* recived callback and gave productions to users
* send Xcode Project to us

