package com.edu.library.model.util;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.DOMBuilder;
import org.xml.sax.SAXException;

import com.edu.library.model.Author;
import com.edu.library.model.Book;
import com.edu.library.model.Magazine;
import com.edu.library.model.Newspaper;
import com.edu.library.model.Publication;
import com.edu.library.model.Publisher;

/**
 * Class that implements the import of all the publications to an ".xml" file.
 *
 * @author kiska
 *
 */
public class ReadXMLFile {

	public ReadXMLFile() {
	}

	/**
	 * Reads the publications from the file given by {@code filename}.
	 *
	 * @param filename
	 *            - the name of the file from which the data will be read.
	 *            Throws exception if something went wrong during the file
	 *            parsing.
	 */
	public static List<Publication> importData(final String filename) {
		// final String fileName = "/Publications/pankaj/employees.xml";
		Document jdomDoc;
		try {
			// we can create JDOM Document from DOM, SAX and STAX Parser Builder
			// classes
			System.out.println("import data");
			jdomDoc = useDOMParser(filename);
			final Element root = jdomDoc.getRootElement();
			final List<Element> publicationListElements = root.getChildren("Magazine");
			final List<Publication> publicationsList = new ArrayList<>();

			for (final Element publicationElement : publicationListElements) {
				final Magazine publication = new Magazine();

				setParameters(publicationElement, publication);
				publication.setAuthors(addAuthorsParam(publicationElement));

				publicationsList.add(publication);
			}

			final List<Element> bookListElements = root.getChildren("Book");

			for (final Element bookElement : bookListElements) {
				final Book book = new Book();

				setParameters(bookElement, book);
				book.setAuthors(addAuthorsParam(bookElement));

				publicationsList.add(book);
			}

			final List<Element> newsListElements = root.getChildren("Newspaper");
			new ArrayList<>();
			for (final Element newsElement : newsListElements) {
				final Newspaper news = new Newspaper();

				setParameters(newsElement, news);

				publicationsList.add(news);
			}
			return publicationsList;
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * Reads the information of an Author object from the file into a list.
	 *
	 * @param publicationElement
	 *            - the XML element from which the authors will be read
	 * @return - a list of all the authors for the XML element given by
	 *         {@code publicationElement}
	 */
	private static List<Author> addAuthorsParam(final Element publicationElement) {
		final List<Element> authorList = publicationElement.getChildren("author");
		final List<Author> authors = new ArrayList<>();
		for (final Element element : authorList) {
			final Author author = new Author();

			author.setUuid(element.getAttributeValue("uuid"));
			author.setName(element.getChildText("name"));
			authors.add(author);
		}
		return authors;
	}

	/**
	 * Reads multiple parameters from the XML element provided by
	 * {@code publicationElement} and invokes the setter methods of the
	 * Publication given by {@code publication}
	 *
	 * @param publicationElement
	 *            - the XML element from which the data is read
	 * @param publication
	 *            - the Publication type object for which the read attributes
	 *            will be set
	 */
	private static void setParameters(final Element publicationElement, final Publication publication) {
		publication.setUuid(publicationElement.getAttributeValue("uuid"));
		publication.setTitle(publicationElement.getChildText("title"));
		publication.setNrOfCopys(Integer.parseInt(publicationElement.getChildText("nrOfCopies")));
		publication.setOnStock(Integer.parseInt(publicationElement.getChildText("onStock")));

		final List<Element> publisherList = publicationElement.getChildren("publisher");
		final List<Publisher> publishers = new ArrayList<>();
		for (final Element element : publisherList) {
			final Publisher publisher = new Publisher();

			publisher.setUuid(element.getAttributeValue("uuid"));
			publisher.setName(element.getChildText("name"));
			publishers.add(publisher);
		}
		publication.setPublisher(publishers.get(0));

		final String date = publicationElement.getChildText("publicationDate");

		final DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date result = null;
		try {
			result = df.parse(date);
		} catch (final ParseException e) {
			e.printStackTrace();
		}

		publication.setPublicationDate(result);
	}

	/**
	 * Gets the JDOM document from the DOM Parser.
	 * 
	 * @param fileName
	 *            - the name of the file to read data from
	 * @return - the Document acquired from {@code fileName}
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */

	private static Document useDOMParser(final String fileName)
			throws ParserConfigurationException, SAXException, IOException {
		// creating DOM Document
		final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		dBuilder = dbFactory.newDocumentBuilder();
		final org.w3c.dom.Document doc = dBuilder.parse(new File("C:\\" + fileName + ".xml"));
		final DOMBuilder domBuilder = new DOMBuilder();
		return domBuilder.build(doc);
	}

}
