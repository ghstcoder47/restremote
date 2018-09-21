package de.innovas.km.innolectus.irrs.business.client.boundary;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.stream.Stream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.innovas.km.innolectus.irrs.applicationconfiguration.ConfigProperties;
import de.innovas.km.innolectus.irrs.business.client.entity.Testclient;

/**
 * 
 * @author janasd
 * verarbeitung von Testclient bezogenen XML Files. Bereitstellung von JavaObjecten aus XML
 */

public class XMLDataStorageClientManager {

	private final static Logger LOGGER = LogManager.getLogger(XMLDataStorageClientManager.class.getName());
	
	public static Testclient findById(String id) throws NoSuchElementException ,IOException {
		LOGGER.info("findById: " + id);
		List<Testclient> umgebungen = findAll();
		
		//gib den testclient mit id zurueck
		Testclient result = umgebungen.stream()
				.filter(umgebung -> umgebung.getId().equals(id))
				.findFirst()
				.get();
		
		Path clientPfad = Paths
				.get(ConfigProperties.getPropertyValue(ConfigProperties.TESTCLIENT_DIR) + "/" + result.getClientPfad());

		//durchsuche den testclient pfad nach aktuellsten zip datei
		Path clientFile;
		try {
			clientFile = Files.walk(clientPfad)
					.filter(Files::isRegularFile)
					.filter(p -> p.toString().endsWith(".zip"))
					.max(Comparator.comparing(f -> f.toFile().lastModified()))
					.get();

		} catch (NoSuchElementException | IOException e) {
			e.printStackTrace();
			LOGGER.error("standalone datei konnte nicht ermittelt werden fuer: " + id);
			return result;
		}

		result.setStandaloneFile(clientFile);

		return result;
	}
	
	public static List<Testclient> findAll() {
		LOGGER.info("findAll");
				
		String xmlUmgebungenDIR = ConfigProperties.getPropertyValue(ConfigProperties.XML_UMGEBUNGEN_DIR);
		LOGGER.info("xmlUmgebungenDIR: " + xmlUmgebungenDIR);
		final List<Testclient> result = new LinkedList<>();

		try (final Stream<Path> xmlClientsPath = Files.list(Paths.get(xmlUmgebungenDIR));) {

			xmlClientsPath.forEach(path -> {
				Testclient umgebung = null;
				try {
					JAXBContext jaxbContext = JAXBContext.newInstance(Testclient.class);
					Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
					umgebung = (Testclient) unmarshaller.unmarshal(new File(path.toString()));
				} catch (JAXBException e) {
					e.printStackTrace();
				}
				if (!umgebung.getClientPfad().trim().isEmpty()) {
					result.add(umgebung);
				}
			});
		} catch (IOException e1) {
			e1.printStackTrace();
			LOGGER.error("Umgebungen liste konnte nicht erzeugt werden: " + e1.toString());
		}

		return result;
	}

	public static Path getClientPath(String id) throws IOException {

		Testclient umgebung = findById(id);
		Path clientPfad = Paths.get(
				ConfigProperties.getPropertyValue(ConfigProperties.TESTCLIENT_DIR) + "/" + umgebung.getClientPfad());

		return clientPfad;
	}

	public static Path getLastModifiedClient(String id) throws IOException {

		Path clientPfad = getClientPath(id);

		Path clientFile = Files.walk(clientPfad).filter(Files::isRegularFile).filter(p -> p.toString().endsWith(".zip"))
				.max(Comparator.comparing(f -> f.toFile().lastModified())).get();

		return clientFile;
	}

}
