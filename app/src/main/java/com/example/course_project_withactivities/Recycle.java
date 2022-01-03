package com.example.course_project_withactivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ErrorCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.zxing.Result;

import java.util.Random;

public class Recycle extends AppCompatActivity {
    private CodeScanner cScanner;
    private boolean qrScanned;
    private int CameraRequestCode = 69;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        //navigation view
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.recycle);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.recycle:
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
                        startActivity(new Intent(getApplicationContext(), GiftHistory.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        setupPermissions();
        codeScanner();
    }
    private void codeScanner(){
        CodeScannerView scanView = findViewById(R.id.scanner_view);
        cScanner = new CodeScanner(this, scanView);
        cScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(Recycle.this, result.getText(),Toast.LENGTH_SHORT).show();
                        qrScanned = true;
                        recycleBottles();
                    }
                });
            }
        });
        cScanner.setErrorCallback(new ErrorCallback() {
            @Override
            public void onError(@NonNull Exception error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Main","Camera init error: "+ error.getMessage());
                    }
                });
            }
        });
        scanView.setOnClickListener(new View.OnClickListener() {//katru reizi, kad velme noskenet velreiz, nepieciesams tap the screen
            @Override
            public void onClick(View v) {
                cScanner.startPreview();
            }
        });
    }
    public void recycleBottles(){
        int minBottles = 15;
        int maxBottles = 150;
        int recycledBottles = 0;
        //formula: int random = new Random().nextInt((max - min) + 1) + min;
        if(qrScanned){
            recycledBottles = new Random().nextInt((maxBottles - minBottles) + 1) + minBottles;
            Toast.makeText(this, "You recycled " + recycledBottles + " bottles!", Toast.LENGTH_SHORT).show();
            //peec shi butu japieskaita lietotaja kopejam pktu skaitam db
            CodeScannerView scanView = findViewById(R.id.scanner_view);
            scanView.setVisibility(View.GONE);
            qrScanned = false;
        }else{
            Toast.makeText(this, "Nothing",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        cScanner.startPreview();
    }

    @Override
    protected void onPause() {
        cScanner.releaseResources();
        super.onPause();
    }
    private void setupPermissions(){
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if(permission != PackageManager.PERMISSION_GRANTED){
            makeRequest();
        }
    }
    private void makeRequest(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},CameraRequestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CameraRequestCode) {
            if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera needs to be enabled!", Toast.LENGTH_SHORT).show();
            }else{
                //all good
            }
        }
    }
}