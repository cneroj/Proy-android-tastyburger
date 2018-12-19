package com.example.alumno.proytastyburger;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.alumno.proytastyburger.Entidades.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegistroActivity extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{

    EditText mtxtuser,mtxtcontra,mtxtconfirmarcontra,mtxttelefono,mtxtdireccion,mtxtcorreo,mtxtdni;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    ProgressDialog progressDialog;
    Boolean confirmarUsuario=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mtxtuser = findViewById(R.id.txtUsuario);
        mtxtdni = findViewById(R.id.txtDNI);
        mtxtcontra = findViewById(R.id.txtContraseña);
        mtxtconfirmarcontra = findViewById(R.id.txtConfirmarContraseña);
        mtxttelefono = findViewById(R.id.txtTelefono);
        mtxtdireccion = findViewById(R.id.txtDireccion);
        mtxtcorreo = findViewById(R.id.txtCorreoElectronico);
        progressDialog = new ProgressDialog(RegistroActivity.this);
        progressDialog.setTitle("Cargando.. ");
        progressDialog.setMessage("Por favor espere");

        request= Volley.newRequestQueue(getApplicationContext());

        mtxtdni.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(mtxtdni.length()<8){
                    mtxtdni.setError("Necesita 8 digitos");
                    mtxtdni.requestFocus();
                }
            }
        });
    }


    public void onClickRegistrar(View view) {
        if(mtxtuser.getText().toString().isEmpty() ||
                mtxtcontra.getText().toString().isEmpty() ||
                mtxtdni.getText().toString().isEmpty() ||
                mtxttelefono.getText().toString().isEmpty()){

            if(mtxtuser.getText().toString().isEmpty()){
                mtxtuser.setError("Campo obligatorio");
                mtxtuser.requestFocus();
            }
            if(mtxtcontra.getText().toString().isEmpty()){
                mtxtcontra.setError("Campo obligatorio");
                mtxtcontra.requestFocus();
            }
            if(mtxtdni.getText().toString().isEmpty()){
                mtxtdni.setError("Campo obligatorio");
                mtxtdni.requestFocus();
            }
            if(mtxtdireccion.getText().toString().isEmpty()){
                mtxtdireccion.setError("Campo obligatorio");
                mtxtdireccion.requestFocus();
            }
            if(mtxttelefono.getText().toString().isEmpty()){
                mtxttelefono.setError("Campo obligatorio");
                mtxttelefono.requestFocus();
            }

            if(mtxtcorreo.getText().toString().isEmpty()){
                mtxtdireccion.setError("Campo obligatorio");
                mtxtdireccion.requestFocus();
            }


        }else{

                if(mtxtcontra.getText().toString().equals(mtxtconfirmarcontra.getText().toString())){
                    progressDialog.show();
                    consultarUsuario();
                    if(confirmarUsuario==true){
                        registrarUsuario();
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                progressDialog.dismiss();
                                Toast.makeText(RegistroActivity.this, "Usuario Registrado Correctamente", Toast.LENGTH_SHORT).show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                                        finish();
                                    }
                                }, 2100);
                            }
                        }, 2000);
                    }else{
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                progressDialog.dismiss();
                                mtxtdni.setError("DNI Registrado");
                                mtxtdni.requestFocus();
                            }
                        }, 2000);
                    }

                }else {
                    mtxtconfirmarcontra.requestFocus();
                    mtxtconfirmarcontra.setError("Contraseñas diferentes");
                }


        }
    }

    private void registrarUsuario() {
        String user=mtxtuser.getText().toString();
        String dni=mtxtdni.getText().toString();
        String contra=mtxtcontra.getText().toString();
        String confirContra = mtxtconfirmarcontra.getText().toString();
        String telefono=mtxttelefono.getText().toString();
        String direccion=mtxtdireccion.getText().toString();
        String correo=mtxtcorreo.getText().toString();

        String url=Total.rutaServidor+"registrousuario.php?idusuario="+dni+"&usuario="+user+"&contrasenia="+contra+
                "&direccion="+direccion+"&telefono="+telefono+"&correo="+correo;
        url = url.replace(" ","%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);

    }

    private void consultarUsuario(){
        String user = mtxtdni.getText().toString();

        String url=Total.rutaServidor+"consultarusuario.php?idusuario="+user;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "No se pudo registrar "+error.toString(), Toast.LENGTH_SHORT).show();
        Log.i("Error", error.toString());
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

            if(miuser.getIdUsuario().equals("null")) {
               confirmarUsuario = true;
            }else{
                confirmarUsuario=false;
            }

            } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
}
