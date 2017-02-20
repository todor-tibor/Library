package com.edu.library.util;

import java.io.Serializable;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.enterprise.context.SessionScoped;


@SessionScoped
public class PropertyProvider implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6253902624155383715L;
	private ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("errorMessages",Locale.ENGLISH);
	
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
		RESOURCE_BUNDLE = ResourceBundle.getBundle("errorMessages", locale);
	}
	
}
