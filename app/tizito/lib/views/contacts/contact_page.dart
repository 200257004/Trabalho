import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:tizito/api/contact_provider.dart';
import 'package:tizito/models/contact.dart';
import 'package:tizito/models/user.dart';
import 'package:tizito/views/contacts/contact_card.dart';
import 'package:tizito/views/users/user_page.dart';

class ContactsPage extends StatefulWidget {
  ContactsPage({Key? key}) : super(key: key);

  _ContactPageState createState() => _ContactPageState();
}

class _ContactPageState extends State<ContactsPage> {
  late Future<List<Contact>> _contacts;

  @override
  void initState() {
    super.initState();
    _contacts = _getContacts();
  }

  Future<List<Contact>> _getContacts() async {
    dynamic friendResponse = await ContactProvider().getFriends();

    if (friendResponse["statusCode"] == 200) {
      List<Contact> friends = [];
      if (friendResponse["data"].length > 0) {
        List<dynamic> fr = friendResponse["data"];
        friends = fr
            .map((e) => User.fromJson(e))
            .toList()
            .map((e) =>
                Contact(id: e.id, name: e.name, status: ContactStatus.ACCEPTED))
            .toList();
      }

      dynamic contactResponse = await ContactProvider().getContacts();
      if (contactResponse["statusCode"] == 200) {
        List<Contact> contacts = [];
        if (contactResponse["data"].length > 0) {
          List<dynamic> cr = contactResponse["data"].toList();
          contacts = cr.map((e) => Contact.fromJson(e)).toList();
        }

        return [...contacts, ...friends];
      }
      ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('${contactResponse["data"]["message"]}')));
    }
    ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('${friendResponse["data"]["message"]}')));
    return [];
  }

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: <Widget>[
        Align(
          alignment: Alignment.topLeft,
          child: FutureBuilder<List<Contact>>(
            future: _contacts,
            builder: (ctx, snapshot) {
              List<Contact>? contacts = snapshot.data;
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
        Padding(
          padding: EdgeInsets.all(8),
          child: Align(
            alignment: Alignment.bottomRight,
            child: FloatingActionButton(
              onPressed: selectUser,
              child: Icon(Icons.add),
            ),
          ),
        ),
      ],
    );
  }

  Widget _buildListView(List<Contact> contacts) {
    return ListView.builder(
      itemBuilder: (ctx, idx) {
        return ContactCard(
          contact: contacts[idx],
          callback: () => setState(() => {_contacts = _getContacts()})
        );
      },
      itemCount: contacts.length,
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
            size: 100,
          ),
          SizedBox(
            height: 10,
          ),
          Align(
            alignment: Alignment.center,
            child: Text(
              "No friends",
              style: TextStyle(
                fontSize: 12,
              ),
            ),
          ),
          SizedBox(
            height: 30,
          ),
          Align(
            alignment: Alignment.center,
            child: Text(
              "Add friends to schedule your meetings",
              style: TextStyle(
                fontSize: 12,
              ),
            ),
          ),
        ]);
  }

  void selectUser() async {
    await Navigator.push(
      context,
      MaterialPageRoute(builder: (context) => UserPage()),
    );
  }
}
