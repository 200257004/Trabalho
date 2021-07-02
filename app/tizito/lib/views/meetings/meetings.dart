import 'package:flutter/material.dart';
import 'package:tizito/api/meeting_provider.dart';
import 'package:tizito/models/meeting.dart';
import 'package:tizito/models/profile.dart';
import 'package:tizito/views/meetings/meeting.dart';
import 'package:tizito/views/meetings/meeting_card.dart';
import 'package:tizito/views/meetings/meeting_detail.dart';

class MeetingsItemView extends StatefulWidget {
  final Profile profile;
  MeetingsItemView({Key? key, required this.profile}) : super(key: key);

  _MeetingsItemViewState createState() => _MeetingsItemViewState();
}

class _MeetingsItemViewState extends State<MeetingsItemView> {
  late Future<List<Meeting>> _meetings;

  @override
  void initState() {
    super.initState();
    _meetings = _getMeetings();
  }

  Future<List<Meeting>> _getMeetings() async {
    dynamic response = await MeetingProvider().getMeetings();

    if (response["statusCode"] == 200) {
      List<Meeting> contacts = [];
      if (response["data"].length > 0) {
        List<dynamic> cr = response["data"];
        contacts = cr.map((e) => Meeting.fromJson(e)).toList();
      }
      return contacts;
    }
    ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('${response["data"]["message"]}')));
    return [];
  }

  @override
  Widget build(BuildContext context) {
    return Material(
      child: Padding(
        padding: EdgeInsets.all(8),
        child: Stack(
          children: <Widget>[
            Align(
              alignment: Alignment.center,
              child: FutureBuilder<List<Meeting>>(
                future: _meetings,
                builder: (ctx, snapshot) {
                  List<Meeting>? meetings = snapshot.data;
                  switch (snapshot.connectionState) {
                    case ConnectionState.done:
                      if (meetings!.isNotEmpty)
                        return _buildListView(meetings);
                      else
                        return _buildNoDataView();
                    default:
                      return _buildLoadingScreen();
                  }
                },
              ),
            ),
            Align(
              alignment: Alignment.bottomRight,
              child: FloatingActionButton(
                onPressed: () => Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => MeetingPage(
                      profile: widget.profile,
                    ),
                  ),
                ),
                child: Icon(Icons.add),
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildNoDataView() {
    return Column(mainAxisAlignment: MainAxisAlignment.center, children: [
      Icon(Icons.link),
      Text(
        'No meetings.',
        style: TextStyle(
          fontWeight: FontWeight.bold,
        ),
      ),
      Text(
          'Make a meeting with your friends.\nDefine when, where and what to do.')
    ]);
  }

  Widget _buildListView(List<Meeting> meetings) {
    return ListView.builder(
      itemBuilder: (ctx, idx) {
        return GestureDetector(
          onTap: () => openDetails(meetings[idx]),
          child: MeetingCard(meetings[idx]),
        );
      },
      itemCount: meetings.length,
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

  openDetails(Meeting meeting) {
    Navigator.push(
      context,
      MaterialPageRoute(builder: (context) => MeetingDetail(meeting)),
    );
  }
}
