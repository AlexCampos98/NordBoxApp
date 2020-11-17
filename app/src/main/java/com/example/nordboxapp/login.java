package com.example.nordboxapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    usuario idUsuario;

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

            String email = etEmailLogin.getText().toString().trim();
            String password = etPasswordLogin.getText().toString().trim();

            if(verificacionLogin(email, password)) {
                idUsuario.getEmail();
            }
            //Poner en caso de error. Despues de verificar si el usuario es correcto.
            //tvLoginError.setVisibility(View.VISIBLE);
        }
    }

    private boolean verificacionLogin(String email, String password){
        final boolean[] verificado = new boolean[1];

        //Instituto TODO cambiara cada vez que este en clase
        String URL = "http://172.16.5.222/android/login.php?email=" + email + "&password=" + password;

        //Casa TODO
        //String URL = "http://192.168.1.254/android/login.php?email=" + email + "&password=" + password;
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
                            verificado[0] =true;
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        verificado[0] =false;
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
        return verificado[0];
    }
}