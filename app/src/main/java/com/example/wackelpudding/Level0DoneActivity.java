package com.example.wackelpudding;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

import android.content.Intent;

public class Level0DoneActivity extends Activity implements OnClickListener{
	private Button next;
	boolean volume;//dazu da, um Musik aus/an-Wahl konstant zu halten
	private Button menu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_level0_done);
		next = (Button)findViewById(R.id.lv0nextlvl);
        next.setOnClickListener(this);
        
        
        
        menu =(Button)findViewById(R.id.lv0main);
        menu.setOnClickListener(this);
	}

	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_level0_done, menu);
		return true;
	}*/
	
	@Override
    public void onClick(View v) {
		//nimmt den boolean Wert aus dem vorherigen Aufruf und gibt diesen weiter
		//takes the boolean from previous call and passes it on
        	volume = getIntent().getBooleanExtra("checker", volume);
        	
        	if (v==next) {
        	Intent myIntent = new Intent(this, Level1Activity.class);
        	myIntent.putExtra("checker", volume);
        	startActivity(myIntent);
        	}
        	
        	
        	else {
        		Intent myIntent = new Intent(this, Hauptmenu.class);	
        		myIntent.putExtra("checker", volume);
        		startActivity(myIntent);
        	}
        	
        }
}
