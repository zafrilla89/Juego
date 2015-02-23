package com.izv.android.atrapamonedas;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by ZAFRA on 18/02/2015.
 */
public class Moneda {
    private Bitmap bmp;
    private int ancho, alto;
    private int ejeY = 0;
    private int direccionY;
    private int ejeX = 0;
    private static int anchoMax=0, altoMax=0;

    public Moneda(Bitmap bmp) {
        this.bmp = bmp;
        this.ancho=bmp.getWidth();
        this.alto=bmp.getHeight();
        setPosicion(); //Para que se vaya a la posición 0,0
    }

    public static void setDimension(int ancho, int alto){
        anchoMax = ancho;
        altoMax = alto;
    }

    public void setPosicion(){ //para calcular la posicion
        ejeX = 0;
        ejeY = 0;
    }

    public void setPosicionAleatorio(int velocidad){ //para calcular la posicion
        Random rnd = new Random();
        ejeX = ancho + rnd.nextInt(anchoMax-ancho*2);
        ejeY = 0;
        direccionY=velocidad;

    }

    public void dibujar(Canvas canvas) {
        movimiento();
        canvas.drawBitmap(bmp, ejeX, ejeY, null);
    }

    private void movimiento(){
        ejeY = ejeY + direccionY;
    }

    public int  getEjeY(){
        return ejeY;
    }

    public int  getEjeX(){
        return ejeX;
    }

    public int  getAlto(){
        return alto;
    }

    public int  getAncho(){
        return ancho;
    }

}
