package com.edu.library;

import java.io.Serializable;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.edu.library.util.PropertyProvider;

@ManagedBean(name = "amibeanunk")
@SessionScoped
public class SessionManagerMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5581520559417627350L;

	@Inject
	LocaleManager localeManager;
	@Inject
	PropertyProvider provider;

	public void englishNow() {
		System.out.println("Before: " + FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
		FacesContext.getCurrentInstance().getViewRoot().setLocale(Locale.ENGLISH);
		provider.setLocale(Locale.ENGLISH);
		localeManager.setUserLocale(Locale.ENGLISH);
		System.out.println("After english now: " + FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
	}

	public void hungarianNow() {
		System.out.println("Before: " + FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
		Locale tmpLoc = new Locale("hu", "HU");
		FacesContext.getCurrentInstance().getViewRoot().setLocale(tmpLoc);
		provider.setLocale(tmpLoc);
		localeManager.setUserLocale(tmpLoc);
		System.out.println(
				"After hungarian now: " + FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
	}
	
	public void reseletLocale(){		
		FacesContext.getCurrentInstance().getViewRoot().setLocale(localeManager.getUserLocale());
	}
}
