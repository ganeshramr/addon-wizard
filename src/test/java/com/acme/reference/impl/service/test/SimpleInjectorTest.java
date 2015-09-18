package com.acme.reference.impl.service.test;

import javax.inject.Inject;

import org.jvnet.testing.hk2testng.HK2;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.acme.reference.impl.service.BechmarkClientMngmtService;

@HK2 (binders = {ApplicationBinderForTests.class})
public class SimpleInjectorTest  {

	
	@Inject
	BechmarkClientMngmtService benchmarkClientService ;

    @Test
    public void assertPrimaryServiceInjecton() {
        Assert.assertNotNull(benchmarkClientService);
    }
}
