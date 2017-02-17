package com.edu.library.business.publicationManagement;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.edu.library.data.publicationManagement.PublisherManager;

@Stateless
@LocalBean
public class PublisherManagementBusiness {
	
	@EJB
	private PublisherManager publisherManager;
	
	

}
