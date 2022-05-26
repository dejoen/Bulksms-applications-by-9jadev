package com.example.bulksms.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bulksms.R;
import com.example.bulksms.activities.HomeActivity;
import com.example.bulksms.activities.RegistrationActivity;
import com.example.bulksms.models.Logintem;
import com.example.bulksms.models.loginModel;
import com.example.bulksms.webApi.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInFragment extends Fragment {
    TextView signUp,forgetPass;
    Button signupBut;
    EditText userName,userPassword;
       Logintem logintem;
       ProgressBar progressBar;
boolean isLoginSuccessful;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignInFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignInFragment newInstance(String param1, String param2) {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_sign_in, container, false);
        signUp=v.findViewById(R.id.signup);
        forgetPass=v.findViewById(R.id.forgetPass);
        signupBut=v.findViewById(R.id.singInBut);
        userName=v.findViewById(R.id.username);
        userPassword=v.findViewById(R.id.password);
        progressBar=v.findViewById(R.id.progressB);
        progressBar.setVisibility(View.GONE);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(), RegistrationActivity.class));
            }
        });
signupBut.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
    progressBar.setVisibility(View.VISIBLE);
        login();
logintem.getT(progressBar);
logintem.getActivity(getActivity());

    }
});
        return v;
    }
    private  void login(){
        String name=userName.getText().toString();
        String pass=userPassword.getText().toString();
        if(TextUtils.isEmpty(name)){
            Toast.makeText(getContext(), "please enter name", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(getContext(), "please enter password ", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass) &(TextUtils.isEmpty(name))){
            Toast.makeText(getContext(), "please enter details  ", Toast.LENGTH_SHORT).show();
            return;
        }

        // new loginModel(name,pass);

   logintem=new Logintem(name,pass,getContext());


    }

    public ProgressBar getProgressBar() {

        return progressBar;
    }

    public  void isLoginSuccessful(boolean opt){
        isLoginSuccessful=opt;
    }
}