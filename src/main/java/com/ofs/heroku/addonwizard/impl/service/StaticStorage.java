package com.ofs.heroku.addonwizard.impl.service;

import java.util.HashMap;

import com.ofs.heroku.addonwizard.impl.dto.AddOnTokens;
import com.ofs.heroku.addonwizard.impl.dto.request.ProvisionRequest;

public class StaticStorage {
	
	public static HashMap<String,ProvisionRequest> addOnProvisioningDataMemoryMap = new HashMap<>();
	public static HashMap<String,AddOnTokens> addonTokenMemoryMap = new HashMap<>();
	public static HashMap<String,Boolean> addonDeployStatusMap = new HashMap<>();
	public static String antiForgeryToken = "anti-forgery-token";
	public static HashMap<String,AddOnTokens> oauthTokenMemoryMap = new HashMap<>() ;

}
