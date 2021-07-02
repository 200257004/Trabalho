class User {
  int id;
  String name;

  User({required this.id, required this.name});

  User.fromJson(Map<String, dynamic> json)
      : id = json["id"],
        name = json["name"];

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'name': name,
    };
  }
}
