package com.example.myshopper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by M-K on 5.2.2017.
 */

public class CustomAdapter extends BaseAdapter{
    private Context mContext;
    private List<Product> productList;
    private LayoutInflater mInflater;
    private TextView nameView;
    private TextView codeView;
    private TextView priceView;
    private TextView countView;


    public CustomAdapter(Context context, List<Product> list) {
        this.mContext = context;
        this.productList = list;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void changePriceView (float price){
        priceView.setText(String.valueOf(price));
    }
    @Override
    public int getCount(){
        return productList.size();
    }

    @Override
    public Product getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        View rowView = mInflater.inflate(R.layout.custom_list_item_layout, parent, false);
        nameView = (TextView)rowView.findViewById(R.id.nameView);
        codeView = (TextView)rowView.findViewById(R.id.codeView);
        priceView = (TextView)rowView.findViewById(R.id.priceView);
        countView = (TextView)rowView.findViewById(R.id.countView);

        Product product = (Product)getItem(position);
        nameView.setText(product.name);
        codeView.setText(product.code);
        priceView.setText(String.valueOf(product.price));
        countView.setText(String.valueOf(product.count));

        return rowView;
    }
}
