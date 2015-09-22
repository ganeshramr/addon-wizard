package com.acme.reference.impl.service.test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.acme.reference.impl.dto.TopLevelDTO;
import com.acme.reference.impl.util.contentwriter.CSVTemplate;
import com.acme.reference.impl.util.contentwriter.WriteTopLevelAsCSV;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Test
public class TestCSVTemplate {
	
	
	@Test
	public void hydrateTemplateTest() throws JsonParseException, JsonMappingException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		CSVTemplate template = mapper.readValue(this.getClass().getClassLoader().getResource("toplevel_csv_template.json"), CSVTemplate.class);
		
		TopLevelDTO tl = new TopLevelDTO();
		tl.setBenchMdsUID("DAS");
		tl.setIndexMdsUID("SAMPLE");
		
		System.out.println(template.hydrateTemplateWith(tl));
		
	    Assert.assertNotNull(template.toString());
	}
	
	@Test
	public void write () throws JsonParseException, JsonMappingException, IOException{
		WriteTopLevelAsCSV writeToCSV = new WriteTopLevelAsCSV();
		List<TopLevelDTO> tllist = new ArrayList<>();
		
		TopLevelDTO tl = new TopLevelDTO();
		tl.setBenchMdsUID("BMK000000022588459");
		tl.setIndexMdsUID("UNX000000022728997");
		
		TopLevelDTO tl2 = new TopLevelDTO();
		tl2.setBenchMdsUID("BMK000000022588459");
		tl2.setIndexMdsUID("UNX000000022728997");
		
		tllist.add(tl);
		tllist.add(tl2);
		
		
		Path p1 = Paths.get("/Users/ganesh/projects/msciwrkspace/benchmark-aggregator/CSVFILEFORTOPLEVEL.csv");
		ObjectMapper mapper = new ObjectMapper();
		CSVTemplate template = mapper.readValue(this.getClass().getClassLoader().getResource("toplevel_csv_template.json"), CSVTemplate.class);
		
		writeToCSV.write(tllist, p1,template);
		
		
	}

}
