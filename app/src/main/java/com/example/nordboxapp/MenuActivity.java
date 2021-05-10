package com.example.nordboxapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

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

    /**
     * Metodo para mostrar u ocultar el menu
     * @param menu Clase menu
     * @return True siempre
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_overflow, menu);
        return true;
    }

    /**
     * Metodo para asignar las funciones al menu
     * @param item Clase MenuItem
     * @return super.onOptionsItemSelected(item)
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Intent i = null;

        //TODO, terminar los diferentes intent
        if (id == R.id.overReservar) {
            //TODO Añadir intent de cambio a la activity calendario.
            Toast.makeText(this, getString(R.string.procesoReservar), Toast.LENGTH_LONG).show();
        } else if (id == R.id.overContacto) {
            //TODO Añadir intent de cambio a la activity Contacto.
            Toast.makeText(this, getString(R.string.procesoContacto), Toast.LENGTH_LONG).show();
        } else if (id == R.id.overBenchmarks) {
            i = new Intent(this, BenchmarksActivity.class);
        } else if (id == R.id.overPerfil) {
            //Añadir intent de cambio a la activity Perfil.
            i = new Intent(this, UsuarioActivity.class);
        } else if (id == R.id.overSalir) {
            //Cerrar la sesion al Salir.
            logoutPerfilValidado();
            i = new Intent(this, Login.class);
        }

        if (i != null) {
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Metodo que captura el click
     * @param v Clase View, con la vista actual.
     */
    public void onClick(View v) {
        int id = v.getId();

        Intent i = null;

        if (id == R.id.btnReservar) {
            //TODO Añadir intent de cambio a la activity calendario.
            mensajeEmergente(v, R.string.procesoReservar);
        } else if (id == R.id.btnContacto) {
            //TODO Añadir intent de cambio a la activity Contacto.
            mensajeEmergente(v , R.string.procesoContacto);
        } else if (id == R.id.btnBenchmarks) {
            i = new Intent(this, BenchmarksActivity.class);
        } else if (id == R.id.btnPerfil) {
            //Añadir intent de cambio a la activity Perfil.
            i = new Intent(this, UsuarioActivity.class);
        } else if (id == R.id.btnSalir) {
            //Cerrar la sesion al Salir.
            logoutPerfilValidado();
            i = new Intent(this, Login.class);
        }

        if (i != null) {
            startActivity(i);
        }
    }

    /**
     * TODO no recuero para que es esto, probarlo y añadir descripcion.
     * @param v Clase View, con la vista actual.
     * @param mensaje a
     */
    private void mensajeEmergente(View v, int mensaje) {
        Snackbar snackbar = Snackbar.make(v, mensaje, Snackbar.LENGTH_LONG);
        snackbar.setDuration(2000);
        snackbar.show();
        snackbar.show();
    }

    /**
     * Metodo usado para desloguarse, con el quitas el inicio de sesion automatico.
     */
    private void logoutPerfilValidado() {
        SharedPreferences preferences = getSharedPreferences("perfilValidado", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharePreference = preferences.edit();
        sharePreference.putInt("perfilValidado", -1);
        sharePreference.apply();
    }
}