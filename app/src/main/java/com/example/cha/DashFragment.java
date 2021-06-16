package com.example.cha;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;


public class DashFragment extends Fragment {

    FragmentCallBack fragmentCallBack;
    public void setFragmentCallBack(FragmentCallBack fragmentCallBack)
    {
        this.fragmentCallBack = fragmentCallBack;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_dashboard,container,false);


        fragmentCallBack.getTextView("dash");


        Button check,profile,logout;
        //onclick check button
        check = v.findViewById(R.id.check);
        check.setOnClickListener(v12 -> {
            if(fragmentCallBack!=null)
            {
                fragmentCallBack.callNextFrag("floor");
            }
        });

        //onclick profile button
        profile = v.findViewById(R.id.profile);

        profile.setOnClickListener(v1 -> {
            if(fragmentCallBack!=null)
            {
                fragmentCallBack.callNextFrag("profile");
            }
        });

        //onclick logout button
        logout = v.findViewById(R.id.logout);

        logout.setOnClickListener(v13 -> {
            if(fragmentCallBack!=null)
            {
                fragmentCallBack.callNextFrag("logout");
            }
        });

        return v;
    }
}
