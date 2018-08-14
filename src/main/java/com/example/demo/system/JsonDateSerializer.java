package com.example.demo.system;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JsonDateSerializer extends JsonSerializer <LocalDate>{

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	
	@Override
	public void serialize(LocalDate localDate, JsonGenerator gen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		
		
		String formatDate = dateFormat.format(localDate);
		gen.writeString(formatDate);
		
	}

}
