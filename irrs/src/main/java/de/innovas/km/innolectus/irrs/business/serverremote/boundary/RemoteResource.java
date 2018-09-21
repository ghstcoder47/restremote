package de.innovas.km.innolectus.irrs.business.serverremote.boundary;

import java.io.IOException;
import java.io.Serializable;
import java.util.NoSuchElementException;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.innovas.km.innolectus.irrs.business.client.boundary.ClientManager;
import de.innovas.km.innolectus.irrs.business.client.entity.Testclient;
import de.innovas.km.innolectus.irrs.business.server.boundary.ServerManager;
import de.innovas.km.innolectus.irrs.business.server.entity.AppSrv;

/**
 * 
 * @author janasd
 * REST Ankppelung über JSCH zum starten und stoppen von Testservern
 */

@Stateless
public class RemoteResource implements Serializable {

	private static final long serialVersionUID = 1L;

	private final static Logger LOGGER = LogManager.getLogger(RemoteResource.class.getName());
	
	String clientId;	
	RemoteManager remoteManager;
	ServerManager serverManager;
	ClientManager clientManager;
	
	public RemoteResource() {
	}
	
	public RemoteResource(String clientId, ClientManager cm, ServerManager sm, RemoteManager rm) {
		this.clientId = clientId;
		this.clientManager = cm;
		this.serverManager = sm;
		this.remoteManager = rm;
	}

	@GET
	@Path("start")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response callStartSkript() throws NoSuchElementException, IOException {

		LOGGER.info("callStartSkript");

		Testclient testclient = clientManager.findById(clientId);
		String skriptToRun = testclient.getStartSkript() + " > /dev/null 2>&1";
		return callSkript(testclient, skriptToRun);
	}
	
	@GET
	@Path("stop")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response callStopSkript() throws NoSuchElementException, IOException {

		LOGGER.info("callStopSkript");
		
		Testclient testclient = clientManager.findById(clientId);		
		String skriptToRun = testclient.getStopSkript();
		return callSkript(testclient, skriptToRun);
	}

	private Response callSkript(Testclient testclient, String skriptToRun) throws NoSuchElementException, IOException {

		LOGGER.info("callStartSkript");

		int returnStatus = 1337;

		AppSrv appSrv = serverManager.findById(testclient.getApplicationServerInstanz());

		String user = appSrv.getUser();
		String host = appSrv.getHost();
		try {
			returnStatus = remoteManager.callSkript(user, host, skriptToRun);
		} catch (NullPointerException e) {
			LOGGER.error("callSkript: " + e.getMessage());
		}
		return Response.ok(skriptToRun + " returnCode: " + returnStatus).build();
	}

	

}
