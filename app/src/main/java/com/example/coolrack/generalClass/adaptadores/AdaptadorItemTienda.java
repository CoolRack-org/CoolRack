package com.example.coolrack.generalClass.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.coolrack.R;
import com.example.coolrack.generalClass.pojos.Libro;

import java.util.List;

public class AdaptadorItemTienda extends RecyclerView.Adapter<AdaptadorItemTienda.BookViewHolder> {

    private List<Libro> books;

    public AdaptadorItemTienda(List<Libro> books){//, AdapterView.OnItemClickListener listener) {
        this.books = books;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_tienda, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load("https://i.postimg.cc/nz6dyrHd/cw.jpg")//books.get(superPosition).getImg())
                .into(holder.imageView);
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
