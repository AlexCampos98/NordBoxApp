package com.example.nordboxapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

//import com.android.volley.RequestQueue;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

//import org.json.JSONObject;

import nordboxcad.NordBoxCADCliente;
import nordboxcad.Usuario;

public class Login extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    //Atributo con el mensaje de error al iniciar sesion
    TextView tvLoginError;

    EditText etEmailLogin, etPasswordLogin;
    Button btnLogin;

    //Objeto usuario en el que guardaremos los datos del usuario, viene de la libreria nordboxcad
    Usuario idUsuario = new Usuario();

    //Controladores para iniciar intent y esperar a la finalizacion del hilo.
    boolean iniciarI = false, esperaHilo = false;

    //Clase para guardar los datos del usuario durante el uso de la aplicacion.
    UsuarioStatico usuarioStatico = new UsuarioStatico();

    //TODO eliminar todo lo que tenga que ver con el mapa.
    private GoogleMap mMap;
//    Boolean actualPosicion;
//    JSONObject jso;
//    Double longitudOrigen, latitudOrigen;

//    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(isPerfilValidado())
        {
            iniciarActividad(true);
        }

        //TODO eliminar todo lo que tenga que ver con el mapa.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getLocalizacion();

        //TODO Posible eliminacion de la musica
        if (cargarPreferencias()) {
            //Musica sencilla.
            MediaPlayer mp = MediaPlayer.create(this, R.raw.intro);
            mp.start();
        }


//        requestQueue = Volley.newRequestQueue(this);

        //Inicializado de componentes
        initUI();

        btnLogin.setOnClickListener(this);
    }

    /**
     * Metodo creado para inicializar todos los componentes de la activity
     */
    private void initUI() {
        etEmailLogin = findViewById(R.id.etEmailLogin);
        etPasswordLogin = findViewById(R.id.etPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        tvLoginError = findViewById(R.id.tvLoginError);
    }

    /**
     * Metodo que captura el click en los componentes.
     * @param v Clase View
     */
    public void onClick(View v) {
        int id = v.getId();
        //Si se ha pulsado el boton de login
        if (id == R.id.btnLogin) {

            //Inicio un hilo para comprobar que el usuario este en la BD.
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    //Conectamos con el servidor.
                    NordBoxCADCliente nordBoxCAD = new NordBoxCADCliente("10.0.2.2", 30501);
                    Usuario u = new Usuario();

                    u.setPassword(etPasswordLogin.getText().toString());
                    u.setCorreo(etEmailLogin.getText().toString());

                    //Llamo al metodo para comprobar que exista y guardamos los datos del usuario.
                    idUsuario = nordBoxCAD.comprobarLogin(u);

                    //Guardamos los datos del usuario para uso en la app.
                    usuarioStatico.setUsuario(idUsuario);

                    //Cambio el valor de controlador de inicio de intent
                    if (idUsuario.getId() != null) {
                        iniciarI = true;
                    } else {
                        iniciarI = false;
                    }
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

            //Guardo el inicio de sesion.
            if(iniciarI){
                guardarPerfilValidado(idUsuario.getId());
            }

            //TODO Revisar, aqui puede que casque la aplicacion, debido a que cuando volvemos a esta activity el iniciarI y el idUsuario estan sin valor por defecto.
            esperaHilo = false;
            iniciarActividad(iniciarI);
            iniciarI = false;
            idUsuario = new Usuario();
        }
    }

    /**
     * Metodo usado para en caso de login correcto cambiar de intent y en caso incorrecto mostrar
     * un mensaje de error.
     * @param iniciar Booleano con resolucion del login del usuario.
     */
    public void iniciarActividad(Boolean iniciar) {
        if (iniciar) {
            Intent i = new Intent(this, MenuActivity.class);
            startActivity(i);
        } else {
            tvLoginError.setVisibility(View.VISIBLE);
        }
    }

    //TODO Posibles pruebas, revisar y quitar en caso de no uso.
    @Override
    protected void onRestart() {
        super.onRestart();
        usuarioStatico = new UsuarioStatico();
    }

    /**
     * Metodo usado para poder obtener la localizacion
     * TODO Eliminacion del mapa.
     */
    private void getLocalizacion() {
        int permiso = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permiso == PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    //TODO Eliminacion del mapa.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        mMap.getUiSettings().setMyLocationButtonEnabled(false);

        LocationManager locationManager = (LocationManager) Login.this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LatLng miUbicacion = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(miUbicacion).title("Ubicacion actual"));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        // Add a marker in Sydney and move the camera
        LatLng nordBox = new LatLng(43.450588, -3.459014);
        mMap.addMarker(new MarkerOptions().position(nordBox).title("NordBox"));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(nordBox)
                .zoom(14)
                .bearing(0)
                .tilt(45)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    /**
     * TODO Posible eliminacion de la musica.
     * @return a
     */
    private boolean cargarPreferencias() {
        SharedPreferences preferences = getSharedPreferences("Musica", Context.MODE_PRIVATE);
        return preferences.getBoolean("Musica", true);
    }

    /**
     * Metodo usado para identificar si anteriormente ya inicio sesion, para saltarse esta
     * activity.
     * @return Devuelve booleano, true si ya inicio sesion previamente y hay datos guardados y false
     * si no hay datos guardados de inicio de sesion.
     */
    private boolean isPerfilValidado() {
        SharedPreferences preferences = getSharedPreferences("perfilValidado", Context.MODE_PRIVATE);
        final Usuario usuario = new Usuario(preferences.getInt("perfilValidado", -1));
        //En caso de exitir, obtengo los datos del usuario y los guardo.
        if (usuario.getId() != -1) {
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {

                    NordBoxCADCliente nordBoxCAD = new NordBoxCADCliente("10.0.2.2", 30501);

                    usuarioStatico.setUsuario(nordBoxCAD.buscarUsuarioID(usuario));
                }
            });
            thread.start();
            return true;
        }
        return false;
    }

    /**
     * Metodo usado para guardar el inicio de sesion del usuario.
     * @param id Identificador del usuario.
     */
    public void guardarPerfilValidado(int id) {
        SharedPreferences preferences = getSharedPreferences("perfilValidado", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharePreference = preferences.edit();
        sharePreference.putInt("perfilValidado", id);
        sharePreference.apply();
    }
}