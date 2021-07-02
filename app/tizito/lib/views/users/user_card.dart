import 'package:flutter/material.dart';
import 'package:tizito/api/contact_provider.dart';
import 'package:tizito/models/user.dart';

class UserCard extends StatelessWidget {
  final User user;

  UserCard(this.user);

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 10),
      decoration: BoxDecoration(
        border: Border(
          bottom: BorderSide(
            color: Colors.grey,
            width: 0.5,
          ),
        ),
      ),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Container(
            padding: const EdgeInsets.only(top: 10, right: 15, bottom: 10),
            child: ClipOval(
              child: Icon(
                Icons.person,
                size: 40,
              ),
            ),
          ),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  '${user.name}',
                  style: TextStyle(
                    fontSize: 20,
                    color: Colors.grey,
                  ),
                ),
              ],
            ),
          ),
          Row(
            mainAxisSize: MainAxisSize.min,
            children: [
              TextButton(
                onPressed: () => sendFriendRequest(context),
                child: Icon(
                  Icons.add_circle,
                  color: Colors.grey[600],
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }

  sendFriendRequest(BuildContext context) async {
    // POST friend request
    ContactProvider().postContact(user).then((value) {
      if (value["statusCode"] >= 200 && value["statusCode"] < 210)
        ScaffoldMessenger.of(context)
            .showSnackBar(SnackBar(content: Text('Friend Request Sent')));
      else
        ScaffoldMessenger.of(context)
            .showSnackBar(SnackBar(content: Text('${value["data"]["message"]}')));
    });
  }
}
