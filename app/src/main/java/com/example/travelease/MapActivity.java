package com.example.travelease;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;
import com.skyfishjy.library.RippleBackground;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ZoomControls;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.android.libraries.places.api.model.Place.*;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
GoogleMap.OnPoiClickListener, GoogleMap.OnInfoWindowClickListener {
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlacesClient PlacesClient;
    private List<AutocompletePrediction> PredictionList;

    private Location mLastknownlocation;
    private LocationCallback mLocationCallback;

    private MaterialSearchBar mMaterialSearchBar;
    private View mapview;
    private Button btnfind;
    private RippleBackground mRippleBackground;
    private Button btnfind2;
    private ImageButton weather;
    private RadioGroup mRadioGroup;
    private RadioButton mRadioButton;
    private RadioButton radiobutton2;
    private Button searchintersts;
    private RadioButton radiobutton3;
    private RadioButton radiobutton4;
    private RadioButton radiobutton5;
    private ImageButton favourite;
    private FloatingActionButton mFloatingActionButton;
    private RadioGroup maptype;
    private RadioButton normal;
    private RadioButton satellite;
    private RadioButton traffic;
    FirebaseAuth fAuth;







    private final float Default_zoom=18;
    private Object mGeoDataClient;
    private Object mPlaceDetectionClient;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mMaterialSearchBar=findViewById(R.id.searchBar);
        btnfind=findViewById(R.id.btn_find);
        weather=findViewById(R.id.weather);
        mRippleBackground=findViewById(R.id.ripple_bg);
        mRadioGroup=findViewById(R.id.searchgroup);
        mRadioButton=findViewById(R.id.restaurants);
        radiobutton2=findViewById(R.id.atm);
        radiobutton3=findViewById(R.id.mall);
        radiobutton5=findViewById(R.id.stations);
        radiobutton4=findViewById(R.id.touristattractions);
        favourite=findViewById(R.id.favourites);
        mFloatingActionButton=findViewById(R.id.choosemaytype);
        maptype=findViewById(R.id.selecttype);
        fAuth=FirebaseAuth.getInstance();



        searchintersts=findViewById(R.id.searchintersts);
        BottomNavigationView mBottomNavigationView=findViewById(R.id.bottomnavigation);


         Places.initialize(getApplicationContext(),"AIzaSyC15AL8JjDv1ClSklVa-rZEEKlNiWUN18o");




        SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapview=mapFragment.getView();
        mFusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(MapActivity.this);
        Places.initialize(MapActivity.this,"AIzaSyC15AL8JjDv1ClSklVa-rZEEKlNiWUN18o");
        PlacesClient= Places.createClient(this);
        final AutocompleteSessionToken token= AutocompleteSessionToken.newInstance();





        mMaterialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
              startSearch(text.toString(),true,null,true);
            }

            @Override
            public void onButtonClicked(int buttonCode) {
                if(buttonCode==MaterialSearchBar.BUTTON_NAVIGATION){
                    





                    //opening or closing a navigation drawer
                }


            }
        });
        mMaterialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                FindAutocompletePredictionsRequest predictionsRequest=FindAutocompletePredictionsRequest.builder()
                        .setSessionToken(token)
                        .setQuery(charSequence.toString())
                        .build();
                PlacesClient.findAutocompletePredictions(predictionsRequest).addOnCompleteListener(new OnCompleteListener<FindAutocompletePredictionsResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<FindAutocompletePredictionsResponse> task) {
                        if(task.isSuccessful()){
                            FindAutocompletePredictionsResponse predictionsResponse=task.getResult();
                            if(predictionsResponse!=null){

                                PredictionList = predictionsResponse.getAutocompletePredictions();
                                List<String> suggestionlist=new ArrayList<>();
                                for(int i=0;i<PredictionList.size();i++){
                                    AutocompletePrediction prediction= PredictionList.get(i);
                                    suggestionlist.add(prediction.getFullText(null).toString());

                                }
                                mMaterialSearchBar.updateLastSuggestions(suggestionlist);
                                if(mMaterialSearchBar.isSuggestionsVisible()){
                                    mMaterialSearchBar.showSuggestionsList();

                                }

                            }



                        }
                        else{


                            Log.i("mytag","Prediction fetching task unsuccessful");
                        }
                    }
                });


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mMaterialSearchBar.setSuggestionsClickListener(new SuggestionsAdapter.OnItemViewClickListener() {
            @Override
            public void OnItemClickListener(int position, View v) {
                if(position>=PredictionList.size()){
                    return;
                }
                AutocompletePrediction selectedpreiction=PredictionList.get(position);
                String suggetion= mMaterialSearchBar.getLastSuggestions().get(position).toString();
                mMaterialSearchBar.setText(suggetion);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mMaterialSearchBar.clearSuggestions();

                    }
                },1000);

                InputMethodManager imm= (InputMethodManager)  getSystemService(INPUT_METHOD_SERVICE);
                if(imm!=null){
                    imm.hideSoftInputFromWindow(mMaterialSearchBar.getWindowToken(),InputMethodManager.HIDE_IMPLICIT_ONLY);

                    String placeid=selectedpreiction.getPlaceId();
                    List<Field> placefields= Arrays.asList(Field.LAT_LNG, Field.NAME, Field.ADDRESS,Field.PHONE_NUMBER,Field.RATING);


                    FetchPlaceRequest fetchPlaceRequest= FetchPlaceRequest.builder(placeid,placefields).build();
                    PlacesClient.fetchPlace(fetchPlaceRequest).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                        @Override
                        public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {

                            final Place place=fetchPlaceResponse.getPlace();
                            Log.i("mytag","Place Found"+place.getName());
                            LatLng latLngofplace= place.getLatLng();
                            if(latLngofplace!=null){


                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngofplace,Default_zoom));
                                MarkerOptions mp = new MarkerOptions();

                                mp.position(new LatLng(latLngofplace.latitude,latLngofplace.longitude));
                                final String phoneno=place.getPhoneNumber();
                                final Double rating=place.getRating();



                                mp.title(place.getName());
                                mp.snippet("Tap to save in Favourites");




                                mp.draggable(true);


                                mp.icon(BitmapDescriptorFactory.fromResource(R.drawable.pinperfect));

                                mMap.addMarker(mp);
                                CustomInfoWindowAdapter adapter = new CustomInfoWindowAdapter(MapActivity.this);
                                mMap.setInfoWindowAdapter(adapter);
                               final String mUserId = fAuth.getUid();




                                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                    @Override
                                    public void onInfoWindowClick(Marker marker) {
                                        locationhelper helper=new locationhelper( place.getName());
                                        FirebaseDatabase.getInstance().getReference("Current Location").child(mUserId).push().setValue(helper).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                Toast  toast= Toast.makeText(MapActivity.this,"Saved In Favourites",Toast.LENGTH_SHORT);
                                                        toast.setGravity(Gravity.CENTER, 0, 700);
                                                        toast.show();



                                            }
                                        });

                                    }
                                });







                            }



                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if(e instanceof ApiException){


                                ApiException apiException=(ApiException)  e;
                                apiException.printStackTrace();
                                int statuscode=apiException.getStatusCode();
                                Log.i("mytag","Place Not Found" +e.getMessage());
                                Log.i("mytag","Status code"+ statuscode);

                            }

                        }
                    });

                }

            }

            @Override
            public void OnItemDeleteListener(int position, View v) {

            }
        });
        btnfind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                LatLng currentmarkerlocation = mMap.getCameraPosition().target;
                mRadioGroup.setVisibility(View.VISIBLE);

            }
        });


         favourite.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent myIntent = new Intent(MapActivity.this,listviewactivty.class);
                 finish();
                 startActivity(myIntent);


             }
         });






        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MapActivity.this,WeatherController.class);
                startActivity(myIntent);

            }
        });
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                maptype.setVisibility(view.VISIBLE);


            }
        });


        searchintersts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

              if(mRadioButton.isChecked()){
                  mRadioGroup.setVisibility(view.INVISIBLE);
                  mRippleBackground.startRippleAnimation();
                  new Handler().postDelayed(new Runnable() {
                      @Override
                      public void run() {

                          findrestaurants();
                          mRippleBackground.stopRippleAnimation();









                      }
                  },3000);





              }
              else if(radiobutton2.isChecked()){
                  mRadioGroup.setVisibility(view.INVISIBLE);
                  mRippleBackground.startRippleAnimation();
                  new Handler().postDelayed(new Runnable() {
                      @Override
                      public void run() {
                          findamusmentparks();


                          mRippleBackground.stopRippleAnimation();









                      }
                  },3000);






              }
              else if(radiobutton3.isChecked()){
                  mRadioGroup.setVisibility(view.INVISIBLE);
                  mRippleBackground.startRippleAnimation();
                  new Handler().postDelayed(new Runnable() {
                      @Override
                      public void run() {
                          findmall();



                          mRippleBackground.stopRippleAnimation();









                      }
                  },3000);




              }
              else if(radiobutton4.isChecked()){
                  mRadioGroup.setVisibility(view.INVISIBLE);
                  mRippleBackground.startRippleAnimation();
                  new Handler().postDelayed(new Runnable() {
                      @Override
                      public void run() {
                          findlocaltouristaatractions();


                          mRippleBackground.stopRippleAnimation();









                      }
                  },3000);





              }
              else if(radiobutton5.isChecked()){



                  mRadioGroup.setVisibility(view.INVISIBLE);
                  mRippleBackground.startRippleAnimation();
                  new Handler().postDelayed(new Runnable() {
                      @Override
                      public void run() {
                          findstations();


                          mRippleBackground.stopRippleAnimation();









                      }
                  },3000);
              }

            }
        });

        mBottomNavigationView.setSelectedItemId(R.id.home);


        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){


                    case R.id.plan:
                        startActivity(new Intent(MapActivity.this,calendar.class));
                        finish();
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

                        return true;


                    case R.id.weather:
                         startActivity(new Intent(MapActivity.this,WeatherController.class));
                         finish();
                         overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

                         return true;

                    case R.id.home:

                        return true;

                    case R.id.favourites:
                        startActivity(new Intent(MapActivity.this,listviewactivty.class));
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
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.normaltype:
                if (checked)
                    // Pirates are the best
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                mMap.setTrafficEnabled(false);
                maptype.setVisibility(view.INVISIBLE);
                    break;
            case R.id.satellite:
                if (checked)
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                maptype.setVisibility(view.INVISIBLE);
                    // Ninjas rule
                    break;

            case R.id.traffic:
                if (checked)
                    mMap.setTrafficEnabled(true);
                maptype.setVisibility(view.INVISIBLE);
                    // Ninjas rule
                    break;
        }
    }

    public  void  findrestaurantsbutton(){

        mRippleBackground.startRippleAnimation();
        findrestaurants();
        mRippleBackground.stopRippleAnimation();




    }
    public void findrestaurants(){

        StringBuilder stringBuilder= new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        stringBuilder.append("location="+mLastknownlocation.getLatitude()+","+mLastknownlocation.getLongitude());
        stringBuilder.append("&type="+"restaurant");
        stringBuilder.append("&radius="+3000);
        stringBuilder.append("&key="+"AIzaSyC15AL8JjDv1ClSklVa-rZEEKlNiWUN18o");

        String url=stringBuilder.toString();
        Object datatransfer[]= new Object[2];
        datatransfer[0]=mMap;
        datatransfer[1]=url;
        getnearbyplaces Getnearbyplaces= new getnearbyplaces();
        Getnearbyplaces.execute(datatransfer);






    }

    public void findamusmentparks(){
        StringBuilder stringBuilder= new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        stringBuilder.append("location="+mLastknownlocation.getLatitude()+","+mLastknownlocation.getLongitude());
        stringBuilder.append("&type="+"amusement_park");
        stringBuilder.append("&radius="+5000);
        stringBuilder.append("&key="+"AIzaSyC15AL8JjDv1ClSklVa-rZEEKlNiWUN18o");

        String url=stringBuilder.toString();
        Object datatransfer[]= new Object[2];
        datatransfer[0]=mMap;
        datatransfer[1]=url;
        getnearbyplaces Getnearbyplaces= new getnearbyplaces();
        Getnearbyplaces.execute(datatransfer);






    }

    public void findmall(){
        StringBuilder stringBuilder= new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        stringBuilder.append("location="+mLastknownlocation.getLatitude()+","+mLastknownlocation.getLongitude());
        stringBuilder.append("&type="+"shopping_mall");
        stringBuilder.append("&radius="+3000);
        stringBuilder.append("&key="+"AIzaSyC15AL8JjDv1ClSklVa-rZEEKlNiWUN18o");

        String url=stringBuilder.toString();
        Object datatransfer[]= new Object[2];
        datatransfer[0]=mMap;
        datatransfer[1]=url;
        getnearbyplaces Getnearbyplaces= new getnearbyplaces();
        Getnearbyplaces.execute(datatransfer);



    }

    public void findlocaltouristaatractions(){

        StringBuilder stringBuilder= new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        stringBuilder.append("location="+mLastknownlocation.getLatitude()+","+mLastknownlocation.getLongitude());
        stringBuilder.append("&type="+"tourist_attraction");
        stringBuilder.append("&radius="+3000);
        stringBuilder.append("&key="+"AIzaSyC15AL8JjDv1ClSklVa-rZEEKlNiWUN18o");

        String url=stringBuilder.toString();
        Object datatransfer[]= new Object[2];
        datatransfer[0]=mMap;
        datatransfer[1]=url;
        getnearbyplaces Getnearbyplaces= new getnearbyplaces();
        Getnearbyplaces.execute(datatransfer);




    }

    public void findstations(){
        StringBuilder stringBuilder= new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        stringBuilder.append("location="+mLastknownlocation.getLatitude()+","+mLastknownlocation.getLongitude());
        stringBuilder.append("&type="+"train_station");
        stringBuilder.append("&radius="+5000);
        stringBuilder.append("&key="+"AIzaSyC15AL8JjDv1ClSklVa-rZEEKlNiWUN18o");

        String url=stringBuilder.toString();
        Object datatransfer[]= new Object[2];
        datatransfer[0]=mMap;
        datatransfer[1]=url;
        getnearbyplaces Getnearbyplaces= new getnearbyplaces();
        Getnearbyplaces.execute(datatransfer);



    }





    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap=googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.setOnInfoWindowClickListener(this);
        mMap.getUiSettings().setZoomGesturesEnabled(true);



           //changing the position of my location button
        if(mapview!=null && mapview.findViewById(Integer.parseInt("1"))!= null){


            View locationbutton= ((View) mapview.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams=(RelativeLayout.LayoutParams)  locationbutton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP,0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
            layoutParams.setMargins(40,0,0,435);

        }

        if(mapview!=null && mapview.findViewById(Integer.parseInt("1"))!= null){
            View toolbar = ((View) mapview.findViewById(Integer.parseInt("1")).
                    getParent()).findViewById(Integer.parseInt("4"));

            // and next place it, for example, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) toolbar.getLayoutParams();
            // position on right bottom
            rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);

            rlp.setMargins(0, 0, 100, 435);
        }




        //check if gps is enabled or not and then request user to enable it
        LocationRequest locationRequest= LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);


        SettingsClient settingsClient= LocationServices.getSettingsClient(MapActivity.this);
        Task<LocationSettingsResponse> task=settingsClient.checkLocationSettings(builder.build());

        task.addOnSuccessListener(MapActivity.this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                getdevicelocation();

                googleMap.setOnPoiClickListener(MapActivity.this);
                mMap.setOnInfoWindowClickListener(MapActivity.this);


            }
        });


        task.addOnFailureListener(MapActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e instanceof ResolvableApiException){

                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    try {
                        resolvable.startResolutionForResult(MapActivity.this,51);
                    } catch (IntentSender.SendIntentException ex) {
                        ex.printStackTrace();
                    }

                }

            }
        });
       mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
           @Override
           public boolean onMyLocationButtonClick() {
               if(mMaterialSearchBar.isSuggestionsVisible())
                   mMaterialSearchBar.clearSuggestions();




               return false;
           }
       });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==51){

            if(resultCode==RESULT_OK){
                getdevicelocation();


            }

        }
    }
    private void getdevicelocation(){
      mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
          @Override
          public void onComplete(@NonNull Task<Location> task) {
              if(task.isSuccessful()){

                  mLastknownlocation= task.getResult();
                  if(mLastknownlocation!=null){
                      String title="this is title";
                      String subTitle="This is subtitle";



                      mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastknownlocation.getLatitude(),mLastknownlocation.getLongitude()),Default_zoom));
                      MarkerOptions mp = new MarkerOptions();

                      mp.position(new LatLng(mLastknownlocation.getLatitude(),mLastknownlocation.getLongitude()));


                      mp.draggable(true);


                     mp.icon(BitmapDescriptorFactory.fromResource(R.drawable.pinperfect));


                      mMap.addMarker(mp);
                      CustomInfoWindowAdapter adapter = new CustomInfoWindowAdapter(MapActivity.this);












                  }
                  else {

                      final LocationRequest locationRequest= LocationRequest.create();
                      locationRequest.setInterval(10000);
                      locationRequest.setFastestInterval(5000);
                      locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                      mLocationCallback= new LocationCallback(){


                          @Override
                          public void onLocationResult(LocationResult locationResult) {
                              super.onLocationResult(locationResult);
                              if(locationRequest == null){
                                  return;

                              }
                              mLastknownlocation= locationResult.getLastLocation();
                              mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastknownlocation.getLatitude(),mLastknownlocation.getLongitude()) ,Default_zoom));
                              mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
                          }
                      };

                      mFusedLocationProviderClient.requestLocationUpdates(locationRequest,mLocationCallback,null);

                  }

              }
              else {
                  Toast.makeText(MapActivity.this,"Unable to Get Last Location",Toast.LENGTH_SHORT).show();

              }



          }
      });


    }

    @Override
    public void onInfoWindowClick(Marker marker) {



    }


    @Override
    public void onPoiClick(PointOfInterest poi) {

        Toast toast = Toast.makeText(MapActivity.this,""+poi.name, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 700);
        toast.show();
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Are you Sure you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finish();
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
    }
}
