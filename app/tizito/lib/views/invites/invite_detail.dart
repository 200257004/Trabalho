import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:tizito/api/invite_provider.dart';
import 'package:tizito/models/invite.dart';
import 'package:tizito/models/meeting_invite.dart';
import 'package:tizito/views/meetings/meeting_location.dart';

class InviteDetail extends StatelessWidget {
  final MeetingInvite meetingInvite;
  final dynamic callback;

  acceptInvite(BuildContext context) {
    InviteProvider()
        .answerInviteRequest(meetingInvite, MeetingResponse.ACCEPTED)
        .then((value) {
      if (value["statusCode"] >= 200 && value["statusCode"] < 210) {
        callback();
        Navigator.pop(context);
      } else
        ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text('${value["data"]["message"]}')));
    });
  }

  rejectInvite(BuildContext context) {
    InviteProvider()
        .answerInviteRequest(meetingInvite, MeetingResponse.NO_ACCEPTED)
        .then((value) {
      if (value["statusCode"] >= 200 && value["statusCode"] < 210) {
        callback();
        Navigator.pop(context);
      } else
        ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text('${value["data"]["message"]}')));
    });
  }

  InviteDetail(this.meetingInvite, this.callback);
  @override
  Widget build(BuildContext context) {
    return Material(
      child: CustomScrollView(
        slivers: <Widget>[
          SliverAppBar(
            pinned: true,
            expandedHeight: 190.0,
            flexibleSpace: FlexibleSpaceBar(
              title: RichText(
                textAlign: TextAlign.left,
                text: TextSpan(
                    text: '${meetingInvite.meeting.idea}',
                    style: TextStyle(fontSize: 20),
                    children: []),
              ),
            ),
          ),
          SliverList(
            delegate: SliverChildListDelegate(
              getInvited(context), // ListTiles++
            ),
          ),
        ],
      ),
    );
  }

  List<Widget> getInvited(BuildContext context) {
    List<Widget> list = [];

    list.add(ButtonBar(
      alignment: MainAxisAlignment.center,
      children: [
        IconButton(
          icon: Icon(
            Icons.check,
            size: 40,
            color: Colors.green,
          ),
          onPressed: () => acceptInvite(context),
        ),
        SizedBox(width: 40),
        IconButton(
          icon: Icon(
            Icons.cancel,
            size: 40,
            color: Colors.red,
          ),
          onPressed: () => rejectInvite(context),
        ),
      ],
    ));

    list.add(ListTile(
      leading: Icon(Icons.calendar_today),
      title: Text(
          '${DateFormat("dd-MM-yyyy").format(meetingInvite.meeting.datetime)}'),
    ));

    list.add(ListTile(
      leading: Icon(Icons.watch_later_outlined),
      title:
          Text('${DateFormat("HH:mm").format(meetingInvite.meeting.datetime)}'),
    ));

    list.add(SizedBox(
      height: MediaQuery.of(context).size.height * 0.4,
      width: MediaQuery.of(context).size.width,
      child: MeetingLocation(meeting: meetingInvite.meeting),
    ));

    list.add(ListTile(
      leading: Icon(Icons.group),
      title: Text('People Invited:'),
    ));

    list.addAll(meetingInvite.meeting.invites
        .map((e) => ListTile(
              leading: Icon(Icons.person),
              title: Text('${e.invited.name}'),
              trailing: checkInvite(e),
            ))
        .toList());

    return list;
  }

  Widget checkInvite(Invite invite) {
    switch (invite.response) {
      case MeetingResponse.ACCEPTED:
        return Icon(Icons.check);
      case MeetingResponse.NO_ACCEPTED:
        return Icon(Icons.cancel);
      default:
        return Icon(Icons.help);
    }
  }
}
