package com.example.coolrack.generalClass.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coolrack.R;
import com.example.coolrack.generalClass.pojos.Libro;

import java.util.List;

public class AdaptadorItemTienda extends RecyclerView.Adapter<AdaptadorItemTienda.LibroViewHolder> {

    private List<Libro> libroList;
    private Context context;

    public AdaptadorItemTienda(List<Libro> libroList, Context context) {
        this.libroList = libroList;
        this.context = context;
    }

    @NonNull
    @Override
    public LibroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_tienda, parent, false);
        return new LibroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LibroViewHolder holder, int position) {
        Libro libro = libroList.get(position);
        holder.tituloTextView.setText(libro.getTitle());
        Glide.with(context)
                .load(libro.getUrlImage())
                //.placeholder(R.drawable.placeholder)
                .into(holder.portadaImageView);
    }

    @Override
    public int getItemCount() {
        return libroList.size();
    }

    public class LibroViewHolder extends RecyclerView.ViewHolder {

        public TextView tituloTextView;
        public ImageView portadaImageView;

        public LibroViewHolder(@NonNull View itemView) {
            super(itemView);
            tituloTextView = itemView.findViewById(R.id.tituloTextView);
            portadaImageView = itemView.findViewById(R.id.portadaImageView);
        }
    }
}
