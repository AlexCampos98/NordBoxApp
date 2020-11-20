package com.example.nordboxapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class menuActivity extends AppCompatActivity implements View.OnClickListener{

    ImageButton btnReservar, btnContacto, btnBenchmarks;
    Button  btnPerfil, btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initUI();

    }

    public void initUI() {
        btnReservar = (ImageButton) findViewById(R.id.btnReservar);
        btnReservar.setOnClickListener(this);
        btnContacto = (ImageButton) findViewById(R.id.btnContacto);
        btnContacto.setOnClickListener(this);
        btnBenchmarks = (ImageButton) findViewById(R.id.btnBenchmarks);
        btnBenchmarks.setOnClickListener(this);
        btnPerfil = (Button) findViewById(R.id.btnPerfil);
        btnPerfil.setOnClickListener(this);
        btnSalir = (Button) findViewById(R.id.btnSalir);
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

        }

        startActivity(i);
        return super.onOptionsItemSelected(item);
    }

    //Metodo que captura el click
    public void onClick(View v) {
        int id = v.getId();

        Intent i = null;

        switch (id) {
            case R.id.btnReservar:
                //TODO Añadir intent de cambio a la activity calendario.
                Toast.makeText(this, "aaaaa", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnContacto:
                //TODO Añadir intent de cambio a la activity Contacto.
                break;

            case R.id.btnBenchmarks:
                //TODO Añadir intent de cambio a la activity Benchmarks.
                break;

            case R.id.btnPerfil:
                //TODO Añadir intent de cambio a la activity Perfil.
                break;

            case R.id.btnSalir:
                //TODO Cerrar la sesion al Salir.
                i = new Intent(this, login.class);
                break;
        }

        startActivity(i);
    }
}