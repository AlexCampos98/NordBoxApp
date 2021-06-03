package com.example.nordboxapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

import nordboxcad.EjercicioBenchUsuario;
import nordboxcad.EjerciciosBench;
import nordboxcad.NordBoxCADCliente;

public class BenchmarkEjercicioActivity extends AppCompatActivity implements View.OnClickListener{

    UsuarioStatico usuarioStatico = new UsuarioStatico();

    Integer idEjercicio;
    TableLayout tablaEjerciciosBench;
    Button btnEjercicioEnviar;
    EjercicioBenchUsuario ejerciciosUsuario;
    ArrayList<EjercicioBenchUsuario> ejercicioBenchUsuarios;

    boolean esperaHilo = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benchmark_ejercicio);

        idEjercicio = (Integer) getIntent().getExtras().get("id");
        ejerciciosUsuario = new EjercicioBenchUsuario(0, idEjercicio, usuarioStatico.getUsuario().getId(), null, null, null);
        InitUI();

        agregarFilaTabla();
    }

    private void InitUI() {
        tablaEjerciciosBench = findViewById(R.id.tablaEjerciciosBench);
        btnEjercicioEnviar = findViewById(R.id.btnEjercicioEnviar);
        btnEjercicioEnviar.setOnClickListener(this);
    }

    //Metodo usado para agregar filas en la tabla
    private void agregarFilaTabla(){


        //Recogida de ejercicios desde la BD

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                //Conectamos con el servidor.
                NordBoxCADCliente nordBoxCAD = new NordBoxCADCliente("10.0.2.2", 30501);

                //Guardo los diferentes tipos de ejercicios.
                ejercicioBenchUsuarios = nordBoxCAD.ejeBenchUsuario(ejerciciosUsuario);
                esperaHilo = false;
            }
        });
        thread.start();

        //Bucle de espera de hilo.
        boolean bucleEspFinHilo = true;
        while (bucleEspFinHilo) {
            if (!esperaHilo) {
                bucleEspFinHilo = false;
                esperaHilo = true;
            }
        }

        //Iterador con los datos que se introduciran en la tabla.
        Iterator<EjercicioBenchUsuario> benchUsuarioIterator = ejercicioBenchUsuarios.iterator();
        while (benchUsuarioIterator.hasNext()){
            TableRow fila = new TableRow(this);

            //Defino el layout de la fila
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            fila.setLayoutParams(lp);

            //Estilo al borde
            fila.setBackgroundResource(R.drawable.borde_tabla);

            EjercicioBenchUsuario ejercicioBenchUsuario = benchUsuarioIterator.next();
            TextView textViewRondas = new TextView(this);
            TextView textViewPeso = new TextView(this);
            TextView textViewFecha = new TextView(this);
            textViewRondas.setBackgroundResource(R.drawable.borde_tabla);
            textViewPeso.setBackgroundResource(R.drawable.borde_tabla);
            textViewFecha.setBackgroundResource(R.drawable.borde_tabla);

            textViewRondas.setText(" " + ejercicioBenchUsuario.getRondas());
            textViewPeso.setText(" " + ejercicioBenchUsuario.getPeso());
            textViewFecha.setText(" " +ejercicioBenchUsuario.getFecha().toString());

            fila.addView(textViewRondas);
            fila.addView(textViewPeso);
            fila.addView(textViewFecha);

            //Agregar fila a la tabla
            tablaEjerciciosBench.addView(fila);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        //TODO tengo el id, ahora tengo que sacar los datos de la tabla, si es que hay. Y hacer que pueda agregar datos nuevos.

    }
}