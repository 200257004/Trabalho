import 'package:enum_to_string/enum_to_string.dart';
import 'package:tizito/models/contact.dart';
import 'package:tizito/models/user.dart';
import 'api_provider.dart';

class ContactProvider {
  static const String CONTACTS_URL = "/requests";
  static const String FRIENDS_URL = "/friends";

  Future<dynamic> getContacts() async {
    try {
      APIProvider api = new APIProvider();
      final dynamic response = await api.get(CONTACTS_URL);
      if (response != null && response['data'] != null) {
        return response;
      }
    } on Exception catch (e) {
      print(e);
    }
    return {
      "statusCode": 500,
      "data": {"message": "Something wrong happened with the server."}
    };
  }

  Future<dynamic> getFriends() async {
    try {
      APIProvider api = new APIProvider();
      final dynamic response = await api.get(FRIENDS_URL);
      if (response != null && response['data'] != null) {
        return response;
      }
    } on Exception catch (e) {
      print(e);
    }
    return {
      "statusCode": 500,
      "data": {"message": "Something wrong happened with the server."}
    };
  }

  Future<dynamic> postContact(User user) async {
    try {
      APIProvider api = new APIProvider();
      final dynamic response =
          await api.post(CONTACTS_URL, {"requested": user.id});
      if (response != null && response['data'] != null) {
        return response;
      }
    } on Exception {
      print('An Error Ocurred');
    }
    return {
      "statusCode": 500,
      "data": {"message": "Something wrong happened with the server."}
    };
  }

  Future<dynamic> deleteContact(Contact contact) async {
    try {
      APIProvider api = new APIProvider();
      final dynamic response = await api.delete(FRIENDS_URL + '/${contact.id}');
      if (response != null && response['data'] != null) {
        return response;
      }
    } on Exception {
      print('An Error Ocurred');
    }
    return null;
  }

  Future<dynamic> answerContactRequest(Contact contact, ContactStatus status) async {
    try {
      APIProvider api = new APIProvider();
      final dynamic response = await api.put(
          '$CONTACTS_URL/${contact.id}/response',
          {"response": EnumToString.convertToString(status)});
      if (response != null && response['data'] != null) {
        return response;
      }
    } on Exception {
      print('An Error Ocurred');
    }
    return {
      "statusCode": 500,
      "data": {"message": "Something wrong happened with the server."}
    };
  }
}
