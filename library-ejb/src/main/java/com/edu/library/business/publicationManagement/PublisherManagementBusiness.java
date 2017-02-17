package com.edu.library.business.publicationManagement;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.edu.library.data.publicationManagement.PublisherManager;
import com.edu.library.model.Publisher;

@Stateless
@LocalBean
public class PublisherManagementBusiness {
	
	@EJB
	private PublisherManager publisherManager;
	
	public List<Publisher> getAll(){
		
		return null;
	}
	
	public List<Publisher> search(String p_searchText){
		
		return null;
	}
	
	public Publisher getById(String p_id){
		
		return null;
	}
	
	public void store(Publisher p_value){
		
	}
	
	public void update(Publisher p_value){
		
	}
	public void remove(String p_id){
		Publisher publisher = publisherManager.getById(p_id);
	}
	
	

}
