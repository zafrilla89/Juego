package com.izv.android.atrapamonedas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.VelocityTracker;

public class VistaJuego  extends SurfaceView implements SurfaceHolder.Callback  {

    /*******************************************************************************************/
    /*     constructor                                                                         */
    /*******************************************************************************************/
    private Bitmap imagenmoneda, imagensaco, imagenbomba;
    private int alto, ancho;
    private HebraJuego hebraJuego;
    private Saco saco;
    private Bomba[] bombas;
    private Moneda moneda;
    private int monedascogidas=0;
    private int monedasperdidas=0;
    private int velocidad=5, velocidadbomba=5;
    private Context context;
    private boolean mover=false;

    public VistaJuego(Context contexto){
        super(contexto);
        this.context=contexto;
        getHolder().addCallback(this);
        imagenmoneda = BitmapFactory.decodeResource(getResources(), R.drawable.monedas);
        imagensaco = BitmapFactory.decodeResource(getResources(), R.drawable.sacodemonedas);
        imagenbomba = BitmapFactory.decodeResource(getResources(), R.drawable.bomba);
        hebraJuego = new HebraJuego(this);
        bombas = new Bomba[4];
        for (int i = 0; i < 4; i++) {
            bombas[i] = new Bomba(imagenbomba);
        }
        saco=new Saco(imagensaco);
        moneda=new Moneda(imagenmoneda);
    }

    /*******************************************************************************************/
    /*    métodos de la clase surfaceview                                                      */
    /*******************************************************************************************/

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        saco.dibujar(canvas);
        moneda.dibujar(canvas);
        for(Bomba b: bombas){
            b.dibujar(canvas);
        }
        if (moneda.getEjeY()>alto) {
            monedasperdidas=monedasperdidas+1;
            if (velocidad<45) {
                velocidad = velocidad + 2;
            }
            moneda.setPosicionAleatorio(velocidad);
        }
        for (int i=0;i<bombas.length;i++){
            if (bombas[i].getEjeY()>alto){
                if (i==0) {
                    if (velocidadbomba < 45) {
                        velocidadbomba = velocidadbomba + 2;
                    }
                }
                bombas[i].setPosicionAleatorio(velocidadbomba);
                while (bombas[i].colisiona(moneda,-30)){
                    bombas[i].setPosicionAleatorio(velocidadbomba);
                }
            }
        }
        if (saco.colisiona(moneda,0)) {
            monedascogidas=monedascogidas+1;
            if (velocidad<45) {
                velocidad = velocidad + 2;
            }
            moneda.setPosicionAleatorio(velocidad);
        }
        for (int i=0;i<bombas.length;i++){
            if (saco.colisiona(bombas[i],0)){
                Intent in = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("ganadas", monedascogidas);
                bundle.putInt("perdidas", monedasperdidas);
                in.putExtras(bundle);
                ((Activity) context).setResult(Activity.RESULT_OK, in);
                ((Activity) context).finish();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x,y;
        x=event.getX();
        y=event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (saco.tocado(x,y)){
                    if(x>0) {
                        if (x>ancho-saco.getAncho()) {
                            saco.setEjeX(ancho-saco.getAncho());
                        }else{
                            saco.setEjeX(x);
                        }
                        mover = true;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mover==true) {
                    if(x>0) {
                        if (x>ancho-saco.getAncho()) {
                            saco.setEjeX(ancho-saco.getAncho());
                        }else{
                            saco.setEjeX(x);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                mover=false;
                break;
        }
        return true;
    }

    /*******************************************************************************************/
    /*     interfaz callback                                                                   */
    /*******************************************************************************************/

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        hebraJuego = new HebraJuego(this);
        hebraJuego.setFuncionando(true);
        hebraJuego.start();
    }

    @Override
    public void surfaceChanged (SurfaceHolder holder, int format, int width, int height) {
        alto=height;
        ancho=width;
        Moneda.setDimension(ancho, alto);
        moneda.setPosicionAleatorio(velocidad);
        Bomba.setDimension(ancho,alto);
        for (int i=0;i<bombas.length;i++) {
            bombas[i].setPosicionAleatorio(velocidadbomba);
            while (bombas[i].colisiona(moneda,-30)){
                bombas[i].setPosicionAleatorio(velocidadbomba);
            }
        }
        Saco.setDimension(ancho,alto);
        saco.setPosicion(0, alto-saco.getAlto());
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        pararhebra();
    }

    public void pararhebra(){
        boolean reintentar = true;
        hebraJuego.setFuncionando(false);
        while (reintentar) {
            try {
                hebraJuego.join();
                reintentar = false;
            } catch (InterruptedException e) {
            }
        }
    }
}
