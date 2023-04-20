package com.example.postapi_demo;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retro_Object_Class
{
    public static Retro_Interface CallApi()
    {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://amiparaandroid.000webhostapp.com/Myapp/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Retro_Interface retro_interface = retrofit.create(Retro_Interface.class);
        return retro_interface;
    }

}
