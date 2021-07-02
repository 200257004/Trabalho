import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:http/http.dart' as http;
import 'dart:io';
import 'dart:convert';
import 'dart:async';

class APIProvider {
  static const String API_URL = "http://116.203.235.9:8080/tizito/api";
  final _storage = FlutterSecureStorage();

  Future<dynamic> get(String url) async {
    final String? token = await _storage.read(key: "token");

    var res;
    try {
      final response = await http.get(
        Uri.parse(API_URL + url),
        headers: {'Authorization': 'Basic $token'},
      );
      res = _response(response);
    } on SocketException {
      print("Not Internet Connection");
    }
    return res;
  }

  Future<dynamic> post(String url, dynamic body) async {
    final String? token = await _storage.read(key: "token");

    var res;
    try {
      final response = await http.post(Uri.parse(API_URL + url),
          headers: {
            HttpHeaders.contentTypeHeader: 'application/json',
            'Authorization': 'Basic $token'
          },
          body: json.encode(body));
      res = _response(response);
    } on SocketException {
      print("Not Internet Connection");
    }
    return res;
  }

  Future<dynamic> put(String url, dynamic body) async {
    final String? token = await _storage.read(key: "token");

    var res;
    try {
      final response = await http.put(Uri.parse(API_URL + url),
          headers: {
            HttpHeaders.contentTypeHeader: 'application/json',
            'Authorization': 'Basic $token'
          },
          body: json.encode(body));
      res = _response(response);
    } on SocketException {
      print("Not Internet Connection");
    }
    return res;
  }

  Future<dynamic> delete(String url) async {
    final String? token = await _storage.read(key: "token");

    var res;
    try {
      final response = await http.delete(Uri.parse(API_URL + url),
          headers: {'Authorization': 'Basic $token'});
      res = _response(response);
    } on SocketException {
      print("Not Internet Connection");
    }
    return res;
  }

  dynamic _response(http.Response res) {
    final statusCode = res.statusCode;
    if (statusCode >= 200 && statusCode < 210 ||
        statusCode == 400 ||
        statusCode == 401) {
      if (res.body != "") {
        var response = json.decode(res.body.toString());
        return {"statusCode": statusCode, "data": response};
      }
      return {"statusCode": statusCode, "data": []};
    }
    return {
      "statusCode": statusCode,
      "data": {"message": "Something wrong happened with the server."}
    };
  }
}
