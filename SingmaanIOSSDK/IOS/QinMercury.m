#import "QinMercury.h"
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
    if GADRewardBasedVideoAd.sharedInstance().isReady == true {
    GADRewardBasedVideoAd.sharedInstance().present(fromRootViewController: self)
    }
}
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
@end


