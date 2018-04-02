package seccion01.android.agomez.seccion_01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import seccion01.android.agomez.seccion_01.actividades.SecondActivity;

public class MainActivity extends AppCompatActivity {

    /**
     * Otra forma de vincular boton de la vista con onclick, para mas infor en el oncreate
     */
    private Button miBoton;

    private String saludo = "hola desde la actividad 1";

    /**
     * Primer metodo al que se llama cuando inicias un activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //1 FORMA DE IMPLEMENTAR UN BOTON
        miBoton = (Button) findViewById(R.id.miButton01);
        miBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "boton onclick desde java", Toast.LENGTH_LONG).show();
                /**
                 * 2 tipos de intent:
                 * Explicito, expecifican el componente(actividad o un servicio) de forma explicita, por ejemplo, navegar a
                 * una actividad nueva o iniciar un servicio que descarga un archivo en segundo plano
                 *
                 * Implicito, no se dice a que componente se quiere ir, pero si la accion a realizar, yo quiero abrir una ubicación
                 * en un mapa, pero no especifico con que aplicación, por ejemplo.
                 *
                 * Cuando crea una intent implícita, el sistema Android busca el componente apropiado para iniciar comparando
                 * el contenido de la intent con los filtros de intents declarados en el archivo de manifiesto de otras
                 * aplicaciones en el dispositivo.
                 * Si la intent coincide con un filtro de intents, el sistema inicia ese componente y le entrega el objeto Intent.
                 * Si varios filtros de intents son compatibles, el sistema muestra un cuadro de diálogo para que el usuario
                 * pueda elegir la aplicación que se debe usar.
                 */
                //navegar a la segunda pagina
                //1. instanciamos una intencion y le pasamos el contexto y la nueva actividad
                Intent intencion = new Intent(MainActivity.this, SecondActivity.class);
                //2. le pasamos un parametro
                intencion.putExtra("param1", saludo);
                //arrancamos la actividad
                startActivity(intencion);

            }
        });
    }


    //2 FORMA DE IMPLEMENTAR UN BOTON, EL METODO SE VINCULA CON ONCLICK DESDE LA VISTA

    /**
     * Metodo que recibe un elemento de la vista
     *
     * @param View v, cualquier elemento de la vista desciende de View
     */
    public void miMetodo(View v) {
        int a = 4;
        //MainActivity.this es el contexto de este activity
        Toast.makeText(MainActivity.this, "boton onclick desde xml", Toast.LENGTH_LONG).show();
    }

}
