/**
 * 
 */
package gallb.wildfly.users.web;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * MessageService for ManageBeans
 * 
 * @author sipost
 */
@ApplicationScoped
public class MessageService implements Serializable {

	private static final long serialVersionUID = -4702598250751689454L;

	/**
	 * Sets faces context error message.
	 * 
	 * @param message
	 */
	public static void error(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", message));
	}

	/**
	 * Sets faces context info message.
	 * 
	 * @param message
	 */
	public static void info(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", message));
	}

	/**
	 * Sets faces context warning message.
	 * 
	 * @param message
	 */
	public static void warn(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", message));
	}

	/**
	 * Sets faces context fatal error message.
	 * 
	 * @param message
	 */
	public static void fatal(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", message));
	}

}
