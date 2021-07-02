import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:http/http.dart' as http;
import 'package:tizito/api/profile_provider.dart';
import 'package:tizito/models/profile.dart';
import 'package:tizito/models/trouble.dart';
import 'package:tizito/utils/token.dart';
import 'package:tizito/views/dasboard.dart';
import 'package:tizito/views/login.dart';
import 'package:tizito/widgets/email.dart';
import 'package:tizito/widgets/footer.dart';
import 'package:tizito/widgets/header.dart';
import 'package:tizito/widgets/message.dart';
import 'package:tizito/widgets/name.dart';
import 'package:tizito/widgets/password.dart';

class Register extends StatelessWidget {
  final _formKey = GlobalKey<FormState>();

  final TextEditingController _nameController = TextEditingController();

  final TextEditingController _emailController = TextEditingController();

  final TextEditingController _passwordController = TextEditingController();

  final _storage = FlutterSecureStorage();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        margin: EdgeInsets.only(
          top: 32,
          right: 16,
          bottom: 32,
          left: 16,
        ),
        padding: EdgeInsets.all(
          16,
        ),
        decoration: BoxDecoration(
          color: Colors.white,
          borderRadius: BorderRadius.circular(8),
        ),
        child: SingleChildScrollView(
          child: Column(
            children: [
              Header(
                'Register',
                "Create an account. It's free!",
              ),
              _form(context),
              Footer(
                "Already have an account?",
                "Login",
                Login(),
              ),
            ],
          ),
        ),
      ),
      backgroundColor: Theme.of(context).colorScheme.secondary,
    );
  }

  Form _form(context) {
    return Form(
      key: _formKey,
      child: Column(
        children: [
          Name(
            _nameController,
          ),
          Email(
            _emailController,
          ),
          Password(
            _passwordController,
          ),
          Padding(
            padding: EdgeInsets.only(
              top: 16,
            ),
            child: SizedBox(
              width: double.infinity,
              child: OutlinedButton(
                onPressed: () {
                  if (_formKey.currentState!.validate()) {
                    _register(context);
                  }
                },
                child: Text(
                  'REGISTER',
                ),
                style: OutlinedButton.styleFrom(
                  primary: Colors.white,
                  backgroundColor: Theme.of(context).colorScheme.secondary,
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }

  void _register(context) async {
    var url = Uri.parse('http://172.16.16.16:8080/tizito/api/accounts');

    final response = await http.post(
      url,
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        "name": _nameController.text,
        "email": _emailController.text,
        "password": _passwordController.text
      }),
    );

    if (response.statusCode == 201) {
      var token = getToken(_emailController.text, _passwordController.text);

      await _storage.write(key: 'token', value: token);

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
          }
          ScaffoldMessenger.of(context).showSnackBar(
              SnackBar(content: Text('${value["data"]["message"]}')));
        } else
          ScaffoldMessenger.of(context).showSnackBar(
              SnackBar(content: Text('${value["data"]["message"]}')));
      });
    } else if (response.statusCode == 400) {
      var trouble = Trouble.fromJson(jsonDecode(response.body));

      message(
        context,
        'Ops!',
        '${trouble.message}',
      );
    } else if (response.statusCode == 409) {
      Navigator.pushAndRemoveUntil(
        context,
        MaterialPageRoute(
          builder: (context) => Login(email: _emailController.text),
        ),
        (route) => false,
      );
    } else {
      message(
        context,
        'Sorry!',
        'Try again later.',
      );
    }
  }
}
