package com.example.nordboxapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;

import de.hdodenhof.circleimageview.CircleImageView;
import nordboxcad.NordBoxCADCliente;
import nordboxcad.Usuario;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

public class usuarioActivity extends AppCompatActivity {

    EditText etPerfilEmail, etPerfilPassword, etPerfilNombre, etPerfilPriApellido, etPerfilSegApellido, etPerfilTelefono, etPerfilTelefonoEmergencia, etPerfilCodPostal, etPerfilLocalidad, etPerfilProvincia;
    Button btnPerfilPassword, btnGuardarPerfil;
    //TODO Crear la accion en el boton cambiarPassword, cuando se pulse se habilita el campo y se procede a enviar de forma normal.
    UsuarioStatico usuarioStatico = new UsuarioStatico();

    //Imagen circular
    CircleImageView imageUsuario;

    //Permiso de la clase Constants
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;
    //selección de imagen Constants
    private static final int IMAGE_PICK_CAMERA_CODE = 102;
    private static final int IMAGE_PICK_GALLERY_CODE = 103;
    // matrices de permisos
    private String[] cameraPermissions; // cámara y almacenamiento
    private String[] storagePermissions;// solo almacenamiento
    // variables (constain datos para guardar)
    private Uri imageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        initUI();
//        btnGuardarPerfil.setOnClickListener((View.OnClickListener) this);
    }

    public void initUI() {
        imageUsuario = findViewById(R.id.imageUsuario);

        etPerfilEmail = findViewById(R.id.etPerfilEmail);
        etPerfilPassword = findViewById(R.id.etPerfilPassword);
        etPerfilNombre = findViewById(R.id.etPerfilNombre);
        etPerfilPriApellido = findViewById(R.id.etPerfilPriApellido);
        etPerfilSegApellido = findViewById(R.id.etPerfilSegApellido);
        etPerfilTelefono = findViewById(R.id.etPerfilTelefono);
        etPerfilTelefonoEmergencia = findViewById(R.id.etPerfilTelefonoEmergencia);
        etPerfilCodPostal = findViewById(R.id.etPerfilCodPostal);
        etPerfilLocalidad = findViewById(R.id.etPerfilLocalidad);
        etPerfilProvincia = findViewById(R.id.etPerfilProvincia);

        etPerfilEmail.append(usuarioStatico.getUsuario().getCorreo());
        etPerfilPassword.append(usuarioStatico.getUsuario().getPassword());
        etPerfilNombre.append(usuarioStatico.getUsuario().getNombre());
        etPerfilPriApellido.append(usuarioStatico.getUsuario().getpApellido());
        etPerfilSegApellido.append(usuarioStatico.getUsuario().getsApellido());
        etPerfilTelefono.append(usuarioStatico.getUsuario().getTelefono());
        etPerfilTelefonoEmergencia.append(usuarioStatico.getUsuario().getTelefonoEmergencia());
        etPerfilCodPostal.append(Integer.toString(usuarioStatico.getUsuario().getCodigoPostal()));
        etPerfilLocalidad.append(usuarioStatico.getUsuario().getLocalidad());
        etPerfilProvincia.append(usuarioStatico.getUsuario().getProvincia());

        btnPerfilPassword = findViewById(R.id.btnPerfilPassword);
        btnGuardarPerfil = findViewById(R.id.btnGuardarPerfil);

        //Inicializamos Permisos arrays
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        imageUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // muestra el cuadro de diálogo de selección de imagen
                imagePickDialog();
                
            }
        });
    }

    public void onClick(View v) {
        int id = v.getId();
        //Si se ha pulsado el boton de login
        if (id == R.id.btnGuardarPerfil) {
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {

                    NordBoxCADCliente nordBoxCAD = new NordBoxCADCliente("10.0.2.2", 30501);
                    File file = null;

                    if(imageUri != null)
                    {
                        file = new File(imageUri.getPath());
                    }

                    usuarioStatico.setUsuario(new Usuario(usuarioStatico.getUsuario().getId(), etPerfilEmail.getText().toString(), null, etPerfilNombre.getText().toString(), etPerfilPriApellido.getText().toString(), etPerfilSegApellido.getText().toString(), etPerfilTelefono.getText().toString(),
                            etPerfilTelefonoEmergencia.getText().toString(), Integer.parseInt(etPerfilCodPostal.getText().toString()), etPerfilLocalidad.getText().toString(), etPerfilProvincia.getText().toString(), file));

                    nordBoxCAD.modificarUsuarioNoPass(usuarioStatico.getUsuario());
                }
            });
            thread.start();



        }
    }

    private void imagePickDialog() {
        // opciones para mostrar en el diálogo
        String[] options = {"Camara", "Galeria"};
        //dialogo
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Titulo
        builder.setTitle("Seleccionar imagen");
        // establecer elementos / opciones
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // manejar clicks
                if (which == 0) {
                    //click en camara
                    if (!checkCameraPermission()) {
                        requestCameraPermission();
                    } else {
                        // permiso ya otorgado
                        PickFromCamera();
                    }

                } else if (which == 1) {
                    //click en el almacenamiento
                    if (!checkStoragePermission()) {
                        requestStoragePermission();
                    } else {
                        // permiso ya otorgado
                        PickFromGallery();
                    }
                }
            }
        });

        // Crear / mostrar diálogo
        builder.create().show();
    }

    private void PickFromGallery() {
        // intento de elegir la imagen de la galería, la imagen se devolverá en el método onActivityResult
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);
    }

    private void PickFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Titulo de la Imagen");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Descripción de la imagen");
        //put image Uri
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        // Intento de abrir la cámara para la imagen
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    private boolean checkStoragePermission() {
        //comprobar si el permiso de almacenamiento está habilitado o no
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
    }

    private void requestStoragePermission() {
        // solicita el permiso de almacenamiento
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        // verifica si el permiso de la cámara está habilitado o no
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result && result1;
    }

    private void requestCameraPermission() {
        // solicita el permiso de la cámara
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); //regrese haciendo clic en el botón de barra de acción
        return super.onSupportNavigateUp();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // resultado del permiso permitido / denegado

        switch (requestCode) {
            case CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0) {

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted && storageAccepted) {
                        // ambos permisos permitidos
                        PickFromCamera();
                    } else {
                        Toast.makeText(this, "Se requieren permisos de cámara y almacenamiento", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            break;
            case STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0) {

                    // si se permite devolver verdadero de lo contrario falso
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted) {
                        // permiso de almacenamiento permitido
                        PickFromGallery();
                    } else {
                        Toast.makeText(this, "Se requiere permiso de almacenamiento", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //image picked from camera or gallery will be received hare
        if (resultCode == RESULT_OK) {
            //Image is picked
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                //Picked from gallery

                //crop image
                assert data != null;
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this);

            } else if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                //Picked from camera
                //crop Image
                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this);

            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                //Croped image received
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    assert result != null;
                    Uri resultUri = result.getUri();
                    imageUri = resultUri;
                    //set Image
                    imageUsuario.setImageURI(resultUri);
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    //ERROR
                    Exception error = result.getError();
                    Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
                }

            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}