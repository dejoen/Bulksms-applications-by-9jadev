package com.example.bulksms.webApi;


import com.example.bulksms.interfaces.ApiInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static ApiClient instance =null;
     ApiInterface myApiInterface;

    private ApiClient(){
         Retrofit retrofit=new Retrofit.Builder()
                 .baseUrl(ApiInterface.BASE_URL)
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();
         myApiInterface=retrofit.create(ApiInterface.class);
     }

    public  static synchronized ApiClient getInstance(){
        if(instance==null){

           instance=new ApiClient();
        }
        return  instance;
    }

    public ApiInterface getWebApi(){
     return myApiInterface;
     }
}
