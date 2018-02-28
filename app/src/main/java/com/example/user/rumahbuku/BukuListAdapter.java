package com.example.user.rumahbuku;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Belal on 2/23/2017.
 */

public class BukuListAdapter extends RecyclerView.Adapter<BukuListAdapter.ViewHolder> {
    private Context context;
    private List<Buku> bukus;

    public BukuListAdapter(Context context, List<Buku> bukus){
        this.bukus = bukus;
        this.context = context;
    }
    @Override
    public BukuListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_buku, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BukuListAdapter.ViewHolder holder, int position) {
        Buku buku = bukus.get(position);
        holder.tv_judul.setText(buku.getJudul_buku());
        holder.tv_namalib.setText(buku.getNamalib());
        Glide.with(context).load(buku.getUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return bukus.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_judul,tv_namalib;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_judul = (TextView) itemView.findViewById(R.id.tv_judul);
            tv_namalib = (TextView)itemView.findViewById(R.id.tv_namalib);
            imageView = (ImageView) itemView.findViewById(R.id.gambarbuku);

        }
    }
}

