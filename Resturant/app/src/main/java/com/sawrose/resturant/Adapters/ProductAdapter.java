package com.sawrose.resturant.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sawrose.resturant.R;
import com.sawrose.resturant.database.ProductClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sawrose on 10/6/16.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Viewholder> {
    public List<ProductClass> productlist;
    public Context context;

    public ProductAdapter(Context context,List<ProductClass> productlist) {

        this.productlist = productlist;
        this.context = context;
    }
    @Override
    public int getItemCount() {
        return productlist.size();
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View questionview= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_product,parent,false);
        Viewholder xy = new Viewholder(questionview);
        return xy;
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        ProductClass data= productlist.get(position);
        holder.name.setText(data.getProduct_name());
        holder.price.setText(data.getProduct_price());
        holder.detail.setText(data.getProduct_detail());
        holder.description.setText(data.getProduct_description());

        Glide
                .with(context)
                .load(data.getProduct_image())
                .centerCrop()
                .crossFade()
                .into(holder.image);

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        public TextView name, price, detail,description;
        public ImageView image;
        public Viewholder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.list_product_name);
            price = (TextView) itemView.findViewById(R.id.list_product_price);
            detail = (TextView) itemView.findViewById(R.id.list_product_details);
            description = (TextView) itemView.findViewById(R.id.list_product_description);
            image = (ImageView) itemView.findViewById(R.id.list_product_image);

        }
    }

}
