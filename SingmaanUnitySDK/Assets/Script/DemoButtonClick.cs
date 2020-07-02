using UnityEngine;
using System.Collections;
using UnityEngine.UI;
using System;

public class DemoButtonClick : MonoBehaviour {

    //public Button btnInit;
    public Button btnPay;
    public Button btnExit;
    public Button btnCdkey;
    public Button btnCloud;
    public Button btOrder;
    public Button btClear;
    public Button btMsg;
    public Button btShowInsert;
    public Button btnInterstitial;
    public Button btnShowBanner;
    public Button btnShowPush;
    public Button btnShowVideo;
    public Button btnRate;
    public Button btnShareWechat;
    public Button btnShareFriends;
    public Texture2D texture;
    public Text logtext;
    // Use this for initialization
    void Start() {
        // logtext.text = "";
        // btnExit.onClick.AddListener(BtExitClick);
        // btMsg.onClick.AddListener(BtMsgClick);
        // btShowInsert.onClick.AddListener(BtnShowInsertClick);
        // btnShowBanner.onClick.AddListener(BtnShowBannerClick);
        // btnShowPush.onClick.AddListener(BtnShowPushClick);
        // btnShowVideo.onClick.AddListener(BtnShowVideoClick);
    }


    /*public void BtInitClick(){
        logtext.text += "初始化按钮点击"+"\n";
    }*/
    public void BtPayClick()
    {
        logtext.text += "支付按钮点击" + "\n";
        // E2WSdk.Instance.Buy("StrProduct");
        PluginMercury.Instance.Purchase("pid");
    }
    public void BtExitClick()
    {
        logtext.text += "退出按钮点击" + "\n";
        PluginMercury.Instance.ExitGame();
    }


    public void BtMsgClick()
    {
        // E2WSdk.Instance.Message("Message");
    }

    public void BtProductClick(string strProduct)
    {
        PluginMercury.Instance.Purchase(strProduct);
    }

    public void BtScreenClickFlipping(bool isPortrait) {
        if (isPortrait)
        {
            Application.LoadLevel("01");
        }
        else {
            Application.LoadLevel("02");
        }
    }
    public void BtnShowInsertClick() {
        logtext.text += "显示插屏图片，广告参数=" + "mainmenu\n";
        PluginMercury.Instance.ActiveInterstitial();
    }
    public void BtnShowBannerClick() {
        logtext.text += "显示横幅广告，广告参数=" + "mainmenu\n";
        PluginMercury.Instance.ActiveBanner();
    }
    public void BtnShowPushClick() {
        logtext.text += "显示推送广告，广告参数=" + "mainmenu\n";
        PluginMercury.Instance.ActiveNative();
    }
    public void BtnShowVideoClick() {
        logtext.text += "显示视频广告，广告参数=" + "mainmenu\n";
        PluginMercury.Instance.ActiveRewardVideo();
    }
    
    public void BtnShowInterstitial()
    {
        logtext.text += "显示插屏视频，广告参数=" + "mainmenu\n";
        PluginMercury.Instance.ActiveInterstitial();
    }

    public void BtnReedem()
    {
        logtext.text += "显示插屏视频，广告参数=" + "mainmenu\n";
        PluginMercury.Instance.Redeem();
    }

    public void BtnRestoreProduct()
    {
        logtext.text += "显示插屏视频，广告参数=" + "mainmenu\n";
        PluginMercury.Instance.RestoreProduct();
    }

    public void SingmaanLogin()
    {
        logtext.text += "显示插屏视频，广告参数=" + "mainmenu\n";
        PluginMercury.Instance.SingmaanLogin();
    }

    public void SingmaanLogout()
    {
        logtext.text += "显示插屏视频，广告参数=" + "mainmenu\n";
        PluginMercury.Instance.SingmaanLogout();
    }

        public void UploadGameData()
    {
        logtext.text += "显示插屏视频，广告参数=" + "mainmenu\n";
        PluginMercury.Instance.UploadGameData();
    }

    public void DownloadGameData()
    {
        logtext.text += "显示插屏视频，广告参数=" + "mainmenu\n";
        PluginMercury.Instance.DownloadGameData();
    }


    public void RateGame()
    {
        logtext.text += "显示插屏视频，广告参数=" + "mainmenu\n";
        PluginMercury.Instance.RateGame();
    }

    public void ShareGame()
    {
        logtext.text += "显示插屏视频，广告参数=" + "mainmenu\n";
        PluginMercury.Instance.ShareGame();
    }

    public void OpenGameCommunity()
    {
        logtext.text += "显示插屏视频，广告参数=" + "mainmenu\n";
        PluginMercury.Instance.OpenGameCommunity();
    }

    public void GetProductionInfo()
    {
        logtext.text += "显示插屏视频，广告参数=" + "mainmenu\n";
        PluginMercury.Instance.GetProductionInfo();
    }
}
