package com.example.course_project_withactivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class GiftShop extends AppCompatActivity {

    String productList[] = {"Apollo Kino", "Euronics dāvanu karte", "bilesu serviss davanu karte"};
    int[] productImage = {R.drawable.kino,R.drawable.euronics,R.drawable.bilesuserviss};
    String[] points = {"3000", "5000", "5000"};
    ListView listView;
    ArrayList productArray = new ArrayList<>();
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "userGiftChoice";
    private static final String KEY_NAME = "name";
    private static final String KEY_POINTS = "points";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_shop);
        listView = (ListView) findViewById(R.id.listview);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        CustomBaseAdapter customBaseAdapter = new CustomBaseAdapter(getApplicationContext(), productList,productImage,points);
        listView.setAdapter(customBaseAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(GiftShop.this);
                builder1.setMessage("Vai vēlaties pasūtīt šo preci?");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Pasūtīt",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //te nāk pārbaudes
                                Toast.makeText(GiftShop.this, "Jūsu izvēlētā prece ir pasūtīta "+ position, Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(KEY_NAME, productList[position]);
                                editor.putString(KEY_POINTS, points[position]);
                                editor.apply();
                            }
                        });



                builder1.setNegativeButton(
                        "Atcelt",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = builder1.create();
                alert.show();
            }
        });

        //navigation view
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.gift);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.recycle:
                        startActivity(new Intent(getApplicationContext(), Recycle.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.gift:
                        return true;
                    case R.id.history:
                        startActivity(new Intent(getApplicationContext(), GiftHistory.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}