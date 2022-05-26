package com.example.bulksms.models;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bulksms.R;
import com.example.bulksms.activities.HomeActivity;
import com.example.bulksms.activities.RegistrationActivity;
import com.example.bulksms.fragments.SignInFragment;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Logintem {
    Context context;
    private final String TAG;
    private final String userName;
    private final String userPass;
    private static final String url = "https://www.freesmsplan.com";
    private final ExecutorService executorService;
    private final Handler handler;
    private boolean isSucesfull = false;
    ProgressBar bar;
    Activity myActivity;
    Document document;

    public Logintem(String userName, String userPass, Context context) {
        this.userName = userName;
        this.userPass = userPass;
        this.context = context;
        TAG = context.getClass().getSimpleName();
        executorService = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());
        asycTask();
    }

    private void asycTask() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                doTask();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (isSucesfull) {
                            int v = View.GONE;
                            context.startActivity(new Intent(myActivity, HomeActivity.class));
                            myActivity.finish();
                            return;
                        }

                        bar.setVisibility(View.GONE);
                        Toast.makeText(context, "invalid log in details", Toast.LENGTH_SHORT).show();


                    }
                });
            }
        });

    }

    public void getT(ProgressBar progressBar) {
        this.bar = progressBar;
    }

    public void getActivity(Activity activity) {
        this.myActivity = activity;
    }

    private void doTask() {
        try {
            Connection.Response loginForm = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .execute();
            FormElement formElement = (FormElement) loginForm.parse()
                    .getElementById("login-form");

            Element user = formElement.getElementById("modlgn-username");
            user.val(userName);
            Element Pass = formElement.getElementById("modlgn-passwd");
            Pass.val(userPass);
            Connection.Response login = formElement.submit()
                    .cookies(loginForm.cookies())
                    .execute();
            // String message=login.parse().select("div[class=breadcrumbs]").html().toString();
            // Log.d(TAG, "doTask: success"+login.parse().html());
            String title = login.parse().title();


            isSucesfull = title.equals("Affordable Bulk sms message");


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
