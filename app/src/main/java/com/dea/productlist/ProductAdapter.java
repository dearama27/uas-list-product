package com.dea.productlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dea.productlist.R;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private ArrayList<Product> list;
    Context context;

    public ProductAdapter(ArrayList<Product> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //menentukan layout yang telah ditentukan
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.rv_item_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        //tempat menampilkan data ke dalam layout
        final String title, posterPath, releaseDate, overview;

        /* mengambil data yg ditampung pada class
        ingat class dapat menampung data seperti database
         */

        title = list.get(position).getTitle();
        posterPath = list.get(position).getPoster_path();
        releaseDate = list.get(position).getRelease_date();
        overview = list.get(position).getOverview();

        holder.tvTitle.setText(title);
        holder.tvReleaseDate.setText(releaseDate);

        // menampilkan gambar kedalam imageview menggunakan glide
        Glide.with(context)
                .load(posterPath)
                .override(150, 150)
                .into(holder.ivPoster);

        // agar bisa di klik
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tampung pada bundle
                Bundle b = new Bundle();
                b.putString("b_title", title);
                b.putString("b_poster_path", posterPath);
                b.putString("b_harga", releaseDate);
                b.putString("b_overview", "Produk dengan nama "+title+" model "+overview+" di bandrol dengan harga "+releaseDate);

                //buka activity detail
                Intent i = new Intent (context, DetailActivity.class);
                i.putExtras(b);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //mendefinisikan objek pada layout, textview, imageview, dsb
        TextView tvTitle, tvReleaseDate;
        ImageView ivPoster;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvReleaseDate = (TextView) itemView.findViewById(R.id.tv_release_date);
            ivPoster = (ImageView) itemView.findViewById(R.id.iv_poster);
        }
    }


}
