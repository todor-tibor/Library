package com.edu.library.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that represents a unified data model for data export.
 *
 * @author kiska
 *
 */
public class UnifiedModel {
	/**
	 * The title, name, etc. of the data
	 */
	String descriptor;
	/**
	 * Detailed information about the data, it specifies what the data
	 * represents.
	 */
	String description;
	/**
	 * The details of data given as key-value elements, such as stockNr, date,
	 * etc.
	 */
	Map<String, String> details = new HashMap<>();
	/**
	 * More information about the data. A list containing usually important but
	 * not mandatory information about the given data. Such is the list borrowed
	 * publication in case of a user type data.
	 */
	List<String> additionalDetails = new ArrayList<>();

	/**
	 * Creates a UnifiedModel object, sets it's fields to the values provided as
	 * parameters
	 * 
	 * @param descriptor
	 *            - the descriptor of the data, usually name or title, rarely it
	 *            is the name of the class
	 * @param description
	 *            - specifies what the data represents
	 * @param details
	 *            - some properties of the data given by key-value elements
	 * @param additionalDetails
	 *            - more information in the form of list (list of borrowings)
	 */
	public UnifiedModel(final String descriptor, final String description, final Map<String, String> details,
			final List<String> additionalDetails) {
		this.descriptor = descriptor;
		this.description = description;
		this.details = details;
		this.additionalDetails = additionalDetails;
	}

	@Override
	public String toString() {
		return this.descriptor + ", " + this.details.toString();
	}

	public String getDescriptor() {
		return this.descriptor;
	}

	public Map<String, String> getDetails() {
		return this.details;
	}

	public List<String> getAdditionalDetails() {
		return this.additionalDetails;
	}

	public String getDescription() {
		return this.description;
	}

}
