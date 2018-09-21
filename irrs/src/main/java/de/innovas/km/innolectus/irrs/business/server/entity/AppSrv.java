package de.innovas.km.innolectus.irrs.business.server.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="applicationServerInstanz")
@XmlAccessorType(XmlAccessType.FIELD)
public class AppSrv {
	
	@XmlElement(name="name")
	private String key;
	private String user;
	private String host;	
	
	public AppSrv() {
	}
	
	public AppSrv(String key, String user, String host) {
		this.key = key;
		this.user = user;
		this.host = host;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	
	
	
}
