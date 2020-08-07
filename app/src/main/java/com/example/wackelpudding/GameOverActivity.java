package com.example.wackelpudding;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;

public class GameOverActivity extends Activity implements OnClickListener{
	private Button back;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_over);
		back =(Button)findViewById(R.id.gobutton1);
        back.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_game_over, menu);
		return true;
	}
	
	@Override
    public void onClick(View v) {
		Intent myIntent = new Intent(this, Hauptmenu.class);    	
    	startActivity(myIntent);
    }

}
