import * as React from 'react';

import { StyleSheet, View, Text } from 'react-native';
import RnBraintreeNoncehelper from 'rn-braintree-noncehelper';

// Change the auth token to test
const authToken = '{PUT AUTH TOKEN HERE}';
export default function App() {
  const [result, setResult] = React.useState<any | undefined>();

  React.useEffect(() => {
    RnBraintreeNoncehelper.createNonce(authToken, {
      number: '4111111111111111',
      expirationMonth: '02',
      expirationYear: '2022',
      cvv: '234',
      postalCode: '90017',
    })
      .then((result) => {
        setResult(result);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  return (
    <View style={styles.container}>
      <Text>Nonce: {result?.nonce}</Text>
      <Text>type: {result?.type}</Text>
      <Text>description: {result?.description}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
