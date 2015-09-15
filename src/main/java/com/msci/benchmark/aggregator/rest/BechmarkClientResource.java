package com.msci.benchmark.aggregator.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.msci.benchmark.aggregator.dto.BenchmarkClientDTO;
import com.msci.benchmark.aggregator.exception.BechmarkClientServiceException;
import com.msci.benchmark.aggregator.service.BechmarkClientMngmtService;

@Api(tags={"Benchmark Clients Management Service"}) //swagger resource annotation
@Path("/benchmarkclient")
public class BechmarkClientResource {
	
private static final Logger logger = LogManager.getLogger(BechmarkClientResource.class);
	
	@Inject
	BechmarkClientMngmtService benchmarkClientService ;

	@POST
	@Consumes("application/json")
    @Produces("text/plain")
	@ApiOperation(value = "Create a benchmark client",
		          notes = "Create a benchmark client")
    public Response createBenchmarkClient(BenchmarkClientDTO benchmarkClientDTO) {
		
		 logger.debug("createBenchmarkClient  is invoked");
		 String reponse = null;
		 try {
			 reponse = benchmarkClientService.createClient(benchmarkClientDTO);
		} catch (BechmarkClientServiceException e) {
			return Response.serverError().build();
		}
		 
		 return Response.ok(reponse,MediaType.TEXT_PLAIN).build();
    }

}
