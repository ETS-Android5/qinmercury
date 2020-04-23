
#import "QinMercury.h"


static QinMercury *instance;

@implementation QinMercury

+(QinMercury *) getAdInstance{
    return instance;
}
-(void) GameInit
{
    NSLog(@"this is GameInit object-c");
    UnitySendMessage("PluginMercury", "onFunctionCallBack", "GameInit success");
}
-(void) ActiveRewardVideo_IOS
{
    NSLog(@"this is ActiveRewardVideo_IOS object-c");
}
-(void) ActiveInterstitial_IOS
{
    NSLog(@"this is ActiveInterstitial_IOS object-c");
}
-(void) ActiveBanner_IOS
{
    NSLog(@"this is ActiveBanner_IOS object-c");
}
-(void) ActiveNative_IOS
{
    NSLog(@"this is ActiveNative_IOS object-c");
}

@end


