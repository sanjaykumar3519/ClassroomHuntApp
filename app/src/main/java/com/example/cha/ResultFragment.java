package com.example.cha;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

public class ResultFragment extends Fragment {

    TextView status,rApprox,approx;
    ImageView imageView;
    FragmentCallBack fragmentCallBack;
    public void setFragmentCallBack(FragmentCallBack fragmentCallBack)
    {
        this.fragmentCallBack = fragmentCallBack;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        if(fragmentCallBack!=null)
        {
            fragmentCallBack.hideActionBar(false);
        }
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_result,container,false);

        //title
        fragmentCallBack.getTextView("result");

        //hooks
        status = v.findViewById(R.id.res_status);
        rApprox = v.findViewById(R.id.res_approx);
        approx = v.findViewById(R.id.approx);
        imageView = v.findViewById(R.id.Bitmap_result);

        //assigning values
        Bundle bundle = getArguments();
        if(bundle!=null)
        {
            if(!bundle.getString("students").equals("false"))
            {
                if(bundle.getString("students").equals("0") || bundle.getString("students").equals("none"))
                {
                    status.setText(R.string.empty);
                    imageView.setVisibility(View.INVISIBLE);
                }
                else if(Integer.parseInt(bundle.getString("students"))<5)
                {
                    status.setText(R.string.empty_bit);
                }else if(Integer.parseInt(bundle.getString("students"))<10)
                {
                    status.setText(R.string.filled_bit);
                }else
                {
                    status.setText(R.string.filled);
                }
                rApprox.setText(bundle.getString("students","none"));
                String rString = bundle.getString("image");
                //decode bitmap
                byte[] decodeArray = Base64.decode(rString,Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decodeArray,0,decodeArray.length);
                imageView.setImageBitmap(bitmap);
            }
            else
            {
                imageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.not_found,null));
                approx.setVisibility(View.GONE);
                rApprox.setVisibility(View.GONE);
                status.setText(getString(R.string.not_found));
            }
        }
        return v;
    }
}
