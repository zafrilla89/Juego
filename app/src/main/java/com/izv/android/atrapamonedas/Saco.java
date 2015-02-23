package com.izv.android.atrapamonedas;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by ZAFRA on 18/02/2015.
 */
public class Saco {

    private Bitmap bmp;
    private int ancho, alto;
    private float ejeY = 0;
    private float ejeX = 0;
    private static int anchoMax = 0, altoMax = 0;

    public Saco(Bitmap bmp) {
        this.bmp = bmp;
        this.ancho = bmp.getWidth();
        this.alto = bmp.getHeight();
        setPosicion();
    }

    public boolean tocado(float x, float y) {
        return x > ejeX && x < ejeX + ancho &&
                y > ejeY && y < ejeY + alto;
    }

    public static void setDimension(int ancho, int alto) {
        anchoMax = ancho;
        altoMax = alto;
    }

    public void setPosicion(int x, int y) {
        ejeX = x;
        ejeY = y;
    }

    public void setPosicion() { //para calcular la posicion
        ejeX = 0;
        ejeY = 0;
    }

    public void dibujar(Canvas canvas) {
        canvas.drawBitmap(bmp, ejeX, ejeY, null);
    }

    public boolean colisiona(Moneda f, int delta) {
        if (this.ejeX + this.ancho - delta < f.getEjeX()) {
            return false;
        }
        if (this.ejeY + this.alto - delta < f.getEjeY()) {
            return false;
        }
        if (this.ejeX > f.getEjeX() + f.getAncho() - delta) {
            return false;
        }
        if (this.ejeY > f.getEjeY() + f.getAlto() - delta) {
            return false;
        }
        return true;
    }

    public boolean colisiona(Bomba b, int delta) {
        if (this.ejeX + this.ancho - delta < b.getEjeX()) {
            return false;
        }
        if (this.ejeY + this.alto - delta < b.getEjeY()) {
            return false;
        }
        if (this.ejeX > b.getEjeX() + b.getAncho() - delta) {
            return false;
        }
        if (this.ejeY > b.getEjeY() + b.getAlto() - delta) {
            return false;
        }
        return true;
    }

    public int  getAlto(){
        return alto;
    }

    public void setEjeX(float ejeX) {
        this.ejeX =ejeX;
    }

    public int  getAncho(){
        return ancho;
    }

}