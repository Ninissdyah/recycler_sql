package com.example.recycler_sql;

public class Product {
    private int ID;
    private String Name;
    private int Price;

//    tanpa id untuk menampilkan data dari recycle view
    public Product(String name, int price) {
        Name = name;
        Price = price;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

//    id digunakan untuk akses data di database helper
    public Product(int ID, String name, int price) {
        this.ID = ID;
        Name = name;
        Price = price;
    }
}
