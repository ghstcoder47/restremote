package de.innovas.km.testsystem.innolectus.irrs.client.boundary;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

public class ClientsResourceIT {

	Client client;
	WebTarget tut;	
	String clientIdforTesting;
	
	@Before
	public void init() {
		this.clientIdforTesting = "demo_PKV_1";
		this.client = ClientBuilder.newClient();
		this.tut = this.client.target("http://innolectus.innovas.de:1180/irrs/resources/clients");
	}
	
	
	@Test
	public void fetchCLients() {

		Response response = this.tut.request(MediaType.APPLICATION_JSON).get();			
		assertThat(response.getStatus(), is(200));
		
		JsonArray payload = response.readEntity(JsonArray.class);
		JsonObject payloadItem = payload.getJsonObject(0);
		System.out.println(payloadItem);
		
		assertTrue(payloadItem.getString("id").startsWith("ext_wf_kunde_v1"));
		
		JsonObject get = this.tut.path("ext_wf_kunde_v1").request(MediaType.APPLICATION_JSON).get(JsonObject.class);
		assertThat(get.getString("id"), containsString(payloadItem.getString("id")));
		
	}
	
	@Test
	public void download() {
		
		this.tut = this.client.target("http://innolectus.innovas.de:1180/irrs/resources/clients/demo_PKV_1/download");
		
		
		Response response = this.tut.request(MediaType.APPLICATION_OCTET_STREAM).get();			
		assertThat(response.getStatus(), is(200));
		
		String attachment = response.getHeaderString("Content-Disposition");		
		System.out.println(attachment);
		
		assertThat(attachment, containsString("DEMO_PKV"));		
		
	}
	
	

}
