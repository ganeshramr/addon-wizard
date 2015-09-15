package com.msci.benchmark.aggregator.framework;

import javax.inject.Singleton;

import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.msci.benchmark.aggregator.dao.BenchmarkClientMngmtDummyDAO;
import com.msci.benchmark.aggregator.dao.BenchmarkClientMngmtInMemoryDAO;
import com.msci.benchmark.aggregator.dao.GenericBenchmarkAggregatorDAO;
import com.msci.benchmark.aggregator.dto.BenchmarkClientDTO;
import com.msci.benchmark.aggregator.service.BechmarkClientMngmtService;
import com.msci.benchmark.aggregator.service.DemoService;

public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
       bind(DemoService.class).to(DemoService.class);
       bind(BechmarkClientMngmtService.class).to(BechmarkClientMngmtService.class);
       bind(BenchmarkClientMngmtInMemoryDAO.class).named("BenchmarkClientMngmtInMemoryDAO")
                                                  .to(new TypeLiteral<GenericBenchmarkAggregatorDAO<BenchmarkClientDTO>>() {})
                                                  .in(Singleton.class);
       bind(BenchmarkClientMngmtDummyDAO.class).named("BenchmarkClientMngmtDummyDAO").to(new TypeLiteral<GenericBenchmarkAggregatorDAO<BenchmarkClientDTO>>() {});
    }
}
