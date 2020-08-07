package com.example.wackelpudding;

import android.graphics.Bitmap;
import java.util.*;
import android.graphics.Canvas;

//Author-Kurt
//Teile des Codes aus folgender Quelle: http://obviam.net/index.php/sprite-animation-with-android/
public class AnimatedBlock {


private Bitmap drawit;//aktuelles Bitmap
	
	//Bitmaps fuer Rotation initialisiert
	private Bitmap a;
	private Bitmap b;
	private Bitmap c;
	private Bitmap d;
	
	private int x;
	private int y;
	
	//Daten releveant fuer Animation
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
	
	/**Erstellt einen Block, der eine Abfolge von Bildern beinhaltet
	 *
	 * @param bitmap Bilder zum Abspielen der Animation
	 * @param x x-Koordinate der Bilder
	 * @param y y-Koordinate der Bilder
	*
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
	
	/**wird momentan nicht genutzt*/
	public void update(long GameTime){
		if (GameTime > frameTicker + framePeriod) {
			frameTicker = GameTime;
			currentFrame++;
			
			if(currentFrame >= frameNr) {
				currentFrame = 0;
			}
		}
	}

	/**ersetzt das aktuelle Bitmap durch das naechste im Uhrzeigersinn*/
	public void rotate(){
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

	/**zeichnet den aktuellen Bitmap des Blocks*/
	public void draw(Canvas canvas) {
		canvas.drawBitmap(drawit,x,y,null);
		
	}

}
