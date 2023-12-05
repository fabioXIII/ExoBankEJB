package it.exolab.exobank.utils;

import java.util.Date;
import java.text.SimpleDateFormat;

public class FormatDate {
	
	public static String formattaData(Date data) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); // Modifica il formato come preferisci

		String dataFormattata = sdf.format(data);

		return dataFormattata;

	}

}
