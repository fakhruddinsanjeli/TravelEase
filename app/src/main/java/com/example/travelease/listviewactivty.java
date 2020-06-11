package com.example.travelease;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    private FloatingActionButton mFloatingActionButton;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listviewactivty);
        mBottomNavigationView=findViewById(R.id.bottomnavigation);
        backbutton=findViewById(R.id.backtomap);
        mFloatingActionButton=findViewById(R.id.floatbutton);
        fAuth=FirebaseAuth.getInstance();


        final ArrayAdapter<String> myarrayadapter= new ArrayAdapter<String>(listviewactivty.this,android.R.layout.simple_list_item_1,myarraylist);

        mListView=(ListView) findViewById(R.id.listview1);
        mListView.setAdapter(myarrayadapter);
        final String mUserId = fAuth.getUid();






        mref= FirebaseDatabase.getInstance().getReference("Current Location").child(mUserId);
        mref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    myarraylist.add(snapshot.getValue().toString());


                }
                myarrayadapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                myarrayadapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                myarrayadapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                myarrayadapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                myarrayadapter.notifyDataSetChanged();

            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(listviewactivty.this);
                builder.setCancelable(false);
                builder.setMessage("Are you Sure you want to Delete?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mref.removeValue();
                        myarrayadapter.notifyDataSetChanged();



                        //if user pressed "yes", then he is allowed to exit from application

                    }

                });
                builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if user select "No", just cancel this dialog and continue with app
                        dialog.cancel();
                    }
                });
                AlertDialog alert=builder.create();
                alert.show();

                return true;
            }
        });



        mBottomNavigationView.setSelectedItemId(R.id.favourites);


        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){

                    case R.id.plan:
                        startActivity(new Intent(listviewactivty.this,calendar.class));
                        finish();
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

                        return true;

                    case R.id.weather:
                        startActivity(new Intent(listviewactivty.this,WeatherController.class));
                        finish();
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

                        return true;

                    case R.id.home:
                        startActivity(new Intent(listviewactivty.this,MapActivity.class));
                        finish();
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

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



        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(listviewactivty.this,"Tap on the InfoWindow To Save", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 700);
                toast.show();
                startActivity(new Intent(listviewactivty.this,MapActivity.class));
                finish();

            }
        });


    }
}
