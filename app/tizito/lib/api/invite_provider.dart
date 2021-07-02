import 'package:enum_to_string/enum_to_string.dart';
import 'package:tizito/models/invite.dart';
import 'package:tizito/models/meeting_invite.dart';
import 'api_provider.dart';

class InviteProvider {
  static const String INVITES_URL = "/invites";

  Future<dynamic> getInvites() async {
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

  Future<dynamic> answerInviteRequest(MeetingInvite meetingInvite, MeetingResponse status) async {
    try {
      APIProvider api = new APIProvider();
      final dynamic response = await api.put(
          '$INVITES_URL/${meetingInvite.id}/response',
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
