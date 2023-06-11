package com.example.smartdiet;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;
import com.example.smartdiet.Models.Plan;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Person_App extends AppCompatActivity implements TextWatcher{
    public TextInputEditText weight,height;
    public ChipGroup obraz,mind,pol;
    public TextView status,kal,ugl,bel,jir,voo,imt,age;
    public String statusdb;
    public Double kaldb,ugldb,beldb,jirdb,voodb,imtdb;
    public Double doub=1.0;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_app);
        Bundle arguments = getIntent().getExtras();
        DatabaseReference plandb= FirebaseDatabase.getInstance().getReference("Plan");
        weight=findViewById(R.id.tiet_weight);
        height=findViewById(R.id.tiet_height);
        age=findViewById(R.id.tiet_age);
        pol=findViewById(R.id.pol);
        obraz=findViewById(R.id.obraz);
        mind=findViewById(R.id.mind);
        status=findViewById(R.id.status);
        kal=findViewById(R.id.kal);
        ugl=findViewById(R.id.ugl);
        bel=findViewById(R.id.bel);
        jir=findViewById(R.id.jir);
        voo=findViewById(R.id.voo);
        imt=findViewById(R.id.imt);
        weight.addTextChangedListener(this);
        height.addTextChangedListener(this);
        age.addTextChangedListener(this);
        Button button=findViewById(R.id.button2);
        button.setOnClickListener(v -> {
            if(!TextUtils.isEmpty(statusdb) && !TextUtils.isEmpty(kaldb.toString()) && !TextUtils.isEmpty(voodb.toString())&& !TextUtils.isEmpty(imtdb.toString())&& !TextUtils.isEmpty(beldb.toString())&& !TextUtils.isEmpty(jirdb.toString())&& !TextUtils.isEmpty(ugldb.toString()))
            {
                Plan plan = new Plan(arguments.get("log").toString(), kaldb, beldb, jirdb,ugldb,imtdb,voodb,statusdb);
                plandb.push().setValue(plan);
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.btm_nav_view);
        bottomNavigationView.getMenu().getItem(3).setChecked(true);
        bottomNavigationView.setOnItemSelectedListener(item -> {
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
        });
    }
    public void Calculate()
    {
        imtdb=Integer.parseInt(weight.getText().toString())/((Integer.parseInt(height.getText().toString())/100.0)*(Integer.parseInt(height.getText().toString())/100.0))*doub;
        if(pol.getCheckedChipId()==R.id.chip10)
            voodb=9.99*Integer.parseInt(weight.getText().toString())+6.25*Integer.parseInt(height.getText().toString())-4.92*Integer.parseInt(age.getText().toString())+5;
        else
            voodb=9.99*Integer.parseInt(weight.getText().toString())+6.25*Integer.parseInt(height.getText().toString())-4.92*Integer.parseInt(age.getText().toString())-161;
        if(imtdb<18.5)
            statusdb="Дефицит МТ";
        else if(imtdb<25)
            statusdb="Нормальная МТ";
        else if(imtdb<30)
            statusdb="Избыточная МТ";
        else if(imtdb<35)
            statusdb="Ожирение I степени";
        else if(imtdb<40)
            statusdb="Ожирение II степени";
        else
            statusdb="Ожирение III степени";
        Double k;
        if(obraz.getCheckedChipId()==R.id.chip)
            k=1.2;
        else if(obraz.getCheckedChipId()==R.id.chip2)
            k=1.375;
        else if(obraz.getCheckedChipId()==R.id.chip3)
            k=1.375;
        else if(obraz.getCheckedChipId()==R.id.chip4)
            k=1.375;
        else
            k=1.9;
        if (mind.getCheckedChipId()==R.id.chip6)
            kaldb=voodb*k*0.8;
        else if (mind.getCheckedChipId()==R.id.chip7)
            kaldb=voodb*k;
        else
            kaldb=voodb*k*1.2;
        beldb=kaldb*0.3/4;
        if(mind.getCheckedChipId()!=R.id.chip6) {
            jirdb = kaldb * 0.3 / 9;
            ugldb=kaldb*0.4/4;
        }
        else {
            jirdb = kaldb * 0.2 / 9;
            ugldb=kaldb*0.5/4;
        }
        imt.setText(String.format("%.1f",imtdb));
        voo.setText(String.format("%.1f",voodb));
        status.setText(statusdb);
        kal.setText(String.format("%.1f",kaldb));
        jir.setText(String.format("%.1f",jirdb));
        bel.setText(String.format("%.1f",beldb));
        ugl.setText(String.format("%.1f",ugldb));
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(!TextUtils.isEmpty(weight.getText().toString()) && !TextUtils.isEmpty(height.getText().toString()) && !TextUtils.isEmpty(age.getText().toString()))
        {
            Calculate();
        }
    }
}