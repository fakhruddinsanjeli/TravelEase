package com.example.travelease;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;

public class splashscreen extends AppCompatActivity {
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        fAuth=FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(fAuth.getCurrentUser()!=null){
                    startActivity(new Intent(getApplicationContext(),MapActivity.class));
                    finish();




                }
                else {
                    startActivity(new Intent(getApplicationContext(),Login.class));
                    finish();



                }



            }
        },1000);
    }
}
