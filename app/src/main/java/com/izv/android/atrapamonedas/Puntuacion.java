package com.izv.android.atrapamonedas;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class Puntuacion extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_puntuacion);
        Bundle b = getIntent().getExtras();
        int ganadas=b.getInt("ganadas");
        int perdidas=b.getInt("perdidas");
        TextView tvganadas=(TextView)findViewById(R.id.mganadas);
        TextView tvperdidas=(TextView)findViewById(R.id.mperdidas);
        TextView tvpuntos=(TextView)findViewById(R.id.puntos);
        tvganadas.setText(""+ganadas);
        tvperdidas.setText(""+perdidas);
        tvpuntos.setText(""+(ganadas-perdidas));
    }

    public void menu(View view){
        this.finish();
    }


}
