package com.example.nordboxapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class menuActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnReservar, btnContacto, btnBenchmarks, btnPerfil, btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initUI();


    }

    public void initUI() {
        btnReservar = findViewById(R.id.btnReservar);
        btnReservar.setOnClickListener(this);
        btnContacto = findViewById(R.id.btnContacto);
        btnContacto.setOnClickListener(this);
        btnBenchmarks = findViewById(R.id.btnBenchmarks);
        btnBenchmarks.setOnClickListener(this);
        btnPerfil = findViewById(R.id.btnPerfil);
        btnPerfil.setOnClickListener(this);
        btnSalir = findViewById(R.id.btnSalir);
        btnSalir.setOnClickListener(this);
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
        if (id == R.id.overReservar) {
            //TODO Añadir intent de cambio a la activity calendario.
            Toast.makeText(this,"Opcion Calendario en proceso",Toast.LENGTH_LONG).show();
        } else if (id == R.id.overContacto) {
            //TODO Añadir intent de cambio a la activity Contacto.
            Toast.makeText(this,"Opcion Contacto en proceso",Toast.LENGTH_LONG).show();
        } else if (id == R.id.overBenchmarks) {
            i = new Intent(this, benchmarksActivity.class);
        } else if (id == R.id.overPerfil) {
            //TODO Añadir intent de cambio a la activity Perfil.
            i = new Intent(this, usuarioActivity.class);
        } else if (id == R.id.overSalir) {
            //TODO Cerrar la sesion al Salir.
            i = new Intent(this, login.class);
        }

        if (i != null) {
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    //Metodo que captura el click
    public void onClick(View v) {
        int id = v.getId();

        Intent i = null;

        if (id == R.id.btnReservar) {
            //TODO Añadir intent de cambio a la activity calendario.
            Toast.makeText(this,"Opcion Calendario en proceso",Toast.LENGTH_LONG).show();
        } else if (id == R.id.btnContacto) {
            //TODO Añadir intent de cambio a la activity Contacto.
            Toast.makeText(this,"Opcion Contacto en proceso",Toast.LENGTH_LONG).show();
        } else if (id == R.id.btnBenchmarks) {
            i = new Intent(this, benchmarksActivity.class);
        } else if (id == R.id.btnPerfil) {
            //TODO Añadir intent de cambio a la activity Perfil.
            i = new Intent(this, usuarioActivity.class);
        } else if (id == R.id.btnSalir) {
            //TODO Cerrar la sesion al Salir.
            i = new Intent(this, login.class);
        }

        if (i != null) {
            startActivity(i);
        }
    }
}