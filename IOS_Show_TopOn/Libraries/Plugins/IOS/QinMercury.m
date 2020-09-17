#import "QinMercury.h"
#import "IAPManager.h"
#import <AnyThinkSDK/AnyThinkSDK.h>
#import <AnyThinkRewardedVideo/AnyThinkRewardedVideo.h>

@interface QinMercury()<ATRewardedVideoDelegate>
//Other properties methods declarations
@end

@implementation QinMercury
+(instancetype) getAdInstance{
    static QinMercury *instance = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        instance = [[QinMercury alloc] init];
    });
    return instance;
}

+(void) GameInit
{
    NSLog(@"this is GameInit self object-c");
    UnitySendMessage("PluginMercury", "onFunctionCallBack", "GameInit");
    [ATAPI setLogEnabled:YES];//Turn on debug logs
    [ATAPI integrationChecking];
    
    [[ATAPI sharedInstance] startWithAppID:@"a5f62deb4e1266" appKey:@"8cd3e6589494074cc2d8bc1be31656e2" error:nil];
    
    [[ATAdManager sharedManager] loadADWithPlacementID:@"b5f62deda3b0f3" extra:@{kATAdLoadingExtraUserIDKey:@"test_user_id"} delegate:[QinMercury getAdInstance]];

}

+(void) ActiveRewardVideo_IOS
{
    NSLog(@"this is ActiveRewardVideo_IOS object-c");
    if ([[ATAdManager sharedManager] rewardedVideoReadyForPlacementID:@"b5f62deda3b0f3"]) {
        //Show rv here
        [[ATAdManager sharedManager] showRewardedVideoWithPlacementID:@"b5f62deda3b0f3"  inViewController:[UIApplication sharedApplication].delegate.window.rootViewController delegate:[QinMercury getAdInstance]];
    } else {
        //Load rv here
         [[ATAdManager sharedManager] loadADWithPlacementID:@"b5f62deda3b0f3" extra:@{kATAdLoadingExtraUserIDKey:@"test_user_id"} delegate:[QinMercury getAdInstance]];
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
#pragma mark - loading delegate
-(void) didFinishLoadingADWithPlacementID:(NSString *)placementID {
    NSLog(@"RV Demo: didFinishLoadingADWithPlacementID");
     UnitySendMessage("PluginMercury", "AdLoadSuccessCallBack", "ActiveRewardVideo_IOS");
}

-(void) didFailToLoadADWithPlacementID:(NSString* )placementID error:(NSError *)error {
    NSLog(@"RV Demo: failed to load:%@", error);
}

-(void) rewardedVideoDidRewardSuccessForPlacemenID:(NSString *)placementID extra:(NSDictionary *)extra{
    NSLog(@"ATRewardedVideoVideoViewController::rewardedVideoDidRewardSuccessForPlacemenID:%@ extra:%@",placementID,extra);
    UnitySendMessage("PluginMercury", "AdShowSuccessCallBack", "ActiveRewardVideo_IOS");
}

-(void) rewardedVideoDidStartPlayingForPlacementID:(NSString *)placementID extra:(NSDictionary *)extra {
    NSLog(@"ATRewardedVideoVideoViewController::rewardedVideoDidStartPlayingForPlacementID:%@ extra:%@", placementID, extra);
}

-(void) rewardedVideoDidEndPlayingForPlacementID:(NSString*)placementID extra:(NSDictionary *)extra {
    NSLog(@"ATRewardedVideoVideoViewController::rewardedVideoDidEndPlayingForPlacementID:%@ extra:%@", placementID, extra);
}

-(void) rewardedVideoDidFailToPlayForPlacementID:(NSString*)placementID error:(NSError*)error extra:(NSDictionary *)extra {
    NSLog(@"ATRewardedVideoVideoViewController::rewardedVideoDidFailToPlayForPlacementID:%@ error:%@ extra:%@", placementID, error, extra);
}

-(void) rewardedVideoDidCloseForPlacementID:(NSString*)placementID rewarded:(BOOL)rewarded extra:(NSDictionary *)extra {
    NSLog(@"ATRewardedVideoVideoViewController::rewardedVideoDidCloseForPlacementID:%@, rewarded:%@ extra:%@", placementID, rewarded ? @"yes" : @"no", extra);
}

-(void) rewardedVideoDidClickForPlacementID:(NSString*)placementID extra:(NSDictionary *)extra {
    NSLog(@"ATRewardedVideoVideoViewController::rewardedVideoDidClickForPlacementID:%@ extra:%@", placementID, extra);
}


#if defined (__cplusplus)
extern "C"
{
#endif
    IAPManager *iapManager = nil;
    void BuyProduct(char *p){
        if(nil == iapManager){//初始化
            iapManager = [[IAPManager alloc] init];
        }
        [iapManager attachObserver];
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


