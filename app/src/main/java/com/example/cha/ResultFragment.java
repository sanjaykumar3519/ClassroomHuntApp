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
import androidx.fragment.app.Fragment;

public class ResultFragment extends Fragment {

    TextView status,approx;
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
        approx = v.findViewById(R.id.res_approx);
        imageView = v.findViewById(R.id.Bitmap_result);

        //assigning values
        Bundle bundle = getArguments();
        if(bundle!=null)
        {
            if(bundle.getString("students").equals("0") || bundle.getString("students").equals("none"))
            {
                status.setText(R.string.empty);
                imageView.setVisibility(View.INVISIBLE);
            }
            else
            {
                status.setText(R.string.taken);
            }
            approx.setText(bundle.getString("students","none"));
            String rString = bundle.getString("image");
            //decode bitmap
            byte[] decodeArray = Base64.decode(rString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodeArray,0,decodeArray.length);
            imageView.setImageBitmap(bitmap);
        }
        return v;
    }
}
