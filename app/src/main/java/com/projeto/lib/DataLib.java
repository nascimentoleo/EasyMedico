package com.projeto.lib;

import android.system.OsConstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DataLib {
	
	
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

	public static String getDataFormatada(int dia, int mes, int ano){
		String sDia, sMes, sAno;
		sDia = String.valueOf(dia);
		sMes = String.valueOf(mes);
		sAno = String.valueOf(ano);
		if (dia < 10)
			sDia = "0" + sDia;
		if (mes < 10)
			sMes = "0" + sMes;
		return sDia + "/" + sMes + "/" + sAno;
	}

	public static String dataAtual(){
		Calendar calendar = Calendar.getInstance();
        return getDataFormatada(calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
	}
	
	

}
