package com.example.landview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LauncherActivity extends AppCompatActivity {
    private Button buttonSignup,buttonLogin;
    private Animation.AnimationListener animationListener;
    private LinearLayout llContent;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.layout_launcher);
        //ánh xạ view
        buttonSignup = findViewById(R.id.btn_signup);
        buttonLogin = findViewById(R.id.btn_Login);
        llContent = findViewById(R.id.content);

        //animation
        initVariables();
        handleAnimation(R.anim.slideup_fadein);

        //sự kiện button
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignupActivity();
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoginActivity();
            }
        });
    }

    private void showLoginActivity() {
        Intent intent = new Intent(LauncherActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    private void showSignupActivity() {
        Intent intent = new Intent(LauncherActivity.this,SignUpActivity.class);
        startActivity(intent);
    }

    //HandleClickAnimationXML
    private void handleAnimation (int animId) {
        //load the animation
        final Animation animation = AnimationUtils.loadAnimation(LauncherActivity.this, animId);
        //set animation listener
        animation.setAnimationListener(animationListener);
        //Handle onclickListener to start animation
        llContent.startAnimation(animation);
    }

    private void initVariables() {
        animationListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}
        };
    }
}
