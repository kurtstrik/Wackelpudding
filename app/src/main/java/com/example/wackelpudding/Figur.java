package com.example.wackelpudding;

import java.util.Vector;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import com.example.wackelpudding.Movement;

public class Figur {
	private Bitmap bitmap=null;
	private int x=0;
	private int y=0;
	private int life = 3;

	//wichtig fuer die Bewegung der Figur, veraendert Position und Richtung
	//important for the movement of the figure, changes the positioning and direction
	private Movement movement = new Movement();

	public Figur(Bitmap bitmap, int x, int y) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;	
		//this.movement =new Movement();
	}
	
	
	
	public Movement getMovement() {
		return movement;
	}

	public void setMovement(int param){
		movement.setxv(param);
		movement.setyv(param);

	}

	public Bitmap getBitmap() {
		return bitmap;	
	}
	
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
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

	/**einfaches life -=life; nicht moeglich, weil bei einmaliger Fallenkollision alle Leben abgezogen wurden
	 *   workaround because decrement instantly deducts all lifes
	 * */
	public void loseLife() {
		if (life == 3) {
			life = 2;
		}
		else if(life==2) {
			life = 1;
		}
		else {
			life = 0;
		}
	}
	
	public int getLife() {
		return life;
	}
	
	public void draw(Canvas canvas) {
	    canvas.drawBitmap(bitmap, x, y, null);
	}
	
	/**
	 * je nachdem, welche Richtungsparameter gegeben sind, bewegt sich die Figur entsprechend
	 *
	 * 	update the movement of the figure according to the direction
	 */
	public void update() {
		if(movement.getxDirection()==Movement.right) {
			
				x+= movement.getxv()*movement.getxDirection();
			
		}
		
		if(movement.getxDirection()==Movement.left) {
				x+= movement.getxv()*movement.getxDirection();
		}
		
		else {
			if(movement.getyDirection()==Movement.up) {
				
				y+=	movement.getyv()*movement.getyDirection();
			}
			if(movement.getyDirection()==Movement.down){
				
				y+=	movement.getyv()*movement.getyDirection();
			}
			
		}
		
	}
	
}
