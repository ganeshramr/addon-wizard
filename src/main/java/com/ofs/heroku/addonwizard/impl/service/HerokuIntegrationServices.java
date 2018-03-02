package com.ofs.heroku.addonwizard.impl.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
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
import com.ofs.heroku.addonwizard.impl.dto.request.ProvisionRequest;
import com.ofs.heroku.addonwizard.impl.dto.response.Config;
import com.ofs.heroku.addonwizard.impl.dto.response.ProvisionResponse;

@Service
public class HerokuIntegrationServices {

	private static final Logger logger = LogManager.getLogger(HerokuIntegrationServices.class);
	private static HashMap<String,ProvisionRequest> addOnProvisioningDataMemoryMap = new HashMap<>();
	private static HashMap<String,AddOnTokens> addonTokenMemoryMap = new HashMap<>();

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
		addOnProvisioningDataMemoryMap.put(data.getUuid(), data);
		
		//EXCHANGE OAUTH GRANT FOR TOKEN:https://devcenter.heroku.com/articles/add-on-partner-api-reference#grant-code-exchange
		
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("grant_type", "authorization_code"));
		params.add(new BasicNameValuePair("code", data.getOauthGrant().getCode()));
		params.add(new BasicNameValuePair("client_secret",ScaryContants.PARTNER_CLIENT_SECRET));
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
					addonTokenMemoryMap.put(data.getUuid(), tokens);
					logger.debug("tokenMemoryMap {}",addonTokenMemoryMap);
					
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
		config.setBLOCKCHAINSERVICEURL("https://blockchain-deploy-wizard.herokuapp.com/rest/heroku/resources");

		provisionResponse.setConfig(config);

		return provisionResponse;
	}
	
	public void deployAddOnBundle(String appName) throws ClientProtocolException, IOException{
		//logger.debug("uuid {}",uuid);
		logger.debug("appName {}",appName);
		
		logger.debug("tokenMemoryMap {}",addonTokenMemoryMap);
		//logger.debug("App ID In MAP {}",addonTokenMemoryMap.get(uuid));
		//logger.debug("Access Token in MAP {}",addonTokenMemoryMap.get(uuid).getAccessToken());
		
		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost("https://api.heroku.com/apps/"+appName+"/builds");
		httppost.setHeader("Accept", "application/vnd.heroku+json; version=3");
		
		httppost.addHeader("Authorization", "Bearer "+ScaryContants.OAUTH_API_KEY);
		DeployData deployData = new DeployData();
		SourceBlob sourceBlob = new SourceBlob();
		sourceBlob.setVersion(keyGen());
		sourceBlob.setUrl(ScaryContants.ADMIN_APP_BUNLDE);
		deployData.setSourceBlob(sourceBlob);
		ObjectMapper mapper = new ObjectMapper();
		String jsonInJson = mapper.writeValueAsString(deployData);
		StringEntity entity = new StringEntity(jsonInJson);
		logger.debug(jsonInJson);
		httppost.setEntity(entity);
		// Execute and get the response.
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity resEntity = response.getEntity();
		if (entity != null) {
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

}
