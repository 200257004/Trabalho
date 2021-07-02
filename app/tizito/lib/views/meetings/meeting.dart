import 'package:flutter/material.dart';
import 'package:flutter_datetime_picker/flutter_datetime_picker.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:intl/intl.dart';
import 'package:tizito/api/contact_provider.dart';
import 'package:tizito/models/meeting.dart';
import 'package:tizito/models/profile.dart';
import 'package:tizito/views/meetings/location_picker.dart';
import '../custom_date_picker.dart';
import 'package:tizito/models/user.dart';

class MeetingPage extends StatefulWidget {
  final Profile profile;
  MeetingPage({Key? key, required this.profile}) : super(key: key);

  @override
  _MeetingPageState createState() => _MeetingPageState();
}

class _MeetingPageState extends State<MeetingPage> {
  int currentStep = 0;
  late List<User> friends;
  late List<User> invited;
  List<Step> steps = [];
  late DateTime time;
  DateTime now = DateTime.now();
  late Future<List<User>> _future;

  final activityController = TextEditingController();

  @override
  void initState() {
    super.initState();
    time = DateTime(1900, 1, 1);
    _future = loadFriends();
    invited = [];
  }

  Future<List<User>> loadFriends() async {
    dynamic response = await ContactProvider().getFriends();

    if (response["statusCode"] == 200) {
      List<User> friends = [];
      if (response["data"].length > 0) {
        List<dynamic> fr = response["data"];
        friends = fr.map((e) => User.fromJson(e)).toList();
      }
      return friends;
    }
    ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('${response["data"]["message"]}')));
    return [];
  }

  next() {
    currentStep + 1 != steps.length
        ? goTo(currentStep + 1)
        : setState(() => finish());
  }

  finish() {
    if (invited.isNotEmpty && DateTime.now().isBefore(time)) {
      MeetingPostModel newMeeting = MeetingPostModel(
          description: activityController.text,
          location: LatLng(-0.01, -0.01),
          datetime: time,
          inviteds: invited.map((e) => e.id).toList());
      Navigator.push(
          context,
          MaterialPageRoute(
              builder: (context) => LocationPicker(
                    newMeeting: newMeeting,
                    profile: widget.profile,
                  )));
    }
  }

  cancel() {
    if (currentStep > 0) {
      goTo(currentStep - 1);
    }
  }

  goTo(int step) {
    setState(() => currentStep = step);
  }

  invite(User friend) {
    List<User> list = invited;
    list.add(friend);
    setState(() => invited = list);
  }

  uninvite(User friend) {
    List<User> list = invited;
    list.remove(friend);
    setState(() => invited = list);
  }

  bool isAlreadyInvited(User friend) {
    return invited.where((e) => e.id == friend.id).length > 0;
  }

  StepState checkStepState(int i) {
    if (currentStep == i)
      return StepState.editing;
    else if (currentStep > i)
      return StepState.complete;
    else
      return StepState.disabled;
  }

  @override
  Widget build(BuildContext context) {
    steps = [
      Step(
        title: const Text('Who'),
        isActive: currentStep == 0,
        state: checkStepState(0),
        content: Container(
            child: FutureBuilder<List<User>>(
          future: _future,
          builder: (ctx, snap) {
            List<User>? users = snap.data;
            switch (snap.connectionState) {
              case ConnectionState.done:
                if (users!.isNotEmpty)
                  return buildFriendList(users);
                else
                  return _buildNoDataView();
              default:
                return _buildLoadingScreen();
            }
          },
        )),
      ),
      Step(
        isActive: currentStep == 1,
        state: checkStepState(1),
        title: const Text('What'),
        content: Column(
          children: <Widget>[
            TextFormField(
              decoration: InputDecoration(labelText: 'Activity'),
              controller: activityController,
            ),
          ],
        ),
      ),
      Step(
        state: checkStepState(2),
        title: const Text('When'),
        isActive: currentStep == 2,
        content: Column(
          children: <Widget>[
            Row(
              children: [
                Text(time.isBefore(DateTime.now().subtract(Duration(days: 1)))
                    ? "Pick a date"
                    : DateFormat('dd-MM-yyyy').format(time)),
                IconButton(
                    onPressed: () => DatePicker.showDatePicker(context,
                        showTitleActions: true,
                        minTime: now,
                        maxTime: now.add(Duration(days: 36500)),
                        onChanged: (date) {
                          print('change $date');
                        },
                        onConfirm: (date) => setState(() =>
                            time = DateTime(date.year, date.month, date.day)),
                        currentTime: DateTime.now(),
                        locale: LocaleType.pt),
                    icon: Icon(Icons.calendar_today_outlined)),
                Text(time.isBefore(now.add(Duration(minutes: 30)))
                    ? "Pick a time"
                    : DateFormat('HH:mm').format(time)),
                IconButton(
                    onPressed: () => DatePicker.showPicker(context,
                        showTitleActions: true,
                        pickerModel: CustomPicker(currentTime: now),
                        onChanged: (date) {
                          print('change $date');
                        },
                        onConfirm: (date) => setState(() => time = DateTime(
                            time.year,
                            time.month,
                            time.day,
                            date.hour,
                            date.minute)),
                        locale: LocaleType.pt),
                    icon: Icon(Icons.alarm))
              ],
            )
          ],
        ),
      )
    ];

    return Scaffold(
        appBar: AppBar(
          leading: IconButton(
            onPressed: () {
              Navigator.of(context).pop();
            },
            icon: Icon(
              Icons.arrow_back,
            ),
          ),
          title: Text(
            'New meeting',
          ),
        ),
        body: Container(
            child: Padding(
                padding: EdgeInsets.all(8),
                child: Column(children: [
                  Expanded(
                    child: Stepper(
                        steps: steps,
                        currentStep: currentStep,
                        onStepContinue: next,
                        onStepTapped: (step) => goTo(step),
                        onStepCancel: cancel,
                        type: StepperType.vertical),
                  )
                ]))));
  }

  Widget buildFriendList(List<User> users) {
    return Row(
      children: [
        Expanded(
            flex: 10,
            child: ListView.builder(
              shrinkWrap: true,
              itemCount: users.length,
              itemBuilder: (context, index) {
                return Card(
                  child: ListTile(
                      leading: Icon(Icons.person),
                      title: Text('${users[index].name}'),
                      trailing: GestureDetector(
                          behavior: HitTestBehavior.translucent,
                          onTap: () => isAlreadyInvited(users[index])
                              ? uninvite(users[index])
                              : invite(users[index]),
                          child: Container(
                            height: 150,
                            child: (isAlreadyInvited(users[index]))
                                ? Icon(Icons.remove)
                                : Icon(Icons.add),
                          ))),
                );
              },
            ))
      ],
    );
  }

  Widget _buildNoDataView() {
    return Column(mainAxisAlignment: MainAxisAlignment.center, children: [
      Icon(Icons.link),
      Text(
        'No Friends.',
        style: TextStyle(
          fontWeight: FontWeight.bold,
        ),
      ),
      Text('Please add friends first.')
    ]);
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
}
