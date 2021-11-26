package com.example.cha;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoadingFragment extends Fragment {
    FragmentCallBack fragmentCallBack;
    ImageView circle, inside;
    Animation scale, alpha, rotate, reverse;
    View LoadingView;
    String rNum;
    //result data
    String result = null;
    Bitmap resImg = null;
    boolean imgF = false;
    //asyncRes task;
    ExecutorService executorService;
    public void setFragmentCallBack(FragmentCallBack fragmentCallBack) {
        this.fragmentCallBack = fragmentCallBack;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (fragmentCallBack != null) {
            fragmentCallBack.hideActionBar(true);
        }
        //to run task background
        executorService = Executors.newSingleThreadExecutor();

        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        LoadingView = inflater.inflate(R.layout.fragment_loading, container, false);
        LoadingView.bringToFront();
        //load animation
        anim();

        Bundle bundle = getArguments();
        if (bundle != null) {
            rNum = bundle.getString("rNumber");
        }
        //setting server address1
        StringBuilder link = new StringBuilder();
        link.append("http://").append(LoginActivity.ip_data.getString("ip","none")).append("/hunt/Detection/python.php");
        //setting server address2
        StringBuilder link1 = new StringBuilder();
        link1.append("http://").append(LoginActivity.ip_data.getString("ip","none")).append("/hunt/Detection/000.jpeg");

        executorService.execute(new Runnable() {
            boolean flag = false;
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {

                if(detection(String.valueOf(link), String.valueOf(link1),rNum))
                {
                    if (fragmentCallBack != null) {
                        fragmentCallBack.setResultData(result, resImg, imgF);
                        flag = true;
                    }
                }
                Handler handler =new Handler(Looper.getMainLooper());
                handler.post(() -> {
                    if (fragmentCallBack != null) {
                        if (flag) {
                            fragmentCallBack.pop();
                            fragmentCallBack.callNextFrag("result");
                        } else {
                            fragmentCallBack.pop();
                            fragmentCallBack.hideActionBar(false);
                        }
                    }
                });
            }
        });
        return LoadingView;
    }

    public void anim() {
        //image hooks
        circle = LoadingView.findViewById(R.id.load_circle);
        inside = LoadingView.findViewById(R.id.load_inside);

        scale = AnimationUtils.loadAnimation(getContext(), R.anim.scale_anim);
        circle.setAnimation(scale);

        alpha = AnimationUtils.loadAnimation(getContext(), R.anim.alp);
        inside.setAnimation(alpha);

        rotate = AnimationUtils.loadAnimation(getContext(), R.anim.load_rotate);
        new Handler().postDelayed(() -> circle.setAnimation(rotate), 1000);

    }
    public void revAnim()
    {
        reverse = AnimationUtils.loadAnimation(getContext(),R.anim.load_rotate_reverse);
        circle.setAnimation(reverse);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean detection(String resUrl, String bitUrl,String roomNum) {
        try {
            URL url = new URL(resUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
            bufferedWriter.write(URLEncoder.encode("room", "UTF-8") + "=" + URLEncoder.encode(roomNum, "UTF-8"));
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            //to retrieve image from inputStream
            InputStream inputBitmap = new java.net.URL(bitUrl).openStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));
            result = bufferedReader.readLine();
            resImg = BitmapFactory.decodeStream(inputBitmap);
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
        } catch (IOException e) {
            imgF = true;
        }
        return true;
    }

}

