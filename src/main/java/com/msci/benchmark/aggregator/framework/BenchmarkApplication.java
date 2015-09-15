package com.msci.benchmark.aggregator.framework;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class BenchmarkApplication extends ResourceConfig {
    public BenchmarkApplication() {
    	
        register(new ApplicationBinder());
        register(io.swagger.jaxrs.listing.ApiListingResource.class);
        register(io.swagger.jaxrs.listing.SwaggerSerializers.class);
        register(JacksonFeature.class);
        packages(true, "com.msci.benchmark.aggregator.rest");
    }
}

