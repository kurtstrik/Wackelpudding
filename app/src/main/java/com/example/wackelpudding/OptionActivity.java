package com.example.wackelpudding;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import android.content.Intent;
import android.widget.TextView;

import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;

public class OptionActivity extends Activity implements OnClickListener {
	
	TextView txt2;
	TextView txt3;
	CheckBox box1;	
	Button back;
	public boolean music;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        
        
        
        String bsp2 = getResources().getString(R.string.optbutton1);
        txt2 = (TextView)findViewById(R.id.Music_on_off);
        txt2.setText(bsp2);
        
        box1 = (CheckBox)findViewById(R.id.checkBox1);
       
        
        String bsp3 = getResources().getString(R.string.optbutton2);
        txt3 = (TextView)findViewById(R.id.OptButton2);
        txt3.setText(bsp3);
               
        back = (Button)findViewById(R.id.OptBack);
        back.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_option, menu);
        return true;
    }
    
  public void onClick(View v) {
    	
    	if (v==back){
    		Intent myIntent = new Intent(this, Hauptmenu.class);
    		
    		startActivity(myIntent);
    		
    	}
    }
}
