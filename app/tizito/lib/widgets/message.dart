import 'package:flutter/material.dart';

message(context, title, content) {
  showDialog(
    context: context,
    builder: (BuildContext context) {
      return AlertDialog(
        title: Text(
          title,
        ),
        content: Text(
          content,
        ),
        actions: [
          TextButton(
            onPressed: () {
              Navigator.pop(context);
            },
            child: Text(
              'OK',
            ),
          )
        ],
      );
    },
  );
}
