package com.example.wackelpudding;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Gravity;
import android.view.Menu;
import android.app.Dialog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.view.Display;
import android.graphics.Point;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Code zu der Idee, Buttons im SurfaceView einzusetzen aus der Quelle:
 * reference source for using buttons in SurfaceView:
 *
 * http://jwlstudios.blogspot.co.at/2012/04/how-to-add-button-to-surfaceview-in.html*/
public class Level3Activity extends Activity implements OnClickListener, SeekBar.OnSeekBarChangeListener{
	private Button ausbreiten;
	private Button einrollen;
	private Button hecht;
	private Button pause;

	private int stepsize = 10;
	private SeekBar slider;
	private TextView tempotext;

	LinearLayout tempo;
	SurfaceViewlv3 GameView;
	FrameLayout layout;
	RelativeLayout Buttons;//das Layout in dem die Buttons zusammengelegt werden | layout containing all the buttons
	Dialog dialog;//fuer Pausemenue | for the pause menu
	Dialog dialog2;//fuer Hilfemenue | for help text menu
	private MediaPlayer background;
	
	boolean volume;//dazu da um Musik an/aus-wahl konstant beizubehalten |  keeps track if music is on/off
	private Button musik;//um die Musik an/aus Schrift zu aendern, wenn drauf geklickt wird | keeps track of music on/off labeltext

	private ProgressDialog progress;

	/**hier werden alle Elemente der Activity programmatisch erstellt
	 /*creates all relevant elements for the activity programmatically*/
	@SuppressLint({"NewApi", "ResourceType"})
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//entfernt Menueleisten, damit wirklich Fullscreen genutzt wird
		//removes titlebar to fully utilize the screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		//damit die Buttons eine gleichmaessige Hoehe haben, wird die Screen Height bestimmt
		//measuring screen height and adjust buttons accordingly
		Display display = getWindowManager().getDefaultDisplay();
		Point displaySize = new Point();
		if (android.os.Build.VERSION.SDK_INT >= 13) {
			display.getSize(displaySize);


		} else {
			DisplayMetrics metrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(metrics);
			displaySize.x = metrics.widthPixels;
			displaySize.y = metrics.heightPixels;
		}

		//GameView.width = displaySize.x;
		//GameView.height = displaySize.y;

		GameView = new SurfaceViewlv3(this);

		layout = new FrameLayout(this);
		Buttons = new RelativeLayout(this);
		ausbreiten = new Button(this);
		einrollen = new Button(this);
		hecht = new Button(this);
		pause = new Button(this);
		background = MediaPlayer.create(this, R.raw.mario);
		background.setLooping(true);

		tempo = new LinearLayout(this);
		tempotext = new TextView(this);

		einrollen.setId(1);
		ausbreiten.setId(2);
		hecht.setId(3);
		pause.setId(4);

		//jeder Button soll von Breite her WRAP_CONTENT und von der Hoehe her 1/4 des Bildschirms haben
		// each button has width- WRAP_CONTENT and height 1/4 of the screen height set
		RelativeLayout.LayoutParams b1 = new LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				(int)(displaySize.y-displaySize.y/10)/4);

		RelativeLayout.LayoutParams b2 = new LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				(int)(displaySize.y-displaySize.y/10)/4);

		RelativeLayout.LayoutParams b3 = new LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				(int)(displaySize.y-displaySize.y/10)/4);

		RelativeLayout.LayoutParams b4 = new LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				(int)(displaySize.y-displaySize.y/10)/4);


		RelativeLayout.LayoutParams params = new LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);

		params.setMargins(10,10,10,10);

		RelativeLayout.LayoutParams bottomparams = new LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);

		bottomparams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		//  bottomparams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);


		slider = new SeekBar(this);
		slider.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		slider.setOnSeekBarChangeListener(this);
		slider.setProgress(50);


		RelativeLayout.LayoutParams bottom = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		bottom.addRule(RelativeLayout.ALIGN_PARENT_TOP);

		tempotext.setLayoutParams(bottom);
		tempotext.setText("Speed: 5");
		tempotext.setTextColor(Color.parseColor("#ffffff"));

		//   tempo.setLayoutParams(bottom);
		tempo.addView(tempotext);
		tempo.addView(slider);

		tempo.setLayoutParams(bottomparams);
		tempo.setGravity(Gravity.CENTER);
		tempo.setOrientation(LinearLayout.VERTICAL);

		//Buttons mit dem entsprechenden Image versehen
		//set each button with respective image
		ausbreiten.setBackgroundResource(R.drawable.zurueck);
		einrollen.setBackgroundResource(R.drawable.abprall);
		hecht.setBackgroundResource(R.drawable.durch);
		pause.setBackgroundResource(R.drawable.menu);

		Buttons.setLayoutParams(params);

		Buttons.addView(ausbreiten);
		Buttons.addView(einrollen);
		Buttons.addView(hecht);
		Buttons.addView(pause);

		Buttons.addView(tempo);

		//damit die Buttons rechts am Rand sind und klickbar
		//places each button on right side and set onclick
		b1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		ausbreiten.setLayoutParams(b1);
		ausbreiten.setOnClickListener(this);

		b2.addRule(RelativeLayout.BELOW, ausbreiten.getId());
		b2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		einrollen.setLayoutParams(b2);
		einrollen.setOnClickListener(this);

		b3.addRule(RelativeLayout.BELOW, einrollen.getId());
		b3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		hecht.setLayoutParams(b3);
		hecht.setOnClickListener(this);

		b4.addRule(RelativeLayout.BELOW, hecht.getId());
		b4.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		pause.setLayoutParams(b4);
		pause.setOnClickListener(this);

		layout.addView(GameView);
		layout.addView(Buttons);

		new Level3Activity.LoadView().execute();//Ladescreen ausfuehren | execute loading screen


	}

	//Code dazu aus: http://www.41post.com/4588/programming/android-coding-a-loading-screen-part-1
	private class LoadView extends AsyncTask<Void, Integer, Void>
    {  
        //Before running code in separate thread  
        @Override  
        protected void onPreExecute()  
        {
			progress = ProgressDialog.show(Level3Activity.this,getResources().getString(R.string.loadingtitle),
					getResources().getString(R.string.loadstage3), false, false);
        }  
  
        //The code to be executed in a background thread.  
        @Override  
        protected Void doInBackground(Void... params)  
        {  
           
            try  
            {  
                //Get the current thread's token  
                synchronized (this)  
                {  
                    //Initialize an integer (that will act as a counter) to zero  
                    int counter = 0;  
                    //While the counter is smaller than four  
                    while(counter <= 4)  
                    {  
                        //Wait 850 milliseconds  
                        this.wait(850);  
                        //Increment the counter  
                        counter++;  
                        //Set the current progress.  
                        //This value is going to be passed to the onProgressUpdate() method.  
                        publishProgress(counter*25);  
                    }  
                }  
            }  
            catch (InterruptedException e)  
            {  
                e.printStackTrace();  
            }  
            return null;  
        }  
  
        //Update the progress  
        @Override  
        protected void onProgressUpdate(Integer... values)  
        {  
            //set the current progress of the progress dialog  
            progress.setProgress(values[0]);  
        }  
  
        //after executing the code in the thread  
        @Override  
        protected void onPostExecute(Void result)  
        {  
            //close the progress dialog  
            progress.dismiss();  
            //initialize the View  
            setContentView(layout);
            background.start();
            GameView.setmedia(background);  
        }  
    }

	/** Behandelt die Aenderungen des Speedsliders, setzt sie bei der Figur um und gibt diese als Textanzeige aus.
	 *   takes care of changes from the speedslider, changes speed of the Figure and gives an updated output.
	 * */
	@Override
	public void onProgressChanged(SeekBar seekBar, int progres, boolean b) {
		//  int stepsize = GameView.getFigur().getMovement().getxv();
		// int stepsize = 10;
		progres = Math.round((float)progres / stepsize) * stepsize;
		seekBar.setProgress(progres);

		int result= progres/(stepsize/2);
		GameView.setParameter(result);
		GameView.getFigur().setMovement(result);


		tempotext.setText("Speed: "+(progres/(stepsize/2))/2);


	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_level3, menu);
		return true;
	}
	
	@Override
	public void onClick(View arg0) {

		if (arg0==einrollen) {
			GameView.touched();
			GameView.getFigur().setBitmap(GameView.geteinrollen());
		}
		else if (arg0==ausbreiten) {
			GameView.touched();
			GameView.getFigur().setBitmap(GameView.getausbreiten());
		}
		
		else if (arg0==hecht) {
			GameView.touched();
			GameView.getFigur().setBitmap(GameView.gethecht());			
		}

		/*falls pausiert wird, soll der MainThread0 nicht updaten und ein Dialog Fenster aufgerufen werden |
          if paused, pause the MainThread0 und call a dialogue window
          Quelle fuer Dialogmenu: http://www.helloandroid.com/tutorials/how-display-custom-dialog-your-android-application */
		else if (arg0==pause) {
			
			GameView.getthread().setRunning(false);
			 dialog = new Dialog(Level3Activity.this);
			dialog.setContentView(R.layout.dialog);
			dialog.setTitle("Pausemenu");
			dialog.setCancelable(true);
			
			Button back = (Button)dialog.findViewById(R.id.button1);
			
			back.setOnClickListener(new OnClickListener() {
				@Override
					public void onClick(View v) {

					dialog.cancel();
					GameView.resumen();
					
				}
				});
			musik = (Button)dialog.findViewById(R.id.button2);

			if (background.isPlaying()) {
				musik.setText(getResources().getString(R.string.dialog2));
			}
			else {
				musik.setText(getResources().getString(R.string.dialog5));
			}
			
			musik.setOnClickListener(new OnClickListener() {
				@Override
					public void onClick(View v) {

					if (background.isPlaying()) {
						background.stop();

						musik.setText(getResources().getString(R.string.dialog5));
						volume = false;
					}
					//Code aus: http://stackoverflow.com/questions/2969242/problems-with-mediaplayer-raw-resources-stop-and-start
					else {

						/*falls Musik aus ist, soll ein neues "Lied"-Objekt zugeteilt werden
                          if music is off, create a new song for the MediaPlayer*/
						background = MediaPlayer.create(getApplicationContext(), R.raw.mario);
						background.start();
						GameView.setmedia(background);
						musik.setText(getResources().getString(R.string.dialog2));
						volume = true;
					}
					
					
				}
				});
			
			Button help = (Button)dialog.findViewById(R.id.button3);
			
			help.setOnClickListener(new OnClickListener() {
				@Override
					public void onClick(View v) {

					/*erzeugt neuen Dialog im Dialog |
                      creates a new Dialog within current Dialog */
					dialog2 = new Dialog(Level3Activity.this);
					dialog2.setContentView(R.layout.activity_hilfe);
					dialog2.setTitle("Tutorial");
					dialog2.setCancelable(true);
					Button back2 = (Button)dialog2.findViewById(R.id.hitebutton1);
					back2.setOnClickListener(new OnClickListener() {
						@Override
							public void onClick(View v) {
							;
							dialog2.cancel();
							
						}
						});
					dialog2.show();
					
				}
				});
			
			Button menu = (Button)dialog.findViewById(R.id.button4);

			/*schliesst alle relevanten Prozesse, damit sauber beendet wird |
              before calling a new activity, close all processes in current activity*/
			menu.setOnClickListener(new OnClickListener() {
			@Override
				public void onClick(View v) {
				background.stop();
				background.release();
				GameView.surfaceDestroyed(GameView.getHolder());
				Intent myIntent = new Intent(Level3Activity.this, Hauptmenu.class);
				myIntent.putExtra("checker", volume);
				dialog.cancel();
				startActivity(myIntent);
			}
			});
			dialog.show();
			
			}
			
		
	}

	/**@return enthaelt aktuelle Musik | get the current music in the MediaPlayer
	 *
	 * */
    public MediaPlayer getmusic() {
    	return background;
    }

	/**@return ist Musik an/aus? | music on/off?
	 *
	 * */
 	public boolean getvol() {
 		return volume;
 	}
}
