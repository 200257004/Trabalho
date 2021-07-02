import 'package:flutter/material.dart';

class Footer extends StatelessWidget {
  final String question;

  final String action;

  final Widget view;

  Footer(this.question, this.action, this.view);

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: EdgeInsets.only(
        top: 32,
        bottom: 16,
      ),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Text(
            this.question,
          ),
          TextButton(
            onPressed: () {
              Navigator.pushAndRemoveUntil(
                context,
                MaterialPageRoute(
                  builder: (context) => this.view,
                ),
                (route) => false,
              );
            },
            child: Text(
              this.action,
              style: TextStyle(
                fontWeight: FontWeight.bold,
              ),
            ),
          ),
        ],
      ),
    );
  }
}
