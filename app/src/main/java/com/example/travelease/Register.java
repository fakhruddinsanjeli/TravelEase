package com.example.travelease;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    EditText mfullname,memail,mpassword,mphone;
    Button mregisterbutton;
    TextView mloginbutton;
    FirebaseAuth fauth;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mfullname=findViewById(R.id.fullname);
        memail=findViewById(R.id.email);
        mpassword=findViewById(R.id.password);
        mregisterbutton=findViewById(R.id.logbutton);
        mloginbutton=findViewById(R.id.alreadyregistered);

        fauth=FirebaseAuth.getInstance();
        mProgressBar=findViewById(R.id.progressBar);

        if(fauth.getCurrentUser()!= null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();



        }

        mregisterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email=memail.getText().toString().trim();
                String password=mpassword.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    memail.setError("Email  is Required");
                    return;

                }
                if(TextUtils.isEmpty(password)){
                    mpassword.setError("Passsword  is Required");
                    return;

                }

                if(password.length()<6){
                    mpassword.setError("Must be Greater than 6 Characters ");
                    return;

                }
                mProgressBar.setVisibility(View.VISIBLE);

                //register the user in firebase

                fauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(Register.this,"User created ",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));


                        }
                        else{
                            Toast.makeText(Register.this,"Error "+ task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            mProgressBar.setVisibility(View.GONE);




                        }
                    }
                });

            }
        });

        mloginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));

            }
        });

    }
}
