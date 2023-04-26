package com.example.postapi_demo.Adapters;

import android.annotation.SuppressLint;
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

import com.example.postapi_demo.Activities.Fragments.Fragment_Interface;
import com.example.postapi_demo.Models.DeleteData;
import com.example.postapi_demo.Models.Productdatum;
import com.example.postapi_demo.R;
import com.example.postapi_demo.Retro_Object_Class;
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
        this.fragment_interface=fragment_interface;
    }

    public MyAdapter() {

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

        holder.p_Name.setText("" + productdataList.get(position).getProName());
        holder.p_Price.setText("" + productdataList.get(position).getProPrice());
        holder.p_Des.setText("" + productdataList.get(position).getProDes());
        String img = "https://amiparaandroid.000webhostapp.com/Myapp/" + productdataList.get(holder.getAdapterPosition()).getProImage();
//        Glide.with(context).load(img).into(holder.imageView);
        Picasso.get()
                .load(img)
                .placeholder(R.drawable.jmkjkfg)
                .into(holder.imageView);
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, holder.imageButton);
                popupMenu.getMenuInflater().inflate(R.menu.edit_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.deleteProduct)
                        {
                            Retro_Object_Class.CallApi().deleteProducttt(productdataList.get(holder.getAdapterPosition()).getId()).enqueue(new Callback<DeleteData>() {
                                @Override
                                public void onResponse(Call<DeleteData> call, Response<DeleteData> response) {
                                    Log.d("TAG===", "onResponse: " + productdataList.get(holder.getAdapterPosition()).getId());
                                    Log.d("delete", "onResponse: " + response.body().getResult());
                                    if (response.body().getConnection() == 1 && response.body().getResult() == 1)
                                    {
                                        Toast.makeText(context, "Product-"+(position+1)+" no more available..", Toast.LENGTH_LONG).show();
                                        productdataList.remove(position);
                                        notifyDataSetChanged();
                                        if(productdataList.isEmpty())
                                        {
                                            Toast.makeText(context, "No more products available..", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    else if (response.body().getResult() == 0)
                                    {
                                        Toast.makeText(context, "No more products available..", Toast.LENGTH_LONG).show();
                                    }
                                    else
                                    {
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
                        if (item.getItemId() == R.id.updateProducr){
                            fragment_interface.onFragmentCall(productdataList.get(position).getId(),productdataList.get(position).getProName(),productdataList.get(position).getProPrice(),productdataList.get(position).getProDes(),productdataList.get(position).getProImage());
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
