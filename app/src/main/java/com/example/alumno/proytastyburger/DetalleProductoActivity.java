package com.example.alumno.proytastyburger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DetalleProductoActivity extends AppCompatActivity {
    TextView mtxtNombreProd,mtxtPrecioProd,mtxtDescripProd,mtxtTitulo;

    String idProducto,nombreProducto,precioProducto,fotoProducto,desProducto;
    ImageView mimagenProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);

        mtxtDescripProd = findViewById(R.id.txtDescripcionProducto);
        mtxtNombreProd = findViewById(R.id.txtNombreProducto);
        mtxtPrecioProd = findViewById(R.id.txtPrecioProducto);
        mimagenProducto = findViewById(R.id.imgFotoProducto);
        mtxtTitulo = findViewById(R.id.txtTitulo);

        Bundle datosRecibidos = getIntent().getExtras();
        idProducto = datosRecibidos.getString("co");
        nombreProducto = datosRecibidos.getString("no");
        precioProducto = datosRecibidos.getString("pr");
        fotoProducto = datosRecibidos.getString("fo");
        desProducto = datosRecibidos.getString("de");

        mtxtTitulo.setText(nombreProducto);

        mtxtNombreProd.setText(nombreProducto);
        mtxtDescripProd.setText(desProducto);
        mtxtPrecioProd.setText(precioProducto);

        String ruta = Total.rutaServidor + fotoProducto;

        Picasso.get().load(ruta).into(mimagenProducto);


    }

    public void onClickRetroceder(View view) {

    }
}
