package com.example.smartdiet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import com.example.smartdiet.Models.Weight;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Change_Weight extends AppCompatActivity {
    public static String curDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_weight);
        Bundle arguments = getIntent().getExtras();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");
        Date date=new Date();
        curDate=formatForDateNow.format(date);
        CalendarView calendarView=findViewById(R.id.calendarView2);
        DatabaseReference weightbd= FirebaseDatabase.getInstance().getReference("Weight");
        BottomNavigationView bottomNavigationView = findViewById(R.id.btm_nav_view);
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.calc_food:
                        Intent intent1 = new Intent(Change_Weight.this, Calc_Food.class);
                        intent1.putExtra("log", arguments.get("log").toString());
                        startActivity(intent1);
                        finish();
                        return true;
                    case R.id.delivery_food:
                        Intent intent2 = new Intent(Change_Weight.this, Delivery_Food.class);
                        intent2.putExtra("log", arguments.get("log").toString());
                        startActivity(intent2);
                        finish();
                        return true;
                    case R.id.about_app:
                        Intent intent3 = new Intent(Change_Weight.this, About_App.class);
                        intent3.putExtra("log", arguments.get("log").toString());
                        startActivity(intent3);
                        finish();
                        return true;
                    case R.id.person_app:
                        Intent intent4 = new Intent(Change_Weight.this, Person_App.class);
                        intent4.putExtra("log", arguments.get("log").toString());
                        startActivity(intent4);
                        finish();
                        return true;
                }
                return false;
            }
        });
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String day=String.valueOf(dayOfMonth);
                String mon=String.valueOf(month);
                String yea=String.valueOf(year);
                if (day.length()==1)
                    day="0"+day;
                if (mon.length()==1)
                    mon="0"+mon;
                curDate=day+"."+mon+"."+yea;
            }
        });
        Button button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText txt1=findViewById(R.id.txt1);
                Weight weight1=new Weight(arguments.get("log").toString(),Integer.parseInt(txt1.getText().toString()),curDate);
                weightbd.push().setValue(weight1);
            }
        });
    }
}