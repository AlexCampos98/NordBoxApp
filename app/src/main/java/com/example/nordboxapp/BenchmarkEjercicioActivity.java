package com.example.nordboxapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import nordboxcad.ApuntoEjercicio;
import nordboxcad.EjercicioBenchUsuario;
import nordboxcad.EjerciciosBench;
import nordboxcad.Evento;
import nordboxcad.NordBoxCADCliente;

public class BenchmarkEjercicioActivity extends AppCompatActivity implements View.OnClickListener{

    UsuarioStatico usuarioStatico = new UsuarioStatico();

    EditText etEjercicioPeso, etEjercicioRonda, etEjercicioFecha;
    TextInputLayout inpEjercicioPeso, inpEjercicioRonda, inpEjercicioFecha;
    TableLayout tablaEjerciciosBench;
    Button btnEjercicioEnviar;
    ImageButton btnEjercicioFecha;

    Integer idEjercicio;
    EjercicioBenchUsuario ejerciciosUsuario;
    ArrayList<EjercicioBenchUsuario> ejercicioBenchUsuarios;
    ApuntoEjercicio apuntoEjercicio;

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
        etEjercicioPeso = findViewById(R.id.etEjercicioPeso);
        etEjercicioRonda = findViewById(R.id.etEjercicioRonda);
        etEjercicioFecha = findViewById(R.id.etEjercicioFecha);
        inpEjercicioPeso = findViewById(R.id.inpEjercicioPeso);
        inpEjercicioRonda = findViewById(R.id.inpEjercicioRonda);
        inpEjercicioFecha = findViewById(R.id.inpEjercicioFecha);
        tablaEjerciciosBench = findViewById(R.id.tablaEjerciciosBench);
        btnEjercicioEnviar = findViewById(R.id.btnEjercicioEnviar);
        btnEjercicioEnviar.setOnClickListener(this);
        btnEjercicioFecha = findViewById(R.id.btnEjercicioFecha);
        btnEjercicioFecha.setOnClickListener(this);
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

    public void abrirCalendario(View view){
        Calendar calendar = Calendar.getInstance();
        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog pickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String fecha = year + "-" + (month+1) + "-" + dayOfMonth;
                etEjercicioFecha.setText(fecha);
            }
        }, anio, mes , dia);
        pickerDialog.show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnEjercicioFecha){
            abrirCalendario(v);
        }
        else if (id == R.id.btnEjercicioEnviar) {
            //Comprobaciones campo por campo, de los errores en ellos.  inpEjercicioPeso, inpEjercicioRonda, inpEjercicioFecha
            boolean comprobacion = true;
            if (etEjercicioPeso.getText().toString().equals("")) {
                inpEjercicioPeso.setErrorEnabled(true);
                inpEjercicioPeso.setError(getString(R.string.errorObligatorio));
                comprobacion = false;
            } else{
                inpEjercicioPeso.setErrorEnabled(false);
            }
            if (etEjercicioRonda.getText().toString().equals("")) {
                inpEjercicioRonda.setErrorEnabled(true);
                inpEjercicioRonda.setError(getString(R.string.errorObligatorio));
                comprobacion = false;
            } else{
                inpEjercicioRonda.setErrorEnabled(false);
            }
            if (etEjercicioFecha.getText().toString().equals("")) {
                inpEjercicioFecha.setErrorEnabled(true);
                inpEjercicioFecha.setError(getString(R.string.errorObligatorio));
                comprobacion = false;
            } else{
                inpEjercicioFecha.setErrorEnabled(false);
            }

            if(comprobacion){

                apuntoEjercicio = new ApuntoEjercicio(idEjercicio, usuarioStatico.getUsuario().getId(), etEjercicioFecha.getText().toString(), Integer.parseInt(etEjercicioPeso.getText().toString()), Integer.parseInt(etEjercicioRonda.getText().toString()));
                //Inicio un hilo para comprobar que el usuario este en la BD.
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //Conectamos con el servidor.
                        NordBoxCADCliente nordBoxCAD = new NordBoxCADCliente("10.0.2.2", 30501);


                        nordBoxCAD.crearEjeBench(apuntoEjercicio);

                        //Cambio el valor del controlador de espera de hilo.
                        esperaHilo = true;
                    }
                });
                thread.start();

                //Bucle que espera a que el hilo termine.
                boolean bucleEspFinHilo = true;
                while (bucleEspFinHilo) {
                    if (esperaHilo) {
                        bucleEspFinHilo = false;
                    }
                }

                TableRow fila = new TableRow(this);

                //Defino el layout de la fila
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                fila.setLayoutParams(lp);

                //Estilo al borde
                fila.setBackgroundResource(R.drawable.borde_tabla);

                TextView textViewRondas = new TextView(this);
                TextView textViewPeso = new TextView(this);
                TextView textViewFecha = new TextView(this);
                textViewRondas.setBackgroundResource(R.drawable.borde_tabla);
                textViewPeso.setBackgroundResource(R.drawable.borde_tabla);
                textViewFecha.setBackgroundResource(R.drawable.borde_tabla);

                textViewRondas.setText(" " + apuntoEjercicio.getnRondas());
                textViewPeso.setText(" " + apuntoEjercicio.getPeso());
                textViewFecha.setText(" " + apuntoEjercicio.getFecha());

                fila.addView(textViewRondas);
                fila.addView(textViewPeso);
                fila.addView(textViewFecha);

                //Agregar fila a la tabla
                tablaEjerciciosBench.addView(fila);

                etEjercicioFecha.setText(null);
                etEjercicioPeso.setText(null);
                etEjercicioRonda.setText(null);
//                finish();
//                startActivity(getIntent());
            }
        }
    }
}