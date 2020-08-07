package com.example.wackelpudding;


/**Quelle: http://obviam.net/index.php/moving-images-on-the-screen-with-androi/*/
public class Movement {
	
	public static final int right = 1;
	public static final int left  = -1;
	public static final int up    = -1;
	public static final int down  = 1;
	public static final int none = 0;
		 
	/**Weg, den die Figur pro update zuruecklegt*/
	private int xv = 10;
	private int yv = 10;
		 
	private int xDirection = right;
	private int yDirection = none;
	
	public Movement() {
		this.xv = 10;
		this.yv = 10;
	}
	
	public Movement(int xv, int yv) {
		this.xv = xv;
		this.yv = yv;
		
	}
	
	public int getxv() {
		return xv;
	}
	
	public void setxv(int xv) {
	    this.xv = xv;
	}
	
	public int getyv() {
	    return yv;
	}
	
	public void setyv(int yv) {
	    this.yv = yv;
	}
		 	 
	public int getxDirection() {
	    return xDirection;
	}
	
	public void setxDirection(int xDirection) {
	    this.xDirection = xDirection;
	}
	
	public int getyDirection() {
	    return yDirection;
	}
	
	public void setyDirection(int yDirection) {
	    this.yDirection = yDirection;
	}
		

}
