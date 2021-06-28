package com.example.cha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProfileFragment extends Fragment {
    TextView profileName,profileEmail,profileDept;
    FragmentCallBack fragmentCallBack;
    String[] rData;
    Button delete;
    ProgressBar progressBar;
    public void setFragmentCallBack(FragmentCallBack fragmentCallBack) {
        this.fragmentCallBack = fragmentCallBack;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        fragmentCallBack.getTextView("profile");
        profileName = v.findViewById(R.id.name_data);
        profileEmail = v.findViewById(R.id.email_data);
        profileDept = v.findViewById(R.id.dept_data);
        delete = v.findViewById(R.id.drop);
        progressBar = v.findViewById(R.id.progress);
        Bundle bundle = getArguments();
        if (bundle != null) {
            rData = bundle.getStringArray("profileData");
            profileName.setText(rData[0]);
            profileEmail.setText(rData[1]);
            profileDept.setText(rData[2]);
        }
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        AccountDelete accountDelete = new AccountDelete(rData[0]);
                        if(accountDelete.onStart())
                        {
                            if(accountDelete.onComp())
                            {
                                if(accountDelete.result.equals("success"))
                                {
                                    if(fragmentCallBack!=null)
                                    {
                                        fragmentCallBack.callNextFrag("logout");
                                        Toast.makeText(requireContext(),"Account deleted",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    }
                });
            }
        });
        return v;
    }
}