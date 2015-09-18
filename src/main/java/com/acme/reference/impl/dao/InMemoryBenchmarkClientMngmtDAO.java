package com.acme.reference.impl.dao;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Singleton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jvnet.hk2.annotations.Service;

import com.acme.reference.impl.di.qualifiers.DefaultDAO;
import com.acme.reference.impl.dto.BenchmarkClientDTO;

@Service 
@DefaultDAO
@Singleton
public class InMemoryBenchmarkClientMngmtDAO implements BenchmarkAggregatorDAOI<BenchmarkClientDTO> {
	
	private ConcurrentHashMap<String,BenchmarkClientDTO> benchmarkClientStore = null;
	private static final Logger logger = LogManager.getLogger(InMemoryBenchmarkClientMngmtDAO.class);

	@Override
	public String create(BenchmarkClientDTO entity) {
		
		getStore().put(entity.getName(), entity);
		return entity.getName();
	}

	@Override
	public BenchmarkClientDTO read(String id) {
		
		return getStore().get(id);
	}

	@Override
	public BenchmarkClientDTO update(BenchmarkClientDTO entity) {
		
		getStore().put(entity.getName(), entity);
		return entity;
	}

	@Override
	public void delete(String id) {
		getStore().remove(id);
		
	}
	
	private Map<String,BenchmarkClientDTO> getStore(){
		
		logger.debug("benchmarkClientStore {}",benchmarkClientStore);
		
		if(benchmarkClientStore == null){
			benchmarkClientStore = new ConcurrentHashMap<>();
		}
		logger.debug("benchmarkClientStore {}",benchmarkClientStore.keySet());
		
		return benchmarkClientStore;
	}

	

}
