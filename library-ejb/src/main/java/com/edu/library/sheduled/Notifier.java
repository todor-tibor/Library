package com.edu.library.sheduled;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import com.edu.library.business.publicationManagement.BorrowManagementBusiness;
import com.edu.library.model.Borrow;

/**
 * Sends scheduled notifications to users by email
 * 
 * @author gallb
 *
 */

@Stateless
public class Notifier {

	private static Logger logger = Logger.getLogger(Notifier.class);

	@EJB
	private BorrowManagementBusiness borrowHandler;

	/**
	 * Runs automatically once every day. Searches for late borrows, iterates
	 * over them and sends a notification e-mail to the users.
	 */
	@Schedule(minute = "33", hour = "11", persistent = false)
	private void automaticDailyLateNotification() {
		final List<Borrow> brwLate = this.borrowHandler.getBorrwLate();
		for (int i = 0; i < brwLate.size(); i++) {
			this.borrowHandler.mailOneLateUser(brwLate.get(i));
			logger.info("Mailing late user: " + brwLate.get(i).getUser().getEmail());
		}
	}
}
