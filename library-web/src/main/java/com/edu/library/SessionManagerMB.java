package com.edu.library;

import java.io.Serializable;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.edu.library.util.PropertyProvider;

/**
 * Session manager. Help to change language.
 *
 * @author gallB
 */
@ManagedBean(name = "amibeanunk")
@SessionScoped
public class SessionManagerMB implements Serializable {

	private static final long serialVersionUID = 5581520559417627350L;

	@Inject
	LocaleManager localeManager;
	@Inject
	PropertyProvider provider;

	/**
	 * Change Language to English.
	 */
	public void englishNow() {
		FacesContext.getCurrentInstance().getViewRoot().setLocale(Locale.ENGLISH);
		this.provider.setLocale(Locale.ENGLISH);
		this.localeManager.setUserLocale(Locale.ENGLISH);
	}

	/**
	 * Change Language to hungarian.
	 */
	public void hungarianNow() {
		Locale tmpLoc = new Locale("hu", "HU");
		FacesContext.getCurrentInstance().getViewRoot().setLocale(tmpLoc);
		this.provider.setLocale(tmpLoc);
		this.localeManager.setUserLocale(tmpLoc);
	}
}
