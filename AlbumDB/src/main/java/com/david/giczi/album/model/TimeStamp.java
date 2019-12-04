package com.david.giczi.album.model;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TimeStamp {

	
	private static SimpleDateFormat format;
	
	
	public static String timeStamp() {
		
		 format= new SimpleDateFormat("yyyy-MM-dd");
		
		return format.format(new Date(System.currentTimeMillis()));
		
	}
	
	
}
