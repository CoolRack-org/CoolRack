package com.example.coolrack.generalClass;

import android.content.Context;
import android.content.Intent;

public class TransitionManager {
    Context context;

    public TransitionManager(Context context) {
        this.context = context;
    }

    public void goToPerfilLibro(String id){
        context.startActivity(new Intent(context, com.example.coolrack.Activities.PerfilLibro.class)
                .putExtra("idBook",  id)
        );
    }

    public void goToLecturaActivity(String url, boolean openInProgram){
        context.startActivity(new Intent(context, com.example.coolrack.Activities.LecturaActivity.class)
                .putExtra("epub_location", url)
                .putExtra("openInProgram", openInProgram)
        );
    }
}
