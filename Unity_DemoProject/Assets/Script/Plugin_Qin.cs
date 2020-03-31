using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using System;
using UnityEngine.UI;
using SimpleJSON;

public class Plugin_Qin : MonoBehaviour
{

#if UNITY_ANDROID
    public static AndroidJavaObject _plugin;
#elif UNITY_IPHONE

#else

#endif  
    public static Plugin_Qin pInstance;
    public bool isShowSplash = true;
    //-------回调--------
    public Action<string> OnBuySuccessCallback;//购买成功回调（参数：物品名称）
    public Action<string> OnBuyFailCallback;//购买失败回调（参数：物品名称）
    public Action<string> OnExchangeSuccessCallback;//兑换成功回调（参数：物品名称）
    public Action OnLoginSuccessCallback;//登录成功回调
    public Action<string> OnRewardVideoRewardedCallback;//激励视频奖励成功回调（参数：奖励名称）
    //---------------
    public static Plugin_Qin Instance
    {
        get
        {
            return pInstance;
        }
    }

    void Awake()
    {
        if (pInstance != null)
        {
            Destroy(gameObject);
            return;
        }
        DontDestroyOnLoad(gameObject);
        pInstance = this;
        GetAndroidInstance();//得到安卓实例
    }

    void Update()
    {
    }

    public void GetAndroidInstance()
    {
#if UNITY_EDITOR
    print("[UNITY_EDITOR]->GetAndroidInstance");
#elif UNITY_ANDROID
        //安卓获取实例
        using (var pluginClass = new AndroidJavaClass("com.east2west.unitygame.MainActivity"))
        {
            _plugin = pluginClass.CallStatic<AndroidJavaObject>("getInstance");
        }
#endif
    }

    public void Purchase(string strProductId)
    {
#if UNITY_EDITOR
        print("[UNITY_EDITOR]->Buy()->strProductId=" + strProductId);
#elif UNITY_ANDROID
        print("[UNITY_ANDROID]->Buy()->SavePid="+strProductId);
        _plugin.Call("purchaseProduct", strProductId );
#endif
    }
    public void Exchange()
    {
#if UNITY_EDITOR
        //ExchangeSuccess("1");
#elif UNITY_ANDROID
        _plugin.Call("Exchange");
#endif
    }
    public void show_insert(string adId)
    {
#if UNITY_EDITOR
        print("[show_insert]");
#elif UNITY_ANDROID
        print("show_insert");
        _plugin.Call("show_insert",adId);
#endif
    }
    public void show_banner(string adId)
    {
#if UNITY_EDITOR
        print("[ShowAd->show_banner]");
#elif UNITY_ANDROID
        _plugin.Call("show_banner",adId);
#endif
    }
    public void show_push(string adId)
    {
#if UNITY_EDITOR
        print("[ShowAd->show_push]");
#elif UNITY_ANDROID
        _plugin.Call("show_push",adId);
#endif
    }
    public void show_out()
    {
#if UNITY_EDITOR
        print("ShowAd->show_out");
#elif UNITY_ANDROID
        _plugin.Call("show_out");
#endif
    }
    public void show_video(string pid)
    {
#if UNITY_EDITOR
        print("ShowAd->show_video");
#elif UNITY_ANDROID
        Message("观看激励视频");
        _plugin.Call("show_video",pid);
#endif
    }

    public void ExchangeSuccess(string pid)
    {

    }

}

