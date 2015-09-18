package com.msci.benchmark.aggragator.service.test;

import javax.inject.Singleton;

import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.msci.benchmark.aggregator.dao.BenchmarkAggregatorDAOI;
import com.msci.benchmark.aggregator.dao.InMemoryBenchmarkClientMngmtDAO;
import com.msci.benchmark.aggregator.di.qualifiers.DefaultDAO_;
import com.msci.benchmark.aggregator.dto.BenchmarkClientDTO;
import com.msci.benchmark.aggregator.service.BechmarkClientMngmtService;

/*
 * Add ONLY what is needed by tests
 * 
 * */
public class ApplicationBinderForTests extends AbstractBinder {
    @Override
    protected void configure() {
    	//USE THIS ONLY FOR TESTS
    
       bind(BechmarkClientMngmtService.class).to(BechmarkClientMngmtService.class);
       bind(InMemoryBenchmarkClientMngmtDAO.class).qualifiedBy(new DefaultDAO_()).to(new TypeLiteral<BenchmarkAggregatorDAOI<BenchmarkClientDTO>>() {}).in(Singleton.class);
    }
}
