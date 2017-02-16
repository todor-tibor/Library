package com.edu.library.access.publicationManagement;

import java.util.List;

import javax.ejb.Stateless;

import gallb.wildfly.users.common.IPublisherService;
import gallb.wildfly.users.common.LibraryException;
import model.Publisher;

/**
 * @author kiska Implements the basics of user login. Validates the given the
 *         input data.
 */
@Stateless
public class PublisherManagementFacade implements IPublisherService {

	@Override
	public List<Publisher> getAll() throws LibraryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Publisher> search(String p_searchTxt) throws LibraryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Publisher getById(String p_id) throws LibraryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void store(Publisher p_value) throws LibraryException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Publisher p_user) throws LibraryException {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String p_id) throws LibraryException {
		// TODO Auto-generated method stub

	}

}
