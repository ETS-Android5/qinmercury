
#import <BUAdSDK/BUAdSDKManager.h>
#import "BUAdSDK/BUSplashAdView.h"
#import <BUAdSDK/BUAdSDK.h>
#import "QinMercury.h"
@interface QinMercury ()<BUNativeExpressRewardedVideoAdDelegate>
@property (nonatomic,strong) BUNativeExpressRewardedVideoAd *rewardedVideoAd;
@end
static QinMercury *instance;
@implementation QinMercury

+(QinMercury *) getAdInstance{
    return instance;
}
-(void) show_video_IOS{
    NSLog(@"this is show_video_IOS object-c");
   if(self.rewardedVideoAd.isAdValid){
       [self.rewardedVideoAd showAdFromRootViewController:self];
    }
}
void show_video_IOS()
{
    NSLog(@"this is show_video_IOS swift");
}
void show_insert_IOS()
{
    NSLog(@"%s this is new string ");
}
void show_banner_IOS(NSString* name)
{
    NSLog(@"%s this is new string ",name);
}
void show_push_IOS(NSString* name)
{
    NSLog(@"%s this is new string ",name);
}
void GameInit()
{
    NSLog(@"this is GameInit swift");
    // splash AD demo
    UnitySendMessage("PluginMercury", "onFunctionCallBack", "GameInit success");
//    [BUAdSDKManager setAppID:@"xxxxxx"]; //xxxxxx是你在穿山甲后台对应的appid
//    [BUAdSDKManager setIsPaidApp:NO];
//    [BUAdSDKManager setLoglevel:BUAdSDKLogLevelDebug];
//
//
//    [self.rewardedVideoAd loadAdData];
//    float scale = [[UIScreen mainScreen] scale];
//    CGRect frame=[UIScreen mainScreen].bounds;
//    BUSplashAdView *splashView = [[BUSplashAdView alloc] initWithSlotID:@"yyyyyyyyyy" frame:frame];
//    //yyyyyyyyyy是你在穿山甲后台对应的广告位Id
//    splashView.delegate = self;
//    UIWindow *keyWindow =[UIApplication sharedApplication].windows.firstObject;
//    [splashView loadAdData];
//    [keyWindow.rootViewController.view addSubview:splashView];
//    splashView.rootViewController = keyWindow.rootViewController;
}

- (void) GameInit
{
    NSLog(@"this is GameInit object-c");
    // splash AD demo
    UnitySendMessage("PluginMercury", "onFunctionCallBack", "GameInit success");
    [BUAdSDKManager setAppID:@"xxxxxx"]; //xxxxxx是你在穿山甲后台对应的appid
    [BUAdSDKManager setIsPaidApp:NO];
    [BUAdSDKManager setLoglevel:BUAdSDKLogLevelDebug];

    
    [self.rewardedVideoAd loadAdData];
//    float scale = [[UIScreen mainScreen] scale];
//    CGRect frame=[UIScreen mainScreen].bounds;
//    BUSplashAdView *splashView = [[BUSplashAdView alloc] initWithSlotID:@"yyyyyyyyyy" frame:frame];
//    //yyyyyyyyyy是你在穿山甲后台对应的广告位Id
//    splashView.delegate = self;
//    UIWindow *keyWindow =[UIApplication sharedApplication].windows.firstObject;
//    [splashView loadAdData];
//    [keyWindow.rootViewController.view addSubview:splashView];
//    splashView.rootViewController = keyWindow.rootViewController;
}

-(void) viewDidLoad
{
        NSLog(@"viewDidLoad");
        instance=self;
        [BUAdSDKManager setAppID:@"5043574"];
        [BUAdSDKManager setIsPaidApp:NO];
        [BUAdSDKManager setLoglevel:BUAdSDKLogLevelDebug];
        
        BURewardedVideoModel *model=[[BURewardedVideoModel alloc]init];
        model.userId=@"ming123";
        self.rewardedVideoAd=[[BUNativeExpressRewardedVideoAd alloc] initWithSlotID:@"945009897" rewardedVideoModel:model];
        self.rewardedVideoAd.delegate=self;
        [self.rewardedVideoAd loadAdData];
}

- (void)nativeExpressRewardedVideoAdDidClose:(BUNativeExpressRewardedVideoAd *)rewardedVideoAd{
    NSLog(@"rewardedVideoAdDidClose");
    [self.rewardedVideoAd loadAdData];        //视频结束后，再加载一次广告数据，保证广告的不重复
}
@end
