package com.example.bulksms.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bulksms.R;
import com.example.bulksms.models.RegistrationMmodel;
import com.example.bulksms.models.RegistrationTask;
import com.example.bulksms.webApi.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
private static final String TAG=RegistrationActivity.class.getSimpleName();
    private EditText  mName,mUsername,mPassword,mConPass,mEmail,mCoonEmail;
    private Button registerBut;

RegistrationTask registrationTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initializeViews();
    }
    private void  initializeViews(){

       mName=findViewById(R.id.Recname);
        mUsername=findViewById(R.id.Recusername);
        mPassword=findViewById(R.id.Recpassword);
        mConPass=findViewById(R.id.Recconpassword);
        mEmail=findViewById(R.id.editTextEmail);
        mCoonEmail=findViewById(R.id.editTextTextEmailAddress);
        registerBut=findViewById(R.id.RecsingInBut);
        registerBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }
    private void   registerUser(){
        String n=mName.getText().toString();
        String user=mUsername.getText().toString();
        String pass=mPassword.getText().toString();
        String conpass=mConPass.getText().toString();
        String email =mEmail.getText().toString();
        String conemail=mCoonEmail.getText().toString();
      registrationTask=new RegistrationTask(this,n,user,pass,conpass,email,conemail);
    }
}