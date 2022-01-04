package com.example.course_project_withactivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class GiftHistory extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "userGiftChoice";
    private static final String KEY_NAME = "name";
    private static final String KEY_POINTS = "points";
    private static final String KEY_IMAGE = "image";
    ArrayList<String> productList = new ArrayList<String>();
    String name;
    String points;
    int imgId;
    String test; //sis nevajadzigs
    TextView prName, prPaid;
    CircleImageView viewImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_history);
        prName = findViewById(R.id.name_product);
        prPaid = findViewById(R.id.paidForProduct);
        viewImage = findViewById(R.id.productImage);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        name = sharedPreferences.getString(KEY_NAME, null);
        points = sharedPreferences.getString(KEY_POINTS,null);
        imgId = sharedPreferences.getInt(KEY_IMAGE, -1);
        if(name !=null || points !=null || imgId !=-1){
            prName.setText(name);
            prPaid.setText(points);
            viewImage.setImageResource(imgId);
        }

        else{
            prName.setText("No Product ordered");
            prPaid.setText("No Product ordered");
            viewImage.setImageResource(R.drawable.gift);
        }

        //navigation view
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.history);
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
                        startActivity(new Intent(getApplicationContext(), GiftShop.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.history:
                        return true;
                }
                return false;
            }
        });
    }
}