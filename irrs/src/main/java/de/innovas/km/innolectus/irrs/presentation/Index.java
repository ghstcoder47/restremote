package de.innovas.km.innolectus.irrs.presentation;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.innovas.km.innolectus.irrs.applicationconfiguration.ConfigProperties;
import de.innovas.km.innolectus.irrs.business.client.boundary.ClientManager;
import de.innovas.km.innolectus.irrs.business.serverremote.boundary.RemoteManager;

/**
 * 
 * @author janasd
 * BackingBean für index.xhtml
 * 
 * Ermoeglicht innoLectus REMOTE start / stop und gibt einige Informationen an die View
 */

@Model
public class Index {
	
	Logger LOGGER = LogManager.getLogger(Index.class.getName());

	@Inject
	ClientManager clientBoundary;
	@Inject
	RemoteManager remoteBoundary;
	
	@PostConstruct
	public void init() {
//		LOGGER.info("post construction");
	}
	
	public String getMessage() {
		return "Hello from Presentation";
	}
	
	public void startInnolectus() {
		
		LOGGER.info("startInnolectus");
		String skriptPath = getInnolectusSkriptPath();		
		callInnolectusRemoteSkript(skriptPath + "/serviceStart.sh" + " > /dev/null 2>&1");
	}	
	
	public void stopInnolectus() {
		
		LOGGER.info("stopInnolectus");
		String skriptPath = getInnolectusSkriptPath();
		callInnolectusRemoteSkript(skriptPath + "/serviceStop.sh");
	}
	
	
	private void callInnolectusRemoteSkript(String skript) {
		String user = ConfigProperties.getPropertyValue(ConfigProperties.INNOLECTUS_HOST_USER);
		String host = ConfigProperties.getPropertyValue(ConfigProperties.INNOLECTUS_HOST);
		remoteBoundary.callSkript(user, host, skript);
	}	
	
	private String getInnolectusSkriptPath() {
		return ConfigProperties.initializeProperties().getProperty(ConfigProperties.INNOLECTUS_SCRIPT_DIR.getValue());
	}
	
	
}
