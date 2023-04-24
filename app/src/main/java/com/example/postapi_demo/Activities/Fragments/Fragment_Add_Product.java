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
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.postapi_demo.Activities.SplashActivity;
import com.example.postapi_demo.Models.ProductAdddd;
import com.example.postapi_demo.R;
import com.example.postapi_demo.Retro_Object_Class;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Add_Product extends Fragment
{
    ImageView imageView;
    EditText proName,proPrice,proDes;
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_product, container, false);

        imageView = view.findViewById(R.id.proImage);
        proName = view.findViewById(R.id.proName);
        proPrice = view.findViewById(R.id.proPrice);
        proDes = view.findViewById(R.id.proDes);
        button = view.findViewById(R.id.btn_addpProduct);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CropImage.activity()
                        .start(getContext(), Fragment_Add_Product.this);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                try {
                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                    byte[] imageInByte = baos.toByteArray();
                    String imagedata = Base64.getEncoder().encodeToString(imageInByte);
                    String userid = SplashActivity.sp.getString("userid", "");
                    Retro_Object_Class.CallApi()
                        .addproducttt(userid, proName.getText().toString(),
                                proPrice.getText().toString(),
                                proDes.getText().toString(), imagedata)
                        .enqueue(new Callback<ProductAdddd>()
                        {
                            @Override
                            public void onResponse(Call<ProductAdddd> call, Response<ProductAdddd> response) {
                                if(response.body().getConnection()==1 && response.body().getProductaddd()==1) {
                                    Toast.makeText(getContext(), "Product added successfully", Toast.LENGTH_LONG).show();
                                }
                                else if (response.body().getProductaddd()==0)
                                {
                                    Toast.makeText(getContext(), "Product not added", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Toast.makeText(getContext(), "Something went wrong..", Toast.LENGTH_LONG).show();
                                }
                                Log.d("aaa", "onResponse: " + response.body());
                            }

                            @Override
                            public void onFailure(Call<ProductAdddd> call, Throwable t) {
                                Log.d("error", "onResponse: " + t.getLocalizedMessage());
                            }
                        });
                }
                catch (ClassCastException exception)
                {
                    Toast.makeText(getContext(), "Please upload image", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Log.d("aaa", "onActivityResult: "+resultUri);
                imageView.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}