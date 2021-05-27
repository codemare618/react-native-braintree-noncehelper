import { NativeModules } from 'react-native';

interface CreditCardOptions {
  number: string;
  expirationMonth: string;
  expirationYear: string;
  cvv: string;
  postalCode?: string;
}

interface CreditCardNonce {
  nonce: string;
  type: string;
  localizedDescription: string;
  default: boolean;
}

type RnBraintreeNoncehelperType = {
  createNonce(clientToken: string, options: CreditCardOptions): Promise<CreditCardNonce>;
};

const { RnBraintreeNoncehelper } = NativeModules;

export default RnBraintreeNoncehelper as RnBraintreeNoncehelperType;
