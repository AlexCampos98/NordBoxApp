package com.example.nordboxapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import java.time.Instant;
import java.util.Calendar;

public class CreacionEvento extends AppCompatActivity implements View.OnClickListener {

    EditText etFechaEvento, etPlazas, etHoraEvento, etNombreEvento;
    TextInputLayout inpFechaEvento, inpPlazas, inpHoraEvento, inpNombreEvento;
    Button btnCreacionEvento;
    ImageButton btnFechaEvento, btnHoraEvento, btnColor;
    TextView tvColor;
    int colorSeleccionado = -65536;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creacion_evento);

        initUI();
    }

    private void initUI() {
        etFechaEvento = findViewById(R.id.etFechaEvento);
        btnFechaEvento = findViewById(R.id.btnFechaEvento);
        btnFechaEvento.setOnClickListener(this);
        inpFechaEvento = findViewById(R.id.inpFechaEvento);
        btnHoraEvento = findViewById(R.id.btnHoraEvento);
        btnHoraEvento.setOnClickListener(this);
        etPlazas = findViewById(R.id.etPlazas);
        inpPlazas = findViewById(R.id.inpPlazas);
        etHoraEvento = findViewById(R.id.etHoraEvento);
        inpHoraEvento = findViewById(R.id.inpHoraEvento);
        etNombreEvento = findViewById(R.id.etNombreEvento);
        inpNombreEvento = findViewById(R.id.inpNombreEvento);
        btnCreacionEvento = findViewById(R.id.btnCreacionEvento);
        btnCreacionEvento.setOnClickListener(this);
        btnColor = findViewById(R.id.btnColor);
        btnColor.setOnClickListener(this);

        tvColor = findViewById(R.id.tvColor);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btnCreacionEvento) {
            //Comprobaciones campo por campo, de los errores en ellos.
            boolean comprobacion = true;
            if (etFechaEvento.getText().toString().equals("")) {
                inpFechaEvento.setErrorEnabled(true);
                inpFechaEvento.setError(getString(R.string.errorObligatorio));
                comprobacion = false;
            } else{
                inpFechaEvento.setErrorEnabled(false);
            }
            if (etPlazas.getText().toString().equals("")) {
                inpPlazas.setErrorEnabled(true);
                inpPlazas.setError(getString(R.string.errorObligatorio));
                comprobacion = false;
            } else{
                inpPlazas.setErrorEnabled(false);
            }
            if (etHoraEvento.getText().toString().equals("")) {
                inpHoraEvento.setErrorEnabled(true);
                inpHoraEvento.setError(getString(R.string.errorObligatorio));
                comprobacion = false;
            } else{
                inpHoraEvento.setErrorEnabled(false);
            }
            if (etNombreEvento.getText().toString().equals("")) {
                inpNombreEvento.setErrorEnabled(true);
                inpNombreEvento.setError(getString(R.string.errorObligatorio));
                comprobacion = false;
            } else{
                inpNombreEvento.setErrorEnabled(false);
            }

            if(comprobacion){

            }

        } else if(id == R.id.btnFechaEvento){
            abrirCalendario(v);
        } else if(id == R.id.btnHoraEvento){
            abrirHora(v);
        } else if(id == R.id.btnColor){
            abrirColores(v);
        }
    }

    private void abrirHora(View v) {
        Calendar calendar = Calendar.getInstance();
        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        final TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                etHoraEvento.setText(hourOfDay + ":" + minute);
            }
        }, hora, min, true);
        timePickerDialog.show();
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
                etFechaEvento.setText(fecha);
            }
        }, anio, mes , dia);
        pickerDialog.show();
    }

    public void abrirColores(View view){
        ColorPickerDialogBuilder
                .with(this)
                .setTitle(getString(R.string.tituloColorPicker))
                .showAlphaSlider(false)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {

                    }
                })
                .setPositiveButton(getString(R.string.confirm), new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        tvColor.setBackgroundColor(selectedColor);
                        colorSeleccionado = selectedColor;
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }
}