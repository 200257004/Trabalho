import 'package:flutter/material.dart';

class Name extends StatelessWidget {
  final TextEditingController controller;

  Name(this.controller);

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: EdgeInsets.only(
        top: 16,
      ),
      child: TextFormField(
        decoration: InputDecoration(
          labelText: "Name",
        ),
        keyboardType: TextInputType.name,
        controller: controller,
      ),
    );
  }
}
