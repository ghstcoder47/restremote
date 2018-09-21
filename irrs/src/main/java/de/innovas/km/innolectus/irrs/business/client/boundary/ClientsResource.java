package de.innovas.km.innolectus.irrs.business.client.boundary;

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

import de.innovas.km.innolectus.irrs.business.client.entity.Testclient;
import de.innovas.km.innolectus.irrs.business.server.boundary.ServerManager;
import de.innovas.km.innolectus.irrs.business.serverremote.boundary.RemoteManager;

@Stateless
@Path("clients")
public class ClientsResource {

	/**
	 * @author janasd
	 * 
	 * REST Schnitstelle Clients allgemein
	 */
	 
	private final static Logger LOGGER = LogManager.getLogger(ClientsResource.class.getName());
	


	@Inject
	ClientManager clientManager;
	@Inject
	ServerManager serverManager;
	@Inject
	RemoteManager remoteManager;
	
	public ClientsResource() {
	}
	
	
	@Path("{id}")
	public ClientResource find(@PathParam("id") String id) {
		return new ClientResource(id, clientManager, serverManager, remoteManager);
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Testclient> getClients() {

		LOGGER.info("getClients alle");

		List<Testclient> result = clientManager.findAll();

		return result;
	}
	
	
}
