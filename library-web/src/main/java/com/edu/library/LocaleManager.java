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
import javax.inject.Named;

/**
 * @author gallb
 * @author sipost
 */

@Named("localeBean")
@SessionScoped
public class LocaleManager implements Serializable {

	private static final long serialVersionUID = -296467386290222310L;
	private Locale userLocale;

	@PostConstruct
	private void init() {
		Iterator<Locale> locales=FacesContext.getCurrentInstance().getExternalContext().getRequestLocales();
		while(locales.hasNext()){
			userLocale=locales.next();
			if("hu".equals(userLocale.getLanguage()) || "HU".equals(userLocale.getCountry())){
				userLocale=new Locale("hu", "HU");
				return;
			}else if("en".equals(userLocale.getLanguage()) || "US".equals(userLocale.getCountry())){
				userLocale=Locale.ENGLISH;
				return;
			}
		}
		userLocale=Locale.ENGLISH;
	}

	public void setUserLocale(Locale userLocale) {
		System.out.println("locale set."+userLocale);
		this.userLocale = userLocale;
	}

	public Locale getUserLocale() {
		System.out.println("locale get."+userLocale);
		return userLocale;
	}
}
