package com.gittoy.utils;

import java.util.Date;

public class DateUtil {

	public static Date formatStartDate(Date date){
		Date temp = date;
		temp.setHours(0);
		temp.setMinutes(0);
		temp.setSeconds(0);
		date = temp;
		return date;
	}
	
	public static Date formatEndDate(Date date){
		Date temp = date;
		temp.setHours(23);
		temp.setMinutes(59);
		temp.setSeconds(59);
		date = temp;
		return date;
	}
}
