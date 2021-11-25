package com.example.cha;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.google.android.material.textfield.TextInputEditText;

public class LoginFragment extends Fragment {

    Button login,signup,forgot;
    TextInputEditText username,password;
    String[] sField,sData;
    LoginCallBack loginCallBack;
    ProgressBar progressBar;
    public void setLoginCallBack(LoginCallBack loginCallBack)
    {
        this.loginCallBack = loginCallBack;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_login,container,false);
        //set Title
        if(loginCallBack!=null)
        {
            loginCallBack.setTitle("login");
        }

        login = v.findViewById(R.id.login);
        signup = v.findViewById(R.id.signup);
        forgot = v.findViewById(R.id.forgot);
        username = v.findViewById(R.id.un_input);
        password = v.findViewById(R.id.pass_input);
        //progress bar
        progressBar = v.findViewById(R.id.progress);

        login.setOnClickListener(v1 -> {
            //progress bar
            progressBar.setVisibility(View.VISIBLE);
            sField = new String[2];
            sData = new String[2];
            sField[0] = "username";
            sField[1] = "password";
            sData[0] = String.valueOf(username.getText());
            sData[1] = String.valueOf(password.getText());
            //setting server address
            StringBuilder link = new StringBuilder();
            link.append("http://").append(LoginActivity.ip_data.getString("ip","none")).append("/test.php");
            if(!sData[0].isEmpty() && !sData[1].isEmpty())
            {
                //login task
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {
                    Database database = new Database(String.valueOf(link), sField, sData);
                    if(database.onStart())
                    {
                        if(database.onComp())
                        {
                            String temp = database.getData();
                            if(temp.equals("Login Success"))
                            {
                                if(loginCallBack!=null)
                                {
                                    Toast.makeText(getContext(),temp,Toast.LENGTH_SHORT).show();
                                    loginCallBack.setData(sData[0]);
                                    loginCallBack.callBacks("main");
                                }
                            }
                            else
                            {
                                Toast.makeText(v1.getContext(),temp,Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    }
                });
            }
            else {
                Toast.makeText(getContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

        signup.setOnClickListener(v12 -> {
            if(loginCallBack!=null){
                loginCallBack.callBacks("signup");
            }
        });

        forgot.setOnClickListener(v13 -> {
            if(loginCallBack!=null){
                loginCallBack.callBacks("forgot");
            }
        });
        return v;
    }
}
