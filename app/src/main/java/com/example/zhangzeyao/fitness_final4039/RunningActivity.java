package com.example.zhangzeyao.fitness_final4039;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.Toast;

import com.mapbox.mapboxsdk.MapboxAccountManager;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.Polyline;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapquest.android.commoncore.model.Location;
import com.mapquest.mapping.maps.MapView;
import com.mapquest.mapping.maps.MapboxMap;
import com.mapquest.mapping.maps.OnMapReadyCallback;

import java.util.List;
import java.util.ArrayList;

import com.mapbox.mapboxsdk.annotations.PolylineOptions;

import android.content.Intent;
import android.widget.Button;

public class RunningActivity extends AppCompatActivity {

    private MapboxMap mMapboxMap;
    private MapView mMapView;

    private ArrayList<LatLng> coordinates;

    private LocationManager locationManager;
    private LocationListener locationListener;
    private Chronometer chronometer;
    private Button stopButton;

    boolean notStop;

    private final LatLng CAULFIELD = new LatLng(-37.8768, 145.0458);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapboxAccountManager.start(getApplicationContext());
        setContentView(R.layout.activity_running);

        coordinates = new ArrayList<>();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        chronometer = (Chronometer)findViewById(R.id.chronometer2);
        chronometer.start();
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                String time = chronometer.getText().toString();
            }
        });

        stopButton = (Button)findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.stop();
                Intent newIntent = new Intent(RunningActivity.this,ExerciseSummaryActivity.class);
                newIntent.putExtra("Time",chronometer.getText().toString());
                Intent thisIntent = getIntent();
                String exercise = thisIntent.getStringExtra("exercise");
                newIntent.putExtra("TheExercise",exercise);
                startActivity(newIntent);

            }
        });



        notStop = true;

        mMapView = (MapView) findViewById(R.id.mapquestMapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final MapboxMap mapboxMap) {
                mMapboxMap = mapboxMap;
                mMapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CAULFIELD, 16));
                addMarker(mapboxMap);

                final PolylineOptions polyline = new PolylineOptions();
                polyline.addAll(coordinates);
                polyline.width(4);
                polyline.color(Color.BLUE);
                mapboxMap.addPolyline(polyline);

                locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(android.location.Location location) {
                        polyline.add(new LatLng(location));
                        mapboxMap.updatePolyline(polyline.getPolyline());
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                        Intent settingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    }
                };

                if (ActivityCompat.checkSelfPermission(RunningActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(RunningActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 2, 10, locationListener);

//                SensorManager mSensorManager;
//                Sensor mAccelerometer;
//
//
//                mSensorManager =
//                        (SensorManager)getSystemService(Context.SENSOR_SERVICE);
//                mAccelerometer =
//                        mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


            }});






    }


    private void addMarker(MapboxMap mapboxMap) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(CAULFIELD);
        markerOptions.title("San Francisco");
        markerOptions.snippet("Welcome to San Fran!");
        mapboxMap.addMarker(markerOptions);
    }

    @Override
    public void onResume()
    { super.onResume(); mMapView.onResume(); }

    @Override
    public void onPause()
    { super.onPause(); mMapView.onPause(); }

    @Override
    protected void onDestroy()
    { super.onDestroy(); mMapView.onDestroy(); }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    { super.onSaveInstanceState(outState); mMapView.onSaveInstanceState(outState); }
}
