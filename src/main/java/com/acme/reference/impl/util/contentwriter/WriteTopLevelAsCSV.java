package com.acme.reference.impl.util.contentwriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import com.acme.reference.impl.dto.TopLevelDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WriteTopLevelAsCSV implements FileWriterI<TopLevelDTO>{
	
	ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public void write(List<TopLevelDTO> toplevels, Path file, CSVTemplate template) throws JsonParseException, JsonMappingException, IOException{
		
		template.writeHeaderTo(file);
		toplevels.stream().map(tl -> template.hydrateTemplateWith(tl)).forEach(content -> writeToFile(content+System.lineSeparator(),file));
		
	}
	

	@Override
	public void write(List<TopLevelDTO> content, Path writeTo)
			throws JsonParseException, JsonMappingException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		CSVTemplate template = mapper.readValue(this.getClass().getClassLoader().getResource("toplevel_csv_template.json"), CSVTemplate.class);
		write(content, writeTo, template);
		
	}

	private void writeToFile(String content,Path file) {
        try {
        	Files.write(file, content.getBytes(),StandardOpenOption.APPEND,StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	
	
}
