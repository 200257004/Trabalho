import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:tizito/models/meeting.dart';

class MeetingCard extends StatelessWidget {
  final Meeting meeting;

  MeetingCard(this.meeting);
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
                    Icons.person_pin,
                    size: 40,
                  ),
                ),
              ),
              Expanded(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      '${meeting.inviter.name}',
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
              '${meeting.idea}',
              style: TextStyle(fontSize: 30, fontWeight: FontWeight.bold),
            ),
          ),
          Align(
            alignment: Alignment.centerLeft,
            child: Text(
              '${DateFormat("dd-MM-yyyy HH:mm").format(meeting.datetime)}',
              style: TextStyle(fontSize: 20),
            ),
          ),
        ],
      ),
    );
  }
}
