package com.example.alumno.proytastyburger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void onClickIngresar(View view) {
        startActivity(new Intent(this,LoginActivity.class));
    }

    public void onClickRegistrar(View view) {
        startActivity(new Intent(this,RegistroActivity.class));
    }
}
