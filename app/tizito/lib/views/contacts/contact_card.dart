import 'package:flutter/material.dart';
import 'package:tizito/api/contact_provider.dart';
import 'package:tizito/models/contact.dart';

class ContactCard extends StatefulWidget {
  final Contact contact;
  final dynamic callback;

  ContactCard({Key? key, required this.contact, required this.callback})
      : super(key: key);

  _ContactCardState createState() => _ContactCardState();
}

class _ContactCardState extends State<ContactCard> {
  late Contact contact;

  @override
  initState() {
    super.initState();
    contact = widget.contact;
  }

  accept(BuildContext context) {
    // Accept friend

    ContactProvider()
        .answerContactRequest(contact, ContactStatus.ACCEPTED)
        .then((value) {
      if (value["statusCode"] >= 200 && value["statusCode"] < 210) {
        widget.callback();
        setState(() => {contact.status = ContactStatus.ACCEPTED});
      } else
        ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text('${value["data"]["message"]}')));
    });
  }

  reject(BuildContext context) {
    // Reject friend

    ContactProvider()
        .answerContactRequest(contact, ContactStatus.NO_ACCEPTED)
        .then((value) {
      if (value["statusCode"] >= 200 && value["statusCode"] < 210) {
        widget.callback();
        setState(() => {contact.status = ContactStatus.NO_ACCEPTED});
      } else
        ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text('${value["data"]["message"]}')));
    });
  }

  remove(BuildContext context) {
    // DELETE friend
    ContactProvider().deleteContact(contact).then((value) {
      if (value["statusCode"] >= 200 && value["statusCode"] < 210) {
        widget.callback();
        setState(() => {contact.status = ContactStatus.NO_ACCEPTED});
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
      child: Row(
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
                  '${contact.name}',
                  style: TextStyle(
                    fontSize: 20,
                    color: Colors.grey,
                  ),
                ),
              ],
            ),
          ),
          Row(
            mainAxisSize: MainAxisSize.min,
            children: getIcons(context, contact.status),
          ),
        ],
      ),
    );
  }

  List<Widget> getIcons(BuildContext context, ContactStatus status) {
    List<Widget> list = List.empty(growable: true);

    switch (status) {
      case ContactStatus.ACCEPTED:
        list.add(
          TextButton(
            onPressed: () => remove(context),
            child: Icon(
              Icons.remove_circle,
              color: Colors.grey[600],
            ),
          ),
        );
        break;
      default:
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
        break;
    }

    return list;
  }
}
