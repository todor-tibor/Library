package gallb.wildfly.users.web;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import gallb.wildfly.users.common.IBorrow;
import gallb.wildfly.users.common.LibraryException;
import model.Borrow;
import model.Publication;
import model.User;

@Named("borrowBean")

@SessionScoped
public class BorrowMB implements Serializable {

	/**
	 * 
	 */
	private Logger oLogger = Logger.getLogger(BorrowMB.class);
	private static final long serialVersionUID = 1479586528417507035L;

	@Inject
	private IBorrow oBorrowBean;

	private List<Borrow> borrows = new ArrayList<>();
	private User currentUser;
	private Publication currentPublication;

	private Borrow borrow = null;

	public List<Borrow> getBorrowsList() {
		return borrows;
	}

	public Borrow getCurrentBorrow() {
		return borrow;
	}

	public void setCurrentBorrow(Borrow borrow) {
		this.borrow = borrow;
	}

	/**
	 * @return
	 */
	public List<Borrow> getAll() {
		oLogger.info("--getAllBorrows()--");
		borrows.clear();
		try {
			oLogger.info("--getAllBorrows()--borrows queried");
			borrows = oBorrowBean.getAll();
		} catch (LibraryException e) {

			MessageService.error("Server internal error.");
		}
		return borrows;
	}

	public void store(Date dateFrom, Date dateUntil) {

		Borrow p_Borrow;
		p_Borrow = new Borrow();
		if ((currentPublication == null) && (currentUser == null) && (dateFrom == null) && (dateUntil == null)) {
			MessageService.warn("All field is requered");
			
		}else{
			p_Borrow.setUser(currentUser);
			p_Borrow.setPublication(currentPublication);
			p_Borrow.setBorrowFrom(dateFrom);
			p_Borrow.setBorrowUntil(dateUntil);
			try {
				oBorrowBean.store(p_Borrow);
				borrows.add(p_Borrow);
			} catch (LibraryException e) {
				MessageService.error(e.getMessage());
			}
			MessageService.info("Succesfully added: " + p_Borrow);
		}
	}

	public void remove() {
		oLogger.info("--remove publication by Id ManagedBean--p_id:" + borrow.getUuid());
		if (borrow == null) {
			MessageService.error("Empty field");
		} else {
			try {
				oBorrowBean.remove(borrow.getUuid());
				getAll();
			} catch (LibraryException e) {
				oLogger.error(e);
				MessageService.error(e.getMessage());
			}

		}
	}

}
