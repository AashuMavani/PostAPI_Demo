package com.example.postapi_demo.Activities.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postapi_demo.Activities.SplashActivity;
import com.example.postapi_demo.Adapters.MyAdapter;
import com.example.postapi_demo.Models.MyviewProducts;
import com.example.postapi_demo.Models.Productdatum;
import com.example.postapi_demo.R;
import com.example.postapi_demo.Retro_Object_Class;
import com.google.android.material.divider.MaterialDividerItemDecoration;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Show_Product extends Fragment {
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_show_product, container, false);
        recyclerView = view.findViewById(R.id.show_prod_recycler);
        viewData();
        return view;
    }

    private void viewData()
    {
        Retro_Object_Class.CallApi().viewProducttt(SplashActivity.sp.getString("userid","")).enqueue(new Callback<MyviewProducts>() {
            @Override
            public void onResponse(Call<MyviewProducts> call, Response<MyviewProducts> response) {
                //Log.d("mmm", "onResponse: "+response.body().getProductdata());
                List<Productdatum> productdataList = response.body().getProductdata();

                MyAdapter myAdapter=new MyAdapter(getContext(),productdataList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                MaterialDividerItemDecoration mDividerItemDecoration = new MaterialDividerItemDecoration(recyclerView.getContext(),
                        layoutManager.getOrientation());
                recyclerView.addItemDecoration(mDividerItemDecoration);
                //myAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(myAdapter);

            }

            @Override
            public void onFailure(Call<MyviewProducts> call, Throwable t) {
                Log.d("mmm", "onError: "+t.getLocalizedMessage());
            }
        });
    }
}