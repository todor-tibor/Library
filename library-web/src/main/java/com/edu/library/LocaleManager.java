/**
 *
 */
package com.edu.library;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.edu.library.util.PropertyProvider;

/**
 * Locale manager for internationalization
 *
 * @author gallb
 * @author sipost
 */

@Named("localeBean")
@SessionScoped
public class LocaleManager implements Serializable {

	private static final long serialVersionUID = -296467386290222310L;

	/**
	 * Current locale of the application
	 */
	private Locale userLocale;

	@Inject
	private PropertyProvider propertyProvider;

	/**
	 * Gets Locals from the browser and set the locale of the application.
	 */
	@PostConstruct
	private void init() {
		Iterator<Locale> locales = FacesContext.getCurrentInstance().getExternalContext().getRequestLocales();
		while (locales.hasNext()) {
			this.userLocale = locales.next();
			if ("hu".equals(this.userLocale.getLanguage()) || "HU".equals(this.userLocale.getCountry())) {
				this.userLocale = new Locale("hu", "HU");
				this.propertyProvider.setLocale(this.userLocale);
				return;
			} else if ("en".equals(this.userLocale.getLanguage()) || "US".equals(this.userLocale.getCountry())) {
				this.userLocale = Locale.ENGLISH;
				this.propertyProvider.setLocale(this.userLocale);
				return;
			}
		}
		this.userLocale = Locale.ENGLISH;
		this.propertyProvider.setLocale(this.userLocale);
	}

	public void setUserLocale(final Locale userLocale) {
		this.userLocale = userLocale;
	}

	public Locale getUserLocale() {
		return this.userLocale;
	}
}
