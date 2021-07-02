class Profile {
  String name;
  String email;

  Profile({required this.name, required this.email});

  Profile.fromJson(Map<String, dynamic> json)
      : name = json["name"],
        email = json["email"];

  Map<String, dynamic> toJson() {
    return {"name": name, "email": email};
  }
}
