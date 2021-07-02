import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:tizito/models/invite.dart';
import 'user.dart';

class Meeting {
  int id;
  List<Invite> invites;
  String idea;
  User inviter;
  DateTime datetime;
  LatLng location;

  Meeting(
      {required this.id,
      required this.invites,
      required this.idea,
      required this.inviter,
      required this.datetime,
      required this.location});

  Meeting.fromJson(Map<String, dynamic> json)
      : id = json["id"],
        invites =
            (json['invites'] as List).map((e) => Invite.fromJson(e)).toList(),
        idea = json["description"],
        inviter = User.fromJson(json["inviter"]),
        datetime = DateTime(
            json["datetime"]["year"],
            json["datetime"]["month"],
            json["datetime"]["dayOfMonth"],
            json["datetime"]["hourOfDay"],
            json["datetime"]["minute"],
            json["datetime"]["second"]),
        location = LatLng(json["latitude"], json["longitude"]);

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'invites': invites.map((e) => e.toJson()).toList(),
      'idea': idea,
      'inviter': inviter.toJson(),
      'datetime': {
        "year": datetime.year,
        "month": datetime.month,
        "dayOfMonth": datetime.day,
        "hourOfDay": datetime.hour,
        "minute": datetime.minute,
        "second": datetime.second
      },
      'latitude': location.latitude,
      'longitude': location.longitude
    };
  }
}

class MeetingPostModel {
  String description;
  LatLng location;
  DateTime datetime;
  List<int> inviteds;

  MeetingPostModel(
      {required this.description,
      required this.location,
      required this.datetime,
      required this.inviteds});

  Map<String, dynamic> toJson() {
    return {
      'description': description,
      'latitude': location.latitude,
      'longitude': location.longitude,
      'datetime': {
        "year": datetime.year,
        "month": datetime.month,
        "dayOfMonth": datetime.day,
        "hourOfDay": datetime.hour,
        "minute": datetime.minute,
        "second": datetime.second
      },
      'inviteds': inviteds
    };
  }
}
