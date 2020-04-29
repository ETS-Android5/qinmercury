#import "UnityAppController.h"
// #import "WXApi.h"

@interface CustomAppController : UnityAppController
@end

IMPL_APP_CONTROLLER_SUBCLASS (CustomAppController)

@implementation CustomAppController

- (BOOL)application:(UIApplication*)application didFinishLaunchingWithOptions:(NSDictionary*)launchOptions
{
    [super application:application didFinishLaunchingWithOptions:launchOptions];

    // [WXApi registerApp: @"_________"];
    NSLog(@"this is application init");
    return YES;
}
@end