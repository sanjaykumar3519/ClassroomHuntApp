package com.example.cha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProfileFragment extends Fragment{
    TextView profileName;
    FragmentCallBack fragmentCallBack;
    public void setFragmentCallBack(FragmentCallBack fragmentCallBack)
    {
        this.fragmentCallBack = fragmentCallBack;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle saveInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_profile,container,false);

        fragmentCallBack.getTextView("profile");

        profileName = v.findViewById(R.id.profile_name);
        Bundle bundle = getArguments();
        if(bundle!=null)
        {
            profileName.setText(bundle.getString("profileName"));
        }
        return v;
    }
}