import 'package:flutter/material.dart';

class Email extends StatelessWidget {
  final TextEditingController controller;

  Email(this.controller);

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: EdgeInsets.only(
        top: 16,
      ),
      child: TextFormField(
        decoration: InputDecoration(
          labelText: "Email",
        ),
        keyboardType: TextInputType.emailAddress,
        controller: controller,
      ),
    );
  }
}
