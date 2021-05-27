package com.rnbraintreenoncehelper;

import androidx.annotation.NonNull;

import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.Card;
import com.braintreepayments.api.interfaces.PaymentMethodNonceCallback;
import com.braintreepayments.api.models.CardBuilder;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.facebook.react.ReactActivity;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.module.annotations.ReactModule;

@ReactModule(name = RnBraintreeNoncehelperModule.NAME)
public class RnBraintreeNoncehelperModule extends ReactContextBaseJavaModule {
    public static final String NAME = "RnBraintreeNoncehelper";

    public RnBraintreeNoncehelperModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }


    // Example method
    // See https://reactnative.dev/docs/native-modules-android
    @ReactMethod
    public void createNonce(String authToken, ReadableMap options, Promise promise){
      try {
        String number = options.getString("number");
        String expirationMonth = options.getString("expirationMonth");
        String expirationYear = options.getString("expirationYear");
        String cvv = options.getString("cvv");

        if (number == null || expirationMonth == null || expirationYear == null || cvv == null) {
          throw new Exception("Invalid card options provided");
        }
        if (number.isEmpty() || expirationMonth.isEmpty() || expirationYear.isEmpty() || cvv.isEmpty()) {
          throw new Exception("Invalid card options provided");
        }

        CardBuilder cardBuilder = new CardBuilder()
          .cardNumber(number)
          .expirationMonth(expirationMonth)
          .expirationYear(expirationYear)
          .cvv(cvv)
          .validate(false);


        String postalCode = options.getString("postalCode");
        if (postalCode != null && !postalCode.isEmpty()) {
          cardBuilder.postalCode(postalCode);
        }

        BraintreeFragment fragment = BraintreeFragment.newInstance((ReactActivity)getCurrentActivity(), authToken);
        Card.tokenize(fragment, cardBuilder, new PaymentMethodNonceCallback() {
          @Override
          public void success(PaymentMethodNonce paymentMethodNonce) {
            WritableNativeMap map = new WritableNativeMap();
            map.putString("nonce", paymentMethodNonce.getNonce());
            map.putString("type", paymentMethodNonce.getTypeLabel());
            map.putString("description", paymentMethodNonce.getDescription());
            map.putBoolean("default", paymentMethodNonce.isDefault());
            promise.resolve(map);
          }

          @Override
          public void failure(Exception exception) {
            promise.reject(exception);
          }
        });
      }catch(Exception ex){
        promise.reject(ex);
      }
    }

}
