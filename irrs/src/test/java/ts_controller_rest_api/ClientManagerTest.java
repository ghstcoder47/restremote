package ts_controller_rest_api;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import de.innovas.km.innolectus.irrs.business.client.boundary.ClientManager;
import de.innovas.km.innolectus.irrs.business.client.entity.Testclient;

public class ClientManagerTest {
	
	ClientManager cut;
	Path testPath;
	
	@Before
	public void init() {
		cut = new ClientManager();
		try {
			testPath = cut.getLastModified("iskv_mssql_IKK_CLASSIC");
		} catch (IOException e) {
			e.printStackTrace();
			fail("konnte nicht initialisiert werden");
		}
	}

	@Test
	public final void testFindById() {
		try {
			Testclient tc = cut.findById("iskv_mssql_IKK_CLASSIC");
			assertNotNull(tc);
			assertThat(tc.getId(), containsString("iskv_mssql_IKK_CLASSIC"));
		} catch (NoSuchElementException | IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public final void testFindAll() {
		
		List<String> ids = cut.findAll().stream().map(testclient -> testclient.getId()).collect(Collectors.toList());		
		assertThat(ids, notNullValue());
	}

	@Test
	public final void testGetLastModified() {
		
		assertTrue(Files.isRegularFile(testPath));
			
		assertThat(testPath.getFileName().toString(), containsString("KOLUMBUS_2018.3.00-B00_ISKV_21c_IKK_CLASSIC_MSSQL"));
	
	}

}
