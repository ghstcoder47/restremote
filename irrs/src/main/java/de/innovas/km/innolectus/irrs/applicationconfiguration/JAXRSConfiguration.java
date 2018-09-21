package de.innovas.km.innolectus.irrs.applicationconfiguration;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


/**
 * 
 * @author janasd
 * startet das Servlet für die REST-Schnittstelle, erreichbar unter dem String in der @ApplicationPath Annotation
 */

@ApplicationPath("/resources")
public class JAXRSConfiguration extends Application {

}
