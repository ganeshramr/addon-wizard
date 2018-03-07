package com.ofs.heroku.addonwizard.impl.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sun.misc.BASE64Decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ofs.heroku.addonwizard.impl.dto.NavData;
import com.ofs.heroku.addonwizard.impl.service.HerokuIntegrationServices;
import com.ofs.heroku.addonwizard.impl.service.ScaryConstants;
import com.ofs.heroku.addonwizard.impl.service.StaticStorage;


@Api(tags = { "AddOnSSOResource" })
// swagger resource annotation
@Path("/heroku/sso")
public class AddOnSSOResource {

	private static final Logger logger = LogManager.getLogger(AddOnSSOResource.class);

	@Inject
	HerokuIntegrationServices demoService;

	@POST
	
	
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ApiOperation(value = "signOn", notes = "signOn")
	public Response signOn(@FormParam("token") String token,
			@FormParam("timestamp") String timestamp,
			@FormParam("id") String id, 
			@FormParam("nav-data") String rawData,
			@FormParam("email") String email,
			@Context HttpServletRequest request) throws IOException, NoSuchAlgorithmException {

		String data = new String (new BASE64Decoder().decodeBuffer(rawData));
		
		logger.debug("Resource class is invoked  id {} ", id);
		logger.debug("data{}, email {} ", data, email);
		logger.debug("timestamp {},token {} ", timestamp,
				token);

		if (token == null) {
			return Response.status(Response.Status.FORBIDDEN).build();
		}

		if (timestamp == null) {
			return Response.status(Response.Status.FORBIDDEN).build();
		}
		

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		String formedToken = id + ':' + ScaryConstants.ADD_ON_MANIFEST_SALT
				+ ':' + timestamp;
		String ourSHAToken = sha1(formedToken);

		logger.debug("ourSHAToken {} ", ourSHAToken);

		if (!ourSHAToken.equals(token)) {

			return Response.status(Response.Status.FORBIDDEN).build();

		}
		
		Timestamp timeNow = new Timestamp(System.currentTimeMillis());
		logger.debug("ourTimeNow {} for comparison {}", timeNow.getTime()/1000L,timeNow.getTime()/1000L-300000);
		logger.debug("condition satisfied ? {} ", !(new Long(timestamp) < (timeNow.getTime()/1000L-300000)));
		
		if(new Long(timestamp) < (timeNow.getTime()/1000L-300)){
			
			return Response.status(Response.Status.FORBIDDEN).build();
			
		}
		
		String url =null;
		if(StaticStorage.addonDeployStatusMap.get(id)==null || !StaticStorage.addonDeployStatusMap.get(id)){
			url = "https://"+request.getServerName()+"/ui/#/";
		}else{
			ObjectMapper objectMapper = new ObjectMapper();
			NavData navData = objectMapper.readValue(data.contains("=")?data.split("=")[0]:data, NavData.class);
			url = "http://"+navData.getAppname()+".herokuapp.com/";
		}
		
		URI targetURIForRedirection = URI.create(url);
		
	    return Response.seeOther(targetURIForRedirection).cookie(new NewCookie("heroku-nav-data",rawData,"/","", "comment", 100, false),new NewCookie("heroku_sso","true","/","", "comment", 100, false),new NewCookie("app-id",id,"/","", "comment", 100, false)).build();

	}
	
	
     
    private String sha1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
         
        return sb.toString();
    }

}
