# rn-braintree-noncehelper

React native module to get payment nonce for credit, debit cards

## Installation

```sh
npm install rn-braintree-noncehelper
```

## Usage

```js
import RnBraintreeNoncehelper from "rn-braintree-noncehelper";

// ...
const result = await RnBraintreeNoncehelper.createNonce(authToken, {
  number: 'XXX',
  expirationMonth: 'XX',
  expirationYear: 'XXXX',
  cvv: 'XXX',
  postalCode: 'XXXX'      // This is optional parameter
});
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT
