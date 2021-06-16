package com.example.cha;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Build;
import android.os.Bundle;

import android.os.Handler;

import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.ImageView;
import android.widget.TextView;



public class SplashScreen extends AppCompatActivity {

    TextView t_logo,t_slogan;
    Animation bot;
    static SharedPreferences ld;

    final int SPLASH = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //hide menu bar or action bar
        if(getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_splash);
        //animation_ini
        bot = AnimationUtils.loadAnimation(this,R.anim.bottom);

        //assigning animation to img,text
        t_logo = findViewById(R.id.main_text);
        t_slogan = findViewById(R.id.main_slogan);

        //set animation to the text and image
        t_logo.setAnimation(bot);
        t_slogan.setAnimation(bot);



        //sharedPref login or dashboard
        ld = getSharedPreferences("login",MODE_PRIVATE);


        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                if(!ld.getBoolean("login",false)) {
                    Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Intent i = new Intent(SplashScreen.this,mainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        },SPLASH);

        //logo animation

        Animation logo_l,logo_r,logo_c,rotate;
        ImageView left,right,center;

        logo_l = AnimationUtils.loadAnimation(SplashScreen.this,R.anim.logo_left);
        logo_r = AnimationUtils.loadAnimation(SplashScreen.this,R.anim.logo_right);
        logo_c = AnimationUtils.loadAnimation(SplashScreen.this,R.anim.logo_center);
        rotate = AnimationUtils.loadAnimation(SplashScreen.this,R.anim.img_rotate);

        left = findViewById(R.id.logo_left);
        right = findViewById(R.id.logo_right);
        center = findViewById(R.id.logo_center);

        left.setAnimation(logo_l);
        right.setAnimation(logo_r);
        center.setAnimation(logo_c);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                left.setAnimation(rotate);
                right.setAnimation(rotate);
            }
        },1000);

    }
}