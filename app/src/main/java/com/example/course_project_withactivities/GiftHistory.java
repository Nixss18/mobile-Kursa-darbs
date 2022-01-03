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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class GiftHistory extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "userGiftChoice";
    private static final String KEY_NAME = "name";
    private static final String KEY_POINTS = "image";
    ArrayList<String> productList = new ArrayList<String>();
    String name;
    String points;
    ListView lw;
    String test; //sis nevajadzigs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_history);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        lw = (ListView) findViewById(R.id.listviewHistory);
        name = sharedPreferences.getString(KEY_NAME, null);
        points = sharedPreferences.getString(KEY_POINTS,null);
        productList.add(name);
        for(int i =0; i < productList.size();i++){
            Log.d("list", name);
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