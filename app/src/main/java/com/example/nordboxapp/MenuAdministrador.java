package com.example.nordboxapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.content.Intent;

public class MenuAdministrador extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnCrearEvento, btnCrearUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_administrador);

        initUI();
    }

    public void initUI() {
        btnCrearEvento = findViewById(R.id.btnCrearEvento);
        btnCrearEvento.setOnClickListener(this);
        btnCrearUsuario = findViewById(R.id.btnCrearUsuario);
        btnCrearUsuario.setOnClickListener(this);
    }

    public void onClick(View v) {
        int id = v.getId();

        Intent i = null;

        if(id == R.id.btnCrearUsuario){
            i = new Intent(this, CreacionUsuario.class);
        } else if(id == R.id.btnCrearEvento){

            i = new Intent(this, CreacionEvento.class);
        }

        if (i != null) {
            startActivity(i);
        }
    }
}