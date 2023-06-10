package com.example.smartdiet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.ref.Reference;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button input_btn=(Button) findViewById(R.id.bSign);
        TextView register_btn=(TextView) findViewById(R.id.tv_reg);
        FirebaseAuth auth=FirebaseAuth.getInstance();
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference users=db.getReference("Users");
        TextInputEditText log_text=findViewById(R.id.tLogin);
        TextInputEditText pas_text=findViewById(R.id.tPassword);
        TextView error_in=findViewById(R.id.tv_error);
        input_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                error_in.setText("");
                if (TextUtils.isEmpty(log_text.getText().toString()) || TextUtils.isEmpty(pas_text.getText().toString()))
                {
                    error_in.setText("Незаполнены обязательные поля");
                }
                else
                {
                    auth.signInWithEmailAndPassword(log_text.getText().toString(),pas_text.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Intent intent = new Intent(MainActivity.this, Change_Weight.class);
                            intent.putExtra("log", log_text.getText().toString());
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            error_in.setText("Ошибка авторизации. "+e.getMessage());
                        }
                    });
                }

            }
        });
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });
    }
}