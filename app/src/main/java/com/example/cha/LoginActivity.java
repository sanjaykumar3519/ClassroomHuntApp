package com.example.cha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements LoginCallBack{

    FragmentManager fragmentManager = getSupportFragmentManager();
    String userName;
    Bundle sData;
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initFrag();

    }

    private void initFrag()
    {
        LoginFragment loginFragment = new LoginFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.login_frame,loginFragment,null);
        loginFragment.setLoginCallBack(this);
        fragmentTransaction.commit();
    }

    @Override
    public void callBacks(String calls) {
        switch (calls)
        {
            case "intent":mainIntent();break;
            case "signup":signupFrag();break;
            case "forgot":forgotFrag();break;
            case "reset":resetFrag();break;
        }

    }

    @Override
    public void setData(String uName) {
        this.userName = uName;
        Log.i("setData",this.userName);
    }

    @Override
    public void setTitle(String cTitle) {
        Animation alpha;
        alpha = AnimationUtils.loadAnimation(this,R.anim.alp);
        title = findViewById(R.id.title_login);
        switch(cTitle)
        {
            case "login":title.setText(R.string.login);title.setAnimation(alpha);break;
            case "signUp":title.setText(R.string.signup);title.setAnimation(alpha);break;
            case "Forgot":title.setText(R.string.forgot);title.setAnimation(alpha);break;
            case "reset":title.setText(R.string.reset);title.setAnimation(alpha);break;
        }
    }

    @Override
    public void pop() {
        getSupportFragmentManager().popBackStack();
    }

    public void mainIntent()
    {
        SplashScreen.ld.edit().putBoolean("login",true).apply();
        startActivity(new Intent(this,mainActivity.class).putExtra("getUsername",this.userName));
        finish();
    }
    public void signupFrag()
    {
        SignupFragment signupFragment = new SignupFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right,0,R.anim.slide_in_left,0);
        fragmentTransaction.addToBackStack("LoginActivityStack");
        fragmentTransaction.replace(R.id.login_frame,signupFragment,null);
        signupFragment.setLoginCallBack(this);
        fragmentTransaction.commit();
    }
    public void forgotFrag()
    {
        ForPassFragment forPassFragment = new ForPassFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right,0,R.anim.slide_in_left,0);
        fragmentTransaction.addToBackStack("LoginActivityStack");
        fragmentTransaction.replace(R.id.login_frame,forPassFragment,null);
        forPassFragment.setLoginCallBack(this);
        fragmentTransaction.commit();
    }
    public void resetFrag()
    {
        fragmentManager.popBackStack();
        sData = new Bundle();
        sData.putString("username",this.userName);
        ResetFragment resetFragment = new ResetFragment();
        resetFragment.setArguments(sData);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right,0,R.anim.slide_in_left,0);
        fragmentTransaction.addToBackStack("LoginActivityStack");
        fragmentTransaction.replace(R.id.login_frame,resetFragment,null);
        resetFragment.setLoginCallBack(this);
        fragmentTransaction.commit();
    }

    public void onBackPressed()
    {
        if(fragmentManager.getBackStackEntryCount() > 0)
        {
            fragmentManager.popBackStack();
        }
        else
        {
            finish();
        }
    }
}