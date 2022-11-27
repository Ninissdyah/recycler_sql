package com.example.recycler_sql;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {
    RecyclerView recycleview;
    Adapter adapter;
    private static DatabaseHelper DBHelper;
    List<Product> productz = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        recycleview = findViewById(R.id.recycleView);
        DBHelper = new DatabaseHelper(this);
        recycleview.setLayoutManager(new LinearLayoutManager(this));
        //memanggil method dari class databasehelper
        productz = DBHelper.getAllProducts();

        //nge set adapter
        adapter = new Adapter(this, R.layout.products, productz);
        recycleview.setAdapter(adapter);
    }
}