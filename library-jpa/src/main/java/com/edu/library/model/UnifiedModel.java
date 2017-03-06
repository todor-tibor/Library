package com.edu.library.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnifiedModel {
	String descriptor;
	Map<String, String> details = new HashMap<>();
	List<String> additionalDetails = new ArrayList<>();

	public UnifiedModel(final String descriptor, final Map<String, String> details,
			final List<String> additionalDetails) {
		super();
		this.descriptor = descriptor;
		this.details = details;
		this.additionalDetails = additionalDetails;
	}

	public UnifiedModel() {
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

}
