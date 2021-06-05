package com.example.nordboxapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import nordboxcad.EjerciciosBench;
import nordboxcad.Evento;
import nordboxcad.NordBoxCADCliente;

public class EventoActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton btnDiaAnterior, btnDiaSiguiente, btnEventoCalendario;
    TextView tvEventoFecha;
    static Calendar calendar;
    static String fechaActual;

    //Para el recycler
    List<Evento> elements;
    ArrayList<Evento> bench;
    boolean esperaHilo = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);

        if(calendar == null){
            calendar = Calendar.getInstance();
            fechaActual = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH)+1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        }

        Evento evento = new Evento();
        evento.setFecha(fechaActual);

        recycler(evento);
        InitUI();
        tvEventoFecha.setText(fechaActual);
    }

    private void InitUI() {
        btnDiaAnterior = findViewById(R.id.btnDiaAnterior);
        btnDiaAnterior.setOnClickListener(this);
        btnDiaSiguiente = findViewById(R.id.btnDiaSiguiente);
        btnDiaSiguiente.setOnClickListener(this);
        btnEventoCalendario = findViewById(R.id.btnEventoCalendario);
        btnEventoCalendario.setOnClickListener(this);
        tvEventoFecha = findViewById(R.id.tvEventoFecha);
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btnDiaAnterior)
        {
            calendar.set(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH)), calendar.get(Calendar.DAY_OF_MONTH)-1);
            fechaActual = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH)) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
            reiniciarActivity(this);
        } else if (id == R.id.btnDiaSiguiente) {
            calendar.set(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH)), calendar.get(Calendar.DAY_OF_MONTH)+1);
            fechaActual = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH)) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
            reiniciarActivity(this);
        } else if (id == R.id.btnEventoCalendario){
            abrirCalendario(v);
        }

    }

    //reinicia una Activity
    public static void reiniciarActivity(Activity actividad){
        Intent intent=new Intent();
        intent.setClass(actividad, actividad.getClass());
        //llamamos a la actividad
        actividad.startActivity(intent);
        //finalizamos la actividad actual
        actividad.finish();
    }

    public void abrirCalendario(View view){
        final Calendar calendar = Calendar.getInstance();
        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog pickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                fechaActual = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH)) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
                finish();
                startActivity(getIntent());
            }
        }, anio, mes , dia);
        pickerDialog.show();
    }
}