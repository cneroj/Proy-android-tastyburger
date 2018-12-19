package com.example.alumno.proytastyburger.Fragments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.alumno.proytastyburger.Adapters.UsuarioAdapter;
import com.example.alumno.proytastyburger.Entidades.Usuario;
import com.example.alumno.proytastyburger.LoginActivity;
import com.example.alumno.proytastyburger.R;
import com.example.alumno.proytastyburger.Total;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsuarioFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener{

    private static final int COD_SELECCIONA = 10;
    private static final int COD_FOTO = 20;
    private static final String CARPETA_PRINCIPAL = "misImagenesApp/";
    private static final String CARPETA_IMAGEN = "imagenes";
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL+CARPETA_IMAGEN;
    private String path;
    File fileImagen;
    Bitmap bitmap;

    View view;
    ImageButton buttonFoto;
    String nombre,dni,direccion,telefono,correo,foto;
    String datoDNI;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    ListView listView;
    ProgressDialog progressDialog;


    public UsuarioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle datosRecibidos = getArguments();
        view = inflater.inflate(R.layout.fragment_usuario, container, false);


        datoDNI = datosRecibidos.getString("id");
        listView = view.findViewById(R.id.lvDetalleUsuario);
        buttonFoto = view.findViewById(R.id.imgFoto);
        request= Volley.newRequestQueue(getContext());
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Cargando.. ");
        progressDialog.setMessage("Por favor espere");
        progressDialog.show();
        cargarWebService();


        buttonFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoOpciones();
            }


        });


        return view;


    }
    private void mostrarDialogoOpciones() {
        final CharSequence[] opciones = {"Tomar foto","Elegir de galeria","Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Elegir una opcion");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(opciones[i].equals("Tomar foto")){

                    abrirCamara();

                }else if(opciones[i].equals("Elegir de galeria")){
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/");
                    startActivityForResult(intent.createChooser(intent,"Seleccione"),COD_SELECCIONA);
                }else{
                    dialogInterface.dismiss();
                }
            }
        });

        builder.show();

    }

    private void abrirCamara() {
        File miFile = new File(Environment.getExternalStorageDirectory(),DIRECTORIO_IMAGEN);
        boolean isCreada = miFile.exists();

        if(isCreada==false){
            isCreada=miFile.mkdirs();
        }

        if(isCreada==true){
            Long consecutivo = System.currentTimeMillis()/1000;
            String nombre = consecutivo.toString()+".jpg";

            path = Environment.getExternalStorageDirectory()+File.separator+DIRECTORIO_IMAGEN+File.separator+nombre;
            fileImagen=new File(path);

            Intent intent = null;
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                String authorities = getContext().getPackageName()+".provider";
                Uri imageUri = FileProvider.getUriForFile(getContext(),authorities,fileImagen);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
            }else{
                intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(fileImagen));
            }

            startActivityForResult(intent,COD_FOTO);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case COD_SELECCIONA:
                Uri miPath = data.getData();
                    buttonFoto.setImageURI(miPath);
                    break;



            case COD_FOTO:
                MediaScannerConnection.scanFile(getContext(), new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("Path",""+path);
                    }
                });

                bitmap = BitmapFactory.decodeFile(path);
                buttonFoto.setImageBitmap(bitmap);

                break;

        }

    }

    private void cargarWebService() {
        String url= Total.rutaServidor+"consultarusuariofoto.php?idusuario="+datoDNI;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "No se pudo obtener usuario "+error.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Usuario miuser = new Usuario();

        JSONArray json = response.optJSONArray("usuario");
        JSONObject jsonObject = null;

        try {
            jsonObject=json.getJSONObject(0);
            miuser.setIdUsuario(jsonObject.optString("idusuario"));
            miuser.setUser(jsonObject.optString("usuario"));
            miuser.setContra(jsonObject.optString("contrasenia"));
            miuser.setDireccion(jsonObject.optString("direccion"));
            miuser.setTelefono(jsonObject.optString("telefono"));
            miuser.setCorreo(jsonObject.optString("correo"));
            miuser.setFoto(jsonObject.optString("foto"));


            nombre = miuser.getUser();
            dni = miuser.getIdUsuario();
            direccion = miuser.getDireccion();
            telefono =miuser.getTelefono();
            correo = miuser.getCorreo();
            foto = miuser.getFoto();

            String[] datos = new String[]{
                    nombre,dni,direccion,telefono,correo
            };


            Integer[] icono = new Integer[]{
                    R.drawable.user,
                    R.drawable.dnio,
                    R.drawable.home,
                    R.drawable.phone,
                    R.drawable.email,
            };

            ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
            for(int i=0;i<=datos.length-1;i++){
                HashMap<String,String> map = new HashMap<>();
                map.put("dato",datos[i]);
                map.put("icon",String.valueOf(icono[i]));
                arrayList.add(map);
            }


            UsuarioAdapter usuarioAdapter = new UsuarioAdapter(getActivity(),arrayList);
            listView.setAdapter(usuarioAdapter);

            String ruta= Total.rutaServidor+foto.toString();
            Picasso.get().load(ruta).placeholder(R.drawable.perfilnodisponible)
                    .error(R.drawable.perfilnodisponible)
                    .into(buttonFoto);
            //Picasso.get().load(ruta).into(buttonFoto);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                }
            },800);





        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
