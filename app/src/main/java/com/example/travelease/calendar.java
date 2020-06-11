package com.example.travelease;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class calendar extends AppCompatActivity {
    CalendarView calendarview;
    TextView mydateview;
    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarview=findViewById(R.id.calendarview);
        mydateview=findViewById(R.id.mydate);
        mBottomNavigationView=findViewById(R.id.bottomnavigation);

        calendarview.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                String date=(i1 + 1)+"/"+i2+"/"+i;
                mydateview.setText(date);

            }
        });


        mBottomNavigationView.setSelectedItemId(R.id.plan);


        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){


                    case R.id.plan:


                        return true;


                    case R.id.weather:
                        startActivity(new Intent(calendar.this,WeatherController.class));
                        finish();
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

                        return true;

                    case R.id.home:
                        startActivity(new Intent(calendar.this,MapActivity.class));
                        finish();
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

                        return true;

                    case R.id.favourites:
                        startActivity(new Intent(calendar.this,listviewactivty.class));
                        finish();
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

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
    }


    public void AddCalendarEvent(View view) {
        Calendar calendarEvent = Calendar.getInstance();
        Intent i = new Intent(Intent.ACTION_EDIT);
        i.setType("vnd.android.cursor.item/event");
        i.putExtra("beginTime", calendarEvent.getTimeInMillis());
        i.putExtra("allDay", true);
        i.putExtra("rule", "FREQ=YEARLY");
        i.putExtra("endTime", calendarEvent.getTimeInMillis() + 60 * 60 * 1000);
        i.putExtra("title", "Calendar Event");
        startActivity(i);
    }
}
