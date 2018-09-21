package de.innovas.km.innolectus.irrs.business.client.boundary;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.innovas.km.innolectus.irrs.business.client.entity.Testclient;
import de.innovas.km.innolectus.irrs.business.server.boundary.ServerManager;
import de.innovas.km.innolectus.irrs.business.serverremote.boundary.RemoteManager;
import de.innovas.km.innolectus.irrs.business.serverremote.boundary.RemoteResource;

@Stateless
public class ClientResource {

	/**
	 * @author janasd
	 * 
	 * REST Schnitstelle bezogen auf einen Client nach seiner ID
	 * zb Zeige Deteils, Download, 
	 */
	
	
	private final static Logger LOGGER = LogManager.getLogger(ClientResource.class.getName());

	String id;
	ClientManager clientManager;
	ServerManager serverManager;
	RemoteManager remoteManager;
	
	public ClientResource() {
		
	}
	
	public ClientResource(String id, ClientManager cm, ServerManager sm, RemoteManager rm) {
		this.id = id;
		this.clientManager = cm;
		this.serverManager = sm;
		this.remoteManager = rm;
	}
	
	@Path("remote")
	public RemoteResource remote() {
		return new RemoteResource(id, clientManager, serverManager, remoteManager);
	}
	
	@GET
	@Path("download")
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public Response getClient() {

		
		LOGGER.info("downloadClient: " + id);

		Testclient testclient;
		Response response;
		try {
			testclient = clientManager.findById(id);
		} catch (NoSuchElementException | IOException e) {
			e.printStackTrace();
			response = Response.serverError().build();
			return response;
		}
		File file = testclient.getStandaloneFile().toFile();

		LOGGER.info("Datei zum download: " + file.toString());
		ResponseBuilder responseBuilder = Response.ok(testclient.getStandaloneFile().toFile());
		responseBuilder.header("Content-Disposition", "attachment; filename=" + file.getName());

		response = responseBuilder.build();
		return response;

	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getUmgebungById() {

		LOGGER.info("getUmgebungById");
		Testclient result = null;
		Response response;
		try {
			result = clientManager.findById(id);
			response = Response.ok(result).build();
		} catch (NoSuchElementException | IOException e) {
			e.printStackTrace();
			response = Response.ok(Response.serverError()).build();
		}

		return response;
	}
	
	@GET
	@Path("lastModified")
	@Produces("text/plain")
	public Response getLastModifiedDate() {

		String result = null;

		try {
			
			java.nio.file.Path clientFile = clientManager.getLastModified(id);

			result = Long.toString(clientFile.toFile().lastModified());

		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error("zip datei konnte nicht ermittelt werden fuer: " + id);
			result = "FILE NOT FOUND NO DATE: " + e.toString();
		}

		return Response.status(200).entity(result).build();

	}

}
