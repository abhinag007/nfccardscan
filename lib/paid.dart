import 'package:flutter/material.dart';
import 'package:mfccardscan/services/payment-services.dart';
import 'package:stripe_payment/stripe_payment.dart';

class Paid extends StatefulWidget {
  const Paid({Key? key}) : super(key: key);

  @override
  State<Paid> createState() => _PaidState();
}

class _PaidState extends State<Paid> {

  Token? _paymentToken;
  PaymentMethod? _paymentMethod;
  String? _error;

  //this client secret is typically created by a backend system
  //check https://stripe.com/docs/payments/payment-intents#passing-to-client
  final String? _paymentIntentClientSecret = null;

  PaymentIntentResult? _paymentIntent;
  Source? _source;

  ScrollController _controller = ScrollController();

  final CreditCard testCard = CreditCard(
    number: '4000002760003184',
    expMonth: 12,
    expYear: 21,
    name: 'Test User',
    cvc: '133',
    addressLine1: 'Address 1',
    addressLine2: 'Address 2',
    addressCity: 'City',
    addressState: 'CA',
    addressZip: '1337',
  );

  GlobalKey<ScaffoldState> _scaffoldKey = GlobalKey();

  @override
  void initState() {
    super.initState();
    StripePayment.setOptions(StripeOptions(publishableKey: 'pk_test_51L0tkIEIKSjVK84v2ybewOisfqhGJi0Thwh8qODQmFT1DRpxu6XmhoLp8q3xM0IWuOGKIqvApPAbsUNYqrGsfB2g00hvBbktqu'));
  }
  void setError(dynamic error) {
    _scaffoldKey.currentState!
        .showSnackBar(SnackBar(content: Text(error.toString())));
    setState(() {
      _error = error.toString();
    });
  }
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        child: Center(
          child: RaisedButton(
            onPressed: (){
              StripePayment.createTokenWithCard(
                testCard,
              ).then((token) {
                _scaffoldKey.currentState!.showSnackBar(
                    SnackBar(content: Text('Received ${token.tokenId}')));
                setState(() {
                  _paymentToken = token;
                });
              }).catchError(setError);

            },
          ),
        ),
      ),
    );
  }
}
