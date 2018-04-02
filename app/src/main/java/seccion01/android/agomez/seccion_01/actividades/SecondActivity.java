package seccion01.android.agomez.seccion_01.actividades;

import android.content.Intent;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import seccion01.android.agomez.seccion_01.R;
import seccion01.android.agomez.seccion_01.ThirdActivity;

public class SecondActivity extends AppCompatActivity {
    String paramRecuperado;
    private TextView textView;
    private Button botonNavegar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textView = (TextView) findViewById(R.id.textoRecibido);

        //un bundle es una caja donde recibimos los datos que nos manda un intent.
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            paramRecuperado = bundle.getString("param1");
            textView.setText(paramRecuperado);
            Toast.makeText(SecondActivity.this, paramRecuperado, Toast.LENGTH_LONG);
        } else {
            Toast.makeText(SecondActivity.this, "no viene nada!", Toast.LENGTH_LONG);

        }
        botonNavegar = (Button) findViewById(R.id.miButtonNav3);
        botonNavegar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                //vamos a navegar a la tercera actividad
                Intent quieroNavegar = new Intent(SecondActivity.this, ThirdActivity.class);
                startActivity(quieroNavegar);

            }

        });
    }
}
