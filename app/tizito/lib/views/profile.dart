import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:tizito/models/profile.dart';

class ProfilePage extends StatelessWidget {
  final Profile profile;
  ProfilePage(this.profile);
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(
          'Profile Details',
        ),
      ),
      body: Column(
        children: <Widget>[
          Padding(
            padding: const EdgeInsets.fromLTRB(0, 50, 0, 50),
            child: Stack(
              children: <Widget>[
                CircleAvatar(
                  radius: 70,
                  child: Image.asset(
                    'assets/images/profile_icon.png',
                    height: 150,
                    width: 150,
                    fit: BoxFit.cover,
                  ),
                ),
              ],
            ),
          ),
          Expanded(
              child: Container(
            decoration: BoxDecoration(
                borderRadius: BorderRadius.only(
                    topLeft: Radius.circular(30),
                    topRight: Radius.circular(30)),
                gradient: LinearGradient(
                    begin: Alignment.topRight,
                    end: Alignment.bottomLeft,
                    colors: [Colors.orange.shade400, Colors.orange.shade800])),
            child: Column(
              children: <Widget>[
                Padding(
                  padding: const EdgeInsets.fromLTRB(20, 25, 20, 4),
                  child: Container(
                    height: 60,
                    child: Align(
                      alignment: Alignment.centerLeft,
                      child: Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: Text(
                          '${profile.name}',
                        ),
                      ),
                    ),
                    decoration: BoxDecoration(
                        borderRadius: BorderRadius.all(Radius.circular(20)),
                        border: Border.all(
                          width: 1.0,
                        )),
                  ),
                ),
                Padding(
                  padding: const EdgeInsets.fromLTRB(20, 5, 20, 4),
                  child: Container(
                    height: 60,
                    child: Align(
                      alignment: Alignment.centerLeft,
                      child: Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: Text(
                          '${profile.email}',
                        ),
                      ),
                    ),
                    decoration: BoxDecoration(
                        borderRadius: BorderRadius.all(Radius.circular(20)),
                        border: Border.all(
                          width: 1.0,
                        )),
                  ),
                ),
              ],
            ),
          ))
        ],
      ),
    );
  }
}
