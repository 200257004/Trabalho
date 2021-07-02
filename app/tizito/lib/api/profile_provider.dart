import 'api_provider.dart';

class ProfileProvider {
  static const String INVITES_URL = "/profile";

  Future<dynamic> getProfile() async {
    try {
      APIProvider api = new APIProvider();
      final dynamic response = await api.get(INVITES_URL);
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
