/**
 * 
 */
package com.edu.library;

import java.util.List;

import com.edu.library.filter.BorrowFilter;
import com.edu.library.model.Borrow;

/**
 * @author nagys, gallb, kiska
 *
 */
public interface IBorrowService extends IService<Borrow> {
	public List<Borrow> filterBorrow(BorrowFilter filter);

	public List<Borrow> getBorrowLate();

	/**
	 * Sends an e-mail message to the user of the given borrowing, with
	 * notification text to return the specified publication.
	 * 
	 * @param borrow
	 *            - the borrow object that is late
	 */
	public void mailOneLateUser(final Borrow borrow);

}
