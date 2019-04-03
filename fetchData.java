package com.example.rsivaganapathykumar.project;

import android.os.AsyncTask;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by R SIVAGANAPATHYKUMAR on 03-04-2019.
 */

public class fetchData extends AsyncTask<Void,Void,Void> {
    String data="";
    String singleData="";
    String combinedData="";
    @Override

    protected Void doInBackground(Void... voids) {
        try {
            URL url= new URL("https://raw.githubusercontent.com/iranjith4/radius-intern-mobile/master/users.json");
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            InputStream inputStream =httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            while(line!=null){
                line= bufferedReader.readLine();
                data=data+line;
            }
            JSONArray ja=new JSONObject(data).getJSONArray("results");
            for(int i=0;i<ja.length();i++)
            {
                try {
                    JSONObject jo=(JSONObject) ja.get(i);
                    String names="";
                    JSONObject name=(JSONObject) jo.get("name");
                    String title=(String) name.get("title");
                    String first=(String) name.get("first");
                    String last=(String) name.get("last");
                    names=title+" "+first+" "+last;

                    JSONObject dob=(JSONObject) jo.get("dob");
                    int age= dob.getInt("age");

                    JSONObject picture=(JSONObject) jo.get("picture");
                    String picPath=(String) picture.get("medium");

                    singleData="NAME : "+names+"\n"+
                                "AGE : "+age+"\n"+
                                "Photo :"+picPath+"\n";
                    combinedData+=singleData+"\n";
                    Log.e("t",combinedData);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        MainActivity.text.setText(this.combinedData);
        MainActivity.text.setMovementMethod(new ScrollingMovementMethod());
    }


}
