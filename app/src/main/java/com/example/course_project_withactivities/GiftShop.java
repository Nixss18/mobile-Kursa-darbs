package com.example.course_project_withactivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

    String productList[] = {"Apollo Kino tickets (2 tickets)", "Euronics gift card (20 EUR)", "Biļešu serviss gift card (20 EUR)", "220.lv gift card (20 EUR)", "Sportland gift card (25EUR)", "Virši DUS gift card (30 EUR)"};
    int[] productImage = {R.drawable.kino,R.drawable.euronics,R.drawable.bilesuserviss ,R.drawable.divsimtdivdesmit,R.drawable.sportland_,R.drawable.virsidavanukarte};
    String[] points = {"100", "200", "300", "300", "400", "500"};
    ListView listView;
    ArrayList productArray = new ArrayList<>();
    SharedPreferences sharedPreferences, userPointsAfterBuy;
    private static final String SHARED_PREF_NAME = "userGiftChoice";
    private static final String KEY_NAME = "name";
    private static final String KEY_POINTS = "points";
    private static final String KEY_IMAGE = "image";
    public static final String SAVED_POINTS = "savedPoints";
    public static final String SHARED_PREFS = "sharedPrefs";
    int userPoints;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_shop);
        listView = (ListView) findViewById(R.id.listview);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        userPointsAfterBuy = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        CustomBaseAdapter customBaseAdapter = new CustomBaseAdapter(getApplicationContext(), productList,productImage,points);
        listView.setAdapter(customBaseAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(GiftShop.this);
                builder1.setMessage("Do you want to order this product?");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Order",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPreferences preference = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                                userPoints = preference.getInt(SAVED_POINTS,0);
                                int getPoints = Integer.parseInt(points[position]);
                                if(getPoints > userPoints){
                                    Toast.makeText(GiftShop.this, "You don't have enough points. You have " + userPoints + " points", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    userPoints = userPoints - getPoints;
                                    Toast.makeText(GiftShop.this, "Your chosen product has been ordered", Toast.LENGTH_SHORT).show();
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(KEY_NAME, productList[position]);
                                    editor.putString(KEY_POINTS, points[position]);
                                    editor.putInt(KEY_IMAGE, productImage[position]);
                                    editor.apply();

                                    SharedPreferences.Editor editorPoints = userPointsAfterBuy.edit();
                                    editorPoints.putInt(SAVED_POINTS, userPoints);
                                    editorPoints.apply();
                                    Toast.makeText(GiftShop.this, "Your total points now: " + userPoints, Toast.LENGTH_SHORT).show();

                                }

                            }
                        });



                builder1.setNegativeButton(
                        "Cancel",
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