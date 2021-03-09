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

    private static final int STORAGE_REQUEST_PERMISSION = 111;
    TextView tvLoginError;
    EditText etEmailLogin, etPasswordLogin;
    Button btnLogin;
    Usuario idUsuario = new Usuario();
    boolean iniciarI = false, esperaHilo = false;

    UsuarioStatico usuarioStatico = new UsuarioStatico();

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

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getLocalizacion();

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

    //Metodo creado para inicializar todos los componentes de la activity
    private void initUI() {
        etEmailLogin = findViewById(R.id.etEmailLogin);
        etPasswordLogin = findViewById(R.id.etPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        tvLoginError = findViewById(R.id.tvLoginError);
    }

    //Metodo que captura el click
    public void onClick(View v) {
        int id = v.getId();
        //Si se ha pulsado el boton de login
        if (id == R.id.btnLogin) {

            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {

                    NordBoxCADCliente nordBoxCAD = new NordBoxCADCliente("10.0.2.2", 30501);
                    Usuario u = new Usuario();

                    u.setPassword(etPasswordLogin.getText().toString());
                    u.setCorreo(etEmailLogin.getText().toString());
                    idUsuario = nordBoxCAD.comprobarLogin(u);

                    usuarioStatico.setUsuario(idUsuario);

                    if (idUsuario.getId() != null) {
                        iniciarI = true;
                    } else {
                        iniciarI = false;
                    }
                    esperaHilo = true;
                }
            });
            thread.start();

            boolean bucleEspFinHilo = true;
            while (bucleEspFinHilo) {
                if (esperaHilo) {
                    bucleEspFinHilo = false;
                }
            }
            guardarPerfilValidado(idUsuario.getId());
            esperaHilo = false;
            iniciarActividad(iniciarI);
            iniciarI = false;
            idUsuario = new Usuario();
        }
    }

    public void iniciarActividad(Boolean iniciar) {
        if (iniciar) {
            Intent i = new Intent(this, MenuActivity.class);
            startActivity(i);
        } else {
            tvLoginError.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        usuarioStatico = new UsuarioStatico();
    }

    /**
     * Metodo usado para poder obtener la localizacion
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

//        String url = "";

//        RequestQueue queue = Volley.newRequestQueue(this);
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    jso = new JSONObject(response);
//                    trazarRuta(jso);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//
//        queue.add(stringRequest);
    }

    //    private void trazarRuta(JSONObject jso) {
//
//        JSONArray jRoutes;
//        JSONArray jLegs;
//        JSONArray jSteps;
//
//        try {
//            jRoutes = jso.getJSONArray("routes");
//            for (int i=0; i<jRoutes.length();i++){
//
//                jLegs = ((JSONObject)(jRoutes.get(i))).getJSONArray("legs");
//
//                for (int j=0; j<jLegs.length();j++){
//
//                    jSteps = ((JSONObject)jLegs.get(j)).getJSONArray("steps");
//
//                    for (int k = 0; k<jSteps.length();k++){
//
//                        String polyline = ""+((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
//                        Log.i("end",""+polyline);
//                        List<LatLng> list = PolyUtil.decode(polyline);
//                        mMap.addPolyline(new PolylineOptions().addAll(list).color(Color.GRAY).width(5));
//                    }
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
    private boolean cargarPreferencias() {
        SharedPreferences preferences = getSharedPreferences("Musica", Context.MODE_PRIVATE);
        boolean sonidoActivado = preferences.getBoolean("Musica", true);
        return sonidoActivado;
    }

    private boolean isPerfilValidado() {
        SharedPreferences preferences = getSharedPreferences("perfilValidado", Context.MODE_PRIVATE);
        final Usuario usuario = new Usuario(preferences.getInt("perfilValidado", -1));
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

    public void guardarPerfilValidado(int id) {
        SharedPreferences preferences = getSharedPreferences("perfilValidado", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharePreference = preferences.edit();
        sharePreference.putInt("perfilValidado", id);
        sharePreference.apply();
    }
}