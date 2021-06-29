package com.example.cha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements LoginCallBack{

    FragmentManager fragmentManager = getSupportFragmentManager();
    String userName;
    Bundle sData;
    TextView title,welcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        welcome = findViewById(R.id.welcome);
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
            case "main":mainIntent();break;
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

    @SuppressLint({"ResourceType", "UseCompatLoadingForDrawables"})
    @Override
    public void setTitle(String cTitle) {
        Animation alpha;
        alpha = AnimationUtils.loadAnimation(this,R.anim.alp);
        title = findViewById(R.id.title_login);
        StringBuilder t = new StringBuilder();
        switch(cTitle)
        {
            case "login": title.setText(t.append(getResources().getString(R.string.login)).append(" "));
                        title.setAnimation(alpha);
                        title.setBackground(getDrawable(R.drawable.login_bg));
                        welcome.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.blue_dark));
                        break;
            case "signUp":title.setText(t.append(getResources().getString(R.string.signup)).append(" "));
                            title.setAnimation(alpha);
                            title.setBackground(getDrawable(R.drawable.signup_bg));
                            welcome.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.yellow));
                            break;
            case "Forgot":title.setText(t.append(getResources().getString(R.string.forgot)).append(" "));
                            title.setAnimation(alpha);
                            title.setBackground(getDrawable(R.drawable.forgot_bg));
                             welcome.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.red_dark));
                break;
            case "reset":title.setText(t.append(getResources().getString(R.string.reset)).append(" "));
                        title.setAnimation(alpha);
                        title.setBackground(getDrawable(R.drawable.reset_bg));
                        welcome.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.green_dark));
                break;
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