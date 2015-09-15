package com.msci.benchmark.aggragator.service.test;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msci.benchmark.aggregator.dto.BenchmarkClientDTO;
import com.msci.benchmark.aggregator.dto.DeliveryChannelDTO;

public class SampleDataGenerator {

	public static void main(String[] args) throws JsonProcessingException {
		BenchmarkClientDTO benchmarkClientDTO = new BenchmarkClientDTO();
		
		benchmarkClientDTO.setName("acme");
		benchmarkClientDTO.setDescription("Demo Org");
		
		List<String> authorizedBenchMarks  = new ArrayList<>();
		authorizedBenchMarks.add("msci");
		authorizedBenchMarks.add("s&p");
		
		benchmarkClientDTO.setAuthorizedBenchMarks(authorizedBenchMarks);
		
		List<DeliveryChannelDTO> deliveryChannels  = new ArrayList<>();
		
		DeliveryChannelDTO ftp = new DeliveryChannelDTO();
		
		ftp.setName("acme-ftp");
		ftp.setType("ftp");
        ftp.setURL("ftp://acme-ftp.com:21");
        ftp.setUsename("foo");
        ftp.setPassword("*****");
        
        deliveryChannels.add(ftp);
        
        benchmarkClientDTO.setDeliveryChannels(deliveryChannels);
        
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(benchmarkClientDTO));
        
	}

}
