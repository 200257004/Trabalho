import 'package:flutter/material.dart';

class Users extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        leading: IconButton(
          onPressed: () {
            Navigator.of(context).pop();
          },
          icon: Icon(
            Icons.arrow_back,
          ),
        ),
      ),
      body: Container(
        child: Text(
          'Users',
        ),
      ),
    );
  }
}
