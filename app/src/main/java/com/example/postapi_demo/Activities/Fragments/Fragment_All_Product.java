package com.example.postapi_demo.Activities.Fragments;

import static android.app.Activity.RESULT_OK;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    public RecyclerView recyclerView;
    public SearchView searchviewe;
    public ProgressBar progressBar;

    ImageView imageView;
    MyAdapter myAdapter;
    EditText name;
    ArrayList<Productdatum> productdataList = new ArrayList<>();
    Button button;

    LottieAnimationView lottieAnimationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_show_product, container, false);

        recyclerView = view.findViewById(R.id.show_prod_recycler);
        searchviewe = view.findViewById(R.id.searchviewe);


        searchviewe.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
//                Log.e("==n===", "onQueryTextChange: "+productdataList.size() );

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                ArrayList<Productdatum> searchlist = new ArrayList<>();

//                Log.e("==n===", "onQueryTextChange: "+productdataList.size() );

//                Toast.makeText(getActivity() ,"======"+productdataList.size(),  Toast.LENGTH_SHORT).show();

                for (int i = 0; i < productdataList.size(); i++) {

                    String name = productdataList.get(i).getProName();

                    Log.e("==n===", "onQueryTextChange: " + name + "     ----     " + s);

                    if (name.trim().toLowerCase().contains(s.trim().toLowerCase())) {

                        searchlist.add(productdataList.get(i));

                        Log.e("====s=", "onQueryTextChange: " + productdataList.get(i));

                    }
                }
                myAdapter = new MyAdapter(Fragment_All_Product.this.getActivity(), searchlist);
                recyclerView.setAdapter(myAdapter);

                return false;
            }
        });



        progressBar = view.findViewById(R.id.progressBar);
        Retro_Object_Class.CallApi().showAllProducts().enqueue(new Callback<MyviewProducts>() {
            @Override
            public void onResponse(Call<MyviewProducts> call, Response<MyviewProducts> response) {
                Log.d("ttt", "onResponse: " + response.body());
                if (response.body().getConnection() == 1 && response.body().getResult() == 1) {
                    productdataList.addAll(response.body().getProductdata());
                    progressBar.setVisibility(ProgressBar.GONE);

                    Log.d("aaa", "onResponse: " + response.body().toString().length());
                    Log.d("aaa", "onResponse:  productdataList = " + productdataList);

                    myAdapter = new MyAdapter(Fragment_All_Product.this.getActivity(), productdataList);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);
                    MaterialDividerItemDecoration mDividerItemDecoration = new MaterialDividerItemDecoration(recyclerView.getContext(),
                            layoutManager.getOrientation());
                    recyclerView.addItemDecoration(mDividerItemDecoration);
                    //myAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(myAdapter);


                } else if (response.body().getResult() == 0) {
                    Toast.makeText(getContext(), "No more items available", Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(ProgressBar.GONE);
                        }
                    }, 2000);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            lottieAnimationView.setVisibility(LottieAnimationView.VISIBLE);
                        }
                    }, 2000);


                } else {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
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

                Log.d("======", "onActivityResult: " + resultUri);
                imageView.setImageURI(resultUri);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}