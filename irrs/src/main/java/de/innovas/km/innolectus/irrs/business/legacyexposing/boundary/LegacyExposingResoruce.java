package de.innovas.km.innolectus.irrs.business.legacyexposing.boundary;

import java.io.IOException;
import java.util.NoSuchElementException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import de.innovas.km.innolectus.irrs.business.client.boundary.ClientManager;
import de.innovas.km.innolectus.irrs.business.client.boundary.ClientResource;
import de.innovas.km.innolectus.irrs.business.server.boundary.ServerManager;
import de.innovas.km.innolectus.irrs.business.serverremote.boundary.RemoteManager;
import de.innovas.km.innolectus.irrs.business.serverremote.boundary.RemoteResource;

/**
 * 
 * @author janasd
 * REST Methoden für Legacy anbindung der TA
 */

@Stateless
@Path("legacy")
public class LegacyExposingResoruce {
	
	@Inject
	ClientManager clientManager;
	@Inject
	ServerManager serverManager;
	@Inject
	RemoteManager remoteManager;
	
	@Path("clients/download/{id}")
	@GET
	public Response legacyMode(@PathParam("id") String id) {
		return new ClientResource(id, clientManager, serverManager, remoteManager).getClient();
	}	
	
	@Path("clients/remote/start/{id}")
	public Response remoteStart(@PathParam("id") String id) throws NoSuchElementException, IOException {
		return new RemoteResource(id, clientManager, serverManager, remoteManager).callStartSkript();
	}
	
	@Path("clients/remote/stop/{id}")
	public Response remoteStop(@PathParam("id") String id) throws NoSuchElementException, IOException {
		return new RemoteResource(id, clientManager, serverManager, remoteManager).callStopSkript();
	}
	
	@GET
	@Path("clients/lastModified/{id}")
	@Produces("text/plain")
	public Response getLastModifiedDate(@PathParam("id") String id) {

		String result = null;

		try {
			
			java.nio.file.Path clientFile = clientManager.getLastModified(id);

			result = Long.toString(clientFile.toFile().lastModified());

		} catch (IOException e) {
			e.printStackTrace();
			result = "FILE NOT FOUND NO DATE: " + e.toString();
		}

		return Response.status(200).entity(result).build();

	}

}
