/**
 * 
 */
package com.edu.library;

import java.io.Serializable;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 * @author gallb
 *
 */

//@Startup
@SessionScoped
public class LocaleManager implements Serializable{
	
	  /**
	 * 
	 */
	private static final long serialVersionUID = -296467386290222310L;
	private Locale userLocale;
	
	 @PostConstruct
	 private void init() {
	    userLocale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
	  }
	  
	public void setUserLocale(Locale userLocale)
	  {
		System.out.println("locale set.");
	    this.userLocale = userLocale;
	  }
	  public Locale getUserLocale()
	  {
		  System.out.println("locale get.");
	    return userLocale;
	  }
	  
	/*  public void changeLocale(ActionEvent actionEvent)
	  {
	    String id = actionEvent.getComponent().getId();
	    setUserLocale(id.substring(id.lastIndexOf("_")+1));
	  }*/
}
