package com.example.smartdiet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smartdiet.Models.ArrayEat;
import com.example.smartdiet.Models.Eat;
import com.example.smartdiet.Models.Weight;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PresentList extends AppCompatActivity {
    RecyclerViewAdapterList adapter;
    Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getIntent().getExtras();
        ArrayList<String> eatl=new ArrayList<>();
        ArrayList<String> kall=new ArrayList<>();
        ArrayList<String> datel=new ArrayList<>();
        setContentView(R.layout.activity_present_list);
        DatabaseReference eat= FirebaseDatabase.getInstance().getReference("Eat");
        Query query =  eat.orderByChild("log").equalTo(arguments.get("log").toString());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Eat eat1;
                for(DataSnapshot singleSnapshot : snapshot.getChildren()){
                    eat1 = singleSnapshot.getValue(Eat.class);
                    eatl.add(eat1.EatList);
                    kall.add(String.format("%.1f",eat1.Kal));
                    datel.add(eat1.date);
                }
                RecyclerView recyclerView = findViewById(R.id.rvlist);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                adapter = new RecyclerViewAdapterList(context, eatl, kall,datel);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
