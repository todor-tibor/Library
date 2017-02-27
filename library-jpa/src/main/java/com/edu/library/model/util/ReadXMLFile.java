package com.edu.library.model.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

public class ReadXMLFile {

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
				publication.setUuid(publicationElement.getAttributeValue("uuid"));
				publication.setTitle(publicationElement.getChildText("title"));
				publication.setNrOfCopys(Integer.parseInt(publicationElement.getChildText("nrOfCopies")));
				publication.setOnStock(Integer.parseInt(publicationElement.getChildText("onStock")));

				final Publisher publisher = new Publisher();
				publisher.setName(publicationElement.getChildText("publisher"));
				// for (Element )
				publication.setPublisher(publisher);
				final List<Element> authorList = publicationElement.getChildren("author");
				final List<Author> authors = new ArrayList<>();
				for (final Element element : authorList) {
					final Author author = new Author();

					author.setUuid(element.getAttributeValue("uuid"));
					author.setName(element.getChildText("name"));
					authors.add(author);
				}
				publication.setAuthors(authors);
				publicationsList.add(publication);
			}

			final List<Element> bookListElements = root.getChildren("Book");

			for (final Element bookElement : bookListElements) {
				final Book book = new Book();
				book.setUuid(bookElement.getAttributeValue("uuid"));
				book.setTitle(bookElement.getChildText("title"));
				book.setNrOfCopys(Integer.parseInt(bookElement.getChildText("nrOfCopies")));
				book.setOnStock(Integer.parseInt(bookElement.getChildText("onStock")));
				final Publisher publisher = new Publisher();
				publisher.setName(bookElement.getChildText("publisher"));
				book.setPublisher(publisher);

				final List<Element> authorList = bookElement.getChildren("author");
				final List<Author> authors = new ArrayList<>();
				for (final Element element : authorList) {
					final Author author = new Author();

					author.setUuid(element.getAttributeValue("uuid"));
					author.setName(element.getChildText("name"));
					authors.add(author);
				}
				book.setAuthors(authors);
				publicationsList.add(book);
			}

			final List<Element> newsListElements = root.getChildren("Newspaper");
			new ArrayList<>();
			for (final Element newsElement : newsListElements) {
				final Newspaper news = new Newspaper();
				news.setUuid(newsElement.getAttributeValue("uuid"));
				news.setTitle(newsElement.getChildText("title"));
				news.setNrOfCopys(Integer.parseInt(newsElement.getChildText("nrOfCopies")));
				news.setOnStock(Integer.parseInt(newsElement.getChildText("onStock")));
				final Publisher publisher = new Publisher();
				publisher.setName(newsElement.getChildText("publisher"));
				news.setPublisher(publisher);

				publicationsList.add(news);
			}
			return publicationsList;
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	// Get JDOM document from DOM Parser
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
