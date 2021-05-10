package com.example.nordboxapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import nordboxcad.EjerciciosBench;
import nordboxcad.NordBoxCADCliente;

public class BenchmarksActivity extends AppCompatActivity implements View.OnClickListener {

    //Para el recycler
    List<ListBench> elements;
    ArrayList<EjerciciosBench> bench;
    ArrayList<Integer> ids;

    //Gif
    LinearLayout layoutAnimado;

    boolean esperaHilo = true;


    //TODO Crear un array de botones, tanto de img como btn, para poder almacenarlos usarlos, segun los que la BD nos indique.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benchmarks);

        initUI();

        recycler();
    }

    /**
     * Metodo usado para crear el recyclerView con los datos obtenidos de la BD.
     */
    public void recycler() {
        elements = new ArrayList<>();
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                //Conectamos con el servidor.
                NordBoxCADCliente nordBoxCAD = new NordBoxCADCliente("10.0.2.2", 30501);

                //Guardo los diferentes tipos de ejercicios.
                bench = nordBoxCAD.ejeBench();
                esperaHilo = false;
            }
        });
        thread.start();

        //Bucle de espera de hilo.
        boolean bucleEspFinHilo = true;
        while (bucleEspFinHilo) {
            if (!esperaHilo) {
                bucleEspFinHilo = false;
            }
        }

        //Introduzco los diferentes ejercicios en una lista
        for (EjerciciosBench ejerciciosBench : bench) {
            ListBench listBench = new ListBench();
            listBench.setId(ejerciciosBench.getId());
            listBench.setName(ejerciciosBench.getNombre());
            listBench.setIcoImagen(Integer.toString(ejerciciosBench.getParteCuerpo()));
            switch (ejerciciosBench.getDificultad()) {
                case 1:
                    listBench.setColor("#02C405");
                    break;

                case 2:
                    listBench.setColor("#D6750D");
                    break;

                case 3:
                    listBench.setColor("#F21B1E");
                    break;

                default:
                    listBench.setColor("#1B1EF2");
            }

            //TODO falra dar valor al ultimo dia de ejercicio y cuantos tiene.
            listBench.setUltimaModificacion("Ultimo dia: 29/01/2021");
            listBench.setnEjerciciosCreados("0");

            elements.add(listBench);
        }

        //Agregamos los elementos al recyclerView
        ListAdapter listAdapter = new ListAdapter(elements, this);
        RecyclerView recyclerView = findViewById(R.id.rvBenchmark);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }

    //TODO Añadir todos los diferentes botones, Los botones se crearan automaticamente, segun los que existan en la BD.
    public void initUI() {
        layoutAnimado = (LinearLayout) findViewById(R.id.layoutAnimado);
        ImageView imageGif = findViewById(R.id.imageGif);
        imageGif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrar();
            }
        });
        Glide.with(getBaseContext()).load(R.drawable.back_squat).into(imageGif);
    }

    /**
     * Metodo para mostrar u ocultar el menu
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_overflow, menu);
        return true;
    }

    /**
     * Metodo para asignar las funciones al menu
     * @param item Clase MenuItem
     * @return super.onOptionsItemSelected(item)
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Intent i = null;

        //TODO, terminar los diferentes intent
        if (id == R.id.overReservar) {
            //TODO Añadir intent de cambio a la activity calendario.
            Toast.makeText(this, getString(R.string.procesoReservar), Toast.LENGTH_LONG).show();
        } else if (id == R.id.overContacto) {
            //TODO Añadir intent de cambio a la activity Contacto.
            Toast.makeText(this, getString(R.string.procesoContacto), Toast.LENGTH_LONG).show();
        } else if (id == R.id.overBenchmarks) {
            i = new Intent(this, BenchmarksActivity.class);
        } else if (id == R.id.overPerfil) {
            //TAñadir intent de cambio a la activity Perfil.
            i = new Intent(this, UsuarioActivity.class);
        } else if (id == R.id.overSalir) {
            //Cerrar la sesion al Salir.
            logoutPerfilValidado();
            i = new Intent(this, Login.class);
        }

        if (i != null) {
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {
        int id = v.getId();

        Intent i = null;

        //TODO Cuando un usuario selecione un ejercicio, llevarle a la activity

        if (i != null) {
            startActivity(i);
        }
    }


//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//
//        getMenuInflater().inflate(R.menu.menu_contextual_mapa, menu);
//    }

    /**
     * Metodo usado para la pulsacion larga.
     * @param item Clase MenuItem
     * @return super.onContextItemSelected(item)
     */
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId())
//        {
//            case R.id.opcion1:
//                Toast.makeText(this, "opcion 1", Toast.LENGTH_SHORT).show();
//                return true;
//            case 4:
//                Toast.makeText(this, "aaaaaaaaaa", Toast.LENGTH_SHORT).show();
//                return true;
//        }
        //Muestra el gif del ejercicio.
        mostrarGif(item.getItemId());

        return super.onContextItemSelected(item);
    }

    //TODO Diferenciar por ejercicio
    /**
     * Metodo usado para mostrar en pantalla el gif del ejercicio seleccionado.
     * @param id Identificacdor del ejercicio a mostrar en el gif
     */
    public void mostrarGif(Integer id) {
        mostrar();
    }
    //Metodo creado para mostrar la imagen gif
    public void mostrar() {
        if (layoutAnimado.getVisibility() == View.INVISIBLE) {
            animar(true);
            layoutAnimado.setVisibility(View.VISIBLE);
        } else {
            layoutAnimado.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Animacion de la salida del gif
     * @param mostrar boleano para mostrar u ocultar.
     */
    private void animar(boolean mostrar) {
        AnimationSet set = new AnimationSet(true);
        Animation animation;
        if (mostrar) {
            //desde la esquina inferior derecha a la superior izquierda
            animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        } else {
            //desde la esquina superior izquierda a la esquina inferior derecha
            animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        }
        //duración en milisegundos
        animation.setDuration(500);
        set.addAnimation(animation);
        LayoutAnimationController controller = new LayoutAnimationController(set, 0.25f);

        layoutAnimado.setLayoutAnimation(controller);
        layoutAnimado.startAnimation(animation);
    }

    /**
     * Metodo usado para desloguarse, con el quitas el inicio de sesion automatico.
     */
    public void logoutPerfilValidado() {
        SharedPreferences preferences = getSharedPreferences("perfilValidado", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharePreference = preferences.edit();
        sharePreference.putInt("perfilValidado", -1);
        sharePreference.apply();
    }
}