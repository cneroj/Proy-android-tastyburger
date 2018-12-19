package com.example.alumno.proytastyburger;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.alumno.proytastyburger.Entidades.Usuario;
import com.example.alumno.proytastyburger.Fragments.UsuarioFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{
    EditText mtxtuser,mtxtcontra;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    Button mbtn;
    String iduser;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Cargando.. ");
        progressDialog.setMessage("Por favor espere");
        mbtn = findViewById(R.id.btnEntrar);
        mtxtuser = findViewById(R.id.txtuser);
        mtxtcontra = findViewById(R.id.txtcontra);


        request= Volley.newRequestQueue(getApplicationContext());


    }

    public void onClickEntrar(View view) {
        if (mtxtuser.getText().toString().isEmpty() || mtxtcontra.getText().toString().isEmpty()) {
            if(mtxtuser.getText().toString().isEmpty()){
                mtxtuser.setError("Campo en blanco");
                mtxtuser.requestFocus();
            }
            if(mtxtcontra.getText().toString().isEmpty()){
                mtxtcontra.setError("Campo en blanco");
                mtxtcontra.requestFocus();
            }
        }else{
            progressDialog.show();


                    cargarWebService();
        }

    }

    private void cargarWebService() {


            String user = mtxtuser.getText().toString();

            String url=Total.rutaServidor+"consultarusuario.php?idusuario="+user;
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
            request.add(jsonObjectRequest);





    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "No se pudo obtener usuario, Error de Conexion. "+error.toString(), Toast.LENGTH_SHORT).show();
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


            if(miuser.getIdUsuario().equals("null")){
                progressDialog.dismiss();
                mtxtuser.setError("Usuario incorrecto");
                mtxtuser.requestFocus();
            }else{
                if(miuser.getContra().equals(mtxtcontra.getText().toString())){

                    iduser = mtxtuser.getText().toString();


                            Bundle bundle = new Bundle();
                            bundle.putString("iduser",iduser);
                            Intent intent = new Intent(getApplicationContext(),NavigationActivity.class);
                            intent.putExtras(bundle);

                            startActivity(intent);
                    progressDialog.dismiss();





                }else{
                    progressDialog.dismiss();
                    mtxtcontra.setError("Contraseña incorrecta");
                    mtxtcontra.requestFocus();
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
