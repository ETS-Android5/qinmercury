#import <BUAdSDK/BUAdSDKManager.h>
#import "BUAdSDK/BUSplashAdView.h"
#import <BUAdSDK/BUAdSDK.h>
#import "QinMercury.h"

#import <BUAdSDK/BUAdSDKManager.h>
#import <BUAdSDK/BURewardedVideoModel.h>
#import <BUAdSDK/BUNativeExpressRewardedVideoAd.h>
@interface QinMercury ()<BUNativeExpressRewardedVideoAdDelegate>
@property (nonatomic,strong) BUNativeExpressRewardedVideoAd *rewardedVideoAd;
@end
static QinMercury *instance;

@implementation QinMercury

+(QinMercury *) getAdInstance{
    return instance;
}
-(void) GameInit
{
    NSLog(@"this is GameInit object-c");
    UnitySendMessage("PluginMercury", "onFunctionCallBack", "GameInit success");
    [BUAdSDKManager setAppID:@"5059494"];
    [BUAdSDKManager setIsPaidApp:NO];
    [BUAdSDKManager setLoglevel:BUAdSDKLogLevelDebug];
    
    BURewardedVideoModel *model=[[BURewardedVideoModel alloc]init];
    model.userId=@"ming123";
    self.rewardedVideoAd=[[BUNativeExpressRewardedVideoAd alloc] initWithSlotID:@"945132328" rewardedVideoModel:model];
    self.rewardedVideoAd.delegate=self;
    [self.rewardedVideoAd loadAdData];
}
-(void) show_video_IOS
{
    NSLog(@"this is show_video_IOS object-c");
    if(self.rewardedVideoAd.isAdValid){
        NSLog(@"this is show_video_IOS isAdValid");
        dispatch_async(dispatch_get_main_queue(), ^{
        [self.rewardedVideoAd showAdFromRootViewController:self];
            });
     }
    else
    {
        NSLog(@"this is show_video_IOS AdValid");
        [self.rewardedVideoAd loadAdData];
    }
}
-(void) show_insert_IOS
{
    NSLog(@"this is show_insert_IOS object-c");
}
-(void) show_banner_IOS
{
    NSLog(@"this is show_banner_IOS object-c");
}
-(void) show_push_IOS
{
    NSLog(@"this is show_push_IOS object-c");
}
- (void)nativeExpressRewardedVideoAdDidClose:(BUNativeExpressRewardedVideoAd *)rewardedVideoAd{
    NSLog(@"rewardedVideoAdDidClose");
    [self.rewardedVideoAd loadAdData];        //视频结束后，再加载一次广告数据，保证广告的不重复
    UnitySendMessage("PluginMercury", "onFunctionCallBack", "ad success");
}
@end


