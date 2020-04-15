## 方法列表

* [`AppApplicationInit(必须调用)`](##AppApplicationInit)
* [`InitSDK(必须调用)`](##InitSDK)
* [`onPause(必须调用)`](##onPause)
* [`onStop(必须调用)`](##onStop)
* [`onStart(必须调用)`](##onStart)
* [`onRestart(必须调用)`](##onRestart)
* [`onDestory(必须调用)`](##onDestory)
* [`onActivityResult(必须调用)`](##onActivityResult)
* [`onNewIntent(必须调用)`](##onNewIntent)
* [`Purchase`](##Purchase)
* [`ExitGame`](##ExitGame)

- [`show_insert`](##show_insert)
- [`show_banner`](##show_banner)
- [`show_video`](##show_video)
- [`show_out`](##show_out)
- [`Exchange`](##Exchange)

## AppApplicationInit

> 必须调用

### 说明

* 用于初始化SDK从而使用SDK的具体方法

* 在android启动的Application的里面初始化SDK
* 如果android的xml没有申明Application手动添加即可

###代码结构

```java
public void APPApplicationInit(Application context)
```

* context: 需要传入安卓的application

### 代码实例

```java
MercuryApplication sdkapp= new MercuryApplication();
sdkapp.APPApplicationInit(this);
```

## InitSDK

> 必须调用

### 说明

* 用于初始化SDK从而使用SDK的具体方法
* 在android启动的activity的里面初始化SDK
* appcall必须注册否则无法获取回掉

###代码结构

```java
public void InitSDK(Context ContextFromUsers,final APPBaseInterface appcall)
```

* ContextFromUsers: 需要传入安卓的Context
* appcall: 需要传入注册的回掉地址

### 代码实例

```java
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
```

## onPause

> 必须调用

###说明

* 显示记录安卓app生命周期，从而实现第三方SDK的功能

* 保证实例是被初始化过
* 不可临时创建，临时调用
* 不可以创建多个SDK实例导致调用混乱

###代码结构

```java
public void onPause()
```

### 代码实例

```java
MercurySDK.onPause();
```

## onStop

> 必须调用

###说明

* 显示记录安卓app生命周期，从而实现第三方SDK的功能

* 保证实例是被初始化过
* 不可临时创建，临时调用
* 不可以创建多个SDK实例导致调用混乱

###代码结构

```java
public void onStop()
```

### 代码实例

```java
MercurySDK.onStop();
```

## onStart

> 必须调用

###说明

* 显示记录安卓app生命周期，从而实现第三方SDK的功能

* 保证实例是被初始化过
* 不可临时创建，临时调用
* 不可以创建多个SDK实例导致调用混乱

###代码结构

```java
public void onStart()
```

### 代码实例

```java
MercurySDK.onStart();
```

## onRestart

> 必须调用

###说明

* 显示记录安卓app生命周期，从而实现第三方SDK的功能

* 保证实例是被初始化过
* 不可临时创建，临时调用
* 不可以创建多个SDK实例导致调用混乱

###代码结构

```java
public void onRestart()
```

### 代码实例

```java
MercurySDK.onRestart();
```

## onResume

> 必须调用

###说明

* 显示记录安卓app生命周期，从而实现第三方SDK的功能

* 保证实例是被初始化过
* 不可临时创建，临时调用
* 不可以创建多个SDK实例导致调用混乱

###代码结构

```java
public void onResume()
```

### 代码实例

```java
MercurySDK.onResume();
```

## onDestory

> 必须调用

###说明

* 显示记录安卓app生命周期，从而实现第三方SDK的功能

* 保证实例是被初始化过
* 不可临时创建，临时调用
* 不可以创建多个SDK实例导致调用混乱

###代码结构

```java
public void onDestory()
```

### 代码实例

```java
MercurySDK.onDestory();
```

## onActivityResult

> 必须调用

###说明

* 显示记录安卓app生命周期，从而实现第三方SDK的功能

* 保证实例是被初始化过
* 不可临时创建，临时调用
* 不可以创建多个SDK实例导致调用混乱

###代码结构

```java
public void onActivityResult(int requestCode, int resultCode, Intent data)
```

### 代码实例

```java
MercurySDK.onActivityResult(requestCode,resultCode,data);
```

## onNewIntent

> 必须调用

###说明

* 显示记录安卓app生命周期，从而实现第三方SDK的功能

* 保证实例是被初始化过
* 不可临时创建，临时调用
* 不可以创建多个SDK实例导致调用混乱

###代码结构

```java
public void onNewIntent(Intent intent)
```

### 代码实例

```java
MercurySDK.onNewIntent(intent);
```

## Purchase

> 需要时调用

### 说明

* 用于实际支付，下发道具的回掉会在APPBaseInterface中获得

* 保证实例是被初始化
* 不可临时创建，临时调用
* 不可以创建多个SDK实例导致调用混乱

###代码结构

```java
public void Purchase(String pidname)
```

* pidname: 传入计费点名，计费点的详细信息在MercuryConst.java中，比如价格，计费点名称。

### 代码实例

```java
MercurySDK.Purchase("production1");
```



## ExitGame

> 需要时调用

###说明

* 退出游戏，关闭进程

* 保证实例是被初始化过
* 不可临时创建，临时调用
* 不可以创建多个SDK实例导致调用混乱

###代码结构

```java
public void ExitGame()
```

### 代码实例

```java
MercurySDK.ExitGame();
```



## show_insert

> 需要时调用

###说明

* 显示`插屏广告`

* 保证实例是被初始化过
* 不可临时创建，临时调用
* 不可以创建多个SDK实例导致调用混乱

###代码结构

```java
public void show_insert()
```

### 代码实例

```java
MercurySDK.show_insert();
```



## show_banner

> 需要时调用

###说明

* 显示`banner(横幅)广告`

* 保证实例是被初始化过
* 不可临时创建，临时调用
* 不可以创建多个SDK实例导致调用混乱

###代码结构

```java
public void show_banner()
```

### 代码实例

```java
MercurySDK.show_banner();
```



## show_video

> 需要时调用

###说明

* 显示`video(横幅)广告`

* 保证实例是被初始化过
* 不可临时创建，临时调用
* 不可以创建多个SDK实例导致调用混乱

###代码结构

```java
public void show_video()
```

### 代码实例

```java
MercurySDK.show_video();
```



## show_out

> 需要时调用

###说明

* 显示`开屏广告`

* 保证实例是被初始化过
* 不可临时创建，临时调用
* 不可以创建多个SDK实例导致调用混乱

###代码结构

```java
public void show_out()
```

### 代码实例

```java
MercurySDK.show_out();
```



## Exchange

> (未完成)需要时调用

###说明

* 显示`兑换码`窗口

* 保证实例是被初始化过
* 不可临时创建，临时调用
* 不可以创建多个SDK实例导致调用混乱

###代码结构

```java
public void Exchange()
```

### 代码实例

```java
MercurySDK.Exchange();
```

