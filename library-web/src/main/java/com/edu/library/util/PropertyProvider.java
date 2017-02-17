package com.edu.library.util;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public enum PropertyProvider {
	INSTANCE;

	private static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("errorMessages",Locale.ENGLISH);
	
	private PropertyProvider() {
	}
	/**
	 * get property using ResourceBundle
	 * 
	 * @param property
	 *            - name of property
	 * @return value of the property
	 */
	public String getProperty(String property) {
		try {
			return RESOURCE_BUNDLE.getString(property);
		} catch (MissingResourceException e) {
			return "!" + property + "!";
		}
	}
	
	public void setLocale(Locale locale) {
		RESOURCE_BUNDLE = ResourceBundle.getBundle("booklandBundle", locale);
	}
	
}
