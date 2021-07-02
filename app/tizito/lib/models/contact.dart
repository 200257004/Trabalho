import 'package:enum_to_string/enum_to_string.dart';

class Contact {
  int id;
  String name;
  ContactStatus status;

  Contact({required this.id, required this.name, required this.status});

  Contact.fromJson(Map<String, dynamic> json)
      : id = json["id"],
        name = json["requester"]["name"],
        status = (json["response"] == null) ? ContactStatus.PENDING : EnumToString.fromString(ContactStatus.values, json["response"]) as ContactStatus;
}

enum ContactStatus { ACCEPTED, NO_ACCEPTED, PENDING }
