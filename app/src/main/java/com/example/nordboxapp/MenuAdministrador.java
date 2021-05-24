package com.example.nordboxapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MenuAdministrador extends AppCompatActivity {

    ImageButton btnCrearEvento, btnCrearUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_administrador);

        initUI();
    }

    public void initUI() {
        btnCrearEvento = findViewById(R.id.btnCrearEvento);
        btnCrearEvento.setOnClickListener((View.OnClickListener) this);
        btnCrearUsuario = findViewById(R.id.btnCrearUsuario);
        btnCrearUsuario.setOnClickListener((View.OnClickListener) this);
    }

    public void onClick(View v) {
        int id = v.getId();

        Intent i = null;

        if(id == R.id.btnCrearEvento){
            i = new Intent(this, CreacionUsuario.class);
        } else if(id == R.id.btnCrearUsuario){
            //TODO Terminar y poner la activity de creacion de evento.
            //i = new Intent(this, BenchmarksActivity.class);
        }

        if (i != null) {
            startActivity(i);
        }
    }
}