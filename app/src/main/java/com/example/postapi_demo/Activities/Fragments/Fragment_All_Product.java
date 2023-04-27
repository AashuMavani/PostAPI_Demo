package com.example.postapi_demo.Activities.Fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.airbnb.lottie.LottieAnimationView;
import com.example.postapi_demo.Activities.SplashActivity;
import com.example.postapi_demo.Adapters.MyAdapter;
import com.example.postapi_demo.Models.MyviewProducts;
import com.example.postapi_demo.Models.ProductAdddd;
import com.example.postapi_demo.Models.Productdatum;
import com.example.postapi_demo.R;
import com.example.postapi_demo.Retro_Object_Class;
import com.google.android.material.divider.MaterialDividerItemDecoration;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_All_Product extends Fragment {

    ImageView imageView;
    EditText name;
    ArrayList<Productdatum> productdataList=new ArrayList<>();
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_all_product, container, false);
//
//        imageView = (ImageView) view.findViewById(R.id.image);
//        name = view.findViewById(R.id.name);
//        button = view.findViewById(R.id.addproduict);


                Retro_Object_Class.CallApi().showAllProducts().enqueue(new Callback<MyviewProducts>() {
                    @Override
                    public void onResponse(Call<MyviewProducts> call, Response<MyviewProducts> response) {
                        productdataList.addAll(response.body().getProductdata());
                       // progressBar.setVisibility(ProgressBar.GONE);
//                        if (response.body().getConnection() == 1 && response.body().getResult() == 1) {
//                            productdataList.addAll(response.body().getProductdata());
//                           // progressBar.setVisibility(ProgressBar.GONE);
//                           // lottieAnimationView.setVisibility(LottieAnimationView.VISIBLE);
//                            Log.e("aaa", "onResponse: " + response.body().toString().length());
//                            MyAdapter myAdapter = new MyAdapter(Fragment_All_Product.this.getActivity(), productdataList){
//
//
//                            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//                            recyclerView.setLayoutManager(layoutManager);
//                            MaterialDividerItemDecoration mDividerItemDecoration = new MaterialDividerItemDecoration(recyclerView.getContext(),
//                                    layoutManager.getOrientation());
//                            recyclerView.addItemDecoration(mDividerItemDecoration);
//                            //myAdapter.notifyDataSetChanged();
//                            recyclerView.setAdapter(myAdapter);
//                        } else if (response.body().getResult() == 0) {
//                            Toast.makeText(getContext(), "No more items available", Toast.LENGTH_LONG).show();
//
//                        } else {
//                            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
//                        }


                    }

                    @Override
                    public void onFailure(Call<MyviewProducts> call, Throwable t) {

                    }
                });

        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                Log.d("======", "onActivityResult: "+resultUri);
                imageView.setImageURI(resultUri);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}