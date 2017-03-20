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
	 * Lists Publications that are in the database.
	 *
	 * @param start
	 *            - start row number
	 * @param fin
	 *            - number of data per page
	 * @return List containing all entities.
	 */
	public List<Publication> getAll(final int start, final int fin);

	/**
	 * Lists of publications that is in the database and contains the
	 * {@code searchTxt} input string
	 *
	 * @param searchTxt
	 *            - String to search for
	 * @param start
	 *            - start row number
	 * @param fin
	 *            - number of data per page
	 * @return List of entities found, empty list if nothing found.
	 */
	public List<Publication> search(final String text, final int start, final int fin);

	/**
	 * Lists the publications that have in content the text given by
	 * {@code searchText}
	 *
	 * @param searchText
	 *            - the text to search for in the content
	 * @param start
	 *            - start row number
	 * @param fin
	 *            - number of data per page
	 * @return List of publications founds, empty list if there was no match
	 */
	public List<Publication> searchContent(final String searchText, final int start, final int fin);

	/**
	 * Count publications that is in the database.
	 *
	 * @return Number of Publications found
	 */
	public long countAll();

	/**
	 * Count publications that is in the database and contains the
	 * {@code searchTxt} input string
	 *
	 * @param title
	 *            - String to search for
	 * @return Number of Publications found
	 */
	public long countSearch(final String title);

	/**
	 * Filters the data specified by the {@code filter} object. This can have
	 * one or more filters set. For example: publication title, publisher etc.
	 *
	 * @param filter
	 *            - a custom class that represents all the fields that can be
	 *            filtered of a publication object.
	 * @param start
	 *            -start row number
	 * @param fin
	 *            -number of data per page
	 * @return List of Publications
	 */
	public List<Publication> filterPublication(PublicationFilter filter, final int start, final int fin);

	/**
	 * Count the data specified by the {@code filter} object. This can have one
	 * or more filters set. For example: publication title, publisher etc.
	 *
	 * @param filter
	 *            - a custom class that represents all the fields that can be
	 *            filtered of a publication object.
	 * @return Number of Publications filtered
	 */
	public long countFilter(PublicationFilter filter);
}
