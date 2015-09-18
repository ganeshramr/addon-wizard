package com.acme.reference.impl.dao;

import javax.inject.Named;

import org.jvnet.hk2.annotations.Service;

import com.acme.reference.impl.dto.BenchmarkClientDTO;

@Service @Named
public class DummyBenchmarkClientMngmtDAO implements BenchmarkAggregatorDAOI<BenchmarkClientDTO> {
	

	@Override
	public String create(BenchmarkClientDTO entity) {
		
		return "DUMMY";
	}

	@Override
	public BenchmarkClientDTO read(String id) {
		
		return new BenchmarkClientDTO();
	}

	@Override
	public BenchmarkClientDTO update(BenchmarkClientDTO entity) {
		
		return new BenchmarkClientDTO();
	}

	@Override
	public void delete(String id) {
		
		
	}
	
	
}
