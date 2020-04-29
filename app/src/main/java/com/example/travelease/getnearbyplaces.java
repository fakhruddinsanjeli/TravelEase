package com.example.travelease;

import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

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

import androidx.annotation.NonNull;

public class getnearbyplaces extends AsyncTask<Object,String,String> {
    GoogleMap mMap;
    String url;
    InputStream is;
    BufferedReader mBufferedReader;
    StringBuilder mStringBuilder;
    String data;
    private final float Default_zoom=13;



    @Override
    protected String doInBackground(Object... params) {
        mMap=(GoogleMap)params[0];
        url=(String) params[1];

       try{ URL myurl= new URL (url);
           HttpURLConnection httpURLConnection=(HttpURLConnection)myurl.openConnection();
           httpURLConnection.connect();
           is=httpURLConnection.getInputStream();
           mBufferedReader=new BufferedReader(new InputStreamReader(is));

           String line="";
          mStringBuilder =new StringBuilder();
           while ((line=mBufferedReader.readLine())!=null){


               mStringBuilder.append(line);



           }
           data=mStringBuilder.toString();



       }catch (MalformedURLException e){

           e.printStackTrace();
       }catch (IOException e){
           e.printStackTrace();

       }

        return data;
    }

    @Override
    protected void onPostExecute(String s) {
        try{

            JSONObject parentobject= new JSONObject(s);
            JSONArray resultsaaray=parentobject.getJSONArray("results");


            for(int i=0;i<resultsaaray.length();i++){


                JSONObject jsonObject=resultsaaray.getJSONObject(i);
                JSONObject locationobject=jsonObject.getJSONObject("geometry").getJSONObject("location");


                String latitude=locationobject.getString("lat");
                String longitude=locationobject.getString("lng");




                JSONObject nameobj=resultsaaray.getJSONObject(i);
                final String name_resturants=nameobj.getString("name");
                final String vicinity=nameobj.getString("vicinity");



                LatLng newlatlng=new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
                final MarkerOptions markerOptions= new MarkerOptions();
                markerOptions.title(name_resturants);
                markerOptions.snippet("Tap to save in Favourites");
                markerOptions.position(newlatlng);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(newlatlng.latitude,newlatlng.longitude),Default_zoom));

                mMap.addMarker(markerOptions);
                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        locationhelper helper=new locationhelper( name_resturants);
                        FirebaseDatabase.getInstance().getReference("Current Location").push().setValue(helper).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {





                            }
                        });
                    }
                });








            }

        }catch (JSONException e){
            e.printStackTrace();

        }


    }
}
