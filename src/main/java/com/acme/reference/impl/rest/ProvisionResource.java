package com.acme.reference.impl.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.net.URI;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sun.misc.BASE64Decoder;

import com.acme.reference.impl.service.ProvisionService;

@Api(tags={"Add on Provisoning  service"}) //swagger resource annotation
@Path("/heroku/resources")
public class ProvisionResource {
	
	private static final Logger logger = LogManager.getLogger(ProvisionResource.class);
	
	@Inject
	ProvisionService demoService ;

	@GET
    @Produces("application/json")
	@ApiOperation(value = "Dummy Test method",
		    notes = "Dummy method to prove deployment - Check")
    public Response testMethod(@HeaderParam("authorization") String authString,@Context HttpServletRequest request) {
		 
		
		 logger.debug("Resource class is invoked");
		 String url = "http://localhost:8080/addon-wizard/ui/";
			
			URI targetURIForRedirection = URI.create(url);
		 return Response.seeOther(targetURIForRedirection).cookie(new NewCookie("heroku-nav-data","my-data","/","", "comment", 100, false)).build();
		 
		 
    }
	
	

	@POST
    @Produces("application/json")
	@Consumes("application/json")
	@ApiOperation(value = "Dummy Test method",
		    notes = "Dummy method to prove deployment")
    public Response provision(String data ,@HeaderParam("authorization") String authString) {
		
		if(!authCheck(authString)){
			 return Response.status(Response.Status.UNAUTHORIZED).build();
		 }
		
		 logger.debug("Resource class is invoked {} ",data);
		 return Response.ok(demoService.ack(), MediaType.APPLICATION_JSON).build();
    }
	
	@DELETE
  
	@Consumes("application/json")
	@Path("{id}")
	@ApiOperation(value = "Dummy Test method",
		    notes = "Dummy method to prove deployment")
    public Response deProvision(@PathParam("id") String id,String data,@HeaderParam("authorization") String authString) {
		
		if(!authCheck(authString)){
			 return Response.status(Response.Status.UNAUTHORIZED).build();
		 }
		
		logger.debug("Resource class is invoked {} for {}",data,id );
		return Response.status(Response.Status.OK).build();
    }
	
	@PUT
    @Produces("application/json")
	@Consumes("application/json")
	@Path("{id}")
	@ApiOperation(value = "Dummy Test method",
		    notes = "Dummy method to prove deployment")
    public Response planChange(@PathParam("id") String id,String data ,@HeaderParam("authorization") String authString) {
		
		if(!authCheck(authString)){
			 return Response.status(Response.Status.UNAUTHORIZED).build();
		 }
		
		 logger.debug("Resource class is invoked {} for {}",data,id );
		 return Response.ok(demoService.ack(), MediaType.APPLICATION_JSON).build();
    }
	
private boolean authCheck(String authString) {
		
		logger.debug("authString {}" ,authString);
		if(authString==null){
			return false;
		}
		
		String[] authParts = authString.split("\\s+");
        String authInfo = authParts[1];
        String auth = null;
        try {
			 auth = new String (new BASE64Decoder().decodeBuffer(authInfo));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String username = auth.split(":")[0];
		String password = auth.split(":")[1];
		logger.debug("username {} pwd {}" ,username,password);
		if(username==null || password == null){
				return false;
		}
		
		if(username.equals("blockchain") && password.endsWith("eb20d907f3cf16de7428a2650abd4084")){
			return true;
		}
		
		return false;
	}
}
