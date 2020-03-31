using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class Product : MonoBehaviour
{
    public Text coinText;
    private int coin = 0;//金币

    // Use this for initialization
    void Start()
    {

    }

    // Update is called once per frame
    void Update()
    {
        
    }



    private void SetCoinText(int num)
    {
        coinText.text = "金币：" + coin;
    }
    public void BuySuccess(string strProduct)
    {
        switch (strProduct)
        {
            case "1": coin += 500;SetCoinText(coin); Debug.Log("11++"+coin); break;
            case "2": coin += 1000; SetCoinText(coin); Debug.Log("22++" + coin); break;
            case "3": coin += 3000; SetCoinText(coin); Debug.Log("33++" + coin); break;
            default:
                break;
        }
    }

    public void RewardVideoRewarded(string obj)
    {
        coin += 500;
        SetCoinText(coin);
    }

    public void ExchangeSuccess(string strProduct)
    {
        switch (strProduct)
        {
            case "1": coin += 500; SetCoinText(coin); break;
            case "2": coin += 1000; SetCoinText(coin); break;
            case "3": coin += 3000; SetCoinText(coin); break;
            default:
                break;
        }
    }
    public void ClearCoin()
    {
        coin = 0;
        SetCoinText(0);
    }
}
