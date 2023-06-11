package com.example.smartdiet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.smartdiet.Models.Users;
import com.example.smartdiet.Models.Weight;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FirebaseAuth auth=FirebaseAuth.getInstance();
        DatabaseReference users=FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference weightbd=FirebaseDatabase.getInstance().getReference("Weight");
        final TextInputEditText log=findViewById(R.id.tllog);
        final TextInputEditText pas1=findViewById(R.id.tlpas1);
        final TextInputEditText pas2=findViewById(R.id.tlpas2);
        final TextInputEditText weight=findViewById(R.id.tlweight);
        final TextInputEditText dateborn=findViewById(R.id.tldateborn);
        TextView error_reg=findViewById(R.id.error_reg);
        Button reg_but=findViewById(R.id.reg_but);
        reg_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(log.getText().toString()) || TextUtils.isEmpty(pas1.getText().toString()) || TextUtils.isEmpty(pas2.getText().toString()) || TextUtils.isEmpty(weight.getText().toString()) || TextUtils.isEmpty(dateborn.getText().toString()))
                {
                    error_reg.setText("Одно из полей не заполнено");
                }
                else if (!TextUtils.equals(pas1.getText().toString(),pas2.getText().toString()))
                {
                    error_reg.setText("Пароли не совпадают");
                }
                else if (pas1.getText().toString().length()<6)
                {
                    error_reg.setText("Пароль должен содержать более 6 символов");
                }
                else
                {
                    auth.createUserWithEmailAndPassword(log.getText().toString(),pas1.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Users user = new Users(log.getText().toString(),weight.getText().toString(),dateborn.getText().toString());
                                    users.push().setValue(user);
                                    Date date = new Date();
                                    SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");
                                    Weight weight1=new Weight(log.getText().toString(),Integer.parseInt(weight.getText().toString()),formatForDateNow.format(date));
                                    weightbd.push().setValue(weight1);
                                    Intent intent = new Intent(Register.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    error_reg.setText("Пользователь с таким именем уже существует");
                                }
                            });
                }
            }
        });

        Button backButton = findViewById(R.id.reg_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}