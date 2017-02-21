package com.edu.library.access.publicationManagement;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.edu.library.IBorrowService;
import com.edu.library.access.util.ServiceValidation;
import com.edu.library.business.publicationManagement.BorrowManagementBusiness;
import com.edu.library.model.Borrow;

/**
 * @author gallb
 * @author kiska Implements the basics of user login. Validates the given the
 *         input data.
 */
@Stateless
public class BorrowManagementFacade implements IBorrowService {

	@EJB
	private BorrowManagementBusiness borrowBusiness;
	
	@Override
	public List<Borrow> getAll() {
		return borrowBusiness.getAll();
	}

	@Override
	public List<Borrow> search(String p_searchTxt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Borrow getById(String p_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void store(Borrow p_value) {
		//Data validation.
		ServiceValidation.checkNotNull(p_value);
		ServiceValidation.checkNotNull(p_value.getPublication());
		ServiceValidation.checkNotNull(p_value.getUser());
		ServiceValidation.checDateOrder(p_value.getBorrowFrom(), p_value.getBorrowUntil());
		
		borrowBusiness.store(p_value);
	}

	@Override
	public void update(Borrow p_user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String p_id) {
		ServiceValidation.checkUuid(p_id);
		borrowBusiness.remove(p_id);
	}
	
}
