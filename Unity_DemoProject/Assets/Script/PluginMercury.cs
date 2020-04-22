using UnityEngine;
using System.Collections;
using System;
using UnityEngine.UI;
using SimpleJSON;
using System.Runtime.InteropServices;
public class PluginMercury : MonoBehaviour
{

#if UNITY_ANDROID
    public static AndroidJavaObject _plugin;
#elif UNITY_IPHONE
    [DllImport ("__Internal")]
    private static extern void show_video_IOS();
    [DllImport ("__Internal")]
    private static extern void show_insert_IOS();
    [DllImport ("__Internal")]
    private static extern void show_banner_IOS();
    [DllImport ("__Internal")]
    private static extern void show_push_IOS();
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
    public void Exchange()
    {
#if UNITY_EDITOR
        print("[UNITY_EDITOR]->Exchange()");
#elif UNITY_ANDROID
        print("[Android]->Exchange()");_plugin.Call("Exchange");
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
    public void show_video()
    {
#if UNITY_EDITOR
        print("[UNITY_EDITOR]->show_video()");
#elif UNITY_ANDROID
        print("[Android]->show_video()");_plugin.Call("show_video");
#elif UNITY_IPHONE
        print("[UNITY_IPHONE]->show_video()");
        show_video_IOS();
#endif
    }

    public void show_insert()
    {
#if UNITY_EDITOR
        print("[UNITY_EDITOR]->show_insert()");
#elif UNITY_ANDROID
        print("[Android]->show_insert()");_plugin.Call("show_insert");
#elif UNITY_IPHONE
        print("[UNITY_IPHONE]->show_insert()");
        show_insert_IOS();
#endif
    }
    public void show_banner()
    {
#if UNITY_EDITOR
        print("[UNITY_EDITOR]->show_banner()");
#elif UNITY_ANDROID
        print("[Android]->show_banner()");_plugin.Call("show_banner");
#elif UNITY_IPHONE
        print("[UNITY_IPHONE]->show_banner()");
        show_banner_IOS();
#endif
    }
    public void show_push()
    {
#if UNITY_EDITOR
        print("[UNITY_EDITOR]->show_push()");
#elif UNITY_ANDROID
        print("[Android]->show_push()");_plugin.Call("show_push");
#elif UNITY_IPHONE
        print("[UNITY_IPHONE]->show_push()");
        show_push_IOS();
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
    public void onFunctionCallBack(string msg)
    {
        print("[Unity]->onFunctionCallBack");
    }

}

