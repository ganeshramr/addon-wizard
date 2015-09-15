package com.msci.benchmark.aggregator.service;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jvnet.hk2.annotations.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msci.benchmark.aggregator.dao.GenericBenchmarkAggregatorDAO;
import com.msci.benchmark.aggregator.dto.BenchmarkClientDTO;
import com.msci.benchmark.aggregator.exception.BechmarkClientServiceException;

@Service
public class BechmarkClientMngmtService {
	
	private static final Logger logger = LogManager.getLogger(BechmarkClientMngmtService.class);	
	
	@Inject  @Named("BenchmarkClientMngmtInMemoryDAO")
	private GenericBenchmarkAggregatorDAO<BenchmarkClientDTO> benchmarkClientMgmtDAO;

	public String createClient(BenchmarkClientDTO benchmarkClientDTO) throws BechmarkClientServiceException {
		
		logger.debug("BechmarkClientMngmtService:createClient  is invoked");
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			
			logger.debug(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(benchmarkClientDTO));
			return benchmarkClientMgmtDAO.create(benchmarkClientDTO);
			
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
			throw new BechmarkClientServiceException("CREATION Failed");
		}
		
		
	}
	
	
public BenchmarkClientDTO readClient(String clientId) throws BechmarkClientServiceException {
		
		logger.debug("BechmarkClientMngmtService:readClient is invoked for id {}",clientId);
		
		try {
			
			return benchmarkClientMgmtDAO.read(clientId);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new BechmarkClientServiceException("READ Failed");
		}
		
		
	}

}
