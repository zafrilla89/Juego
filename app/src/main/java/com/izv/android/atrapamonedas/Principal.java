package com.izv.android.atrapamonedas;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class Principal extends Activity {

    private final static int FINPARTIDA=1;
    private int ganadas=0, perdidas=0, puntuacionmaxima;
    private TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);
        SharedPreferences pc;
        pc = PreferenceManager.getDefaultSharedPreferences(
                getApplicationContext());
        puntuacionmaxima=pc.getInt("puntos",0);
        tv=(TextView)findViewById(R.id.pmaxima);
        tv.setText(puntuacionmaxima+"");
    }

    public void jugar(View view){
        Intent i= new Intent(this, Jugar.class);
        startActivityForResult(i, FINPARTIDA);
    }

    public void salir(View view){
        this.finish();
    }

    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK &&
                requestCode == FINPARTIDA) {
            ganadas = data.getIntExtra("ganadas", 0);
            perdidas = data.getIntExtra("perdidas", 0);
            int puntos=ganadas-perdidas;
            if (puntuacionmaxima<puntos){
                tv.setText(puntos+"");
                SharedPreferences pc;
                pc = PreferenceManager.getDefaultSharedPreferences(
                        getApplicationContext());
                SharedPreferences.Editor ed = pc.edit();
                ed.putInt("puntos", puntos);
                ed.commit();
            }
            Intent i=new Intent(this,Puntuacion.class);
            i.putExtra("ganadas",ganadas);
            i.putExtra("perdidas",perdidas);
            startActivity(i);
        }

    }

}

