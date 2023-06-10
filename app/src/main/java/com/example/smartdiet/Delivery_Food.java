package com.example.smartdiet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Observable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.data.DataBufferObserver;
import com.google.android.gms.location.LocationRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.location.FilteringMode;
import com.yandex.mapkit.location.Location;
import com.yandex.mapkit.location.LocationListener;
import com.yandex.mapkit.location.LocationManager;
import com.yandex.mapkit.location.LocationStatus;
import com.yandex.mapkit.map.CameraPosition;

import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.runtime.image.ImageProvider;
import com.yandex.runtime.subscription.Subscription;

import android.Manifest;

import java.util.ArrayList;
import java.util.List;

import pl.charmas.android.reactivelocation2.ReactiveLocationProvider;


public class Delivery_Food extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener {
    RecyclerViewAdapter adapter;
    public static List<Point> mappoints = new ArrayList<>();
    private static Boolean initialized = false;
    private PlacemarkMapObject Now_Geoposition;
    private CoordinatorLayout rootCoordinatorLayout;
    private LocationManager locationManager;
    private LocationListener myLocationListener;
    private Point myLocation;
    private static final double DESIRED_ACCURACY = 0;
    private static final long MINIMAL_TIME = 1000;
    private static final double MINIMAL_DISTANCE = 1;
    private static final boolean USE_IN_BACKGROUND = false;
    public void MapKitInitializer(Boolean initialized1) {
        if (initialized1) {
            return;
        }
        MapKitFactory.setApiKey("46d7d8d3-2c4e-403d-8622-3d3d5354c9e6");
        MapKitFactory.initialize(this);
        initialized = true;
    }

    private MapView mapView;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitInitializer(initialized);
        setContentView(R.layout.activity_delivery_food);
        Bundle arguments = getIntent().getExtras();
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.getMap().move(
                new CameraPosition(new Point(54.740312, 55.986193), 13.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);
        ActivityCompat.requestPermissions(Delivery_Food.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        ImageProvider imageprov = ImageProvider.fromResource(this, R.drawable.me);
        Point mappoint = new Point(54.738662, 55.992206);
        mapView.getMap().getMapObjects().addPlacemark(mappoint, ImageProvider.fromResource(this, R.drawable.maps_shop));
        mappoints.add(mappoint);
        mappoint = new Point(54.742256, 55.987142);
        mapView.getMap().getMapObjects().addPlacemark(mappoint, ImageProvider.fromResource(this, R.drawable.maps_shop));
        mappoints.add(mappoint);
        mappoint = new Point(54.731640, 55.976641);
        mapView.getMap().getMapObjects().addPlacemark(mappoint, ImageProvider.fromResource(this, R.drawable.maps_shop));
        mappoints.add(mappoint);
        mappoint = new Point(54.743546, 55.997750);
        mapView.getMap().getMapObjects().addPlacemark(mappoint, ImageProvider.fromResource(this, R.drawable.maps_shop));
        mappoints.add(mappoint);
        mappoint = new Point(54.750547, 55.993280);
        mapView.getMap().getMapObjects().addPlacemark(mappoint, ImageProvider.fromResource(this, R.drawable.maps_shop));
        mappoints.add(mappoint);
        mappoint = new Point(54.753281, 55.989139);
        mapView.getMap().getMapObjects().addPlacemark(mappoint, ImageProvider.fromResource(this, R.drawable.maps_shop));
        mappoints.add(mappoint);
        mappoint = new Point(54.757280, 55.994103);
        mapView.getMap().getMapObjects().addPlacemark(mappoint, ImageProvider.fromResource(this, R.drawable.maps_shop));
        mappoints.add(mappoint);
        mappoint = new Point(54.734680, 55.993218);
        mapView.getMap().getMapObjects().addPlacemark(mappoint, ImageProvider.fromResource(this, R.drawable.maps_shop));
        mappoints.add(mappoint);
        mappoint = new Point(54.726603, 55.962610);
        mapView.getMap().getMapObjects().addPlacemark(mappoint, ImageProvider.fromResource(this, R.drawable.maps_shop));
        mappoints.add(mappoint);
        ArrayList<String> MagazineName = new ArrayList<>();
        MagazineName.add("Магнит 'Семейный'");
        MagazineName.add("ВкуссВилл");
        MagazineName.add("Пятёрочка");
        MagazineName.add("Магнит");
        MagazineName.add("Пятёрочка");
        MagazineName.add("Пятёрочка");
        MagazineName.add("Магнит");
        MagazineName.add("Магнит");
        MagazineName.add("Магнит");
        ArrayList<String> MagazineName2 = new ArrayList<>();
        MagazineName2.add("Комсомольская улица, 2к2");
        MagazineName2.add("проспект Октября, 9");
        MagazineName2.add("Революционная улица, 84");
        MagazineName2.add("Комсомольская улица, 18/2");
        MagazineName2.add("улица Братьев Кадомцевых, 8А");
        MagazineName2.add("улица Рихарда Зорге, 21");
        MagazineName2.add("улица Рихарда Зорге, 35");
        MagazineName2.add("бульвар Хадии Давлетшиной, 24");
        MagazineName2.add("улица Чернышевского, 125");
        ArrayList<Integer> MyImage = new ArrayList<>();
        MyImage.add(R.drawable.magnit);
        MyImage.add(R.drawable.vkusvill);
        MyImage.add(R.drawable.pyterochka);
        MyImage.add(R.drawable.magnit);
        MyImage.add(R.drawable.pyterochka);
        MyImage.add(R.drawable.pyterochka);
        MyImage.add(R.drawable.magnit);
        MyImage.add(R.drawable.magnit);
        MyImage.add(R.drawable.magnit);
        MapKit mapKit = MapKitFactory.getInstance();
        locationManager = MapKitFactory.getInstance().createLocationManager();
        myLocationListener = new LocationListener() {
            @Override
            public void onLocationUpdated(Location location) {
                if (myLocation == null) {
                    moveCamera(location.getPosition(), 15.0f);
                }
                myLocation = location.getPosition(); //this user point
            }

            @Override
            public void onLocationStatusUpdated(LocationStatus locationStatus) {
            }
        };
        RecyclerView recyclerView = findViewById(R.id.rvAnimals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, MagazineName, MyImage,MagazineName2);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        BottomNavigationView bottomNavigationView = findViewById(R.id.btm_nav_view);
        bottomNavigationView.getMenu().getItem(2).setChecked(true);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.change_weight:
                        Intent intent = new Intent(Delivery_Food.this, Change_Weight.class);
                        intent.putExtra("log", arguments.get("log").toString());
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.calc_food:
                        Intent intent1 = new Intent(Delivery_Food.this, Calc_Food.class);
                        intent1.putExtra("log", arguments.get("log").toString());
                        startActivity(intent1);
                        finish();
                        return true;
                    case R.id.about_app:
                        Intent intent3 = new Intent(Delivery_Food.this, About_App.class);
                        intent3.putExtra("log", arguments.get("log").toString());
                        startActivity(intent3);
                        finish();
                        return true;
                    case R.id.person_app:
                        Intent intent4 = new Intent(Delivery_Food.this, Person_App.class);
                        intent4.putExtra("log", arguments.get("log").toString());
                        startActivity(intent4);
                        finish();
                        return true;
                }
                return false;
            }
        });

    }
    @Override
    public void onItemClick(View view, int position) {
        mapView.getMap().move(
                new CameraPosition(mappoints.get(position), 15.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);
    }
    @Override
    protected void onStop() {
        // Вызов onStop нужно передавать инстансам MapView и MapKit.
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        // Вызов onStart нужно передавать инстансам MapView и MapKit.
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
        subscribeToLocationUpdate();
    }
    private void subscribeToLocationUpdate() {
        if (locationManager != null && myLocationListener != null) {
            locationManager.subscribeForLocationUpdates(DESIRED_ACCURACY, MINIMAL_TIME, MINIMAL_DISTANCE, USE_IN_BACKGROUND, FilteringMode.OFF, myLocationListener);
        }
    }
    private void moveCamera(Point point, float zoom) {
        mapView.getMap().move(
                new CameraPosition(point, zoom, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 1),
                null);
    }
}