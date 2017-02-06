package com.example.myshopper;

/**
 * Created by M-K on 3.2.2017.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SpecsActivity extends AppCompatActivity {
    EditText editName;
    EditText editPrice;
    TextView viewCode;
    String codeInput;
    Basket b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specs);
        Bundle extras = getIntent().getExtras(); //User input from the main comes here
        codeInput = extras.getString("codeInput");
        this.b = Basket.getInstance();
        viewCode = (TextView)findViewById(R.id.viewCode);
        viewCode.setText(codeInput);
        editName = (EditText)findViewById(R.id.editName);
        editPrice = (EditText)findViewById(R.id.editPrice);
    }

    public void onClickCreateProduct (View view) {
        Product p = new Product(editName.getText().toString(), codeInput, Float.parseFloat(editPrice.getText().toString()));
        b.insert(p);
        Log.d("onClickCreateProduct", p.name);
        finish();
    }
}
