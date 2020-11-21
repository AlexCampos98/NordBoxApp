package com.example.nordboxapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class benchmarksActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout layoutAnimado;
    ImageButton btnBack_Squat_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benchmarks);

        initUI();

    }

    public void initUI() {
        btnBack_Squat_info = (ImageButton) findViewById(R.id.btnBack_Squat_info);
        btnBack_Squat_info.setOnClickListener(this);

        ImageView imageView2 = findViewById(R.id.imageView2);
        Glide.with(getBaseContext()).load(R.drawable.back_squat).into(imageView2);

        layoutAnimado = (LinearLayout) findViewById(R.id.layoutAnimado);
    }

    //Metodo para mostrar y ocultar el menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_overflow, menu);
        return true;
    }

    //Metodopara asignar las funciones al menu
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Intent i = null;

        //TODO, terminar los diferentes intent
        switch (id) {
            case R.id.overReservar:
                //TODO Añadir intent de cambio a la activity calendario.
                break;

            case R.id.overContacto:
                //TODO Añadir intent de cambio a la activity Contacto.
                break;

            case R.id.overBenchmarks:
                i = new Intent(this, benchmarksActivity.class);
                break;

            case R.id.overPerfil:
                //TODO Añadir intent de cambio a la activity Perfil.
                break;

            case R.id.overSalir:
                //TODO Cerrar la sesion al Salir.
                i = new Intent(this, login.class);
                break;
        }

        startActivity(i);
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btnBack_Squat_info) {
            mostrar(v);
//            ocultar(v);
        }
    }

    //Metodo creado para mostrar la imagen gif
    public void mostrar(View button) {
        if (layoutAnimado.getVisibility() == View.GONE) {
            animar(true);
            layoutAnimado.setVisibility(View.VISIBLE);
        } else {
            animar(false);
            layoutAnimado.setVisibility(View.GONE);
        }
    }

//    //Metodo creado para ocultar el gif
//    public void ocultar(View button) {
//        if (layoutAnimado.getVisibility() == View.VISIBLE) {
//            animar(false);
//            layoutAnimado.setVisibility(View.GONE);
//        }
//
//    }

    //Animacion de la salida del gif
    private void animar(boolean mostrar) {
        AnimationSet set = new AnimationSet(true);
        Animation animation = null;
        if (mostrar) {
            //desde la esquina inferior derecha a la superior izquierda
            animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        } else {    //desde la esquina superior izquierda a la esquina inferior derecha
            animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        }
        //duración en milisegundos
        animation.setDuration(500);
        set.addAnimation(animation);
        LayoutAnimationController controller = new LayoutAnimationController(set, 0.25f);

        layoutAnimado.setLayoutAnimation(controller);
        layoutAnimado.startAnimation(animation);
    }
}