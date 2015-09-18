package com.acme.reference.impl.framework;

import java.io.IOException;

import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.Populator;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ClasspathDescriptorFileFinder;

public class JerseyHk2AnnotationScanner {
    final ServiceLocator serviceLocator;
    
 
    public JerseyHk2AnnotationScanner(final ServiceLocator serviceLocatorIn) {
        serviceLocator = serviceLocatorIn;
        
    }
 
    public void scan() {
        try {
            DynamicConfigurationService dcs = serviceLocator.getService(DynamicConfigurationService.class);
            Populator populator = dcs.getPopulator();
            populator.populate(new ClasspathDescriptorFileFinder());
        } catch (IOException e) {
            throw new MultiException(e);
        }
    }
}
