package com.example.myshopper;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by M-K on 4.2.2017.
 */

public class Basket implements Observable{
    private List<Product> products;
    private final Set<Observer> users;

    private Basket () {
        this.products = new ArrayList<>();
        this.users = new HashSet<>();
    }

    public void insert (Product product) {
        Log.d("Basket", "Hello");
        products.add(product);
        notifyUsers(product);
    }

    @Override
    public void register(Observer observer) {
        users.add(observer);
    }

    @Override
    public void deregister(Observer observer) {
        users.remove(observer);
    }

    @Override
    public void notifyUsers(Product product) {
        for(Observer observer: this.users){
            observer.update(product);
        }
    }

    private static class BasketHolder {
        private static final Basket INSTANCE = new Basket();
    }

    public static Basket getInstance() {
        return BasketHolder.INSTANCE;
    }

}

