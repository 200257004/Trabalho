import 'package:flutter/material.dart';

class Password extends StatelessWidget {
  final TextEditingController controller;

  Password(this.controller);

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: EdgeInsets.only(
        top: 16,
      ),
      child: TextFormField(
        decoration: InputDecoration(
          labelText: "Password",
        ),
        keyboardType: TextInputType.text,
        controller: controller,
        obscureText: true,
        enableSuggestions: false,
        autocorrect: false,
      ),
    );
  }
}
