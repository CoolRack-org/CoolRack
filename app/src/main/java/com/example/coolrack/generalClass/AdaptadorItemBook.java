package com.example.coolrack.generalClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coolrack.R;

import java.util.ArrayList;

import nl.siegmann.epublib.domain.Book;

public class AdaptadorItemBook extends RecyclerView.Adapter<AdaptadorItemBook.ViewHolder> implements View.OnClickListener{

    private LayoutInflater inflater;
    private ArrayList<Book> model;

    //listener
    private View.OnClickListener listener;

    public AdaptadorItemBook(Context context, ArrayList<Book> model){
        this.inflater = LayoutInflater.from(context);
        this.model = model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_item_book,parent,false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    //asignacion de valores al item mediante el ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String titulo = model.get(position).getTitle();
        String autor = String.valueOf(model.get(position).getMetadata().getAuthors()); //Falalta serie
        String formato = model.get(position).getMetadata().getFormat();
        int imagen = R.drawable.ic_launcher_background; //model.get(position).getImg();

        holder.titulo.setText(titulo);
        holder.autor.setText(autor);
        holder.formato.setText(formato);
        holder.imagen.setImageResource(imagen);
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public void setOnclickLister(View.OnClickListener listener){
        this.listener = listener;
    }

    //funncion que dicta el comportamiento del item al hacer click en el
    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }

    //Constructor ViewHolder para el item
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titulo,autor,formato;
        ImageView imagen;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.titulo_book);
            autor = itemView.findViewById(R.id.autor_book);
            formato = itemView.findViewById(R.id.formato_book);
            imagen = itemView.findViewById(R.id.imageBookItem);

        }
    }
}
