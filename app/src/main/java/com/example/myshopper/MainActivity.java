package com.example.myshopper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Observer{
    private List<String> codeList;
    private ListView listView;
    private List<Product> productList;
    private CustomAdapter adapter1;
    public Basket b;
    private EditText editCode;

    private Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.b = Basket.getInstance();
        b.register(this);

        productList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.ListView);
        adapter1 = new CustomAdapter(this, productList);//luodaan oma kustomoitu adapteri
        listView.setAdapter(adapter1);
        codeList = new ArrayList<>();

        editCode = (EditText)findViewById(R.id.editCode);
        ok = (Button)findViewById(R.id.okButton);

        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (editCode.getText() != null) {
                    String codeInput = editCode.getText().toString(); //ottaa koodin talteen
                    //editCode.getText().clear(); //tyhjentää kentän
                    Log.d("OnClick", codeInput);

                    if (codeList.contains(codeInput)) {
                        isOnList();
                        Log.d("List", "The list already has this item");
                        //tarkistetaan onko tuote jo listalla
                    } else {
                        Intent intent = new Intent();
                        intent.setClass(getBaseContext(), SpecsActivity.class);
                        editCode.getText().clear();
                        intent.putExtra("codeInput", codeInput);
                        startActivity(intent);
                    }
                    codeList.add(codeInput);

                }
                adapter1.notifyDataSetChanged(); //This needs to be moved to other place
            }
        });
    }

    public void onClickIncreaseCount (final View view) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int position = listView.getPositionForView(view);
                Product p = adapter1.getItem(position);
                p.addCount();
                adapter1.notifyDataSetChanged();
            }
        });
    }

    public void isOnList() { //Tämä avaa dialogi varoituksen, jos tuote on jo listalla
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.titleDialog);
        alert.setMessage(R.string.messageDialog);
        alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //This code will be executed when the user clicks "ok"
            }
        });
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        exitApp();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void update(final Product product) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("UI", product.name);
                productList.add(product); //Lisää objectin objecti listaan
                adapter1.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onBackPressed() {
        exitApp();
    }

    public void exitApp() {

        float sum = 0;
        for(int i = 0; i < productList.size(); i++) {
            sum += productList.get(i).count * productList.get(i).price;
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.titleDialogSave);
        alert.setMessage("The total count is: " + sum);
        alert.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                moveTaskToBack(true);
                Process.killProcess(Process.myPid());
                System.exit(1);
            }
        });
        alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alert.show();
    }
}
