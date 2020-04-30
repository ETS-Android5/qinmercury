#import "UnityAppController.h"
#import <BUAdSDK/BUAdSDKManager.h>

@interface OverrideAppDelegate : UnityAppController
@end


IMPL_APP_CONTROLLER_SUBCLASS(OverrideAppDelegate)


@implementation OverrideAppDelegate


-(BOOL)application:(UIApplication*) application didFinishLaunchingWithOptions:(NSDictionary*) options
{
    NSLog(@"[OverrideAppDelegate application:%@ didFinishLaunchingWithOptions:%@]", application, options);
    
    NSLog(@"this is application object-c");
    UnitySendMessage("PluginMercury", "onFunctionCallBack", "GameInit success");
    [BUAdSDKManager setAppID:@"5063017"];
    [BUAdSDKManager setIsPaidApp:NO];
    [BUAdSDKManager setLoglevel:BUAdSDKLogLevelDebug];
    return [super application:application didFinishLaunchingWithOptions:options];
    
}


@end
