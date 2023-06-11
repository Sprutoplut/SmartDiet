package com.example.smartdiet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.smartdiet.Models.Eat;
import com.example.smartdiet.Models.Weight;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.opencsv.CSVReader;

import org.apache.commons.lang3.ArrayUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Calc_Food extends AppCompatActivity {
    public static String curDate;
    public static String[] txtstr=new String[5];
    public static Integer[] tetstr=new Integer[5];
    public static Integer k=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_food);
        //Получение аргументов с прошлых layer
        Bundle arguments = getIntent().getExtras();
        //Формат даты
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");
        //Сегодняшняя дата
        Date date=new Date();
        curDate=formatForDateNow.format(date);
        //Подключение к таблице еда
        DatabaseReference eatbd= FirebaseDatabase.getInstance().getReference("Eat");
        String[] name=new String[391];
        String[] kkal=new String[391];
        InputStream inputStream=getResources().openRawResource(R.raw.table);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, Charset.forName("UTF-8"))
        );
        String line = "";
        try {
            reader.readLine();
            Integer i=0;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(";");
                if (i==0)
                    i++;
                else
                {
                    name[i-1]=tokens[0];
                    kkal[i-1]=tokens[4];
                    i++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        Button buttonPresent=findViewById(R.id.buttonPresent);
        buttonPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Calc_Food.this, PresentList.class);
                intent.putExtra("log", arguments.get("log").toString());
                startActivity(intent);
                finish();
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.btm_nav_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, name);
        AutoCompleteTextView txtcalc1=(AutoCompleteTextView)findViewById(R.id.txtcalc1);
        txtcalc1.setThreshold(1);
        txtcalc1.setAdapter(adapter);
        AutoCompleteTextView txtcalc2=(AutoCompleteTextView)findViewById(R.id.txtcalc2);
        txtcalc2.setThreshold(1);
        txtcalc2.setAdapter(adapter);
        AutoCompleteTextView txtcalc3=(AutoCompleteTextView)findViewById(R.id.txtcalc3);
        txtcalc3.setThreshold(1);
        txtcalc3.setAdapter(adapter);
        AutoCompleteTextView txtcalc4=(AutoCompleteTextView)findViewById(R.id.txtcalc4);
        txtcalc4.setThreshold(1);
        txtcalc4.setAdapter(adapter);
        AutoCompleteTextView txtcalc5=(AutoCompleteTextView)findViewById(R.id.txtcalc5);
        txtcalc5.setThreshold(1);
        txtcalc5.setAdapter(adapter);
        TextInputEditText tet1=findViewById(R.id.tet1);
        TextInputEditText tet2=findViewById(R.id.tet2);
        TextInputEditText tet3=findViewById(R.id.tet3);
        TextInputEditText tet4=findViewById(R.id.tet4);
        TextInputEditText tet5=findViewById(R.id.tet5);
        Double a=1.0;
        Button button=findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Proverka(txtcalc1,tet1);
                Proverka(txtcalc2,tet2);
                Proverka(txtcalc3,tet3);
                Proverka(txtcalc4,tet4);
                Proverka(txtcalc5,tet5);
                String itog="";
                Double Kal=0.0;
                for(int i=0;i<k;i++)
                {
                    itog=itog+";"+txtstr[i];
                    Double Kaled=Double.parseDouble(kkal[ArrayUtils.indexOf(name,txtstr[i])])*(tetstr[i]*a/100);
                    Kal=Kal+Kaled;
                }
                if(itog!="") {
                    Eat eat = new Eat(arguments.get("log").toString(), itog, Kal, curDate);
                    eatbd.push().setValue(eat);
                }
                Toast toast = Toast.makeText(getApplicationContext(),
                        "ОШИБКА!!! Шучу шучу, все добавлено", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });

        LinearLayout parentLayout = findViewById(R.id.parentlayout);
        Button buttonplus = findViewById(R.id.buttonplus);
        buttonplus.setOnClickListener(new View.OnClickListener() {
            private ScrollView scrollView2;

            @Override
            public void onClick(View v) {
                LinearLayout newLayout = new LinearLayout(Calc_Food.this);
                newLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT

                ));
                newLayout.setOrientation(LinearLayout.VERTICAL);

                TextInputLayout autoCompleteLayout = new TextInputLayout(Calc_Food.this);
                autoCompleteLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                AutoCompleteTextView autoCompleteTextView = new AutoCompleteTextView(Calc_Food.this);
                autoCompleteTextView.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                autoCompleteTextView.setHint("Название");
                autoCompleteLayout.addView(autoCompleteTextView);

                TextInputLayout editTextLayout = new TextInputLayout(Calc_Food.this);
                editTextLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                TextInputEditText editText = new TextInputEditText(Calc_Food.this);
                editText.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                editText.setHint("Вес");
                editTextLayout.addView(editText);

                newLayout.addView(autoCompleteLayout);
                newLayout.addView(editTextLayout);

                parentLayout.addView(newLayout);

                /*scrollView2.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView2.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });*/
            }
        });

        bottomNavigationView.getMenu().getItem(1).setChecked(true);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.change_weight:
                        Intent intent = new Intent(Calc_Food.this, Change_Weight.class);
                        intent.putExtra("log", arguments.get("log").toString());
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.delivery_food:
                        Intent intent2 = new Intent(Calc_Food.this, Delivery_Food.class);
                        intent2.putExtra("log", arguments.get("log").toString());
                        startActivity(intent2);
                        finish();
                        return true;
                    case R.id.about_app:
                        Intent intent3 = new Intent(Calc_Food.this, About_App.class);
                        intent3.putExtra("log", arguments.get("log").toString());
                        startActivity(intent3);
                        finish();
                        return true;
                    case R.id.person_app:
                        Intent intent4 = new Intent(Calc_Food.this, Person_App.class);
                        intent4.putExtra("log", arguments.get("log").toString());
                        startActivity(intent4);
                        finish();
                        return true;
                }
                return false;
            }
        });
    }
    public static void Proverka(AutoCompleteTextView str,TextInputEditText str1)
    {
        if(!TextUtils.isEmpty(str.getText().toString()) && !TextUtils.isEmpty(str1.getText().toString()))
        {
            txtstr[k]=str.getText().toString();
            tetstr[k]=Integer.parseInt(str1.getText().toString());
            k++;
        }
    }
}