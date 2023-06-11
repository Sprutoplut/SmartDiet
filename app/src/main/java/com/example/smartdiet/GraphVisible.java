package com.example.smartdiet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import com.example.smartdiet.Models.ArrayWeight;
import com.example.smartdiet.Models.Plan;
import com.example.smartdiet.Models.Weight;
import com.github.mikephil.charting.charts.LineChart;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GraphVisible extends AppCompatActivity {
    private LineChart chart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getIntent().getExtras();
        setContentView(R.layout.activity_graph_visible);
        chart=findViewById(R.id.chart);
        DatabaseReference weight= FirebaseDatabase.getInstance().getReference("Weight");
        Query query =  weight.orderByChild("log").equalTo(arguments.get("log").toString());
        weight.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Entry> arrayWeight=new ArrayList<>();
                Weight weight1;
                for(DataSnapshot singleSnapshot : snapshot.getChildren()){
                    weight1 = singleSnapshot.getValue(Weight.class);
                    String[] datestr=(weight1.date).split("\\.");
                    arrayWeight.add(new Entry(Integer.parseInt(datestr[0])+(Integer.parseInt(datestr[1])-1)*30,weight1.weight));
                }
                Collections.sort(arrayWeight, (o1, o2) -> (int) (o1.getX() - o2.getX()));
                LineDataSet setComp1 = new LineDataSet(arrayWeight,"Вес");
                setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
                List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                dataSets.add(setComp1);
                LineData data = new LineData(dataSets);
                chart.setData(data);
                chart.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}