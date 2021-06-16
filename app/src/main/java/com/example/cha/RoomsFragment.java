package com.example.cha;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.IOException;


public class RoomsFragment extends Fragment{

    Button r1, r2, r3;
    Bundle bundle;
    FragmentCallBack fragmentCallBack;
    public void setFragmentCallBack(FragmentCallBack fragmentCallBack)
    {
        this.fragmentCallBack = fragmentCallBack;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rooms, container, false);

        fragmentCallBack.getTextView("rooms");

        String[] roomArray = new String[3];
        //Room 1 button
        r1 = v.findViewById(R.id.g_one);
        r2 = v.findViewById(R.id.g_two);
        r3 = v.findViewById(R.id.g_three);
        //setting room numbers

        bundle = getArguments();
        if(bundle!=null)
        {
            System.arraycopy(bundle.getStringArray("roomNumbers"),0,roomArray,0,roomArray.length);
        }
        r1.setText(roomArray[0]);
        r2.setText(roomArray[1]);
        r3.setText(roomArray[2]);

        //
        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentCallBack!=null)
                {
                    fragmentCallBack.setRoom(String.valueOf(r1.getText()));
                    fragmentCallBack.callNextFrag("loading");
                }
            }
        });

        //Room 2 button
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentCallBack!=null)
                {
                    fragmentCallBack.setRoom(String.valueOf(r2.getText()));
                    fragmentCallBack.callNextFrag("loading");
                }
            }
        });

        //Room 3 button
        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentCallBack!=null)
                {
                    fragmentCallBack.setRoom(String.valueOf(r3.getText()));
                    fragmentCallBack.callNextFrag("loading");
                }
            }
        });
        return v;
    }
}

