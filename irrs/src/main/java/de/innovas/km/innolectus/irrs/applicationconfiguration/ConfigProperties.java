package de.innovas.km.innolectus.irrs.applicationconfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.innovas.km.innolectus.irrs.business.client.boundary.ClientsResource;

/**
 * 
 * @author janasd
 * Die Klasse stellt properties zu verfügung, welche über die externe datei "config.properties" bereit gestellt werden 
 */



public enum ConfigProperties {
	
	XML_UMGEBUNGEN_DIR("xmlUmgebungenDIR"),
	TESTCLIENT_DIR("testclientsDir"),
	APPLICATION_SERVER_LIST("applicationServerList"),
	CONFIG_FILE("config.properties"),
	INNOLECTUS_SCRIPT_DIR("innolektusScriptDir"),
	INNOLECTUS_HOST_USER("innolectusHostUser"),
	INNOLECTUS_HOST("innolectusHost"),
	PRIVATE_RSA_KEY_FILE("privateRSAKey"),
	INNOLECTUS_SKRIPT_START("innolectusStartSkript"),
	INNOLECTUS_SKRIPT_STOP("innolectusStopSkript");
	
	private String value;
	
	ConfigProperties(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public static Properties initializeProperties() {
				
		final Logger LOGGER = LogManager.getLogger(ClientsResource.class.getName());
		
		Properties properties = new Properties();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				
		try (InputStream input = classLoader.getResourceAsStream(CONFIG_FILE.getValue())) {
			properties.load(input);
			
			properties.list(System.out);
		} catch (IOException | NullPointerException e) {
			LOGGER.error("scheisse: " + e.getMessage());
			
			throw new RuntimeException("probleme mit config.properties" + e);
		}
		return properties;
	}	
	
	public static String getPropertyValue(ConfigProperties property) {
		return initializeProperties().getProperty(property.getValue());
	}
	
}
