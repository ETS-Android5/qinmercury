#import "QinMercury.h"
#import "IAPManager.h"

static QinMercury *instance;
@implementation QinMercury

+(QinMercury *) getAdInstance{
    return instance;
}
+(void) GameInit
{
    NSLog(@"this is GameInit self object-c");
    UnitySendMessage("PluginMercury", "onFunctionCallBack", "GameInit");
}

+(void) ActiveRewardVideo_IOS
{
    NSLog(@"this is ActiveRewardVideo_IOS object-c");
    UnitySendMessage("PluginMercury", "AdShowSuccessCallBack", "ActiveRewardVideo_IOS");
    UnitySendMessage("PluginMercury", "AdLoadSuccessCallBack", "ActiveRewardVideo_IOS");
}
+(void) ActiveInterstitial_IOS
{
    NSLog(@"this is ActiveInterstitial_IOS object-c");
    UnitySendMessage("PluginMercury", "AdShowSuccessCallBack", "ActiveInterstitial_IOS");
    UnitySendMessage("PluginMercury", "AdLoadSuccessCallBack", "ActiveRewardVideo_IOS");
}
+(void) ActiveBanner_IOS
{
    NSLog(@"this is ActiveBanner_IOS object-c");
    UnitySendMessage("PluginMercury", "AdShowSuccessCallBack", "ActiveBanner_IOS");
    UnitySendMessage("PluginMercury", "AdLoadSuccessCallBack", "ActiveRewardVideo_IOS");
}
+(void) ActiveNative_IOS
{
    NSLog(@"this is ActiveNative_IOS object-c");
    UnitySendMessage("PluginMercury", "AdShowSuccessCallBack", "ActiveNative_IOS");
    UnitySendMessage("PluginMercury", "AdLoadSuccessCallBack", "ActiveRewardVideo_IOS");
}

#if defined (__cplusplus)
extern "C"
{
#endif
    IAPManager *iapManager = nil;
    void BuyProduct(char *p){
        if(nil == iapManager){//初始化
            iapManager = [[IAPManager alloc] init];
            [iapManager attachObserver];
        }
        NSString *pid = [NSString stringWithUTF8String:p];
        NSLog(@"商品编码:%@",pid);
        //pid = [NSString stringWithFormat:@"com.singmaan.sdk.6"];
        //商品信息
        [iapManager requestProductData:pid];
        //购买商品
        [iapManager buyRequest:pid];
    }
#if defined (__cplusplus)
}
#endif
@end


