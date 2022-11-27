package com.example.recycler_sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "clothes-db";
    private static final  int DATABASE_VERSION = 1;
    //The table
    private static final String PRODUCT_TABLE = "product";
    private static final String PRODUCT_ID = "id";
    private static final String PRODUCT_NAME = "name";
    private static final String PRODUCT_PRICE = "price";

//    constructor karena extends sqlite
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

//    untuk membuat tabel
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_PRODUCT = "CREATE TABLE " + PRODUCT_TABLE +
                "("+ PRODUCT_ID +" INTEGER PRIMARY KEY, "+
                PRODUCT_NAME +" TEXT, "+ PRODUCT_PRICE + " TEXT)";
        sqLiteDatabase.execSQL(CREATE_TABLE_PRODUCT);
    }

// untuk mengecek apakah tabel yang sudah di create sudah pernah dipakai,
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//        kalau sudah dipakai menjalankan hapus tabel, kalau belum create tabel
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ DATABASE_NAME);
        onCreate(sqLiteDatabase);
    }

//    insert product atau menambahkan product
    public void insertProduct(Product product){
        SQLiteDatabase db = getWritableDatabase();

        // data dimasukkan ke content values
        ContentValues values = new ContentValues();
        values.put(PRODUCT_NAME, product.getName());
        values.put(PRODUCT_PRICE, product.getPrice());

        //Insert data into DB
        db.insert(PRODUCT_TABLE,null, values);
        db.close();
    }

//    method untuk menampilkan semua data
    public List<Product> getAllProducts(){
        // semua product
        List<Product> allProduct = new ArrayList<>();
        // karena hanya menampilkan jadi pake readable
        SQLiteDatabase db = getReadableDatabase();
        // query untuk select from
        String selectQuery = "SELECT * from " + PRODUCT_TABLE;
        // krn menggunakan rawquery maka hrs menggunakan cursor
        Cursor cursor = db.rawQuery(selectQuery,null);

        // untuk mengambil semua data hingga data habis
        if (cursor.moveToFirst()){
            do {
                int ID = cursor.getInt(0);
                String ProductName = cursor.getString(1);
                int ProductPrice = cursor.getInt(2);
                allProduct.add(new Product(ID, ProductName, ProductPrice));
            } while (cursor.moveToNext());
        }
        // menghentikan proses pengambilan data
        db.close();
        // me return ke product yang sudah diambil agar tampil
        return allProduct;
    }

    // update product berdasarkan id
    public int updateProduct(Product product){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRODUCT_NAME, product.getName());
        values.put(PRODUCT_PRICE, product.getPrice());
        return db.update(PRODUCT_TABLE,values, PRODUCT_ID +"=?",
                new String[] {String.valueOf(product.getID())});
    }

    // menghapus data berdasarkan id
    public void deleteProduct(int ID){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(PRODUCT_TABLE, PRODUCT_ID +"=?", new String[]{String.valueOf(ID)});
        db.close();
    }
}
