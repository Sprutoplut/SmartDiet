package com.example.smartdiet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yandex.mapkit.Animation;
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
import com.yandex.runtime.image.ImageProvider;
import android.Manifest;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Delivery_Food extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener {
    RecyclerViewAdapter adapter;
    public static List<Point> mappoints = new ArrayList<>();
    private static Boolean initialized = false;
    private LocationManager locationManager;
    private LocationListener myLocationListener;
    private Point myLocation;
    private PlacemarkMapObject Now_Geoposition;
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
    @SuppressLint({"NonConstantResourceId", "ServiceCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitInitializer(initialized);
        setContentView(R.layout.activity_delivery_food);
        Bundle arguments = getIntent().getExtras();
        mapView = findViewById(R.id.mapview);
        mapView.getMap().move(
                new CameraPosition(new Point(54.740312, 55.986193), 13.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);
        ActivityCompat.requestPermissions(Delivery_Food.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        ImageProvider imageprov = ImageProvider.fromResource(this, R.drawable.maps_shop);
        mappoints.add(new Point(54.738662, 55.992206));
        mappoints.add(new Point(54.742256, 55.987142));
        mappoints.add(new Point(54.731640, 55.976641));
        mappoints.add(new Point(54.743546, 55.997750));
        mappoints.add(new Point(54.750547, 55.993280));
        mappoints.add(new Point(54.753281, 55.989139));
        mappoints.add(new Point(54.757280, 55.994103));
        mappoints.add(new Point(54.734680, 55.993218));
        mappoints.add(new Point(54.726603, 55.962610));
        for(int i=0;i<9;i++)
        {
            PointAdd(mappoints.get(i),imageprov);
        }
        ImageView minus=findViewById(R.id.minus);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapView.getMap().move(
                        new CameraPosition(mapView.getMap().getCameraPosition().getTarget(), mapView.getMap().getCameraPosition().getZoom()-1.0f, 0.0f, 0.0f),
                        new Animation(Animation.Type.SMOOTH, 0),
                        null);
            }
        });
        ImageView plus=findViewById(R.id.plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapView.getMap().move(
                        new CameraPosition(mapView.getMap().getCameraPosition().getTarget(), mapView.getMap().getCameraPosition().getZoom()+1.0f, 0.0f, 0.0f),
                        new Animation(Animation.Type.SMOOTH, 0),
                        null);
            }
        });
        ArrayList<String> MagazineName = new ArrayList<>(Arrays.asList("Магнит 'Семейный'","ВкуссВилл","Пятёрочка","Магнит","Пятёрочка","Пятёрочка","Магнит","Магнит","Магнит"));
        ArrayList<String> MagazineName2 = new ArrayList<>(Arrays.asList("Комсомольская улица, 2к2","проспект Октября, 9","Революционная улица, 84","Комсомольская улица, 18/2","улица Братьев Кадомцевых, 8А","улица Рихарда Зорге, 21","улица Рихарда Зорге, 35","бульвар Хадии Давлетшиной, 24","улица Чернышевского, 125"));
        ArrayList<Integer> MyImage = new ArrayList<>(Arrays.asList(R.drawable.magnit,R.drawable.vkusvill,R.drawable.pyterochka,R.drawable.magnit,R.drawable.pyterochka,R.drawable.pyterochka,R.drawable.magnit,R.drawable.magnit,R.drawable.magnit));
        locationManager = MapKitFactory.getInstance().createLocationManager();
        Now_Geoposition = mapView.getMap().getMapObjects().addPlacemark(new Point(54.749667, 55.998604), ImageProvider.fromResource(this, R.drawable.me));
        myLocationListener=new LocationListener() {
            @Override
            public void onLocationUpdated(@NonNull Location location) {
                myLocation = location.getPosition();
                Now_Geoposition.setGeometry(new Point(myLocation.getLatitude(),myLocation.getLongitude()));
            }

            @Override
            public void onLocationStatusUpdated(@NonNull LocationStatus locationStatus) {

            }
        };
        locationManager.requestSingleUpdate(myLocationListener);
        RecyclerView recyclerView = findViewById(R.id.rvAnimals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, MagazineName, MyImage,MagazineName2);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        BottomNavigationView bottomNavigationView = findViewById(R.id.btm_nav_view);
        bottomNavigationView.getMenu().getItem(2).setChecked(true);
        bottomNavigationView.setOnItemSelectedListener(item -> {
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
        });

    }
    public void PointAdd(Point point,ImageProvider img)
    {
        mapView.getMap().getMapObjects().addPlacemark(point, img);
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
        locationManager.unsubscribe(myLocationListener);
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
            locationManager.subscribeForLocationUpdates(0, 10, 1, false, FilteringMode.OFF, myLocationListener);
        }
    }
}