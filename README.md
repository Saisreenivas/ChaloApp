# ChaloApp
[![CircleCI](https://circleci.com/gh/Saisreenivas/ChaloApp.svg?style=svg)](https://circleci.com/gh/Saisreenivas/ChaloApp)
Chalo Android App

Develop a mobile application that will help the user search for a particular route and let them see the stop
details on a map with its route plotted, when you select that route. You should position the vehicle on the
map based on the response from the nextstop API call.
Additional functionality to set an alarm (notification) whenever a bus is going to arrive at a particular stop.

Screen 1 -
● Use metadata API to fetch route details. Show a loading state till the response is fetched.
● Search functionality to filter out a route
Screen 2 -
● Map view to display stops along with polyline
● Mark the polyline based on the stop’s lat long.
● Info window to see the stop name when you click a stop icon
● When the screen opens, the vehicle should appear at the first stop.
● Every 10 sec, use the nextstop API to position the vehicle icon at the stop based on the response of this API.
● Once it reaches the last stop, the journey ends!
● Users can also set the alarm to show a notification when the bus arrives at a particular stop. (Works only when
the screen is open). Dialog allows the user to select a stop from the dropdown menu.


Metadata API -
To fetch route metadata use below endpoint to fetch list of routes. Each route has route metadata and list
of stops with stop details.
Request:
GET
http://mock.yourwebsite.com:8080/metadata
Response:
Content-Type:application/json
[
 {
 "routeId": "23",
 "routeName": "Maharajbag to yasodhra",
 "stopDataList": [
 {
 "stopId": "1",
 "stopName": "Maharajbag",
 "sequence": 1,
 "latitute": 21.14387,
 "longitude": 79.07862
 },
 {
 "stopId": "2",
 "stopName": "Balbharti",
 "sequence": 2,
 "latitute": 21.14544,
 "longitude": 79.07436
 }
 ]
 }
]
Next Stop API
To fetch the id of next stop, use the below endpoint and pass route Id and current stop Id as path params.
The response contains the routeId and nextStopId.
If nextStopId is -1 either the route id is invalid or the stop id is invalid (not within the range of stops)
Request
GET
http://mock.yourwebsite.com:8080/routes/{routeId}/stops/{stopId}
Response
Content-Type:application/json
{
 "routeId": 23,
 "nextStopId": 2
}
