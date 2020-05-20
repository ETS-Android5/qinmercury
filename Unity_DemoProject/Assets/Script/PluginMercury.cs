using UnityEngine;
using System.Collections;
using System;
using UnityEngine.UI;
using System.Runtime.InteropServices;
public class PluginMercury : MonoBehaviour
{

#if UNITY_ANDROID
    public static AndroidJavaObject _plugin;
#elif UNITY_IPHONE
    [DllImport ("__Internal")]
    private static extern void ActiveRewardVideo_IOS();
    [DllImport ("__Internal")]
    private static extern void ActiveInterstitial_IOS();
    [DllImport ("__Internal")]
    private static extern void ActiveBanner_IOS();
    [DllImport ("__Internal")]
    private static extern void ActiveNative_IOS();
    [DllImport ("__Internal")]
    private static extern void GameInit(string name);
#endif

    public static PluginMercury pInstance;
    public static PluginMercury Instance
    {
        get
        {
            return pInstance;
        }
    }
    private void Update() {
        if (Input.GetKeyDown(KeyCode.Escape))
        {
            ExitGame();
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

    public void GetAndroidInstance()
    {
#if UNITY_EDITOR
    print("[UNITY_EDITOR]->GetAndroidInstance");
#elif UNITY_ANDROID
        //安卓获取实例
        using (var pluginClass = new AndroidJavaClass("com.singmaan.game.GameActivity"))
        {
            _plugin = pluginClass.CallStatic<AndroidJavaObject>("getInstance");
        }
#elif UNITY_IPHONE
        print("[UNITY_IPHONE]->GameInit()");
        GameInit("GameInit");
#endif
    }

    public void Purchase(string strProductId)
    {
#if UNITY_EDITOR
        print("[UNITY_EDITOR]->Purchase()->strProductId=" + strProductId);
#elif UNITY_ANDROID
        print("[UNITY_ANDROID]->Purchase()->strProductId="+strProductId);
        _plugin.Call("Purchase", strProductId );
#endif
    }
    public void Redeem()
    {
#if UNITY_EDITOR
        print("[UNITY_EDITOR]->Exchange()");
#elif UNITY_ANDROID
        print("[Android]->Exchange()");_plugin.Call("Redeem");
#endif
    }
    public void RestoreProduct()
    {
#if UNITY_EDITOR
        print("[UNITY_EDITOR]->Exchange()");
#elif UNITY_ANDROID
        print("[Android]->Exchange()");_plugin.Call("RestoreProduct");
#endif
    }


    public void ExitGame()
    {
#if UNITY_EDITOR
        print("[UNITY_EDITOR]->ExitGame()");
#elif UNITY_ANDROID
        print("[Android]->ExitGame()");_plugin.Call("ExitGame");
#endif
    }
    public void ActiveRewardVideo()
    {
#if UNITY_EDITOR
        print("[UNITY_EDITOR]->ActiveRewardVideo()");
#elif UNITY_ANDROID
        print("[Android]->ActiveRewardVideo()");_plugin.Call("ActiveRewardVideo");
#elif UNITY_IPHONE
        print("[UNITY_IPHONE]->ActiveRewardVideo()");
        ActiveRewardVideo_IOS();
#endif
    }

    public void ActiveInterstitial()
    {
#if UNITY_EDITOR
        print("[UNITY_EDITOR]->ActiveInterstitial()");
#elif UNITY_ANDROID
        print("[Android]->ActiveInterstitial()");_plugin.Call("ActiveInterstitial");
#elif UNITY_IPHONE
        print("[UNITY_IPHONE]->ActiveInterstitial()");
        ActiveInterstitial_IOS();
#endif
    }
    public void ActiveBanner()
    {
#if UNITY_EDITOR
        print("[UNITY_EDITOR]->ActiveBanner()");
#elif UNITY_ANDROID
        print("[Android]->ActiveBanner()");_plugin.Call("ActiveBanner");
#elif UNITY_IPHONE
        print("[UNITY_IPHONE]->ActiveBanner()");
        ActiveBanner_IOS();
#endif
    }
    public void ActiveNative()
    {
#if UNITY_EDITOR
        print("[UNITY_EDITOR]->ActiveNative()");
#elif UNITY_ANDROID
        print("[Android]->ActiveNative()");_plugin.Call("ActiveNative");
#elif UNITY_IPHONE
        print("[UNITY_IPHONE]->ActiveNative()");
        ActiveNative_IOS();
#endif
    }
    public void GetProductionInfo()
    {
#if UNITY_EDITOR
        print("[UNITY_EDITOR]->GetProductionInfo()");
#elif UNITY_ANDROID
        print("[Android]->GetProductionInfo()");_plugin.Call("GetProductionInfo");
#endif
    }


    public void PurchaseSuccessCallBack(string msg)
    {
        print("[Unity]->PurchaseSuccessCallBack");
    }
    public void PurchaseFailedCallBack(string msg)
    {
        print("[Unity]->PurchaseFailedCallBack");
    }
    public void LoginSuccessCallBack(string msg)
    {
        print("[Unity]->LoginSuccessCallBack");
    }
    public void LoginCancelCallBack(string msg)
    {
        print("[Unity]->LoginCancelCallBack");
    }
    public void AdLoadSuccessCallBack(string msg)
    {
        print("[Unity]->AdLoadSuccessCallBack");
    }
    public void AdLoadFailedCallBack(string msg)
    {
        print("[Unity]->AdLoadFailedCallBack");
    }
    
    public void AdShowSuccessCallBack(string msg)
    {
        print("[Unity]->AdShowSuccessCallBack");
    }
    public void AdShowFailedCallBack(string msg)
    {
        print("[Unity]->AdShowFailedCallBack");
    }
    public void onFunctionCallBack(string msg)
    {
        print("[Unity]->onFunctionCallBack");
    }

}

