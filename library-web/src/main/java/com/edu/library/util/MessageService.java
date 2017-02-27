/**
 *
 */
package com.edu.library.util;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 * MessageService for ManagedBeans, using PropertyProvider to get value of the
 * message key
 *
 * @author sipost
 */
@SessionScoped
public class MessageService implements Serializable {

	private static final long serialVersionUID = -4702598250751689454L;

	@Inject
	PropertyProvider provider;

	/**
	 * Sets faces context error message.
	 *
	 * @param message
	 */
	public void error(final String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", this.provider.getProperty(message)));
	}

	/**
	 * Sets faces context info message.
	 *
	 * @param message
	 */
	public void info(final String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", this.provider.getProperty(message)));
	}

	/**
	 * Sets faces context warning message.
	 *
	 * @param message
	 */
	public void warn(final String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", this.provider.getProperty(message)));
	}

	/**
	 * Sets faces context fatal error message.
	 *
	 * @param message
	 */
	public void fatal(final String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", this.provider.getProperty(message)));
	}

}
