package com.acme.reference.impl.service.test;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.glassfish.hk2.api.PerLookup;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.acme.reference.impl.dao.BenchmarkAggregatorDAOI;
import com.acme.reference.impl.dao.InMemoryDBBenchmarkClientDAO;
import com.acme.reference.impl.di.qualifiers.InMemory_;
import com.acme.reference.impl.framework.AppEntityManagerFactory;
import com.acme.reference.impl.framework.AppEMFFactory;
import com.acme.reference.impl.model.BenchmarkClient;
import com.acme.reference.impl.service.BechmarkClientMngmtService;
import com.acme.reference.impl.service.DemoService;

/*
 * Add ONLY what is needed by tests
 * 
 * */
public class ApplicationBinderForTests extends AbstractBinder {
    @Override
    protected void configure() {
    	//USE THIS ONLY FOR TESTS
       bind(DemoService.class).to(DemoService.class);
       bind(BechmarkClientMngmtService.class).to(BechmarkClientMngmtService.class).in(PerLookup.class);
       bind(InMemoryDBBenchmarkClientDAO.class).qualifiedBy(new InMemory_()).to(new TypeLiteral<BenchmarkAggregatorDAOI<BenchmarkClient>>() {}).in(PerLookup.class);
       bindFactory(AppEMFFactory.class).to(EntityManagerFactory.class).in(Singleton.class);
   	   bindFactory(AppEntityManagerFactory.class).to(EntityManager.class).in(PerLookup.class);
   	
    }
}
