#import "UnityAppController.h"
@interface OverrideAppDelegate : UnityAppController
@end


IMPL_APP_CONTROLLER_SUBCLASS(OverrideAppDelegate)


@implementation OverrideAppDelegate


-(BOOL)application:(UIApplication*) application didFinishLaunchingWithOptions:(NSDictionary*) options
{
    NSLog(@"[OverrideAppDelegate application:%@ didFinishLaunchingWithOptions:%@]", application, options);
    return [super application:application didFinishLaunchingWithOptions:options];
    
}


@end
