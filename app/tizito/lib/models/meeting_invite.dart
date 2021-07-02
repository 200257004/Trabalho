import 'package:enum_to_string/enum_to_string.dart';
import 'package:tizito/models/invite.dart';
import 'package:tizito/models/meeting.dart';
import 'package:tizito/models/user.dart';

class MeetingInvite {
  Meeting meeting;
  int id;
  User invited;
  MeetingResponse response;

  MeetingInvite(
      {required this.meeting,
      required this.id,
      required this.invited,
      required this.response});

  MeetingInvite.fromJson(Map<String, dynamic> json)
      : meeting = Meeting.fromJson(json["details"]),
        id = json["id"],
        invited = User.fromJson(json["invited"]),
        response = (json["response"] == null || json["response"] == "")
            ? MeetingResponse.NO_RESPONSE
            : EnumToString.fromString(MeetingResponse.values, json["response"])
                as MeetingResponse;

  Map<String, dynamic> toJson() {
    return {
      'meeting': meeting.toJson(),
      'id': id,
      'invited': invited.toJson(),
      'response':
          (response != MeetingResponse.NO_RESPONSE) ? response.toString() : ""
    };
  }
}
