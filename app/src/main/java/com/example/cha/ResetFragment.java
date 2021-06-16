package com.example.cha;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class ResetFragment extends Fragment {

    TextInputEditText pass, cPass;
    Button reset;
    String[] sField;
    String[] sData;
    String password;
    Bundle bundle;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reset, container, false);
        pass = v.findViewById(R.id.input_pass);
        cPass = v.findViewById(R.id.input_cPass);
        reset = v.findViewById(R.id.reset_but);
        sField = new String[2];
        sData = new String[2];
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickReset();
            }
        });
        return v;
    }

    public void onResume() {
        //setting sData
        bundle = getArguments();
        if (bundle != null) {
            sData[0] = bundle.getString("username");
            Log.i("onResume", sData[0]);
        }
        //setting sField
        sField[0] = "username";
        sField[1] = "password";

        super.onResume();
    }

    public void onClickReset() {
        sData[1] = String.valueOf(pass.getText());
        password = String.valueOf(cPass.getText());
        if (checkValid()) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Database database = new Database("http://192.168.247.153/hunt/fp.php", sField, sData);
                    if (database.onStart()) {
                        if (database.onComp()) {
                            String temp = database.getData();
                            if (temp.equals("success")) {
                                getParentFragmentManager().popBackStack();
                            }
                            Toast.makeText(getContext(), temp, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }

    public boolean checkValid() {
        boolean valid = false;
        if(sData[1].isEmpty() || password.isEmpty())
        {
            Toast.makeText(getContext(),"All fields are required",Toast.LENGTH_SHORT).show();
        }else if(!sData[1].equals(password))
        {
            Toast.makeText(getContext(),"password not matching",Toast.LENGTH_SHORT).show();
        }else if(password.length()>10)
        {
            Toast.makeText(getContext(),"password too long",Toast.LENGTH_SHORT).show();
        }else{valid = true;}
        return valid;
    }
}