package com.example.wackelpudding;

import android.graphics.Bitmap;
import java.util.*;
import android.graphics.Canvas;

/**
 * Teile des Codes aus folgender Quelle: http://obviam.net/index.php/sprite-animation-with-android/
 * parts of source code from: http://obviam.net/index.php/sprite-animation-with-android/
 */
public class AnimatedBlock {


private Bitmap drawit;//aktuelles Bitmap| current bitmap
	
	//Bitmaps fuer Rotation initialisiert| bitmaps for rotation
	private Bitmap a;
	private Bitmap b;
	private Bitmap c;
	private Bitmap d;
	
	private int x;
	private int y;
	
	//Daten releveant fuer Animation| data relevant for the animation
	private Vector<Bitmap> animation = new Vector<Bitmap>(4);
	private int frameNr;
	private int currentFrame;
	private long frameTicker;
	private int framePeriod;
	
	public AnimatedBlock(Bitmap bitmap, int x, int y) {
		drawit = bitmap;
		this.x = x;
		this.y = y;
	}
	
	/**Erstellt einen Block, der eine Abfolge von Bildern beinhaltet.
	 * creates a block containing a sequence of images.
	 *
	 * @param bitmap Bilder zum Abspielen der Animation| images for the animation
	 * @param x x-Koordinate der Bilder | x-coordinate for the image
	 * @param y y-Koordinate der Bilder | y-coordinate for the image
	* */
	public AnimatedBlock(Bitmap bitmap, Bitmap bitmap1, Bitmap bitmap2, Bitmap bitmap3, int x, int y) {
		a = bitmap;
		b = bitmap1;
		c = bitmap2;
		d = bitmap3;
		
		drawit = bitmap;
		this.x = x;
		this.y = y;		
	}
	
	//getter fuer die Koordinaten des Blocks
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;		
	}
	
	//dasselbe als setter

	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setBitmaps(Bitmap bitmap, Bitmap bitmap1, Bitmap bitmap2, Bitmap bitmap3) {
		a = bitmap;
		b = bitmap1;
		c = bitmap2;
		d = bitmap3;
		
		drawit = bitmap;
	}
	
	public Bitmap getBitmap() {
		return drawit;		
	}
	
	/*
	public void update(long GameTime){
		if (GameTime > frameTicker + framePeriod) {
			frameTicker = GameTime;
			currentFrame++;
			
			if(currentFrame >= frameNr) {
				currentFrame = 0;
			}
		}
	}*/

	/**ersetzt das aktuelle Bitmap durch das naechste im Uhrzeigersinn.
	 * replaces current bitmap with the next one in line.
	 * */
	public void rotate(){

		// TODO: switch statement
		if (drawit==a) {
			drawit = b;
		}
		else {
			if (drawit==b) {
				drawit = c;
			}
			else {
				if (drawit==c) {
					drawit = d;
				}
				else{
					drawit = a;
				}
			}		
		}		
	}

	/**zeichnet den aktuellen Bitmap des Blocks.
	 * draws the current bitmap on the given canvas.
	 * */
	public void draw(Canvas canvas) {
		canvas.drawBitmap(drawit,x,y,null);
		
	}

}
