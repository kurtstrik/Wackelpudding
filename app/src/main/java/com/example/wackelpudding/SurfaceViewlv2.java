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
/**
 * Teile aus der Quelle:
 * references used:
 *
 * http://obviam.net/index.php/displaying-graphics-with-android/
 * http://obviam.net/index.php/moving-images-on-the-screen-with-android/*/
public class SurfaceViewlv2 extends SurfaceView implements SurfaceHolder.Callback {
	
	private MainThread2 thread;
	private boolean touched;//wird ein Button gedrueckt? | was a button touched?
	private Bitmap hintergrund = BitmapFactory.decodeResource(getResources(), R.drawable.hintergrund2);

	//Bitmaps fuer die Figur Posen | bitmaps for the figure poses
	private Bitmap einrollen = BitmapFactory.decodeResource(getResources(), R.drawable.einrollera);
	private Bitmap einrollen2 = BitmapFactory.decodeResource(getResources(), R.drawable.einrollerb);
	private Bitmap einrollen3 = BitmapFactory.decodeResource(getResources(), R.drawable.einrollerc);
	private Bitmap einrollen4 = BitmapFactory.decodeResource(getResources(), R.drawable.einrollerd);
	
	private Bitmap ausbreiten = BitmapFactory.decodeResource(getResources(), R.drawable.ausbreitena);
	private Bitmap ausbreiten2 = BitmapFactory.decodeResource(getResources(), R.drawable.ausbreitenb);
	
	private Bitmap hecht = BitmapFactory.decodeResource(getResources(), R.drawable.hechtb);
	private Bitmap hecht2 = BitmapFactory.decodeResource(getResources(), R.drawable.hechtc);
	private Bitmap hecht3 = BitmapFactory.decodeResource(getResources(), R.drawable.hechtd);
	private Bitmap hecht4 = BitmapFactory.decodeResource(getResources(), R.drawable.hechta);
	private Figur figur;

	private int parameter = 10;//Geschwindigkeit der Figur | figure speed
	private int width = hintergrund.getWidth();
	private int height = hintergrund.getHeight();

	//es gibt 15 Bloecke in diesem Level | there are 15 blocks in this level
	private Vector<AnimatedBlock> blocke = new Vector<AnimatedBlock>(15);
	//es gibt 11 Fallen in diesem Level | there are 11 traps in this level
	private Vector<AnimatedFalle> falle = new Vector<AnimatedFalle>(11);

	//Bitmaps fuer Rotation der Bloecke | bitmaps for rotation of the blocks
	private Bitmap a = BitmapFactory.decodeResource(getResources(), R.drawable.block_a);
	private Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.block_b);
	private Bitmap c = BitmapFactory.decodeResource(getResources(), R.drawable.block_c);
	private Bitmap d = BitmapFactory.decodeResource(getResources(), R.drawable.block_d);

	//Bitmaps fuer Fallen | bitmaps for the traps
	private Bitmap e = BitmapFactory.decodeResource(getResources(), R.drawable.fallea);
	private Bitmap f = BitmapFactory.decodeResource(getResources(), R.drawable.fallea2);
	private Bitmap g = BitmapFactory.decodeResource(getResources(), R.drawable.fallea3);
	private Bitmap h = BitmapFactory.decodeResource(getResources(), R.drawable.fallea4);
	private Bitmap i = BitmapFactory.decodeResource(getResources(), R.drawable.fallea5);
		
	private Bitmap j = BitmapFactory.decodeResource(getResources(), R.drawable.falleb);
	private Bitmap k = BitmapFactory.decodeResource(getResources(), R.drawable.falleb2);
	private Bitmap l = BitmapFactory.decodeResource(getResources(), R.drawable.falleb3);
	private Bitmap m = BitmapFactory.decodeResource(getResources(), R.drawable.falleb4);
	private Bitmap n = BitmapFactory.decodeResource(getResources(), R.drawable.falleb5);
	
	private Bitmap Leben = BitmapFactory.decodeResource(getResources(), R.drawable.teller_heller);
	
	private Bitmap Eingang = BitmapFactory.decodeResource(getResources(), R.drawable.eingang);
	private Bitmap Ausgang = BitmapFactory.decodeResource(getResources(), R.drawable.ausgangb);



	//diverse wichtige Koordinaten zur Platzierung | various important coordinates for placing the objects
	private int x0 = 0;
	private int x1 = width/15;
	private int x2 =  (width/15)*2;   // 65;
	private int x3 =  (width/15)*3;   // 130;
	private int x4 =  (width/15)*4;   // 195;
	private int x5 =  (width/15)*5;    //260;
	private int x6 =  (width/15)*6;    //325;
	private int x7 =  (width/15)*7;    //390;
	private int x8 =  (width/15)*8;    //455;
	private int x9 =  (width/15)*9;    //520;
	private int x10 = (width/15)*10;   // 585;
	private int x11 = (width/15)*11;   // 650;
	private int x12 = (width/15)*12;   // 600;
	private int x13 = (width/15)*13;   // 650;
	private int x14 = (width/15)*14;   // 700;
	
	private int y0 = (height/10)*1;  //0;
	private int y1 = (height/10)*3;  //110;
	private int y2 = (height/10)*5;  //220;
	private int y3 = (height/10)*7;  //330;
	private int y4 = (height/10)*9;  //440;

	//dazu da um bei Levelabschluss Musik richtig zu beenden | properly stops music when quitting
	private MediaPlayer helper;
	
	public SurfaceViewlv2(Context context) {
        super(context);
	        // adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this);
        
        figur = new Figur(einrollen, x0, y2);

		//erstellt die Bloecke an den entsprechenden Stellen
		//creates the blocks at designated places
		blocke.add(new AnimatedBlock(c,d,a,b,x2,y0));
		blocke.add(new AnimatedBlock(a,b,c,d,x2,y2));
		blocke.add(new AnimatedBlock(d,a,b,c,x2,y4));
		blocke.add(new AnimatedBlock(d,a,b,c,x4,y0));
		blocke.add(new AnimatedBlock(a,b,c,d,x4,y2));
		blocke.add(new AnimatedBlock(d,a,b,c,x4,y4));
		blocke.add(new AnimatedBlock(b,c,d,a,x6,y0));
		blocke.add(new AnimatedBlock(c,d,a,b,x6,y2));
		blocke.add(new AnimatedBlock(b,c,d,a,x6,y4));
		blocke.add(new AnimatedBlock(b,c,d,a,x8,y0));
		blocke.add(new AnimatedBlock(c,d,a,b,x8,y2));
		blocke.add(new AnimatedBlock(b,c,d,a,x8,y4));
		blocke.add(new AnimatedBlock(b,c,d,a,x12,y0));
		blocke.add(new AnimatedBlock(c,d,a,b,x12,y2));
		blocke.add(new AnimatedBlock(b,c,d,a,x12,y4));

		//erstellt die Fallen an den entsprechenden Stellen
		//creates traps at designated places
		falle.add(new AnimatedFalle(j,k,l,m,n,x0, y0,5,5));
		falle.add(new AnimatedFalle(j,k,l,m,n,x0, y4,5,5));
		falle.add(new AnimatedFalle(e,f,g,h,i,x2, y1, 5,5));
		falle.add(new AnimatedFalle(j,k,l,m,n,x3, y4, 5,5));
		falle.add(new AnimatedFalle(j,k,l,m,n,x5, y2, 5,5));
		falle.add(new AnimatedFalle(j,k,l,m,n,x7, y4, 5,5));
		falle.add(new AnimatedFalle(e,f,g,h,i,x8, y1, 5,5));
		falle.add(new AnimatedFalle(j,k,l,m,n,x10, y0, 5,5));
		falle.add(new AnimatedFalle(j,k,l,m,n,x10, y2, 5,5));
		falle.add(new AnimatedFalle(j,k,l,m,n,x13, y0, 5,5));
		falle.add(new AnimatedFalle(j,k,l,m,n,x13, y4, 5,5));
		
		thread = new MainThread2(getHolder(), this); // gameloop
		
		setFocusable(true); // um events handeln zu koennen | to handle events
	}

	/**zeichnet alle nichtstatischen Objekte in der GameLoop auf den canvas
	 *
	 * draws all non static objects in the gameloop on the canvas
	 * */
	protected void onDraw(Canvas canvas) {
		//damit vorige draw calls geloescht werden | clears canvas from previous draw call
		canvas.drawColor(0, PorterDuff.Mode.CLEAR);

    	canvas.drawBitmap(hintergrund,0,0,null);
		    	
		blocke.get(0).draw(canvas);
		blocke.get(1).draw(canvas);
		blocke.get(2).draw(canvas);
		blocke.get(3).draw(canvas);
		blocke.get(4).draw(canvas);
		blocke.get(5).draw(canvas);
		blocke.get(6).draw(canvas);
		blocke.get(7).draw(canvas);
		blocke.get(8).draw(canvas);
		blocke.get(9).draw(canvas);
		blocke.get(10).draw(canvas);
		blocke.get(11).draw(canvas);
		blocke.get(12).draw(canvas);
		blocke.get(13).draw(canvas);
		blocke.get(14).draw(canvas);
		falle.get(0).draw(canvas);
		falle.get(1).draw(canvas);
		falle.get(2).draw(canvas);
		falle.get(3).draw(canvas);
		falle.get(4).draw(canvas);
		falle.get(5).draw(canvas);
		falle.get(6).draw(canvas);
		falle.get(7).draw(canvas);
		falle.get(8).draw(canvas);
		falle.get(9).draw(canvas);
		falle.get(10).draw(canvas);
		canvas.drawBitmap(Eingang,x0,y2,null);
		canvas.drawBitmap(Ausgang,x14,y2,null);
		
		if (figur.getLife()==3) {
			canvas.drawBitmap(Leben,0,0,null);
			canvas.drawBitmap(Leben,0,Leben.getHeight(),null);
			canvas.drawBitmap(Leben,0,Leben.getHeight()*2,null);
		}
		else if (figur.getLife()==2) {
			canvas.drawBitmap(Leben,0,0,null);
			canvas.drawBitmap(Leben,0,Leben.getHeight(),null);
		}
		else if(figur.getLife()==1) {
			canvas.drawBitmap(Leben,0,0,null);
		}
		
		figur.draw(canvas);	
    }
	
	 public void update() {

		//Goal
		 if (ziel()==true) {
			 //Code dazu aus: http://www.anddev.org/surfaceview_to_new_activity_issue-t8376.html
			 // http://stackoverflow.com/questions/4811366/how-to-make-a-class-that-extends-surfaceview-start-an-activity

			 Context context = getContext();
			 Intent myIntent = new Intent(context, Level2DoneActivity.class);

			 myIntent.putExtra("checker", ((Level2Activity)context).getvol());
			 thread.setRunning(false);
			 ((Activity)context).startActivity(myIntent);
			return;

		 }
		 //Game Over

		 if (figur.getLife()==0) {
			 helper.stop();
			 helper.release();
			 Context context = getContext();
			 Intent myIntent = new Intent(context, GameOverActivity.class);

			 myIntent.putExtra("checker", ((Level2Activity)context).getvol());
			 thread.setRunning(false);
			 ((Activity)context).startActivity(myIntent);
			return;

		 }


	    	falle.get(0).update(System.currentTimeMillis());
			falle.get(1).update(System.currentTimeMillis());
			falle.get(2).update(System.currentTimeMillis());
			falle.get(3).update(System.currentTimeMillis());
			falle.get(4).update(System.currentTimeMillis());
			falle.get(5).update(System.currentTimeMillis());
			falle.get(6).update(System.currentTimeMillis());
			falle.get(7).update(System.currentTimeMillis());
			falle.get(8).update(System.currentTimeMillis());
			falle.get(9).update(System.currentTimeMillis());
			falle.get(10).update(System.currentTimeMillis());

		 //solange anfangs kein Button gedrueckt wird, bewegt sich die Figur nicht
		 //as long as there is no button input, the figure won't move
			if (touched == false) {
				figur.getMovement().setxv(0);
				figur.getMovement().setyv(0);
			}
			
			else {
				figur.getMovement().setxv(parameter);
				figur.getMovement().setyv(parameter);
			}


		 //wenn die Figur an eine Falle oder ausserhalb des Levels kommt
		 //if the figure collides with a trap or gets outofbounds
			if (trapcollide()==true) {
				
				
				figur.loseLife();

				figur.setX(x0);
				figur.setY(y2);
				
				figur.getMovement().setxDirection(Movement.right);
				figur.getMovement().setyDirection(Movement.none);
				touched = false;
				resetten();
				
			}


			//Kollisionsbehandlung

			if(collision()>=0) {
				int t1 = collision();
				AnimatedBlock t2 = blocke.get(t1);
				Bitmap t3 = t2.getBitmap();

				//wenn die Figur von links kam bei der Kollision | figure came from the left
				if (figur.getMovement().getxDirection()==Movement.right) {
					if (figur.getBitmap()==einrollen||figur.getBitmap()==einrollen2||figur.getBitmap()==einrollen3||figur.getBitmap()==einrollen4){
						if(t3==a) {						
							figur.getMovement().setxDirection(Movement.none);
							figur.getMovement().setyDirection(Movement.up);
							figur.setBitmap(einrollen4);
													
						}
						else if(t3==d){
							figur.getMovement().setxDirection(Movement.none);
							figur.getMovement().setyDirection(Movement.down);
							figur.setBitmap(einrollen2);
						}
						else {
							figur.getMovement().setxDirection(Movement.left);
							figur.setBitmap(einrollen3);
						}

						//nach der Kollision muss der Block rotieren

						t2.rotate();
						blocke.set(t1, t2);
						
					}
					else if(figur.getBitmap()==ausbreiten||figur.getBitmap()==ausbreiten2) {
						figur.getMovement().setxDirection(Movement.left);
						t2.rotate();
						blocke.set(t1, t2);
					}
					else{
						
					}
					
				}
				//falls die Figur von rechts kam bei der Kollision
				else if (figur.getMovement().getxDirection()==Movement.left) {
					if (figur.getBitmap()==einrollen||figur.getBitmap()==einrollen2||figur.getBitmap()==einrollen3||figur.getBitmap()==einrollen4){
						if(t3==b) {
							figur.getMovement().setxDirection(Movement.none);
							figur.getMovement().setyDirection(Movement.up);
							figur.setBitmap(einrollen4);
						}
						else if(t3==c) {
							figur.getMovement().setxDirection(Movement.none);
							figur.getMovement().setyDirection(Movement.down);
							figur.setBitmap(einrollen2);
						}
						else {
							figur.getMovement().setxDirection(Movement.right);
							figur.setBitmap(einrollen);
						}
						t2.rotate();
						blocke.set(t1, t2);
					}
					else if(figur.getBitmap()==ausbreiten||figur.getBitmap()==ausbreiten2) {
						figur.getMovement().setxDirection(Movement.right);
						t2.rotate();
						blocke.set(t1, t2);
					}
					else{
						
					}		
				}
				
				else {
					//Figur kam von unten bei Kollision
					if (figur.getMovement().getyDirection()==Movement.up) {
						if (figur.getBitmap()==einrollen||figur.getBitmap()==einrollen2||figur.getBitmap()==einrollen3||figur.getBitmap()==einrollen4){
							if(t3==c) {
								figur.getMovement().setxDirection(Movement.right);
								figur.getMovement().setyDirection(Movement.none);
								figur.setBitmap(einrollen);
							}
							else if(t3==d) {
								figur.getMovement().setxDirection(Movement.left);
								figur.getMovement().setyDirection(Movement.none);
								figur.setBitmap(einrollen3);
							}
							else {
								
								figur.getMovement().setyDirection(Movement.down);
								figur.setBitmap(einrollen2);
							}
							t2.rotate();
							blocke.set(t1, t2);
						}
						else if(figur.getBitmap()==ausbreiten||figur.getBitmap()==ausbreiten2) {
							figur.getMovement().setyDirection(Movement.down);
							t2.rotate();
							blocke.set(t1, t2);
						}
						else{
							
						}
					}
					//Figur kam von oben bei Kollision
					else {
						if (figur.getBitmap()==einrollen||figur.getBitmap()==einrollen2||figur.getBitmap()==einrollen3||figur.getBitmap()==einrollen4){
							if(t3==a) {
								figur.getMovement().setxDirection(Movement.left);
								figur.getMovement().setyDirection(Movement.none);
								figur.setBitmap(einrollen3);
							}
							else if(t3==b) {
								figur.getMovement().setxDirection(Movement.right);
								figur.getMovement().setyDirection(Movement.none);
								figur.setBitmap(einrollen);
							}
							else {
								figur.getMovement().setyDirection(Movement.up);
								figur.setBitmap(einrollen4);
							}
							t2.rotate();
							blocke.set(t1, t2);
						}
						else if(figur.getBitmap()==ausbreiten||figur.getBitmap()==ausbreiten2) {
							figur.getMovement().setyDirection(Movement.up);
							t2.rotate();
							blocke.set(t1, t2);
						}
						else{
							
						}
					}
					
				}
				//hier wird die Position der Figur berichtigt, da sie bei Kollision nicht genau auf block-x UND y-Axis ist
				figur.setX(t2.getX());
				figur.setY(t2.getY());
				
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

	/**retourniert jenen Blockindex, mit dem die Figur kollidiert.
	 * returns respective blockindex, colliding with the figure.
	 *
	 * @return int Index aus der Blockliste, der den Kollisionsblock enthaelt
	 * */
    public int collision() {
		 int collideradiusx = figur.getMovement().getxv();
		 int collideradiusy = figur.getMovement().getyv();

		//die Koordinaten der Bloecke | block coordinates

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
		
		int blockx7 = blocke.get(7).getX();
		int blocky7 = blocke.get(7).getY();
		
		int blockx8 = blocke.get(8).getX();
		int blocky8 = blocke.get(8).getY();
		
		int blockx9 = blocke.get(9).getX();
		int blocky9 = blocke.get(9).getY();
		
		int blockx10 = blocke.get(10).getX();
		int blocky10 = blocke.get(10).getY();
		
		int blockx11 = blocke.get(11).getX();
		int blocky11 = blocke.get(11).getY();
		
		int blockx12 = blocke.get(12).getX();
		int blocky12 = blocke.get(12).getY();
		
		int blockx13 = blocke.get(13).getX();
		int blocky13 = blocke.get(13).getY();
		
		int blockx14 = blocke.get(14).getX();
		int blocky14 = blocke.get(14).getY();


		//kontrolliere mit welchem Block Index genau die Figur kollidiert
		//get the respective block index colliding with the figure
		
		if ( (figur.getX()>blockx0 - collideradiusx && figur.getX()<blockx0 + collideradiusx) &&
				(figur.getY()>blocky0 - collideradiusy && figur.getY()<blocky0 + collideradiusy) ) {
			return 0;
		}
		
		else if ( (figur.getX()>blockx1 - collideradiusx && figur.getX()<blockx1 + collideradiusx) &&
					(figur.getY()>blocky1 - collideradiusy && figur.getY()<blocky1 + collideradiusy) ) {
			return 1;
		}
		
		else if ( (figur.getX()>blockx2 - collideradiusx && figur.getX()<blockx2 + collideradiusx) &&
					(figur.getY()>blocky2 - collideradiusy && figur.getY()<blocky2 + collideradiusy) ) {
			return 2;
		}
		
		else if ( (figur.getX()>blockx3 - collideradiusx && figur.getX()<blockx3 + collideradiusx) &&
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
		
		else if ( (figur.getX()>blockx7 - collideradiusx && figur.getX()<blockx7 + collideradiusx) &&
					(figur.getY()>blocky7 - collideradiusy && figur.getY()<blocky7 + collideradiusy) ) {
			return 7;
		}
		else if ( (figur.getX()>blockx8 - collideradiusx && figur.getX()<blockx8 + collideradiusx) &&
					(figur.getY()>blocky8 - collideradiusy && figur.getY()<blocky8 + collideradiusy) ){
			return 8;
		}
		
		else if ( (figur.getX()>blockx9 - collideradiusx && figur.getX()<blockx9 + collideradiusx) &&
					(figur.getY()>blocky9 - collideradiusy && figur.getY()<blocky9 + collideradiusy) ){
			return 9;
		}
		
		else if ( (figur.getX()>blockx10 - collideradiusx && figur.getX()<blockx10 + collideradiusx) &&
					(figur.getY()>blocky10 - collideradiusy && figur.getY()<blocky10 + collideradiusy) ){
			return 10;
		}
		
		else if ( (figur.getX()>blockx11 - collideradiusx && figur.getX()<blockx11 + collideradiusx) &&
					(figur.getY()>blocky11 - collideradiusy && figur.getY()<blocky11 + collideradiusy) ){
			return 11;
		}
		
		else if ( (figur.getX()>blockx12 - collideradiusx && figur.getX()<blockx12 + collideradiusx) &&
					(figur.getY()>blocky12 - collideradiusy && figur.getY()<blocky12 + collideradiusy) ){
			return 12;
		}
		
		else if ( (figur.getX()>blockx13 - collideradiusx && figur.getX()<blockx13 + collideradiusx) &&
					(figur.getY()>blocky13 - collideradiusy && figur.getY()<blocky13 + collideradiusy) ){
			return 13;
		}
		
		else if ( (figur.getX()>blockx14 - collideradiusx && figur.getX()<blockx14 + collideradiusx) &&
					(figur.getY()>blocky14 - collideradiusy && figur.getY()<blocky14 + collideradiusy) ){
			return 14;
		}

		//falls keine Kollision | no collision
		else {
			return -1;
		}
	}

	/**diese Methode soll kontrollieren, ob sich die Figur in eine Falle bewegt
	 * check if figure collides with a trap
	 *
	 * @return boolean Wert, ob Kollision mit Falle erfolgt ist
	 * */
	public boolean trapcollide(){
		int collideradiusx = figur.getMovement().getxv();
		int collideradiusy = figur.getMovement().getyv();
		
		int trapx0 = falle.get(0).getX();
		int trapy0 = falle.get(0).getY();
		
		int trapx1 = falle.get(1).getX();
		int trapy1 = falle.get(1).getY();
		
		int trapx2 = falle.get(2).getX();
		int trapy2 = falle.get(2).getY();
		
		int trapx3 = falle.get(3).getX();
		int trapy3 = falle.get(3).getY();
		
		int trapx4 = falle.get(4).getX();
		int trapy4 = falle.get(4).getY();
	
		int trapx5 = falle.get(5).getX();
		int trapy5 = falle.get(5).getY();
		
		int trapx6 = falle.get(6).getX();
		int trapy6 = falle.get(6).getY();
		
		int trapx7 = falle.get(7).getX();
		int trapy7 = falle.get(7).getY();
		
		int trapx8 = falle.get(8).getX();
		int trapy8 = falle.get(8).getY();
		
		int trapx9 = falle.get(9).getX();
		int trapy9 = falle.get(9).getY();
		
		int trapx10 = falle.get(10).getX();
		int trapy10 = falle.get(10).getY();
			
		if( ((figur.getX()>=trapx0 - collideradiusx && figur.getX() <= trapx0 + collideradiusx) && (figur.getY()>=trapy0 - collideradiusy && figur.getY() <= trapy0 + collideradiusy)) ||
				((figur.getX()>=trapx1 - collideradiusx && figur.getX() <= trapx1 + collideradiusx) && (figur.getY()>=trapy1 - collideradiusy && figur.getY() <= trapy1 + collideradiusy)) ||
				((figur.getX()>=trapx2 - collideradiusx && figur.getX() <= trapx2 + collideradiusx) && (figur.getY()>=trapy2 - collideradiusy && figur.getY() <= trapy2 + collideradiusy)) ||
				((figur.getX()>=trapx3 - collideradiusx && figur.getX() <= trapx3 + collideradiusx) && (figur.getY()>=trapy3 - collideradiusy && figur.getY() <= trapy3 + collideradiusy)) ||
				((figur.getX()>=trapx4 - collideradiusx && figur.getX() <= trapx4 + collideradiusx) && (figur.getY()>=trapy4 - collideradiusy && figur.getY() <= trapy4 + collideradiusy)) ||
				((figur.getX()>=trapx5 - collideradiusx && figur.getX() <= trapx5 + collideradiusx) && (figur.getY()>=trapy5 - collideradiusy && figur.getY() <= trapy5 + collideradiusy)) ||
				((figur.getX()>=trapx6 - collideradiusx && figur.getX() <= trapx6 + collideradiusx) && (figur.getY()>=trapy6 - collideradiusy && figur.getY() <= trapy6 + collideradiusy)) ||
				((figur.getX()>=trapx7 - collideradiusx && figur.getX() <= trapx7 + collideradiusx) && (figur.getY()>=trapy7 - collideradiusy && figur.getY() <= trapy7 + collideradiusy)) ||
				((figur.getX()>=trapx8 - collideradiusx && figur.getX() <= trapx8 + collideradiusx) && (figur.getY()>=trapy8 - collideradiusy && figur.getY() <= trapy8 + collideradiusy)) ||
				((figur.getX()>=trapx9 - collideradiusx && figur.getX() <= trapx9 + collideradiusx) && (figur.getY()>=trapy9 - collideradiusy && figur.getY() <= trapy9 + collideradiusy)) ||
				((figur.getX()>=trapx10 - collideradiusx && figur.getX() <= trapx10 + collideradiusx) && (figur.getY()>=trapy10 - collideradiusy && figur.getY() <= trapy10 + collideradiusy)) ||
				
				
				( figur.getX() < -10||figur.getX() > width)|| (figur.getY() <= -10 ||figur.getY() >= height)){
			return true;
		}
		else {
			return false;
		}
			
	}
    
    public Figur getFigur() {
		return figur;
	}


	/**Hilfsmethode fuer Activity bei Button press der Pose, damit das richtige Bitmap der Pose bzglich der Richtung kommt
	 * returns the correct bitmap in regards to the figure movement. this methods gets called by the activity associated with this SurfaceView
	 *
	 * @return Einrollen Pose entsprechend der Richtung | rolled in pose according to the figure movement
	 * */
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
	 * returns the correct bitmap in regards to the figure movement. this methods gets called by the activity associated with this SurfaceView
	 *
	 * @return Ausbreiten Pose entsprechend der Richtung | stretched out pose according to the figure movement
	 * */
	public Bitmap getausbreiten() {
		if (figur.getMovement().getyDirection()==Movement.up||figur.getMovement().getyDirection()==Movement.down) {
			return ausbreiten2;
		}
		else {
			return ausbreiten;
		}
	}

	/**Hilfsmethode fuer Activity bei Button press der Pose, damit das richtige Bitmap der Pose bzglich der Richtung kommt
	 * returns the correct bitmap in regards to the figure movement. this methods gets called by the activity associated with this SurfaceView
	 *
	 * @return Hecht Pose entsprechend der Richtung | dive pose according to the figure movement
	 * */
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

	/**ob die Figur ans Ziel kommt
	 * check if figure reaches the goal
	 *
	 * @return boolean Wert, ob Ziel erreicht
	 * */
	public boolean ziel() {
		int collideradiusx = figur.getMovement().getxv();
		int collideradiusy = figur.getMovement().getyv();
		
		if ( (figur.getX() > x14 - collideradiusx && figur.getX() < x14 + collideradiusx) && (figur.getY() >= y2 - collideradiusy && figur.getY() <= y2 + collideradiusy)){
			return true;
		}
		else {
			return false;
		}
		
	}

	public void touched() {

		touched = true;
	}
	
	public void setmedia(MediaPlayer x) {
		helper = x;
	}

	/**Hilfsmethode fuer Activity*/
	public MainThread2 getthread() {
		return thread;
	}

	/*Quelle: http://panjutorials.de/tutorial-28-den-dialog-anzeigen-lassen-onbackpressed/
	 * nach Aufruf des Dialogs muss ein neuer MainThread0 erstellt werden, um wieder updaten zu koennen*/
	public void resumen() {
		thread = new MainThread2(getHolder(),this);
		thread.setRunning(true);
		thread.start();
	}

	/**dazu da um die Blocke wieder in die Anfangspostition zu rotieren*/
	public void resetten() {
		blocke.get(0).setBitmaps(c,d,a,b);
		blocke.get(1).setBitmaps(a,b,c,d);
		blocke.get(2).setBitmaps(d,a,b,c);
		blocke.get(3).setBitmaps(d,a,b,c);
		blocke.get(4).setBitmaps(a,b,c,d);
		blocke.get(5).setBitmaps(d,a,b,c);
		blocke.get(6).setBitmaps(b,c,d,a);
		blocke.get(7).setBitmaps(c,d,a,b);
		blocke.get(8).setBitmaps(b,c,d,a);
		blocke.get(9).setBitmaps(b,c,d,a);
		blocke.get(10).setBitmaps(c,d,a,b);
		blocke.get(11).setBitmaps(b,c,d,a);
		blocke.get(12).setBitmaps(b,c,d,a);
		blocke.get(13).setBitmaps(c,d,a,b);
		blocke.get(14).setBitmaps(b,c,d,a);
	}

	/**zum Setzen der Geschwindigkeit
	 * @param i Geschwindigkeitswert 0-10
	 * */
    public void setParameter(int i) {
		parameter = i;
    }
}
