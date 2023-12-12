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

    private void createSnackbar(View view, int text, Libro libro, int option, int modelPosition){
        Snackbar.make(view,text,Snackbar.LENGTH_LONG).setAction("DESHACER", v -> {
            personalizeCallback(libro, option, modelPosition);
            notifyDataSetChanged();
        }).show();

        // Update estado del libro en DB después que termine el Snackbar
        personalizeCallback(libro, option, modelPosition);
        notifyDataSetChanged();
    }

    private void personalizeCallback(Libro libro, int option, int modelPosition){
        switch (option){
            case 1: // favorito
                if (!libro.getFavorito() && seccion.equals("Favoritos")){
                    model.add(modelPosition, libro);
                    notifyItemInserted(modelPosition);
                }

                libro.setFavorito(!libro.getFavorito());
                break;

            case 2: // paraLeer
                libro.setParaLeer(!libro.getParaLeer());

                if (libro.getLeido()){
                    libro.setLeido(false);
                }
                if (libro.getParaLeer() && seccion.equals("Para Leer")){
                    model.add(modelPosition, libro);
                    notifyItemInserted(modelPosition);
                }
                break;

            case 3: // leido
                libro.setLeido(!libro.getLeido());

                if (libro.getParaLeer()){
                    libro.setParaLeer(false);
                }

                if (!libro.getLeido() && seccion.equals("Leyendo") || libro.getLeido() && seccion.equals("Leidos")) {
                    model.add(modelPosition, libro);
                    notifyItemInserted(modelPosition);
                }
                break;

            case 4: //papelera
                if (!libro.getPapelera()) {
                    model.remove(modelPosition);
                    notifyItemRemoved(modelPosition);
                }
                libro.setPapelera(!libro.getPapelera());
                if (!libro.getPapelera()) {
                    model.add(modelPosition, libro);
                    notifyItemInserted(modelPosition);
                }
                break;
        }
        queryRecord.updateBook(libro);
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

// -------- Marca las acciones de los botones al hacer click y cambia sus colores ---------------------------------------------------------
            bFaborito.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer text;
                    if (libro.getFavorito()){
                        bFaborito.setImageResource(R.drawable.ic_star_border);
                        text = R.string.item_book_drop_favoritos;
                    } else {
                        bFaborito.setImageResource(R.drawable.ic_star_border_color);
                        text = R.string.item_book_add_favoritos;
                    }
                    createSnackbar(view, text, libro, 1, getPosition());

                    // si el libro se encuentra en el fragment "Favoritos"
                    // al ser seleccionado en "Favoritos" se entiende que se elimina de la lista
                    if (seccion.equals("Favoritos")){
                        model.remove(getPosition());
                        notifyDataSetChanged();
                    }
                }
            });

            bParaLeer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer texto;
                    if (libro.getParaLeer()){
                        bParaLeer.setImageResource(R.drawable.ic_access_time);
                        texto = R.string.item_book_drop_paraLeer;
                    } else {
                        bParaLeer.setImageResource(R.drawable.ic_access_time_color);
                        bLeidos.setImageResource(R.drawable.ic_done_all);
                        texto = R.string.item_book_add_paraLeer;
                    }

                    if (seccion.equals("Leidos") || seccion.equals("Para Leer")){
                        model.remove(getPosition());
                        notifyDataSetChanged();
                    }

                    createSnackbar(view, texto,libro, 2, getPosition());
                }
            });

            bLeidos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer texto;
                    if (libro.getLeido()){
                        bLeidos.setImageResource(R.drawable.ic_done_all);
                        texto = R.string.item_book_drop_leidos;
                    } else {
                        bLeidos.setImageResource(R.drawable.ic_done_all_color);
                        bParaLeer.setImageResource(R.drawable.ic_access_time);
                        texto = R.string.item_book_add_leidos;
                    }

                    // Los libros con el estado "Leido" no pueden estar en las secciondes de "Leyendo" y "Para Leer"
                    // a su vez, al ser seleccionado en "Leidos" entiende que se elimina de la lista
                    if (seccion.equals("Leyendo") || seccion.equals("Leidos") || seccion.equals("Para Leer")){
                        model.remove(getPosition());
                        notifyItemRemoved(getPosition());
                        notifyDataSetChanged();
                    }

                    createSnackbar(view, texto, libro, 3, getPosition());
                }
            });

            bPapelera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createSnackbar(view, R.string.item_book_go_to_papelera, libro, 4, getPosition());
                }
            });

        }
    }
}
