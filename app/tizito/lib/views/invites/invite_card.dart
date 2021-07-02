import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:tizito/api/invite_provider.dart';
import 'package:tizito/models/invite.dart';
import 'package:tizito/models/meeting_invite.dart';

class InviteCard extends StatefulWidget {
  final MeetingInvite invite;
  final dynamic callback;

  InviteCard({Key? key, required this.invite, required this.callback})
      : super(key: key);

  _InviteCardState createState() => _InviteCardState();
}

class _InviteCardState extends State<InviteCard> {
  late MeetingInvite meetingInvite;

  @override
  initState() {
    super.initState();
    meetingInvite = widget.invite;
  }

  accept(BuildContext context) {
    // Accept Invite

    InviteProvider()
        .answerInviteRequest(meetingInvite, MeetingResponse.ACCEPTED)
        .then((value) {
      if (value["statusCode"] >= 200 && value["statusCode"] < 210) {
        widget.callback();
      } else
        ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text('${value["data"]["message"]}')));
    });
  }

  reject(BuildContext context) {
    // Reject Invite

    InviteProvider()
        .answerInviteRequest(meetingInvite, MeetingResponse.NO_ACCEPTED)
        .then((value) {
      if (value["statusCode"] >= 200 && value["statusCode"] < 210) {
        widget.callback();
      } else
        ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text('${value["data"]["message"]}')));
    });
  }

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
      child: Column(
        children: [
          Row(
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
                      '${meetingInvite.meeting.inviter.name}',
                      style: TextStyle(fontSize: 20),
                    ),
                  ],
                ),
              ),
            ],
          ),
          Align(
            alignment: Alignment.centerLeft,
            child: Text(
              '${meetingInvite.meeting.idea}',
              style: TextStyle(fontSize: 30, fontWeight: FontWeight.bold),
            ),
          ),
          Align(
            alignment: Alignment.centerLeft,
            child: Text(
              '${DateFormat("dd-MM-yyyy HH:mm").format(meetingInvite.meeting.datetime)}',
              style: TextStyle(fontSize: 20),
            ),
          ),
        ],
      ),
    );
  }

  List<Widget> getIcons(BuildContext context) {
    List<Widget> list = List.empty(growable: true);

    list.add(
      TextButton(
        onPressed: () => accept(context),
        child: Icon(
          Icons.check_circle,
          color: Colors.grey[600],
        ),
      ),
    );
    list.add(
      TextButton(
        onPressed: () => reject(context),
        child: Icon(
          Icons.cancel,
          color: Colors.grey[600],
        ),
      ),
    );

    return list;
  }
}
