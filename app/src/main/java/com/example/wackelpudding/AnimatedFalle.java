package com.example.wackelpudding;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import java.util.*;
//Author-Kurt

//Teile des Coddes aus folgender Quelle: http://obviam.net/index.php/sprite-animation-with-android/
public class AnimatedFalle {
	private Bitmap xs;
	private int x;
	private int y;
	
	//wichtig fuer Animation
	private Vector<Bitmap> animation = new Vector<Bitmap>(5);
	private int frameNr;
	private int currentFrame;
	private long frameTicker;
	private int framePeriod;
	
	/**erstellt eine unsichtbare Falle, eventuell fuer Randbehandlung*/
	public AnimatedFalle(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @param bitmap
	 * @param x
	 * @param y
	 * @param fps
	 * @param frameCount
	 *
	 * speichert die Animationsbilder in einen Vektor, currentFrame gibt den Index des aktuellen Bildes aus*/
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
	/**gibt den Index des aktuellen Bitmap-Vectors aus*/
	public int getcurrentFrame() {
		return currentFrame;
	}

}
