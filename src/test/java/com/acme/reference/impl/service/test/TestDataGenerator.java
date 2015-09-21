package com.acme.reference.impl.service.test;

import java.util.ArrayList;
import java.util.List;

import com.acme.reference.impl.dto.BenchmarkClientDTO;
import com.acme.reference.impl.dto.DeliveryChannelDTO;

public class TestDataGenerator {

	public static BenchmarkClientDTO generateBenchmarkClientDTO(){
		
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
        
		return benchmarkClientDTO;
        
	}

}
