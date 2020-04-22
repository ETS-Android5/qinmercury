
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
-(void) show_video_IOS
{
    NSLog(@"this is show_video_IOS object-c");
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

@end


