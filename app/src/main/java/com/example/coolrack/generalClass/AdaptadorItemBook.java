package com.example.coolrack.generalClass;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coolrack.R;
import com.example.coolrack.generalClass.ImagesManagers.BitmapManager;
import com.example.coolrack.generalClass.SQLiteControll.QueryRecord;

import java.util.ArrayList;

public class AdaptadorItemBook extends RecyclerView.Adapter<AdaptadorItemBook.ViewHolder> implements View.OnClickListener{

    private LayoutInflater inflater;
    private ArrayList<Libro> model;
    private Context context;

    private QueryRecord queryRecord;

    //listener
    private View.OnClickListener listener;

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
        Bitmap imagen = new BitmapManager().bitmapUncompress(model.get(position).getImg()); //StringToBitMap(model.get(position).getImg());
        String path = model.get(position).getOriginalBookUrl();
        Libro libro = model.get(position);

        holder.titulo.setText(titulo);
        holder.autor.setText(autor);
        holder.formato.setText(formato);
        holder.imagen.setImageBitmap(imagen);
        holder.path = path;
        holder.libro = libro;
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
        String path;
        Libro libro;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.titulo_book);
            autor = itemView.findViewById(R.id.autor_book);
            formato = itemView.findViewById(R.id.formato_book);
            imagen = itemView.findViewById(R.id.imageBookItem);

            imagen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!libro.getLeyendo()){
                        libro.setLeyendo(true);
                        queryRecord.updateBook(libro);
                    }

                    context.startActivity(new Intent(context, com.example.coolrack.Activities.LecturaActivity.class)
                            .putExtra("epub_location", libro.getCopyBookUrl())
                    );
                }
            });

        }
    }
}
