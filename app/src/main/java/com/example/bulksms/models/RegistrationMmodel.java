package com.example.bulksms.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegistrationMmodel {
    @SerializedName("Name")
    @Expose
    private String  Name;
    @SerializedName("Username")
    @Expose
    private String  Username;
    @SerializedName("Password")
    @Expose
    private String  Password;
    @SerializedName("Confirm Password")
    @Expose
    private String  ConfirmPassword;
    @SerializedName("Email Address")
    @Expose
    private String  EmailAddress;
    @SerializedName("Confirm Email Address")
    @Expose
    private String  ConfirmEmailAddress;

    public RegistrationMmodel(String name, String username, String password, String confirmPassword, String emailAddress, String confirmEmailAddress) {
        Name = name;
        Username = username;
        Password = password;
        ConfirmPassword = confirmPassword;
        EmailAddress = emailAddress;
        ConfirmEmailAddress = confirmEmailAddress;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        ConfirmPassword = confirmPassword;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getConfirmEmailAddress() {
        return ConfirmEmailAddress;
    }

    public void setConfirmEmailAddress(String confirmEmailAddress) {
        ConfirmEmailAddress = confirmEmailAddress;
    }
}
