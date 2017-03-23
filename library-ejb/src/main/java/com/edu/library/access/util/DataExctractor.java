package com.edu.library.access.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.edu.library.model.Author;
import com.edu.library.model.Book;
import com.edu.library.model.Borrow;
import com.edu.library.model.Magazine;
import com.edu.library.model.Publication;
import com.edu.library.model.Publisher;
import com.edu.library.model.Role;
import com.edu.library.model.UnifiedModel;
import com.edu.library.model.User;

/**
 * Implements the data extraction for PDF export of the publication, user,
 * publisher, author, role, borrow objects.
 *
 * @author kiska
 *
 */
public class DataExctractor {
	/**
	 * Extracts data from a list of publication objects into a list of
	 * UnifiedModel objects, which is the model that is compatible with the PDF
	 * export mechanism.
	 *
	 * @param pubs
	 *            - the list of publications
	 * @return - the list of Unified models containing data extracted from the
	 *         {@code pubs} list
	 */
	public List<UnifiedModel> publicationExtractor(final List<Publication> pubs) {

		final List<UnifiedModel> listOfConvertedPublications = new ArrayList<>();

		for (final Publication publication : pubs) {
			final String descriptor = publication.getTitle();
			final String description = "Provides information about publications.";
			final Map<String, String> details = new HashMap<String, String>();
			final List<String> additionalDetails = new ArrayList<>();
			details.put("Publisher", publication.getPublisher().getName());
			details.put("Left on stock", Integer.toString(publication.getOnStock()));
			details.put("Nr. of copies", Integer.toString(publication.getNrOfCopys()));
			details.put("Publication date", publication.getPublicationDate().toString());

			if (publication instanceof Book) {
				additionalDetails.add("Written by " + ((Book) publication).getAuthors().toString());
			} else if (publication instanceof Magazine) {
				additionalDetails.add("Written by " + ((Magazine) publication).getAuthors().toString());
			} else {
				additionalDetails.add("There are no authors for this publication");
			}

			if (publication.getBorrows().isEmpty()) {
				additionalDetails.add("Nobody has borrowed this publication.");
			} else {
				additionalDetails.add(publication.getBorrows().toString());
			}
			final UnifiedModel unifiedModel = new UnifiedModel(descriptor, description, details, additionalDetails);
			listOfConvertedPublications.add(unifiedModel);
		}
		return listOfConvertedPublications;
	}

	/**
	 * Extracts data from a list of user objects into a list of UnifiedModel
	 * objects, which is the model that is compatible with the PDF export
	 * mechanism.
	 *
	 * @param users
	 *            - the list of users
	 * @return - the list of Unified models containing data extracted from the
	 *         {@code users} list
	 */
	public List<UnifiedModel> userExtractor(final List<User> users) {

		final List<UnifiedModel> listOfConvertedPublications = new ArrayList<>();

		for (final User user : users) {
			final String descriptor = user.getUserName();
			final String description = "Provides information about users.";
			final Map<String, String> details = new HashMap<String, String>();
			final List<String> additionalDetails = new ArrayList<>();
			details.put("Email", user.getEmail());
			details.put("Loyalty index", Integer.toString(user.getLoyaltyIndex()));
			additionalDetails.add("Role: " + user.getRoles().toString());

			if (user.getBorrows().isEmpty()) {
				additionalDetails.add("This user has nothing borrowed.");
			} else {
				additionalDetails.add(user.getBorrows().toString());
			}
			final UnifiedModel unifiedModel = new UnifiedModel(descriptor, description, details, additionalDetails);
			listOfConvertedPublications.add(unifiedModel);
		}
		return listOfConvertedPublications;
	}

	/**
	 * Extracts data from a list of role objects into a list of UnifiedModel
	 * objects, which is the model that is compatible with the PDF export
	 * mechanism.
	 *
	 * @param roles
	 *            - the list of roles
	 * @return - the list of Unified models containing data extracted from the
	 *         {@code roles} list
	 */
	public List<UnifiedModel> roleExtractor(final List<Role> roles) {
		final List<UnifiedModel> listOfConvertedPublications = new ArrayList<>();

		for (final Role role : roles) {
			final String descriptor = role.getRole();
			final String description = "Provides information about roles.";
			final Map<String, String> details = new HashMap<String, String>();
			final List<String> additionalDetails = new ArrayList<>();

			final UnifiedModel unifiedModel = new UnifiedModel(descriptor, description, details, additionalDetails);
			listOfConvertedPublications.add(unifiedModel);
		}
		return listOfConvertedPublications;
	}

	/**
	 * Extracts data from a list of author objects into a list of UnifiedModel
	 * objects, which is the model that is compatible with the PDF export
	 * mechanism.
	 *
	 * @param authors
	 *            - the list of authors
	 * @return - the list of Unified models containing data extracted from the
	 *         {@code authors} list
	 */
	public List<UnifiedModel> authorExtractor(final List<Author> authors) {
		final List<UnifiedModel> listOfConvertedPublications = new ArrayList<>();

		for (final Author author : authors) {
			final String descriptor = author.getName();
			final String description = "Provides information about authors.";
			final Map<String, String> details = new HashMap<String, String>();
			final List<String> additionalDetails = new ArrayList<>();

			final UnifiedModel unifiedModel = new UnifiedModel(descriptor, description, details, additionalDetails);
			listOfConvertedPublications.add(unifiedModel);
		}
		return listOfConvertedPublications;
	}

	/**
	 * Extracts data from a list of publisher objects into a list of
	 * UnifiedModel objects, which is the model that is compatible with the PDF
	 * export mechanism.
	 *
	 * @param publishers
	 *            - the list of publishers
	 * @return - the list of Unified models containing data extracted from the
	 *         {@code publishers} list
	 */
	public List<UnifiedModel> publisherExtractor(final List<Publisher> publishers) {
		final List<UnifiedModel> listOfConvertedPublications = new ArrayList<>();

		for (final Publisher publisher : publishers) {
			final String descriptor = publisher.getName();
			final String description = "Provides information about publishers.";
			final Map<String, String> details = new HashMap<String, String>();
			final List<String> additionalDetails = new ArrayList<>();

			final UnifiedModel unifiedModel = new UnifiedModel(descriptor, description, details, additionalDetails);
			listOfConvertedPublications.add(unifiedModel);
		}
		return listOfConvertedPublications;
	}

	/**
	 * Extracts data from a list of borrow objects into a list of UnifiedModel
	 * objects, which is the model that is compatible with the PDF export
	 * mechanism.
	 *
	 * @param borrows
	 *            - the list of borrows
	 * @return - the list of Unified models containing data extracted from the
	 *         {@code borrows} list
	 */
	public List<UnifiedModel> borrowExtractor(final List<Borrow> borrows) {
		final List<UnifiedModel> listOfConvertedPublications = new ArrayList<>();

		for (final Borrow borrow : borrows) {
			final String descriptor = borrow.toString();
			final String description = "Provides information about borrowings.";
			final Map<String, String> details = new HashMap<String, String>();
			final List<String> additionalDetails = new ArrayList<>();

			final UnifiedModel unifiedModel = new UnifiedModel(descriptor, description, details, additionalDetails);
			listOfConvertedPublications.add(unifiedModel);
		}
		return listOfConvertedPublications;
	}
}
