import 'package:flutter/material.dart';

class Header extends StatelessWidget {
  final String title;

  final String description;

  Header(this.title, this.description);

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Padding(
          padding: EdgeInsets.only(
            top: 16,
          ),
          child: Text(
            this.title,
            style: TextStyle(
              fontSize: 24,
              fontWeight: FontWeight.bold,
            ),
          ),
        ),
        Padding(
          padding: EdgeInsets.only(
            top: 8,
          ),
          child: Text(
            this.description,
          ),
        ),
      ],
    );
  }
}
