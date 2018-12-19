package com.example.alumno.proytastyburger.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alumno.proytastyburger.R;
import com.example.alumno.proytastyburger.Total;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Alumno-CT on 27/06/2018.
 */

public class CategoriasAdapter extends ArrayAdapter {
    Activity vActivity;
    ArrayList varrayDatos;

    public CategoriasAdapter(Activity activity, ArrayList arrayDatos) {
        super(activity, R.layout.item_fcategoria,arrayDatos);
        vActivity = activity;
        varrayDatos = arrayDatos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rootView = vActivity.getLayoutInflater().inflate(R.layout.item_fcategoria,null);

        TextView mtxtID = rootView.findViewById(R.id.txtID);
        TextView mtxtNombre = rootView.findViewById(R.id.txtNombreCategoria);
        ImageView mimgFotoCate = rootView.findViewById(R.id.imgFotoCategoria);

        HashMap<String,String> map = (HashMap<String,String>) varrayDatos.get(position);

        mtxtID.setText(map.get("cod").toString());
        mtxtNombre.setText(map.get("nom").toString());


        String ruta = Total.rutaServidor + map.get("fot").toString();

        Picasso.get().load(ruta).into(mimgFotoCate);

        return rootView;
    }
}
