package de.innovas.km.testsystem.innolectus.irrs.client.boundary;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

public class LegacyExposingResourceIT {

	Client client;
	WebTarget tut;	
	String clientIdforTesting;
	
	@Before
	public void init() {
		this.clientIdforTesting = "demo_PKV_1";
		
		this.client = ClientBuilder.newClient();
		this.tut = this.client.target("http://innolectus.innovas.de:1180/irrs/resources/legacy/clients/lastModified/" + clientIdforTesting);
	}	
	
	@Test
	public void lastModified() {

		Response response = this.tut.request(MediaType.TEXT_PLAIN).get();			
		assertThat(response.getStatus(), is(200));
		
		Long payload = response.readEntity(Long.class);
		
		Date lastModified = new Date(payload);
		
		System.out.println(clientIdforTesting + " is lastModified: " + lastModified);				
	}
	
	@Test
	public void downnload() {
		
		this.tut = this.client.target("http://innolectus.innovas.de:1180/irrs/resources/legacy/clients/download/" + clientIdforTesting);

		Response response = this.tut.request(MediaType.APPLICATION_OCTET_STREAM).get();			
		assertThat(response.getStatus(), is(200));
		
		String attachment = response.getHeaderString("Content-Disposition");		
		System.out.println(attachment);
		
		assertThat(attachment, containsString("DEMO_PKV"));		
	}
}
