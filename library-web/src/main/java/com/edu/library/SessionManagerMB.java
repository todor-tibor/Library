package com.edu.library;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

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

	public void englishNow() {
		System.out.println("Before: " + FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
		// Locale tmpLoc = new Locale("en", "US");
		FacesContext.getCurrentInstance().getViewRoot().setLocale(Locale.ENGLISH);
		PropertyProvider.setLocale(Locale.ENGLISH);
		localeManager.setUserLocale(Locale.ENGLISH);
		System.out.println("After english now: " + FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
	}

	public void hungarianNow() {
		System.out.println("Before: " + FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
		Locale tmpLoc = new Locale("hu", "HU");
		FacesContext.getCurrentInstance().getViewRoot().setLocale(tmpLoc);
		PropertyProvider.setLocale(tmpLoc);
		localeManager.setUserLocale(tmpLoc);
		System.out.println(
				"After hungarian now: " + FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
	}
	
	public void reseletLocale(){		
		FacesContext.getCurrentInstance().getViewRoot().setLocale(localeManager.getUserLocale());
	}
}
