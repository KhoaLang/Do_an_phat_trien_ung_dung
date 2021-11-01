package com.example.landview;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {
    Timer timer;
    @Override
    protected void onPostCreate( Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.layout_splash_screen);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        ShowStartDialog();
    }

    private void ShowStartDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setIcon(R.drawable.map_location);
        alertDialog.setTitle("Location Access Permission");
        alertDialog.setMessage("Access your location to server better!");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        Intent intent = new Intent(SplashScreen.this,LaucherActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },3000);
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SplashScreen.this,LaucherActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },3000);
            }
        });
        alertDialog.show();
    }
}
