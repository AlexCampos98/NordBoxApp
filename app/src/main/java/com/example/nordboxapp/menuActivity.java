package com.example.nordboxapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class menuActivity extends AppCompatActivity {
    TextView a;
    usuario idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initUI();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idUsuario = new usuario(extras.getString("idUsuario"));
            a.setText(idUsuario.getEmail());
        }

    }

    private void initUI() {
        a = (TextView) findViewById(R.id.textView2);
    }
}