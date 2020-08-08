package com.example.wackelpudding;

import android.app.SearchManager;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.view.Gravity;
import android.view.ViewGroup;

import android.media.MediaPlayer;
import android.app.Activity;
import android.view.Menu;
import android.app.Dialog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.view.Display;
import android.graphics.Point;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.app.ProgressDialog;
import android.os.AsyncTask;

/**Author/Kurt
 * Code zu der Idee, Buttons im SurfaceView einzusetzen aus der Quelle: 
 * http://jwlstudios.blogspot.co.at/2012/04/how-to-add-button-to-surfaceview-in.html*/

public class Level0Activity extends Activity implements OnClickListener, SeekBar.OnSeekBarChangeListener {

    private ProgressDialog progress;
	private Button ausbreiten;
	private Button einrollen;
	private Button hecht;
	private Button pause;
	private SeekBar slider;
	private TextView tempotext;
   // private  Bitmap hintergrundbild = BitmapFactory.decodeResource(getResources(), R.drawable.hintergrund2);

  //  private int linearlayoutwidth = hintergrundbild.getWidth();


	SurfaceViewlv0 GameView;
	FrameLayout layout;
	RelativeLayout Buttons;//das Layout in dem die Buttons zusammengelegt werden


	LinearLayout tempo;
	Dialog dialog;//fuer Pausemenue
	Dialog dialog2;//fuer Hilfemenue
	private MediaPlayer background;
	//private Chronometer chrono;
	
	boolean volume;//dazu da um Musik an/aus-wahl konstant beizubehalten
	private Button musik;//um die Musik an/aus Schrift zu aendern, wenn drauf geklickt wird
	
	private int stepsize = 10;
	
    @SuppressLint({"NewApi", "ResourceType"})
    @Override
    public void onCreate(Bundle savedInstanceState) {//hier werden alle Elemente der Activity programmatisch erstellt
        super.onCreate(savedInstanceState);
        
        //entfernt Menueleisten, damit wirklich Fullscreen genutzt wird
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        //damit die Buttons eine gleichmaessige Hoehe haben, wird die Screen Height bestimmt
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
        
        GameView = new SurfaceViewlv0(this);
        layout = new FrameLayout(this);
        Buttons = new RelativeLayout(this);

        ausbreiten = new Button(this);
        einrollen = new Button(this);
        hecht = new Button(this);
        pause = new Button(this);
        background = MediaPlayer.create(this, R.raw.bossa);
        background.setLooping(true);
        //chrono = new Chronometer(this);

        tempo = new LinearLayout(this);
        tempotext = new TextView(this);
        
        einrollen.setId(1);
        ausbreiten.setId(2);
        hecht.setId(3);
        pause.setId(4);

        
       /* RelativeLayout.LayoutParams b0 =   new LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);*/

        //jeder Button soll von Breite her WRAP_CONTENT und von der Hoehe her 1/4 des Bildschirms haben

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


  //    bottom.gravity = Gravity.CENTER;
  //      bottom.bottomMargin = 10;



      /*  tempotext.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));*/

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
        //Buttons.addView(chrono);

        //damit die Buttons rechts am Rand sind und klickbar
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

        //b0.addRule(RelativeLayout.ALIGN_LEFT);
        //b0.addRule(Relative.Layout.ALIGN_BOTTOM);
        //chrono.setLayoutParams(b0);

     //   GameView = new MySurfaceView(this, displaySize.x, displaySize.y, ausbreiten.getWidth());



        layout.addView(GameView);
       // layout.addView(tempo);
        layout.addView(Buttons);


        new LoadView().execute();//Ladescreen ausfuehren
        
        
       
        //setContentView(layout);
        //background.start();
        //GameView.setmedia(background);
    }

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

    //Code dazu aus: http://www.41post.com/4588/programming/android-coding-a-loading-screen-part-1
    private class LoadView extends AsyncTask<Void, Integer, Void>  
    {  
        //Before running code in separate thread  
        @Override  
        protected void onPreExecute()  
        {  
            progress = ProgressDialog.show(Level0Activity.this,"Ladevorgang...",
                    "Level 0 wird geladen. Bitte warten...", false, false);
        }  
  
        //The code to be executed in a background thread.  
        @Override  
        protected Void doInBackground(Void... params)  
        {  
            /* This is just a code that delays the thread execution 4 times, 
             * during 850 milliseconds and updates the current progress. This 
             * is where the code that is going to be executed on a background 
             * thread must be placed. 
             */  
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
            
            volume = getIntent().getBooleanExtra("checker", volume);
            
            if (volume) {
                background.setVolume(0.25f,0.25f);
            	background.start();
            }
            //chrono.start();
            GameView.setmedia(background);  
            
        }  
    }  
    
    
    
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_level0, menu);
        return true;
    }*/
    
    @Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
    	//Quelle fuer Dialogmenu: http://www.helloandroid.com/tutorials/how-display-custom-dialog-your-android-application
    	
    	
		//signalisiert der Surface View, dass ein Button/legitimer Touch gedrueckt/erfolgt ist. das Bitmap der jeweiligen Figur wird entsprechend des Buttons gesetzt
		
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
		
		//falls pausiert wird, soll der MainThread0 nicht updaten und ein Dialog Fenster aufgerufen werden
		else if (arg0==pause) {
			
			GameView.getthread().setRunning(false);
			 dialog = new Dialog(Level0Activity.this);
			dialog.setContentView(R.layout.dialog);
			dialog.setTitle(getResources().getString(R.string.dialog0));
			dialog.setCancelable(false);
        //    dialog.setCanceledOnTouchOutside(true);
			//chrono.stop();

            Button back = (Button)dialog.findViewById(R.id.button1);

            back.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ;

                    //dialog nicht als lokale Variable, weil dismiss/cancel sonst nicht nutzbar

                    dialog.cancel();
                    GameView.resumen();
                    //chrono.start();

                }
            });
/* fehlerhaft momentan
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                   GameView.resumen();
                }
            });
*/
			musik = (Button)dialog.findViewById(R.id.button2);
			
			//falls Musik an -> Buttonschrift ebenfalls Musik an, andernfalls Musik aus
			if (background.isPlaying()) {
				musik.setText(getResources().getString(R.string.dialog2));
			}
			else {
				musik.setText(getResources().getString(R.string.dialog5));
			}
				
			musik.setOnClickListener(new OnClickListener() {
				@Override
					public void onClick(View v) {
					;
					//prueft ob Musik spielt
					
					if (background.isPlaying()) {
						
						background.stop();

						//nach klicken ebenfalls Buttonschrift und boolean fuer konstante Wahl aendern
						musik.setText(getResources().getString(R.string.dialog5));
						volume = false;
					}
					//Code aus: http://stackoverflow.com/questions/2969242/problems-with-mediaplayer-raw-resources-stop-and-start
					else {
						
						//falls Musik aus ist, soll ein neues "Lied"-Objekt zugeteilt werden
						background = MediaPlayer.create(getApplicationContext(), R.raw.bossa);
						background.setVolume(0.25f, 0.25f);
						background.start();
						musik.setText(getResources().getString(R.string.dialog2));
						GameView.setmedia(background);
						volume = true;
					}
					
					
				}
				});
			
			Button help = (Button)dialog.findViewById(R.id.button3);
			
			help.setOnClickListener(new OnClickListener() {
				@Override
					public void onClick(View v) {

					//erzeugt neuen Dialog im Dialog
					dialog2 = new Dialog(Level0Activity.this);
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
			//schliesst alle relevanten Prozesse, damit sauber beendet wird
			menu.setOnClickListener(new OnClickListener() {
			@Override
				public void onClick(View v) {
				background.stop();
				background.release();
				GameView.surfaceDestroyed(GameView.getHolder());
                dialog.cancel();
				Intent myIntent = new Intent(Level0Activity.this, Hauptmenu.class);
				myIntent.putExtra("checker", volume);

				startActivity(myIntent);
					
			}
			});
			dialog.show();
			
			}
			
		
	}
    
    /**Hilfsmethoden fuer SurfaceView*/
    public MediaPlayer getmusic() {
    	return background;
    }
    public boolean getvol() {
 		return volume;
 	}
}
