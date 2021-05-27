#import "RnBraintreeNoncehelper.h"

@implementation RnBraintreeNoncehelper

RCT_EXPORT_MODULE()

// Example method
// See // https://reactnative.dev/docs/native-modules-ios
RCT_REMAP_METHOD(createNonce,
                  createClientWithAuthroizationToken:(nonnull NSString*) token
                  withOptions:(nonnull NSDictionary *)options
                  withResolver:(RCTPromiseResolveBlock)resolve
                  withRejector:(RCTPromiseRejectBlock)reject
                 )
{
    NSString * number = [options objectForKey:@"number"];
    NSString * month = [options objectForKey:@"expirationMonth"];
    NSString * year = [options objectForKey:@"expirationYear"];
    NSString * cvv = [options objectForKey:@"cvv"];
    
    // Validate parameters
    if (number == nil || month == nil || year == nil || cvv == nil) {
        reject(@"InvalidParameters", @"Invalid card options provided.", nil);
        return;
    }
    
    if (!number.length || !month.length || !year.length || !cvv.length) {
        reject(@"InvalidParameters", @"Invalid card options provided.", nil);
        return;
    }
    
    // additional parameters if present
    NSString * postalCode = [options objectForKey:@"postalCode"];
    
    // Create BTCard from options
    BTCard * btCard = [[BTCard alloc] initWithNumber:number expirationMonth:month expirationYear:year cvv:cvv];
    btCard.postalCode = postalCode;
    btCard.shouldValidate = NO;
    
    BTAPIClient * apiClient = [[BTAPIClient alloc] initWithAuthorization:token];
    BTCardClient * cardClient = [[BTCardClient alloc] initWithAPIClient:apiClient];
    
    [cardClient tokenizeCard:btCard completion:^(BTCardNonce * _Nullable tokenizedCard, NSError * _Nullable error) {
        if (error != nil) {
            reject(@"Tokenized Failed", @"Tokenizing card failed.", error);
            return;
        }
        resolve(@{
            @"nonce": tokenizedCard.nonce,
            @"type": tokenizedCard.type,
            @"description":tokenizedCard.localizedDescription,
            @"default": @(tokenizedCard.isDefault)
                });
    }];
}


@end
