package com.edu.library.util;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import com.edu.library.LibraryException;
import com.edu.library.MessageService;
/**
 * ExceptionHandler to handle exceptions and show it using MessageService
 * @author sipost
 *
 */
@SessionScoped
public class ExceptionHandler implements Serializable {
	private static final long serialVersionUID = -2696292772744640375L;
	@Inject
	MessageService message;

	public void showMessage(Exception e) {
		Throwable t = e;

		while ((t != null) && !(t instanceof LibraryException) && !(t instanceof IllegalArgumentException)) {
			t = t.getCause();
		}
		if (t instanceof LibraryException) {
			int level = ((LibraryException) t).getLevel();
			if (level == 1) {
				message.error(t.getMessage());
			}
			if (level == 2) {
				message.warn(t.getMessage());
			}
		} else if (t instanceof IllegalArgumentException) {
			message.warn(t.getMessage());
		} else {
			message.error(e.getMessage());
		}
	}	
}
