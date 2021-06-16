package com.example.cha;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;

public class ForPassFragment extends Fragment {

    LoginCallBack loginCallBack;
    TextInputEditText un;
    Button submit;
    public void setLoginCallBack(LoginCallBack loginCallBack)
    {
        this.loginCallBack = loginCallBack;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_forpass,container,false);
        submit = v.findViewById(R.id.fp_but);
        un = v.findViewById(R.id.input_username);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubmit();
            }
        });
        return v;
    }

    public void onClickSubmit()
    {
        String sData = String.valueOf(un.getText());
        String sField = "check_user";
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Database database = new Database("http://192.168.247.153/hunt/fp.php",sField,sData);
                if(database.onStart())
                {
                    if(database.onComp())
                    {
                        String temp = database.getData();
                        if(temp.equals("true"))
                        {
                            if(loginCallBack!=null)
                            {
                                Toast.makeText(getContext(),"username Found",Toast.LENGTH_SHORT).show();
                                loginCallBack.setData(sData);
                                loginCallBack.callBacks("reset");
                            }
                        }
                        else
                        {
                            Toast.makeText(getContext(),temp,Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

}