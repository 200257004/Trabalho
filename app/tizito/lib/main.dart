import 'package:flutter/material.dart';
import 'package:tizito/views/splash.dart';

void main() {
//  runApp(MultiProvider(
//    providers: [
//      ChangeNotifierProvider(
//        create: (context) => Index(0),
//      ),
//    ],
//    child: TizitoApp(),
//  ));

  runApp(TizitoApp());
}

class TizitoApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Tizito',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        primarySwatch: Colors.orange,
      ),
      home: Splash(),
    );
  }
}
