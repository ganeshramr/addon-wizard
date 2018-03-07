package com.ofs.heroku.addonwizard.impl.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jvnet.hk2.annotations.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ofs.heroku.addonwizard.impl.dto.AddOnTokens;
import com.ofs.heroku.addonwizard.impl.dto.DeployData;
import com.ofs.heroku.addonwizard.impl.dto.SourceBlob;
import com.ofs.heroku.addonwizard.impl.dto.request.DeployRequest;
import com.ofs.heroku.addonwizard.impl.dto.request.ProvisionRequest;
import com.ofs.heroku.addonwizard.impl.dto.response.Config;
import com.ofs.heroku.addonwizard.impl.dto.response.ProvisionResponse;

@Service
public class HerokuIntegrationServices {

	private static final Logger logger = LogManager.getLogger(HerokuIntegrationServices.class);
	

	public ProvisionResponse ack() {
		logger.debug("Test service is invoked now");
		ProvisionResponse provisionResponse = new ProvisionResponse();
		provisionResponse.setId("37136775-c6f6-4d29-aa6b-12d1dad4febb");
		provisionResponse.setMessage("Yee Haw Add On");

		Config config = new Config();
		config.setBLOCKCHAINSERVICEURL("https://blockchain-deploy-wizard.herokuapp.com/rest/heroku/resources");

		provisionResponse.setConfig(config);

		return provisionResponse;

	}

	public ProvisionResponse provision(ProvisionRequest data) throws Exception {

		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost("https://id.heroku.com/oauth/token");
		AddOnTokens tokens = null;
		
		//STORE PROVISION REQUEST
		logger.debug("data {} {}", data.getOauthGrant().getCode(),data.getUuid());
		StaticStorage.addOnProvisioningDataMemoryMap.put(data.getUuid(), data);
		
		//EXCHANGE OAUTH GRANT FOR TOKEN:https://devcenter.heroku.com/articles/add-on-partner-api-reference#grant-code-exchange
		
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("grant_type", "authorization_code"));
		params.add(new BasicNameValuePair("code", data.getOauthGrant().getCode()));
		params.add(new BasicNameValuePair("client_secret",ScaryConstants.PARTNER_CLIENT_SECRET));
		try {
			
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				InputStream instream = entity.getContent();
				try {
					
					ObjectMapper mapper = new ObjectMapper();
					tokens = mapper.readValue(instream, AddOnTokens.class);
					logger.debug("UUID {} Access tokens {} Refresh Token {}",data.getUuid(), tokens.getAccessToken(),tokens.getRefreshToken());
					//STORE TOKEN EXCHANGED
					StaticStorage.addonTokenMemoryMap.put(data.getUuid(), tokens);
					logger.debug("addonTokenMemoryMap {}",StaticStorage.addonTokenMemoryMap);
					
				} finally {
					instream.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		ProvisionResponse provisionResponse = new ProvisionResponse();
		provisionResponse.setId(data.getUuid());
		provisionResponse.setMessage("Your add on is being provisioned");

		Config config = new Config();
		config.setBLOCKCHAINSERVICEURL("http://private-blockchain.herokuapp.com/");

		provisionResponse.setConfig(config);

		return provisionResponse;
	}
	
	public void deployAddOnBundle(DeployRequest data) throws ClientProtocolException, IOException{
		logger.debug("uuid {}",data.getUuid());
		logger.debug("appName {}",data.getAppName());
		
		logger.debug("oauthTokenMemoryMap {}",StaticStorage.oauthTokenMemoryMap);
		logger.debug("token object {}",StaticStorage.oauthTokenMemoryMap.get(data.getUuid()));
		logger.debug("Access Token in MAP {}",StaticStorage.oauthTokenMemoryMap.get(data.getUuid()).getAccessToken());
		
		logger.debug("UPDATE CONFIG VAR");
		HttpClient httpclient = HttpClients.createDefault();
		HttpPatch httpPatch = new HttpPatch("https://api.heroku.com/apps/"+data.getAppName()+"/config-vars");
		httpPatch.setHeader("Accept", "application/vnd.heroku+json; version=3");
		httpPatch.addHeader("Authorization", "Bearer "+StaticStorage.oauthTokenMemoryMap.get(data.getUuid()).getAccessToken());
		httpPatch.addHeader("Content-Type", "application/json");
		String configJson = "{\"BLOCKCHAIN_SERVICE_URL\":\"http://"+data.getAppName()+".herokuapp.com/blockchain\"}";
		logger.debug(configJson);
		httpPatch.setEntity(new StringEntity(configJson));
		// Execute and get the response.
		HttpResponse response = httpclient.execute(httpPatch);
		HttpEntity resEntity = response.getEntity();
		if (resEntity != null) {
			InputStream instream = resEntity.getContent();
			try {
				StringWriter writer = new StringWriter();
				IOUtils.copy(instream, writer);
				String theString = writer.toString();
				logger.debug(theString);
			} finally {
				instream.close();
			}
		}
		
		logger.debug("UPDATE APP STACK");
		
	    httpclient = HttpClients.createDefault();
	    httpPatch = new HttpPatch("https://api.heroku.com/apps/"+data.getAppName());
		httpPatch.setHeader("Accept", "application/vnd.heroku+json; version=3");
		httpPatch.addHeader("Authorization", "Bearer "+StaticStorage.oauthTokenMemoryMap.get(data.getUuid()).getAccessToken());
		httpPatch.addHeader("Content-Type", "application/json");
		String stackJson = "{\"build_stack\": \"container\",\"maintenance\": false,\"name\": \""+data.getAppName()+"\"}";
		logger.debug(stackJson);
		httpPatch.setEntity(new StringEntity(stackJson));
		// Execute and get the response.
		response = httpclient.execute(httpPatch);
		resEntity = response.getEntity();
		if (resEntity != null) {
			InputStream instream = resEntity.getContent();
			try {
				StringWriter writer = new StringWriter();
				IOUtils.copy(instream, writer);
				String theString = writer.toString();
				logger.debug(theString);
			} finally {
				instream.close();
			}
		}
		
		logger.debug("DEPLOY BUNDLE");
		
		HttpPost httppost = new HttpPost("https://api.heroku.com/apps/"+data.getAppName()+"/builds");
		httppost.setHeader("Accept", "application/vnd.heroku+json; version=3");
		
		httppost.addHeader("Authorization", "Bearer "+StaticStorage.oauthTokenMemoryMap.get(data.getUuid()).getAccessToken());
		DeployData deployData = new DeployData();
		SourceBlob sourceBlob = new SourceBlob();
		sourceBlob.setVersion(keyGen());
		sourceBlob.setUrl(ScaryConstants.ADMIN_APP_BUNLDE);
		deployData.setSourceBlob(sourceBlob);
		ObjectMapper mapper = new ObjectMapper();
		String jsonInJson = mapper.writeValueAsString(deployData);
		StringEntity entity = new StringEntity(jsonInJson);
		logger.debug(jsonInJson);
		httppost.setEntity(entity);
		// Execute and get the response.
		response = httpclient.execute(httppost);
		resEntity = response.getEntity();
		if (resEntity != null) {
			InputStream instream = resEntity.getContent();
			try {
				StringWriter writer = new StringWriter();
				IOUtils.copy(instream, writer);
				String theString = writer.toString();
				logger.debug(theString);
			} finally {
				instream.close();
			}
		}
		
		StaticStorage.addonDeployStatusMap.put(data.getUuid(), true);
		
	}
	
	private String keyGen(){
		
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder(20);
		Random random = new Random();
		for (int i = 0; i < 20; i++) {
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);
		}
		return sb.toString();
		
	}

	public void fetchToken(String code,String uuid) throws Exception {
		// TODO Auto-generated method stub
		
		//curl -X POST https://id.heroku.com/oauth/token \
		//	-d "grant_type=authorization_code&code=01234567-89ab-cdef-0123-456789abcdef&client_secret=01234567-89ab-cdef-0123-456789abcdef"
		
		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost("https://id.heroku.com/oauth/token");
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("grant_type", "authorization_code"));
		params.add(new BasicNameValuePair("code", code));
		params.add(new BasicNameValuePair("client_secret",ScaryConstants.OAUTH_CLIENT_SECRET));
		AddOnTokens tokens = null;
try {
			
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				InputStream instream = entity.getContent();
				try {
					
					ObjectMapper mapper = new ObjectMapper();
					tokens = mapper.readValue(instream, AddOnTokens.class);
					logger.debug("UUID {} Access tokens {} Refresh Token {}",uuid, tokens.getAccessToken(),tokens.getRefreshToken());
					//STORE TOKEN EXCHANGED
					StaticStorage.oauthTokenMemoryMap.put(uuid, tokens);
					logger.debug("oauthTokenMemoryMap {}",StaticStorage.oauthTokenMemoryMap);
					
				} finally {
					instream.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

}
