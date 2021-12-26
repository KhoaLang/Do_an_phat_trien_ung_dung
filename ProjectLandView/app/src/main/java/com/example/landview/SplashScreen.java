package com.example.landview;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {
    Timer timer;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_splash_screen);
        mAuth = FirebaseAuth.getInstance();
        ShowStartDialog();
    }

    private void ShowStartDialog() {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(SplashScreen.this, LauncherActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },3000);
            }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(SplashScreen.this, MainActivity.class));
        }
    }

}
