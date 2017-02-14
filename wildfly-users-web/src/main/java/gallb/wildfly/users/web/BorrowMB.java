package gallb.wildfly.users.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import gallb.wildfly.users.common.IBorrow;
import gallb.wildfly.users.common.LibraryException;
import model.Borrow;

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
	public List<Borrow> getALl() {
		oLogger.info("--getAllBorrows()--");
		try {
			oLogger.info("--getAllBorrows()--borrows queried");
			borrows = oBorrowBean.getAll();
		} catch (LibraryException e) {
			
			MessageService.error("Server internal error.");
		}
		return borrows;
	}

}
