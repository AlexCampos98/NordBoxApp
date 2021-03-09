package com.example.nordboxapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnReservar, btnContacto, btnBenchmarks, btnPerfil, btnSalir;

    private PendingIntent pendingIntent;
    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initUI();

        setPendingIntent();
        createNotificatonChannel();
        createNotification("Hora de fortalecerse", "Es hora de hacer ejercicios y apuntar los resultados.");

//        Notificaciones notificaciones = new Notificaciones(this);
//        notificaciones.setPendingIntent(BenchmarksActivity.class);
//        Notificaciones.createNotificatonChannel();
//        Notificaciones.createNotification("Hora de fortalecerse", "Es hora de hacer ejercicios y apuntar los resultados.");
    }

    public void initUI() {
        btnReservar = findViewById(R.id.btnReservar);
        btnReservar.setOnClickListener(this);
        btnContacto = findViewById(R.id.btnContacto);
        btnContacto.setOnClickListener(this);
        btnBenchmarks = findViewById(R.id.btnBenchmarks);
        btnBenchmarks.setOnClickListener(this);
        btnPerfil = findViewById(R.id.btnPerfil);
        btnPerfil.setOnClickListener(this);
        btnSalir = findViewById(R.id.btnSalir);
        btnSalir.setOnClickListener(this);
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
            Toast.makeText(this,"Opcion Calendario en proceso",Toast.LENGTH_LONG).show();
        } else if (id == R.id.overContacto) {
            //TODO Añadir intent de cambio a la activity Contacto.
            Toast.makeText(this,"Opcion Contacto en proceso",Toast.LENGTH_LONG).show();
        } else if (id == R.id.overBenchmarks) {
            i = new Intent(this, BenchmarksActivity.class);
        } else if (id == R.id.overPerfil) {
            //Añadir intent de cambio a la activity Perfil.
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

    //Metodo que captura el click
    @SuppressLint("ResourceAsColor")
    public void onClick(View v) {
        int id = v.getId();

        Intent i = null;

        if (id == R.id.btnReservar) {
            //TODO Añadir intent de cambio a la activity calendario.
            Snackbar snackbar = Snackbar.make(v, R.string.btnBack_Squat , Snackbar.LENGTH_LONG);
            snackbar.setDuration(2000);
            snackbar.show();
            snackbar.show();
        } else if (id == R.id.btnContacto) {
            //TODO Añadir intent de cambio a la activity Contacto.
            Toast.makeText(this,"Opcion Contacto en proceso",Toast.LENGTH_LONG).show();
//            i = new Intent(this, ma)
        } else if (id == R.id.btnBenchmarks) {
            i = new Intent(this, BenchmarksActivity.class);
        } else if (id == R.id.btnPerfil) {
            //Añadir intent de cambio a la activity Perfil.
            i = new Intent(this, UsuarioActivity.class);
        } else if (id == R.id.btnSalir) {
            //Cerrar la sesion al Salir.
            logoutPerfilValidado();
            i = new Intent(this, Login.class);
        }

        if (i != null) {
            startActivity(i);
        }
    }

    public void createNotification(String titulo, String texto){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.brazo);
        builder.setContentTitle(titulo);
        builder.setContentText(texto);
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.BLUE, 1000, 1000);
        builder.setVibrate(new long[]{1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);

        builder.setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());
    }

    public void createNotificatonChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "NOTIFICACION";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public void setPendingIntent() {
        Intent intent = new Intent(this, BenchmarksActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(BenchmarksActivity.class);
        stackBuilder.addNextIntent(intent);
        pendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void logoutPerfilValidado() {
        SharedPreferences preferences = getSharedPreferences("perfilValidado", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharePreference = preferences.edit();
        sharePreference.putInt("perfilValidado", -1);
        sharePreference.apply();
    }
}