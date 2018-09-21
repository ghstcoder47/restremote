package de.innovas.km.innolectus.irrs.business.server.boundary;

import java.io.IOException;
import java.util.NoSuchElementException;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.innovas.km.innolectus.irrs.business.server.entity.AppSrv;

/**
 * 
 * @author janasd
 * REST Schnitstelle für einen Server nach KEY (aus Testclient umgebung)
 */

@Stateless
public class ServerResource {
	
	private final static Logger LOGGER = LogManager.getLogger(ServerResource.class.getName());
	
	/**
	 * @author janasd
	 */
	
	String key;
	ServerManager serverManager;
	
	public ServerResource() {
		
	}
	
	public ServerResource(String key, ServerManager serverManager) {
		this.key = key;
		this.serverManager = serverManager;
	}
	
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public AppSrv getAppSrvByKey() throws NoSuchElementException, IOException {
		LOGGER.info("find: " + key);
		return serverManager.findById(key);
	}
	
	
	
	public Response getStatus() throws NoSuchElementException, IOException {
		LOGGER.info("getStatus: " + key);
		AppSrv result = serverManager.findById(key);
		return Response.ok(result).build();
	}

}
