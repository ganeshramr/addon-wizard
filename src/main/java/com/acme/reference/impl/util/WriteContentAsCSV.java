package com.acme.reference.impl.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import com.acme.reference.impl.dto.TopLevelDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WriteContentAsCSV {
	
	ObjectMapper mapper = new ObjectMapper();
	

	public void write(List<TopLevelDTO> toplevels, Path file, Template template) throws JsonParseException, JsonMappingException, IOException{
		
		toplevels.stream().map(tl -> tl.hydrateTemplate(template)).forEach(content -> writeToFile(content+System.lineSeparator(),file));
		
	}
	
	private void writeToFile(String content,Path file) {
        try {
        	Files.write(file, content.getBytes(),StandardOpenOption.APPEND,StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	
}
