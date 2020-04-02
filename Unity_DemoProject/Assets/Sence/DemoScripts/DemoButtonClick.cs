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
        logtext.text = "";
        //btnInit.onClick.AddListener(BtInitClick);
        btnExit.onClick.AddListener(BtExitClick);
        btnCdkey.onClick.AddListener(BtCdkeyClick);
        btnCloud.onClick.AddListener(BtCloudClick);
        btOrder.onClick.AddListener(BtOrderClick);
        btClear.onClick.AddListener(BtClearClick);
        btMsg.onClick.AddListener(BtMsgClick);
        btnRate.onClick.AddListener(BtRateClick);
        btShowInsert.onClick.AddListener(BtnShowInsertClick);
        btnShowBanner.onClick.AddListener(BtnShowBannerClick);
        btnShowPush.onClick.AddListener(BtnShowPushClick);
        btnShowVideo.onClick.AddListener(BtnShowVideoClick);
        // btnInterstitial.onClick.AddListener(BtnShowInterstitial);
        btnShareFriends.onClick.AddListener(BtnShareToFriends);
        btnShareWechat.onClick.AddListener(BtnShareToWechat);
        // btnShowVideo.gameObject.SetActive(E2WSdk.isADs[3]);
        //RateGame.Instance.OnSubmitRating += SubmitRating;
        //RateGame.Instance.OnRatedFailed += RatedFailed;
        //RateGame.Instance.OnRatedSuccess += RatedSuccess;
    }


    private void OnDisable()
    {
        //RateGame.Instance.OnSubmitRating -= SubmitRating;
        //RateGame.Instance.OnRatedFailed -= RatedFailed;
        //RateGame.Instance.OnRatedSuccess -= RatedSuccess;
    }

    private void RatedSuccess()
    {
        logtext.text += "回调：提交成功" + "\n";
    }

    private void RatedFailed()
    {
        logtext.text += "回调：提交失败" + "\n";
    }

    private void SubmitRating(string arg1, string arg2)
    {
        logtext.text += "回调：正在提交" + "\n";
    }

    // Update is called once per frame
    void Update() {

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
    public void BtCdkeyClick()
    {
        logtext.text += "兑换码按钮点击" + "\n";
        // E2WSdk.Instance.Exchange();

    }
    public void BtCloudClick()
    {
        logtext.text += "云存储按钮点击" + "\n";
        //E2WSdk.Instance.Cloud();
    }
    public void BtOrderClick()
    {
        logtext.text += "(模拟补单效果测试),已经成功掉单商品2，请重启游戏(五秒后自动退出)，获得商品" + "\n";
        PurchanseProducts.Instance.IssueOrdeResend();
    }
    /// <summary>
    /// 模拟调单，程序退出
    /// </summary>
    private void IssueOrdeQuit() { Application.Quit(); }
    public void BtClearClick()
    {
        logtext.text = "";
        FindObjectOfType<Product>().ClearCoin();
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
        PluginMercury.Instance.show_insert();
    }
    public void BtnShowBannerClick() {
        logtext.text += "显示横幅广告，广告参数=" + "mainmenu\n";
        PluginMercury.Instance.show_banner();
    }
    public void BtnShowPushClick() {
        logtext.text += "显示推送广告，广告参数=" + "mainmenu\n";
        PluginMercury.Instance.show_push();
    }
    public void BtnShowVideoClick() {
        logtext.text += "显示视频广告，广告参数=" + "mainmenu\n";
        PluginMercury.Instance.show_video();
    }
    
    public void BtnShowInterstitial()
    {
        logtext.text += "显示插屏视频，广告参数=" + "mainmenu\n";
    }
    private void BtRateClick()
    {
    }

    private void BtnShareToFriends()
    {
        logtext.text += "正在分享到微信好友";
        byte[] bytes = texture.EncodeToPNG();
        System.IO.File.WriteAllBytes(Application.persistentDataPath + "/Shot4Share.png", bytes);
    }
    private void BtnShareToWechat()
    {
        logtext.text += "正在分享到微信朋友圈";
        byte[] bytes = texture.EncodeToPNG();
        System.IO.File.WriteAllBytes(Application.persistentDataPath + "/Shot4Share.png", bytes);
    }
}
