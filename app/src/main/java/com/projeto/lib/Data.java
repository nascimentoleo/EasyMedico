package com.projeto.lib;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Data {
	
	
	public static boolean dataValida(String data){
		DateFormat df = new SimpleDateFormat ("dd/MM/yyyy");  
		df.setLenient (false); // aqui o pulo do gato  
		try {  
		    df.parse (data); 
		    // data valida
		    return true;
		} catch (ParseException ex) {  
		   // data invalida
			return false;
		} 
	}
	
	

}
