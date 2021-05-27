import { NativeModules } from 'react-native';

type RnBraintreeNoncehelperType = {
  multiply(a: number, b: number): Promise<number>;
};

const { RnBraintreeNoncehelper } = NativeModules;

export default RnBraintreeNoncehelper as RnBraintreeNoncehelperType;
