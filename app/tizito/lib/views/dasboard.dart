import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:tizito/models/profile.dart';
import 'package:tizito/views/contacts/contact_page.dart';
import 'package:tizito/views/invites/invites_page.dart';
import 'package:tizito/views/login.dart';
import 'package:tizito/views/meetings/meetings.dart';
import 'package:tizito/views/profile.dart';

class Dashboard extends StatefulWidget {
  final Profile profile;

  Dashboard({Key? key, required this.profile}) : super(key: key);

  @override
  _DashboardState createState() => _DashboardState();
}

class _DashboardState extends State<Dashboard> {
  final _storage = FlutterSecureStorage();

  int _selectedIndex = 0;

  PageController pageController = PageController(
    initialPage: 0,
    keepPage: true,
  );

  Widget buildPageView() {
    return PageView(
      controller: pageController,
      onPageChanged: (index) {
        pageChanged(index);
      },
      children: <Widget>[
        MeetingsItemView(profile: widget.profile),
        InvitesPage(),
        ContactsPage(),
      ],
    );
  }

  @override
  void initState() {
    super.initState();
  }

  void pageChanged(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  void changeView(int index) {
    setState(() {
      _selectedIndex = index;
      pageController.animateToPage(
        index,
        duration: Duration(milliseconds: 500),
        curve: Curves.ease,
      );
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      drawer: Drawer(
        child: ListView(
          padding: EdgeInsets.zero,
          children: [
            DrawerHeader(
              child: Text(
                'Hello ${widget.profile.name}.',
              ),
              decoration: BoxDecoration(
                color: Colors.orange,
              ),
            ),
            ListTile(
              title: Text(
                'Profile',
              ),
              onTap: () => Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => ProfilePage(widget.profile),
                  )),
            ),
            // ListTile(
            //   title: Text(
            //     'Account',
            //   ),
            //   onTap: () {
            //     //
            //     Navigator.pop(context);
            //   },
            // ),
            ListTile(
              title: Text(
                'Logout',
              ),
              onTap: () async {
                await _storage.delete(key: 'token');

                Navigator.pushAndRemoveUntil(
                  context,
                  MaterialPageRoute(
                    builder: (context) => Login(),
                  ),
                  (route) => false,
                );
              },
            ),
          ],
        ),
      ),
      appBar: AppBar(
        title: Text(
          'Tizito',
        ),
        actions: [
          // IconButton(
          //   onPressed: () {
          //     //
          //   },
          //   icon: Icon(
          //     Icons.notifications,
          //   ),
          // )
        ],
      ),
      body: buildPageView(),
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: _selectedIndex,
        onTap: (index) {
          changeView(index);
        },
        items: [
          BottomNavigationBarItem(
            icon: Icon(
              Icons.home,
            ),
            label: 'Home',
          ),
          BottomNavigationBarItem(
            icon: Icon(
              Icons.email,
            ),
            label: 'Invites',
          ),
          BottomNavigationBarItem(
            icon: Icon(
              Icons.people,
            ),
            label: 'Friends',
          ),
        ],
      ),
    );
  }
}
