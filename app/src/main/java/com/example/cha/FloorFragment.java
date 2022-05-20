package com.example.cha;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;


public class FloorFragment extends Fragment{
    FragmentCallBack fragmentCallBack;
    public void setFragmentCallBack(FragmentCallBack fragmentCallBack)
    {
        this.fragmentCallBack = fragmentCallBack;
    }
//testing
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_floor,container,false);

        fragmentCallBack.getTextView("floor");
        //Button hooks
        Button gf,ff,sf,tf;
        gf = v.findViewById(R.id.gf);
        ff = v.findViewById(R.id.ff);
        sf = v.findViewById(R.id.sf);
        tf = v.findViewById(R.id.tf);

        gf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentCallBack!=null)
                {
                    fragmentCallBack.setFloorRooms("gf");
                    fragmentCallBack.callNextFrag("rooms");
                }
            }
        });

        ff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentCallBack!=null)
                {
                    fragmentCallBack.setFloorRooms("ff");
                    fragmentCallBack.callNextFrag("rooms");
                }
            }
        });

        sf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentCallBack!=null)
                {
                    fragmentCallBack.setFloorRooms("sf");
                    fragmentCallBack.callNextFrag("rooms");
                }
            }
        });

        tf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentCallBack!=null)
                {
                    fragmentCallBack.setFloorRooms("tf");
                    fragmentCallBack.callNextFrag("rooms");
                }
            }
        });
        return v;
    }
}
