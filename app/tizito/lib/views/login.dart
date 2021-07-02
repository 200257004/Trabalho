import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:tizito/api/profile_provider.dart';
import 'package:tizito/models/profile.dart';
import 'package:tizito/utils/token.dart';
import 'package:tizito/views/dasboard.dart';
import 'package:tizito/views/register.dart';
import 'package:tizito/views/replace.dart';
import 'package:tizito/widgets/email.dart';
import 'package:tizito/widgets/footer.dart';
import 'package:tizito/widgets/header.dart';
import 'package:tizito/widgets/message.dart';
import 'package:tizito/widgets/password.dart';
import 'package:http/http.dart' as http;

class Login extends StatelessWidget {
  final _formKey = GlobalKey<FormState>();

  final TextEditingController _emailController = TextEditingController();

  final TextEditingController _passwordController = TextEditingController();

  final _storage = FlutterSecureStorage();

  Login({String? email}) {
    if (email != null) {
      _emailController.text = email;
    }
  }

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
                'Login',
                "Login with your credentials.",
              ),
              _form(context),
              Footer(
                "Don't have an account?",
                "Register",
                Register(),
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
          Email(
            _emailController,
          ),
          Password(
            _passwordController,
          ),
          Align(
            alignment: Alignment.centerRight,
            child: TextButton(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => Replace(),
                  ),
                );
              },
              child: Text(
                'Forgot your password?',
                style: TextStyle(
                  fontWeight: FontWeight.bold,
                ),
              ),
            ),
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
                    _login(context);
                  }
                },
                child: Text(
                  'LOGIN',
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

  void _login(context) async {
    var url = Uri.parse('http://116.203.235.9:8080/tizito/api/profile');

    String token = getToken(_emailController.text, _passwordController.text);

    final response = await http.get(
      url,
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': 'Basic $token',
      },
    );

    if (response.statusCode == 200) {
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
          } else {
            ScaffoldMessenger.of(context).showSnackBar(
                SnackBar(content: Text('${value["data"]["message"]}')));
          }
        } else
          ScaffoldMessenger.of(context).showSnackBar(
              SnackBar(content: Text('${value["data"]["message"]}')));
      });
    } else if (response.statusCode == 401) {
      message(
        context,
        'Ops!',
        'Email and/or password not found.',
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
