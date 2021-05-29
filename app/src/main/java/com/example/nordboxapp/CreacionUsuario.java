package com.example.nordboxapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import nordboxcad.ExcepcionNordBox;
import nordboxcad.NordBoxCADCliente;
import nordboxcad.Usuario;

public class CreacionUsuario extends AppCompatActivity implements View.OnClickListener {

    EditText etEmailCreacionUsu;
    Button btnCreacionUsu;

    //Clase para guardar los datos del usuario durante el uso de la aplicacion.
    UsuarioStatico usuarioStatico = new UsuarioStatico();
    boolean esperaHilo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creacion_usuario);

        initUI();
    }

    private void initUI() {
        etEmailCreacionUsu = findViewById(R.id.etEmailCreacionUsu);
        btnCreacionUsu = findViewById(R.id.btnCreacionUsu);
        btnCreacionUsu.setOnClickListener(this);
    }

    //Metodo que captura el click
    public void onClick(View v) {
        int id = v.getId();
        //Si se ha pulsado el boton de login
        if (id == R.id.btnCreacionUsu) {
            String email = etEmailCreacionUsu.getText().toString();

            final Usuario usuario = new Usuario();
            usuario.setCorreo(email);

            //Inicio un hilo para comprobar que el usuario este en la BD.
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    //Conectamos con el servidor.
                    NordBoxCADCliente nordBoxCAD = new NordBoxCADCliente("10.0.2.2", 30501);

                    try {
                        nordBoxCAD.crearUsuario(usuario);
                    } catch (ExcepcionNordBox excepcionNordBox) {
                        excepcionNordBox.printStackTrace();
                    }

                    //Cambio el valor del controlador de espera de hilo.
                    esperaHilo = true;
                }
            });
            thread.start();

            //Bucle que espera a que el hilo termine.
            boolean bucleEspFinHilo = true;
            while (bucleEspFinHilo) {
                if (esperaHilo) {
                    bucleEspFinHilo = false;
                }
            }

            //Todo indicar al usuario que se ha enviado con exito. Esto que nos lo indique el servidor, por el mismo socket.
        }
    }
}