/**
 * 
 */
package com.edu.library;

import java.util.List;

import com.edu.library.filter.BorrowFilter;
import com.edu.library.model.Borrow;

/**
 * Defines persistence operations, for Borrow entities.
 * 
 * @author nagys
 * @author gallb
 * @author kiska
 */
public interface IBorrowService extends IService<Borrow> {
	/**
	 * Filters the data specified by the {@code filter} object. This can have
	 * one or more filters set. For example: publication title, the user name
	 * etc.
	 * 
	 * @param filter
	 *            - a custom class that represents all the fields that can be
	 *            filtered of a borrow object.
	 * @return List of Borrows
	 */
	public List<Borrow> filterBorrow(final BorrowFilter filter);

}
