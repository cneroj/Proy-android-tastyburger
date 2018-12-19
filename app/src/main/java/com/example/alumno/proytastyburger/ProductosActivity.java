package com.example.alumno.proytastyburger;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alumno.proytastyburger.Adapters.ProductosAdapter;
import com.example.alumno.proytastyburger.Fragments.CategoriasFragment;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class ProductosActivity extends AppCompatActivity {
    ListView mlvProductos;
    TextView mtxtTitulo;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        mtxtTitulo = findViewById(R.id.txtTitulo);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Cargando.. ");
        progressDialog.setMessage("Por favor espere");
        progressDialog.show();

        Bundle datosRecibidos = getIntent().getExtras();
        mtxtTitulo.setText(datosRecibidos.getString("no"));

        final String codigocategoria = datosRecibidos.getString("co");

        mlvProductos = findViewById(R.id.lvProductos);
        String ruta = Total.rutaServidor + "/consultarproductoscategoria.php";
        //String codigocategoria = "2";

        RequestParams requestParams = new RequestParams();
        requestParams.put("idcategoria",codigocategoria);


        new AsyncHttpClient().get(ruta,requestParams,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                final ArrayList<HashMap<String,String>> arrayList =
                        new ArrayList<>();
                try {
                    for(int i = 0;i <= response.length()-1; i++){
                        HashMap<String,String> map = new HashMap<>();
                        JSONObject jsonObject = null;

                        jsonObject = response.getJSONObject(i);

                        map.put("cod",jsonObject.getString("idproducto"));
                        map.put("nom",jsonObject.getString("nomprod"));
                        map.put("pre",jsonObject.getString("precioprod"));
                        map.put("fot",jsonObject.getString("fotoprod"));
                        map.put("des",jsonObject.getString("desprod"));
                        arrayList.add(map);
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                ProductosAdapter productosAdapter = new ProductosAdapter(
                        ProductosActivity.this,arrayList);
                mlvProductos.setAdapter(productosAdapter);

               /* new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                },1500);*/
                progressDialog.dismiss();


                mlvProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        HashMap<String,String> map = (HashMap<String,String>)arrayList.get(position);

                        String idprod = map.get("cod");
                        String nombreprod = map.get("nom");
                        String precioprod = map.get("pre");
                        String fotoprod = map.get("fot");
                        String descpro = map.get("des");

                        Bundle bundle = new Bundle();
                        bundle.putString("co",idprod);
                        bundle.putString("no",nombreprod);
                        bundle.putString("pr",precioprod);
                        bundle.putString("fo",fotoprod);
                        bundle.putString("de",descpro);


                        Intent intent = new Intent(ProductosActivity.this,DetalleProductoActivity.class);

                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });


            }
        });
    }

    public void onClickRetroceder(View view) {

    }
}