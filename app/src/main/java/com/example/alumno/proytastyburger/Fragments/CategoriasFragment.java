package com.example.alumno.proytastyburger.Fragments;


import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.alumno.proytastyburger.Adapters.CategoriasAdapter;
import com.example.alumno.proytastyburger.Adapters.UsuarioAdapter;
import com.example.alumno.proytastyburger.Entidades.Categoria;
import com.example.alumno.proytastyburger.Entidades.Usuario;
import com.example.alumno.proytastyburger.NavigationActivity;
import com.example.alumno.proytastyburger.ProductosActivity;
import com.example.alumno.proytastyburger.R;
import com.example.alumno.proytastyburger.RegistroActivity;
import com.example.alumno.proytastyburger.Total;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriasFragment extends Fragment{
    View view;
    ListView mlvCategorias;

    ProgressDialog progressDialog;

    public CategoriasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_categorias, container, false);
        mlvCategorias = view.findViewById(R.id.lvCategorias);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Cargando.. ");
        progressDialog.setMessage("Por favor espere");
        progressDialog.show();

        String ruta = Total.rutaServidor + "consultarcategorias.php";

        new AsyncHttpClient().get(ruta,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                final ArrayList <HashMap<String,String>> arrayList =
                        new ArrayList<>();
                try {
                    for(int i = 0;i <= response.length()-1; i++){
                        HashMap<String,String> map = new HashMap<>();
                        JSONObject jsonObject = null;

                        jsonObject = response.getJSONObject(i);

                        map.put("cod",jsonObject.getString("idcategoria"));
                        map.put("nom",jsonObject.getString("nomcate"));
                        map.put("fot",jsonObject.getString("fotocate"));
                        arrayList.add(map);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

                CategoriasAdapter categoriasAdapter = new CategoriasAdapter(getActivity(),arrayList);
                mlvCategorias.setAdapter(categoriasAdapter);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                },1500);



                mlvCategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        HashMap<String,String> map = (HashMap<String,String>)arrayList.get(position);

                        String idcat = map.get("cod");
                        String nombr = map.get("nom");

                        Bundle bundle = new Bundle();
                        bundle.putString("co",idcat);
                        bundle.putString("no",nombr);


                        Intent intent = new Intent(getContext(),ProductosActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });



            }
        });


        return view;

    }









}
