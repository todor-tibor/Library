package com.edu.library.model.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.edu.library.model.Author;
import com.edu.library.model.Book;
import com.edu.library.model.Magazine;
import com.edu.library.model.Newspaper;
import com.edu.library.model.Publication;

/**
 * Class that implements the export of all the publications to an ".xml" file.
 *
 * @author kiska
 *
 */
public class WriteXMLFile {
	private static String publicationType = "";
	static final Document doc = new Document();

	private WriteXMLFile() {
	}

	/**
	 * Writes the publications given by {@code publications} to the file given
	 * in {@code filename}.
	 *
	 * @param publications
	 *            - a list containing all the publications
	 * @param filename
	 *            - the name of the file to which the data will be written
	 *            Throws exception if it can't write to the ".xml" file.
	 */
	public static void exportData(final List<Publication> publications, final String filename) {

		final Element allPublications = new Element("allPublications");

		doc.setRootElement(allPublications);

		writeBooks(publications);
		writeNewspapers(publications);
		writeMagazines(publications);

		final XMLOutputter xmlOutput = new XMLOutputter();
		xmlOutput.setFormat(Format.getPrettyFormat());
		try {
			xmlOutput.output(doc, new FileWriter("C:\\" + filename + ".xml"));
		} catch (final IOException io) {
			throw new RuntimeException("Cannot save to xml!");
		}

	}

	/**
	 * Write all the magazine given by {@code publications} to the XML file.
	 *
	 * @param publications
	 *            - list of all the magazines
	 */
	private static void writeMagazines(final List<Publication> publications) {
		final List<Magazine> magazines = publications.stream().filter(p -> p instanceof Magazine).map(p -> (Magazine) p)
				.collect(Collectors.toList());
		publicationType = "Magazine";
		for (final Magazine magazine : magazines) {
			addElements(magazine, publicationType);
		}
	}

	/**
	 * Write all the book given by {@code publications} to the XML file.
	 *
	 * @param publications
	 *            - list of all the books
	 */
	private static void writeBooks(final List<Publication> publications) {
		final List<Book> books = publications.stream().filter(p -> p instanceof Book).map(p -> (Book) p)
				.collect(Collectors.toList());
		publicationType = "Book";
		for (final Book book : books) {
			addElements(book, publicationType);
		}
	}

	/**
	 * Write all the newspaper given by {@code publications} to the XML file.
	 *
	 * @param publications
	 *            - list of all the books
	 */
	private static void writeNewspapers(final List<Publication> publications) {
		final List<Newspaper> newspapers = publications.stream().filter(p -> p instanceof Newspaper)
				.map(p -> (Newspaper) p).collect(Collectors.toList());
		publicationType = "Newspaper";
		for (final Newspaper newspaper : newspapers) {
			addElements(newspaper, publicationType);
		}
	}

	/**
	 * Writes the information given by {@code pub} of type {@code type} to an
	 * XML file.
	 *
	 * @param pub
	 *            - the publication to export
	 * @param type
	 *            - the type of the publication
	 */
	private static void addElements(final Publication pub, final String type) {
		final Element publicationElement = new Element(type);

		addParams(publicationElement, pub);

		List<Author> authors = new ArrayList<>();
		if (type.equals("Book")) {
			authors = (((Book) pub).getAuthors().stream().collect(Collectors.toList()));
		} else if (type.equals("Magazine")) {
			authors = (((Magazine) pub).getAuthors().stream().collect(Collectors.toList()));
		}
		addAuthors(publicationElement, authors);
		doc.getRootElement().addContent(publicationElement);
	}

	/**
	 * Adds the authors given by {@code authors} to the XML element given by
	 * {@code publicationElement}
	 *
	 * @param publicationElement
	 *            - the XML element to which the author's data will be written
	 * @param authors
	 *            - list of a publication's authors
	 */
	private static void addAuthors(final Element publicationElement, final List<Author> authors) {
		for (final Author au : authors) {
			publicationElement.addContent(addAuthorParam(au));
		}
	}

	/**
	 * Sets the author attribute given by {@code au} to an XML type element.
	 *
	 * @param au
	 *            - the author to write to the XML type element
	 * @return - the XML element that contains the information of an Author
	 *         object given by {@code au}
	 */
	private static Element addAuthorParam(final Author au) {
		final Element author = new Element("author");
		author.setAttribute(new Attribute("uuid", au.getUuid()));
		author.addContent(new Element("name").setText(au.getName()));
		return author;
	}

	/**
	 * Adds the publication elements given by {@code publication} to the element
	 * given by {@code publicationElement}
	 *
	 * @param publicationElement
	 *            - the XML type element to which data will be added
	 * @param publication
	 *            - the publication from which the data will be written
	 */
	private static void addParams(final Element publicationElement, final Publication publication) {
		publicationElement.setAttribute(new Attribute("uuid", publication.getUuid()));
		publicationElement.addContent(new Element("title").setText(publication.getTitle()));
		publicationElement.addContent(new Element("nrOfCopies").setText(Integer.toString(publication.getNrOfCopys())));
		publicationElement.addContent(new Element("onStock").setText(Integer.toString(publication.getOnStock())));

		final Element publisher = new Element("publisher");
		publisher.setAttribute(new Attribute("uuid", publication.getPublisher().getUuid()));
		publisher.addContent(new Element("name").setText(publication.getPublisher().getName()));
		publicationElement.addContent(publisher);

		publicationElement
				.addContent(new Element("publicationDate").setText(publication.getPublicationDate().toString()));
	}

}
