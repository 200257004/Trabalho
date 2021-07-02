import 'package:enum_to_string/enum_to_string.dart';

import 'user.dart';

class Invite {
  int id;
  User invited;
  MeetingResponse response;

  Invite({required this.id, required this.invited, required this.response});

  Invite.fromJson(Map<String, dynamic> json)
      : id = json["id"],
        invited = User.fromJson(json["invited"]),
        response = (json["response"] == null || json["response"] == "")
            ? MeetingResponse.NO_RESPONSE
            : EnumToString.fromString(MeetingResponse.values, json["response"])
                as MeetingResponse;

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'invited': invited.toJson(),
      'response':
          (response != MeetingResponse.NO_RESPONSE) ? response.toString() : ""
    };
  }
}

enum MeetingResponse { NO_RESPONSE, ACCEPTED, NO_ACCEPTED }
