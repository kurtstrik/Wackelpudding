package com.example.wackelpudding;

public class StringInt {
	//Autor: Julia
	
		//In den Shared Preferences werden der Name und der Score gespeichert,
		//damit beides aber "schoen" herausgeholt und so uebergeben werden kann, dass
		//das Programm es anzeigen kann, gibt es diese Klasse, mit der ein Array 
		//vom Typ StringInt (nimmt einen String fuer den Namen und eine int fuer 
		//den Score) erstellt wird.
	
		public String name ;
		public int score ;
		
		public StringInt(String n, int s) {
			name = n ;
			score = s ;
		
		}
		
		public String getSt() {
			return name ;
		}
		
		public int getIn() {
			return score ;
		}
}
