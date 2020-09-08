package com.example.wackelpudding;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;

public class HilfeActivity extends Activity implements OnClickListener {
	boolean volume;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hilfe);
	}
/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_hilfe, menu);
		return true;
	}
*/
	@Override
    public void onClick(View v) {

		/*
		volume = getIntent().getBooleanExtra("checker", volume);
        Intent myIntent = new Intent(this, Hauptmenu.class);
        myIntent.putExtra("checker", volume);
      startActivity(myIntent);*/

    	finish(); //zurueck zu vorheriger Hauptmenu Activity | resume back to previous Hauptmenu Activity
    }

}
