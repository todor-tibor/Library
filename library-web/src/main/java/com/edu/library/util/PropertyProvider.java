package com.edu.library.util;

import java.io.Serializable;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.enterprise.context.SessionScoped;

import org.jboss.logging.Logger;

/**
 * Property provider to get messages from resource bundle using property name
 *
 * @author sipost
 */
@SessionScoped
public class PropertyProvider implements Serializable {

	private static final long serialVersionUID = 6253902624155383715L;

	private final Logger logger = Logger.getLogger(PropertyProvider.class);
	/**
	 * Represents the ResourceBundle for user friendly error messages used at
	 * Internationalization.
	 */
	private ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("errorMessages", Locale.ENGLISH);

	/**
	 * Get property using ResourceBundle
	 *
	 * @param property
	 *            - name of property
	 * @return value of the property
	 */

	public String getProperty(final String property) {
		try {
			return this.RESOURCE_BUNDLE.getString(property);
		} catch (final MissingResourceException e) {
			this.logger.error("Missing Resource");
			return "!" + property + "!";
		} catch (final Exception e) {
			return property;
		}
	}

	/**
	 * Reset Resource bundle using the new locale
	 *
	 * @param locale
	 */
	public void setLocale(final Locale locale) {
		this.RESOURCE_BUNDLE = ResourceBundle.getBundle("errorMessages", locale);
	}

}
