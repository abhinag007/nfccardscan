import 'dart:convert';

import 'package:http/http.dart' as http;

import 'package:flutter/material.dart';

import 'package:stripe_payment/stripe_payment.dart';

class Payment extends StatefulWidget {
  const Payment({Key? key}) : super(key: key);

  @override
  State<Payment> createState() => _PaymentState();
}

class _PaymentState extends State<Payment> {
  static String secret = 'sk_test_51L0tkIEIKSjVK84vx6JJEpZRLwS31TBdfCcMPPY0NJxxOYnoAbppcW4A9FU3jBUSuqlKwS4SxrerQYU5wkWJKWLD00NQT9lf5B';

  TextEditingController cardNumberController = TextEditingController();
  TextEditingController expYearController = TextEditingController();
  TextEditingController expMonthController = TextEditingController();

  @override
  initState() {
    super.initState();

    StripePayment.setOptions(
        StripeOptions(publishableKey: "pk_test_51L0tkIEIKSjVK84v2ybewOisfqhGJi0Thwh8qODQmFT1DRpxu6XmhoLp8q3xM0IWuOGKIqvApPAbsUNYqrGsfB2g00hvBbktqu", merchantId: "Test", androidPayMode: 'test')
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            TextField(
              controller: cardNumberController,
              decoration: const InputDecoration(labelText: 'Card Number'),
            ),
            TextField(
              controller: expMonthController,
              keyboardType: TextInputType.number,
              decoration: const InputDecoration(labelText: 'Expired Month'),
            ),
            TextField(
              controller: expYearController,
              keyboardType: TextInputType.number,
              decoration: const InputDecoration(labelText: 'Expired Year'),
            ),
            RaisedButton(
              child: Text('card'),
              onPressed: () {
                final CreditCard testCard =  CreditCard(
                    number: cardNumberController.text,
                    expMonth: int.parse(expMonthController.text),
                    expYear: int.parse(expYearController.text)
                );

                StripePayment.createTokenWithCard(testCard).then((token) {
                  print(token.tokenId);

                  createCharge(token.tokenId.toString());
                });
              },)
          ],
        ),
      ),
    );
  }

  static Future<Map<String, dynamic>?> createCharge(String tokenId) async {
    try {
      Map<String, dynamic> body = {
        'amount': '2000',
        'currency': 'usd',
        'source': tokenId,
        'description': 'My first try'
      };
      var response = await http.post(
          Uri.parse('https://api.stripe.com/v1/charges'),
          body: body,
          headers: { 'Authorization': 'Bearer ${secret}','Content-Type': 'application/x-www-form-urlencoded'}
      );
      return jsonDecode(response.body);
    } catch (err) {
      print('err charging user: ${err.toString()}');
    }
    return null;
  }
}
