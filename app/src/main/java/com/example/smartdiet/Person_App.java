package com.example.smartdiet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;

public class Person_App extends AppCompatActivity {
    public static TextInputEditText weight,height,pol;
    public static ChipGroup obraz,mind;
    public static TextView status,kal,ugl,bel,jir,voo,imt;
    public static String statusdb;
    public static Double kaldb,ugldb,beldb,jirdb,voodb,imtdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_app);
        Bundle arguments = getIntent().getExtras();
        weight=findViewById(R.id.tiet_weight);
        height=findViewById(R.id.tiet_height);
        pol=findViewById(R.id.tiet_pol);
        obraz=findViewById(R.id.obraz);
        mind=findViewById(R.id.mind);
        status=findViewById(R.id.status);
        kal=findViewById(R.id.kal);
        ugl=findViewById(R.id.ugl);
        bel=findViewById(R.id.bel);
        jir=findViewById(R.id.jir);
        voo=findViewById(R.id.voo);
        imt=findViewById(R.id.imt);
        weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(!TextUtils.isEmpty(weight.getText().toString()) && !TextUtils.isEmpty(height.getText().toString()) && !TextUtils.isEmpty(pol.getText().toString()))
                {

                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.btm_nav_view);
        bottomNavigationView.getMenu().getItem(3).setChecked(true);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.change_weight:
                        Intent intent = new Intent(Person_App.this, Change_Weight.class);
                        intent.putExtra("log", arguments.get("log").toString());
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.calc_food:
                        Intent intent1 = new Intent(Person_App.this, Calc_Food.class);
                        intent1.putExtra("log", arguments.get("log").toString());
                        startActivity(intent1);
                        finish();
                        return true;
                    case R.id.delivery_food:
                        Intent intent2 = new Intent(Person_App.this, Delivery_Food.class);
                        intent2.putExtra("log", arguments.get("log").toString());
                        startActivity(intent2);
                        finish();
                        return true;
                    case R.id.about_app:
                        Intent intent3 = new Intent(Person_App.this, About_App.class);
                        intent3.putExtra("log", arguments.get("log").toString());
                        startActivity(intent3);
                        finish();
                        return true;
                }
                return false;
            }
        });
    }
    public void Calculate()
    {

    }
}