#import "QinMercury.h"
#import "SDKInteraction.h"

static QinMercury *inter = nil;

void GameInit()
{
    NSLog(@"this is GameInit SDKInteraction");
    [QinMercury GameInit];
}
void ActiveRewardVideo_IOS()
{
    NSLog(@"this is ActiveRewardVideo_IOS SDKInteraction");
    [QinMercury ActiveRewardVideo_IOS];
}
void ActiveInterstitial_IOS()
{
    NSLog(@"this is ActiveInterstitial_IOS SDKInteraction");
    [QinMercury ActiveInterstitial_IOS];
}

void ActiveBanner_IOS()
{
    NSLog(@"this is show_banner_IOS SDKInteraction");
    [QinMercury ActiveBanner_IOS];
}

void ActiveNative_IOS()
{
    NSLog(@"this is show_push_IOS SDKInteraction");
    [QinMercury ActiveNative_IOS];
}




