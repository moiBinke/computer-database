package com.excilys.computerDatabase.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class MyTools {

	public static LocalDate convertFromDateToLocalDateViaSqlDate(Date dateToConvert) {
		if(dateToConvert==null)
			return null;
	    return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
	}
	
	public static LocalDate convertStringToLocalDateViaSqlDate(String dateString) throws ParseException {
        Date dateToConvert=new SimpleDateFormat("yyyy-MM-dd").parse(dateString);  
	    return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
	}	
}
