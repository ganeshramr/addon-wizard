package com.ofs.heroku.addonwizard.impl.framework;

import javax.inject.Inject;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class AddOnWizardApplication extends ResourceConfig {
	
	/**
     * Sets up all of the standard features.
     */
    @Inject
    public AddOnWizardApplication(final ServiceLocator serviceLocator) {
    	
    	new JerseyHk2AnnotationScanner(serviceLocator).scan();
        register(new ApplicationBinder());
        register(io.swagger.jaxrs.listing.ApiListingResource.class);
        register(io.swagger.jaxrs.listing.SwaggerSerializers.class);
        register(JacksonFeature.class);
        packages(true, "com.ofs.heroku.addonwizard.impl.rest");
    }
}

