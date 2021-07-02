import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:tizito/api/profile_provider.dart';
import 'package:tizito/models/profile.dart';
import 'package:tizito/views/dasboard.dart';
import 'package:tizito/views/login.dart';

class Splash extends StatelessWidget {
  final _storage = FlutterSecureStorage();

  @override
  Widget build(BuildContext context) {
    const _time = 3;
    const _slogan = 'My social calendar';

    // Validar conectividade

    Timer(
      Duration(
        seconds: _time,
      ),
      () async {
        String? value = await _storage.read(key: 'token');

        if (value == null) {
          Navigator.pushReplacement(
            context,
            MaterialPageRoute(
              builder: (context) => Login(),
            ),
          );
        } else {
          ProfileProvider().getProfile().then((value) {
            if (value["statusCode"] >= 200 && value["statusCode"] < 210) {
              if (value["data"] != null && value["date"] != "") {
                Profile profile = Profile.fromJson(value["data"]);
                Navigator.pushAndRemoveUntil(
                  context,
                  MaterialPageRoute(
                    builder: (context) => Dashboard(profile: profile),
                  ),
                  (route) => false,
                );
              } else {
                ScaffoldMessenger.of(context).showSnackBar(
                    SnackBar(content: Text('${value["data"]["message"]}')));
              }
            } else
              ScaffoldMessenger.of(context).showSnackBar(
                  SnackBar(content: Text('${value["data"]["message"]}')));
          });

          // Navigator.pushReplacement(
          //   context,
          //   MaterialPageRoute(
          //     builder: (context) => Dashboard(),
          //   ),
          // );
        }
      },
    );

    return Scaffold(
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Image.asset(
                "assets/images/tizito_logo.png",
              ),
              Text(
                _slogan,
                style: TextStyle(
                  color: Colors.white,
                  fontSize: 24,
                ),
              ),
            ],
          ),
        ),
        backgroundColor: Theme.of(context).colorScheme.secondary);
  }
}
