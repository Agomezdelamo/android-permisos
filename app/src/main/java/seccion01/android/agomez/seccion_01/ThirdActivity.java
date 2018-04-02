package seccion01.android.agomez.seccion_01;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    private EditText editTextPhone;
    private EditText editTextWeb;
    private ImageButton imageButtonPhone;
    private ImageButton imageButtonWeb;
    private ImageButton imageButtonCamera;
    private ImageButton agendaButton;
    private ImageButton mailButton;

    //variable que actua como código cuando pides los permisos disponibles
    private final int PHONE_CALL_CODE = 100;
    private final int PICTURE_FROM_CAMERA = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextWeb = (EditText) findViewById(R.id.editTextWeb);
        imageButtonPhone = (ImageButton) findViewById(R.id.imageButtonPhone);
        imageButtonWeb = (ImageButton) findViewById(R.id.imageButtonWeb);
        imageButtonCamera = (ImageButton) findViewById(R.id.imageButtonCamara);
        agendaButton = (ImageButton) findViewById(R.id.agendaButton);
        mailButton = (ImageButton) findViewById(R.id.mailButton);

        /**
         * Cuando hacemos click sobre el boton que quiere llamar,
         * 1. le ponemos una escucha y ejecutamos el onclick.
         * 2. Comprobamos los permisos del manifest para ver si
         *
         */
        imageButtonPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numTel = editTextPhone.getText().toString();
                if (numTel != null && !numTel.isEmpty()) {
                    //comprobamos si la version que tenemoss es mayor o igual a la version 6
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        //si ha aceptado
                        if(checkPermission(Manifest.permission.CALL_PHONE)) {
                            //ha aceptado
                            Intent i = new Intent(Intent.ACTION_CALL, Uri.parse(numTel));
                            startActivity(i);
                        } else {
                            //si ha denegado o es la primera vez
                            if(!shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)){
                                //si no se le a preguntado, es la primera vez

                                //NUEVA FORMA DE COMPROBAR LOS PERMISOS,
                                // 1. SE LE PREGUNTA AL USUARIO SI QUIERE PERMITIR USAR EL TELEFONO
                                // pasas un array con el permiso o permisos y un codigo
                                requestPermissions(new String[]{
                                        Manifest.permission.CALL_PHONE
                                }, PHONE_CALL_CODE);
                            } else {
                                //ha denegado
                                Toast.makeText(ThirdActivity.this, "por favor, activa el permiso", Toast.LENGTH_LONG);
                                //intent para ir a settings
                                Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                i.addCategory(Intent.CATEGORY_DEFAULT);
                                i.setData(Uri.parse("package:" + getPackageName()));
                                //Las banderas sirven para indicar:
                                //que este intent crea una nueva tarea con su pila de actividades,
                                //y que el sitio al que lleva el intent, se quita de la historia y de los recientes, de tal forma
                                //que si navegas dentro de las settings, y das atras, no pasas por la pantalla principal de settings, sino que saltas
                                //otra vez a la app
                               i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //iniciamos una nueva tarea con su pila de actividades
                               i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY); //lo sacamos del historial
                               i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS); //lo exluimos de recientes
                                startActivity(i);
                            }
                        }
                    } else {
                        olderVersions(numTel);
                    }
                } else {
                    Toast.makeText(ThirdActivity.this, "No has introducido ningún numero", Toast.LENGTH_LONG).show();
                }
            }

            /**
             * La vieja forma, comprobamos el permiso del telefono, si esta ok,
             * instanciamos el intent con la intencion de llamar a un telefono
             * y la lanzamos
             */
            private void olderVersions(String numero) {
                // COMPROBAMOS SI TENEMOS EL PERMISO Y EFECTUAMOS EL INTENT
                if (checkPermission(Manifest.permission.CALL_PHONE)) {
                    //intencion implicita, decimos que queremos hacer, pero no con que aplicacion o componente
                    Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + numero));
                    startActivity(intentCall);
                } else {
                    Toast.makeText(ThirdActivity.this, "no tienes permisos ", Toast.LENGTH_LONG);
                }
            }


        });

        imageButtonWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = editTextWeb.getText().toString();
                if(url != null && !url.isEmpty()) {
                    Intent i = new Intent();
                    i.setAction(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("http://" + url));
                    startActivity(i);
                }
            }
        });

        agendaButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.setData(Uri.parse("content://contacts/people"));
                startActivity(i);
            }
        });

        mailButton.setOnClickListener(new View.OnClickListener(){
            String destinatario = "agomezdelamo@gmail.com";
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_SEND);
                i.setData(Uri.parse(destinatario));
                i.setType("plain/text");
                i.putExtra(Intent.EXTRA_SUBJECT, "De parte del curso de android");
                i.putExtra(Intent.EXTRA_TEXT, "Este es un correo desde android para mi mismo");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"agomez2007@gmail.com", "mongolasindeguas@hotmail.com"});
                startActivity(Intent.createChooser(i,"ELEGIR UN CLIENTE"));
            }
        });

        imageButtonCamera.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(i, PICTURE_FROM_CAMERA);
            }
        });
    }
    //FIN ON CREATE

    /**
     * Nueva FORMA de comprobar permisos,
     * 2. se le pregunta al usuario, y cuando responde se ejecuta este metodo.
     *
     * @params requestCode es el código que pasamos cuando se pidio el permiso,
     * es una manera de recordar que permiso hemos solicitado,
     * puesto que nosotros declaramos un codigo para cada permiso, llamada, camara, y una vez preguntamos,
     * como el metodo es asincrono, pasamos ese codigo para cuando el usuario conteste, saber a que permiso se refiere.
     *
     * @param permissions array con los permisos que se han solicitado, tendremos que buscar el correspondiente al requestCode
     *
     * @param grantResults array con las decisiones del usuario sobre cada permiso, habra que comprobar
     * si la decision del usuario es igual a la constante de Manifest, permisionGranted
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //si el codigo que nos viene de vuelta cuando se ha comprobado el permiso es el del telefono...
        switch (requestCode) {
            case PHONE_CALL_CODE:
                String permission = permissions[0];
                int result = grantResults[0];

                //si el permiso devuelto es el del telefono
                if (permission.equals(Manifest.permission.CALL_PHONE)) {
                    //comprobamos si la peticion a sido aceptada o denegada
                    if (result == PackageManager.PERMISSION_GRANTED) {
                        //EL USUARIO CONDECE EL PERMISO
                        String numTel = editTextPhone.getText().toString();
                        Intent quieroLlamar = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + numTel));
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(quieroLlamar);
                    } else {
                        //EL USUARIO NO CONCEDE EL PERMISO
                        Toast.makeText(ThirdActivity.this, "no me das permisos ", Toast.LENGTH_LONG);
                    }
                }
                break;

            default:
                //llamamos al padre
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case PICTURE_FROM_CAMERA:
                if(resultCode == Activity.RESULT_OK) {
                    String result = data.toUri(0);
                    Toast.makeText(this, "result ->"+ result, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "try again", Toast.LENGTH_LONG).show();

                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * METODO QUE COMPRUEBA SI TENEMOS UN PERMISO EN EL MANIFEST
     */
    private boolean checkPermission(String permission) {
        //comprobamos el permiso
        int result = this.checkCallingOrSelfPermission(permission);
        //Y SI EL PERMISO QUE A DEVUELTO ES UN INT 0, Y ESTE ES IGUAL A LA COSNTANTE DE PERMISION GRANTED PUES TRUE, SINO FALSE.
        return result == PackageManager.PERMISSION_GRANTED;
    }
}
