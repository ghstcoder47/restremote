package ts_controller_rest_api;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import de.innovas.km.innolectus.irrs.applicationconfiguration.ConfigProperties;

public class ConfigPropertiesTest {
	
	Properties cut;
	
	@Before
	public void init() {
				
		cut = ConfigProperties.initializeProperties();
	}

	@Test
	public void testInitializeProperties() {
		
		assertNotNull(cut);
	}
	
	@Test
	public void testServerlistProperties() {
		assertThat(cut.getProperty(ConfigProperties.APPLICATION_SERVER_LIST.getValue()), is("src/test/resources/ApplicationServer.xml"));
	}
	
	@Test
	public void testServerListPropertiesRefactoried() {
		assertThat(ConfigProperties.getPropertyValue(ConfigProperties.APPLICATION_SERVER_LIST), is("src/test/resources/ApplicationServer.xml"));
	}
	
	@Test
	public void testEquivalenz() {
		assertThat(cut.getProperty(ConfigProperties.APPLICATION_SERVER_LIST.getValue()), is(ConfigProperties.getPropertyValue(ConfigProperties.APPLICATION_SERVER_LIST)));
	}
	
	
}
