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
public class SurfaceViewlv1 extends SurfaceView implements SurfaceHolder.Callback {
	
	private MainThread1 thread;
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

	//es gibt 12 Bloecke in diesem Level | there are 12 blocks in this level
	private Vector<AnimatedBlock> blocke = new Vector<AnimatedBlock>(12);
	//es gibt 10 Fallen in diesem Level | there are 10 traps in this level
	private Vector<AnimatedFalle> falle = new Vector<AnimatedFalle>(10);

	//Bitmaps fuer Rotation der Bloecke | bitmaps for rotation of the blocks
	private Bitmap a = BitmapFactory.decodeResource(getResources(), R.drawable.block_a);
	private Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.block_b);
	private Bitmap c = BitmapFactory.decodeResource(getResources(), R.drawable.block_c);
	private Bitmap d = BitmapFactory.decodeResource(getResources(), R.drawable.block_d);
	
	//Bitmap fuer fixen Block | bitmap for a fixed block
	private Bitmap c2 = BitmapFactory.decodeResource(getResources(), R.drawable.block_c_locked);

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

	private int parameter = 10;//Geschwindigkeit der Figur | figure speed
	private int width = hintergrund.getWidth();
	private int height = hintergrund.getHeight();

	//diverse wichtige Koordinaten zur Platzierung | various important coordinates for placing the objects
	private int x0 ;
	private int x1 ;
	private int x2 ;
	private int x3 ;
	private int x4 ;
	private int x5 ;
	private int x6 ;
	private int x7 ;
	private int x8 ;
	private int x9 ;
	private int x10;

	private int y0 ;
	private int y1 ;
	private int y2 ;
	private int y3 ;
	private int y4 ;

	//dazu da um bei Levelabschluss Musik richtig zu beenden | properly stops music when quitting
	private MediaPlayer helper;

	//dazu da, weil in diesem Level auch fixierte Bloecke ohne Rotation da sind
	//important for differentiating between normal/fixed block
	private boolean rotate = true;
	
	public SurfaceViewlv1(Context context) {
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


		figur = new Figur(einrollen, x0, y2);

		//erstellt die Bloecke an den entsprechenden Stellen
		//creates the blocks at designated places
		blocke.add(new AnimatedBlock(c2,x2,y0));
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

		//erstellt die Fallen an den entsprechenden Stellen
		//creates traps at designated places
		falle.add(new AnimatedFalle(j,k,l,m,n,x1, y0,5,5));
		falle.add(new AnimatedFalle(j,k,l,m,n,x1, y4,5,5));
		falle.add(new AnimatedFalle(j,k,l,m,n,x3, y2, 5,5));
		falle.add(new AnimatedFalle(j,k,l,m,n,x3, y4, 5,5));
		falle.add(new AnimatedFalle(e,f,g,h,i,x4, y3, 5,5));
		falle.add(new AnimatedFalle(j,k,l,m,n,x5, y4, 5,5));
		falle.add(new AnimatedFalle(e,f,g,h,i,x7, y2, 5,5));
		falle.add(new AnimatedFalle(j,k,l,m,n,x8, y1, 5,5));
		falle.add(new AnimatedFalle(j,k,l,m,n,x9, y0, 5,5));
		falle.add(new AnimatedFalle(j,k,l,m,n,x9, y4, 5,5));
		
		
		thread = new MainThread1(getHolder(), this); // gameloop
		
		setFocusable(true);// um events handeln zu koennen | to handle events
	}

	/**zeichnet alle nichtstatischen Objekte in der GameLoop auf den canvas
	 *
	 * draws all non static objects in the gameloop on the canvas
	 * */
	protected void onDraw(Canvas canvas) {

		//damit vorige draw calls geloescht werden | clears canvas from previous draw call
		canvas.drawColor(0, PorterDuff.Mode.CLEAR);
		// canvas.drawColor(Color.BLACK);
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
		
		canvas.drawBitmap(Eingang,x0,y2,null);
		canvas.drawBitmap(Ausgang,x10,y2,null);
		
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
			 //http://stackoverflow.com/questions/4811366/how-to-make-a-class-that-extends-surfaceview-start-an-activity


			 helper.stop();
			 helper.release();
			 Context context = getContext();
			 Intent myIntent = new Intent(context, Level1DoneActivity.class);

			 myIntent.putExtra("checker", ((Level1Activity)context).getvol());
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


			 myIntent.putExtra("checker", ((Level1Activity)context).getvol());
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
				//Hilfsvariablen
				int t1 = collision();
				AnimatedBlock t2 = blocke.get(t1);
				Bitmap t3 = t2.getBitmap();

				//wenn die Figur von links kam bei der Kollision | figure came from the left
				if (figur.getMovement().getxDirection()==Movement.right) {
					if (figur.getBitmap()==einrollen||figur.getBitmap()==einrollen2||figur.getBitmap()==einrollen3||figur.getBitmap()==einrollen4){
						
						//wie steht der Block bezogen zur Figur
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
						//nach der Kollision muss der Block rotieren, wenn kein fixer Block

						if (rotate) {
						t2.rotate();
						blocke.set(t1, t2);}
						
					}
					else if(figur.getBitmap()==ausbreiten||figur.getBitmap()==ausbreiten2) {
						figur.getMovement().setxDirection(Movement.left);
						
						if(rotate) {
						t2.rotate();
						blocke.set(t1, t2);}
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
						else if(t3==c||t3==c2) {
							figur.getMovement().setxDirection(Movement.none);
							figur.getMovement().setyDirection(Movement.down);
							figur.setBitmap(einrollen2);
						}
						else {
							figur.getMovement().setxDirection(Movement.right);
							figur.setBitmap(einrollen);
						}
						
						if(rotate) {
							t2.rotate();
							blocke.set(t1, t2);}
					}
					else if(figur.getBitmap()==ausbreiten||figur.getBitmap()==ausbreiten2) {
						figur.getMovement().setxDirection(Movement.right);
						if(rotate) {
							t2.rotate();
							blocke.set(t1, t2);}
					}
					else{						
					}		
				}
				
				else {
					//Figur kam von unten bei Kollision
					if (figur.getMovement().getyDirection()==Movement.up) {
						if (figur.getBitmap()==einrollen||figur.getBitmap()==einrollen2||figur.getBitmap()==einrollen3||figur.getBitmap()==einrollen4){
							if(t3==c||t3==c2) {
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
							if(rotate) {
								t2.rotate();
								blocke.set(t1, t2);}
						}
						else if(figur.getBitmap()==ausbreiten||figur.getBitmap()==ausbreiten2) {
							figur.getMovement().setyDirection(Movement.down);
							if(rotate) {
								t2.rotate();
								blocke.set(t1, t2);}
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
							if(rotate) {
								t2.rotate();
								blocke.set(t1, t2);}
						}
						else if(figur.getBitmap()==ausbreiten||figur.getBitmap()==ausbreiten2) {
							figur.getMovement().setyDirection(Movement.up);
							if(rotate) {
								t2.rotate();
								blocke.set(t1, t2);}
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


		//kontrolliere mit welchem Block Index genau die Figur kollidiert
		//get the respective block index colliding with the figure

		if ( (figur.getX()>blockx0 - collideradiusx && figur.getX()<blockx0 + collideradiusx) &&
				(figur.getY()>blocky0 - collideradiusy && figur.getY()<blocky0 + collideradiusy) ) {
			rotate = false;
			return 0;
		}
		
		else if ( (figur.getX()>blockx1 - collideradiusx && figur.getX()<blockx1 + collideradiusx) &&
					(figur.getY()>blocky1 - collideradiusy && figur.getY()<blocky1 + collideradiusy) ) {
			return 1;
		}
		
		else if ( (figur.getX()>blockx2 - collideradiusx && figur.getX()<blockx2 + collideradiusx) &&
					(figur.getY()>blocky2 - collideradiusy && figur.getY()<blocky2 + collideradiusy) ) {
			rotate = true;
			return 2;
		}
		
		else if ( (figur.getX()>blockx3 - collideradiusx && figur.getX()<blockx3 + collideradiusx) &&
					(figur.getY()>blocky3 - collideradiusy && figur.getY()<blocky3 + collideradiusy) ) {
			rotate = true;
			return 3;
		}
		
		else if( (figur.getX()>blockx4 - collideradiusx && figur.getX()<blockx4 + collideradiusx) &&
					(figur.getY()>blocky4 - collideradiusy && figur.getY()<blocky4 + collideradiusy) ) {
			rotate = true;
			return 4;
		}
		
		else if ( (figur.getX()>blockx5 - collideradiusx && figur.getX()<blockx5 + collideradiusx) &&
					(figur.getY()>blocky5 - collideradiusy && figur.getY()<blocky5 + collideradiusy) ) {
			rotate = true;
			return 5;
		}
		
		else if ( (figur.getX()>blockx6 - collideradiusx && figur.getX()<blockx6 + collideradiusx) &&
					(figur.getY()>blocky6 - collideradiusy && figur.getY()<blocky6 + collideradiusy) ) {
			rotate = true;
			return 6;
		}
		
		else if ( (figur.getX()>blockx7 - collideradiusx && figur.getX()<blockx7 + collideradiusx) &&
					(figur.getY()>blocky7 - collideradiusy && figur.getY()<blocky7 + collideradiusy) ) {
			rotate = true;
			return 7;
		}
		else if ( (figur.getX()>blockx8 - collideradiusx && figur.getX()<blockx8 + collideradiusx) &&
					(figur.getY()>blocky8 - collideradiusy && figur.getY()<blocky8 + collideradiusy) ){
			rotate = true;
			return 8;
		}
		
		else if ( (figur.getX()>blockx9 - collideradiusx && figur.getX()<blockx9 + collideradiusx) &&
					(figur.getY()>blocky9 - collideradiusy && figur.getY()<blocky9 + collideradiusy) ){
			rotate = true;
			return 9;
		}
		
		else if ( (figur.getX()>blockx10 - collideradiusx && figur.getX()<blockx10 + collideradiusx) &&
					(figur.getY()>blocky10 - collideradiusy && figur.getY()<blocky10 + collideradiusy) ){
			rotate = true;
			return 10;
		}
		
		else if ( (figur.getX()>blockx11 - collideradiusx && figur.getX()<blockx11 + collideradiusx) &&
					(figur.getY()>blocky11 - collideradiusy && figur.getY()<blocky11 + collideradiusy) ){
			rotate = true;
			return 11;
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
				( figur.getX() < -10||figur.getX() > width+10)|| (figur.getY() <= -10 ||figur.getY() >= height+10)){
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
		
		if ( (figur.getX() > x10 - collideradiusx && figur.getX() < x10 + collideradiusx) && (figur.getY() >= y2 - collideradiusy && figur.getY() <= y2 + collideradiusy)){
			figur.setMovement(0);
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


	public MainThread1 getthread() {
		return thread;
	}

	/**nach Aufruf des Dialogs muss ein neuer MainThread0 erstellt werden, um wieder updaten zu koennen
	 * after finishing the dialog, a new MainThread0 is created, to resume update.
	 *
	 * Quelle: http://panjutorials.de/tutorial-28-den-dialog-anzeigen-lassen-onbackpressed/
	 * */
	public void resumen() {
		thread = new MainThread1(getHolder(),this);
		thread.setRunning(true);
		thread.start();
	}

	/**dazu da um die Blocke wieder in die Anfangspostition zu rotieren
	 * returns the blocks back in original state
	 * */
	public void resetten() {
		
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
	}

	/**zum Setzen der Geschwindigkeit | for setting the speed
	 * @param i Geschwindigkeitswert 0-10
	 * */
	public void setParameter(int i){
		parameter = i;
	}
}
