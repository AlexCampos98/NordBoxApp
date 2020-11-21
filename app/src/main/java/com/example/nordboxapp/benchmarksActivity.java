package com.example.nordboxapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class benchmarksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benchmarks);

        ImageView imageView2 = findViewById(R.id.imageView2);
        Glide.with(getBaseContext()).load(R.drawable.back_squat).into(imageView2);
    }

    //Metodo para mostrar y ocultar el menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_overflow, menu);
        return true;
    }

    //Metodopara asignar las funciones al menu
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Intent i = null;

        //TODO, terminar los diferentes intent
        switch (id) {
            case R.id.overReservar:
                //TODO Añadir intent de cambio a la activity calendario.
                break;

            case R.id.overContacto:
                //TODO Añadir intent de cambio a la activity Contacto.
                break;

            case R.id.overBenchmarks:
                i = new Intent(this, benchmarksActivity.class);
                break;

            case R.id.overPerfil:
                //TODO Añadir intent de cambio a la activity Perfil.
                break;

            case R.id.overSalir:
                //TODO Cerrar la sesion al Salir.
                i = new Intent(this, login.class);
                break;
        }

        startActivity(i);
        return super.onOptionsItemSelected(item);
    }
}