//
//  IAPManager.m
//  SingmaanSDK
//
//  Created by 星漫 on 2020/6/9.
//  Copyright © 2020 App. All rights reserved.
//

#import "IAPManager.h"
@implementation IAPManager

-(void) attachObserver{
    NSLog(@"AttachObserver");
    [[SKPaymentQueue defaultQueue] addTransactionObserver:self];
}

-(BOOL) CanMakePayment{
    return [SKPaymentQueue canMakePayments];
}

-(void) requestProductData:(NSString *)productIdentifiers{
    NSArray *idArray = [productIdentifiers componentsSeparatedByString:@"\t"];
    NSSet *idSet = [NSSet setWithArray:idArray];
    [self sendRequest:idSet];
}

-(void) sendRequest:(NSSet *)idSet{
    SKProductsRequest *request = [[SKProductsRequest alloc] initWithProductIdentifiers:idSet];
    request.delegate = self;
    [request start];
}

-(void) productsRequest:(SKProductsRequest *)request didReceiveResponse:(SKProductsResponse *)response{
    
    NSLog(@"-----------收到产品反馈信息--------------");
    NSArray *products = response.products;
    NSLog(@"产品Product ID:%@",response.invalidProductIdentifiers);
    NSLog(@"产品付费数量: %d", (int)[products count]);
    // populate UI
    for (SKProduct *p in products) {
        NSLog(@"product info");
        NSLog(@"SKProduct 描述信息%@", [products description]);
        NSLog(@"产品标题 %@" , p.localizedTitle);
        NSLog(@"产品描述信息: %@" , p.localizedDescription);
        NSLog(@"价格: %@" , p.price);
        NSLog(@"Product id: %@" , p.productIdentifier);
        //UnitySendMessage("IOSIAPMgr", "ShowProductList", [[self productInfo:p] UTF8String]);
    }
    for(NSString *invalidProductId in response.invalidProductIdentifiers){
        NSLog(@"Invalid product id:%@",invalidProductId);
    }
    // [request autorelease];
}

-(void)buyRequest:(NSString *)productIdentifier{
//    NSArray* transactions=[SKPaymentQueue defaultQueue].transactions;
//    if(transactions.count>0) {
//      for(SKPaymentTransaction *tran in transactions) {
//          NSLog(@"**************************************************************%@",tran.transactionState);
//          //检查是否有完成的交易
//          SKPaymentTransaction* transaction = [transactions firstObject];
//          if(tran.transactionState == SKPaymentTransactionStatePurchasing) {
//              NSLog(@"----------------------%@",tran.transactionState);
//              [[SKPaymentQueue defaultQueue] finishTransaction:tran];
//              return;
//          }
//      }
//    }
    productIndentify = productIdentifier;
    SKPayment *payment = [SKPayment paymentWithProductIdentifier:productIdentifier];
    [[SKPaymentQueue defaultQueue] addPayment:payment];
}

-(NSString *)productInfo:(SKProduct *)product{
    NSArray *info = [NSArray arrayWithObjects:product.localizedTitle,product.localizedDescription,product.price,product.productIdentifier, nil];
    
    return [info componentsJoinedByString:@"\t"];
}
//沙盒测试环境验证
#define SANDBOX @"https://sandbox.itunes.apple.com/verifyReceipt"
//正式环境验证
#define AppStore @"https://buy.itunes.apple.com/verifyReceipt"
/**
 *  验证购买，避免越狱软件模拟苹果请求达到非法购买问题
 */
-(void)verifyPurchaseWithPaymentTransaction{
    //从沙盒中获取交易凭证并且拼接成请求体数据
    NSURL *receiptUrl=[[NSBundle mainBundle] appStoreReceiptURL];
    NSData *receiptData=[NSData dataWithContentsOfURL:receiptUrl];
    
    NSString *receiptString=[receiptData base64EncodedStringWithOptions:NSDataBase64EncodingEndLineWithLineFeed];//转化为base64字符串
    
    NSString *bodyString = [NSString stringWithFormat:@"{\"receipt-data\" : \"%@\"}", receiptString];//拼接请求数据
    NSData *bodyData = [bodyString dataUsingEncoding:NSUTF8StringEncoding];
    
    
    //测试的时候填写沙盒路径，上APPStore的时候填写正式环境路径
    NSURL *url=[NSURL URLWithString:SANDBOX];
    NSMutableURLRequest *requestM=[NSMutableURLRequest requestWithURL:url];
    requestM.HTTPBody=bodyData;
    requestM.HTTPMethod=@"POST";
    //创建连接并发送同步请求
    NSError *error = nil;
    NSData *responseData = [NSURLConnection sendSynchronousRequest:requestM returningResponse:nil error:&error];
    if (error) {
        NSLog(@"验证购买过程中发生错误，错误信息：%@",error.localizedDescription);
        return;
    }
    NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:responseData options:NSJSONReadingAllowFragments error:nil];
    NSLog(@"%@",dic);
    
    if([dic[@"status"] intValue] == 0){
        NSLog(@"购买成功！");
        NSDictionary *dicReceipt = dic[@"receipt"];
        
        NSLog(@"--------------%@", dicReceipt);
        //NSDictionary *dicInApp = [dicReceipt[@"in_app"] firstObject];
        for(NSDictionary  *tmp in dicReceipt[@"in_app"])
        {
            // NSLog(@"+++++++++++%@",dicInApp);
            NSString *productIdentifier = tmp[@"product_id"];//读取产品标识
            NSLog(@"+++++++++++++++++++++++++++++++++++++%@",productIdentifier);
            NSLog(@"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx%@",productIndentify);
            //如果是消耗品则记录购买数量，非消耗品则记录是否购买过
            NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
            if ([productIdentifier isEqualToString:productIndentify]) 
            {
                NSInteger purchasedCount = [defaults integerForKey:productIdentifier];//已购买数量
                [[NSUserDefaults standardUserDefaults] setInteger:(purchasedCount+1) forKey:productIdentifier];
                UnitySendMessage("PluginMercury", "PurchaseSuccessCallBack", productIdentifier.UTF8String);
            }else{
                [[NSUserDefaults standardUserDefaults] setBool:YES forKey:productIdentifier];
                UnitySendMessage("PluginMercury", "PurchaseSuccessCallBack", productIdentifier.UTF8String);
            }
        }
    }else{
        NSLog(@"购买失败，未通过验证！");
    }
}
//监听购买结果
- (void)paymentQueue:(SKPaymentQueue *)queue updatedTransactions:(NSArray *)transaction{
    for(SKPaymentTransaction *tran in transaction){
        switch (tran.transactionState) {
            case SKPaymentTransactionStatePurchased:{
                NSLog(@"交易完成");
                [self verifyPurchaseWithPaymentTransaction];// 发送到苹果服务器验证凭证
                [[SKPaymentQueue defaultQueue] finishTransaction:tran];
            }
                break;
            case SKPaymentTransactionStatePurchasing:
                NSLog(@"商品添加进列表");
                break;
            case SKPaymentTransactionStateRestored:
                NSLog(@"已经购买过商品");
                [[SKPaymentQueue defaultQueue] finishTransaction:tran];
                break;
            case SKPaymentTransactionStateFailed:{
                NSLog(@"交易失败");
                [[[UIAlertView alloc]initWithTitle:@"提示" message:@"交易失败" delegate:nil cancelButtonTitle:@"确定" otherButtonTitles:nil, nil] show];
                [[SKPaymentQueue defaultQueue] finishTransaction:tran];
            }
                break;
            default:
                [[SKPaymentQueue defaultQueue] finishTransaction:tran];
                break;
        }
    }
}
@end
