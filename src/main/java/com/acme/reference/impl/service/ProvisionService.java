package com.acme.reference.impl.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jvnet.hk2.annotations.Service;

import com.acme.reference.impl.dto.Config;
import com.acme.reference.impl.dto.ProvisionResponse;

@Service
public class ProvisionService {
	
	private static final Logger logger = LogManager.getLogger(ProvisionService.class);
	
	public ProvisionResponse ack(){
		logger.debug("Test service is invoked now");
		ProvisionResponse provisionResponse = new ProvisionResponse();
		provisionResponse.setId("37136775-c6f6-4d29-aa6b-12d1dad4febb");
		provisionResponse.setMessage("Yee Haw Add On");
		
		Config config = new Config();
		config.setBLOCKCHAINSERVICEURL("https://blockchain-deploy-wizard.herokuapp.com/rest/heroku/resources");
		
		provisionResponse.setConfig(config);
		
		return provisionResponse;

	}

}
