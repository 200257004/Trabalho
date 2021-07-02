import 'package:flutter/material.dart';
import 'package:tizito/api/invite_provider.dart';
import 'package:tizito/models/meeting_invite.dart';
import 'package:tizito/views/invites/invite_card.dart';
import 'package:tizito/views/invites/invite_detail.dart';

class InvitesPage extends StatefulWidget {
  InvitesPage({Key? key}) : super(key: key);

  _InvitesPageState createState() => _InvitesPageState();
}

class _InvitesPageState extends State<InvitesPage> {
  late Future<List<MeetingInvite>> _meetingInvites;

  @override
  initState() {
    super.initState();
    _meetingInvites = getInvites(context);
  }

  Future<List<MeetingInvite>> getInvites(BuildContext context) async {
    dynamic response = await InviteProvider().getInvites();

    if (response["statusCode"] == 200) {
      List<MeetingInvite> invites = [];
      if (response["data"].length > 0) {
        List<dynamic> res = response["data"];
        invites = res.map((e) => MeetingInvite.fromJson(e)).toList();
      }
      return invites;
    }
    ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('${response["data"]["message"]}')));
    return [];
  }

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: <Widget>[
        Align(
          alignment: Alignment.topLeft,
          child: FutureBuilder<List<MeetingInvite>>(
            future: _meetingInvites,
            builder: (ctx, snapshot) {
              List<MeetingInvite>? meetingInvites = snapshot.data;
              switch (snapshot.connectionState) {
                case ConnectionState.done:
                  if (meetingInvites!.isNotEmpty)
                    return _buildListView(meetingInvites);
                  else
                    return _buildNoDataView();
                default:
                  return _buildLoadingScreen();
              }
            },
          ),
        ),
      ],
    );
  }

  Widget _buildListView(List<MeetingInvite> invites) {
    return ListView.builder(
      itemBuilder: (ctx, idx) {
        return GestureDetector(
          onTap: () => openDetails(context, invites[idx]),
          child: InviteCard(
            invite: invites[idx],
            callback: () =>
                setState(() => {_meetingInvites = getInvites(context)}),
          ),
        );
      },
      itemCount: invites.length,
    );
  }

  Widget _buildLoadingScreen() {
    return Center(
      child: Container(
        width: 50,
        height: 50,
        child: CircularProgressIndicator(),
      ),
    );
  }

  Widget _buildNoDataView() {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Icon(
            Icons.email,
          ),
          Text(
            'No invites.',
            style: TextStyle(
              fontWeight: FontWeight.bold,
            ),
          ),
          Text(
            'When you have invites, they show up here.',
          ),
        ],
      ),
    );
  }

  openDetails(BuildContext context, MeetingInvite meetingInvite) {
    Navigator.push(
      context,
      MaterialPageRoute(
          builder: (context) => InviteDetail(meetingInvite,
              () => setState(() => {_meetingInvites = getInvites(context)}))),
    );
  }
}
