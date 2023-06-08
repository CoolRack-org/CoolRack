package com.example.coolrack.generalClass.adaptadores;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coolrack.R;
import com.example.coolrack.generalClass.pojos.LibroTienda;

import java.util.List;

public class AdaptadorItemTienda extends RecyclerView.Adapter<AdaptadorItemTienda.BookViewHolder> {

    private List<LibroTienda> books;

    public AdaptadorItemTienda(List<LibroTienda> books){//, AdapterView.OnItemClickListener listener) {
        this.books = books;
        notifyDataSetChanged();
    }

    public void setLibros(List<LibroTienda> libros) {
        this.books = libros;
        notifyDataSetChanged();
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_tienda, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(books.get(position).getImg())//books.get(superPosition).getImg())
                .into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,books.get(position).getImg());
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public interface OnItemClickListener {
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView;

        public BookViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardviewTienda);
            imageView = itemView.findViewById(R.id.coverTiendaImg);
        }
    }
}
