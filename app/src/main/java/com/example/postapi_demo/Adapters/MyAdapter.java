package com.example.postapi_demo.Adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.postapi_demo.Activities.Fragments.Fragment_All_Product;
import com.example.postapi_demo.Activities.Fragments.Fragment_Interface;
import com.example.postapi_demo.Activities.PaymentActivity;
import com.example.postapi_demo.Models.DeleteData;
import com.example.postapi_demo.Models.Productdatum;
import com.example.postapi_demo.R;
import com.example.postapi_demo.Retro_Object_Class;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.User_Holder> {
    FragmentActivity context;
    List<Productdatum> productdataList = new ArrayList<>();
    Fragment_Interface fragment_interface;

    public MyAdapter(FragmentActivity context, List<Productdatum> productdataList, Fragment_Interface fragment_interface) {
        this.context = context;
        this.productdataList = productdataList;
        this.fragment_interface = fragment_interface;
    }

    public MyAdapter() {

    }

    public MyAdapter(FragmentActivity activity, ArrayList<Productdatum> productdataList) {
        this.context=activity;
        this.productdataList=productdataList;
    }

    @NonNull
    @Override
    public MyAdapter.User_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.show_prod_item, parent, false);
        User_Holder holder = new User_Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.User_Holder holder, @SuppressLint("RecyclerView") int position) {


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view)
            {
                Intent intent= new Intent(context, PaymentActivity.class);
                intent.putExtra("proName",productdataList.get(position).getProName());
                intent.putExtra("proPrice",productdataList.get(position).getProPrice());
                intent.putExtra("proDes",productdataList.get(position).getProDes());
                intent.putExtra("proImage",productdataList.get(position).getProImage());
                context.startActivity(intent);
                return false;
            }
        });

        holder.p_Name.setText("" + productdataList.get(position).getProName());
        holder.p_Price.setText("" + productdataList.get(position).getProPrice());
        holder.p_Des.setText("" + productdataList.get(position).getProDes());
        String img = "https://amiparaandroid.000webhostapp.com/Myapp/" + productdataList.get(holder.getAdapterPosition()).getProImage();

        //Picasso.get().load(img).memoryPolicy(MemoryPolicy.NO_CACHE).into(holder.imageView);


//        Glide.with(context).load(img).into(holder.imageView);
        Picasso.get().invalidate(img);
        Picasso.get().load(img).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.jmkjkfg).into(holder.imageView);
//                .into(holder.imageView);
//
//        Glide.with(context).load(img)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true)
//                .into(holder.imageView);

//        Picasso.get()
//                .load(img)
//                .placeholder(R.drawable.jmkjkfg)
//                .into(holder.imageView);
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, holder.imageButton);
                popupMenu.getMenuInflater().inflate(R.menu.edit_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.deleteProduct) {
                            Retro_Object_Class.CallApi().deleteProducttt(productdataList.get(holder.getAdapterPosition()).getId()).enqueue(new Callback<DeleteData>() {
                                @Override
                                public void onResponse(Call<DeleteData> call, Response<DeleteData> response) {
                                    Log.d("TAG===", "onResponse: " + productdataList.get(holder.getAdapterPosition()).getId());
                                    Log.d("delete", "onResponse: " + response.body().getResult());
                                    if (response.body().getConnection() == 1 && response.body().getResult() == 1) {
                                        Toast.makeText(context, "Product-" + (position + 1) + " no more available..", Toast.LENGTH_LONG).show();
                                        productdataList.remove(position);
                                        notifyDataSetChanged();
                                        if (productdataList.isEmpty()) {
                                            Toast.makeText(context, "No more products available..", Toast.LENGTH_LONG).show();
                                        }
                                    } else if (response.body().getResult() == 0) {
                                        Toast.makeText(context, "No more products available..", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(context, "Something went wrong..", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<DeleteData> call, Throwable t) {
                                    Log.e("delete", "onResponse: " + t.getLocalizedMessage());
                                    Toast.makeText(context, "Something went wrong..", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                        if (item.getItemId() == R.id.updateProducr) {
                            fragment_interface.onFragmentCall(productdataList.get(position).getId(), productdataList.get(position).getProName(), productdataList.get(position).getProPrice(), productdataList.get(position).getProDes(), productdataList.get(position).getProImage());
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }

        });
    }

    @Override
    public int getItemCount() {

        return productdataList.size();
    }

    public class User_Holder extends RecyclerView.ViewHolder {
        TextView p_Name, p_Price, p_Des;
        ImageView imageView;
        ImageButton imageButton;

        public User_Holder(@NonNull View itemView) {
            super(itemView);
            p_Name = itemView.findViewById(R.id.txt_proName);
            p_Price = itemView.findViewById(R.id.txt_proPrice);
            p_Des = itemView.findViewById(R.id.txt_proDes);
            imageView = itemView.findViewById(R.id.show_pro_item_proImage);
            imageButton = itemView.findViewById(R.id.menu);

        }
    }
}
