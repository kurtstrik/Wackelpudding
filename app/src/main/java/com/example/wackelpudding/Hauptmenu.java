package com.example.wackelpudding;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageView;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation;
import android.content.Intent;

public class Hauptmenu extends Activity implements OnClickListener{
	private Button Start;
	private Button Option;
	//private Button Score;
	private Button Help;
	private Button Schummel;
	private Button Tutorial;

	private ImageView hell;
	private ImageView mittel;
	private ImageView dunkel;

	private MediaPlayer background;

	boolean volume;//dazu da, um Musik aus/an-Wahl konstant zu halten | keeps track if music is on/off

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hauptmenu);
          
        hell = (ImageView)findViewById(R.id.icon);
        mittel = (ImageView)findViewById(R.id.icon2);
        dunkel = (ImageView)findViewById(R.id.icon3);
        
        Start = (Button)findViewById(R.id.Button1);
        Start.setOnClickListener(this);
        
        Option =(Button)findViewById(R.id.Button2);
        Option.setOnClickListener(this);


   		background = MediaPlayer.create(this, R.raw.jazz);
		background.setLooping(true);


        volume = getIntent().getBooleanExtra("checker", volume);
        if (volume) {
        	if(!background.isPlaying()) {

				background.setVolume(0.3f, 0.3f);
				background.start();
				Option.setText(getResources().getString(R.string.dialog2));
			}
        }
        else {
        	Option.setText(getResources().getString(R.string.dialog5));
        }

        Help =(Button)findViewById(R.id.Button4);
        Help.setOnClickListener(this);

        Tutorial =(Button)findViewById(R.id.Button5);
		Tutorial.setOnClickListener(this);

        Animation a = AnimationUtils.loadAnimation(this, R.anim.menu_load);
        hell.startAnimation(a);
		Animation b = AnimationUtils.loadAnimation(this, R.anim.menu_load);
		mittel.startAnimation(b);
		Animation c = AnimationUtils.loadAnimation(this, R.anim.menu_load);
		dunkel.startAnimation(c);
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_hauptmenu, menu);
        return true;
    }*/
    
    @Override
    public void onClick(View v) {

        	if (v==Start) {
				background.stop();//aktuelle Musik vor Wechsel beenden | stop current music before activity change
				background.release();
        	Intent myIntent = new Intent(this, Level0Activity.class);
        	myIntent.putExtra("checker", volume);
        	startActivity(myIntent);
        	}
        	else if (v==Option){
        		
        		if (volume) {
					//Musik deaktivieren | turn music off

					if(background.isPlaying())
        				background.pause();

        			Option.setText(getResources().getString(R.string.dialog5));
        			volume = false;
        		}
        		else {

        			//Musik starten | start music
					if(!background.isPlaying()) {
						background.setVolume(0.3f, 0.3f);
						background.start();
					}
        			Option.setText(getResources().getString(R.string.dialog2));
        			volume = true;
        		}
        		
        		//Intent myIntent = new Intent(this, OptionActivity.class);
        		//startActivity(myIntent);
        	}

        	else if(v==Tutorial){
				background.stop();//aktuelle Musik vor Wechsel beenden | stop current music before activity change
				background.release();
				Intent myIntent = new Intent(this, TutorialActivity.class);
				myIntent.putExtra("checker", volume);
				startActivity(myIntent);
			}

        	else {
					Intent myIntent = new Intent(this, HilfeActivity.class);
					myIntent.putExtra("checker", volume);
					startActivity(myIntent);
			}
        }
}
