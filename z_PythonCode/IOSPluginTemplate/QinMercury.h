//
//  BUAdManager.h
//  BUDemo
//
//  Created by carlliu on 2017/7/27.
//  Copyright © 2017年 chenren. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface QinMercury : NSObject
+ (NSString *)appKey;
+(QinMercury *) getAdInstance;            //用来获取实例的方法
-(void) show_video_IOS;
-(void) GameInit;
@end

