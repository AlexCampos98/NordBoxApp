package com.example.nordboxapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class BenchmarkEjercicioActivity extends AppCompatActivity {

    TableLayout tablaEjerciciosBench;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benchmark_ejercicio);

        InitUI();
    }

    private void InitUI() {
        tablaEjerciciosBench = findViewById(R.id.tablaEjerciciosBench);
    }

    //Metodo usado para agregar filas en la tabla
    private void agregarFilaTabla(){
        TableRow fila = new TableRow(this);

        //Defino el layout de la fila
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        fila.setLayoutParams(lp);

        //Estilo al borde
        fila.setBackgroundResource(R.drawable.borde_tabla);

        //Agregar columnas a la fila, con contenido en ellas
        TextView textView = new TextView(this);
        textView.setText("algo");
        fila.addView(textView);

        //Agregar fila a la tabla
        tablaEjerciciosBench.addView(fila);

        /*
        //Ejemplo
        String[] tallas = {"X1", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] colores = {"BLANCO", "VERDE", "PÚRPURA", "AMARILLO", "MARRÓN"};

        // Primero dibujar el encabezado; esto es poner "TALLAS" y a la derecha todas las tallas
        TableRow fila = new TableRow(getActivity());
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        fila.setLayoutParams(lp);
        // Borde
        fila.setBackgroundResource(R.drawable.borde_tabla);
        //El elemento de la izquierda
        TextView tv = new TextView(getActivity());
        tv.setTypeface(null, Typeface.BOLD);
        tv.setText("TALLAS");
        fila.addView(tv);

        // Ahora agregar las tallas
        for (int x = 0; x < tallas.length; x++) {
            TextView tvTalla = new TextView(getActivity());
            tvTalla.setText(tallas[x]);
            fila.addView(tvTalla);
        }
        // Finalmente agregar la fila en la primera posición
        tableLayout.addView(fila, 0);

        // Ahora por cada color hacer casi lo mismo
        for (int x = 0; x < colores.length; x++) {
            TableRow filaColor = new TableRow(getActivity());
            filaColor.setLayoutParams(lp);
            // Borde
            filaColor.setBackgroundResource(R.drawable.borde_tabla);
            // El nombre del color
            TextView textViewColor = new TextView(getActivity());
            textViewColor.setText(colores[x]);
            textViewColor.setTypeface(null, Typeface.BOLD);
            filaColor.addView(textViewColor);
            // Y ahora por cada talla, agregar un campo de texto
            for (int y = 0; y < tallas.length; y++) {
                EditText editText = new EditText(getActivity());
                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                editText.setWidth(Constantes.ANCHURA_PIXELES_EDITTEXT_TABLA_DINAMICA);
                filaColor.addView(editText);
                filaColor.setMinimumWidth(Constantes.ANCHURA_PIXELES_EDITTEXT_TABLA_DINAMICA);
            }
            // Finalmente agregar la fila
            tableLayout.addView(filaColor);
        }

        */
    }
}