package com.acme.reference.impl.service;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jvnet.hk2.annotations.Service;

import com.acme.reference.impl.dao.BenchmarkAggregatorDAOI;
import com.acme.reference.impl.di.qualifiers.DefaultDAO;
import com.acme.reference.impl.dto.BenchmarkClientDTO;
import com.acme.reference.impl.exception.BechmarkClientServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BechmarkClientMngmtService {
	
	private static final Logger logger = LogManager.getLogger(BechmarkClientMngmtService.class);	
	
	
	private final BenchmarkAggregatorDAOI<BenchmarkClientDTO> benchmarkClientMgmtDAO;
	
	@Inject 
	public BechmarkClientMngmtService(@DefaultDAO final BenchmarkAggregatorDAOI<BenchmarkClientDTO> benchmarkClientMgmtDAO){
		
		this.benchmarkClientMgmtDAO = benchmarkClientMgmtDAO; 
	}
	
	
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
