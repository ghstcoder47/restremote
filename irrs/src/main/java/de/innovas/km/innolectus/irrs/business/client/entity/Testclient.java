package de.innovas.km.innolectus.irrs.business.client.entity;

import java.nio.file.Path;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 * @author janasd
 * Entity für XML - Java Bindings
 */

@XmlRootElement(name = "umgebung")
@XmlAccessorType(XmlAccessType.FIELD)
public class Testclient {

	private String id;
	private String clientPfad;
	private String startSkript;
	private String stopSkript;
	private String applicationServerInstanz;	

	@XmlTransient
	private Path standaloneFile;
	

	public Testclient() {
		
	}
	
	public Testclient(String id, String clientPfad, String startSkript, String stopSkript, String applicationServerInstanz) {
		this.id = id;
		this.clientPfad = clientPfad;
		this.startSkript = startSkript;
		this.stopSkript = stopSkript;
		this.applicationServerInstanz = applicationServerInstanz;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClientPfad() {
		return clientPfad;
	}

	public void setClientPfad(String clientPfad) {
		this.clientPfad = clientPfad;
	}

	public String getStartSkript() {
		return startSkript;
	}

	public void setStartSkript(String startSkript) {
		this.startSkript = startSkript;
	}

	public String getStopSkript() {
		return stopSkript;
	}

	public void setStopSkript(String stopSkript) {
		this.stopSkript = stopSkript;
	}
	
	public Path getStandaloneFile() {
		return standaloneFile;
	}

	public void setStandaloneFile(Path standaloneFile) {
		this.standaloneFile = standaloneFile;
	}
	
	public String getApplicationServerInstanz() {
		return applicationServerInstanz;
	}

	public void setApplicationServerInstanz(String applicationServerInstanz) {
		this.applicationServerInstanz = applicationServerInstanz;
	}
	
	
}
