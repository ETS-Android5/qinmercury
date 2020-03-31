using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class PurchanseProducts : MonoBehaviour {
    public static PurchanseProducts pInstance;

    public static PurchanseProducts Instance
    {
        get
        {
            if (pInstance == null)
                pInstance = FindObjectOfType<PurchanseProducts>();
            return pInstance;
        }
    }


    private void OnEnable()
    {
        // E2WSdkEvent.onPurchaseSuccessEvent += BuySuccess;
        // E2WSdkEvent.onPurchaseFailEvent += BuyFail;
        // E2WSdkEvent.onExchangeSuccessEvent += ExchangeSuccess;
        // E2WSdkEvent.onRewardedVideoAdRewardedEvent += RewardVideoRewarded;
    }


    private void OnDisable()
    {
        // E2WSdkEvent.onPurchaseSuccessEvent -= BuySuccess;
        // E2WSdkEvent.onPurchaseFailEvent -= BuyFail;
        // E2WSdkEvent.onExchangeSuccessEvent -= ExchangeSuccess;
        // E2WSdkEvent.onRewardedVideoAdRewardedEvent -= RewardVideoRewarded;
    }
    void Start() {

    }

    // Update is called once per frame
    void Update() {

    }

    /// <summary>
    /// 物品购买成功
    /// </summary>
    public void BuySuccess(string strProductId)
    {
        print("[BuySuccess]->strProductId=" + strProductId);
        GameObject.Find("logtext").GetComponent<Text>().text += "+BuySuccess=" + strProductId + "\n";
        FindObjectOfType<Product>().BuySuccess(strProductId);
        
        switch (strProductId)
        {
            #region
            case "GiftRecoder":

                break;
                #endregion
        }
    }
    public void BuyFail(string strProductId) {
        GameObject.Find("logtext").GetComponent<Text>().text += "+BuyFaile=" + strProductId + "\n";
        print("[BuyFaile]->strProductId=" + strProductId);
    }

    /// <summary>
    /// 物品兑换成功
    /// </summary>
    public void ExchangeSuccess(string pid) {
        GameObject.Find("logtext").GetComponent<Text>().text += "+ExchangeSuccess=" + pid + "\n";
        FindObjectOfType<Product>().ExchangeSuccess(pid);
        PlayerPrefs.SetInt("IssueOrdeRsresend", 0);
    }

    private void RewardVideoRewarded(string obj)
    {
        FindObjectOfType<Product>().RewardVideoRewarded(obj);
    }
    #region 模拟补单

    /// <summary>
    /// 模拟补单
    /// </summary>
    public void IssueOrdeResend()
    {
        PlayerPrefs.SetInt("IssueOrdeRsresend", 1);
    }
    #endregion

}
