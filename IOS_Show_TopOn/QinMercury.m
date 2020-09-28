#import "QinMercury.h"
#import "IAPManager.h"
#import "TalkingDataGA.h"
#import <AnyThinkSDK/AnyThinkSDK.h>
#import <AnyThinkRewardedVideo/AnyThinkRewardedVideo.h>
@interface QinMercury()<ATRewardedVideoDelegate>
//Other properties methods declarations
@end
static QinMercury *instance;
NSString* const gamename =@"TerraGenesis";
NSString* const backup_url =@"http://192.168.10.7:10010/uploadgamedata";
NSString* const download_url =@"http://192.168.10.7:10010/downloadgamedata";
NSString* const redeem_url =@"http://office.singmaan.com:9989/redeem";
extern NSString *unique_id=@"";

@implementation QinMercury

+(QinMercury *) getAdInstance{
    static QinMercury *instance = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        instance = [[QinMercury alloc] init];
    });
    return instance;
}
+(void) GameInit
{
    NSLog(@"[GameInit]");
    NSString* uuid = [QinMercury getDeviceIDInKeychain];
    unique_id = uuid;
    UnitySendMessage("PluginMercury", "onFunctionCallBack", "GameInit");
    [ATAPI setLogEnabled:YES];//Turn on debug logs
    [ATAPI integrationChecking];
    
    [[ATAPI sharedInstance] startWithAppID:@"a5f62deb4e1266" appKey:@"8cd3e6589494074cc2d8bc1be31656e2" error:nil];
    
    [[ATAdManager sharedManager] loadADWithPlacementID:@"b5f62deda3b0f3" extra:@{kATAdLoadingExtraUserIDKey:@"test_user_id"} delegate:[QinMercury getAdInstance]];


}

+(void) ActiveRewardVideo_IOS
{
    NSLog(@"[ActiveRewardVideo_IOS]");
    if ([[ATAdManager sharedManager] rewardedVideoReadyForPlacementID:@"b5f62deda3b0f3"]) {
        //Show rv here
        [[ATAdManager sharedManager] showRewardedVideoWithPlacementID:@"b5f62deda3b0f3"  inViewController:[UIApplication sharedApplication].delegate.window.rootViewController delegate:[QinMercury getAdInstance]];
    } else {
        //Load rv here
         [[ATAdManager sharedManager] loadADWithPlacementID:@"b5f62deda3b0f3" extra:@{kATAdLoadingExtraUserIDKey:@"test_user_id"} delegate:[QinMercury getAdInstance]];
    }
}
#pragma mark - loading delegate
-(void) didFinishLoadingADWithPlacementID:(NSString *)placementID {
    NSLog(@"RV Demo: didFinishLoadingADWithPlacementID");
    UnitySendMessage("PluginMercury", "AdLoadSuccessCallBack", "ActiveRewardVideo_IOS");
}

-(void) didFailToLoadADWithPlacementID:(NSString* )placementID error:(NSError *)error {
    NSLog(@"RV Demo: failed to load:%@", error);
}

-(void) rewardedVideoDidRewardSuccessForPlacemenID:(NSString *)placementID extra:(NSDictionary *)extra{
    NSLog(@"ATRewardedVideoVideoViewController::rewardedVideoDidRewardSuccessForPlacemenID:%@ extra:%@",placementID,extra);
    UnitySendMessage("PluginMercury", "AdShowSuccessCallBack", "ActiveRewardVideo_IOS");
}

-(void) rewardedVideoDidStartPlayingForPlacementID:(NSString *)placementID extra:(NSDictionary *)extra {
    NSLog(@"ATRewardedVideoVideoViewController::rewardedVideoDidStartPlayingForPlacementID:%@ extra:%@", placementID, extra);
    [[ATAdManager sharedManager] loadADWithPlacementID:@"b5f62deda3b0f3" extra:@{kATAdLoadingExtraUserIDKey:@"test_user_id"} delegate:[QinMercury getAdInstance]];
    
}

-(void) rewardedVideoDidEndPlayingForPlacementID:(NSString*)placementID extra:(NSDictionary *)extra {
    NSLog(@"ATRewardedVideoVideoViewController::rewardedVideoDidEndPlayingForPlacementID:%@ extra:%@", placementID, extra);
}

-(void) rewardedVideoDidFailToPlayForPlacementID:(NSString*)placementID error:(NSError*)error extra:(NSDictionary *)extra {
    NSLog(@"ATRewardedVideoVideoViewController::rewardedVideoDidFailToPlayForPlacementID:%@ error:%@ extra:%@", placementID, error, extra);
}

-(void) rewardedVideoDidCloseForPlacementID:(NSString*)placementID rewarded:(BOOL)rewarded extra:(NSDictionary *)extra {
    NSLog(@"ATRewardedVideoVideoViewController::rewardedVideoDidCloseForPlacementID:%@, rewarded:%@ extra:%@", placementID, rewarded ? @"yes" : @"no", extra);
}

-(void) rewardedVideoDidClickForPlacementID:(NSString*)placementID extra:(NSDictionary *)extra {
    NSLog(@"ATRewardedVideoVideoViewController::rewardedVideoDidClickForPlacementID:%@ extra:%@", placementID, extra);
}
+(void) ActiveInterstitial_IOS
{
    NSLog(@"[ActiveInterstitial_IOS]");
    UnitySendMessage("PluginMercury", "AdShowSuccessCallBack", "ActiveInterstitial_IOS");
    UnitySendMessage("PluginMercury", "AdLoadSuccessCallBack", "ActiveInterstitial_IOS");
}
+(void) ActiveBanner_IOS
{
    NSLog(@"[ActiveBanner_IOS]");
    UnitySendMessage("PluginMercury", "AdShowSuccessCallBack", "ActiveBanner_IOS");
    UnitySendMessage("PluginMercury", "AdLoadSuccessCallBack", "ActiveBanner_IOS");
}
+(void) ActiveNative_IOS
{
    NSLog(@"[ActiveNative_IOS]");
    UnitySendMessage("PluginMercury", "AdShowSuccessCallBack", "ActiveNative_IOS");
    UnitySendMessage("PluginMercury", "AdLoadSuccessCallBack", "ActiveNative_IOS");
}

+(void) MercuryLogin_IOS
{
    NSLog(@"[MercuryLogin_IOS]");

    UnitySendMessage("PluginMercury", "LoginSuccessCallBack", unique_id.UTF8String);
}

+(void) Redeem_IOS:(NSString *)code
{
    NSLog(@"[Redeem_IOS]");
    NSMutableDictionary *dict  = [[NSMutableDictionary alloc] init];
    NSError *error;
    NSString *game_code = code;
    NSString *postParams = [[NSString alloc] initWithFormat:@"gamename=%@&redeemcode=%@", gamename, game_code];
    NSData *postData = [postParams dataUsingEncoding:NSUTF8StringEncoding];
    NSString *postLength = [NSString stringWithFormat:@"%d", [postData length]];
    NSMutableURLRequest *request = [[NSMutableURLRequest alloc] init];
    [request setURL:[NSURL URLWithString:redeem_url]];
    [request setHTTPMethod:@"POST"];
    [request setValue:postLength forHTTPHeaderField:@"Content-Length"];
    [request setHTTPBody:postData];
    NSURLSession *session = [NSURLSession sessionWithConfiguration:[NSURLSessionConfiguration defaultSessionConfiguration]];
    [[session dataTaskWithRequest:request completionHandler:^(NSData *data, NSURLResponse *response, NSError *error) {
        NSString *requestReply = [[NSString alloc] initWithData:data encoding:NSASCIIStringEncoding];
        NSString *RedeemGameData = [redeem_url stringByAppendingString:postParams];;
        NSString *result = @"Redeem_IOS:";
        NSString *final_response = [result stringByAppendingString:requestReply];;
        NSLog(@"[Redeem_IOS]RedeemGameData=%@",RedeemGameData);
        UnitySendMessage("PluginMercury", "onFunctionCallBack", final_response.UTF8String);
    }] resume];
}

+(void) Data_UseItem_IOS:(NSString *)quantity item:(NSString *)item
{
    NSLog(@"[Data_UseItem_IOS]%@,%@",quantity,item);
    NSDictionary *dict3=@{quantity:item};
    [TalkingDataGA onEvent:@"Event" eventData:dict3];

}

+(void) Data_LevelBegin_IOS:(NSString *)eventID
{
    NSLog(@"[Data_LevelBegin_IOS]");
    NSDictionary *dict3=@{@"LevelBegin":eventID};
    [TalkingDataGA onEvent:@"Event" eventData:dict3];
}

+(void) Data_LevelCompleted_IOS:(NSString *)eventID
{
    NSLog(@"[Data_LevelCompleted_IOS]");
    NSDictionary *dict3=@{@"LevelCompleted":eventID};
    [TalkingDataGA onEvent:@"Event" eventData:dict3];
    
}

+(void) Data_Event_IOS:(NSString *)eventID
{
    NSLog(@"[Data_Event_IOS]");
    NSDictionary *dict3=@{@"key":eventID};
    [TalkingDataGA onEvent:@"Event" eventData:dict3];
}

+(void) UploadGameData_IOS:(NSString *)data
{
    NSLog(@"[UploadGameData_IOS]data=%@",data);
    NSMutableDictionary *dict  = [[NSMutableDictionary alloc] init];
    NSError *error;
    NSString *game_data = data;
    NSString *postParams = [[NSString alloc] initWithFormat:@"gamename=%@&unique_id=%@&data=%@", gamename, unique_id,game_data ];
    NSData *postData = [postParams dataUsingEncoding:NSUTF8StringEncoding];
    NSString *postLength = [NSString stringWithFormat:@"%d", [postData length]];
    NSMutableURLRequest *request = [[NSMutableURLRequest alloc] init];
    [request setURL:[NSURL URLWithString:backup_url]];
    [request setHTTPMethod:@"POST"];
    [request setValue:postLength forHTTPHeaderField:@"Content-Length"];
    [request setHTTPBody:postData];
    NSURLSession *session = [NSURLSession sessionWithConfiguration:[NSURLSessionConfiguration defaultSessionConfiguration]];
    [[session dataTaskWithRequest:request completionHandler:^(NSData *data, NSURLResponse *response, NSError *error) {
        NSString *requestReply = [[NSString alloc] initWithData:data encoding:NSASCIIStringEncoding];
        NSString *UploadGameData = [backup_url stringByAppendingString:postParams];;
        NSString *result = @"UploadGameData_IOS:";
        NSLog(@"[UploadGameData_IOS]UploadGameData=%@",UploadGameData);
        NSString *final_response = [result stringByAppendingString:requestReply];;
        UnitySendMessage("PluginMercury", "onFunctionCallBack", final_response.UTF8String);
    }] resume];
}

+(void) DownloadGameData_IOS
{
    NSLog(@"[DownloadGameData_IOS]");
    NSMutableDictionary *dict  = [[NSMutableDictionary alloc] init];
    NSError *error;
    NSString *postParams = [[NSString alloc] initWithFormat:@"gamename=%@&unique_id=%@", gamename, unique_id ];
    NSData *postData = [postParams dataUsingEncoding:NSUTF8StringEncoding];
    NSString *postLength = [NSString stringWithFormat:@"%d", [postData length]];
    NSMutableURLRequest *request = [[NSMutableURLRequest alloc] init];
    [request setURL:[NSURL URLWithString:download_url]];
    [request setHTTPMethod:@"POST"];
    [request setValue:postLength forHTTPHeaderField:@"Content-Length"];
    [request setHTTPBody:postData];
    NSURLSession *session = [NSURLSession sessionWithConfiguration:[NSURLSessionConfiguration defaultSessionConfiguration]];
    [[session dataTaskWithRequest:request completionHandler:^(NSData *data, NSURLResponse *response, NSError *error) {
        NSString *requestReply = [[NSString alloc] initWithData:data encoding:NSASCIIStringEncoding];
        NSString *UploadGameData = [download_url stringByAppendingString:postParams];;
        NSString *result = @"DownloadGameData_IOS:";
        NSLog(@"[UploadGameData_IOS]UploadGameData=%@",UploadGameData);
        NSString *final_response = [result stringByAppendingString:requestReply];;
        UnitySendMessage("PluginMercury", "onFunctionCallBack", final_response.UTF8String);
    }] resume];
}


+(NSString *)getDeviceIDInKeychain {
    NSString *bundleId =[[NSBundle mainBundle]bundleIdentifier];
    NSString *getUDIDInKeychain = (NSString *)[QinMercury load:bundleId];
    if (!getUDIDInKeychain ||[getUDIDInKeychain isEqualToString:@""]||[getUDIDInKeychain isKindOfClass:[NSNull class]]) {
        CFUUIDRef puuid = CFUUIDCreate( nil );
        CFStringRef uuidString = CFUUIDCreateString( nil, puuid );
        NSString * result = (NSString *)CFBridgingRelease(CFStringCreateCopy( NULL, uuidString));
        CFRelease(puuid);
        CFRelease(uuidString);
        [QinMercury save:bundleId data:result];
        getUDIDInKeychain = (NSString *)[QinMercury load:bundleId];
    }
    NSLog(@"[getDeviceIDInKeychain]getUDIDInKeychain=%@",getUDIDInKeychain);
    return getUDIDInKeychain;
}


+(NSMutableDictionary *)getKeychainQuery:(NSString *)service {
    return [NSMutableDictionary dictionaryWithObjectsAndKeys:
            (id)kSecClassGenericPassword,(id)kSecClass,
            service, (id)kSecAttrService,
            service, (id)kSecAttrAccount,
            (id)kSecAttrAccessibleAfterFirstUnlock,(id)kSecAttrAccessible,
            nil];
}

+(void)save:(NSString *)service data:(id)data {
    NSMutableDictionary *keychainQuery = [self getKeychainQuery:service];
    SecItemDelete((CFDictionaryRef)keychainQuery);
    [keychainQuery setObject:[NSKeyedArchiver archivedDataWithRootObject:data] forKey:(id)kSecValueData];
    SecItemAdd((CFDictionaryRef)keychainQuery, NULL);
}


+(id)load:(NSString *)service {
    id ret = nil;
    NSMutableDictionary *keychainQuery = [self getKeychainQuery:service];
    [keychainQuery setObject:(id)kCFBooleanTrue forKey:(id)kSecReturnData];
    [keychainQuery setObject:(id)kSecMatchLimitOne forKey:(id)kSecMatchLimit];
    CFDataRef keyData = NULL;
    if (SecItemCopyMatching((CFDictionaryRef)keychainQuery, (CFTypeRef *)&keyData) == noErr) {
        ret = [NSKeyedUnarchiver unarchiveObjectWithData:(__bridge NSData *)keyData];
    }
    if (keyData)
        CFRelease(keyData);
    return ret;
}

+ (void)delete:(NSString *)service {
    NSMutableDictionary *keychainQuery = [self getKeychainQuery:service];
    SecItemDelete((CFDictionaryRef)keychainQuery);
}




#if defined (__cplusplus)
extern "C"
{
#endif
    IAPManager *iapManager = nil;
    NSString *timeString = @"";
    void BuyProduct(char *p)
    {
        NSDate* date = [NSDate dateWithTimeIntervalSinceNow:0];//获取当前时间0秒后的时间
        NSTimeInterval time=[date timeIntervalSince1970]*1000;// *1000 是精确到毫秒，不乘就是精确到秒
        timeString = [NSString stringWithFormat:@"%.0f", time];
        NSString *pid = [NSString stringWithUTF8String:p];
        NSLog(@"[C][BuyProduct]:%@",timeString);
        NSLog(@"[C][BuyProduct]:%@",pid);
        NSString *my_iapId = @"";
        int my_currencyAmount = 0;
        NSArray *myWords = [pid componentsSeparatedByString:@"."];
        int size = [myWords count];
        NSLog(@"[C][BuyProduct]:size=%d",size);
        if(size==0)
        {
            my_iapId = myWords[size];
        }
        else
        {
            my_iapId = myWords[size-1];
        }
        NSLog(@"[C][BuyProduct]:size=%@",my_iapId);
        if([pid isEqualToString:@"uk.fiveaces.nsfcchina.superstriker"])
        {
            my_currencyAmount = 20;
        }
        else if([pid isEqualToString:@"uk.fiveaces.nsfcchina.superstriker"])
        {
            my_currencyAmount = 20;
        }
        else if([pid isEqualToString:@"uk.fiveaces.nsfcchina.superstriker"])
        {
            my_currencyAmount = 20;
        }
        else if([pid isEqualToString:@"uk.fiveaces.nsfcchina.superstriker"])
        {
            my_currencyAmount = 20;
        }
        else if([pid isEqualToString:@"uk.fiveaces.nsfcchina.superstriker"])
        {
            my_currencyAmount = 20;
        }
        else if([pid isEqualToString:@"uk.fiveaces.nsfcchina.superstriker"])
        {
            my_currencyAmount = 20;
        }
        else if([pid isEqualToString:@"uk.fiveaces.nsfcchina.superstriker"])
        {
            my_currencyAmount = 20;
        }
        else
        {
            my_iapId =@"testpid";
            my_currencyAmount = 1;
        }
        
        
        [TDGAVirtualCurrency onChargeRequst:timeString
                                      iapId:my_iapId
                             currencyAmount:my_currencyAmount
                               currencyType:@"RMB"
                      virtualCurrencyAmount:1
                                paymentType:@"AppStore"];
        //pid = [NSString stringWithFormat:@"com.singmaan.sdk.6"];
        //商品信息
        [iapManager requestProductData:pid];
        //购买商品
        [iapManager buyRequest:pid];
        [iapManager giveParam:timeString];
        

    }
    
    void GameInit()
    {
        NSLog(@"[C][GameInit]");
        [QinMercury GameInit];
        if(nil == iapManager){//初始化
            iapManager = [[IAPManager alloc] init];
        }
        [iapManager attachObserver];
    }
    
    void ActiveRewardVideo_IOS()
    {
        NSLog(@"[C][ActiveRewardVideo_IOS]");
        [QinMercury ActiveRewardVideo_IOS];
    }
    
    void ActiveInterstitial_IOS()
    {
        NSLog(@"[C][ActiveInterstitial_IOS]");
        [QinMercury ActiveInterstitial_IOS];
    }

    void ActiveBanner_IOS()
    {
        NSLog(@"[C][ActiveBanner_IOS]");
        [QinMercury ActiveBanner_IOS];
    }

    void ActiveNative_IOS()
    {
        NSLog(@"[C][ActiveNative_IOS]");
        [QinMercury ActiveNative_IOS];
    }

    void MercuryLogin_IOS()
    {
        NSLog(@"[C][MercuryLogin_IOS]");
        [QinMercury MercuryLogin_IOS];
    }

    void UploadGameData_IOS(char *p)
    {
        NSString *pid = [NSString stringWithUTF8String:p];
        NSLog(@"[C][UploadGameData_IOS]");
        [QinMercury UploadGameData_IOS:pid];
    }

    void DownloadGameData_IOS()
    {
        NSLog(@"[C][DownloadGameData_IOS]");
        [QinMercury DownloadGameData_IOS];
    }

    void Data_UseItem_IOS(char *quantity,char *item)
    {
        NSString *c_quantity = [NSString stringWithUTF8String:quantity];
        NSString *c_item = [NSString stringWithUTF8String:item];
        NSLog(@"[C][Data_UseItem]");
        [QinMercury Data_UseItem_IOS:c_quantity item:c_item];
    }

    void Data_LevelBegin_IOS(char *eventID)
    {
        NSString *c_eventID = [NSString stringWithUTF8String:eventID];
        NSLog(@"[C][Data_LevelBegin]");
        [QinMercury Data_LevelBegin_IOS:c_eventID];
    }

    void Data_LevelCompleted_IOS(char *eventID)
    {
        NSString *c_eventID = [NSString stringWithUTF8String:eventID];
        NSLog(@"[C][Data_LevelCompleted]");
        [QinMercury Data_LevelCompleted_IOS:c_eventID];
    }

    void Data_Event_IOS(char *eventID)
    {
        NSString *c_eventID = [NSString stringWithUTF8String:eventID];
        NSLog(@"[C][Data_Event]");
        [QinMercury Data_Event_IOS:c_eventID];
    }

    void Redeem_IOS(char *code)
    {
        NSString *c_code = [NSString stringWithUTF8String:code];
        NSLog(@"[C][Data_Event]");
        [QinMercury Redeem_IOS:c_code];
    }
    

#if defined (__cplusplus)
}
#endif
@end


