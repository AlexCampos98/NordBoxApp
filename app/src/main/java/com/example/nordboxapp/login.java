package com.example.nordboxapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class login extends AppCompatActivity implements View.OnClickListener {

    TextView tvLoginError;
    EditText etEmailLogin, etPasswordLogin;
    Button btnLogin;
    usuario idUsuario = new usuario();

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        requestQueue = Volley.newRequestQueue(this);

        //Inicializado de componentes
        initUI();

        btnLogin.setOnClickListener(this);
    }

    //Metodo creado para inicializar todos los componentes de la activity
    private void initUI() {
        etEmailLogin = findViewById(R.id.etEmailLogin);
        etPasswordLogin = findViewById(R.id.etPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        tvLoginError = findViewById(R.id.tvLoginError);
    }

    //Metodo que captura el click
    public void onClick(View v) {
        int id = v.getId();
        //Si se ha pulsado el boton de login
        if (id == R.id.btnLogin) {

//            String email = (String) etEmailLogin.getText().toString().trim();
//            String password = (String) etPasswordLogin.getText().toString().trim();

            //TODO Revisar el verificacionLogin, cuando inicio sesion la primera vez da error, pero la segunda entra por el if.
//            verificacionLogin(email, password);

//            if (idUsuario.getEmail() != null) {
                Intent i = new Intent(this, menuActivity.class);
//                i.putExtra("idUsuario", idUsuario.getEmail());
                startActivity(i);
//            } else {
//                tvLoginError.setVisibility(View.VISIBLE);
//            }
        }
    }

    //TODO Revisar verificacionLogin, problema.
    private void verificacionLogin(String email, String password) {
        //Instituto TODO cambiara cada vez que este en clase
//        String URL = "http://172.16.5.222/android/login.php?email=" + email + "&password=" + password;

        //Casa TODO
        String URL = "http://192.168.1.254/android/login.php?email=" + email + "&password=" + password;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String email;
                        try {
                            email = response.getString("email");

                            idUsuario = new usuario(email);
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
}