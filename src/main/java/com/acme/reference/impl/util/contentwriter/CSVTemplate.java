package com.acme.reference.impl.util.contentwriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.acme.reference.impl.util.annotaions.FieldOnFile;

public class CSVTemplate {
	
	private String[] fieldsInOrder;
	private String delimiter;
	private Object content;
	
	public String[] getFieldsInOrder() {
		return fieldsInOrder;
	}

	public void setFieldsInOrder(String[] fieldsInOrder) {
		this.fieldsInOrder = fieldsInOrder;
	}
  
	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public String toString(){
		return Arrays.deepToString(getFieldsInOrder());
	}
	
	private  Function<String,String> fieldToValue = (field) -> { 
		return Arrays.stream(content.getClass().getMethods())
				 .filter(m -> m.getAnnotation(FieldOnFile.class)!=null && m.getAnnotation(FieldOnFile.class).name().equals(field))
				 .map(m -> {
					        try{ 
					        	 return(String) m.invoke(content);
					        	}catch(Exception e){
					        		e.printStackTrace();
					        		throw new RuntimeException(e);
					        	}
					        })
				 .collect(Collectors.joining(""));
				 
	};
	
	public String hydrateTemplateWith(Object content){
		this.content = content;
		return Arrays.stream(getFieldsInOrder()).map(fieldToValue).collect(Collectors.joining(getDelimiter()));
		
	}

	/*THIS MUST USE CREATE_NEW since that will indicate that that file is already 
	 * available and any way writing the header to an already existing file should file*/
	public void writeHeaderTo(Path file) throws IOException {
		
		String header = Arrays.stream(getFieldsInOrder())
				              .reduce((t, u) -> t + getDelimiter() + u)
				              .get();
		Files.write(file, (header+System.lineSeparator()).getBytes(),StandardOpenOption.CREATE_NEW,StandardOpenOption.CREATE);
	}

	
	
}
