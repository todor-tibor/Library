package com.edu.library.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.edu.library.model.Publication;
import com.edu.library.model.UnifiedModel;

public class DataExctractor {
	public List<UnifiedModel> publicationExtractor(final List<Publication> pubs) {
		final List<String> additionalDetails = new ArrayList<>();
		final List<UnifiedModel> listOfConvertedPublications = new ArrayList<>();

		for (final Publication publication : pubs) {
			final String descriptor = publication.getTitle();

			final Map<String, String> details = new HashMap<String, String>();
			details.put("Publisher", publication.getPublisher().getName());
			details.put("Left on stock", Integer.toString(publication.getOnStock()));
			details.put("Nr. of copies", Integer.toString(publication.getNrOfCopys()));
			details.put("Publication date", publication.getPublicationDate().toString());

			System.out.println("||||||   borrows   ||||||");
			publication.getBorrows().forEach(b -> additionalDetails.add(b.toString()));

			final UnifiedModel unifiedModel = new UnifiedModel(descriptor, details, additionalDetails);
			listOfConvertedPublications.add(unifiedModel);
		}

		listOfConvertedPublications.forEach(u -> System.out.println(u.toString()));
		return listOfConvertedPublications;
	}
}
