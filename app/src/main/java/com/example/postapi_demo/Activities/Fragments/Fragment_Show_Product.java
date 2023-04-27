package com.example.postapi_demo.Activities.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.postapi_demo.Activities.SplashActivity;
import com.example.postapi_demo.Adapters.MyAdapter;
import com.example.postapi_demo.Models.MyviewProducts;
import com.example.postapi_demo.Models.Productdatum;
import com.example.postapi_demo.R;
import com.example.postapi_demo.Retro_Object_Class;
import com.google.android.material.divider.MaterialDividerItemDecoration;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Show_Product extends Fragment {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    LottieAnimationView lottieAnimationView;
    ArrayList<Productdatum> productdataList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_show_product, container, false);
        recyclerView = view.findViewById(R.id.show_prod_recycler);
        progressBar=view.findViewById(R.id.progressBar);
        lottieAnimationView=view.findViewById(R.id.lottyAnimation);
        viewData();
        return view;
    }

    private void viewData() {
        Retro_Object_Class.CallApi().viewProducttt(SplashActivity.sp.getString("userid", "")).enqueue(new Callback<MyviewProducts>() {
            @Override
            public void onResponse(Call<MyviewProducts> call, Response<MyviewProducts> response) {
                Log.e("aaa", "onResponse: " + response.body());

                if (response.body().getConnection() == 1 && response.body().getResult() == 1) {
                    productdataList.addAll(response.body().getProductdata());
                    progressBar.setVisibility(ProgressBar.GONE);
                    lottieAnimationView.setVisibility(LottieAnimationView.VISIBLE);
                    Log.e("aaa", "onResponse: " + response.body().toString().length());
                    MyAdapter myAdapter = new MyAdapter(Fragment_Show_Product.this.getActivity(), productdataList, new Fragment_Interface() {
                        @Override
                        public void onFragmentCall(String id, String proName, String proPrice, String proDes, String proImage) {

                            Fragment_Add_Product fragment = new Fragment_Add_Product();
                            Bundle bundle = new Bundle();
                            bundle.putString("id", id);
                            bundle.putString("name", proName);
                            bundle.putString("price", proPrice);
                            bundle.putString("des", proDes);
                            bundle.putString("proImage", proImage);

                            fragment.setArguments(bundle);
                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction = fm.beginTransaction();
                            transaction.replace(R.id.frame, fragment);
                            transaction.commit();
                        }
                    });
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

                } else {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<MyviewProducts> call, Throwable t) {
                Log.d("mmm", "onError: " + t.getLocalizedMessage());
            }
        });
    }
}