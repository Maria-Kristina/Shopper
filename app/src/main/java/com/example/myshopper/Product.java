package com.example.myshopper;

/**
 * Created by M-K on 3.2.2017.
 */

public class Product {
    String name;
    String code;
    float price;
    int count;

    public Product (String name, String code, float price){
        this.name = name;
        this.code = code;
        this.price = price;
        this.count = 1;
    }

    public void addCount () {
        count++;
    }
}
