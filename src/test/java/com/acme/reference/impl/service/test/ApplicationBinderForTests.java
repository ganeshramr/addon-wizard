package com.acme.reference.impl.service.test;

import javax.inject.Singleton;

import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.acme.reference.impl.dao.BenchmarkAggregatorDAOI;
import com.acme.reference.impl.dao.InMemoryBenchmarkClientMngmtDAO;
import com.acme.reference.impl.di.qualifiers.DefaultDAO_;
import com.acme.reference.impl.dto.BenchmarkClientDTO;
import com.acme.reference.impl.service.BechmarkClientMngmtService;

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
