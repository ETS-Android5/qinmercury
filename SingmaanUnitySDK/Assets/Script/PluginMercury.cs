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
    [DllImport("__Internal")]
    private static extern void BuyProduct(string s);//购买商品(AppStore)
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
#elif UNITY_IPHONE
        BuyProduct(strProductId);
#endif
    }
    public void Redeem()
    {
#if UNITY_EDITORRedeem
        print("[UNITY_EDITOR]->Redeem()");
#elif UNITY_ANDROID
        print("[Android]->Redeem()");_plugin.Call("Redeem");
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

    public void RestoreProduct()
    {
#if UNITY_EDITOR
        print("[UNITY_EDITOR]->RestoreProduct()");
#elif UNITY_ANDROID
        print("[Android]->RestoreProduct()");_plugin.Call("RestoreProduct");
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


    public void UploadGameData()
    {
#if UNITY_EDITOR
        print("[UNITY_EDITOR]->UploadGameData()");
#elif UNITY_ANDROID
        print("[Android]->UploadGameData()");_plugin.Call("UploadGameData");
#endif
    }

    public void DownloadGameData()
    {
#if UNITY_EDITOR
        print("[UNITY_EDITOR]->DownloadGameData()");
#elif UNITY_ANDROID
        print("[Android]->DownloadGameData()");_plugin.Call("DownloadGameData");
#endif
    }


    public void SingmaanLogin()
    {
#if UNITY_EDITOR
        print("[UNITY_EDITOR]->SingmaanLogin()");
#elif UNITY_ANDROID
        print("[Android]->SingmaanLogin()");_plugin.Call("SingmaanLogin");
#endif
    }

    public void SingmaanLogout()
    {
#if UNITY_EDITOR
        print("[UNITY_EDITOR]->SingmaanLogout()");
#elif UNITY_ANDROID
        print("[Android]->SingmaanLogout()");_plugin.Call("SingmaanLogout");
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
#endif
    }

    public void RateGame()
    {
#if UNITY_EDITOR
        print("[UNITY_EDITOR]->RateGame()");
#elif UNITY_ANDROID
        print("[Android]->RateGame()");_plugin.Call("RateGame");
#elif UNITY_IPHONE
        print("[UNITY_IPHONE]->RateGame()");
#endif
    }


    public void ShareGame()
    {
#if UNITY_EDITOR
        print("[UNITY_EDITOR]->ShareGame()");
#elif UNITY_ANDROID
        print("[Android]->ShareGame()");_plugin.Call("ShareGame");
#elif UNITY_IPHONE
        print("[UNITY_IPHONE]->ShareGame()");
#endif
    }


    public void OpenGameCommunity()
    {
#if UNITY_EDITOR
        print("[UNITY_EDITOR]->OpenGameCommunity()");
#elif UNITY_ANDROID
        print("[Android]->OpenGameCommunity()");_plugin.Call("OpenGameCommunity");
#elif UNITY_IPHONE
        print("[UNITY_IPHONE]->OpenGameCommunity()");
#endif
    }

    public void PurchaseSuccessCallBack(string msg)
    {
        print("[Unity]->PurchaseSuccessCallBack"+msg);
    }
    public void PurchaseFailedCallBack(string msg)
    {
        print("[Unity]->PurchaseFailedCallBack"+msg);
    }
    public void LoginSuccessCallBack(string msg)
    {
        print("[Unity]->LoginSuccessCallBack"+msg);
    }
    public void LoginCancelCallBack(string msg)
    {
        print("[Unity]->LoginCancelCallBack"+msg);
    }
    public void AdLoadSuccessCallBack(string msg)
    {
        print("[Unity]->AdLoadSuccessCallBack"+msg);
    }
    public void AdLoadFailedCallBack(string msg)
    {
        print("[Unity]->AdLoadFailedCallBack"+msg);
    }
    
    public void AdShowSuccessCallBack(string msg)
    {
        print("[Unity]->AdShowSuccessCallBack"+msg);
    }
    public void AdShowFailedCallBack(string msg)
    {
        print("[Unity]->AdShowFailedCallBack"+msg);
    }
    public void onFunctionCallBack(string msg)
    {
        print("[Unity]->onFunctionCallBack"+msg);
    }

}

