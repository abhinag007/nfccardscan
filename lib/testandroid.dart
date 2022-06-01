import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class TestAndroid extends StatefulWidget {
  const TestAndroid({Key? key}) : super(key: key);

  @override
  State<TestAndroid> createState() => _TestAndroidState();
}

class _TestAndroidState extends State<TestAndroid> {
  static const methodChannel = MethodChannel('com.nfc.transaction/method');

  @override
  void initState() {
    super.initState();
    startNfcService();
  }

  @override
  Widget build(BuildContext context) {

    return Scaffold(body: Container(
      child: Center(child: const Text("WORKS")),
    ));
  }

  Future startNfcService() async{ 
    await methodChannel.invokeMethod('startNfcService');
  }
}