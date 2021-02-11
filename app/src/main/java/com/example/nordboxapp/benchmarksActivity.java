package com.example.nordboxapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nordboxcad.EjerciciosBench;
import nordboxcad.NordBoxCADCliente;
import nordboxcad.Usuario;

public class benchmarksActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout layoutAnimado;

    Button btnBack_Squat;
    ImageButton btnBack_Squat_info;

    //Para el recycler
    List<ListBench> elements;
    ArrayList<EjerciciosBench> bench;
    boolean esperaHilo = true;


    //TODO Crear un array de botones, tanto de img como btn, para poder almacenarlos usarlos, segun los que la BD nos indique.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benchmarks);

        initUI();

        recycler();
    }

    public void recycler() {
        elements = new ArrayList<>();
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                NordBoxCADCliente nordBoxCAD = new NordBoxCADCliente("10.0.2.2", 30501);

                bench = nordBoxCAD.ejeBench();
                //FIXME A partir de aqui no llega, casca en el bench = nordBoxCAD.ejeBench();
//                if (idUsuario.getId() != null) {
//                    iniciarI = true;
//                } else {
//                    iniciarI = false;
//                }
                esperaHilo = false;
            }
        });
        thread.start();
        boolean bucleEspFinHilo = true;
        while (bucleEspFinHilo) {
            if (!esperaHilo) {
                bucleEspFinHilo = false;
            }
        }
        Iterator<EjerciciosBench> iterator = bench.iterator();
        while (iterator.hasNext()) {
            EjerciciosBench ejerciciosBench = iterator.next();
            ListBench listBench = new ListBench();
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


            listBench.setUltimaModificacion("Ultimo dia: 29/01/2021");
            listBench.setnEjerciciosCreados("0");

            elements.add(listBench);
        }

        ListAdapter listAdapter = new ListAdapter(elements, this);
        RecyclerView recyclerView = findViewById(R.id.rvBenchmark);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }

    //TODO Añadir todos los diferentes botones, Los botones se crearan automaticamente, segun los que existan en la BD.
    public void initUI() {
        btnBack_Squat_info = findViewById(R.id.btnBack_Squat_info);
        btnBack_Squat_info.setOnClickListener(this);

        btnBack_Squat = findViewById(R.id.btnBack_Squat);
        btnBack_Squat.setOnClickListener(this);

        //TODO Pensar como poder añadir los diferentes gif, segun que boton han pulsado. En la BD, se almacenara la direccion del gif.
        ImageView imageGif = findViewById(R.id.imageGif);
        Glide.with(getBaseContext()).load(R.drawable.back_squat).into(imageGif);

        //Iniciado el layout que hara aparecer el gif de informacion.
        layoutAnimado = findViewById(R.id.layoutAnimado);
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
        if (id == R.id.overReservar) {
            //TODO Añadir intent de cambio a la activity calendario.
            Toast.makeText(this, "Opcion Calendario en proceso", Toast.LENGTH_LONG).show();
        } else if (id == R.id.overContacto) {
            //TODO Añadir intent de cambio a la activity Contacto.
            Toast.makeText(this, "Opcion Contacto en proceso", Toast.LENGTH_LONG).show();
        } else if (id == R.id.overBenchmarks) {
            i = new Intent(this, benchmarksActivity.class);
        } else if (id == R.id.overPerfil) {
            //TODO Añadir intent de cambio a la activity Perfil.
            i = new Intent(this, usuarioActivity.class);
        } else if (id == R.id.overSalir) {
            //TODO Cerrar la sesion al Salir.
            i = new Intent(this, login.class);
        }

        if (i != null) {
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {
        int id = v.getId();

        Intent i = null;

        if (id == R.id.btnBack_Squat_info) {
            mostrar(v);
            //TODO, segun la opcion del usuario, se añadira un gif a 'R.id.imageGif'
        }

        if (id == R.id.btnBack_Squat) {
            i = new Intent(this, benchmarkEjercicioActivity.class);

        }

        if (i != null) {
            startActivity(i);
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

    //Animacion de la salida del gif
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
}