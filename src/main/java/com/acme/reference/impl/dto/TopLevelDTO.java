package com.acme.reference.impl.dto;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.acme.reference.impl.util.Field;
import com.acme.reference.impl.util.Template;

public class TopLevelDTO {
	
	private Timestamp observationTs;
	private String benchMdsUID;
	private String indexMdsUID;
	private String vendorId;
	private String returnHorizon;
	
	public Timestamp getObservationTs() {
		return observationTs;
	}
	public void setObservationTs(Timestamp observationTs) {
		this.observationTs = observationTs;
	}
	
	@Field(name="BENCH_MDS_UID")
	public String getBenchMdsUID() {
		return benchMdsUID;
	}
	public void setBenchMdsUID(String benchMdsUID) {
		this.benchMdsUID = benchMdsUID;
	}
	
	@Field(name="INDEX_MDS_UID")
	public String getIndexMdsUID() {
		return indexMdsUID;
	}
	public void setIndexMdsUID(String indexMdsUID) {
		this.indexMdsUID = indexMdsUID;
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public String getReturnHorizon() {
		return returnHorizon;
	}
	public void setReturnHorizon(String returnHorizon) {
		this.returnHorizon = returnHorizon;
	}
	
	
	private  Function<String,String> fieldToValue = (field) -> { 
		return Arrays.stream(this.getClass().getMethods())
				 .filter(m -> m.getAnnotation(Field.class)!=null && m.getAnnotation(Field.class).name().equals(field))
				 .map(m -> {
					        try{ 
					        	 return(String) m.invoke(this);
					        	}catch(Exception e){
					        		e.printStackTrace();
					        		throw new RuntimeException(e);
					        	}
					        })
				 .collect(Collectors.joining(""));
				 
	};
	
	public String hydrateTemplate(Template t){
		
		return Arrays.stream(t.getFieldsInOrder()).map(fieldToValue).collect(Collectors.joining(t.getDelimiter())); }
		
	}

