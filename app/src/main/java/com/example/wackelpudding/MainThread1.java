package com.example.wackelpudding;

import android.annotation.SuppressLint;
import android.view.SurfaceHolder;

import android.graphics.Canvas;


/**author-Kurt
 * Quelle aus den Folien und aus den Links:
 * http://obviam.net/index.php/a-very-basic-the-game-loop-for-android/
	http://obviam.net/index.php/the-android-game-loop/*/

public class MainThread1 extends Thread {
	private SurfaceHolder surfaceHolder;
	private SurfaceViewlv1 myView;
	private boolean running; //laeuft das Spiel gerade?
	
	// desired fps
	private final static int    MAX_FPS = 30;  
	// maximum number of frames to be skipped
	private final static int    MAX_FRAME_SKIPS = 3;   
	// the frame period
	private final static int    FRAME_PERIOD = 1000 / MAX_FPS;
	
	long beginTime;
	long timeDiff;
	int sleepTime =0;
	int framesSkipped;
	
	public MainThread1(SurfaceHolder s, SurfaceViewlv1 v) {
		super();
		surfaceHolder = s;
		myView = v;
		running = false;
	
	}
	
	public void setRunning(boolean running)	{
		this.running = running;
	}
	
	
	
	@SuppressLint("WrongCall")
    public void run() {
		Canvas c;
		while(running) {
			c = null;
			try{
				c = this.surfaceHolder.lockCanvas();
				
			    synchronized (surfaceHolder) {
				    	beginTime = System.currentTimeMillis();
				    	framesSkipped = 0;  // resetting the frames skipped
				    	myView.update();
						myView.onDraw(c);
						 // calculate how long did the cycle take
						timeDiff = System.currentTimeMillis() - beginTime;
						// calculate sleep time
						sleepTime = (int)(FRAME_PERIOD - timeDiff);
					
					if (sleepTime > 0) {
					// if sleepTime > 0 we're OK
					try {
						// send the thread to sleep for a short period
						// very useful for battery saving
							Thread.sleep(sleepTime);   
					} 	catch (InterruptedException e) {}
					}
					
					while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
						// we need to catch up
						// update without rendering
						myView.update();
						// add frame period to check if in next frame
						sleepTime += FRAME_PERIOD; 
						framesSkipped++;
					}
				}
			}
			
			finally {
				if (c != null) {
				    surfaceHolder.unlockCanvasAndPost(c);
				}
			}				
		}
	}	
}
