//
//  IAPManager.h
//  SingmaanSDK
//
//  Created by 星漫 on 2020/6/9.
//  Copyright © 2020 App. All rights reserved.
//

#ifndef IAPManager_h
#define IAPManager_h
#import <Foundation/Foundation.h>
#import <StoreKit/StoreKit.h>

@interface IAPManager : NSObject<SKProductsRequestDelegate, SKPaymentTransactionObserver>{
    SKProduct *proUpgradeProduct;
    SKProductsRequest *productsRequest;
    NSString *productIndentify;
}

-(void)attachObserver;
-(BOOL)CanMakePayment;
-(void)requestProductData:(NSString *)productIdentifiers;
-(void)buyRequest:(NSString *)productIdentifier;//保存Unity传递的商品ID

@end

#endif /* IAPManager_h */
