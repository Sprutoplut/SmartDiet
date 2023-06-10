package com.example.smartdiet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class About_App extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        Bundle arguments = getIntent().getExtras();
        BottomNavigationView bottomNavigationView = findViewById(R.id.btm_nav_view);
        bottomNavigationView.getMenu().getItem(4).setChecked(true);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.change_weight:
                        Intent intent = new Intent(About_App.this, Change_Weight.class);
                        intent.putExtra("log", arguments.get("log").toString());
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.calc_food:
                        Intent intent1 = new Intent(About_App.this, Calc_Food.class);
                        intent1.putExtra("log", arguments.get("log").toString());
                        startActivity(intent1);
                        finish();
                        return true;
                    case R.id.delivery_food:
                        Intent intent2 = new Intent(About_App.this, Delivery_Food.class);
                        intent2.putExtra("log", arguments.get("log").toString());
                        startActivity(intent2);
                        finish();
                        return true;
                    case R.id.person_app:
                        Intent intent4 = new Intent(About_App.this, Person_App.class);
                        intent4.putExtra("log", arguments.get("log").toString());
                        startActivity(intent4);
                        finish();
                        return true;
                }
                return false;
            }
        });
    }
}