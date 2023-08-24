package com.example.postapi_demo;

import com.example.postapi_demo.Models.DeleteData;
import com.example.postapi_demo.Models.LoginData;
import com.example.postapi_demo.Models.MyviewProducts;
import com.example.postapi_demo.Models.ProductAdddd;
import com.example.postapi_demo.Models.RegdData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Retro_Interface {
    @FormUrlEncoded
    @POST("Register.php")
    Call<RegdData> userRegister(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password);


    @FormUrlEncoded
    @POST("login.php")
    Call<LoginData> userLogin(
            @Field("email") String email,
            @Field("password") String password);


    @FormUrlEncoded
    @POST("addProduct.php")
    Call<ProductAdddd> addproducttt(
            @Field("userid") String userid,
            @Field("pname") String proName,
            @Field("pprize") String proPrice,
            @Field("pdes") String proDes,
            @Field("productimage") String proImage);

    @FormUrlEncoded
    @POST("viewProduct.php")
    Call<MyviewProducts> viewProducttt(@Field("userid") String userid);

    @FormUrlEncoded
    @POST("deleteproduct.php")
    Call<DeleteData> deleteProducttt(@Field("id") String userid);

    @FormUrlEncoded
    @POST("updateproduct.php")
        //Response is same for both update and delete, hence pojo is same for both
    Call<DeleteData> updateProduct(@Field("id") String id,
                                   @Field("name")String name,
                                   @Field("price") String price,
                                   @Field("description")String description,
                                   @Field("imagedata")String imagedata,
                                   @Field("imagename")String imagename);

    @GET("showAllproducts.php")

    Call<MyviewProducts> showAllProducts();
}
