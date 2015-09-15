package com.msci.benchmark.aggregator.dto;

import java.util.List;

public class BenchmarkClientDTO {
	
	String name;
	String description;
	List<String> authorizedBenchMarks;
	List<DeliveryChannelDTO> deliveryChannels;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<String> getAuthorizedBenchMarks() {
		return authorizedBenchMarks;
	}
	public void setAuthorizedBenchMarks(List<String> authorizedBenchMarks) {
		this.authorizedBenchMarks = authorizedBenchMarks;
	}
	public List<DeliveryChannelDTO> getDeliveryChannels() {
		return deliveryChannels;
	}
	public void setDeliveryChannels(List<DeliveryChannelDTO> deliveryChannels) {
		this.deliveryChannels = deliveryChannels;
	}
	
	

}
