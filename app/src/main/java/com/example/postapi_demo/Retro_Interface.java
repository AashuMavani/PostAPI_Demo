package com.example.postapi_demo;

import com.example.postapi_demo.Models.LoginData;
import com.example.postapi_demo.Models.ProductAdddd;
import com.example.postapi_demo.Models.RegdData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Retro_Interface {
    @FormUrlEncoded
    @POST("Register.php")
    Call<RegdData> userRegister(@Field("name") String name, @Field("email") String email, @Field("password") String password);


    @FormUrlEncoded
    @POST("login.php")
    Call<LoginData> userLogin(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("addProduct.php")
    Call<ProductAdddd> addproducttt(@Field("userid") String useridd, @Field("pname") String pname, @Field("pprize") String pimage, @Field("pdes") String pdesss, @Field("productimage") String productimage );
}
