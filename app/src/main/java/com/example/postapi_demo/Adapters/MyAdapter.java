package com.example.postapi_demo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.postapi_demo.Models.Productdatum;
import com.example.postapi_demo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.User_Holder>
{
    Context context;
    List<Productdatum> productdataList = new ArrayList<>();
    public MyAdapter(Context context, List<Productdatum> productdataList) {
        this.context=context;
        this.productdataList=productdataList;
    }

    @NonNull
    @Override
    public MyAdapter.User_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.show_prod_item,parent,false);
        User_Holder holder = new User_Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.User_Holder holder, int position) {

        holder.p_Name.setText(""+productdataList.get(position).getProName());
        holder.p_Price.setText(""+productdataList.get(position).getProPrice());
        holder.p_Des.setText(""+productdataList.get(position).getProDes());
        String img="https://amiparaandroid.000webhostapp.com/Myapp/"+productdataList.get(position).getProImage();
//        Glide.with(context).load(img).into(holder.imageView);
        Picasso.get()
                .load(img)
                .placeholder(R.drawable.jmkjkfg)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return productdataList.size();
    }

    public class User_Holder extends RecyclerView.ViewHolder {
        TextView p_Name,p_Price,p_Des;
        ImageView imageView;
        public User_Holder(@NonNull View itemView) {
            super(itemView);
            p_Name=itemView.findViewById(R.id.txt_proName);
            p_Price=itemView.findViewById(R.id.txt_proPrice);
            p_Des=itemView.findViewById(R.id.txt_proDes);
            imageView=itemView.findViewById(R.id.show_pro_item_proImage);

        }
    }
}
