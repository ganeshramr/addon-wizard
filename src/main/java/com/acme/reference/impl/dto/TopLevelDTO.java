package com.acme.reference.impl.dto;

import java.sql.Timestamp;

import com.acme.reference.impl.util.annotaions.FieldOnFile;

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
	
	@FieldOnFile(name="BENCH_MDS_UID")
	public String getBenchMdsUID() {
		return benchMdsUID;
	}
	public void setBenchMdsUID(String benchMdsUID) {
		this.benchMdsUID = benchMdsUID;
	}
	
	@FieldOnFile(name="INDEX_MDS_UID")
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
		
	}

