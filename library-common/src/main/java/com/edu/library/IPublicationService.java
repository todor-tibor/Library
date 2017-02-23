package com.edu.library;

import java.util.List;

import com.edu.library.filter.PublicationFilter;
import com.edu.library.model.Publication;

/**
 * 
 * IPublication interface extends the IEntity interface which define the crud
 * operations
 * 
 * @author sipost
 *
 */
public interface IPublicationService extends IService<Publication> {
	public static final String jndiNAME = "java:global/wildfly-users-ear-0.0.1-SNAPSHOT/wildfly-users-ejb-0.0.1-SNAPSHOT/PublicationBean";
	
	public List<Publication> filterPublication(PublicationFilter filter);
}
