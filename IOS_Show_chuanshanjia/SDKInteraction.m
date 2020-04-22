#import "QinMercury.h"
#import "SDKInteraction.h"

QinMercury *inter = nil;

void GameInit()
{
    NSLog(@"this is GameInit swift");
    inter = [[QinMercury alloc]init];
    [inter GameInit];
    
}
void show_video_IOS()
{
    NSLog(@"this is show_video_IOS swift");
    [inter show_video_IOS];
}
void show_insert_IOS()
{
    NSLog(@"this is show_insert_IOS swift");
    [inter show_insert_IOS];
}

void show_banner_IOS()
{
    NSLog(@"this is show_banner_IOS swift");
    [inter show_banner_IOS];
}

void show_push_IOS()
{
    NSLog(@"this is show_push_IOS swift");
    [inter show_push_IOS];
}




