package com.example.nordboxapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import nordboxcad.EjerciciosBench;
import nordboxcad.Evento;
import nordboxcad.NordBoxCADCliente;

public class EventoActivity extends AppCompatActivity {

    //Para el recycler
    List<Evento> elements;
    ArrayList<Evento> bench;
    boolean esperaHilo = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);

        //Borrar
        Evento evento = new Evento();
        evento.setFecha("2021/06/05");

        recycler(evento);
    }

    /**
     * Metodo usado para crear el recyclerView con los datos obtenidos de la BD.
     */
    public void recycler(final Evento evento) {
        elements = new ArrayList<>();
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                //Conectamos con el servidor.
                NordBoxCADCliente nordBoxCAD = new NordBoxCADCliente("10.0.2.2", 30501);

                //Guardo los diferentes tipos de ejercicios.
                bench = nordBoxCAD.obtenerEventosFecha(evento);
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
        elements.addAll(bench);

        //Agregamos los elementos al recyclerView
        ListAdapterEvento listAdapter = new ListAdapterEvento(elements, this);
        RecyclerView recyclerView = findViewById(R.id.rvEvento);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }
}