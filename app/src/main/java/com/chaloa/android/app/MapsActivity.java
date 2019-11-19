package com.chaloa.android.app;

import Model.MockData;
import Model.StopWiseData;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private static final String CHANNEL_ID = "a";
    private GoogleMap mMap;
    MockData mock;

    LatLng pos;
    MarkerOptions markerOptions;
    ArrayList<StopWiseData> stopWiseData;
//    ArrayList<StopWiseData> stopWiseData;
    FloatingActionButton fab;
    MarkerPosition markerPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Intent intent;
        if((intent = getIntent()) != null){
            String data = intent.getStringExtra("Mock");
            mock = (new Gson()).fromJson(data, MockData.class);
            mapFragment.getMapAsync(this);
        }

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);


    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        PolylineOptions polylineOptions = new PolylineOptions()
                .clickable(true);

//                .clickable(true)

//        for(int i=0;i<
//
//        LatLng =

//        polyline1..add();



        int length = 0;
        if(mock != null){
            stopWiseData = mock.getStopDataList();
            length = stopWiseData.size();
            for(int i=0;i<stopWiseData.size();i++){
                pos = new LatLng(
                        Double.parseDouble(String.valueOf(stopWiseData.get(i).getLatitude())),
                        Double.parseDouble(String.valueOf(stopWiseData.get(i).getLongitude())));
                polylineOptions.add(pos);
                markerOptions = new MarkerOptions()
                        .position(pos)
                        .title(stopWiseData.get(i).getStopName());
//                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_maps_24dp_img));
                mMap.addMarker(markerOptions);
//                mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            }
        }

//          LatLng a= new LatLng(-35.016, 143.321);
//        LatLng b= new LatLng(-34.364, 147.891);
//        LatLng c= new LatLng(-33.501, 150.217);
//        LatLng d= new LatLng(-32.306, 149.248);
//        LatLng e=new LatLng(-32.491, 147.309);
//        LatLng f= new LatLng(-34.747, 145.592);

//        polylineOptions.add(a,b,c,d,e,f);

        Polyline polyline1 = mMap.addPolyline(polylineOptions);
        polyline1.setVisible(true);
//        googleMap.setMinZoomPreference(6.0f);
        LatLngBounds totalRoute;
        Double verticalOne= Double.parseDouble(String.valueOf(mock.getStopDataList().get(0).getLongitude()));
        Double verticalTwo= Double.parseDouble(String.valueOf(mock.getStopDataList().get(length-1).getLongitude()));
        Double horizontalOne= Double.parseDouble(String.valueOf(mock.getStopDataList().get(0).getLatitude()));
        Double horizontalTwo = Double.parseDouble(String.valueOf(mock.getStopDataList().get(length-1).getLatitude()));
        Double left, right, top, bottom;
        if(verticalOne < verticalTwo){
            left = verticalOne;
            right = verticalTwo;
        }else{
            left = verticalTwo;
            right= verticalOne;
        }
        if(horizontalOne< horizontalTwo){
            top = horizontalOne;
            bottom = horizontalTwo;
        }else{
            top = horizontalTwo;
            bottom = horizontalOne;
        }

//        if(Double.parseDouble(String.valueOf(mock.getStopDataList().get(0).getLongitude())) <
//                Double.parseDouble(String.valueOf(mock.getStopDataList().get(length-1).getLongitude()))){
//
        totalRoute = new LatLngBounds(
                new LatLng(top, left),
                new LatLng(bottom, right)
        );
//            totalRoute = new LatLngBounds(
//                    new LatLng(Double.parseDouble(String.valueOf(mock.getStopDataList().get(0).getLatitude())),
//                            Double.parseDouble(String.valueOf(mock.getStopDataList().get(0).getLongitude()))),
//                    new LatLng(Double.parseDouble(String.valueOf(mock.getStopDataList().get(length-1).getLatitude())),
//                            Double.parseDouble(String.valueOf(mock.getStopDataList().get(length-1).getLongitude()))));

//        }else{
//            totalRoute = new LatLngBounds(
//                    new LatLng(Double.parseDouble(String.valueOf(mock.getStopDataList().get(length-1).getLatitude())),
//                            Double.parseDouble(String.valueOf(mock.getStopDataList().get(length-1).getLongitude()))),
//                    new LatLng(Double.parseDouble(String.valueOf(mock.getStopDataList().get(0).getLatitude())),
//                            Double.parseDouble(String.valueOf(mock.getStopDataList().get(0).getLongitude()))));
//        }


//        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(
//                Double.parseDouble(String.valueOf(mock.getStopDataList().get(0).getLatitude())),
//                Double.parseDouble(String.valueOf(mock.getStopDataList().get(0).getLongitude())))));
        mMap.setMinZoomPreference(15.0f);
        mMap.setLatLngBoundsForCameraTarget(totalRoute);


        busEnroute(mMap, mock);

    }

    private void busEnroute(GoogleMap mMap, final MockData mock) {
        if(stopWiseData.get(0) != null){
            markerPosition = new MarkerPosition(mock.getStopDataList(), mMap);
            markerPosition.setPos(0);
            markerPosition.addMarkerPosition();

            Timer repeatTask = new Timer();
            repeatTask.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {

                    int stopPos = markerPosition.getStopDataPos();
                    if(stopPos < mock.getStopDataList().size()){
                        int routeId = mock.getRouteId();
                        new NextLocation(getParent(), routeId, (stopPos+1), markerPosition).execute();
                    }else{

                    }

                    // Here do something
                    // This task will run every 10 sec repeat
                }
            }, 0, 10000);
        }

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.fab){

            final ArrayAdapter<String> adp = new ArrayAdapter<String>(MapsActivity.this,
                    R.layout.alarm_item, mock.getStopNamesList(mock.getStopDataList()));
            LinearLayout layout = new LinearLayout(MapsActivity.this);
            layout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
            Resources r = getApplicationContext().getResources();
            int px = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    16,
                    r.getDisplayMetrics()
            );
            lparams.setMargins(px,px,px,px);
            layout.setLayoutParams(lparams);
            layout.setPadding(px,px,px,px);

            TextView titleView = new TextView(this);
            LinearLayout.LayoutParams laparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            titleView.setLayoutParams(laparams);
            titleView.setText("Stop Name");
            final Spinner sp = new Spinner(MapsActivity.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT
                    , LinearLayout.LayoutParams.FILL_PARENT);

            sp.setLayoutParams(lp);

            sp.setAdapter(adp);

            layout.addView(titleView);
            layout.addView(sp);



            AlertDialog.Builder dialog = new AlertDialog.Builder(MapsActivity.this);
            dialog.setTitle("Set Alarm");
            dialog.setView(layout);
            dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
//                   normalNotification();

                    timedNotifications(sp);

                }
            });
            dialog.create().show();
        }
    }

    private void timedNotifications(Spinner sp) {
        AlarmManager alarmMgr;

        Intent notificationIntent = new Intent(getApplicationContext(), MyNewIntentService.class);
        notificationIntent
                .putExtra("heading", "Location Arrived")
                .putExtra("text", sp.getSelectedItem() + " is here")
//                            .putExtra("icon", R.drawable.ic_marker_maps_24dp_img)
        ;
        PendingIntent contentIntent = PendingIntent.getService(
                getApplicationContext(),
                0,
                notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        alarmMgr = (AlarmManager)getApplication().getSystemService(Context.ALARM_SERVICE);
//                    Intent intent1= new Intent(getApplicationContext(), MapsActivity.class);
        alarmMgr.cancel(contentIntent);

//                    alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
        int timing = 0;
        if(( sp.getSelectedItemPosition() - 1 - markerPosition.getStopDataPos())*10000 > 0 ){
            timing = (sp.getSelectedItemPosition() - 1 - markerPosition.getStopDataPos())*10000;
            Log.v("TIMING", timing + " " + markerPosition.getStopDataPos()+ " " + sp.getSelectedItemPosition());
            alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() +
                            timing
                    , contentIntent);
        }else{
            AlertDialog.Builder dialog = new AlertDialog.Builder(MapsActivity.this);
            dialog.setTitle("Alert");
            dialog.setMessage("Bus already crossed this stop");
            dialog.create().show();
        }


    }

    private void normalNotification() {
        AlarmManager alarmMgr;
        PendingIntent alarmIntent;


        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);

// Creating a pending intent and wrapping our intent
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext()
                , 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

//                    NotificationManager notificationManager = (NotificationManager)
//                            getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());

        mBuilder.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle("Notifications Title");
        mBuilder.setContentText("Your notification content here.");
        mBuilder.setSubText("Tap to view the website.");
        NotificationManager notificationManager;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(CHANNEL_ID);
            notificationManager.notify(1, mBuilder.build());
//                        startForeground(1, notification);
//                        Intent intento = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
//                        intento.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
//                        intento.putExtra(Settings.EXTRA_CHANNEL_ID, channel.getId());
//                        startActivity(intento);
        }else{
            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(1, mBuilder.build());

        }
    }

    private class MarkerPosition {
        int markerPos;
        int stopDataPos;
        LatLng pos;
        ArrayList<StopWiseData> stopWiseData;
        GoogleMap mMap;
        private Marker marker;
        MarkerOptions markerOptions;
        public MarkerPosition(ArrayList<StopWiseData> stopWiseData, GoogleMap mMap) {
            this.stopWiseData = stopWiseData;
            this.mMap = mMap;
        }




        public void addMarkerPosition() {

            markerOptions = new MarkerOptions()
                    .position(pos)
                    ;
            marker = mMap.addMarker(markerOptions);
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_directions_bus_black_24dp));
        }

        private LatLng getPos(int i) {
            return new LatLng(
                    Double.parseDouble(String.valueOf(mock.getStopDataList().get(i).getLatitude())),
                    Double.parseDouble(String.valueOf(mock.getStopDataList().get(i).getLongitude())));
        }

        public int getStopDataPos() {
            return stopDataPos;
        }

        public void setMarkerPosition() {
            Log.v("POSITION", marker.getPosition().latitude + " " + marker.getPosition().longitude + "");
            marker.setPosition(pos);
        }

        public void removeMarker(){
            marker.remove();

        }

        public void setPos(int i) {
            stopDataPos = i;
            pos = getPos(i);
        }
    }

    private class NextLocation extends AsyncTask<String, Void, String> {
        private final  Activity parent;
        private final int routeId;
        private final int stopPos;
        private final MarkerPosition markerPosition;
        public NextLocation(Activity parent, int routeId, int stopPos, MarkerPosition markerPosition) {
            this.parent = parent;
            this.routeId = routeId;
            this.stopPos = stopPos;
            this.markerPosition = markerPosition;
        }

        @Override
        protected String doInBackground(String... strings) {
            int outputStop=0;
            try {
                URL url = new URL("http://mock.chalo.com:8080/routes/"+routeId +"/stops/"+ stopPos);

            Log.v("MAPSACTIVITY","Hello");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000  /*milliseconds*/ );
            conn.setConnectTimeout(15000  /*milliseconds */);
            conn.setRequestMethod("GET");
            Log.v("MAIN", conn.getResponseCode() + " ");

            int responseCode=conn.getResponseCode();
            Log.v("RESPONSE CODE", responseCode + " ");

            final StringBuilder output = new StringBuilder("Request URL " + url);
            Log.v("OUTPUTURL", url+ "");
            output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
            output.append(System.getProperty("line.separator") + "Type "+ "GET");

            if(responseCode == HttpURLConnection.HTTP_OK){
                outputStop= convertAndReturn(conn.getInputStream(), markerPosition);
            }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return outputStop+ "";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            int stop  = Integer.parseInt(result);
            if(stop >0 && stop < mock.getStopDataList().size()) {
                markerPosition.setPos(stop-1);
                markerPosition.setMarkerPosition();
                Log.v("MARKER POSITION", (stop-1)+" ");
            }else if(stop == 0) {
                Toast.makeText(MapsActivity.this, "Socket/Network Timeout, please Refresh", Toast.LENGTH_LONG).show();
            }else{
                    Log.v("JOURNEY", "JOURNEY ENDED");
                    markerPosition.removeMarker();
                }
            }

        }


    private int convertAndReturn(InputStream inputStream, MarkerPosition markerPosition) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        StringBuilder sb = new StringBuilder();
        try {
            while (((line = br.readLine()) != null)) {

                sb.append(line + "\n");
            }

        } catch (IOException e) {
            Log.v("JSONERROR", e.getMessage());
        }



        line = sb.toString();
        int stop=0, route;
        try{
            JSONObject jsonObject = new JSONObject(line);
            route = jsonObject.getInt("routeId");
            stop = jsonObject.getInt("nextStopId");
        } catch (JSONException e) {
            Log.v("JSONMaps", e.getMessage() +"");
        }




        return stop;
    }
}
