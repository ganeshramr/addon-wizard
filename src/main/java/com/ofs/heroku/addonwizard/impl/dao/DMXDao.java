package com.ofs.heroku.addonwizard.impl.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.ofs.heroku.addonwizard.impl.di.qualifiers.DMX;

public class DMXDao {
	
	@Inject
	@DMX
	EntityManager em;

	//DO DMX stuff here
	
}
