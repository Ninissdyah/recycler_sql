package com.example.recycler_sql;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    Context context;
    private final List<Product> product;
    private static DatabaseHelper DBHelper;

    //constructor
    public Adapter(Context context,  int products, List<Product> product) {
        this.context = context;
        this.product = product;
        DBHelper = new DatabaseHelper(context);
    }

    // set view ke recycle view
    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.products, null);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        // get posisi dari produk
        final Product setView = product.get(position);
        // set value ke recycleview
        holder.namaBarang.setText(setView.getName());
        holder.hargaBarang.setText(String.valueOf(setView.getPrice()));

        //method untuk delete ketika btn delete diklik
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper.deleteProduct(setView.getID());

                //merefresh page
                ((Activity) context).finish();
                context.startActivity(((Activity) context).getIntent());
            }
        });

        // ketika item rv di klik akan muncul form edit
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editData(setView);
            }
        });
    }

    //me return size dari produk
    @Override
    public int getItemCount() {
        return product.size();
    }


    //assign dari xml
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView namaBarang, hargaBarang;
        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaBarang = (TextView) itemView.findViewById(R.id.namaBarang);
            hargaBarang = (TextView) itemView.findViewById(R.id.hargaBarang);
            btnDelete = (Button) itemView.findViewById(R.id.btn_delete);
        }
    }

    //method edit data
    private void editData(final Product product){
        //set view
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.edit, null);

        final EditText namaBarang = (EditText)view.findViewById(R.id.edit_nama_barang);
        final EditText hargaBarang = (EditText)view.findViewById(R.id.edit_harga_barang);

        //menampilkan data yang sebelumnya telah diinput
        if(product != null){
            namaBarang.setText(product.getName());
            hargaBarang.setText(String.valueOf(product.getPrice()));
        }

        //alert digunakan untuk membuat form edit
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Data Produk!");
        builder.setView(view);
        builder.create();


        builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final String productName = namaBarang.getText().toString();
                final Integer productPrice = Integer.valueOf(hargaBarang.getText().toString());

                if (TextUtils.isEmpty(productName)){
                    Toast.makeText(context, "Silahkan cek data nama barang dan harga barang yang telah dimasukkan!", Toast.LENGTH_SHORT).show();
                }else{
                    //memanggil fungsi updateProduct dari class DatabaseHelper
                    DBHelper.updateProduct(new Product(product.getID(), productName, productPrice));

                    Toast.makeText(context, "Data Berhasil Diupdate", Toast.LENGTH_SHORT).show();
//
                    //refresh page
                    ((Activity) context).finish();
                    context.startActivity(((Activity) context).getIntent());
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        });
        //menampilkan alert dialog
        builder.show();
    }
}
