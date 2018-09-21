package de.innovas.km.innolectus.irrs.business.server.entity;


import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="applicationServerInstanzen")
@XmlAccessorType(XmlAccessType.FIELD)
public class AppSrvCollection {
	
	@XmlElement(name="applicationServerInstanz")
	List<AppSrv> appSrvList = new ArrayList<>();
	
	public AppSrvCollection() {
		
	}

	public List<AppSrv> getAppSrvList() {
		return appSrvList;
	}

	public void setAppSrvList(List<AppSrv> appSrvList) {
		this.appSrvList = appSrvList;
	}
	
	

}
