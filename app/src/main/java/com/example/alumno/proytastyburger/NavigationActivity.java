package com.example.alumno.proytastyburger;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alumno.proytastyburger.Fragments.AcercaAppFragment;
import com.example.alumno.proytastyburger.Fragments.CarritoFragment;
import com.example.alumno.proytastyburger.Fragments.CategoriasFragment;
import com.example.alumno.proytastyburger.Fragments.InicioFragment;
import com.example.alumno.proytastyburger.Fragments.OrdenesFragment;
import com.example.alumno.proytastyburger.Fragments.UsuarioFragment;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView textView;
    ImageView imageView;
    String iduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        InicioFragment fragment = new InicioFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        textView = findViewById(R.id.txtTitulo);
        textView.setText("Sobre Nosotros");
        imageView =findViewById(R.id.idImagen);

        //Recibiendo de Login
        Bundle datosRecibidos = getIntent().getExtras();
        iduser = datosRecibidos.getString("iduser");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        boolean fragmentSeleccion = false;
        android.support.v4.app.Fragment fragment = null;



        if (id == R.id.nav_camera) {
            fragment = new InicioFragment();
            fragmentSeleccion = true;
            textView.setText("Sobre Nosotros");
            imageView.setImageResource(R.drawable.iconx128);
        } else if (id == R.id.nav_gallery) {
            fragment = new UsuarioFragment();
            fragmentSeleccion = true;
            textView.setText("Datos del Usuario");
            imageView.setImageResource(R.drawable.usercolor);

            Bundle bundle = new Bundle();
            bundle.putString("id",iduser);
            fragment.setArguments(bundle);

        } else if (id == R.id.nav_slideshow) {
            fragment = new CategoriasFragment();
            fragmentSeleccion = true;
            textView.setText("Productos");
            imageView.setImageResource(R.drawable.products);

        } else if (id == R.id.nav_manage) {
            fragment = new CarritoFragment();
            fragmentSeleccion = true;
            textView.setText("Carrito de Compras");
            imageView.setImageResource(R.drawable.cartcolor);

        } else if (id == R.id.nav_share) {
            fragment = new OrdenesFragment();
            fragmentSeleccion = true;
            textView.setText("Ordenes");
            imageView.setImageResource(R.drawable.listcolor);
        }
        else if (id == R.id.nav_send) {
            fragment = new AcercaAppFragment();
            fragmentSeleccion = true;
            textView.setText("Sobre la App");
            imageView.setImageResource(R.drawable.problems);
        }else if(id==R.id.nav_salir){
            startActivity(new Intent(this,MainActivity.class));
        }



        if(fragmentSeleccion==true){
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
