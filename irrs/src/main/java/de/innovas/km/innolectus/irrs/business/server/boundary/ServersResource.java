package de.innovas.km.innolectus.irrs.business.server.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.innovas.km.innolectus.irrs.business.server.entity.AppSrv;

/**
 * 
 * @author janasd
 * allgemeine REST ankopplung für Server
 */

@Stateless
@Path("server")
public class ServersResource {

	private final static Logger LOGGER = LogManager.getLogger(ServersResource.class.getName());

	/**
	 * @author janasd
	 */
	@Inject
	ServerManager serverManager;

	public ServersResource() {
	}
	
	@GET
	@Path("{key}")
	public ServerResource find(@PathParam("key") String key) {
		return new ServerResource(key, serverManager);
	}
	
	
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<AppSrv> getAppSrvList() {
		LOGGER.info("getAppSrvList");
		return serverManager.findAll();
	}
}
