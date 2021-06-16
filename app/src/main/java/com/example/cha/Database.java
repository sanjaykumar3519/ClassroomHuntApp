package com.example.cha;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

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

public class Database extends Thread{
    private final String url;
    private String result;
    public String[] field,data;
    public String rData = null;
    public String rField = null;

    public Database(String url, String[] rField,String[] rData)
    {
        this.url = url;
        this.field = new String[rField.length];
        this.data = new String[rData.length];
        System.arraycopy(rField,0,this.field,0,this.field.length);
        System.arraycopy(rData,0,this.data,0,this.data.length);
    }
    //check username
    public Database(String url,String rField,String rData)
    {
        this.url = url;
        this.rField = rField;
        this.rData = rData;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void run()
    {
        String res = null;
       try{
           URL url = new URL(this.url);
           HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
           httpURLConnection.setRequestMethod("POST");
           httpURLConnection.setDoInput(true);
           httpURLConnection.setDoOutput(true);

           OutputStream outputStream = httpURLConnection.getOutputStream();
           BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
           StringBuilder stringBuilder = new StringBuilder();
           if(rData!=null)
           {
               stringBuilder.append(URLEncoder.encode(rField,"UTF-8")).append("=").append(URLEncoder.encode(this.rData,"UTF-8"));
           }
           else
           {
               for(int i=0;i<this.field.length;i++)
               {
                   stringBuilder.append(URLEncoder.encode(this.field[i], "UTF-8")).append("=").append(URLEncoder.encode(this.data[i],"UTF-8")).append("&");
               }
           }
           bufferedWriter.write(stringBuilder.toString());
           bufferedWriter.flush();
           bufferedWriter.close();
           outputStream.close();

           InputStream inputStream = httpURLConnection.getInputStream();
           BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,StandardCharsets.UTF_8));
           res = bufferedReader.readLine();
           bufferedReader.close();
           inputStream.close();
           httpURLConnection.disconnect();
           resData(res);
       }
       catch(IOException e){
                resData(e.toString());
       }
    }
    public void resData(String res)
    {
        this.result = res;
    }
    public String getData()
    {
        return this.result;
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
            if(!this.isAlive())
            {
                return true;
            }
        }
    }

}
