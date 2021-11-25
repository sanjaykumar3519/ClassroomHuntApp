package com.example.cha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.zip.Inflater;

public class LoginActivity extends AppCompatActivity implements LoginCallBack{

    FragmentManager fragmentManager = getSupportFragmentManager();
    String userName;
    Bundle sData;
    TextView title,welcome;
    //ip image
    static SharedPreferences ip_data;
    ImageView ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        welcome = findViewById(R.id.welcome);
        //ip address changer
        ip = findViewById(R.id.ip_menu);
        ip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPop(view);
            }
        });
        ip_data = getSharedPreferences("ip",MODE_PRIVATE);

        //fragment initialization
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
    //ip change menu inflate
    public void showPop(View v)
    {
        LayoutInflater layoutInflater = (LayoutInflater) v.getContext().getSystemService(v.getContext().LAYOUT_INFLATER_SERVICE);
        View popWin = layoutInflater.inflate(R.layout.ip_pop,null);
        int w = 700;
        int h = 600;
        PopupWindow popupWindow = new PopupWindow(popWin,w,h,true);
        popupWindow.showAtLocation(v,Gravity.CENTER,0,0);
        Button b = popWin.findViewById(R.id.ip_but);
        TextInputEditText ip_in = popWin.findViewById(R.id.ip_add);
        //onlcik
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = String.valueOf(ip_in.getText());
                if(!s.isEmpty())
                    ip_data.edit().putString("ip",s).apply();
                Toast.makeText(getApplicationContext(),"IP address changed",Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });
        //dismiss
        popWin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                popupWindow.dismiss();
                return true;
            }
        });
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