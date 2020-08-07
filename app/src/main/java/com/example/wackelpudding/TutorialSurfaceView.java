package com.example.wackelpudding;

import android.content.Intent;
import android.graphics.PorterDuff;

import android.view.SurfaceView;
import android.graphics.Canvas;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.app.Activity;
import android.content.Context;

import android.view.SurfaceHolder;

import java.util.*;
/** @author-Kurt
 * Teile des Codes aus den Folien, andere Teile aus der Quelle:
 * http://obviam.net/index.php/displaying-graphics-with-android/
 * http://obviam.net/index.php/moving-images-on-the-screen-with-android/*/
public class TutorialSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private Tut_Thread thread;
    private boolean touched;//wird ein Button gedrueckt?


  //  private Bitmap bild = BitmapFactory.decodeResource(getResources(), R.drawable.tut1);
    private  Bitmap bild1 = BitmapFactory.decodeResource(getResources(), R.drawable.tut1);
    private  Bitmap bild2 = BitmapFactory.decodeResource(getResources(), R.drawable.tut2);
    private  Bitmap bild3 = BitmapFactory.decodeResource(getResources(), R.drawable.tut3);
    private  Bitmap bild4 = BitmapFactory.decodeResource(getResources(), R.drawable.tut4);

    //Bitmaps fuer die Figur Posen

    private Bitmap einrollen = BitmapFactory.decodeResource(getResources(), R.drawable.einroller_1);
    private Bitmap einrollen2 = BitmapFactory.decodeResource(getResources(), R.drawable.einroller_2);
    private Bitmap einrollen3 = BitmapFactory.decodeResource(getResources(), R.drawable.einroller_3);
    private Bitmap einrollen4 = BitmapFactory.decodeResource(getResources(), R.drawable.einroller_4);

    private Bitmap ausbreiten = BitmapFactory.decodeResource(getResources(), R.drawable.ausbreiten_1);
    private Bitmap ausbreiten2 = BitmapFactory.decodeResource(getResources(), R.drawable.ausbreiten_2);
    private Bitmap ausbreiten3 = BitmapFactory.decodeResource(getResources(), R.drawable.ausbreiten_3);
    private Bitmap ausbreiten4 = BitmapFactory.decodeResource(getResources(), R.drawable.ausbreiten_4);


    private Bitmap hecht = BitmapFactory.decodeResource(getResources(), R.drawable.hecht_2);
    private Bitmap hecht2 = BitmapFactory.decodeResource(getResources(), R.drawable.hecht_3);
    private Bitmap hecht3 = BitmapFactory.decodeResource(getResources(), R.drawable.hecht_4);
    private Bitmap hecht4 = BitmapFactory.decodeResource(getResources(), R.drawable.hecht_1);

    private Figur figur;

    //hier werden 7 Bloecke gebraucht
    private Vector<AnimatedBlock> blocke = new Vector<AnimatedBlock>(7);

    //Bitmaps fuer Rotation der Bloecke
    private Bitmap blocka = BitmapFactory.decodeResource(getResources(), R.drawable.block_a);
    private Bitmap blockb = BitmapFactory.decodeResource(getResources(), R.drawable.block_b);
    private Bitmap blockc = BitmapFactory.decodeResource(getResources(), R.drawable.block_c);
    private Bitmap blockd = BitmapFactory.decodeResource(getResources(), R.drawable.block_d);

    //Bitmap fuer fixen Block
    private Bitmap blocka2 = BitmapFactory.decodeResource(getResources(), R.drawable.block_a_locked);
    private Bitmap blockc2 = BitmapFactory.decodeResource(getResources(), R.drawable.block_c_locked);
    private Bitmap blockd2 = BitmapFactory.decodeResource(getResources(), R.drawable.block_d_locked);

    private Bitmap Leben = BitmapFactory.decodeResource(getResources(), R.drawable.teller_heller);

    private Bitmap Eingang = BitmapFactory.decodeResource(getResources(), R.drawable.eingang);
    private Bitmap Ausgang = BitmapFactory.decodeResource(getResources(), R.drawable.ausgangb);

    private int parameter = 10;//Geschwindigkeit der Figur
    private int width = bild1.getWidth();
    private int height = bild1.getHeight();

    //diverse wichtige Koordinaten zur Platzierung
    private int x0;
    private int x1;
    private int x2;
    private int x3;
    private int x4;
    private int x5;
    private int x6;
    private int x7;
    private int x8;
    private int x9;
    private int x10;

    private int y0;
    private int y1;
    private int y2;
    private int y3;
    private int y4;

    //dazu da um bei Levelabschluss Musik richtig zu beenden
    private MediaPlayer helper;

    private int level = 1;

    //Initialisierungen
    public TutorialSurfaceView(Context context) {
        super(context);
        // adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this);

        x0 = (width/24)*2;
        x1 = (width/24)*4;
        x2 = (width/24)*6;
        x3 = (width/24)*8;
        x4 = (width/24)*10;
        x5 = (width/24)*12;
        x6 = (width/24)*14;
        x7 = (width/24)*16;
        x8 = (width/24)*18;
        x9 = (width/24)*20;
        x10 =(width/24)*22;

        y0 = (height/12)*2;
        y1 = (height/12)*4;
        y2 = (height/12)*6;
        y3 = (height/12)*8;
        y4 = (height/12)*10;


        figur = new Figur(einrollen, x5, y1);

        //erstellt die Bloecke an den entsprechenden Stellen
        blocke.add(new AnimatedBlock(blocka2,x10,y1));
        blocke.add(new AnimatedBlock(blockd2,x10,y0));
        blocke.add(new AnimatedBlock(blocka2,x5,y2));
        blocke.add(new AnimatedBlock(blockd2,x9,y0));
        blocke.add(new AnimatedBlock(blocka2,x9,y2));
        blocke.add(new AnimatedBlock(blockc2,x6,y2));
        blocke.add(new AnimatedBlock(blockc,blockd,blocka,blockb,x6,y4));


        thread = new Tut_Thread(getHolder(), this); // gameloop erstellen

        setFocusable(true); // um events handeln zu koennen
    }


    protected void onDraw(Canvas canvas) {
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        // canvas.drawColor(Color.BLACK);

       // canvas.drawBitmap(bild,0,0,null);


        //je nachdem in welcher Phase wir sind, wird dementsprechend alles aufgebaut

        switch(level){
           case 1:
                canvas.drawBitmap(bild1,0,0,null);//zuerst Hintergrund zeichnen

                blocke.get(0).draw(canvas);

                canvas.drawBitmap(Eingang,x5,y1,null);
                canvas.drawBitmap(Ausgang,x0,y1,null);

                break;

            case 2:
                canvas.drawBitmap(bild2,0,0,null);

                blocke.get(1).draw(canvas);

                canvas.drawBitmap(Eingang,x0,y0,null);
                canvas.drawBitmap(Ausgang,x10,y4,null);

                break;

            case 3:
                canvas.drawBitmap(bild3,0,0,null);

                blocke.get(2).draw(canvas);

                canvas.drawBitmap(Eingang,x0,y2,null);
                canvas.drawBitmap(Ausgang,x10,y2,null);

                break;

            case 4:
                canvas.drawBitmap(bild4,0,0,null);

                blocke.get(3).draw(canvas);
                blocke.get(4).draw(canvas);
                blocke.get(5).draw(canvas);
                blocke.get(6).draw(canvas);

                canvas.drawBitmap(Eingang,x0,y0,null);
                canvas.drawBitmap(Ausgang,x10,y4,null);

                break;

            default:
                canvas.drawBitmap(bild1,0,0,null);

                blocke.get(0).draw(canvas);

                canvas.drawBitmap(Eingang,x3,y2,null);
                canvas.drawBitmap(Ausgang,x0,y2,null);


                break;
        }
        figur.draw(canvas);
    }

    //wird in jedem Updatezyklus aufgerufen
    public void update() {


        //falls die Figur ans Ziel kommt

        if(ziel1()==true){
            touched = false;
            level = 2;

        }

        else if(ziel2()==true){
            touched = false;
            level = 3;

        }
        else if(ziel3()==true){
            touched = false;
            level = 4;
        }

        else if(ziel()==true){//am Endziel angekommen -> zum Hauptmenu


            /*Code dazu aus: http://www.anddev.org/surfaceview_to_new_activity_issue-t8376.html
             * http://stackoverflow.com/questions/4811366/how-to-make-a-class-that-extends-surfaceview-start-an-activity*/


            helper.stop();
            helper.release();
            Context context = getContext();
            Intent myIntent = new Intent(context, Hauptmenu.class);

            thread.setRunning(false);
            ((Activity)context).startActivity(myIntent);
        }

        //ist ein Button gedrueckt worden oder nicht
        if (touched == false) {
            figur.getMovement().setxv(0);
            figur.getMovement().setyv(0);
        }

        else {
            figur.getMovement().setxv(parameter);
            figur.getMovement().setyv(parameter);
        }

        //wenn die Figur an ausserhalb des Levels kommt, alle Einstellungen resetten
        if (outofBounds()==true) {

            switch(level){
                case 1:
                    figur.setX(x5);
                    figur.setY(y1);

                    figur.getMovement().setxDirection(Movement.right);
                    figur.getMovement().setyDirection(Movement.none);
                    figur.setBitmap(einrollen);

                    touched = false;
                    break;
                case 2:
                    figur.setX(x0);
                    figur.setY(y0);

                    figur.getMovement().setxDirection(Movement.right);
                    figur.getMovement().setyDirection(Movement.none);
                    figur.setBitmap(einrollen);
                    touched = false;
                    break;
                case 3:
                    figur.setX(x0);
                    figur.setY(y0);

                    figur.getMovement().setxDirection(Movement.right);
                    figur.getMovement().setyDirection(Movement.none);
                    figur.setBitmap(einrollen);
                    touched = false;
                    break;
                case 4:
                    figur.setX(x0);
                    figur.setY(y0);

                    figur.getMovement().setxDirection(Movement.right);
                    figur.getMovement().setyDirection(Movement.none);
                    figur.setBitmap(einrollen);
                    touched = false;

                    blocke.get(6).setBitmaps(blockc,blockd,blocka,blockb);


                    break;
                default:
                    figur.setX(x3);
                    figur.setY(y2);

                    figur.getMovement().setxDirection(Movement.right);
                    figur.getMovement().setyDirection(Movement.none);
                    figur.setBitmap(einrollen);
                    touched = false;
                    break;
            }

        }

        int t1 = collision();//ermittelt ob/mit welchem Block kollidiert wurde

        //Kollisionsbehandlung
        if(t1>=0) {

            //Hilfsvariablen
            AnimatedBlock currentblock = blocke.get(t1);
            Bitmap currentpic = currentblock.getBitmap();


            if(level==1){

                if (figur.getMovement().getxDirection()==Movement.right) {//von welcher Seite kommt die Figur?
                    if (figur.getBitmap() == ausbreiten) {//welche Pose hat die Figur?
                        if (currentpic == blocka2) {//auf was kollidiert die Figur?

                            figur.getMovement().setxDirection(Movement.left);//neue Richtung der Figur setzen

                        }
                    } else {
                        if (figur.getBitmap() == einrollen) {
                            if (currentpic == blocka2) {
                                figur.getMovement().setxDirection(Movement.none);
                                figur.getMovement().setyDirection(Movement.up);
                                figur.setBitmap(einrollen4);
                            }
                        }
                    }
                }

            }

            else if(level==2){
                if (figur.getMovement().getxDirection()==Movement.right) {
                    if (figur.getBitmap()==einrollen){

                        //wie steht der Block bezogen zur Figur
                        if(currentpic==blockd2) {
                            figur.getMovement().setxDirection(Movement.none);
                            figur.getMovement().setyDirection(Movement.down);
                            figur.setBitmap(einrollen2);

                        }

                    }
                    else if(figur.getBitmap()==ausbreiten) {
                        if(currentpic==blockd2) {
                            figur.getMovement().setxDirection(Movement.left);

                        }
                    }
                }


            }
            else if(level==3){
                if (figur.getMovement().getxDirection()==Movement.right) {
                    if (figur.getBitmap()==einrollen){

                        if(currentpic==blockd2) {
                            figur.getMovement().setxDirection(Movement.none);
                            figur.getMovement().setyDirection(Movement.down);
                            figur.setBitmap(einrollen2);

                        }

                    }
                    else if(figur.getBitmap()==ausbreiten) {
                        if(currentpic==blockd2) {
                            figur.getMovement().setxDirection(Movement.left);

                        }
                    }
                }

            }

            else{

                if(currentpic==blockd2){

                    //die Figur kann hier nur von links/oben auf diesen Block kommen & kollidieren

                    if (figur.getMovement().getxDirection()==Movement.right) {

                        if (figur.getBitmap()==einrollen){

                                figur.getMovement().setxDirection(Movement.none);
                                figur.getMovement().setyDirection(Movement.down);
                                figur.setBitmap(einrollen2);



                        }
                        else if(figur.getBitmap()==ausbreiten) {
                            figur.getMovement().setxDirection(Movement.left);
                        }

                    }
                    else if(figur.getMovement().getyDirection()==Movement.up){
                        if (figur.getBitmap()==einrollen4){

                            figur.getMovement().setxDirection(Movement.left);
                            figur.getMovement().setyDirection(Movement.none);

                            figur.setBitmap(einrollen3);



                        }
                        else if(figur.getBitmap()==ausbreiten2) {
                            figur.getMovement().setxDirection(Movement.none);
                            figur.getMovement().setyDirection(Movement.down);


                        }

                    }


                }
                else if(currentpic==blocka2){

                    if (figur.getMovement().getxDirection()==Movement.right) {

                        if (figur.getBitmap()==einrollen){

                            figur.getMovement().setxDirection(Movement.none);
                            figur.getMovement().setyDirection(Movement.up);
                            figur.setBitmap(einrollen4);



                        }
                        else if(figur.getBitmap()==ausbreiten) {
                            figur.getMovement().setxDirection(Movement.left);
                        }

                    }
                    else if(figur.getMovement().getyDirection()==Movement.down){
                        if (figur.getBitmap()==einrollen2){

                            figur.getMovement().setxDirection(Movement.left);
                            figur.getMovement().setyDirection(Movement.none);

                            figur.setBitmap(einrollen3);



                        }
                        else if(figur.getBitmap()==ausbreiten2) {
                            figur.getMovement().setxDirection(Movement.none);
                            figur.getMovement().setyDirection(Movement.up);


                        }

                    }

                }
                else if(currentpic==blockc2){

                    if (figur.getMovement().getxDirection()==Movement.left) {

                        if (figur.getBitmap()==einrollen3){

                            figur.getMovement().setxDirection(Movement.none);
                            figur.getMovement().setyDirection(Movement.down);
                            figur.setBitmap(einrollen2);



                        }
                        else if(figur.getBitmap()==ausbreiten) {
                            figur.getMovement().setxDirection(Movement.right);
                        }

                    }
                    else if(figur.getMovement().getyDirection()==Movement.up){
                        if (figur.getBitmap()==einrollen4){

                            figur.getMovement().setxDirection(Movement.right);
                            figur.getMovement().setyDirection(Movement.none);

                            figur.setBitmap(einrollen);



                        }
                        else if(figur.getBitmap()==ausbreiten2) {
                            figur.getMovement().setxDirection(Movement.none);
                            figur.getMovement().setyDirection(Movement.down);
                        }

                    }

                }
                else {

                    if(currentpic!=blockb){
                         //da wir wissen, dass die Figur nur von oben kollidieren kann ist Movement= down

                        if (figur.getBitmap()==einrollen2){

                                figur.getMovement().setxDirection(Movement.none);
                                figur.getMovement().setyDirection(Movement.up);

                                figur.setBitmap(einrollen4);

                         }
                            else if(figur.getBitmap()==ausbreiten2) {
                                figur.getMovement().setxDirection(Movement.none);
                                figur.getMovement().setyDirection(Movement.up);
                         }

                        currentblock.rotate();
                        blocke.set(t1, currentblock);


                    }
                    else {
                        if (figur.getBitmap()==einrollen2){

                            figur.getMovement().setxDirection(Movement.right);
                            figur.getMovement().setyDirection(Movement.none);

                            figur.setBitmap(einrollen);

                        }
                        else if(figur.getBitmap()==ausbreiten2) {
                            figur.getMovement().setxDirection(Movement.none);
                            figur.getMovement().setyDirection(Movement.up);
                        }
                        currentblock.rotate();
                        blocke.set(t1, currentblock);

                    }


                }


            }


            //hier wird die Position der Figur berichtigt, da sie bei Kollision nicht genau auf block-x UND y-Axis ist
             figur.setX(currentblock.getX());
             figur.setY(currentblock.getY());

            }

        figur.update();
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }


    public boolean outofBounds(){
        int collideradiusx = figur.getMovement().getxv();
        int collideradiusy = figur.getMovement().getyv();

        if( (figur.getX() <= 0 + collideradiusx || figur.getX() >= width - collideradiusx)||
                (figur.getY() <= 0 + collideradiusy || figur.getY() >= height - collideradiusy)){

            return true;
        }
        else {
            return false;
        }

    }

    /**retourniert jenen Blockindex, mit dem die Figur kollidiert
     *
     * @return int Index aus der block-Liste mit dem die Figur eine Kollision hat
     * */
    public int collision() {
        int collideradiusx = figur.getMovement().getxv();
        int collideradiusy = figur.getMovement().getyv();

        //die Koordinaten der Bloecke
        int blockx0 = blocke.get(0).getX();
        int blocky0 = blocke.get(0).getY();

        int blockx1 = blocke.get(1).getX();
        int blocky1 = blocke.get(1).getY();

        int blockx2 = blocke.get(2).getX();
        int blocky2 = blocke.get(2).getY();

        int blockx3 = blocke.get(3).getX();
        int blocky3 = blocke.get(3).getY();

        int blockx4 = blocke.get(4).getX();
        int blocky4 = blocke.get(4).getY();

        int blockx5 = blocke.get(5).getX();
        int blocky5 = blocke.get(5).getY();

        int blockx6 = blocke.get(6).getX();
        int blocky6 = blocke.get(6).getY();

        switch(level) {//in welchem Level befinden wir uns?
            case 1:
                //kontrolliere mit welchem Block genau die Figur kollidiert
                if ( (figur.getX()>blockx0 - collideradiusx && figur.getX()<blockx0 + collideradiusx) &&
                        (figur.getY()>blocky0 - collideradiusy && figur.getY()<blocky0 + collideradiusy) ) {

                    return 0;
                }
                else//falls keine Kollision
                    return -1;


            case 2:

                //kontrolliere mit welchem Block genau die Figur kollidiert
                if ( (figur.getX()>blockx1 - collideradiusx && figur.getX()<blockx1 + collideradiusx) &&
                        (figur.getY()>blocky1 - collideradiusy && figur.getY()<blocky1 + collideradiusy) ) {

                    return 1;
                }
                else//falls keine Kollision
                    return -1;



            case 3:
                //kontrolliere mit welchem Block genau die Figur kollidiert
                if ( (figur.getX()>blockx2 - collideradiusx && figur.getX()<blockx2 + collideradiusx) &&
                        (figur.getY()>blocky2 - collideradiusy && figur.getY()<blocky2 + collideradiusy) ) {

                    return 2;
                }
                else//falls keine Kollision
                    return -1;

            case 4:
                //kontrolliere mit welchem Block genau die Figur kollidiert
                if ( (figur.getX()>blockx3 - collideradiusx && figur.getX()<blockx3 + collideradiusx) &&
                        (figur.getY()>blocky3 - collideradiusy && figur.getY()<blocky3 + collideradiusy) ) {

                    return 3;
                }

                else if( (figur.getX()>blockx4 - collideradiusx && figur.getX()<blockx4 + collideradiusx) &&
                            (figur.getY()>blocky4 - collideradiusy && figur.getY()<blocky4 + collideradiusy) ) {

                    return 4;
                }

                else if ( (figur.getX()>blockx5 - collideradiusx && figur.getX()<blockx5 + collideradiusx) &&
                            (figur.getY()>blocky5 - collideradiusy && figur.getY()<blocky5 + collideradiusy) ) {

                    return 5;
                }

                else if ( (figur.getX()>blockx6 - collideradiusx && figur.getX()<blockx6 + collideradiusx) &&
                            (figur.getY()>blocky6 - collideradiusy && figur.getY()<blocky6 + collideradiusy) ) {

                    return 6;
                }
                else//falls keine Kollision
                    return -1;


            default:
                //kontrolliere mit welchem Block genau die Figur kollidiert
                if ( (figur.getX()>blockx0 - collideradiusx && figur.getX()<blockx0 + collideradiusx) &&
                        (figur.getY()>blocky0 - collideradiusy && figur.getY()<blocky0 + collideradiusy) ) {

                    return 0;
                }
                else//falls keine Kollision
                    return -1;

        }

    }

    /**gibt Daten der Figur aus
     *
     * @return Figur Object der Klasse Figur, die alle relevanten Infos enthaelt */
    public Figur getFigur() {
        return figur;
    }

    /**Hilfsmethode fuer Activity bei Button press der Pose, damit das richtige Bitmap der Pose bzglich der Richtung kommt
     *
     * @return Bitmap gibt das Figurbild entsprechend der Ausrichtung aus*/
    public Bitmap geteinrollen() {
        if(figur.getMovement().getxDirection()==Movement.left) {
            return einrollen3;
        }
        else if(figur.getMovement().getyDirection()==Movement.up){
            return einrollen4;
        }
        else if(figur.getMovement().getyDirection()==Movement.down){
            return einrollen2;
        }
        else {
            return einrollen;
        }
    }

    /**Hilfsmethode fuer Activity bei Button press der Pose, damit das richtige Bitmap der Pose bzglich der Richtung kommt
     *
     * @return Bitmap gibt das Figurbild entsprechend der Ausrichtung aus*/
    public Bitmap getausbreiten() {
        if (figur.getMovement().getyDirection()==Movement.up||figur.getMovement().getyDirection()==Movement.down) {
            return ausbreiten2;
        }
        else {
            return ausbreiten;
        }
    }

    /**Hilfsmethode fuer Activity bei Button press der Pose, damit das richtige Bitmap der Pose bzglich der Richtung kommt
     *
     * @return Bitmap gibt das Figurbild entsprechend der Ausrichtung aus*/
    public Bitmap gethecht() {
        if(figur.getMovement().getxDirection()==Movement.left) {
            return hecht3;
        }
        else if(figur.getMovement().getyDirection()==Movement.up){
            return hecht4;
        }
        else if(figur.getMovement().getyDirection()==Movement.down){
            return hecht2;
        }
        else {
            return hecht;
        }
    }


    /** Check ob Figur an Ziel von Phase1 gekommen ist
     *
     * @return boolean Wert ob Phase1 beendet ist */
    public boolean ziel1() {
        int collideradiusx = figur.getMovement().getxv();
        int collideradiusy = figur.getMovement().getyv();

        if ( (figur.getX() > x0 - collideradiusx && figur.getX() < x0 + collideradiusx) &&
                (figur.getY() >= y1 - collideradiusy && figur.getY() <= y1 + collideradiusy) && level==1){

            figur.setMovement(0);
            figur.setX(x0);
            figur.setY(y0);
            figur.setBitmap(einrollen);
            figur.getMovement().setxDirection(Movement.right);
            figur.getMovement().setyDirection(Movement.none);
            return true;
        }
        else {
            return false;
        }
    }

    /** Check ob Figur an Ziel von Phase2 gekommen ist
     *
     * @return boolean Wert ob Phase2 beendet ist */
    public boolean ziel2() {
        int collideradiusx = figur.getMovement().getxv();
        int collideradiusy = figur.getMovement().getyv();

        if ( (figur.getX() > x10 - collideradiusx && figur.getX() < x10 + collideradiusx) &&
                (figur.getY() >= y4 - collideradiusy && figur.getY() <= y4 + collideradiusy) && level==2){
            figur.setMovement(0);
            figur.setX(x0);
            figur.setY(y2);
            figur.setBitmap(einrollen);
            figur.getMovement().setxDirection(Movement.right);
            figur.getMovement().setyDirection(Movement.none);
            return true;
        }
        else {
            return false;
        }
    }

    /** Check ob Figur an Ziel von Phase3 gekommen ist
     *
     * @return boolean Wert ob Phase3 beendet ist */
    public boolean ziel3() {
        int collideradiusx = figur.getMovement().getxv();
        int collideradiusy = figur.getMovement().getyv();

        if ( (figur.getX() > x10 - collideradiusx && figur.getX() < x10 + collideradiusx) &&
                (figur.getY() >= y2 - collideradiusy && figur.getY() <= y2 + collideradiusy) && level==3){
            figur.setMovement(0);
            figur.setX(x0);
            figur.setY(y0);
            figur.setBitmap(einrollen);
            figur.getMovement().setxDirection(Movement.right);
            figur.getMovement().setyDirection(Movement.none);
            return true;
        }
        else {
            return false;
        }
    }


    /**ob die Figur ans Ziel kommt
     * @return boolean true/false, je nachdem ob am Ziel angekommen*/
    public boolean ziel() {
        int collideradiusx = figur.getMovement().getxv();
        int collideradiusy = figur.getMovement().getyv();

        if ( (figur.getX() > x10 - collideradiusx && figur.getX() < x10 + collideradiusx) &&
                (figur.getY() >= y4 - collideradiusy && figur.getY() <= y4 + collideradiusy) && level==4){
            figur.setMovement(0);
            return true;
        }
        else {
            return false;
        }

    }

    public void touched() {
        // TODO Auto-generated method stub
        touched = true;
    }

    public void setmedia(MediaPlayer x) {
        helper = x;
    }


    /**Hilfsmethode fuer Activity
     * @return Tut_Thread retourniert den aktuellen Thread*/
    public Tut_Thread getthread() {
        return thread;
    }


    /**Quelle: http://panjutorials.de/tutorial-28-den-dialog-anzeigen-lassen-onbackpressed/
     * nach Aufruf des Dialogs muss ein neuer MainThread0 erstellt werden, um wieder updaten zu koennen*/
    public void resumen() {
        thread = new Tut_Thread(getHolder(),this);
        thread.setRunning(true);
        thread.start();
    }


    /**
     * legt die Geschwindigkeit der Figur fest
     *
     * @param i Geschwindigkeitswert von 1-20
     * */
    public void setParameter(int i){
        parameter = i;
    }
}

