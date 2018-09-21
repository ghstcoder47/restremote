package de.innovas.km.innolectus.irrs.business.server.boundary;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.innovas.km.innolectus.irrs.business.server.entity.AppSrv;

/**
 * 
 * @author janasd
 * entkoppelt Technik von Fachlichkeit  (XML-bindings)
 */

@Stateless
public class ServerManager {
	
	private final static Logger LOGGER = LogManager.getLogger(ServerManager.class.getName());
	
	public AppSrv findById(String key) throws NoSuchElementException, IOException {
		LOGGER.info("findById: " + key);
		return XMLDataStorageServerManager.findById(key);
	}
	
	
	public List<AppSrv> findAll() {
		LOGGER.info("findAll");
		return XMLDataStorageServerManager.findAll();
	}
}
