package com.izv.android.atrapamonedas;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class Jugar extends Activity {

    private VistaJuego vjuego;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vjuego = new VistaJuego(this);
        setContentView(vjuego);
    }

}