package com.example.travelease;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

public class Login extends AppCompatActivity {
    EditText memail, mpassword;
    Button mloginbttn;
    TextView mcreatebtn;
    ProgressBar mProgressBar;
    FirebaseAuth fAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        memail=findViewById(R.id.email);
        mpassword=findViewById(R.id.password);
        mProgressBar=findViewById(R.id.progressBar2);
        fAuth=FirebaseAuth.getInstance();
        mloginbttn=findViewById(R.id.logbutton);
        mcreatebtn=findViewById(R.id.alreadyregistered);

        mloginbttn.setOnClickListener(new View.OnClickListener() {
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

                //authenticate the user

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){


                            Toast.makeText(Login.this,"Logged in Successfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                         else{

                            Toast.makeText(Login.this,"Error "+ task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            mProgressBar.setVisibility(View.GONE);
                        }

                    }
                });
            }
        });
        mcreatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });


    }
}
