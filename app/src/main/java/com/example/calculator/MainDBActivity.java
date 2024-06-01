package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainDBActivity extends AppCompatActivity {
    private MyDatabaseManager dbManager;
    private RecyclerView recyclerView;
    private CalculationAdapter adapter;
    private List<Calculation> calculationList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dbactivity);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        calculationList = new ArrayList<>();
        adapter = new CalculationAdapter(calculationList);
        recyclerView.setAdapter(adapter);

        dbManager = new MyDatabaseManager(this);
        dbManager.open();

        loadCalculations();

        dbManager.close();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadCalculations() {
        Cursor cursor = dbManager.fetchAllCalculations();
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String expression = cursor.getString(cursor.getColumnIndex("expression"));
                @SuppressLint("Range") double result = cursor.getDouble(cursor.getColumnIndex("result"));
                calculationList.add(new Calculation(expression, result));
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    public void ReturnToCalculation(View v){
        Intent IManHinhChinh = new Intent(this, MainActivity.class);
        startActivity(IManHinhChinh);
    }
}