package com.example.bulksms.interfaces;


import com.example.bulksms.models.RegisterIdResponse;
import com.example.bulksms.models.RegistrationMmodel;
import com.example.bulksms.models.SendSmSResponse;
import com.example.bulksms.models.loginModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    public static final String BASE_URL = "https://app.smartsmssolutions.com/io/api/client/v1/";
	
	    @FormUrlEncoded
		    @POST("senderid/create/")
		    Call<RegisterIdResponse> registerId(@Field("token") String str, @Field("senderid") String str2, @Field("message") String str3, @Field("organisation") String str4, @Field("regno") String str5, @Field("address") String str6);
	
		    @FormUrlEncoded
		    @POST("sms/")
    Call<SendSmSResponse> sendSmSResponseCall(@Field("token") String str, @Field("sender") String str2, @Field("to") String str3, @Field("message") String str4, @Field("type") String str5, @Field("routing") String str6);

}
