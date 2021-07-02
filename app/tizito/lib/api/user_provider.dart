import 'api_provider.dart';

class UserProvider {
  static const String USERS_URL = "/users";

  Future<dynamic> getUsers() async {
    try {
      APIProvider api = new APIProvider();
      final dynamic response = await api.get(USERS_URL);
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
}
