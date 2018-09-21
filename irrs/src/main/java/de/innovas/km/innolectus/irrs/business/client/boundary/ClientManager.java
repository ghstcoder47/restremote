package de.innovas.km.innolectus.irrs.business.client.boundary;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;

import javax.ejb.Stateless;

import de.innovas.km.innolectus.irrs.business.client.entity.Testclient;

/**
 * 
 * @author janasd
 * abkoppelung der Fachlichkeit von der Technik (XML - Java Bindings)
 */

@Stateless
public class ClientManager {
	
	public Testclient findById(String id) throws NoSuchElementException, IOException {
		return XMLDataStorageClientManager.findById(id);
	}
	
	public List<Testclient> findAll() {
		return XMLDataStorageClientManager.findAll();
	}
	
	public Path getLastModified(String id) throws IOException {
		return XMLDataStorageClientManager.getLastModifiedClient(id);
	}

}
