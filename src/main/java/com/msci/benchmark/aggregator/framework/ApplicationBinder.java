package com.msci.benchmark.aggregator.framework;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
    	//USE THIS IF THE JerseyHk2AnnotationScanner screws up
    	
    	
       /*bind(DemoService.class).to(DemoService.class);
       bind(BechmarkClientMngmtService.class).to(BechmarkClientMngmtService.class);
       bind(BenchmarkClientMngmtInMemoryDAO.class).named("BenchmarkClientMngmtInMemoryDAO")
                                                  .to(new TypeLiteral<GenericBenchmarkAggregatorDAO<BenchmarkClientDTO>>() {})
                                                  .in(Singleton.class);
       bind(BenchmarkClientMngmtDummyDAO.class).named("BenchmarkClientMngmtDummyDAO").to(new TypeLiteral<GenericBenchmarkAggregatorDAO<BenchmarkClientDTO>>() {});*/
    }
}
