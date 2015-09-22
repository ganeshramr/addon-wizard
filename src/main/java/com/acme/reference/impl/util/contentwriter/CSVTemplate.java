package com.acme.reference.impl.util.contentwriter;

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

	
	
}
