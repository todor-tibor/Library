/**
 * 
 */
package com.edu.library;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.edu.library.util.PropertyProvider;

/**
 * MessageService for ManagedBeans
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
	public void error(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", provider.getProperty(message)));
	}

	/**
	 * Sets faces context info message.
	 * 
	 * @param message
	 */
	public void info(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", provider.getProperty(message)));
	}

	/**
	 * Sets faces context warning message.
	 * 
	 * @param message
	 */
	public void warn(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", provider.getProperty(message)));
	}

	/**
	 * Sets faces context fatal error message.
	 * 
	 * @param message
	 */
	public void fatal(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", provider.getProperty(message)));
	}

}
