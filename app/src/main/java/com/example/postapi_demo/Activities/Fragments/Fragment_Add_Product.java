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

public class Fragment_Add_Product extends Fragment {

    ImageView imageView;
    EditText name;

    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_product, container, false);

        imageView = (ImageView) view.findViewById(R.id.image);
        name = view.findViewById(R.id.name);
        button = view.findViewById(R.id.addproduict);

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


                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                byte[] imageInByte = baos.toByteArray();


                String imagedata = Base64.getEncoder().encodeToString(imageInByte);

                String userid = SplashActivity.sp.getString("userid", "");


                Retro_Object_Class.CallApi().addproducttt(userid, name.getText().toString(), "565", "free free", imagedata).enqueue(new Callback<ProductAdddd>() {
                    @Override
                    public void onResponse(Call<ProductAdddd> call, Response<ProductAdddd> response) {

                        Log.d("aaa", "onResponse: " + response.body());


                    }

                    @Override
                    public void onFailure(Call<ProductAdddd> call, Throwable t) {

                        Log.d("error", "onResponse: " + t.getLocalizedMessage());


                    }
                });


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