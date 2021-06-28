package com.example.cha;

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

public class ProfileData extends Thread{
    String readData = null;
    String username;
    public ProfileData(String username)
    {
        this.username = username;
    }
    public void run()
    {
        try {
            URL url = new URL("http://192.168.1.37/hunt/profile.php");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
            bufferedWriter.write(URLEncoder.encode("username","UTF-8") + "=" + URLEncoder.encode(username,"UTF-8"));
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));
            setData(bufferedReader.readLine());
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
        } catch (Exception e) {
            setData(e.toString());

        }
    }
    public void setData(String r)
    {
        this.readData = r;
    }
    public boolean onStart()
    {
        this.start();
        return true;
    }
    public boolean onComp()
    {
        while(true)
        {
            if(!isAlive())
            {
                return true;
            }
        }
    }


    }
