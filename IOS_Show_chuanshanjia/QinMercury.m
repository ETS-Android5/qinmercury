
#import "QinMercury.h"

#import "BUAdSDK/BUSplashAdView.h"
#import <BUAdSDK/BUAdSDK.h>
#import <BUAdSDK/BURewardedVideoModel.h>
#import <BUAdSDK/BUNativeExpressRewardedVideoAd.h>
@interface QinMercury ()<BUNativeExpressRewardedVideoAdDelegate>
@end
static QinMercury *instance;

@implementation QinMercury

BUNativeExpressRewardedVideoAd *rewardedAd;
bool isAdReady;
bool isAdComplete;

+(QinMercury *) getAdInstance{
    return instance;
}
+(void) GameInit
{
     NSLog(@"this is GameInit self object-c%@",self);
     isAdReady = false;
     isAdComplete = false;
     BURewardedVideoModel *model = [[BURewardedVideoModel alloc] init];
     model.userId = @"";
     rewardedAd = [[BUNativeExpressRewardedVideoAd alloc] initWithSlotID:@"945155188" rewardedVideoModel:model];
     rewardedAd.delegate = self;
     [rewardedAd loadAdData];
    
}
+(void) ActiveRewardVideo_IOS
{
    if(!isAdReady)
    {
        NSLog(@"loading AD");
        [rewardedAd loadAdData];
    }
    UIWindow * window = [[UIApplication sharedApplication] keyWindow];
    [rewardedAd showAdFromRootViewController:window.rootViewController];

}
+(void) ActiveInterstitial_IOS
{
    NSLog(@"this is ActiveInterstitial_IOS object-c");
}
+(void) ActiveBanner_IOS
{
    NSLog(@"this is ActiveBanner_IOS object-c");
}
+(void) ActiveNative_IOS
{
    NSLog(@"this is ActiveNative_IOS object-c");
}
#pragma mark BUNativeExpressRewardedVideoAdDelegate
+ (void)nativeExpressRewardedVideoAdDidLoad:(BUNativeExpressRewardedVideoAd *)rewardedVideoAd {
    NSLog(@"BUNativeExpressRewardedVideoAdDelegate %s",__func__);
}
+ (void)nativeExpressRewardedVideoAd:(BUNativeExpressRewardedVideoAd *)rewardedVideoAd didFailWithError:(NSError *_Nullable)error {
    NSLog(@"nativeExpressRewardedVideoAd %s",__func__);
//    [_rewardBasedVideoAdConnector adapter:self didFailToLoadRewardBasedVideoAdwithError:error];
}

+ (void)nativeExpressRewardedVideoAdDidDownLoadVideo:(BUNativeExpressRewardedVideoAd *)rewardedVideoAd {
    NSLog(@"nativeExpressRewardedVideoAdDidDownLoadVideo %s",__func__);
}

+ (void)nativeExpressRewardedVideoAdViewRenderSuccess:(BUNativeExpressRewardedVideoAd *)rewardedVideoAd {
    NSLog(@"nativeExpressRewardedVideoAdViewRenderSuccess %s",__func__);
}

+ (void)nativeExpressRewardedVideoAdViewRenderFail:(BUNativeExpressRewardedVideoAd *)rewardedVideoAd error:(NSError *_Nullable)error {
    NSLog(@"nativeExpressRewardedVideoAdViewRenderFail %s",__func__);
}

+ (void)nativeExpressRewardedVideoAdWillVisible:(BUNativeExpressRewardedVideoAd *)rewardedVideoAd {
    NSLog(@"nativeExpressRewardedVideoAdWillVisible %s",__func__);
}

+ (void)nativeExpressRewardedVideoAdDidVisible:(BUNativeExpressRewardedVideoAd *)rewardedVideoAd {
    NSLog(@"nativeExpressRewardedVideoAdDidVisible %s",__func__);
}

+ (void)nativeExpressRewardedVideoAdWillClose:(BUNativeExpressRewardedVideoAd *)rewardedVideoAd {
    NSLog(@"nativeExpressRewardedVideoAdWillClose %s",__func__);
}

+ (void)nativeExpressRewardedVideoAdDidClose:(BUNativeExpressRewardedVideoAd *)rewardedVideoAd {
    NSLog(@"nativeExpressRewardedVideoAdDidClose %s",__func__);
}

+ (void)nativeExpressRewardedVideoAdDidClick:(BUNativeExpressRewardedVideoAd *)rewardedVideoAd {
    NSLog(@"nativeExpressRewardedVideoAdDidClick %s",__func__);
}

+ (void)nativeExpressRewardedVideoAdDidClickSkip:(BUNativeExpressRewardedVideoAd *)rewardedVideoAd {
    NSLog(@"nativeExpressRewardedVideoAdDidClickSkip %s",__func__);
}

+ (void)nativeExpressRewardedVideoAdDidPlayFinish:(BUNativeExpressRewardedVideoAd *)rewardedVideoAd didFailWithError:(NSError *_Nullable)error {
    NSLog(@"nativeExpressRewardedVideoAdDidPlayFinish %s",__func__);
    [rewardedAd loadAdData];
}

+ (void)nativeExpressRewardedVideoAdServerRewardDidSucceed:(BUNativeExpressRewardedVideoAd *)rewardedVideoAd verify:(BOOL)verify {
    NSLog(@"nativeExpressRewardedVideoAdServerRewardDidSucceed %s",__func__);
    UnitySendMessage("PluginMercury", "onFunctionCallBack", "nativeExpressRewardedVideoAdServerRewardDidSucceed");
}

+ (void)nativeExpressRewardedVideoAdServerRewardDidFail:(BUNativeExpressRewardedVideoAd *)rewardedVideoAd {
    NSLog(@"nativeExpressRewardedVideoAdServerRewardDidFail %s",__func__);
}

@end


