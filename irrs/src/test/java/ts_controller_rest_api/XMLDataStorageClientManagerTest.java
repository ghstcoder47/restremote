package ts_controller_rest_api;

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import de.innovas.km.innolectus.irrs.applicationconfiguration.ConfigProperties;
import de.innovas.km.innolectus.irrs.business.client.boundary.XMLDataStorageClientManager;
import de.innovas.km.innolectus.irrs.business.client.entity.Testclient;

public class XMLDataStorageClientManagerTest {

	Testclient cut;

	@Before
	public void init() {

		try {
			cut = XMLDataStorageClientManager.findById("iskv_mssql_IKK_CLASSIC");
		} catch (NoSuchElementException | IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testclientTest() {

		assertNotNull(cut);
		assertEquals(cut.getClientPfad(), "DRG/Test/IKK_CLASSIC_MSSQL/");
	}

	@Test
	public void test() {
	
		String xmlUmgebungenDIR = ConfigProperties.getPropertyValue(ConfigProperties.XML_UMGEBUNGEN_DIR);

		assertThat(xmlUmgebungenDIR, notNullValue());
		assertTrue(Objects.equals(xmlUmgebungenDIR, "src/test/resources/xmlUmgebungen"));
	}

	

	@Test
	public void listTestclientsTest() {

		List<Testclient> testClients = XMLDataStorageClientManager.findAll();
		assertTrue(testClients.size() > 0);
	}

	@Test
	public void testclientDownloadTest() {
		
		String id = cut.getId();
		try {
			Path tmpClietnPath = XMLDataStorageClientManager.getLastModifiedClient(id);
			String testclientName = tmpClietnPath.toFile().getName();
			assertThat(testclientName, both(containsString("IKK_CLASSIC")).and(containsString("zip")));
		} catch (NoSuchElementException | IOException e) {
			String message = e.getMessage();
			fail(message + " id: " + id);
		}
	}

}
