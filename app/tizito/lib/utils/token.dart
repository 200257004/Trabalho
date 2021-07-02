import 'dart:convert';

String getToken(email, password) {
  String text = email + ':' + password;

  List<int> encodedText = utf8.encode(text);

  return base64.encode(encodedText);
}
