package com.edu.library.util;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import com.edu.library.exception.ErrorLevel;
import com.edu.library.exception.LibraryException;

/**
 * ExceptionHandler to handle exceptions and show it using MessageService
 *
 * @author sipost
 *
 */
@SessionScoped
public class ExceptionHandler implements Serializable {
	private static final long serialVersionUID = -2696292772744640375L;
	@Inject
	MessageService message;

	public void showMessage(final Exception e) {
		Throwable t = e;

		while ((t != null) && !(t instanceof LibraryException) && !(t instanceof IllegalArgumentException)) {
			t = t.getCause();
		}
		if (t instanceof LibraryException) {
			ErrorLevel level = ((LibraryException) t).getLevel();
			if (ErrorLevel.ERROR.equals(level)) {
				this.message.error(t.getMessage());
			}
			if (ErrorLevel.WARNING.equals(level)) {
				this.message.warn(t.getMessage());
			}
		} else if (t instanceof IllegalArgumentException) {
			this.message.warn(t.getMessage());
		} else {
			this.message.error(e.getMessage());
		}
	}
}
