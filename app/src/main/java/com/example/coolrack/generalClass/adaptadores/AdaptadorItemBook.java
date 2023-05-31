package com.example.coolrack.generalClass.adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coolrack.R;
import com.example.coolrack.generalClass.ImagesManagers.BitmapManager;
import com.example.coolrack.generalClass.SQLiteControll.QueryRecord;
import com.example.coolrack.generalClass.TransitionManager;
import com.example.coolrack.generalClass.pojos.Libro;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class AdaptadorItemBook extends RecyclerView.Adapter<AdaptadorItemBook.ViewHolder> implements View.OnClickListener{

    private LayoutInflater inflater;
    private ArrayList<Libro> model;
    private Context context;
    // Titulo de la seccion del programa(ej: Leyendo, Biblioteca, etc...).
    String seccion;

    private QueryRecord queryRecord;

    //listener
    private View.OnClickListener listener;

    public AdaptadorItemBook(Context context, ArrayList<Libro> model, String seccion){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.model = model;
        this.queryRecord = QueryRecord.get(context);
        this.seccion = seccion;
    }

    public AdaptadorItemBook(Context context, ArrayList<Libro> model){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.model = model;
        this.queryRecord = QueryRecord.get(context);
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
        String autor = model.get(position).getAuthor();
        String formato = model.get(position).getFormat();
        Bitmap imagen = new BitmapManager().bitmapUncompress(model.get(position).getImg());
        Libro libro = model.get(position);

        holder.titulo.setText(titulo);
        holder.autor.setText(autor);
        holder.formato.setText(formato);
        holder.imagen.setImageBitmap(imagen);
        holder.libro = libro;

        detectButtonsColor(holder, libro);
    }

    // cambia el color de la imagenes de los botones dependiendo de los booleanos del pojo
    private void detectButtonsColor(ViewHolder holder, Libro libro){
        // Boton favorito
            if (libro.getFavorito())
                holder.bFaborito.setImageResource(R.drawable.ic_star_border_color);
            else
                holder.bFaborito.setImageResource(R.drawable.ic_star_border);
        // Boton leidos
            if (libro.getLeido())
                holder.bLeidos.setImageResource(R.drawable.ic_done_all_color);
            else
                holder.bLeidos.setImageResource(R.drawable.ic_done_all);
        // Boton para leer
            if (libro.getParaLeer())
                holder.bParaLeer.setImageResource(R.drawable.ic_access_time_color);
            else
                holder.bParaLeer.setImageResource(R.drawable.ic_access_time);
        // Boton papelera
            if (libro.getPapelera())
                holder.bPapelera.setImageResource(R.drawable.ic_delete_color);
            else
                holder.bPapelera.setImageResource(R.drawable.ic_delete);
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
        ImageButton bFaborito, bLeidos, bParaLeer, bPapelera;
        Libro libro;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.titulo_book);
            autor = itemView.findViewById(R.id.autor_book);
            formato = itemView.findViewById(R.id.formato_book);
            imagen = itemView.findViewById(R.id.imageBookItem);
            bFaborito = itemView.findViewById(R.id.imageButtonCardFavorito);
            bLeidos = itemView.findViewById(R.id.imageButtonCardLeido);
            bParaLeer = itemView.findViewById(R.id.imageButtonCardParaLeer);
            bPapelera = itemView.findViewById(R.id.imageButtonCardPapelera);

            imagen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!libro.getLeyendo()){
                        libro.setLeyendo(true);
                        queryRecord.updateBook(libro);
                    }

                    new TransitionManager(context).goToLecturaActivity(libro.getCopyBookUrl(), true);
                }
            });

            // cambia el color de la imagenes de los botones dependiendo de los booleanos del pojo
            // Boton favorito

// -------- Marca las acciones de los botones al hacer click y cambia sus colores ---------------------------------------------------------
            bFaborito.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (libro.getFavorito()){
                        libro.setFavorito(false);
                        bFaborito.setImageResource(R.drawable.ic_star_border);

                        Snackbar.make(view,"Quitado de \"Favoritos\"",Snackbar.LENGTH_LONG).show();
                    } else {
                        libro.setFavorito(true);
                        bFaborito.setImageResource(R.drawable.ic_star_border_color);

                        Snackbar.make(view,"Agragado a \"Favoritos\"",Snackbar.LENGTH_LONG).show();
                    }
                    queryRecord.updateBook(libro);

                    if (seccion == "Favoritos"){
                        model.remove(getPosition());
                        notifyDataSetChanged();
                    }
                }
            });


            bParaLeer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (libro.getParaLeer()){
                        libro.setParaLeer(false);
                        bParaLeer.setImageResource(R.drawable.ic_access_time);

                        Snackbar.make(view,"Quitado de \"Para Leer\"",Snackbar.LENGTH_LONG).show();
                    } else {
                        libro.setParaLeer(true);
                        bParaLeer.setImageResource(R.drawable.ic_access_time_color);

                        libro.setLeido(false);
                        bLeidos.setImageResource(R.drawable.ic_done_all);

                        Snackbar.make(view,"Agragado a \"Para Leer\"",Snackbar.LENGTH_LONG).show();
                    }
                    queryRecord.updateBook(libro);

                    if (seccion == "Leidos" || seccion == "Para Leer"){
                        model.remove(getPosition());
                        notifyDataSetChanged();
                    }
                }
            });


            bLeidos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (libro.getLeido()){
                        libro.setLeido(false);
                        bLeidos.setImageResource(R.drawable.ic_done_all);

                        Snackbar.make(view,"Quitado de \"Leidos\"",Snackbar.LENGTH_LONG).show();
                    } else {
                        libro.setLeido(true);
                        bLeidos.setImageResource(R.drawable.ic_done_all_color);

                        libro.setParaLeer(false);
                        bParaLeer.setImageResource(R.drawable.ic_access_time);

                        Snackbar.make(view,"Agragado a \"Leidos\"",Snackbar.LENGTH_LONG).show();
                    }
                    queryRecord.updateBook(libro);

                    if (seccion == "Leyendo" || seccion == "Leidos" || seccion == "Para Leer"){
                        model.remove(getPosition());
                        notifyDataSetChanged();
                    }
                }
            });

            bPapelera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    libro.setPapelera(true);
                    queryRecord.updateBook(libro);

                    model.remove(getPosition());
                    notifyDataSetChanged();
                    queryRecord.updateBook(libro);

                    Snackbar.make(view,"Libro enviado a la Papelera",Snackbar.LENGTH_LONG).show();

                }
            });

        }
    }
}
