package com.edu.library;

import java.util.List;

import com.edu.library.filter.PublicationFilter;
import com.edu.library.model.Publication;

/**
 * IPublication interface extends the IEntity interface which define the crud
 * operations
 * 
 * @author sipost
 *
 */
public interface IPublicationService extends IService<Publication> {
	/**
	 * Filters the data specified by the {@code filter} object. This can have
	 * one or more filters set. For example: publication title, publisher etc.
	 * 
	 * @param filter
	 *            - a custom class that represents all the fields that can be
	 *            filtered of a publication object.
	 * @return List of Publications
	 */
	public List<Publication> filterPublication(PublicationFilter filter);
}
