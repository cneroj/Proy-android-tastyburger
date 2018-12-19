package com.example.alumno.proytastyburger.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alumno.proytastyburger.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Alumno-CT on 27/06/2018.
 */

public class UsuarioAdapter extends ArrayAdapter {
    Activity vActivity;
    ArrayList varrayDatos;

    public UsuarioAdapter(Activity activity, ArrayList arrayDatos) {
        super(activity, R.layout.item_fusuario,arrayDatos);
        this.vActivity = activity;
        this.varrayDatos = arrayDatos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rootView = vActivity.getLayoutInflater().inflate(R.layout.item_fusuario,null);

        TextView mtvDato = rootView.findViewById(R.id.tvDato);
        ImageView mivIcono = rootView.findViewById(R.id.ivIcono);

        HashMap<String,String> map = (HashMap<String,String>) varrayDatos.get(position);
        mtvDato.setText(map.get("dato").toString());

        mivIcono.setImageResource(Integer.parseInt(map.get("icon")));


        return rootView;
    }
}
