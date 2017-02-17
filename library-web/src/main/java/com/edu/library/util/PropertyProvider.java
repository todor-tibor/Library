package com.edu.library.util;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.inject.Inject;

import com.edu.library.LocaleManager;

public class PropertyProvider {

	private static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("errorMessages",new Locale(""));

	@Inject 
	static LocaleManager localeManager;
	
	private PropertyProvider() {
	}
	/**
	 * get property using ResourceBundle
	 * 
	 * @param property
	 *            - name of property
	 * @return value of the property
	 */
	public static String getProperty(String property) {
		RESOURCE_BUNDLE = ResourceBundle.getBundle("errorMessages", localeManager.getUserLocale());
		return RESOURCE_BUNDLE.getString(property);
	}
}
