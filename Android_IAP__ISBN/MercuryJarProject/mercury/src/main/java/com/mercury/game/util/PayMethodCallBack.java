package com.mercury.game.util;

/**
 * @Author: Risemy
 * Create Time:2020/7/30
 */
public interface PayMethodCallBack {
    void Alipay(String msg);

    void WechatPay(String msg);
}
