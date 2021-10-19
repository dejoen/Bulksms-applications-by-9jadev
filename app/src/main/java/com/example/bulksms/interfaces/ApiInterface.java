package com.example.bulksms.interfaces;


import com.example.bulksms.models.RegistrationMmodel;
import com.example.bulksms.models.loginModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    public static  final  String BASE_URL="https://www.app.smartsmssolutions.ng/";
   @POST("io/api/client/v1/sms/")
   @FormUrlEncoded
   Call<loginModel>  loginUser(@Field("token")String token ,
   @Field("sender")String sender_id,@Field("to")String sendTo,@Field("message") String message,
                               @Field("type") int type, @Field("routing") int routing);


}
