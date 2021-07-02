class Trouble {
  final String message;

  Trouble(this.message);

  factory Trouble.fromJson(Map<String, dynamic> json) {
    return Trouble(json['message']);
  }
}
