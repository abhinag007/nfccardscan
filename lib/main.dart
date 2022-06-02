// @dart=2.9

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:mfccardscan/paid.dart';
import 'package:mfccardscan/pay.dart';
import 'package:mfccardscan/testandroid.dart';

import 'Transaction.dart';
import 'existings-card.dart';

void main() async{
  // WidgetsFlutterBinding.ensureInitialized();
  // Stripe.publishableKey = 'pk_test_51L0tkIEIKSjVK84v2ybewOisfqhGJi0Thwh8qODQmFT1DRpxu6XmhoLp8q3xM0IWuOGKIqvApPAbsUNYqrGsfB2g00hvBbktqu';
  // await Stripe.instance.applySettings();
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: Paid(),
    );
  }
}



/*
class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {


  @override
  Widget build(BuildContext context) {

    return Scaffold(
      appBar: AppBar(

        title: Text(widget.title),
      ),
      body: Center(

        child: Column(

          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            const Text(
              'You have pushed the button this many times:',
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        // onPressed: ()=>Navigator.of(context).push(MaterialPageRoute(builder: (context)=>TestAndroid())),
        onPressed: ()=> Navigator.of(context).push(MaterialPageRoute(builder: (context)=>Transaction())),
        tooltip: 'Increment',
        child: const Icon(Icons.add),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}*/
