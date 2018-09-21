package de.innovas.km.innolectus.irrs.business.serverremote.boundary;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author janasd
 * entkoppelung Fachlichkeit von Technik (JSCH)
 */

@Stateless
public class RemoteManager {
	
	private final static Logger LOGGER = LogManager.getLogger(RemoteManager.class.getName());
	
	public int callSkript(String user, String host, String skript) throws NullPointerException {
		LOGGER.info("callSkript " + user + " " + host + " " + skript);
		int returnStatus = JSCHManager.prepareCommand(user, host).callCommand(skript);
		return returnStatus;
	}
	

}
