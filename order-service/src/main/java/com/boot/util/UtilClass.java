package com.boot.util;

import java.util.Calendar;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class UtilClass {

	public static String createOrderNumber() {
		Calendar cal = Calendar.getInstance();
		String ord_num = String.valueOf(cal.get(Calendar.HOUR_OF_DAY)) + String.valueOf(cal.get(Calendar.MINUTE))
				+ String.valueOf(cal.get(Calendar.SECOND)) + String.valueOf(cal.get(Calendar.MILLISECOND));
		return ord_num;
	}


}
