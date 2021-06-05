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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnReservar, btnContacto, btnBenchmarks, btnPerfil, btnSalir;
    TextView tvContacto;

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
        tvContacto = findViewById(R.id.tvContacto);
        btnBenchmarks = findViewById(R.id.btnBenchmarks);
        btnBenchmarks.setOnClickListener(this);
        btnPerfil = findViewById(R.id.btnPerfil);
        btnPerfil.setOnClickListener(this);
        btnSalir = findViewById(R.id.btnSalir);
        btnSalir.setOnClickListener(this);

        //TODO Si es administrador se cambia a boton de administracion
        if(true){
            tvContacto.setText(getString(R.string.tituloAdministracion));
            btnContacto.setImageResource(R.drawable.ic_add_photo_small);
        }
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
            i = new Intent(this, EventoActivity.class);
//            mensajeEmergente(v, R.string.procesoReservar);
        } else if (id == R.id.btnContacto) {
            //TODO Añadir intent de cambio a la activity Contacto. Si es administrador o no
            if(true){
                i = new Intent(this, MenuAdministrador.class);
            } else {
                mensajeEmergente(v, R.string.procesoContacto);
            }
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