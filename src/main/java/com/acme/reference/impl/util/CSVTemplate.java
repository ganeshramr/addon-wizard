package com.acme.reference.impl.util;

import java.util.Arrays;

public class CSVTemplate implements Template{
	
	private String[] fieldsInOrder;
	private String delimiter;
	
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

	
	
}
