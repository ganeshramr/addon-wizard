package com.msci.benchmark.aggregator.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.msci.benchmark.aggregator.dto.BenchmarkClientDTO;
import com.msci.benchmark.aggregator.exception.BechmarkClientServiceException;
import com.msci.benchmark.aggregator.service.BechmarkClientMngmtService;

@Api(tags={"Benchmark Client Detail Management Service"}) //swagger resource annotation
@Path("/benchmarkclient/{clientId}")
public class BechmarkClientDetailResource {
	
private static final Logger logger = LogManager.getLogger(BechmarkClientDetailResource.class);
	
	@Inject
	BechmarkClientMngmtService benchmarkClientService ;

	@GET
	@Produces("application/json")
 
	@ApiOperation(value = "Read a benchmark client",
		          notes = "Read a benchmark client")
    public Response readBenchmarkClient(@PathParam("clientId") String clientId) {
		
		 logger.debug("readBenchmarkClient  is invoked for client id {}",clientId);
		 BenchmarkClientDTO reponse = null;
		 try {
			 reponse = benchmarkClientService.readClient(clientId);
		} catch (BechmarkClientServiceException e) {
			return Response.serverError().build();
		}
		 
		 return Response.ok(reponse,MediaType.APPLICATION_JSON).build();
    }

}
