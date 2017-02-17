package com.edu.library;

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
}
