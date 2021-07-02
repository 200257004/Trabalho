import 'package:tizito/models/meeting.dart';

import 'api_provider.dart';

class MeetingProvider {
  static const String MEETING_URL = "/meetings";

  Future<dynamic> getMeetings() async {
    try {
      APIProvider api = new APIProvider();
      final dynamic response = await api.get(MEETING_URL);
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

  Future<dynamic> postMeeting(MeetingPostModel newMeeting) async {
    try {
      APIProvider api = new APIProvider();
      final dynamic response = await api.post(MEETING_URL, newMeeting.toJson());
      if (response != null && response['data'] != null) {
        return response;
      }
    } on Exception{
      print('An Error Ocurred');
    }
    return null;
  }
}
