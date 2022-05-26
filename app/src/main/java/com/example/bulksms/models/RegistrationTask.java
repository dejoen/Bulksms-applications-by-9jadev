package com.example.bulksms.models;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bulksms.activities.HomeActivity;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegistrationTask {
    Context context;
    private final String TAG;
    private final String Name;
    private final String userName;
    private final String userPass;
    private final String confirmPass;
    private final String userEmail;
    private final String confirmUserEmail;
    private static final String url = "https://www.freesmsplan.com/register";
    private final ExecutorService executorService;
    private final Handler handler;
    private boolean isSucesfull = false;
    ProgressBar bar;
    Activity myActivity;
    Document document;

    public RegistrationTask(Context context, String name, String userName, String userPass, String confirmPass, String userEmail, String confirmUserEmail) {
        this.context = context;
        this.Name  = name;
        this.userName = userName;
        this.userPass = userPass;
        this.confirmPass = confirmPass;
        this.userEmail = userEmail;
        this.confirmUserEmail = confirmUserEmail;
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
                            //context.startActivity(new Intent(myActivity, HomeActivity.class));
                         //   myActivity.finish();
                            return;
                        }

                      //  bar.setVisibility(View.GONE);
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
                    .getElementById("member-registration");

            Element user = formElement.getElementById("jform_name");
            user.val(Name);
            Element usern = formElement.getElementById("jform_username");
            usern.val(userPass);
            Element pass=formElement.getElementById("jform_password1");
            pass.val(userPass);
            Element passCon=formElement.getElementById("jform_password2");
            passCon.val(confirmPass);
            Element email=formElement.getElementById("jform_email1");
            email.val(userEmail);
            Element conEmail=formElement.getElementById("jform_email2");
            conEmail.val(confirmUserEmail);
            Element capt=formElement.getElementById("jform_captcha");
            capt.getElementsByAttribute("data-sitekey").val("6Lfk-KoUAAAAAOBLJPxPp_QDCj2sPDOSawrVJqkx");
            capt.absUrl("https://www.google.com/recaptcha/api.js?onload=JoomlaInitReCaptcha2&amp;render=explicit&amp;hl=en-GB");

            Connection.Response login = formElement.submit()
                    .cookies(loginForm.cookies())
                    .execute();
            // String message=login.parse().select("div[class=breadcrumbs]").html().toString();
             Log.d(TAG, "doTask: success"+login.parse().html());
            String title = login.parse().title();


            //isSucesfull = title.equals("Affordable Bulk sms message");


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
