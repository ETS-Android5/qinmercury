#import "QinMercury.h"
#import "SDKInteraction.h"

QinMercury *inter = nil;

void GameInit()
{
    NSLog(@"this is GameInit swift");
    inter = [[QinMercury alloc]init];
    [inter GameInit];
    
}
void ActiveRewardVideo_IOS()
{
    NSLog(@"this is show_video_IOS swift");
    [inter ActiveRewardVideo_IOS];
}
void ActiveInterstitial_IOS()
{
    NSLog(@"this is show_insert_IOS swift");
    [inter ActiveInterstitial_IOS];
}

void ActiveBanner_IOS()
{
    NSLog(@"this is show_banner_IOS swift");
    [inter ActiveBanner_IOS];
}

void ActiveNative_IOS()
{
    NSLog(@"this is show_push_IOS swift");
    [inter ActiveNative_IOS];
}




