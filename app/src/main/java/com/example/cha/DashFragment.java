package com.example.cha;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;


public class DashFragment extends Fragment {

    FragmentCallBack fragmentCallBack;
    ProgressBar progressBar;
    public void setFragmentCallBack(FragmentCallBack fragmentCallBack)
    {
        this.fragmentCallBack = fragmentCallBack;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_dashboard,container,false);

        progressBar = v.findViewById(R.id.progress);

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
            progressBar.setVisibility(View.VISIBLE);
            Bundle bundle = getArguments();
            if(bundle!=null)
            {
                if(getProfileData(bundle.getString("username")))
                {
                    if(fragmentCallBack!=null)
                    {
                        fragmentCallBack.callNextFrag("profile");
                    }
                }
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
    public boolean getProfileData(String name)
    {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                ProfileData profileData = new ProfileData(name);
                if(profileData.onStart())
                {
                    if(profileData.onComp())
                    {
                        if(fragmentCallBack!=null)
                        {
                            fragmentCallBack.setProfileData(profileData.readData.split(":"));
                        }
                    }
                }
            }
        });
        return true;
    }

}
