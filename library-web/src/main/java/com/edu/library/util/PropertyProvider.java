package com.edu.library.util;

import java.util.ResourceBundle;

public class PropertyProvider {

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("errorMessages");

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
		return RESOURCE_BUNDLE.getString(property);
	}
}
