import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:location/location.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:tizito/models/meeting.dart';

class MeetingLocation extends StatefulWidget {
  final Meeting meeting;
  MeetingLocation({Key? key, required this.meeting}) : super(key: key);

  _MeetingLocationState createState() => _MeetingLocationState();
}

class _MeetingLocationState extends State<MeetingLocation> {
  late GoogleMapController mapController;
  List<Marker> markers = [];
  late Marker marker;
  Location location = Location();

  LatLng _initialcameraposition = LatLng(20.5937, 78.9629);

  @override
  void initState() {
    super.initState();
    _initialcameraposition = widget.meeting.location;
    markers.add(
      Marker(
        markerId: MarkerId(widget.meeting.location.toString()),
        position: widget.meeting.location,
        infoWindow: InfoWindow(
            title: '${widget.meeting.inviter.name} (${widget.meeting.idea})'),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        height: MediaQuery.of(context).size.height * 0.4,
        width: MediaQuery.of(context).size.width,
        child: Stack(
          children: [
            GoogleMap(
              initialCameraPosition:
                  CameraPosition(target: _initialcameraposition, zoom: 15),
              mapType: MapType.normal,
              myLocationEnabled: true,
              markers: Set.from(markers),
            ),
          ],
        ),
      ),
    );
  }
}
