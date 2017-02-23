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

}
