import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:location/location.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:tizito/api/meeting_provider.dart';
import 'package:tizito/models/meeting.dart';
import 'package:tizito/models/profile.dart';
import 'package:tizito/views/dasboard.dart';

class LocationPicker extends StatefulWidget {
  final MeetingPostModel newMeeting;
  final Profile profile;
  LocationPicker({Key? key, required this.newMeeting, required this.profile})
      : super(key: key);

  _LocationPickerState createState() => _LocationPickerState();
}

class _LocationPickerState extends State<LocationPicker> {
  late GoogleMapController mapController;
  List<Marker> markers = [];
  late Marker marker;
  Location location = Location();

  late GoogleMapController _controller;

  LatLng _initialcameraposition = LatLng(20.5937, 78.9629);

  @override
  void initState() {
    super.initState();
    getPosition();
  }

  getPosition() async {
    LocationData locData = await location.getLocation();
    _initialcameraposition =
        LatLng(locData.latitude ?? 20.5937, locData.longitude ?? 78.9629);
  }

  void _onMapCreated(GoogleMapController controller) {
    mapController = controller;
    location.onLocationChanged.listen((l) {
      _controller.animateCamera(
        CameraUpdate.newCameraPosition(
          CameraPosition(
              target: LatLng(l.latitude ?? 0.0, l.longitude ?? 0.0), zoom: 15),
        ),
      );
    });
  }

  addMarker(LatLng tappedPoint) {
    setState(() {
      markers = [];
      marker = Marker(
          markerId: MarkerId(tappedPoint.toString()),
          position: tappedPoint,
          draggable: true,
          icon: BitmapDescriptor.defaultMarkerWithHue(
              BitmapDescriptor.hueOrange));
      markers.add(marker);
    });
  }

  submitMeeting(BuildContext context) {
    MeetingPostModel newMeeting = widget.newMeeting;
    newMeeting.location = marker.position;
    print(newMeeting);

    // POST new Meeting
    MeetingProvider().postMeeting(newMeeting).then((value) {
      if (value["statusCode"] >= 200 && value["statusCode"] < 210)
        Navigator.push(
            context,
            MaterialPageRoute(
              builder: (context) => Dashboard(profile: widget.profile),
            ));
      else
        ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text('${value["data"]["message"]}')));
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        height: MediaQuery.of(context).size.height,
        width: MediaQuery.of(context).size.width,
        child: Stack(
          children: [
            GoogleMap(
              initialCameraPosition:
                  CameraPosition(target: _initialcameraposition),
              onMapCreated: _onMapCreated,
              mapType: MapType.normal,
              myLocationEnabled: true,
              markers: Set.from(markers),
              onTap: (tappedPoint) => addMarker(tappedPoint),
            ),
            Column(
              mainAxisAlignment: MainAxisAlignment.end,
              children: [
                Padding(
                  padding: EdgeInsets.all(10),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.start,
                    children: [
                      FloatingActionButton(
                        onPressed: () => submitMeeting(context),
                        child: Icon(Icons.save),
                      ),
                    ],
                  ),
                )
              ],
            )
          ],
        ),
      ),
    );
  }
}
