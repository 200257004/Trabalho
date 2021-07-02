import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:tizito/models/invite.dart';
import 'package:tizito/models/meeting.dart';
import 'package:tizito/views/meetings/meeting_location.dart';

class MeetingDetail extends StatelessWidget {
  final Meeting meeting;

  MeetingDetail(this.meeting);
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
                    text: '${meeting.idea}',
                    style: TextStyle(fontSize: 20),
                    children: <TextSpan>[
                      TextSpan(
                        text:
                            '\n${(meeting.invites.length)} convites',
                        style: TextStyle(
                          fontSize: 16,
                        ),
                      ),
                    ]),
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

    list.add(ListTile(
      leading: Icon(Icons.calendar_today),
      title: Text('${DateFormat("dd-MM-yyyy").format(meeting.datetime)}'),
    ));

    list.add(ListTile(
      leading: Icon(Icons.watch_later_outlined),
      title: Text('${DateFormat("HH:mm").format(meeting.datetime)}'),
    ));

    list.add(SizedBox(
      height: MediaQuery.of(context).size.height * 0.4,
      width: MediaQuery.of(context).size.width,
      child: MeetingLocation(meeting: meeting),
    ));

    list.add(ListTile(
      leading: Icon(Icons.group),
      title: Text('People Invited:'),
    ));

    list.addAll(meeting.invites
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
