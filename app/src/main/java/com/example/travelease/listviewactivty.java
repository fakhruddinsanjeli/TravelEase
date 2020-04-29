package com.example.travelease;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class listviewactivty extends AppCompatActivity {
    ListView mListView;
    ArrayList<String> myarraylist= new ArrayList<>();
    DatabaseReference mref;
    private BottomNavigationView mBottomNavigationView;
    private Button backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listviewactivty);
        mBottomNavigationView=findViewById(R.id.bottomnavigation);
        backbutton=findViewById(R.id.backtomap);


        final ArrayAdapter<String> myarrayadapter= new ArrayAdapter<String>(listviewactivty.this,android.R.layout.simple_list_item_1,myarraylist);

        mListView=(ListView) findViewById(R.id.listview1);
        mListView.setAdapter(myarrayadapter);


        mref= FirebaseDatabase.getInstance().getReference().child("Current Location");
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    myarraylist.add(snapshot.getValue().toString());


                }
                myarrayadapter.notifyDataSetChanged();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mBottomNavigationView.setSelectedItemId(R.id.favourites);


        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){

                    case R.id.weather:
                        startActivity(new Intent(listviewactivty.this,WeatherController.class));
                        finish();
                        overridePendingTransition(0,0);

                        return true;

                    case R.id.home:
                        startActivity(new Intent(listviewactivty.this,MapActivity.class));
                        finish();
                        overridePendingTransition(0,0);

                        return true;

                    case R.id.favourites:


                        return true;

                    case  R.id.logout:
                        //user can logout
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(),Login.class));
                        finish();

                        return true;






                }
                return false;
            }
        });


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(listviewactivty.this,MapActivity.class));
                finish();
            }
        });


    }
}
