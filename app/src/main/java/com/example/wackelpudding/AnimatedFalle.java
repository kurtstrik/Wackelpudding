package com.example.wackelpudding;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import java.util.*;

/**
 * Teile des Codes aus folgender Quelle: http://obviam.net/index.php/sprite-animation-with-android/
 * parts of code from: http://obviam.net/index.php/sprite-animation-with-android/
 */
public class AnimatedFalle {
	private Bitmap xs;
	private int x;
	private int y;
	
	//wichtig fuer Animation | variables for animation
	private Vector<Bitmap> animation = new Vector<Bitmap>(5);
	private int frameNr;
	private int currentFrame;
	private long frameTicker;
	private int framePeriod;

	public AnimatedFalle(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * speichert die Animationsbilder in einen Vektor, currentFrame gibt den Index des aktuellen Bildes aus
	 *  saves animation images in a vector, currentFrame returns index of current image
	 *
	 * @param bitmap Bilder zum Abspielen der Animation| images for the animation
	 * @param x x-Koordinate der Bilder | x-coordinate for the image
	 * @param y y-Koordinate der Bilder | y-coordinate for the image
	 * @param fps
	 * @param frameCount
	 *
	 * */
	public AnimatedFalle(Bitmap bitmap, Bitmap bitmap_b, Bitmap bitmap_c, Bitmap bitmap_d, Bitmap bitmap_e, int x, int y, int fps, int frameCount) {
		animation.add(bitmap);
		animation.add(bitmap_b);
		animation.add(bitmap_c);
		animation.add(bitmap_d);
		animation.add(bitmap_e);
		this.x = x;
		this.y = y;
		currentFrame = 0;
		frameNr = frameCount;
		framePeriod = 1000/fps;
		frameTicker = 0l;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;		
	}
	
	public void setX(int x) {
		this.x = x;
		
	}
	
	public void setY(int y) {
		this.y = y;
		
	}
		
	public void draw(Canvas canvas) {
		canvas.drawBitmap(animation.get(getcurrentFrame()),x,y,null);
	}
	
	public void update(long gameTime) {
		if (gameTime > frameTicker + framePeriod) {
			frameTicker = gameTime;
			// increment the frame
			currentFrame++;
			if (currentFrame >= frameNr) {
			    currentFrame = 0;
			}
		}
		
	}
	/**@return int Index des aktuellen Bitmaps | index of current bitmap in the image-list*/
	public int getcurrentFrame() {
		return currentFrame;
	}

}
