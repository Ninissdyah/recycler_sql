package com.example.recycler_sql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText inputBarang, inputHarga;
    Button btnInsert, btnView;
    private static DatabaseHelper DBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper = new DatabaseHelper(this);
        inputBarang = findViewById(R.id.input_nama_barang);
        inputHarga = findViewById(R.id.input_harga_barang);
        btnInsert = findViewById(R.id.btn_insert);
        btnView = findViewById(R.id.btn_view);

        //ketika btn insert di klik memanggil method adddata
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddData();
            }
        });

        //perpindahkan ke act 2
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

    }

    //method adddata menambahkan data
    public void AddData() {
        //nge get inputan user
        String productName = inputBarang.getText().toString();
        int productPrice = Integer.valueOf(inputHarga.getText().toString());
        Product product = new Product(productName, productPrice);

        //memanggil method insertProduct dari DBHelper
        DBHelper.insertProduct(product);
        Toast.makeText(this, "Data telah berhasil ditambahkan!", Toast.LENGTH_SHORT).show();

        //mereset form
        inputHarga.setText("");
        inputBarang.setText("");
    }
}