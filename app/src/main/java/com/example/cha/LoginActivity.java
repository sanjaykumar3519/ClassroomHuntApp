package com.example.cha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.WindowManager;

public class LoginActivity extends AppCompatActivity implements LoginCallBack{

    FragmentManager fragmentManager = getSupportFragmentManager();
    String userName;
    Bundle sData;
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
        fragmentTransaction.addToBackStack("LoginActivityStack");
        fragmentTransaction.replace(R.id.login_frame,signupFragment,null);
        fragmentTransaction.commit();
    }
    public void forgotFrag()
    {
        ForPassFragment forPassFragment = new ForPassFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
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
        fragmentTransaction.addToBackStack("LoginActivityStack");
        fragmentTransaction.replace(R.id.login_frame,resetFragment,null);
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