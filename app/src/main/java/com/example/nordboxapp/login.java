package com.example.nordboxapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class login extends AppCompatActivity implements View.OnClickListener {

    TextView tvLoginError;
    EditText etEmailLogin, etPasswordLogin;
    Button btnLogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Inicializado de componentes
        initUI();
    }

    //Metodo creado para inicializar todos los componentes de la activity
    private void initUI(){
        etEmailLogin = (EditText) findViewById(R.id.etEmailLogin);
        etPasswordLogin = (EditText) findViewById(R.id.etPasswordLogin);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        tvLoginError = (TextView) findViewById(R.id.tvLoginError);
    }

    //Metodo que captura el click
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.btnLogin){


            //Poner en caso de error. Despues de verificar si el usuario es correcto.
            //tvLoginError.setVisibility(View.VISIBLE);
        }
    }

    private void verificacionLogin(){
        //JsonObjectRequest


    }
}