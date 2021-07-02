import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:tizito/api/user_provider.dart';
import 'package:tizito/models/user.dart';
import 'user_card.dart';

class UserPage extends StatefulWidget {
  UserPage({Key? key}) : super(key: key);

  _UserPageState createState() => _UserPageState();
}

class _UserPageState extends State<UserPage> {
  late Future<List<User>> _contacts;
  late Future<List<User>> _foundContacts;

  TextEditingController _controller = TextEditingController();

  @override
  void initState() {
    super.initState();
    _contacts = _getContacts();
    _foundContacts = _contacts;
  }

  Future<List<User>> _getContacts() async {
    dynamic response = await UserProvider().getUsers();

    if (response["statusCode"] == 200) {
      List<User> users = [];
      if (response["data"].length > 0) {
        List<dynamic> usr = response["data"];
        users = usr.map((e) => User.fromJson(e)).toList();
        return users;
      }
      return [];
    }

    ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('${response["data"]["message"]}')));
    return [];
  }

  @override
  Widget build(BuildContext context) {
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
          'Add Friends',
        ),
      ),
      body: Material(
          child: Padding(
        padding: EdgeInsets.all(8),
        child: Stack(
          children: <Widget>[
            Align(
              alignment: Alignment.topLeft,
              child: FutureBuilder<List<User>>(
                future: _foundContacts,
                builder: (ctx, snapshot) {
                  List<User>? contacts = snapshot.data;
                  switch (snapshot.connectionState) {
                    case ConnectionState.done:
                      if (contacts!.isNotEmpty)
                        return _buildListView(contacts);
                      else
                        return _buildNoDataView();
                    default:
                      return _buildLoadingScreen();
                  }
                },
              ),
            ),
          ],
        ),
      )),
    );
  }

  Widget _buildListView(List<User> contacts) {
    return Column(
      children: [
        Row(
          children: [
            Expanded(
              flex: 8,
              child: TextField(
                decoration: InputDecoration(
                  hintText: 'Search Here...',
                ),
                controller: _controller,
              ),
            ),
            Expanded(
                flex: 2,
                child: IconButton(
                    onPressed: () => filter(_controller.text),
                    icon: Icon(Icons.search)))
          ],
        ),
        Expanded(
          child: ListView.builder(
            itemBuilder: (ctx, idx) {
              return UserCard(contacts[idx]);
            },
            itemCount: contacts.length,
          ),
        ),
      ],
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
    return Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Icon(
            Icons.person_outline,
            size: 50,
          ),
          SizedBox(
            height: 10,
          ),
          Align(
            alignment: Alignment.center,
            child: Text(
              "No User",
              style: TextStyle(
                fontSize: 12,
              ),
            ),
          ),
        ]);
  }

  void filter(String search) async {
    List<User> results = await _contacts;

    if (search.isNotEmpty) {
      results = results
          .where((contact) =>
              contact.name.toLowerCase().contains(search.toLowerCase()))
          .toList();
    }

    setState(() {
      _foundContacts = Future.delayed(Duration(seconds: 0), () {
        return results;
      });
    });
  }
}
