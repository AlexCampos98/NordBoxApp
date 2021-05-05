package com.example.nordboxapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreacionUsuario extends AppCompatActivity {

    EditText etEmailCreacionUsu;
    Button btnCreacionUsu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creacion_usuario);

        initUI();
    }

    private void initUI() {
        etEmailCreacionUsu = findViewById(R.id.etEmailCreacionUsu);
        btnCreacionUsu = findViewById(R.id.btnCreacionUsu);
    }

    //Metodo que captura el click
    public void onClick(View v) {
        int id = v.getId();
        //Si se ha pulsado el boton de login
        if (id == R.id.btnCreacionUsu) {
            //TODO Crear la creacion del usuario, que envie al servidor el email, luego el servidor enviara un correo.
            String email = etEmailCreacionUsu.getText().toString();

            //Todo indicar al usuario que se ha enviado con exito. Esto que nos lo indique el servidor, por el mismo socket.
        }
    }
}