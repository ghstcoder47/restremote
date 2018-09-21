package de.innovas.km.innolectus.irrs.business.server.boundary;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.innovas.km.innolectus.irrs.applicationconfiguration.ConfigProperties;
import de.innovas.km.innolectus.irrs.business.server.entity.AppSrv;
import de.innovas.km.innolectus.irrs.business.server.entity.AppSrvCollection;

/**
 * 
 * @author janasd
 * verarbeitung von Server bezogenen XML Files. Bereitstellung von JavaObjecten aus XML
 */

public class XMLDataStorageServerManager {

	private final static Logger LOGGER = LogManager.getLogger(XMLDataStorageServerManager.class.getName());

	public static AppSrv findById(String key) {
		
		LOGGER.info("findById: " + key);
		
		List<AppSrv> servers = findAll();
		return servers.stream().filter(s -> Objects.equals(s.getKey(), key)).findFirst().get();
	}

	
	public static List<AppSrv> findAll() {
		
		LOGGER.info("findAll");
		
		String appSrvList = ConfigProperties.getPropertyValue(ConfigProperties.APPLICATION_SERVER_LIST);
		AppSrvCollection col = null;

		Path appSrvListFile = Paths.get(appSrvList);
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(AppSrvCollection.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			col = (AppSrvCollection) unmarshaller.unmarshal(new File(appSrvListFile.toString()));
		} catch (JAXBException e) {
			e.printStackTrace();
			LOGGER.error("findAll failed: " + e.getMessage());
		}

		return col.getAppSrvList();

	}
}
